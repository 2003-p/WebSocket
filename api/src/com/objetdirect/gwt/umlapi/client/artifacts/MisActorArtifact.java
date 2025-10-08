package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;


public class MisActorArtifact extends NodeArtifact {
	MisActorPartGfxArtifact	misactorGfx;
	MisPrincipalPartGfxArtifact misprincipalGfx;
	MisExPrincipalPartGfxArtifact misexprincipalGfx;
	MisActorPartNameArtifact		misactorName;
	int type;


	/*default needs */

//	public MisActorArtifact() {
//		this("MisActor");
//	}

	public MisActorArtifact(final String misactorName,int type) {
		this(misactorName, "",type);
	}

	public MisActorArtifact(final String misactorName, final String stereotype,int type) {
		super();
		this.misactorName = new MisActorPartNameArtifact(misactorName, stereotype);
		if(type==1){
			this.misactorGfx = new MisActorPartGfxArtifact();
			this.nodeParts.add(this.misactorName);
			this.nodeParts.add(this.misactorGfx);
			this.misactorName.setNodeArtifact(this);
			this.misactorGfx.setNodeArtifact(this);
			this.type = type;
		}else if(type==2){
			this.misexprincipalGfx = new MisExPrincipalPartGfxArtifact();
			this.nodeParts.add(this.misactorName);
			this.nodeParts.add(this.misexprincipalGfx);
			this.misactorName.setNodeArtifact(this);
			this.misexprincipalGfx.setNodeArtifact(this);
			this.type = type;
		}else if(type==3){
			this.misprincipalGfx = new MisPrincipalPartGfxArtifact();
			this.nodeParts.add(this.misactorName);
			this.nodeParts.add(this.misprincipalGfx);
			this.misactorName.setNodeArtifact(this);
			this.misprincipalGfx.setNodeArtifact(this);
			this.type = type;
		}

	}

	public GfxObject getGfx() {
		return this.misactorGfx.gfxObject;
	}

	@Override
	public String getName() {
		return this.misactorName.getMisActorName();
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle misactorNameRightMenu = this.misactorName.getRightMenu();

		rightMenu.setName("MisActor:" + this.misactorName.getMisActorName());
		rightMenu.addItem(misactorNameRightMenu.getName(), misactorNameRightMenu.getSubMenu());
		return rightMenu;
	}

	@Override
	public String toURL() {
		return "MisActor$" + this.getLocation() + "!" + this.misactorName.toURL()+"!"+this.type;
	}

}
