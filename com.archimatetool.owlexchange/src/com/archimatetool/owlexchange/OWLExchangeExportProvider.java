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
import org.semanticweb.owlapi.model.OWLException;

import com.archimatetool.editor.model.IModelExporter;
import com.archimatetool.model.IArchimateModel;



/**
 * Export Archi Model to OWL Exchange File
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLExchangeExportProvider implements IModelExporter, IOWLExchangeGlobals {
    
    @Override
    public void export(IArchimateModel model) throws IOException {
        File file = askSaveFile();
        if(file == null) {
            return;
        }
        
        OWLModelExporter owlModelExporter = new OWLModelExporter();
        
        try {
            owlModelExporter.exportModel(model, file, OWLExchangePlugin.INSTANCE.getOWLDefinitionFile());
        }
        catch(OWLException ex) {
            ex.printStackTrace();
            
            MessageDialog.openError(Display.getCurrent().getActiveShell(),
                    "Error exporting model",
                    "Cannot export to '" +  file + "'"
                    + "\n" + ex.getMessage());

        }
    }
    
    /**
     * Ask user for file name to save to
     */
    private File askSaveFile() {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setText("Export Model");
        dialog.setFilterExtensions(new String[] { FILE_EXTENSION_WILDCARD, "*.*" } );
        String path = dialog.open();
        if(path == null) {
            return null;
        }
        
        // Only Windows adds the extension by default
        if(dialog.getFilterIndex() == 0 && !path.endsWith(FILE_EXTENSION)) {
            path += FILE_EXTENSION;
        }
        
        File file = new File(path);
        
        // Make sure the file does not already exist
        if(file.exists()) {
            boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
                    "Export Model",
                    "'" + file + "' already exists. Are you sure you want to overwrite it?");
            if(!result) {
                return null;
            }
        }
        
        return file;
    }
}
