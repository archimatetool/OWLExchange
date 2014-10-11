/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * Activitor
 * 
 * @author Phillip Beauvoir
 */
@SuppressWarnings("nls")
public class OWLExchangePlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.archimatetool.owlexchange";

    public static final File ARCHIMATE_DEFINITION_FILE = new File("owl", "ArchiMate_V0.06.owl");

    /**
     * The shared instance
     */
    public static OWLExchangePlugin INSTANCE;

    public OWLExchangePlugin() {
        INSTANCE = this;
    }


    /**
     * @return The OWL ArchiMate definition file
     */
    public File getOWLDefinitionFile() {
        return OWLExchangePlugin.INSTANCE.getAssetFile(ARCHIMATE_DEFINITION_FILE);
    }

    /**
     * @return An asset file relative to this Bundle
     */
    public File getAssetFile(File file) {
        URL url = FileLocator.find(Platform.getBundle(PLUGIN_ID), new Path(file.getPath()), null);
        
        try {
            url = FileLocator.resolve(url);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return new File(url.getPath()); 
    }

}
