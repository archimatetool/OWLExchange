/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.archimatetool.model.FolderType;
import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IFolder;
import com.archimatetool.model.IProperties;
import com.archimatetool.model.IProperty;
import com.archimatetool.model.IRelationship;




/**
 * OWL Model Exporter
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLModelExporter implements IOWLExchangeGlobals {
    
    private static final boolean CopyPrefixesFromDefinitionFile = false;

    // Element to individual mapping
    private Map<IArchimateElement, OWLNamedIndividual> fElementMapping = new Hashtable<IArchimateElement, OWLNamedIndividual>();
    
    private IArchimateModel fModel;
    
    private OWLOntologyManager fOWLOntologyManager;
    private OWLDataFactory fOWLDataFactory;
    
    private OWLOntology fDefinitionOntology;

    private OWLOntology fInstanceOntology;

    
    public void exportModel(IArchimateModel model, File outputFile, File owlDefinitionFile) throws OWLOntologyCreationException, OWLOntologyStorageException {
        fModel = model;
        
        IRI documentIRI = IRI.create(outputFile);
        
        fOWLOntologyManager = OWLManager.createOWLOntologyManager();
        fOWLDataFactory = fOWLOntologyManager.getOWLDataFactory();
        
        // Load the OWL definition file
        fDefinitionOntology = fOWLOntologyManager.loadOntologyFromOntologyDocument(owlDefinitionFile);
        
        // Create a new instance Ontology
        fInstanceOntology = fOWLOntologyManager.createOntology(ARCHI_INTERCHANGE_IRI);
        
        // Write the model to in memory OWL
        writeModel();
        
        // This is the OWL format to persist to
        OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();

        // Copy over prefixes from definition file
        if(CopyPrefixesFromDefinitionFile) {
            format.copyPrefixesFrom(fOWLOntologyManager.getOntologyFormat(fDefinitionOntology).asPrefixOWLOntologyFormat());
        }
                
        // Add <Import> statement
        OWLImportsDeclaration importDeclaraton = fOWLDataFactory.getOWLImportsDeclaration(OPEN_GROUP_INTERCHANGE_IRI_OWL);
        fInstanceOntology.getOWLOntologyManager().applyChange(new AddImport(fInstanceOntology, importDeclaraton));
        
        // Save it
        fOWLOntologyManager.saveOntology(fInstanceOntology, format, documentIRI);
    }
    

    /**
     * Write the model
     * @throws OWLOntologyCreationException 
     */
    private void writeModel() throws OWLOntologyCreationException {
        // Model name
        if(hasSomeText(fModel.getName())) {
        }
        
        // Model Documentation (Purpose)
        if(hasSomeText(fModel.getPurpose())) {
        }
        
        // Model Elements
        writeModelElements();
        
        // Relationships
        writeModelRelationships();
    }

    /**
     * Write the elements from the layers and extensions
     * @throws OWLOntologyCreationException 
     */
    private void writeModelElements() throws OWLOntologyCreationException {
        writeModelElementsFolder(fModel.getFolder(FolderType.BUSINESS));
        writeModelElementsFolder(fModel.getFolder(FolderType.APPLICATION));
        writeModelElementsFolder(fModel.getFolder(FolderType.TECHNOLOGY));
        writeModelElementsFolder(fModel.getFolder(FolderType.MOTIVATION));
        writeModelElementsFolder(fModel.getFolder(FolderType.IMPLEMENTATION_MIGRATION));
        writeModelElementsFolder(fModel.getFolder(FolderType.CONNECTORS));
    }

    /**
     * Write the elements from an Archi folder
     * @throws OWLOntologyCreationException 
     */
    private void writeModelElementsFolder(IFolder folder) throws OWLOntologyCreationException {
        List<EObject> list = new ArrayList<EObject>();
        getElements(folder, list);
        for(EObject eObject : list) {
            if(eObject instanceof IArchimateElement) {
                writeModelElement((IArchimateElement)eObject);
             }
        }
    }

    /**
     * Write an element
     * @throws OWLOntologyCreationException 
     */
    private void writeModelElement(IArchimateElement element) throws OWLOntologyCreationException {
        IRI classIRI = OWLMapper.getElementIRI(element.eClass());
        
        if(classIRI == null) {
            return;
        }
        
        // Use the element's class name and ID as the IRI
        String id = element.eClass().getName() + "-" + element.getId();  //$NON-NLS-1$
        IRI namedIRI = IRI.create(id);
        
        // Create the Named Individual.
        OWLNamedIndividual individual = fOWLDataFactory.getOWLNamedIndividual(namedIRI);
        
        // Add to our mapping
        fElementMapping.put(element, individual);

        // Add the Declaration of the Named Individual
        // TODO - do we actually need to do this?
        OWLDeclarationAxiom declarationAxiom = fOWLDataFactory.getOWLDeclarationAxiom(individual);
        fOWLOntologyManager.addAxiom(fInstanceOntology, declarationAxiom);

        // Get the reference to the class
        OWLClass owlclass = fOWLDataFactory.getOWLClass(classIRI);
        
        // Now create a ClassAssertion to specify that the individual is an instance of owlclass
        OWLClassAssertionAxiom classAssertion = fOWLDataFactory.getOWLClassAssertionAxiom(owlclass, individual);
        
        // Add the Class Assertion
        fOWLOntologyManager.addAxiom(fInstanceOntology, classAssertion);
        
        // Name
        writeModelElementName(element, individual);

        // Documentation
        writeModelElementDocumentation(element, individual);
        
        // Properties
        writeModelElementProperties(element, individual);
    }
    
    /**
     * Write the Name for an Element
     * @param element
     * @param individual
     */
    void writeModelElementName(IArchimateElement element, OWLNamedIndividual individual) {
        if(hasSomeText(element.getName())) {
            OWLAnnotation nameAnnotation = fOWLDataFactory.getOWLAnnotation(fOWLDataFactory.getRDFSLabel(),
                    fOWLDataFactory.getOWLLiteral(element.getName(), "en"));
            
            OWLAxiom axiom = fOWLDataFactory.getOWLAnnotationAssertionAxiom(individual.getIRI(), nameAnnotation);
            
            fOWLOntologyManager.addAxiom(fInstanceOntology, axiom);
        }
    }
    
    /**
     * Write the Documentation for an Element
     * @param element
     * @param individual
     */
    void writeModelElementDocumentation(IArchimateElement element, OWLNamedIndividual individual) {
        if(hasSomeText(element.getDocumentation())) {
            OWLAnnotation commentAnnotation = fOWLDataFactory.getOWLAnnotation(fOWLDataFactory.getRDFSComment(),
                    fOWLDataFactory.getOWLLiteral(element.getDocumentation(), "en"));
            
            OWLAxiom axiom = fOWLDataFactory.getOWLAnnotationAssertionAxiom(individual.getIRI(), commentAnnotation);
            
            fOWLOntologyManager.addAxiom(fInstanceOntology, axiom);
        }
    }
    
    /**
     * Write the Properties for an Element
     * @param propertiesElement
     * @param individual
     */
    void writeModelElementProperties(IProperties propertiesElement, OWLNamedIndividual individual) {
        for(IProperty property : propertiesElement.getProperties()) {
            // Property Key translates to OWLDeclarationAxiom
            // TODO: This is not the way to do this. We need a proper IRI or some other means to do this.
            IRI iri = IRI.create(property.getKey());
            
            OWLDataProperty namedProperty = fOWLDataFactory.getOWLDataProperty(iri);
            OWLDataProperty dataProperty = fOWLDataFactory.getOWLDataProperty(iri);
            namedProperty.getDataPropertiesInSignature().add(dataProperty);
            
            // Add the Declaration of the Named Individual
            OWLDeclarationAxiom namedPropertyDeclarationAxiom = fOWLDataFactory.getOWLDeclarationAxiom(namedProperty);
            fOWLOntologyManager.addAxiom(fInstanceOntology, namedPropertyDeclarationAxiom);
            
            // Property Value translates to DataPropertyAssertion
            OWLDataPropertyExpression propertyExpression = fOWLDataFactory.getOWLDataProperty(iri);
            // TODO use data types such as:
            //OWLDataPropertyAssertionAxiom propertyAssertionAxiom = fOWLDataFactory.getOWLDataPropertyAssertionAxiom(propertyExpression, individual, 23);
            // But we only support strings!
            OWLDataPropertyAssertionAxiom propertyAssertionAxiom = fOWLDataFactory.getOWLDataPropertyAssertionAxiom(propertyExpression, individual, property.getValue());
            fOWLOntologyManager.addAxiom(fInstanceOntology, propertyAssertionAxiom);
        }
    }

    /**
     * Write the relationships
     */
    private void writeModelRelationships() {
        writeModelRelationshipsFolder(fModel.getFolder(FolderType.RELATIONS));
    }
    
    /**
     * Write the relationships from an Archi folder
     */
    private void writeModelRelationshipsFolder(IFolder folder) {
        List<EObject> list = new ArrayList<EObject>();
        getElements(folder, list);
        for(EObject eObject : list) {
            if(eObject instanceof IRelationship) {
                writeModelRelationship((IRelationship)eObject);
             }
        }
    }

    /**
     * Write a relationship
     */
    private void writeModelRelationship(IRelationship relationship) {
        IRI classIRI = OWLMapper.getRelationIRI(relationship.eClass());
        
        if(classIRI == null) {
            return;
        }
        
        // Identifier
        //String id = relationship.eClass().getName() + "-" + relationship.getId();
        
        IArchimateElement sourceElement = relationship.getSource();
        IArchimateElement targetElement = relationship.getTarget();
        
        OWLNamedIndividual individual = fElementMapping.get(sourceElement);
        OWLNamedIndividual object = fElementMapping.get(targetElement);
        
        OWLObjectProperty propertyExpression = fOWLDataFactory.getOWLObjectProperty(classIRI);
        OWLObjectPropertyAssertionAxiom axiom = fOWLDataFactory.getOWLObjectPropertyAssertionAxiom(propertyExpression, individual, object);
        fOWLOntologyManager.addAxiom(fInstanceOntology, axiom);

        // Name
        if(hasSomeText(relationship.getName())) {
            // TODO
        }
        
        // Documentation
        if(hasSomeText(relationship.getDocumentation())) {
            // TODO
        } 
    }

    /**
     * Return true if string has at least some text
     */
    private boolean hasSomeText(String string) {
        return string != null && !string.isEmpty();
    }
    
    /**
     * Return all elements in a folder and its sub-folders
     */
    private void getElements(IFolder folder, List<EObject> list) {
        for(EObject object : folder.getElements()) {
            list.add(object);
        }
        
        for(IFolder f : folder.getFolders()) {
            getElements(f, list);
        }
    }

}
