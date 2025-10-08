package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;

//2015/5/13 complete
public class MisUseCaseArtifact extends NodeArtifact{

	//MisUseCasePartTypesArtifact usecaseType;
	MisUseCasePartNameArtifact misusecaseName;

	public MisUseCaseArtifact() {
		this("MisUseCase");
	}

	public MisUseCaseArtifact(final String misusecaseName) {
		this(misusecaseName, "");
	}

	public MisUseCaseArtifact(final String misusecaseName, final String stereotype){
		super();
		this.misusecaseName= new MisUseCasePartNameArtifact(misusecaseName);
		//this.misusecaseType = new MisUseCasePartTypesArtifact();
		this.nodeParts.add(this.misusecaseName);
		//this.nodeParts.add(this.misusecaseType);
		this.misusecaseName.setNodeArtifact(this);
		//this.misusecaseType.setNodeArtifact(this);
	}

//	public void addType(final UMLMisUseCaseType type){
//		this.misusecaseType.add(type);
//	}

//	public List<UMLMisUseCaseType> getType(){
//		return this.misusecaseType.getList();
//	}

	public String getName(){
		return this.misusecaseName.getMisUseCaseName();
	}


	@Override
	public String toURL(){
		return "MisUc$" + this.getLocation() + "!" + this.misusecaseName.toURL() ;
	}

	@Override
	public MenuBarAndTitle getRightMenu(){
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle misusecaseNameRightMenu = this.misusecaseName.getRightMenu();
		//final MenuBarAndTitle misusecaseTypeRightMenu = this.misusecaseType.getRightMenu();

		rightMenu.setName("MisUseCase:" + this.misusecaseName.getMisUseCaseName());

		rightMenu.addItem(misusecaseNameRightMenu.getName(), misusecaseNameRightMenu.getSubMenu());
	//	rightMenu.addItem(misusecaseTypeRightMenu.getName(), misusecaseTypeRightMenu.getSubMenu());

		return rightMenu;
	}

}