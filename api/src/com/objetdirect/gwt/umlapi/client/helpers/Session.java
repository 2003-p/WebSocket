/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;


/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class Session {
	public static UMLCanvas	activeCanvas =null;
	public static String language = "jp";
	public static String	mode = "none";
	public static String step = null;
	public static int preEditEventID;
	public static UMLDiagram.Type   diagramType = null;
	public static int exerciseId = -1;
	public static String studentId = "";

	/**
	 * Getter for the active canvas
	 *
	 * @return The active {@link UMLCanvas}
	 */
	public static UMLCanvas getActiveCanvas() {
		return Session.activeCanvas;
	}

	/**
	 * @return mode
	 */
	public static String getMode() {
		return mode;
	}

	/**
	 * @param mode セットする mode
	 */
	public static void setMode(String mode) {
		Session.mode = mode;
	}

	/**
	 * Setter for the active canvas (the one which will receive the hot keys)
	 *
	 * @param canvas
	 *            The active {@link UMLCanvas}
	 */
	public static void setActiveCanvas(final UMLCanvas canvas) {
		Session.activeCanvas = canvas;
	}

	public static String getStep() {
		return step;
	}

	public static void setStep(String step) {
		Session.step = step;
	}
}
