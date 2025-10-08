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
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * This class represent an attribute in a class
 *
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLClassAttribute extends UMLNode {

	protected String		name;
	protected String		type;
	protected boolean		validated	= true;

	protected UMLVisibility	visibility;

	// add Yamazaki
	private Map<String,GfxObject> attributesGfxObject = new HashMap<String,GfxObject>();


	/**
	 * Parse an attribute from a {@link String}
	 *
	 * @param attributeToParse
	 *            The string containing an {@link UMLClassAttribute} obtained with {@link UMLClassAttribute#toString()}
	 *
	 * @return The new parsed {@link UMLClassAttribute} or an empty one if there was a problem
	 */
	public static UMLClassAttribute parseAttribute(final String attributeToParse) {

		final LexicalAnalyzer lex = new LexicalAnalyzer(attributeToParse);
		String type = "";
		String name = "";
		UMLVisibility visibility = UMLVisibility.PRIVATE; //TODO PACKAGED => PRIVATE
		try {

			LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk != null) && (tk.getType() != LexicalFlag.VISIBILITY)) {
				visibility = UMLVisibility.PRIVATE;
			} else if (tk != null) {
				visibility = UMLVisibility.getVisibilityFromToken(tk.getContent().charAt(0));
				tk = lex.getToken();
			}
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type'");
			}
			name = tk.getContent();
			tk = lex.getToken();
			if (tk != null) {
				if ((tk.getType() != LexicalFlag.SIGN) || !tk.getContent().equals(":")) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type'");
				}
				tk = lex.getToken();
				if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type'");
				}
				type = tk.getContent();
			}

		} catch (final GWTUMLAPIException e) {
			Logger.getGlobal().severe(e.getMessage());
		}
		return new UMLClassAttribute(visibility, type, name);
	}



	/**
	 * Constructor of the attribute
	 *
	 * @param visibility
	 * @param type
	 *            Type of the attribute
	 * @param name
	 *            Name of the attribute
	 */
	public UMLClassAttribute(final UMLVisibility visibility, final String type, final String name) {
		super();
		this.visibility = visibility;
		this.type = type;
		this.name = name;
	}

	/**
	 * Getter for the name
	 *
	 * @return the name
	 *
	 */
	public String getName() {
		return this.name.toString().replaceAll(" ", "");
	}

	/**
	 * Getter for the type
	 *
	 * @return the type
	 *
	 */
	public String getType() {
		return this.type.toString().replaceAll(" ", "");
	}

	/**
	 * Getter for the visibility
	 *
	 * @return the visibility
	 */
	public UMLVisibility getVisibility() {
		return this.visibility;
	}

	/**
	 * add Yamazaki Getter for the visibility String
	 *
	 * @return visibility String
	 */
	public String getVisibilitytoString()
	{
		return this.visibility.toString();
	}

	/**
	 * Get the validated state of the attribute
	 *
	 * @return <ul>
	 *         <li><b>True</b> if validated</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 *
	 */
	public boolean isValidated() {
		return this.validated;
	}

	/**
	 * Setter for the name
	 *
	 * @param name
	 *            to be set
	 *
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Setter for the type
	 *
	 * @param type
	 *            to be set
	 *
	 */
	@Override
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Set the validation state
	 *
	 * @param validated
	 *            boolean for validated validation state
	 *
	 */

	public void setValidated(final boolean validated) {
		this.validated = validated;
	}

	/**
	 * Set the visibility of the Attribute
	 *
	 * @see UMLVisibility
	 * @param visibility
	 */
	@Override
	public void setVisibility(final UMLVisibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * Format a string from attribute name and type
	 *
	 * @return the UML formatted string for attribute name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(this.visibility);
		f.append(this.name);
		if ((this.type != null) && !this.type.equals("")) {
			f.append(" : ");
			f.append(this.type);
		}
		return f.toString();
	}

	@Override
	public String getDiffVisibilityKey() {
		// TODO 自動生成されたメソッド・スタブ
		String key = name + "{visibility";
		return key.replaceAll(" ","");
	}

	@Override
	public String getDiffNameKey() {
		// TODO 自動生成されたメソッド・スタブ
		return this.name.replaceAll(" ","");
	}

	@Override
	public String getDiffTypeKey() {
		// TODO 自動生成されたメソッド・スタブ
		String key = this.name + "}type";
		return key.replaceAll(" ","");
	}

	@Override
	public void setStrokeBLACK(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.attributesGfxObject.containsKey(key))
		{
			super.setStroke_BLACK(this.attributesGfxObject.get(key));
		}

	}

	@Override
	public void setStrokeRED(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.attributesGfxObject.containsKey(key))
			super.setStroke_RED(this.attributesGfxObject.get(key));
	}
	
	@Override
	public void setStrokeBLUE(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.attributesGfxObject.containsKey(key))
			super.setStroke_BLUE(this.attributesGfxObject.get(key));
	}

	@Override
	public void setVisibilityGfxObject(GfxObject visibility) {
		// TODO 自動生成されたメソッド・スタブ
		attributesGfxObject.put(this.getDiffVisibilityKey(), visibility);
	}

	@Override
	public void setNameGfxObject(GfxObject name) {
		// TODO 自動生成されたメソッド・スタブ
		attributesGfxObject.put(this.getDiffNameKey(), name);
	}

	@Override
	public void setTypeGfxObject(GfxObject type) {
		// TODO 自動生成されたメソッド・スタブ
		attributesGfxObject.put(this.getDiffTypeKey(), type);
	}

	@Override
	public boolean hasGfxObjectKey(String haskey) {
		// TODO 自動生成されたメソッド・スタブ
		return attributesGfxObject.containsKey(haskey) ? true : false;
	}

	public GfxObject getGfxObject(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return attributesGfxObject.get(key);
	}



}
