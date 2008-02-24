/* $Revision: 7636 $ $Author: nielsout $ $Date: 2007-01-04 18:46:10 +0100 (Thu, 04 Jan 2007) $
 *
 * Copyright (C) 2007  Niels Out <nielsout@users.sf.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All I ask is that proper credit is given for my work, which includes
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
 */
package org.openscience.cdk.controller;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.vecmath.Point2d;

import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.renderer.Renderer2DModel;
import org.openscience.cdk.renderer.progz.IJava2DRenderer;
import org.openscience.cdk.tools.manipulator.ChemModelManipulator;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.Atom;


/**
 * Adds an atom on the given location on mouseclick
 * 
 * @author Niels Out
 * @cdk.svnrev  $Revision: 9162 $
 *
 */
public class Controller2DModuleAddAtom implements IController2DModule {

	//private String atomType;
	
	private IChemModelRelay chemObjectRelay;
	/*private IViewEventRelay eventRelay;
	public void setEventRelay(IViewEventRelay relay) {
		this.eventRelay = relay;
	}*/
/*	public Controller2DModuleAddAtom() {
		this.atomType = "C";
	}*/
/*	public Controller2DModuleAddAtom(String atomType) {
		this.atomType = atomType;
	}*/
	
	public void mouseClickedDouble(Point2d worldCoord) {
		// TODO Auto-generated method stub
	}

	public void mouseClickedDown(Point2d worldCoord) {

		IAtom closestAtom = chemObjectRelay.getClosestAtom(worldCoord);
		String atomType = chemObjectRelay.getController2DModel().getDrawElement();
		if (closestAtom == null) {
			//add atom
			System.out.println("Trying adding atom " + atomType);
			chemObjectRelay.addAtom(atomType, worldCoord);
		}
		else {
			//replace existing atom with new one
			String formerSymbol = closestAtom.getSymbol();

			closestAtom.setSymbol(atomType);
			// configure the atom, so that the atomic number matches the symbol
			try
			{
				IsotopeFactory.getInstance(closestAtom.getBuilder()).configure(closestAtom);
			} catch (Exception exception)
			{
			//	logger.error("Error while configuring atom");
			//	logger.debug(exception);
			}
			// update atom
		//	IAtomContainer container = ChemModelManipulator.getRelevantAtomContainer(chemObjectRelay.getIChemModel(), closestAtom);
		//FIXME: make this update the hydrogen count?
			//	updateAtom(container, closestAtom);

		}
		chemObjectRelay.updateView();

	}

	public void mouseClickedUp(Point2d worldCoord) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDrag(Point2d worldCoordFrom, Point2d worldCoordTo) {
		
	}

	public void mouseEnter(Point2d worldCoord) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExit(Point2d worldCoord) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMove(Point2d worldCoord) {
		
	}

	public void setChemModelRelay(IChemModelRelay relay) {
		this.chemObjectRelay = relay;
	}
	
}