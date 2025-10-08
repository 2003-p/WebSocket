
package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.MisActorPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisActor;


public class MisActorPartNameArtifact extends NodePartArtifact {

	private final UMLMisActor	uMLmisactor;
	private GfxObject		nameRect;
	private GfxObject		nameText;


	public MisActorPartNameArtifact(final String misactorName) {
		this(misactorName, "");
	}

	public MisActorPartNameArtifact(final String misactorName, final String stereotype) {
		super();
		this.uMLmisactor = new UMLMisActor(misactorName);
		this.height = 0;
		this.width = 0;
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.nameRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.nameRect);
		GfxManager.getPlatform().setFillColor(this.nameRect, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().setStroke(this.nameRect, ThemeManager.getTheme().getClassForegroundColor(), 1);

		// Centering name class :
		GfxManager.getPlatform().translate(
				this.nameText,
				new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
						.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));

		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
	}

	@Override
	public void computeBounds() {
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

		this.nameText = GfxManager.getPlatform().buildText(this.uMLmisactor.getName(),
				new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
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
	public void edit() {
			this.edit(this.nameText);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final MisActorPartNameFieldEditor editor = new MisActorPartNameFieldEditor(this.canvas, this);
		String edited;
			edited = this.uMLmisactor.getName();
		editor.startEdition(edited, (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), this.nodeArtifact.getLocation().getY() + GfxManager.getPlatform().getLocationFor(editedGfxObject).getY(),
				this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
						- OptionsManager.get("RectangleLeftPadding"), false, false);
	}

	public String getMisActorName() {
		return this.uMLmisactor.getName();
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int[] getOpaque() {
		return null;
	}

	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.getHeight());
		GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(rect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(rect, ThemeManager.getTheme().getClassBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, rect);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(NAME.getMessage());
		rightMenu.addItem(EDIT_NAME.getMessage(), this.editCommand(this.nameText));

		return rightMenu;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	public void setMisActorName(final String misactorName) {
		this.uMLmisactor.setName(misactorName);
	}

	@Override
	public String toURL() {
		return this.getMisActorName();
	}

	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.nameRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.nameRect, ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				MisActorPartNameArtifact.this.edit(gfxo);
			}
		};
	}

}
