/* $Revision: 8050 $ $Author: egonw $ $Date: 2007-03-08 13:03:42 +0100 (Thu, 08 Mar 2007) $
 * 
 * Copyright (C) 2005-2007  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 */
package org.openscience.cdk.test;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import org.junit.Assert;
import org.openscience.cdk.tools.LoggingTool;

/**
 * Super class for <b>all</b> CDK TestCase implementations that ensures that
 * the LoggingTool is configured. This is the JUnit4 version of CDKTestCase.
 *
 * @cdk.module test
 * 
 * @see        CDKTestCase
 */
public class NewCDKTestCase {

    static {
        LoggingTool.configureLog4j();
    }

    /**
     * Determines if slow JUnit tests are to be run. You can set this
     * from the command line when running Ant: 
     * <pre>
     *   ant -f build.xml -DrunSlowTests=false test-all
     * </pre>
     * 
     * @return
     */
    public boolean runSlowTests() {
    	if (System.getProperty("runSlowTests", "true").equals("false")) 
    		return false;
    	
    	// else
    	return true;
    }

    /**
     * Determines if JUnit tests for known and unfixed bugs are to be run.
     * This is to aid the 'Open Source JVM Test Suite', so that all bugs that
     * show up in the report are caused by the JVM or the Java library is
     * uses (mostly a Classpath version).
     *  
     * <p>You can set this from the command line when running Ant: 
     * <pre>
     *   ant -f build.xml -DrunKnownBugs=false test-all
     * </pre>
     * 
     * <p><b>This method may only be used in JUnit classes, it the bug is reported
     * on SourceForge, and both the test <i>and</i> the affected Class are marked
     * with a JavaDoc @cdk.bug taglet!</b>
     * 
     * @return a boolean indicating wether known bugs should be tested
     */
    public boolean runKnownBugs() {
    	if (System.getProperty("runKnownBugs", "true").equals("false")) 
    		return false;
    	
    	// else
    	return true;
    }

    /**
     * Compares two Point2d objects, and asserts that the XY coordinates
     * are identical within the given error.
     * 
     * @param p1    first Point2d
     * @param p2    second Point2d
     * @param error maximal allowed error
     */
    public void assertEquals(Point2d p1, Point2d p2, double error) {
    	Assert.assertEquals(p1.x, p2.x, error);
    	Assert.assertEquals(p1.y, p2.y, error);
    }
        
    /**
     * Compares two Point3d objects, and asserts that the XY coordinates
     * are identical within the given error.
     * 
     * @param p1    first Point3d
     * @param p2    second Point3d
     * @param error maximal allowed error
     */
    public void assertEquals(Point3d p1, Point3d p2, double error) {
    	Assert.assertEquals(p1.x, p2.x, error);
    	Assert.assertEquals(p1.y, p2.y, error);
    	Assert.assertEquals(p1.z, p2.z, error);
    }
        
}