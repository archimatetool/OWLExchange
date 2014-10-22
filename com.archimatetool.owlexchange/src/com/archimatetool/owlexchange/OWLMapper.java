/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.semanticweb.owlapi.model.IRI;

import com.archimatetool.model.IArchimatePackage;



/**
 * Mapping of IRIs to eCore Model objects
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLMapper implements IOWLExchangeGlobals {
    
    // Business Elements
    public static IRI IRI_BUSINESS_ACTOR = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessActor");
    public static IRI IRI_BUSINESS_ROLE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessRole");
    public static IRI IRI_BUSINESS_COLLABORATION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessCollaboration");
    public static IRI IRI_BUSINESS_INTERFACE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessInterface");
    public static IRI IRI_BUSINESS_FUNCTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessFunction");
    public static IRI IRI_BUSINESS_PROCESS = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessProcess");
    public static IRI IRI_BUSINESS_EVENT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessEvent");
    public static IRI IRI_BUSINESS_INTERACTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessInteraction");
    public static IRI IRI_BUSINESS_PRODUCT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Product");
    public static IRI IRI_BUSINESS_CONTRACT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Contract");
    public static IRI IRI_BUSINESS_SERVICE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessService");
    public static IRI IRI_BUSINESS_VALUE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Value");
    public static IRI IRI_BUSINESS_MEANING = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Meaning");
    public static IRI IRI_BUSINESS_REPRESENTATION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Representation");
    public static IRI IRI_BUSINESS_OBJECT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "BusinessObject");
    public static IRI IRI_BUSINESS_LOCATION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Location");
    
    // Application Elements
    public static IRI IRI_APPLICATION_COMPONENT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationComponent");
    public static IRI IRI_APPLICATION_COLLABORATION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationCollaboration");
    public static IRI IRI_APPLICATION_INTERFACE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationInterface");
    public static IRI IRI_APPLICATION_SERVICE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationService");
    public static IRI IRI_APPLICATION_FUNCTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationFunction");
    public static IRI IRI_APPLICATION_INTERACTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "ApplicationInteraction");
    public static IRI IRI_APPLICATION_DATA_OBJECT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "DataObject");
    
    // Technology Elements
    public static IRI IRI_ARTIFACT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Artifact");
    public static IRI IRI_COMMUNICATION_PATH = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "CommunicationPat");
    public static IRI IRI_NETWORK = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Network");
    public static IRI IRI_INFRASTRUCTURE_INTERFACE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "InfrastructureInterface");
    public static IRI IRI_INFRASTRUCTURE_FUNCTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "InfrastructureFunction");
    public static IRI IRI_INFRASTRUCTURE_SERVICE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "InfrastructureService");
    public static IRI IRI_NODE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Node");
    public static IRI IRI_SYSTEM_SOFTWARE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "SystemSoftware");
    public static IRI IRI_DEVICE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Device");
    
    // Extensions
    public static IRI IRI_STAKEHOLDER = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Stakeholder");
    public static IRI IRI_DRIVER = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Driver");
    public static IRI IRI_ASSESSMENT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Assessment");
    public static IRI IRI_GOAL = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Goal");
    public static IRI IRI_PRINCIPLE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Principle");
    public static IRI IRI_REQUIREMENT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Requirement");
    public static IRI IRI_CONSTRAINT = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Constraint");
    public static IRI IRI_WORKPACKAGE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "WorkPackage");
    public static IRI IRI_DELIVERABLE = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Deliverable");
    public static IRI IRI_PLATEAU = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Plateau");
    public static IRI IRI_GAP = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Gap");

    // Relations
    public static IRI IRI_ASSIGNED_TO = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "AssignedTo");
    public static IRI IRI_ASSIGNED_FROM = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "AssignedFrom");
    public static IRI IRI_ACCESSED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "AccessedBy");
    public static IRI IRI_ACCESSES = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Accesses");
    public static IRI IRI_ASSOCIATED_WITH = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "AssociatedWith");
    public static IRI IRI_FLOWS_FROM = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "FlowsFrom");
    public static IRI IRI_FLOWS_TO = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "FlowsTo");
    public static IRI IRI_INFLUENCED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "InfluencedBy");
    public static IRI IRI_INFLUENCES = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Influences");
    public static IRI IRI_IS_AGGREGATION_OF = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsAggregationOf");
    public static IRI IRI_IS_COMPOSITION_OF = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsCompositionOf");
    public static IRI IRI_IS_GROUPING_OF = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsGroupingOf");
    public static IRI IRI_IS_PART_OF_AGGREGATION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsPartOfAggregation");
    public static IRI IRI_IS_PART_OF_COMPOSITION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsPartOfComposition");
    public static IRI IRI_IS_PART_OF_GROUPING = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsPartOfGrouping");
    public static IRI IRI_IS_SPECIALIZATION_OF = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsSpecializationOf");
    public static IRI IRI_IS_SPECIALIZED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "IsSpecializedBy");
    public static IRI IRI_REALISED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "RealizedBy");
    public static IRI IRI_REALIZES = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Realizes");
    public static IRI IRI_TRIGGERED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "TriggeredBy");
    public static IRI IRI_TRIGGERS = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Triggers");
    public static IRI IRI_USED_BY = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "UsedBy");
    public static IRI IRI_USES = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Uses");
    
    // Junction
    public static IRI IRI_JUNCTION = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "Junction");

    // Class mapping of IRI strings to Element Eobjects
    private static Map<IRI, EClass> IRI_ElementMapping = new Hashtable<IRI, EClass>();
    
    // Class mapping of IRI strings to Element Eobjects
    private static Map<IRI, EClass> IRI_RelationMapping = new Hashtable<IRI, EClass>();

    static {
        // Business Elements
        IRI_ElementMapping.put(IRI_BUSINESS_ACTOR, IArchimatePackage.eINSTANCE.getBusinessActor());
        IRI_ElementMapping.put(IRI_BUSINESS_ROLE, IArchimatePackage.eINSTANCE.getBusinessRole());
        IRI_ElementMapping.put(IRI_BUSINESS_COLLABORATION, IArchimatePackage.eINSTANCE.getBusinessCollaboration());
        IRI_ElementMapping.put(IRI_BUSINESS_INTERFACE, IArchimatePackage.eINSTANCE.getBusinessInterface());
        IRI_ElementMapping.put(IRI_BUSINESS_FUNCTION, IArchimatePackage.eINSTANCE.getBusinessFunction());
        IRI_ElementMapping.put(IRI_BUSINESS_PROCESS, IArchimatePackage.eINSTANCE.getBusinessProcess());
        IRI_ElementMapping.put(IRI_BUSINESS_EVENT, IArchimatePackage.eINSTANCE.getBusinessEvent());
        IRI_ElementMapping.put(IRI_BUSINESS_INTERACTION, IArchimatePackage.eINSTANCE.getBusinessInteraction());
        IRI_ElementMapping.put(IRI_BUSINESS_PRODUCT, IArchimatePackage.eINSTANCE.getProduct());
        IRI_ElementMapping.put(IRI_BUSINESS_CONTRACT, IArchimatePackage.eINSTANCE.getContract());
        IRI_ElementMapping.put(IRI_BUSINESS_SERVICE, IArchimatePackage.eINSTANCE.getBusinessService());
        IRI_ElementMapping.put(IRI_BUSINESS_VALUE, IArchimatePackage.eINSTANCE.getValue());
        IRI_ElementMapping.put(IRI_BUSINESS_MEANING, IArchimatePackage.eINSTANCE.getMeaning());
        IRI_ElementMapping.put(IRI_BUSINESS_REPRESENTATION, IArchimatePackage.eINSTANCE.getRepresentation());
        IRI_ElementMapping.put(IRI_BUSINESS_OBJECT, IArchimatePackage.eINSTANCE.getBusinessObject());
        IRI_ElementMapping.put(IRI_BUSINESS_LOCATION, IArchimatePackage.eINSTANCE.getLocation());
        
        // Application Elements
        IRI_ElementMapping.put(IRI_APPLICATION_COMPONENT, IArchimatePackage.eINSTANCE.getApplicationComponent());
        IRI_ElementMapping.put(IRI_APPLICATION_COLLABORATION, IArchimatePackage.eINSTANCE.getApplicationCollaboration());
        IRI_ElementMapping.put(IRI_APPLICATION_INTERFACE, IArchimatePackage.eINSTANCE.getApplicationInterface());
        IRI_ElementMapping.put(IRI_APPLICATION_SERVICE, IArchimatePackage.eINSTANCE.getApplicationService());
        IRI_ElementMapping.put(IRI_APPLICATION_FUNCTION, IArchimatePackage.eINSTANCE.getApplicationFunction());
        IRI_ElementMapping.put(IRI_APPLICATION_INTERACTION, IArchimatePackage.eINSTANCE.getApplicationInteraction());
        IRI_ElementMapping.put(IRI_APPLICATION_DATA_OBJECT, IArchimatePackage.eINSTANCE.getDataObject());
        
        // Technology Elements
        IRI_ElementMapping.put(IRI_ARTIFACT, IArchimatePackage.eINSTANCE.getArtifact());
        IRI_ElementMapping.put(IRI_COMMUNICATION_PATH, IArchimatePackage.eINSTANCE.getCommunicationPath());
        IRI_ElementMapping.put(IRI_NETWORK, IArchimatePackage.eINSTANCE.getNetwork());
        IRI_ElementMapping.put(IRI_INFRASTRUCTURE_INTERFACE, IArchimatePackage.eINSTANCE.getInfrastructureInterface());
        IRI_ElementMapping.put(IRI_INFRASTRUCTURE_FUNCTION, IArchimatePackage.eINSTANCE.getInfrastructureFunction());
        IRI_ElementMapping.put(IRI_INFRASTRUCTURE_SERVICE, IArchimatePackage.eINSTANCE.getInfrastructureService());
        IRI_ElementMapping.put(IRI_NODE, IArchimatePackage.eINSTANCE.getNode());
        IRI_ElementMapping.put(IRI_SYSTEM_SOFTWARE, IArchimatePackage.eINSTANCE.getSystemSoftware());
        IRI_ElementMapping.put(IRI_DEVICE, IArchimatePackage.eINSTANCE.getDevice());

        // Extensions
        IRI_ElementMapping.put(IRI_STAKEHOLDER, IArchimatePackage.eINSTANCE.getStakeholder());
        IRI_ElementMapping.put(IRI_DRIVER, IArchimatePackage.eINSTANCE.getDriver());
        IRI_ElementMapping.put(IRI_ASSESSMENT, IArchimatePackage.eINSTANCE.getAssessment());
        IRI_ElementMapping.put(IRI_GOAL, IArchimatePackage.eINSTANCE.getGoal());
        IRI_ElementMapping.put(IRI_PRINCIPLE, IArchimatePackage.eINSTANCE.getPrinciple());
        IRI_ElementMapping.put(IRI_REQUIREMENT, IArchimatePackage.eINSTANCE.getRequirement());
        IRI_ElementMapping.put(IRI_CONSTRAINT, IArchimatePackage.eINSTANCE.getConstraint());
        IRI_ElementMapping.put(IRI_WORKPACKAGE, IArchimatePackage.eINSTANCE.getWorkPackage());
        IRI_ElementMapping.put(IRI_DELIVERABLE, IArchimatePackage.eINSTANCE.getDeliverable());
        IRI_ElementMapping.put(IRI_PLATEAU, IArchimatePackage.eINSTANCE.getPlateau());
        IRI_ElementMapping.put(IRI_GAP, IArchimatePackage.eINSTANCE.getGap());
        
        // Junction
        IRI_ElementMapping.put(IRI_JUNCTION, IArchimatePackage.eINSTANCE.getJunction());

        // Relations
        IRI_RelationMapping.put(IRI_ASSIGNED_TO, IArchimatePackage.eINSTANCE.getAssignmentRelationship());
        IRI_RelationMapping.put(IRI_ACCESSES, IArchimatePackage.eINSTANCE.getAccessRelationship());
        IRI_RelationMapping.put(IRI_ASSOCIATED_WITH, IArchimatePackage.eINSTANCE.getAssociationRelationship());
        IRI_RelationMapping.put(IRI_IS_COMPOSITION_OF, IArchimatePackage.eINSTANCE.getCompositionRelationship());
        IRI_RelationMapping.put(IRI_IS_PART_OF_AGGREGATION, IArchimatePackage.eINSTANCE.getAggregationRelationship());
        IRI_RelationMapping.put(IRI_USES, IArchimatePackage.eINSTANCE.getUsedByRelationship());
        IRI_RelationMapping.put(IRI_TRIGGERS, IArchimatePackage.eINSTANCE.getTriggeringRelationship());
        IRI_RelationMapping.put(IRI_FLOWS_TO, IArchimatePackage.eINSTANCE.getFlowRelationship());
        IRI_RelationMapping.put(IRI_REALIZES, IArchimatePackage.eINSTANCE.getRealisationRelationship());
        IRI_RelationMapping.put(IRI_IS_SPECIALIZATION_OF, IArchimatePackage.eINSTANCE.getSpecialisationRelationship());
        IRI_RelationMapping.put(IRI_INFLUENCES, IArchimatePackage.eINSTANCE.getInfluenceRelationship());        
    }
    
    // Relations mapping
    private static Map<IRI, EClass> IRI_RelationsMapping = new Hashtable<IRI, EClass>();
    
    public static IRI IRI_RELATION_ASSIGNED_TO = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING + "AssignedTo");
    
    static {
        IRI_RelationsMapping.put(IRI_RELATION_ASSIGNED_TO, IArchimatePackage.eINSTANCE.getAssignmentRelationship());
    }
    
    public static EClass getElementEClass(IRI iri) {
        return IRI_ElementMapping.get(iri);
    }
    
    public static IRI getElementIRI(EClass eClass) {
        return findIRI(eClass, IRI_ElementMapping);
    }
    
    public static EClass getRelationEClass(IRI iri) {
        return IRI_RelationMapping.get(iri);
    }
    
    public static IRI getRelationIRI(EClass eClass) {
        return findIRI(eClass, IRI_RelationMapping);
    }
    
    private static IRI findIRI(EClass eClass, Map<IRI, EClass> table) {
        for(Entry<IRI, EClass> element : table.entrySet()) {
            if(element.getValue().equals(eClass)) {
                return element.getKey();
            }
        }
        
        return null;
    }
}
