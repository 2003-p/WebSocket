package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;

public class UseCaseArtifact extends NodeArtifact{

	//UseCasePartTypesArtifact usecaseType;
	UseCasePartNameArtifact usecaseName;

	public UseCaseArtifact() {
		this("UseCase");
	}

	public UseCaseArtifact(final String usecaseName) {
		this(usecaseName, "");
	}

	public UseCaseArtifact(final String usecaseName, final String stereotype){
		super();
		this.usecaseName= new UseCasePartNameArtifact(usecaseName);
		//this.usecaseType = new UseCasePartTypesArtifact();
		this.nodeParts.add(this.usecaseName);
		//this.nodeParts.add(this.usecaseType);
		this.usecaseName.setNodeArtifact(this);
		//this.usecaseType.setNodeArtifact(this);
	}

//	public void addType(final UMLUseCaseType type){
//		this.usecaseType.add(type);
//	}

//	public List<UMLUseCaseType> getType(){
//		return this.usecaseType.getList();
//	}

	public String getName(){
		return this.usecaseName.getUseCaseName();
	}


	@Override
	public String toURL(){
		return "Uc$" + this.getLocation() + "!" + this.usecaseName.toURL() ;
	}

	@Override
	public MenuBarAndTitle getRightMenu(){
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle usecaseNameRightMenu = this.usecaseName.getRightMenu();
		//final MenuBarAndTitle usecaseTypeRightMenu = this.usecaseType.getRightMenu();

		rightMenu.setName("UseCase:" + this.usecaseName.getUseCaseName());

		rightMenu.addItem(usecaseNameRightMenu.getName(), usecaseNameRightMenu.getSubMenu());
	//	rightMenu.addItem(usecaseTypeRightMenu.getName(), usecaseTypeRightMenu.getSubMenu());

		return rightMenu;
	}

}