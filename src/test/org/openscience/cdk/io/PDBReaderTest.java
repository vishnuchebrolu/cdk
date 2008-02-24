/* $Revision$ $Author$ $Date$
 * 
 * Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  */
package org.openscience.cdk.test.io;

import org.junit.Assert;
import org.junit.Test;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.interfaces.*;
import org.openscience.cdk.io.IChemObjectReader;
import org.openscience.cdk.io.PDBReader;
import org.openscience.cdk.nonotify.NNChemFile;
import org.openscience.cdk.protein.data.PDBAtom;
import org.openscience.cdk.protein.data.PDBMonomer;
import org.openscience.cdk.protein.data.PDBPolymer;
import org.openscience.cdk.protein.data.PDBStrand;
import org.openscience.cdk.test.NewCDKTestCase;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import java.io.InputStream;
import java.util.List;

/**
 * TestCase for the PDBReader class.
 *
 * @cdk.module test-pdb
 *
 * @author      Edgar Luttmann <edgar@uni-paderborn.de>
 * @author      Martin Eklund <martin.eklund@farmbio.uu.se>
 * @cdk.created 2001-08-09
 */
public class PDBReaderTest extends NewCDKTestCase {


    public void testAccepts() {
    	PDBReader reader = new PDBReader();
    	Assert.assertTrue(reader.accepts(ChemFile.class));
    }

	public void testPDBFileCoffein() throws Exception {
        String filename = "data/pdb/coffeine.pdb";
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

        IChemObjectReader oReader = new PDBReader(ins);
        Assert.assertNotNull(oReader);

        IChemFile oChemFile = (IChemFile)oReader.read(new NNChemFile());
        Assert.assertNotNull(oChemFile);
        Assert.assertEquals(oChemFile.getChemSequenceCount(), 1);

        IChemSequence oSeq = oChemFile.getChemSequence(0);
        Assert.assertNotNull(oSeq);
        Assert.assertEquals(oSeq.getChemModelCount(), 1);

        IChemModel oModel = oSeq.getChemModel(0);
        Assert.assertNotNull(oModel);
        Assert.assertEquals(1, oModel.getMoleculeSet().getMoleculeCount());

        IAtomContainer container = oModel.getMoleculeSet().getMolecule(0);
        Assert.assertFalse(container instanceof IBioPolymer);
        Assert.assertTrue(container instanceof IAtomContainer);
        IAtomContainer oMol = (IAtomContainer)container;
        Assert.assertNotNull(oMol);
        Assert.assertEquals(oMol.getAtomCount(), 14);

        IAtom nAtom = oMol.getFirstAtom();
        Assert.assertNotNull(nAtom);
        Assert.assertTrue(nAtom instanceof PDBAtom);
        PDBAtom oAtom = (PDBAtom)nAtom;
        Assert.assertEquals(new String("C"), oAtom.getSymbol());
        Assert.assertEquals(1, oAtom.getSerial());
        Assert.assertEquals("C1", oAtom.getName());
        Assert.assertEquals("MOL", oAtom.getResName());
        Assert.assertEquals("1", oAtom.getResSeq());
        Assert.assertEquals(1.0, oAtom.getOccupancy(), 0);
        Assert.assertEquals(0.0, oAtom.getTempFactor(), 0);

        nAtom = oMol.getAtom(3);
        Assert.assertNotNull(nAtom);
        Assert.assertTrue(nAtom instanceof PDBAtom);
        oAtom = (PDBAtom)nAtom;
        Assert.assertEquals("O", oAtom.getSymbol());
        Assert.assertEquals(4, oAtom.getSerial());
        Assert.assertEquals("O4", oAtom.getName());
        Assert.assertEquals("MOL", oAtom.getResName());
        Assert.assertEquals("1", oAtom.getResSeq());
        Assert.assertEquals(1.0, oAtom.getOccupancy(), 0);
        Assert.assertEquals(0.0, oAtom.getTempFactor(), 0);

        nAtom = oMol.getLastAtom();
        Assert.assertNotNull(nAtom);
        Assert.assertTrue(nAtom instanceof PDBAtom);
        oAtom = (PDBAtom)nAtom;
        Assert.assertEquals("N", oAtom.getSymbol());
        Assert.assertEquals(14, oAtom.getSerial());
        Assert.assertEquals("N14", oAtom.getName());
        Assert.assertEquals("MOL", oAtom.getResName());
        Assert.assertEquals("1", oAtom.getResSeq());
        Assert.assertEquals(1.0, oAtom.getOccupancy(), 0);
        Assert.assertEquals(0.0, oAtom.getTempFactor(), 0);
	}

	/**
	 * Tests reading a protein PDB file.
	 */
	@Test public void testProtein() throws Exception {
		String filename = "data/pdb/Test-1crn.pdb";
		InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

		IChemObjectReader reader = new PDBReader(ins);
		Assert.assertNotNull(reader);

		ChemFile chemFile = (ChemFile) reader.read(new NNChemFile());
		Assert.assertNotNull(chemFile);
		Assert.assertEquals(1, chemFile.getChemSequenceCount());

		org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
		Assert.assertNotNull(seq);
		Assert.assertEquals(1, seq.getChemModelCount());

		IChemModel model = seq.getChemModel(0);
		Assert.assertNotNull(model);
		Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

		IAtomContainer container = model.getMoleculeSet().getMolecule(0);
		Assert.assertTrue(container instanceof IBioPolymer);
		IBioPolymer mol = (IBioPolymer)container;
		Assert.assertNotNull(mol);
		Assert.assertEquals(327, mol.getAtomCount());
		Assert.assertEquals(46, mol.getMonomerCount());
		Assert.assertNotNull(mol.getMonomer("THRA1", "A"));
		Assert.assertEquals(7, mol.getMonomer("THRA1", "A").getAtomCount());
		Assert.assertNotNull(mol.getMonomer("ILEA7", "A"));
		Assert.assertEquals(8, mol.getMonomer("ILEA7", "A").getAtomCount());

		IAtom nAtom = mol.getAtom(94);
		Assert.assertNotNull(nAtom);
		Assert.assertTrue(nAtom instanceof PDBAtom);
		PDBAtom atom = (PDBAtom)nAtom;
		Assert.assertEquals("C", atom.getSymbol());
		Assert.assertEquals(95, atom.getSerial());
		Assert.assertEquals("CZ", atom.getName());
		Assert.assertEquals("PHE", atom.getResName());
		Assert.assertEquals("13", atom.getResSeq());
		Assert.assertEquals(1.0, atom.getOccupancy(), 0.001);
		Assert.assertEquals(6.84, atom.getTempFactor(), 0.001);

	}
  
    @Test public void test114D() throws Exception {
	    String filename = "data/pdb/114D.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    Assert.assertTrue(
	    	"Strand A is not a PDBStrand",
	    	polymer.getStrand("A") instanceof PDBStrand
	    );
	    PDBStrand strandA=(PDBStrand)polymer.getStrand("A");
	    List lst=(List)strandA.getMonomerNamesInSequentialOrder();
	    String monomer1=(String)lst.get(0);
	    IMonomer mono1=strandA.getMonomer(monomer1);
	    Assert.assertNotNull(mono1);
	    Assert.assertNotNull(mono1.getMonomerName());
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono1 instanceof PDBMonomer
		    	);
	    PDBMonomer pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals(pdbMonomer.getResSeq(), "1");

	    String monomer2=(String)lst.get(1);
	    IMonomer mono2=strandA.getMonomer(monomer2);
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono2 instanceof PDBMonomer
		    	);
	    PDBMonomer pdbMonomer2=(PDBMonomer)mono2;
	    Assert.assertEquals(pdbMonomer2.getResSeq(), "2");
	    
	    
	    // chemical validation
	    Assert.assertEquals(552, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(2, polymer.getStrandCount());
	    Assert.assertEquals(24, polymer.getMonomerCount());

	      Assert.assertTrue(polymer.getStrandNames().contains("A"));
	      Assert.assertTrue(polymer.getStrandNames().contains("B"));
	      Assert.assertFalse(polymer.getStrandNames().contains("C"));
	      Assert.assertEquals(24, polymer.getMonomerCount());
	      
	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(0, pdb.getStructures().size());
	      
    }
  
    @Test public void test1SPX() throws Exception {
	    String filename = "data/pdb/1SPX.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(1904, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(1, polymer.getStrandCount());
	    Assert.assertEquals(237, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(19, pdb.getStructures().size());
	      
    }

    @Test public void test1XKQ() throws Exception {
	    String filename = "data/pdb/1XKQ.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(8955, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(4, polymer.getStrandCount());
	    Assert.assertEquals(1085, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(90, pdb.getStructures().size());
	      
    }

    @Test public void test1A00() throws Exception {
	    String filename = "data/pdb/1A00.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(4770, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(4, polymer.getStrandCount());
	    Assert.assertEquals(574, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(35, pdb.getStructures().size());
	      
    }


    @Test public void test1BOQ() throws Exception {
	    String filename = "data/pdb/1BOQ.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(1538, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(1, polymer.getStrandCount());
	    Assert.assertEquals(198, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(21, pdb.getStructures().size());
	      
    }

    @Test public void test1TOH() throws Exception {
	    String filename = "data/pdb/1TOH.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(2804, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(1, polymer.getStrandCount());
	    Assert.assertEquals(325, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(23, pdb.getStructures().size());
    }
    
    @Test public void test1CKV() throws Exception {
	    String filename = "data/pdb/1CKV.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(14, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    // chemical validation
	    Assert.assertEquals(31066, ChemFileManipulator.getAtomCount(chemFile));
	    Assert.assertEquals(1, polymer.getStrandCount());
	    Assert.assertEquals(141, polymer.getMonomerCount());

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;

	    // PDB validation
	    Assert.assertEquals(9, pdb.getStructures().size());
    }
    
    @Test
    public void test1D66() throws Exception {
	    String filename = "data/pdb/1D66.pdb";
	    InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);

	    IChemObjectReader reader = new PDBReader(ins);
	    Assert.assertNotNull(reader);

	    IChemFile chemFile = (IChemFile) reader.read(new NNChemFile());
	    Assert.assertNotNull(chemFile);
	    Assert.assertEquals(1, chemFile.getChemSequenceCount());

	    org.openscience.cdk.interfaces.IChemSequence seq = chemFile.getChemSequence(0);
	    Assert.assertNotNull(seq);
	    Assert.assertEquals(1, seq.getChemModelCount());

	    IChemModel model = seq.getChemModel(0);
	    Assert.assertNotNull(model);
	    Assert.assertEquals(1, model.getMoleculeSet().getMoleculeCount());

	    IAtomContainer container = model.getMoleculeSet().getMolecule(0);
	    Assert.assertTrue(container instanceof IBioPolymer);
	    IBioPolymer polymer = (IBioPolymer)container;

	    Assert.assertTrue(polymer instanceof PDBPolymer);
	    PDBPolymer pdb = (PDBPolymer)polymer;
	    Assert.assertEquals(4, pdb.getStrandCount());

	    Assert.assertTrue(polymer.getStrandNames().contains("D"));
	    Assert.assertTrue(
	    	"Strand D is not a PDBStrand",
	    	polymer.getStrand("D") instanceof PDBStrand
	    );
	    Assert.assertTrue(polymer.getStrandNames().contains("E"));
	    Assert.assertTrue(
		    	"Strand E is not a PDBStrand",
		    	polymer.getStrand("E") instanceof PDBStrand
		    );
	    Assert.assertTrue(polymer.getStrandNames().contains("A"));
	    Assert.assertTrue(
		    	"Strand A is not a PDBStrand",
		    	polymer.getStrand("A") instanceof PDBStrand
		    );
	    Assert.assertTrue(polymer.getStrandNames().contains("B"));
	    Assert.assertTrue(
		    	"Strand B is not a PDBStrand",
		    	polymer.getStrand("B") instanceof PDBStrand
		    );

	    //Check to pick up all 4 strands
	    Assert.assertEquals(polymer.getStrands().size(),4);

	    //The following check is to see that the first monomers in a strand
	    //can be accessed consecutively
	    //i.e. their resSeq numbering follows that in the File

	    //Strand A
	    PDBStrand strandA=(PDBStrand)polymer.getStrand("A");
	    List lst=(List)strandA.getMonomerNamesInSequentialOrder();
	    
	    //Should be 57 monomers in strand A
	    Assert.assertEquals(57, lst.size());
	    
	    String monomer1=(String)lst.get(0);
	    IMonomer mono1=strandA.getMonomer(monomer1);
	    Assert.assertNotNull(mono1);
	    Assert.assertNotNull(mono1.getMonomerName());
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono1 instanceof PDBMonomer
		    	);
	    PDBMonomer pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("A", pdbMonomer.getChainID());
	    Assert.assertEquals("8", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(1);
	    mono1=strandA.getMonomer(monomer1);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("A", pdbMonomer.getChainID());
	    Assert.assertEquals("9", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(2);
	    mono1=strandA.getMonomer(monomer1);
	    pdbMonomer =(PDBMonomer)mono1;
	    Assert.assertEquals("A", pdbMonomer.getChainID());
	    Assert.assertEquals("10",pdbMonomer.getResSeq());

	    //Strand B
	    PDBStrand strandB=(PDBStrand)polymer.getStrand("B");
	    lst=(List)strandB.getMonomerNamesInSequentialOrder();
	    
	    //Should be 57 monomers in strand B
	    Assert.assertEquals(57, lst.size());
	    
	    monomer1=(String)lst.get(0);
	    mono1=strandB.getMonomer(monomer1);
	    Assert.assertNotNull(mono1);
	    Assert.assertNotNull(mono1.getMonomerName());
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono1 instanceof PDBMonomer
		    	);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("B", pdbMonomer.getChainID());
	    Assert.assertEquals("8", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(1);
	    mono1=strandB.getMonomer(monomer1);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("B", pdbMonomer.getChainID());
	    Assert.assertEquals("9", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(2);
	    mono1=strandB.getMonomer(monomer1);
	    pdbMonomer =(PDBMonomer)mono1;
	    Assert.assertEquals("B", pdbMonomer.getChainID());
	    Assert.assertEquals("10",pdbMonomer.getResSeq());


	    //Strand E
	    PDBStrand strandE=(PDBStrand)polymer.getStrand("E");
	    lst=(List)strandE.getMonomerNamesInSequentialOrder();
	    
	    //Should be 19 monomers in strand E
	    Assert.assertEquals(19, lst.size());
	    
	    monomer1=(String)lst.get(0);
	    mono1=strandE.getMonomer(monomer1);
	    Assert.assertNotNull(mono1);
	    Assert.assertNotNull(mono1.getMonomerName());
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono1 instanceof PDBMonomer
		    	);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("E", pdbMonomer.getChainID());
	    Assert.assertEquals("20", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(1);
	    mono1=strandE.getMonomer(monomer1);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("E", pdbMonomer.getChainID());
	    Assert.assertEquals("21", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(2);
	    mono1=strandE.getMonomer(monomer1);
	    pdbMonomer =(PDBMonomer)mono1;
	    Assert.assertEquals("E", pdbMonomer.getChainID());
	    Assert.assertEquals("22",pdbMonomer.getResSeq());


	    
	    //Chain D should be 1,2,3...19
	    PDBStrand strandD=(PDBStrand)polymer.getStrand("D");
	    lst=(List)strandD.getMonomerNamesInSequentialOrder();

	    //Should be 19 monomers in strand D
	    Assert.assertEquals(19, lst.size());
	    
	    monomer1=(String)lst.get(0);
	    mono1=strandD.getMonomer(monomer1);
	    Assert.assertNotNull(mono1);
	    Assert.assertNotNull(mono1.getMonomerName());
	    Assert.assertTrue(
		    	"Monomer is not a PDBMonomer",
		    	mono1 instanceof PDBMonomer
		    	);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("D", pdbMonomer.getChainID());
	    Assert.assertEquals("1", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(1);
	    mono1=strandD.getMonomer(monomer1);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("D", pdbMonomer.getChainID());
	    Assert.assertEquals("2", pdbMonomer.getResSeq());

	    monomer1=(String)lst.get(2);
	    mono1=strandD.getMonomer(monomer1);
	    pdbMonomer=(PDBMonomer)mono1;
	    Assert.assertEquals("D", pdbMonomer.getChainID());
	    Assert.assertEquals("3",pdbMonomer.getResSeq());

	    // PDB Structures validation
	    //Should have 6 helices
	    Assert.assertEquals(6, pdb.getStructures().size());
	      
    }
  
    
}