package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.UseCasePartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLUseCase;

public class UseCasePartNameArtifact extends NodePartArtifact{

	private final UMLUseCase uMLusecase;
	private GfxObject nameEllip;
	private GfxObject nameText;

	public UseCasePartNameArtifact(final String usecaseName){
		super();
		this.uMLusecase = new UMLUseCase(usecaseName);
		this.height = 0;
		this.width = 0;
	}


    @Override
    public void buildGfxObject(){
    	//2015/03/18
    	if(this.textVirtualGroup == null){
    		this.computeBounds();
    	}

    	//this.nameEllip = GfxManager.getPlatform().buildEllipse(this.nodeWidth,this.height);
    	this.nameEllip = GfxManager.getPlatform().buildEllipse(this.width,this.height);
    	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.nameEllip);
    	GfxManager.getPlatform().setFillColor(this.nameEllip, ThemeManager.getTheme().getClassBackgroundColor());
    	GfxManager.getPlatform().setStroke(this.nameEllip, ThemeManager.getTheme().getClassForegroundColor(), 1); //caution

    	GfxManager.getPlatform().translate(
				this.nameText,
				new Point( (this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
						.get("TextLeftPadding")), OptionsManager.get("EllipseTopPadding"))); //一緒でいいかも
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

//    	GfxManager.getPlatform().translate(
//				this.nameText,
//				new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
//						.get("TextLeftPadding")) / 2, OptionsManager.get("EllipseTopPadding"))); //一緒でいいかも
//		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
//    }

    @Override
	public void computeBounds() {
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
		this.nameText = GfxManager.getPlatform().buildText(this.uMLusecase.getName(),
				new Point(OptionsManager.get("EllipseTextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
		GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.nameText);
		GfxManager.getPlatform().setFont(this.nameText, OptionsManager.getFont());
		GfxManager.getPlatform().setStroke(this.nameText, ThemeManager.getTheme().getClassBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(this.nameText, ThemeManager.getTheme().getClassForegroundColor());
		final int thisNameWidth = GfxManager.getPlatform().getTextWidthFor(this.nameText) + OptionsManager.get("TextRightPadding")
				+ OptionsManager.get("TextLeftPadding");
		this.width = thisNameWidth > this.width ? thisNameWidth : this.width;
		this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
		this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		//Logger.getGlobal().info("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}


	@Override
	public void edit(){
			this.edit(this.nameText);
	}

	@Override
	public void edit(final GfxObject editedGfxObject){
		if(!editedGfxObject.equals(this.nameText)){
			this.edit();
			return;
		}
		final UseCasePartNameFieldEditor editor = new UseCasePartNameFieldEditor(this.canvas,this);
		String edited;
			edited = this.uMLusecase.getName();
		editor.startEdition(edited, (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), this.nodeArtifact.getLocation().getY() + GfxManager.getPlatform().getLocationFor(editedGfxObject).getY(),
				this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
						- OptionsManager.get("RectangleLeftPadding"), false, false);
	}

	public String getUseCaseName(){
		return this.uMLusecase.getName();
	}

	@Override
	public MenuBarAndTitle getRightMenu(){
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(NAME.getMessage());
		rightMenu.addItem(EDIT_NAME.getMessage(), this.editCommand(this.nameText));
		return rightMenu;
	}

	@Override
	public int getWidth(){
		return this.width;
	}

	public void setUseCaseName(final String usecaseName) {
		this.uMLusecase.setName(usecaseName);
	}

	@Override
	public int getHeight(){
		return this.height;
	}

	@Override
	public int[] getOpaque(){
		return null;
	}

	/**
	 * Getter for the outline of an artifact. <br>
	 * The outline is what is been drawn during drag and drop
	 *
	 * @return The graphical object of this outline
	 */
	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final GfxObject ellip = GfxManager.getPlatform().buildEllipse(this.nodeWidth, this.getHeight());
		GfxManager.getPlatform().setStrokeStyle(ellip, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(ellip, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(ellip, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, ellip);
		return vg;
	}

	@Override
	public String toURL(){
		return this.getUseCaseName();
}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.nameEllip, ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width){
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.nameEllip, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				UseCasePartNameArtifact.this.edit(gfxo);
			}
		};
	}

}