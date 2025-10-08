
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.logging.Logger;

import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;


public class ActorPartGfxArtifact extends NodePartArtifact {
	//private final Map<GfxObject, UMLActorGfx>	GfxObjects;
	private GfxObject								gfxRect;
	//private final List<UMLActorGfx>			gfx;
	private GfxObject								lastGfxObject;
	private GfxObject								gfxImage;

	public ActorPartGfxArtifact() {
		super();
		//this.gfx = new ArrayList<UMLActorGfx>();
		//this.GfxObjects = new LinkedHashMap<GfxObject, UMLActorGfx>();
		this.height = 0;
		this.width = 0;
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.gfxImage = GfxManager.getPlatform().buildImage("normalActor.png");
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, gfxImage);
//		GfxManager.getPlatform().setFillColor(this.gfxImage, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().setStroke(this.gfxImage, ThemeManager.getTheme().getClassForegroundColor(), 1);

		this.gfxRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.gfxRect);
		GfxManager.getPlatform().setFillColor(this.gfxRect, ThemeManager.getTheme().getClassBackgroundColor());
		//GfxManager.getPlatform().setStroke(this.gfxRect, ThemeManager.getTheme().getClassBackgroundColor(), 1);
		GfxManager.getPlatform().translate(this.textVirtualGroup,
				new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
		GfxManager.getPlatform().moveToFront(this.gfxImage);
	}

	@Override
	public void computeBounds() {
		//this.GfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

//		for (final UMLActorGfx gFx : this.gfx) {
//			final GfxObject gfxText = GfxManager.getPlatform().buildText(gFx.toString(),
//					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
//			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, gfxText);
//			GfxManager.getPlatform().setFont(gfxText, OptionsManager.getSmallFont());
//			GfxManager.getPlatform().setStroke(gfxText, ThemeManager.getTheme().getClassBackgroundColor(), 0);
//			GfxManager.getPlatform().setFillColor(gfxText, ThemeManager.getTheme().getClassForegroundColor());
//			int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(gfxText);
//			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(gfxText);
//			thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
//			//thisAttributeWidth += OptionsManager.get("TextLeftPadding");
//			thisAttributeHeight += OptionsManager.get("Te@xtTopPadding") + OptionsManager.get("TextBottomPadding");
//			this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
//			this.height += thisAttributeHeight;
//			this.GfxObjects.put(gfxText, gFx);
//			this.lastGfxObject = gfxText;
//		}
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Logger.getGlobal().info("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int[] getOpaque() {
		return null;
	}

	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		//final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.getHeight());
		final GfxObject image = GfxManager.getPlatform().buildImage("normalActor.png");
		GfxManager.getPlatform().setStrokeStyle(image, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(image, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(image, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, image);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
//		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
//		rightMenu.setName(ATTRIBUTES.getMessage());
//
//		for (final Entry<GfxObject, UMLActorGfx> attribute : this.GfxObjects.entrySet()) {
//			final MenuBar subsubMenu = new MenuBar(true);
//			subsubMenu.addItem(EDIT.getMessage(), this.editCommand(attribute.getKey()));
//			subsubMenu.addItem(DELETE.getMessage(), this.deleteCommand(attribute.getValue()));
//			rightMenu.addItem(attribute.getValue().toString(), subsubMenu);
//		}
//		rightMenu.addItem(ADD_NEW.getMessage(), this.editCommand());
//		return rightMenu;
		return null;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public String toURL() {
//		final StringBuilder attributesURL = new StringBuilder();
//		for (final UMLActorGfx attribute : this.gfx) {
//			attributesURL.append(gfx);
//			attributesURL.append("%");
//		}
//		return attributesURL.toString();
		return null;
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.gfxRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.gfxRect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

}