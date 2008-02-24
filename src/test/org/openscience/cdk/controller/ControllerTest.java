/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
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

package org.openscience.cdk.test.controller;

import java.io.FileInputStream;

import org.openscience.cdk.Atom;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemModel;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.applications.swing.MoleculeViewer2D;
import org.openscience.cdk.controller.Controller2DModel;
import org.openscience.cdk.controller.PopupController2D;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.io.IChemObjectReader.Mode;
import org.openscience.cdk.renderer.Renderer2DModel;

/**
 * @cdk.module test-extra
 */
public class ControllerTest
{
	MDLReader mr;
	ChemFile chemFile;
	org.openscience.cdk.interfaces.IChemSequence chemSequence;
	org.openscience.cdk.interfaces.IChemModel chemModel;
	org.openscience.cdk.interfaces.IMoleculeSet setOfMolecules;
	org.openscience.cdk.interfaces.IMolecule molecule;
	PopupController2D inputAdapter;
	
	public ControllerTest() throws Exception
	{
//		molecule = buildFusedRings();
//		molecule = buildMolecule4x3();
//		molecule = buildMolecule2x3();
//		molecule = buildMolecule2x4();
//		molecule = buildSpiroRings();
		molecule = loadMolecule("data/mdl/reserpine.mol");
//		molecule = buildRing();
//		molecule = new Molecule();

//
//		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
//		sdg.setMolecule(molecule);
//		sdg.generateCoordinates(new Vector2d(0,1));
//		molecule = sdg.getMolecule();
//

		Renderer2DModel r2dm = new Renderer2DModel();
		MoleculeViewer2D mv = new MoleculeViewer2D(molecule, r2dm);
//		r2dm.setDrawNumbers(true);
		mv.display();
        ChemModel model = new ChemModel();
        MoleculeSet moleculeSet = new MoleculeSet();
        moleculeSet.addMolecule(molecule);
        model.setMoleculeSet(moleculeSet);
		Controller2DModel c2dm = new Controller2DModel();
		inputAdapter = new PopupController2D(model, r2dm, c2dm);
		c2dm.setDrawMode(Controller2DModel.DrawMode.DRAWBOND);
		mv.addMouseMotionListener(inputAdapter);
		mv.addMouseListener(inputAdapter);
		mv.addKeyListener(inputAdapter);
	
	}

	/* build a molecule from 4 condensed triangles */
	
	Molecule buildMolecule4x3()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		mol.addAtom(new Atom("C")); // 4
		mol.addAtom(new Atom("C")); // 5
		mol.addAtom(new Atom("C")); // 6		
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 3, IBond.Order.SINGLE); // 3
		mol.addBond(3, 4, IBond.Order.SINGLE); // 4
		mol.addBond(4, 5, IBond.Order.SINGLE); // 5
		mol.addBond(5, 6, IBond.Order.SINGLE); // 6
		mol.addBond(2, 0, IBond.Order.SINGLE); // 7
		mol.addBond(1, 3, IBond.Order.SINGLE); // 8
		mol.addBond(4, 2, IBond.Order.SINGLE); // 9
		mol.addBond(6, 4, IBond.Order.SINGLE); // 9		
		
		return mol;
	}
	
	Molecule buildMolecule2x3()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 0, IBond.Order.SINGLE); // 3
		mol.addBond(2, 3, IBond.Order.SINGLE); // 7
		mol.addBond(1, 3, IBond.Order.SINGLE); // 8
		return mol;
	}
	

	Molecule buildMolecule2x4()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 3, IBond.Order.SINGLE); // 3
		mol.addBond(3, 0, IBond.Order.SINGLE); // 7
		mol.addBond(1, 4, IBond.Order.SINGLE); // 8
		mol.addBond(4, 5, IBond.Order.SINGLE); // 7
		mol.addBond(5, 2, IBond.Order.SINGLE); // 8
		return mol;
	}

	
	Molecule buildFusedRings()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		mol.addAtom(new Atom("C")); // 4
		mol.addAtom(new Atom("C")); // 5
		mol.addAtom(new Atom("C")); // 6
		mol.addAtom(new Atom("C")); // 7
		mol.addAtom(new Atom("C")); // 8
		mol.addAtom(new Atom("C")); // 9
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 3, IBond.Order.SINGLE); // 3
		mol.addBond(3, 4, IBond.Order.SINGLE); // 4
		mol.addBond(4, 5, IBond.Order.SINGLE); // 5
		mol.addBond(5, 0, IBond.Order.SINGLE); // 6
		mol.addBond(5, 6, IBond.Order.SINGLE); // 7
		mol.addBond(6, 7, IBond.Order.SINGLE); // 8
		mol.addBond(7, 4, IBond.Order.SINGLE); // 9
		mol.addBond(8, 0, IBond.Order.SINGLE); // 10
		mol.addBond(9, 1, IBond.Order.SINGLE); // 11		
		mol.addBond(9, 8, IBond.Order.SINGLE); // 11		
		
			
		return mol;
	}

	Molecule buildSpiroRings()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		mol.addAtom(new Atom("C")); // 4
		mol.addAtom(new Atom("C")); // 5
		mol.addAtom(new Atom("C")); // 6
		mol.addAtom(new Atom("C")); // 7
		mol.addAtom(new Atom("C")); // 8
		mol.addAtom(new Atom("C")); // 9

		
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 3, IBond.Order.SINGLE); // 3
		mol.addBond(3, 4, IBond.Order.SINGLE); // 4
		mol.addBond(4, 5, IBond.Order.SINGLE); // 5
		mol.addBond(5, 6, IBond.Order.SINGLE); // 6
		mol.addBond(6, 0, IBond.Order.SINGLE); // 7
		mol.addBond(6, 7, IBond.Order.SINGLE); // 8
		mol.addBond(7, 8, IBond.Order.SINGLE); // 9
		mol.addBond(8, 9, IBond.Order.SINGLE); // 10
		mol.addBond(9, 6, IBond.Order.SINGLE); // 11				
		return mol;
	}
	
	
	Molecule buildRing()
	{
		Molecule mol = new Molecule();
		mol.addAtom(new Atom("C")); // 0
		mol.addAtom(new Atom("C")); // 1
		mol.addAtom(new Atom("C")); // 2
		mol.addAtom(new Atom("C")); // 3
		mol.addAtom(new Atom("C")); // 4
		mol.addAtom(new Atom("C")); // 5
//		mol.addAtom(new Atom("C")); // 6
//		mol.addAtom(new Atom("C")); // 7
//		mol.addAtom(new Atom("C")); // 8
//		mol.addAtom(new Atom("C")); // 9
		
		mol.addBond(0, 1, IBond.Order.SINGLE); // 1
		mol.addBond(1, 2, IBond.Order.SINGLE); // 2
		mol.addBond(2, 3, IBond.Order.SINGLE); // 3
		mol.addBond(3, 4, IBond.Order.SINGLE); // 4
		mol.addBond(4, 5, IBond.Order.SINGLE); // 5
		mol.addBond(5, 0, IBond.Order.SINGLE); // 6
//		mol.addBond(5, 6, IBond.Order.SINGLE); // 7
//		mol.addBond(6, 7, IBond.Order.SINGLE); // 8
//		mol.addBond(7, 4, IBond.Order.SINGLE); // 9
//		mol.addBond(8, 0, IBond.Order.SINGLE); // 10
//		mol.addBond(9, 1, IBond.Order.SINGLE); // 11		
		
			
		return mol;
	}
	

	
	IMolecule loadMolecule(String inFile) throws Exception
	{
		FileInputStream fis = new FileInputStream(inFile);
		mr = new MDLReader(fis, Mode.STRICT);
		chemFile = (ChemFile)mr.read((ChemObject)new ChemFile());
		fis.close();
		chemSequence = chemFile.getChemSequence(0);
		chemModel = chemSequence.getChemModel(0);
		setOfMolecules = chemModel.getMoleculeSet();
		molecule = setOfMolecules.getMolecule(0);
//		for (int i = 0; i < molecule.getAtomCount(); i++)
//		{
//			molecule.getAtomAt(i).setPoint2d(null);
//		}
		return molecule;
	}
}
