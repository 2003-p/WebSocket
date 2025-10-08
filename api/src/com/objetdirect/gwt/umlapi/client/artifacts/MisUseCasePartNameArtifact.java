package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.MisUseCasePartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisUseCase;
import com.objetdirect.tatami.client.gfx.Color;

public class MisUseCasePartNameArtifact extends NodePartArtifact{

	private final UMLMisUseCase uMLmisusecase;
	private GfxObject nameEllip;
	private GfxObject nameText;
	private String stereotype="Architecture";
	private GfxObject stereotypeText;

	public MisUseCasePartNameArtifact(final String misusecaseName){
		super();
		this.uMLmisusecase = new UMLMisUseCase(misusecaseName);
		this.stereotype = stereotype.equals("") ? "" : "«" + stereotype + "»";
		this.height = 0;
		this.width = 0;
	}


    @Override
    public void buildGfxObject(){
    	if(this.textVirtualGroup == null){
    		this.computeBounds();
    	}
    	this.nameEllip = GfxManager.getPlatform().buildEllipse(this.nodeWidth,this.height);
    	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.nameEllip);
     	GfxManager.getPlatform().setFillColor(nameEllip, ThemeManager.getTheme().getDirectionPanelPressedColor());//getTheme()==NORMAL koiao
//    	GfxManager.getPlatform().setFillColor(nameEllip, ThemeManager.getTheme().getClassForegroundColor());//getTheme()==NORMAL　黒　楕円の中身

    	GfxManager.getPlatform().setStroke(this.nameEllip, ThemeManager.getTheme().getClassForegroundColor(), 1);//線の太さ

    	GfxManager.getPlatform().translate(
				this.nameText,
				new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
						.get("TextLeftPadding")) / 2, OptionsManager.get("EllipseTopPadding")));
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
//		new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
//				.get("TextLeftPadding")) / 2, OptionsManager.get("EllipseTopPadding")));
//GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
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
		if ((this.stereotype != null) && (this.stereotype != "")) {
			this.stereotypeText = GfxManager.getPlatform().buildText(this.stereotype,
					new Point(OptionsManager.get("MisEllipseTextLeftPadding"), OptionsManager.get("EllipseTopPadding")));
			GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.stereotypeText);
			GfxManager.getPlatform().setFont(this.stereotypeText, OptionsManager.getFont());
			GfxManager.getPlatform().setStroke(this.stereotypeText, ThemeManager.getTheme().getClassBackgroundColor(), 0);
			GfxManager.getPlatform().setFillColor(this.stereotypeText, ThemeManager.getTheme().getClassForegroundColor());
			this.width = GfxManager.getPlatform().getTextWidthFor(this.stereotypeText);
			this.height = GfxManager.getPlatform().getTextHeightFor(this.stereotypeText);
			this.width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		}
		this.nameText = GfxManager.getPlatform().buildText(this.uMLmisusecase.getName(),
				new Point(OptionsManager.get("MisEllipseTextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
		GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.nameText);
		GfxManager.getPlatform().setFont(this.nameText, OptionsManager.getFont());
		//Tatamiとgwt.wedgetのColorの相性が悪いから消す　GfxManager.getPlatform().setColor(this.nameText, Color.RED);//to complete method inTatamiGfxPlatform
		GfxManager.getPlatform().setStroke(this.nameText, ThemeManager.getTheme().getClassBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(this.nameText, ThemeManager.getTheme().getClassForegroundColor());
		final int thisNameWidth = GfxManager.getPlatform().getTextWidthFor(this.nameText) + OptionsManager.get("TextRightPadding")
				+ OptionsManager.get("TextLeftPadding");
		this.width = thisNameWidth > this.width ? thisNameWidth : this.width;
		this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
		this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

	}

	@Override
	public void edit(){
		if ((this.stereotype == null) || this.stereotype.equals("")) {
			this.stereotype = "«Architecture»";
			this.nodeArtifact.rebuildGfxObject();
			this.edit(this.stereotypeText);
		} else {
			this.edit(this.nameText);
		}
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final boolean isTheStereotype = editedGfxObject.equals(this.stereotypeText);
		if (!isTheStereotype && !editedGfxObject.equals(this.nameText)) {
			this.edit();
			return;
		}
		final MisUseCasePartNameFieldEditor editor = new MisUseCasePartNameFieldEditor(this.canvas, this, isTheStereotype);
		String edited;
		if (isTheStereotype) {
			edited = this.stereotype.replaceAll("»", "").replaceAll("«", "");
		} else {
			edited = this.uMLmisusecase.getName();
		}
		editor.startEdition(edited, (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), this.nodeArtifact.getLocation().getY() + GfxManager.getPlatform().getLocationFor(editedGfxObject).getY(),
				this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
						- OptionsManager.get("RectangleLeftPadding"), false, false);
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(NAME.getMessage());
		rightMenu.addItem(EDIT_NAME.getMessage(), this.editCommand(this.nameText));
		if (this.stereotypeText == null) {
			rightMenu.addItem(ADD_STEREOTYPE.getMessage(), this.createStereotype());
		} else {
			rightMenu.addItem(EDIT_STEREOTYPE.getMessage(), this.editCommand(this.stereotypeText));
			rightMenu.addItem(DELETE_STEREOTYPE.getMessage(), this.deleteStereotype());
		}

		return rightMenu;
	}

	public String getMisUseCaseName() {
		return this.uMLmisusecase.getName();
	}

	@Override
	public int getWidth(){
		return this.width;
	}

	public String getStereotype() {
		return this.stereotype.replaceAll("»", "").replaceAll("«", "");
	}

	public void setStereotype(final String stereotype) {
		this.stereotype = stereotype;
	}

	public void setMisUseCaseName(final String misusecaseName) {
		this.uMLmisusecase.setName(misusecaseName);
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
		return this.getMisUseCaseName() + "!" + this.getStereotype();
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

	private Command deleteStereotype() {
		return new Command() {
			public void execute() {
				MisUseCasePartNameArtifact.this.stereotype = null;
				MisUseCasePartNameArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command createStereotype() {
		return new Command() {
			public void execute() {
				MisUseCasePartNameArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				MisUseCasePartNameArtifact.this.edit(gfxo);
			}
		};
	}


}