/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import com.archimatetool.editor.utils.StringUtils;
import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IDocumentable;
import com.archimatetool.model.IFolder;
import com.archimatetool.model.INameable;
import com.archimatetool.model.IProperty;
import com.archimatetool.model.IRelationship;




/**
 * OWL Model Importer
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLModelImporter implements IOWLExchangeGlobals {
    
    // Individual to element mapping
    private Map<OWLNamedIndividual, IArchimateElement> fIndividualMapping = new Hashtable<OWLNamedIndividual, IArchimateElement>();
    
    private IArchimateModel fModel;
    
    private OWLOntology fInstanceOntology;
    
    public IArchimateModel createArchiMateModel(File instanceFile, File owlDefinitionFile) throws OWLOntologyCreationException {
        if(fModel != null) {
            return fModel;
        }
        
        // Create a new Archimate Model and set its defaults
        fModel = IArchimateFactory.eINSTANCE.createArchimateModel();
        fModel.setDefaults();
        
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        
        // We can either map the definition OWL IRI to a local file...
        OWLOntologyIRIMapper mapper = new SimpleIRIMapper(OPEN_GROUP_INTERCHANGE_IRI_OWL, IRI.create(owlDefinitionFile));
        manager.addIRIMapper(mapper);
        
        // Or just directly load the definition file...
        //manager.loadOntologyFromOntologyDocument(owlDefinitionFile);

        // Load instance file into an Ontology
        fInstanceOntology = manager.loadOntologyFromOntologyDocument(instanceFile);

        // Read it
        readOntology();

        return fModel;
    }
    
    /**
     * Read the OWLOntology
     * 
     * We're looking for "Individuals" that we know we can handle
     * 
     * We need to create a mapping between the Entities' IRIs and ArchiMate elements
     */
    private void readOntology() {
        // TODO get model name
        fModel.setName("OWL Model");
        
        // Iterate for all Named Individuals and create Elements
        for(OWLNamedIndividual individual : fInstanceOntology.getIndividualsInSignature()) {
            IArchimateElement element = createElementFromNamedIndividual(individual);
            // Add to mapping
            if(element != null) {
                fIndividualMapping.put(individual, element);
            }
        }
        
        // Now iterate thru our collection of Named Individuals to add relationships
        for(Entry<OWLNamedIndividual, IArchimateElement> entry : fIndividualMapping.entrySet()) {
            createRelationshipsForSubjectIndividual(entry.getKey());
        }
    }
    
    /**
     * Create an ArchiMate element from a NamedIndividual type
     * 
     * @param individual The OWLNamedIndividual
     * @return The IArchimateElement or null
     */
    IArchimateElement createElementFromNamedIndividual(OWLNamedIndividual individual) {
        EClass eClass = null;
        
        /*
         * Iterate thru all of the individual's class types and find the one we can handle
         */
        for(OWLClassExpression classExpression : individual.getTypes(fInstanceOntology)) {
            if(classExpression instanceof OWLNamedObject) {
                // Find EClass from the IRI in class mapping lookup table
                IRI classIRI = ((OWLNamedObject)classExpression).getIRI();
                eClass = OWLMapper.getElementEClass(classIRI);
                if(eClass != null) {
                    break; // Only support one Class per Individual
                }
            }
        } 
        
        // Didn't find a Class
        if(eClass == null) {
            return null;
        }

        // Create and add new Element
        IArchimateElement element = (IArchimateElement)IArchimateFactory.eINSTANCE.create(eClass);
        
        // Can we re-use the NamedIndividual IRI as an ID?
        String id = createIDFromIRI(individual.getIRI());
        if(id != null) {
            element.setId(id);
        }
        
        // Add it to folder
        IFolder folder = fModel.getDefaultFolderForElement(element);
        if(folder != null) {
            folder.getElements().add(element);
        }
        
        // Iterate thru the annotations for object name and documentation
        for(OWLAnnotation annotation : individual.getAnnotations(fInstanceOntology)) {
            addAnnotationToElement(annotation, element);
        }
        
        // Iterate thru the data properties for object properties
        for(Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : individual.getDataPropertyValues(fInstanceOntology).entrySet()) {
            addPropertyToElement(entry, element);
        }
        
        return element;
    }
    
    /**
     * Add an Annotation to an ArchiMate element
     * @param annotation The OWLAnnotation
     * @param element The Element to add to
     */
    void addAnnotationToElement(OWLAnnotation annotation, IArchimateElement element) {
        // If the annotation's value is a literal...
        if(annotation.getValue() instanceof OWLLiteral) {
            OWLLiteral literal = (OWLLiteral)annotation.getValue();
            OWLAnnotationProperty annotationProperty = annotation.getProperty();
            
            // Label is name
            if(annotationProperty.isLabel()) {
                ((INameable)element).setName(literal.getLiteral());
            }
            
            // Comment is documentation
            else if(annotationProperty.isComment()) {
                ((IDocumentable)element).setDocumentation(literal.getLiteral());
            }
        }
    }
    
    /**
     * TODO: This is perhaps not the best way to store properties!
     * Add a Property to an ArchiMate element
     * @param entry
     * @param element
     */
    void addPropertyToElement(Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry, IArchimateElement element) {
        // Can only handle OWLDataProperty
        if(entry.getKey() instanceof OWLDataProperty) {
            OWLDataProperty expression = (OWLDataProperty)entry.getKey();
            
            String key = null, value = null;
            
            // Get the IRI's fragment for the property key
            IRI iri = expression.getIRI();
            if(iri != null) {
                key = iri.getFragment();
            }
            
            // Now we are only interested in one literal value, so take the last one if there is more than one
            Set<OWLLiteral> literals = entry.getValue();
            for(OWLLiteral owlLiteral : literals) {
                value = owlLiteral.getLiteral();
                // TODO store data type
                //OWLDatatype dataType = owlLiteral.getDatatype();
            }

            if(key != null) {
                IProperty property = IArchimateFactory.eINSTANCE.createProperty();
                property.setKey(key);
                property.setValue(value);
                element.getProperties().add(property);
            }
        }
    }
    
    /**
     * Iterate through all the ObjectPropertyAssertion axioms declared to look for relationships
     * for a Named Individual
     * 
     * @param individual The Named Individual
     */
    private void createRelationshipsForSubjectIndividual(OWLNamedIndividual individual) {
        for(OWLObjectPropertyAssertionAxiom axiom : fInstanceOntology.getObjectPropertyAssertionAxioms(individual)) {
            createRelationshipFromPropertyAssertionAxiom(axiom);
        }
    }

    /**
     * Create a single relationship from a PropertyAssertionAxiom
     * The axiom represents a construct like:
     * 
     * <ObjectPropertyAssertion>
     *     <ObjectProperty IRI="http://www.opengroup.org/archimate/interchange/AssignedTo"/>
     *     <NamedIndividual IRI="BusinessRole-030632a4"/>
     *     <NamedIndividual IRI="BusinessProcess-f9883fec"/>
     * </ObjectPropertyAssertion>
     *
     * This consists of
     * The IRI of the relationship
     * The subject (source) NamedIndividual
     * The object (target) NamedIndividual
     * 
     * @param axiom The axiom
     * @return The Relationship or null if not successful
     */
    IRelationship createRelationshipFromPropertyAssertionAxiom(OWLObjectPropertyAssertionAxiom axiom) {
        IRelationship relationship = null;
        
        OWLObjectPropertyExpression propertyExpression = axiom.getProperty();
        OWLObjectProperty namedProperty = propertyExpression.getNamedProperty();
        IRI iri = namedProperty.getIRI();

        OWLIndividual subject = axiom.getSubject();
        OWLIndividual object = axiom.getObject();
        EClass eClass = OWLMapper.getRelationEClass(iri);
        
        if(subject != null && object != null && eClass != null) {
            IArchimateElement sourceElement = fIndividualMapping.get(subject);
            IArchimateElement targetElement = fIndividualMapping.get(object);

            if(sourceElement != null && targetElement != null) {
                relationship = (IRelationship)IArchimateFactory.eINSTANCE.create(eClass);
                relationship.setSource(sourceElement);
                relationship.setTarget(targetElement);
                IFolder folder = fModel.getDefaultFolderForElement(relationship);
                folder.getElements().add(relationship);
                
                // TODO Get Relationship's Name, Documentation
                
                // TODO Get Relationship's Properties
            }
        }
        
        return relationship;
    }
    
    /**
     * Create an ID from an IRI
     * The format should be BusinessProcess-1224 for the fragment part
     * @param iri
     * @return ID string or null if not possible
     */
    String createIDFromIRI(IRI iri) {
        if(iri == null) {
            return null;
        }
        
        // Should have a fragment part
        String id = iri.getFragment();
        
        // No, try the namespace
        if(!StringUtils.isSetAfterTrim(id)) {
            id = iri.getNamespace();
        }
        
        if(StringUtils.isSetAfterTrim(id)) {
            // Look for the last "-" sep
            int index = id.lastIndexOf("-");
            
            // As a kludge for a badly formed IRI, look for the last "/"
            if(index == -1) {
                index = id.lastIndexOf("/");
            }
            
            if(index != -1 && index < id.length()) {
                id = id.substring(index + 1);
                return StringUtils.isSet(id) ? id : null;
            }
            
            // Else return part
            return id;
        }
        
        return null;
    }
    
}
