package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

public class LinkAssetArtifact extends LinkArtifact {
	GfxObject		line	= null;
	AssetArtifact	asset;
	UMLArtifact		target;

	public LinkAssetArtifact(final AssetArtifact asset, final UMLArtifact target) {
		super(asset, target);
		this.asset = asset;
		this.asset.addDependency(this, target);
		this.target = target;
		this.target.addDependency(this, asset);
	}

	@Override
	public void buildGfxObject() {

		this.leftPoint = this.asset.getCenter();
		this.rightPoint = this.target.getCenter();
		this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(this.line, GfxStyle.DASH);
		GfxManager.getPlatform().moveToBack(this.gfxObject);

	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		// Nothing to edit
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(ASSET_LINK.getMessage());
		return rightMenu;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	@Override
	public void removeCreatedDependency() {
		this.asset.removeDependency(this);
		this.target.removeDependency(this);
	}

	@Override
	public void setCanvas(final UMLCanvas canvas) {
		this.canvas = canvas;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "LinkAsset$<" + this.asset.getId() + ">!<" + this.target.getId() + ">";
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkNoteHighlightedForegroundColor(), 2);
	}


}
