package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;

public class SecurityUseCaseArtifact extends NodeArtifact{

	SecurityUseCasePartNameArtifact securityusecaseName;

	public SecurityUseCaseArtifact() {
		this("SecurityUseCase");
	}

	public SecurityUseCaseArtifact(final String securityusecaseName) {
		this(securityusecaseName, "");
	}

	public SecurityUseCaseArtifact(final String securityusecaseName, final String stereotype){
		super();
		this.securityusecaseName= new SecurityUseCasePartNameArtifact(securityusecaseName, stereotype);

		this.nodeParts.add(this.securityusecaseName);

		this.securityusecaseName.setNodeArtifact(this);

	}

	public String getName(){
		return this.securityusecaseName.getSecurityUseCaseName();
	}

	@Override
	public String toURL(){
		return "SecurityUc$" + this.getLocation() + "!" + this.securityusecaseName.toURL() ;
	}

	@Override
	public MenuBarAndTitle getRightMenu(){
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle securityusecaseNameRightMenu = this.securityusecaseName.getRightMenu();

		rightMenu.setName("SecurityUseCase:" + this.securityusecaseName.getSecurityUseCaseName());

		rightMenu.addItem(securityusecaseNameRightMenu.getName(), securityusecaseNameRightMenu.getSubMenu());

		return rightMenu;
	}

}