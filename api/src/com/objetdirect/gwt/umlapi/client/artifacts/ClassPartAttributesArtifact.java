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
import com.objetdirect.gwt.umlapi.client.editors.ClassPartAttributesFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * This class represent the middle Part of a {@link NodeArtifact} It can hold an attribute list
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartAttributesArtifact extends NodePartArtifact {
	private final Map<GfxObject, UMLClassAttribute>	attributeGfxObjects;
	private GfxObject								attributeRect;
	private final List<UMLClassAttribute>			attributes;
	private GfxObject								lastGfxObject;



	/**
	 * Constructor of ClassPartAttributesArtifact It initializes the attribute list
	 *
	 */
	public ClassPartAttributesArtifact() {
		super();
		this.attributes = new ArrayList<UMLClassAttribute>();
		this.attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute>();

		this.height = 0;
		this.width = 0;
	}

	/**
	 * Add an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 *
	 * @param attribute
	 *            The new attribute to add
	 */
	public void add(final UMLClassAttribute attribute) {
		this.attributes.add(attribute);
	}

	@Override
	public void buildGfxObject() {

		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.attributeRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.attributeRect);
		GfxManager.getPlatform().setFillColor(this.attributeRect, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().setStroke(this.attributeRect, ThemeManager.getTheme().getClassForegroundColor(), 1); //ThemeManager.getTheme().getClassForegroundColor()
		GfxManager.getPlatform().translate(this.textVirtualGroup,
				new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
	}

	@Override
	public void computeBounds() {
		this.attributeGfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

		for (final UMLClassAttribute attribute : this.attributes) {
			int attributeWidth = 0;

			// add Yamazaki
			//修飾子
			GfxObject vmtGroup = GfxManager.getPlatform().buildVirtualGroup();
			GfxObject visibility = GfxManager.getPlatform().buildText(
					attribute.getVisibility().toString(),
					new Point(
							OptionsManager.get("AddYamazakiGfxObjectPadding"), 
							OptionsManager.get("TextTopPadding") + this.height
					)
			);
			
			String chengeObjectColorKey = attribute.getDiffVisibilityKey();
			//Window.alert("Key: " + chengeObjectColorKey);
			GfxObject beforeGfxObject = attribute.getGfxObject(chengeObjectColorKey);

			if(beforeGfxObject == null)
			{
				//Window.alert("attribute visibility beforeGfxObject null");
				super.setStroke_BLACK(visibility);
			}
			else
			{
				//Window.alert("attribute visibility beforeGfxObject　nullじゃない");
				super.setStroke_BLACK(visibility,beforeGfxObject);
			}

			attribute.setVisibilityGfxObject(visibility);
			attributeWidth = OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(visibility);


			//　名前------------------------------------------------------------++++++++++++++++
			GfxObject name = GfxManager.getPlatform().buildText(
					attribute.getName(),
					new Point(
							OptionsManager.get("AddYamazakiGfxObjectPadding") + attributeWidth, 
							OptionsManager.get("TextTopPadding") + this.height
					)
			);
			
			chengeObjectColorKey = attribute.getDiffNameKey();
			beforeGfxObject = attribute.getGfxObject(chengeObjectColorKey);

			if(beforeGfxObject == null)
				super.setStroke_BLACK(name);
			else
				super.setStroke_BLACK(name,beforeGfxObject);

			attribute.setNameGfxObject(name);
			attributeWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(name);


			// 型
			GfxObject type = GfxManager.getPlatform().buildText(":" + attribute.getType(),
					new Point(OptionsManager.get("AddYamazakiGfxObjectPadding") + attributeWidth, OptionsManager.get("TextTopPadding") + this.height));

			chengeObjectColorKey = attribute.getDiffTypeKey();
			beforeGfxObject = attribute.getGfxObject(chengeObjectColorKey);

			if(beforeGfxObject == null)
				super.setStroke_BLACK(type);
			else
				super.setStroke_BLACK(type,beforeGfxObject);

			attribute.setTypeGfxObject(type);
			attributeWidth += OptionsManager.get("AddYamazakiGfxObjectPadding") + GfxManager.getPlatform().getTextWidthFor(type);


			GfxManager.getPlatform().addToVirtualGroup(vmtGroup, visibility);
			GfxManager.getPlatform().addToVirtualGroup(vmtGroup, name);
			GfxManager.getPlatform().addToVirtualGroup(vmtGroup, type);


			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, vmtGroup);

			int thisAttributeWidth = attributeWidth;
			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(name);

			/////////////////////
			thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
			this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
			this.height += thisAttributeHeight;
			this.attributeGfxObjects.put(vmtGroup, attribute);
			this.lastGfxObject = vmtGroup;

			//大元
//			final GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString(),
//					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
//			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, attributeText);
//			GfxManager.getPlatform().setFont(attributeText, OptionsManager.getSmallFont());
//			GfxManager.getPlatform().setStroke(attributeText, GfxColor.RED, 1); //ThemeManager.getTheme().getClassBackgroundColor()
//			GfxManager.getPlatform().setFillColor(attributeText, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
//			int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(attributeText);
//			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(attributeText);
//			thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
//			//thisAttributeWidth += OptionsManager.get("TextLeftPadding");
//			thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
//			this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
//			this.height += thisAttributeHeight;
//			this.attributeGfxObjects.put(attributeText, attribute);
//			this.lastGfxObject = attributeText;

		}
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Logger.getGlobal().info("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}



	@Override
	public void edit() {
		//final UMLClassAttribute attributeToCreate = new UMLClassAttribute(UMLVisibility.PRIVATE, "String", "attribute");
		final UMLClassAttribute attributeToCreate = new UMLClassAttribute(UMLVisibility.NONE, "", "");
		this.attributes.add(attributeToCreate);
		this.nodeArtifact.rebuildGfxObject();
		this.attributeGfxObjects.put(this.lastGfxObject, attributeToCreate);
		this.edit(this.lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassAttribute attributeToChange = this.attributeGfxObjects.get(editedGfxObject);
		if (attributeToChange == null) {
			this.edit();
		} else {
			final ClassPartAttributesFieldEditor editor = new ClassPartAttributesFieldEditor(this.canvas, this, attributeToChange);
			editor.startEdition(attributeToChange.toString(), (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")), (this.nodeArtifact.getLocation().getY() + ((ClassArtifact) this.nodeArtifact).className.getHeight()
							+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY() + OptionsManager.get("RectangleTopPadding")), this.nodeWidth
							- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
							- OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter for the attribute list
	 *
	 * @return The current attribute list
	 */
	public List<UMLClassAttribute> getList() {
		return this.attributes;
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
		GfxManager.getPlatform().setStroke(rect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1); //ThemeManager.getTheme().getClassHighlightedForegroundColor()

		GfxManager.getPlatform().setFillColor(rect, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, rect);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(ATTRIBUTES.getMessage());

		for (final Entry<GfxObject, UMLClassAttribute> attribute : this.attributeGfxObjects.entrySet()) {
			final MenuBar subsubMenu = new MenuBar(true);
			subsubMenu.addItem(EDIT.getMessage(), this.editCommand(attribute.getKey()));
			subsubMenu.addItem(DELETE.getMessage(), this.deleteCommand(attribute.getValue()));
			rightMenu.addItem(attribute.getValue().toString(), subsubMenu);
		}
		rightMenu.addItem(ADD_NEW.getMessage(), this.editCommand());
		return rightMenu;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * Remove an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 *
	 * @param attribute
	 *            The attribute to be removed
	 */
	public void remove(final UMLClassAttribute attribute) {
		this.attributes.remove(attribute);
		ClassArtifact classArtifact = (ClassArtifact) (this.getNodeArtifact() );

		if(!attribute.toString().equals("")){ //初期値の空白でなければRemoveイベントを記録
			MyLoggerExecute.registEditEvent(-1, "Attribute", "Remove",
					attribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, attribute.toString(), "", null, UMLArtifact.getIdCount());

			MyLoggerExecute.registEditEvent(-1, "Attribute", "RemoveArtifacts",
					attribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, attribute.toString(), "", this.canvas.toUrl(), UMLArtifact.getIdCount());
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder attributesURL = new StringBuilder();
		for (final UMLClassAttribute attribute : this.attributes) {
			attributesURL.append(attribute);
			attributesURL.append("%");
		}
		return attributesURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.attributeRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.attributeRect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command deleteCommand(final UMLClassAttribute attribute) {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.remove(attribute);
				ClassPartAttributesArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.edit(gfxo);
			}
		};
	}

}
