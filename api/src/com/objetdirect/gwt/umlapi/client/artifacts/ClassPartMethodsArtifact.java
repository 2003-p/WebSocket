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
package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartMethodsFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * This class represent the lower Part of a {@link NodeArtifact} It can hold a method list
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartMethodsArtifact extends NodePartArtifact {

	private GfxObject								lastGfxObject;
	private final Map<GfxObject, UMLClassMethod>	methodGfxObjects;
	private GfxObject								methodRect;
	private final List<UMLClassMethod>				methods;


	/**
	 * Constructor of ClassPartMethodsArtifact It initializes the method list
	 *
	 */
	public ClassPartMethodsArtifact() {
		super();
		this.methods = new ArrayList<UMLClassMethod>();
		this.methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
		// List<Parameter> methodParameters = new ArrayList<Parameter>();
		// methodParameters.add(new Parameter("String", "parameter1"));
		// methods.add(new Method("void","method", methodParameters));
		this.height = 0;
		this.width = 0;
	}

	/**
	 * Add a method to the current method list. The graphical object must be rebuilt to reflect the changes
	 *
	 * @param method
	 *            The new method to add
	 */
	public void add(final UMLClassMethod method) {
		this.methods.add(method);
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.methodRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.methodRect);
		GfxManager.getPlatform().setFillColor(this.methodRect, ThemeManager.getTheme().getClassBackgroundColor());//ThemeManager.getTheme().getClassBackgroundColor()
		GfxManager.getPlatform().setStroke(this.methodRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
		GfxManager.getPlatform().translate(this.textVirtualGroup,
				new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
	}

	@Override
	public void computeBounds() {
		this.methodGfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

		for (final UMLClassMethod method : this.methods) {

			int methodElementWidth = 0; //OptionsManager.get("TextLeftPadding");
			List<UMLParameter> parasList = method.getParameters();
			GfxObject methodVNPRGroup = GfxManager.getPlatform().buildVirtualGroup();

			//visibility
			GfxObject visibility = GfxManager.getPlatform().buildText(
					method.getVisibility().toString(),
					new Point(
							OptionsManager.get("AddYamazakiGfxObjectPadding"), 
							OptionsManager.get("TextTopPadding") + this.height
					)
			);

			String chengeObjectColorKey = method.getDiffVisibilityKey();
			GfxObject beforeGfxObject = method.getGfxObject(chengeObjectColorKey);

			if(beforeGfxObject == null)
				super.setStroke_BLACK(visibility);
			else
				super.setStroke_BLACK(visibility,beforeGfxObject);

			method.setVisibilityGfxObject(visibility);
			methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(visibility);

			// name
			GfxObject methodname = GfxManager.getPlatform().buildText(method.getName() + "(",
					new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));

			chengeObjectColorKey = method.getDiffNameKey();
			beforeGfxObject = method.getGfxObject(chengeObjectColorKey);

			if(beforeGfxObject == null)
				super.setStroke_BLACK(methodname);
			else
				super.setStroke_BLACK(methodname,beforeGfxObject);

			method.setNameGfxObject(methodname);
			methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(methodname);

			// para
			List<GfxObject> useVirtualGroupAddGfxObjectList = new ArrayList<GfxObject>();
//			List<GfxObject> paranameList = new ArrayList<GfxObject>();
//			List<GfxObject> paratypeList = new ArrayList<GfxObject>();

			for(int index = 0; index < parasList.size(); index++)
			{
				UMLParameter paraObject = parasList.get(index);

				GfxObject paraname = GfxManager.getPlatform().buildText(paraObject.getName(),
						new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
				chengeObjectColorKey = paraObject.getDiffNameKey();
				beforeGfxObject = paraObject.getGfxObject(chengeObjectColorKey);

				if(beforeGfxObject == null)
					super.setStroke_BLACK(paraname);
				else
					super.setStroke_BLACK(paraname,beforeGfxObject);


				paraObject.setNameGfxObject(paraname);
				methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(paraname);
				useVirtualGroupAddGfxObjectList.add(paraname);

				if(index == parasList.size() - 1)
				{
					GfxObject paratype = GfxManager.getPlatform().buildText(":" + paraObject.getType(),
							new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
					chengeObjectColorKey = paraObject.getDiffTypeKey();
					beforeGfxObject = paraObject.getGfxObject(chengeObjectColorKey);

					if(beforeGfxObject == null)
						super.setStroke_BLACK(paratype);
					else
						super.setStroke_BLACK(paratype,beforeGfxObject);

					paraObject.setTypeGfxObject(paratype);
					methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(paratype);
					useVirtualGroupAddGfxObjectList.add(paratype);
				}
				else
				{
					GfxObject paratype = GfxManager.getPlatform().buildText(":" + paraObject.getType() + ",",
							new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
					chengeObjectColorKey = paraObject.getDiffTypeKey();
					beforeGfxObject = paraObject.getGfxObject(chengeObjectColorKey);

					if(beforeGfxObject == null)
						super.setStroke_BLACK(paratype);
					else
						super.setStroke_BLACK(paratype,beforeGfxObject);

					paraObject.setTypeGfxObject(paratype);
					methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(paratype);
					useVirtualGroupAddGfxObjectList.add(paratype);
				}
			}

//			for(UMLParameter paraObject : parasList)
//			{
//				GfxObject paraname = GfxManager.getPlatform().buildText(paraObject.getName(),
//						new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
//				super.setStroke_BLACK(paraname);
//				paraObject.setNameGfxObject(paraname);
//				methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(paraname);
//				useVirtualGroupAddGfxObjectList.add(paraname);
//
//				GfxObject paratype = GfxManager.getPlatform().buildText(":" + paraObject.getType(),
//						new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
//				super.setStroke_BLACK(paratype);
//				paraObject.setTypeGfxObject(paratype);
//				methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(paratype);
//				useVirtualGroupAddGfxObjectList.add(paratype);
//			}

			// return type
			String artifactReturnTypeString =  method.getReturnType();
			GfxObject returntype;
			if(!artifactReturnTypeString.equals(""))
			{
				returntype = GfxManager.getPlatform().buildText("):" + method.getReturnType(),
						new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
				chengeObjectColorKey = method.getDiffTypeKey();
				beforeGfxObject = method.getGfxObject(chengeObjectColorKey);
			}
			else
			{
				returntype = GfxManager.getPlatform().buildText(")",
						new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + methodElementWidth, OptionsManager.get("TextTopPadding") + this.height));
				chengeObjectColorKey = method.getDiffTypeKey();
				beforeGfxObject = method.getGfxObject(chengeObjectColorKey);
			}
			if(beforeGfxObject == null)
				super.setStroke_BLACK(returntype);
			else
				super.setStroke_BLACK(returntype,beforeGfxObject);

			method.setTypeGfxObject(returntype);
			methodElementWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(returntype);

			// virtual groupにオブジェクトを入れる
			GfxManager.getPlatform().addToVirtualGroup(methodVNPRGroup, visibility);
			GfxManager.getPlatform().addToVirtualGroup(methodVNPRGroup, methodname);

			for(GfxObject addParaGfxObject : useVirtualGroupAddGfxObjectList)
				GfxManager.getPlatform().addToVirtualGroup(methodVNPRGroup, addParaGfxObject);

			GfxManager.getPlatform().addToVirtualGroup(methodVNPRGroup, returntype);

			// method 全体の横幅計算
			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, methodVNPRGroup);
			int thisMethodWidth = methodElementWidth;
			int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(methodname);
			thisMethodWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			//thisMethodWidth += OptionsManager.get("TextLeftPadding");
			thisMethodHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");

			this.width = thisMethodWidth > this.width ? thisMethodWidth : this.width;
			this.height += thisMethodHeight;
			this.methodGfxObjects.put(methodVNPRGroup, method);
			this.lastGfxObject = methodVNPRGroup;



//			final GfxObject methodText = GfxManager.getPlatform().buildText(method.toString(),
//					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
//			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, methodText);
//			GfxManager.getPlatform().setFont(methodText, OptionsManager.getSmallFont());
//			GfxManager.getPlatform().setStroke(methodText, ThemeManager.getTheme().getClassBackgroundColor(), 0);//ThemeManager.getTheme().getClassBackgroundColor()
//			GfxManager.getPlatform().setFillColor(methodText, ThemeManager.getTheme().getClassForegroundColor());//ThemeManager.getTheme().getClassForegroundColor()
//			int thisMethodWidth = GfxManager.getPlatform().getTextWidthFor(methodText);
//			int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(methodText);
//			thisMethodWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
//			//thisMethodWidth += OptionsManager.get("TextLeftPadding");
//			thisMethodHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
//			this.width = thisMethodWidth > this.width ? thisMethodWidth : this.width;
//			this.height += thisMethodHeight;
//
//			this.methodGfxObjects.put(methodText, method);
//			this.lastGfxObject = methodText;
		}
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Logger.getGlobal().info("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
		final List<UMLParameter> methodToCreateParameters = new ArrayList<UMLParameter>();
		methodToCreateParameters.add(new UMLParameter("String", "parameter1"));
		this.methods.add(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "method", methodToCreateParameters));
		this.nodeArtifact.rebuildGfxObject();
		this.edit(this.lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassMethod methodToChange = this.methodGfxObjects.get(editedGfxObject);
		if (methodToChange == null) {
			this.edit();
		} else {
			final ClassPartMethodsFieldEditor editor = new ClassPartMethodsFieldEditor(this.canvas, this, methodToChange);
			editor
			.startEdition(methodToChange.toString(), (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")),
					(this.nodeArtifact.getLocation().getY() + ((ClassArtifact) this.nodeArtifact).className.getHeight()
							+ ((ClassArtifact) this.nodeArtifact).classAttributes.getHeight()
							+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY() + OptionsManager.get("RectangleTopPadding")),
							this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
							- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter for the method list
	 *
	 * @return The current method list
	 */
	public List<UMLClassMethod> getList() {
		return this.methods;
	}

	@Override
	public int[] getOpaque() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOutline()
	 */
	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.getHeight());
		GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(rect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(rect, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, rect);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(METHODS.getMessage());

		for (final Entry<GfxObject, UMLClassMethod> method : this.methodGfxObjects.entrySet()) {
			final MenuBar subsubMenu = new MenuBar(true);
			subsubMenu.addItem(EDIT.getMessage(), this.editCommand(method.getKey()));
			subsubMenu.addItem(DELETE.getMessage(), this.deleteCommand(method.getValue()));
			rightMenu.addItem(method.getValue().toString(), subsubMenu);
		}
		rightMenu.addItem(ADD_NEW.getMessage(), this.editCommand());
		return rightMenu;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	/**
	 * Remove a method to the current method list. The graphical object must be rebuilt to reflect the changes
	 *
	 * @param method
	 *            The method to be removed
	 */
	public void remove(final UMLClassMethod method) {
		String defaultString="+method(parameter1 : String) : void";
		this.methods.remove(method);
		ClassArtifact classArtifact = (ClassArtifact) (this.getNodeArtifact() );

		if(!defaultString.equals(method.toString())){
			MyLoggerExecute.registEditEvent(-1, "Method", "Remove",
					classArtifact.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, method.toString(), "", null, UMLArtifact.getIdCount());
			MyLoggerExecute.registEditEvent(-1, "Method", "RemoveArtifacts",
					classArtifact.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, method.toString(), "", this.canvas.toUrl(), UMLArtifact.getIdCount());

		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder methodsURL = new StringBuilder();
		for (final UMLClassMethod method : this.methods) {
			methodsURL.append(method);
			methodsURL.append("%");
		}
		return methodsURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.methodRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.methodRect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command deleteCommand(final UMLClassMethod method) {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.remove(method);
				ClassPartMethodsArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.edit(gfxo);
			}
		};
	}


}
