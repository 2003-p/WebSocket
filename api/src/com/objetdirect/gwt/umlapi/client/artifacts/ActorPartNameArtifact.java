
package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.ActorPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLActor;


public class ActorPartNameArtifact extends NodePartArtifact {

	private final UMLActor	uMLactor;
	private GfxObject		nameRect;
	private GfxObject		nameText;

	public ActorPartNameArtifact(final String actorName) {
		this(actorName, "");
	}

	public ActorPartNameArtifact(final String actorName, final String stereotype) {
		super();
		this.uMLactor = new UMLActor(actorName);
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

		this.nameText = GfxManager.getPlatform().buildText(this.uMLactor.getName(),
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
		final ActorPartNameFieldEditor editor = new ActorPartNameFieldEditor(this.canvas, this);
		String edited;
			edited = this.uMLactor.getName();
		editor.startEdition(edited, (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), this.nodeArtifact.getLocation().getY() + GfxManager.getPlatform().getLocationFor(editedGfxObject).getY(),
				this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
						- OptionsManager.get("RectangleLeftPadding"), false, false);
	}

	public String getActorName() {
		return this.uMLactor.getName();
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

	public void setActorName(final String actorName) {
		this.uMLactor.setName(actorName);
	}

	@Override
	public String toURL() {
		return this.getActorName();
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

	private Command deleteStereotype() {
		return new Command() {
			public void execute() {
				ActorPartNameArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ActorPartNameArtifact.this.edit(gfxo);
			}
		};
	}

}
