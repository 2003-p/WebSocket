package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;


public class LinkUseCaseRelationArtifact extends LinkArtifact {
	UseCaseArtifact				usecaseArtifact;
	GfxObject					line	= null;
	UseCaseRelationLinkArtifact	relationLinkArtifact;

	public LinkUseCaseRelationArtifact(final UseCaseArtifact usecaseArtifact, final UseCaseRelationLinkArtifact relation) {
		super(usecaseArtifact, relation);
		this.usecaseArtifact = usecaseArtifact;
		this.usecaseArtifact.addDependency(this, relation);
		this.relationLinkArtifact = relation;
		this.relationLinkArtifact.addDependency(this, usecaseArtifact);
	}

	@Override
	public void buildGfxObject() {
		this.leftPoint = this.usecaseArtifact.getCenter();
		this.rightPoint = this.relationLinkArtifact.getCenter();
		this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkClassForegroundColor(), 1);
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
		rightMenu.setName(CLASS_RELATION_LINK.getMessage() + this.usecaseArtifact.getName());
		return rightMenu;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	@Override
	public void removeCreatedDependency() {
		this.usecaseArtifact.removeDependency(this);
		this.relationLinkArtifact.removeDependency(this);
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
		return "LinkUseCaseRelation$<" + this.usecaseArtifact.getId() + ">!<" + this.relationLinkArtifact.getId() +">";
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkClassForegroundColor(), 1);
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getLinkClassHighlightedForegroundColor(), 2);
	}

}
