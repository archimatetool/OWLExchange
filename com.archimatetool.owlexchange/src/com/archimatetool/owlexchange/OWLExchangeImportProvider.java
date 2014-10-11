/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.editor.model.IModelImporter;
import com.archimatetool.model.IArchimateModel;




/**
 * Import OWL Exchange File to ECore Archi Model
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLExchangeImportProvider implements IModelImporter, IOWLExchangeGlobals {
    
    @Override
    public void doImport() throws IOException {
        File file = askOpenFile();
        if(file == null) {
            return;
        }

        // Create a model
        OWLModelImporter owlModelImporter = new OWLModelImporter();
        IArchimateModel model = null;
        
        try {
            model = owlModelImporter.createArchiMateModel(file, OWLExchangePlugin.INSTANCE.getOWLDefinitionFile());
        }
        catch(OWLOntologyCreationException ex) {
            ex.printStackTrace();
            
            MessageDialog.openError(Display.getCurrent().getActiveShell(),
                    "Error importing model",
                    "Cannot open '" + file + "'"
                    + "\n" + ex.getMessage());
        }
        
        // And open the Model in the Editor
        if(model != null) {
            IEditorModelManager.INSTANCE.openModel(model);
        }
    }
    
    private File askOpenFile() {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
        dialog.setFilterExtensions(new String[] { FILE_EXTENSION_WILDCARD, "*.*" } );
        String path = dialog.open();
        return path != null ? new File(path) : null;
    }
    
}
