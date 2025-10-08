package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;

public class AssetArtifact extends NodeArtifact{

	//AssetPartTypesArtifact assetType;
	AssetPartNameArtifact assetName;

	public AssetArtifact() {
		this("Asset");
	}

	public AssetArtifact(final String assetName) {
		this(assetName, "");
	}

	public AssetArtifact(final String assetName, final String stereotype){
		super();
		this.assetName= new AssetPartNameArtifact(assetName, stereotype);
		//this.assetType = new AssetPartTypesArtifact();
		this.nodeParts.add(this.assetName);
		//this.nodeParts.add(this.assetType);
		this.assetName.setNodeArtifact(this);
		//this.assetType.setNodeArtifact(this);
	}

//	public void addType(final UMLAssetType type){
//		this.assetType.add(type);
//	}

//	public List<UMLAssetType> getType(){
//		return this.assetType.getList();
//	}

	public String getName(){
		return this.assetName.getAssetName();
	}


	@Override
	public String toURL(){
		return "Asset$" + this.getLocation() + "!" + this.assetName.toURL() ;
	}

	@Override
	public MenuBarAndTitle getRightMenu(){
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle assetNameRightMenu = this.assetName.getRightMenu();
		//final MenuBarAndTitle assetTypeRightMenu = this.assetType.getRightMenu();

		rightMenu.setName("Asset:" + this.assetName.getAssetName());

		rightMenu.addItem(assetNameRightMenu.getName(), assetNameRightMenu.getSubMenu());
	//	rightMenu.addItem(misusecaseTypeRightMenu.getName(), misusecaseTypeRightMenu.getSubMenu());

		return rightMenu;
	}


}