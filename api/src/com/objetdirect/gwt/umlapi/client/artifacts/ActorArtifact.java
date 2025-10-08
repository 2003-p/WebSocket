package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;


public class ActorArtifact extends NodeArtifact {
	ActorPartGfxArtifact	actorGfx;
	ActorPartNameArtifact		actorName;

	public ActorArtifact() {
		this("Actor");
	}

	public ActorArtifact(final String actorName) {
		this(actorName, "");
	}

	public ActorArtifact(final String actorName, final String stereotype) {
		super();
		this.actorName = new ActorPartNameArtifact(actorName);
		this.actorGfx = new ActorPartGfxArtifact();
		this.nodeParts.add(this.actorName);
		this.nodeParts.add(this.actorGfx);
		this.actorName.setNodeArtifact(this);
		this.actorGfx.setNodeArtifact(this);
	}

	public GfxObject getGfx() {
		return this.actorGfx.gfxObject;
	}

	@Override
	public String getName() {
		return this.actorName.getActorName();
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle actorNameRightMenu = this.actorName.getRightMenu();

		rightMenu.setName("Actor:" + this.actorName.getActorName());
		rightMenu.addItem(actorNameRightMenu.getName(), actorNameRightMenu.getSubMenu());
		return rightMenu;
	}

	@Override
	public String toURL() {
		return "Actor$" + this.getLocation() + "!" + this.actorName.toURL();

	}


}