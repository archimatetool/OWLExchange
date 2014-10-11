/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.owlexchange;


import junit.framework.TestSuite;

@SuppressWarnings("nls")
public class AllTests {

    public static junit.framework.Test suite() {
		TestSuite suite = new TestSuite("com.archimatetool.owlexchange");

		suite.addTest(OWLModelExporterTests.suite());
        suite.addTest(OWLModelImporterTests.suite());
		
        return suite;
	}

}