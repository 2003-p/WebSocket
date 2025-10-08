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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyzer;
import com.objetdirect.gwt.umlapi.client.artifacts.ISetStrokeRED;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * This class represent a method in a class
 *
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLClassMethod extends UMLNode implements IGetDiffString, ISetStrokeRED {

	/**
	 * Parse a method from a {@link String}
	 *
	 * @param methodToParse
	 *            The string containing an {@link UMLClassMethod} obtained with {@link UMLClassMethod#toString()}
	 *
	 * @return The new parsed {@link UMLClassMethod} or an empty one if there was a problem
	 */
	public static UMLClassMethod parseMethod(final String methodToParse) {
		final LexicalAnalyzer lex = new LexicalAnalyzer(methodToParse);

		try {
			final MethodSyntaxAnalyzer methodSyntaxAnalyser = new MethodSyntaxAnalyzer();

			methodSyntaxAnalyser.process(lex, null);
			return methodSyntaxAnalyser.getMethod();

		} catch (final GWTUMLAPIException e) {
			Logger.getGlobal().severe(e.getMessage());

		}
		return null;
	}

	private String				name;

	private List<UMLParameter>	parameters;

	private String				returnType;

	private boolean				validated	= true;

	private UMLVisibility		visibility;

	// add Yamazaki
	private Map<String,GfxObject> methodGfxObjectMap = new HashMap<String,GfxObject>();

	/**
	 * Constructor of the method
	 *
	 * @param visibility
	 * @param returnType
	 *            The return type of the method
	 * @param name
	 *            The name of the method
	 * @param parameters
	 *            The parameters list of this method
	 */
	public UMLClassMethod(final UMLVisibility visibility, final String returnType, final String name, final List<UMLParameter> parameters) {
		super();
		this.visibility = visibility;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
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
	 * Getter for the parameters list
	 *
	 * @return the parameters list
	 *
	 */
	public List<UMLParameter> getParameters() {
		return this.parameters;
	}

	/**
	 * Getter for the return type
	 *
	 * @return the return type
	 *
	 */
	public String getReturnType() {
		return this.returnType.toString().replaceAll(" ", "");
	}

	/**
	 * Getter of the visibility of this method
	 *
	 * @return The visibility of this method
	 */
	public UMLVisibility getVisibility() {
		return this.visibility;
	}

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
	 * Setter for the parameters list
	 *
	 * @param parameters
	 *            list to be set
	 *
	 */
	public void setParameters(final List<UMLParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Setter for the return type
	 *
	 * @param returnType
	 *            return type to be set
	 *
	 */
	public void setReturnType(final String returnType) {
		this.returnType = returnType;
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
	 * Setter for the visibility of the method
	 *
	 * @param visibility
	 *            The {@link UMLVisibility} of this method
	 */
	@Override
	public void setVisibility(final UMLVisibility visibility) {
		this.visibility = visibility;
	}

	/////////////////////////////////////////////////////
	// add Yamazaki
	public void addParametar(UMLParameter para)
	{
		this.parameters.add(para);
	}
	/////////////////////////////////////////////////////

	/**
	 * Format a string from method name, parameters and return type
	 *
	 * @return the UML formatted string for method name, parameters and return type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(this.visibility);
		f.append(this.name);
		f.append("(");
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			boolean first = true;
			for (final UMLParameter parameter : this.parameters) {
				if (!first) {
					f.append(", ");
				} else {
					first = false;
				}
				f.append(parameter.toString());
			}
		}
		f.append(")");
		if (this.returnType != null && !this.getReturnType().equals("")) {
			f.append(" : ");
			f.append(this.returnType);
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
		return name.replaceAll(" ","");
	}

	@Override
	public String getDiffTypeKey() {
		// TODO 自動生成されたメソッド・スタブ
		String key = name + "}type";
		return key.replaceAll(" ","");
	}

	@Override
	public void setStrokeBLACK(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(methodGfxObjectMap.containsKey(key))
			super.setStroke_BLACK(this.methodGfxObjectMap.get(key));
	}

	@Override
	public void setStrokeRED(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(methodGfxObjectMap.containsKey(key))
			super.setStroke_RED(this.methodGfxObjectMap.get(key));
	}
	
	@Override
	public void setStrokeBLUE(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(methodGfxObjectMap.containsKey(key))
			super.setStroke_RED(this.methodGfxObjectMap.get(key));
	}

	@Override
	public void setVisibilityGfxObject(GfxObject visibility) {
		// TODO 自動生成されたメソッド・スタブ
		//this.methodVGfxObject = visibility;
		methodGfxObjectMap.put(this.getDiffVisibilityKey(), visibility);
	}

	@Override
	public void setNameGfxObject(GfxObject name) {
		// TODO 自動生成されたメソッド・スタブ
		//this.methodNGfxObject = name;
		methodGfxObjectMap.put(this.getDiffNameKey(), name);
	}

	@Override
	public void setTypeGfxObject(GfxObject type) {
		// TODO 自動生成されたメソッド・スタブ
		//this.methodTGfxObject = type;
		methodGfxObjectMap.put(this.getDiffTypeKey(), type);
	}

	@Override
	public void setType(String type) {
		// TODO 自動生成されたメソッド・スタブ
		this.returnType = type;
	}

	@Override
	public boolean hasGfxObjectKey(String haskey) {
		// TODO 自動生成されたメソッド・スタブ
		return methodGfxObjectMap.containsKey(haskey) ? true : false;
	}

	public void removeParameter(UMLParameter targetpara)
	{
		parameters.remove(targetpara);
	}

	public GfxObject getGfxObject(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return methodGfxObjectMap.get(key);
	}


}
