/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import org.semanticweb.owlapi.model.IRI;



/**
 * Exchange Globals
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public interface IOWLExchangeGlobals {
    
    String FILE_EXTENSION = ".owl";
    String FILE_EXTENSION_WILDCARD = "*.owl";

    String OPEN_GROUP_INTERCHANGE_IRI_STRING = "http://www.opengroup.org/archimate/interchange/";
    IRI OPEN_GROUP_INTERCHANGE_IRI = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_STRING);
    
    String OPEN_GROUP_INTERCHANGE_IRI_OWL_STRING = "http://www.opengroup.org/archimate/interchange/ArchiMate_V0.06.owl";
    IRI OPEN_GROUP_INTERCHANGE_IRI_OWL = IRI.create(OPEN_GROUP_INTERCHANGE_IRI_OWL_STRING);
    
    String ARCHI_INTERCHANGE_IRI_STRING = "http://www.archimatetool.com/interchange/";
    IRI ARCHI_INTERCHANGE_IRI = IRI.create(ARCHI_INTERCHANGE_IRI_STRING);
}
