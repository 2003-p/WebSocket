package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UseAndSecurityUseRelationLinkArtifact extends RelationLinkArtifact {
	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	protected enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	protected GfxObject											arrowVirtualGroup;
	protected UseCaseArtifact									useCaseArtifact;
	protected GfxObject											line;
	protected SecurityUseCaseArtifact						securityUseCaseArtifact;
	protected GfxObject											textVirtualGroup;
	private final HashMap<RelationLinkArtifactPart, GfxObject>	gfxObjectPart	= new HashMap<RelationLinkArtifactPart, GfxObject>();
	private int current_delta;

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 *
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public UseAndSecurityUseRelationLinkArtifact(final UseCaseArtifact left, final SecurityUseCaseArtifact right, final LinkKind relationKind) {
		super(left, right, relationKind);
		if (relationKind != LinkKind.INSTANTIATION) {
			Logger.getGlobal().severe("Making a instantiation relation artifact for : " + relationKind.getName());
		}
		this.relation = new UMLRelation(relationKind);
		this.useCaseArtifact = left;
		left.addDependency(this, right);
		this.securityUseCaseArtifact = right;
		right.addDependency(this, left);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final RelationLinkArtifactPart editPart = RelationLinkArtifactPart.getPartForGfxObject(editedGfxObject);
		if (editPart == null) {
			this.edit(RelationLinkArtifactPart.NAME);
		} else {
			final RelationFieldEditor editor = new RelationFieldEditor(this.canvas, this, editPart);
			editor.startEdition(editPart.getText(this.relation), GfxManager.getPlatform().getLocationFor(editedGfxObject).getX(), GfxManager.getPlatform()
					.getLocationFor(editedGfxObject).getY(), GfxManager.getPlatform().getTextWidthFor(editedGfxObject)
					+ OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	public void edit(final RelationLinkArtifactPart part) {
		String defaultText;
		switch (part) {
		case NAME:
			final String name = part.getText(this.relation);
			if ((name == null) || name.equals("")) {
				defaultText = this.useCaseArtifact.getName() + "-" + this.securityUseCaseArtifact.getName();
			} else {
				defaultText = name;
			}
			break;
		case LEFT_CARDINALITY:
		case RIGHT_CARDINALITY:
			defaultText = "0..*";
			break;
		case LEFT_CONSTRAINT:
		case RIGHT_CONSTRAINT:
			defaultText = "{union}";
			break;
		case LEFT_ROLE:
		case RIGHT_ROLE:
			defaultText = "role";
			break;
		default:
			defaultText = "?";

		}
		part.setText( this.relation, defaultText);
		this.rebuildGfxObject();
		this.edit(this.gfxObjectPart.get(part));
	}

	/**
	 * Getter for the left {@link ClassArtifact} of this relation
	 *
	 * @return the left {@link ClassArtifact} of this relation
	 */
	public UseCaseArtifact getUseCaseArtifact() {
		return this.useCaseArtifact;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 *
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public SecurityUseCaseArtifact getSecurityUseCaseArtifact() {
		return this.securityUseCaseArtifact;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(this.relation.getLinkKind().getNameInMenu() + " " + this.useCaseArtifact.getName() + " "
				+ this.relation.getLeftAdornment().getShape().getIdiom() + "-" + this.relation.getRightAdornment().getShape().getIdiom(true) + " "
				+ this.securityUseCaseArtifact.getName());
		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		this.useCaseArtifact.removeDependency(this);
		this.securityUseCaseArtifact.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "UseAndSecurityUseRelationLink$<" + this.useCaseArtifact.getId() + ">!<" + this.securityUseCaseArtifact.getId() + ">!" + this.relation.getLinkKind().getName()
				+ "!" + this.relation.getName() + "!" + this.relation.getLinkStyle().getName() + "!" + this.relation.getLeftAdornment().getName() + "!"
				+ this.relation.getLeftCardinality() + "!" + this.relation.getLeftConstraint() + "!" + this.relation.getLeftRole() + "!"
				+ this.relation.getRightAdornment().getName() + "!" + this.relation.getRightCardinality() + "!" + this.relation.getRightConstraint() + "!"
				+ this.relation.getRightRole();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(this.arrowVirtualGroup, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		Point curveControl = Point.getOrigin();

		this.gfxObjectPart.clear();
		ArrayList<Point> linePoints = new ArrayList<Point>();
		final boolean isComputationNeededOnLeft = this.relation.getLeftAdornment() != LinkAdornment.NONE;
		final boolean isComputationNeededOnRight = this.relation.getRightAdornment() != LinkAdornment.NONE;

		if (isComputationNeededOnLeft && isComputationNeededOnRight) {
			linePoints = GeometryManager.getPlatform().getLineBetween(this.useCaseArtifact, this.securityUseCaseArtifact);
			this.leftPoint = linePoints.get(0);
			this.rightPoint = linePoints.get(1);
		} else if (isComputationNeededOnLeft) {
			this.rightPoint = this.securityUseCaseArtifact.getCenter();
			this.leftPoint = GeometryManager.getPlatform().getPointForLine(this.useCaseArtifact, this.rightPoint);
		} else if (isComputationNeededOnRight) {
			this.leftPoint = this.useCaseArtifact.getCenter();
			this.rightPoint = GeometryManager.getPlatform().getPointForLine(this.useCaseArtifact, this.leftPoint);
		} else {
			this.leftPoint = this.useCaseArtifact.getCenter();
			this.rightPoint = this.securityUseCaseArtifact.getCenter();
		}
		if (this.order == 0) {
			this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		} else {
			int factor = 50 * ((this.order + 1) / 2);
			factor *= (this.order % 2) == 0 ? -1 : 1;
			curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint, factor);
			this.line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(this.line, this.leftPoint);
			GfxManager.getPlatform().curveTo(this.line, this.rightPoint, curveControl);
			GfxManager.getPlatform().setOpacity(this.line, 0, true);
		}

		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(this.line, this.relation.getLinkStyle().getGfxStyle());
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);

		// Making arrows group :
		this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.arrowVirtualGroup);
		if (isComputationNeededOnLeft) {
			GfxManager.getPlatform().addToVirtualGroup(
					this.arrowVirtualGroup,
					this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.leftPoint, this.rightPoint, this.relation.getLeftAdornment())
							: GeometryManager.getPlatform().buildAdornment(this.leftPoint, curveControl, this.relation.getLeftAdornment()));
		}
		if (isComputationNeededOnRight) {
			GfxManager.getPlatform().addToVirtualGroup(
					this.arrowVirtualGroup,
					this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.rightPoint, this.leftPoint, this.relation.getRightAdornment())
							: GeometryManager.getPlatform().buildAdornment(this.rightPoint, curveControl, this.relation.getRightAdornment()));
		}

		// Making the text group :
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
		Logger.getGlobal().info("Creating name");
		Point linkMiddle = Point.getMiddleOf(this.leftPoint, this.rightPoint);
		if (this.order != 0) {
			linkMiddle = Point.getMiddleOf(curveControl, linkMiddle);
		}
		final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(this.relation.getName(), linkMiddle);
		//		final GfxObject nameGfxObject = GfxManager.getPlatform().buildText("«include»", linkMiddle);
		GfxManager.getPlatform().setFont(nameGfxObject, OptionsManager.getSmallFont());
		GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, nameGfxObject);
		GfxManager.getPlatform().setStroke(nameGfxObject, ThemeManager.getTheme().getInstantiationBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getTheme().getInstantiationForegroundColor());
		GfxManager.getPlatform().translate(nameGfxObject, new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));
		RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationLinkArtifactPart.NAME);
		this.gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);

		GfxManager.getPlatform().moveToBack(this.gfxObject);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(this.arrowVirtualGroup, ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
	}


	private Command changeToCommand(final UMLRelation linkRelation, final LinkKind relationKind) {
		return new Command() {
			public void execute() {
				linkRelation.setLinkKind(relationKind);
				linkRelation.setLinkStyle(relationKind.getDefaultLinkStyle());
				linkRelation.setLeftAdornment(relationKind.getDefaultLeftAdornment());
				linkRelation.setRightAdornment(relationKind.getDefaultRightAdornment());
				UseAndSecurityUseRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	private Command createCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				UseAndSecurityUseRelationLinkArtifact.this.edit(relationArtifactPart);
			}
		};
	}

	private GfxObject createText(final String text, final RelationLinkArtifactPart part) {
		final GfxObject textGfxObject = GfxManager.getPlatform().buildText(text, Point.getOrigin());
		GfxManager.getPlatform().setFont(textGfxObject, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(textGfxObject, ThemeManager.getTheme().getClassRelationBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(textGfxObject, ThemeManager.getTheme().getClassRelationForegroundColor());

		if (part.isLeft()) {
			GfxManager.getPlatform().translate(
					textGfxObject,
					Point.add(this.useCaseArtifact.getCenter(), new Point(OptionsManager.get("ArrowWidth") / 2 + OptionsManager.get("TextLeftPadding"),
							-(this.useCaseArtifact.getHeight() + OptionsManager.get("ReflexivePathYGap")) / 2 + this.current_delta)));
		} else {
			GfxManager.getPlatform().translate(
					textGfxObject,
					Point.add(this.securityUseCaseArtifact.getLocation(), new Point(this.securityUseCaseArtifact.getWidth() + OptionsManager.get("ReflexivePathXGap")
							+ OptionsManager.get("TextLeftPadding"), this.current_delta)));
		}
		this.current_delta += 8;

		RelationLinkArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
		this.gfxObjectPart.put(part, textGfxObject);
		return textGfxObject;
	}

	private Command deleteCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				relationArtifactPart.setText(UseAndSecurityUseRelationLinkArtifact.this.relation, "");
				UseAndSecurityUseRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	private Command editCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				UseAndSecurityUseRelationLinkArtifact.this.edit(UseAndSecurityUseRelationLinkArtifact.this.gfxObjectPart.get(relationArtifactPart));
			}
		};
	}

	private Command reverseCommand(final UMLRelation linkRelation) {
		return new Command() {
			public void execute() {
				linkRelation.reverse();
				UseAndSecurityUseRelationLinkArtifact.this.rebuildGfxObject();
			}

		};
	}

	private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft) {
		return new Command() {
			public void execute() {
				if (isLeft) {
					relation.setLeftAdornment(LinkAdornment.NONE);
				} else {
					relation.setRightAdornment(LinkAdornment.NONE);
				}
				UseAndSecurityUseRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft, final boolean isNavigable) {
		return new Command() {
			public void execute() {
				final LinkAdornment adornment = isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS;
				if (isLeft) {
					relation.setLeftAdornment(adornment);
				} else {
					relation.setRightAdornment(adornment);
				}
				UseAndSecurityUseRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}


	/**
	 * Reset the navigability of the left side to unknown <br />
	 * The left side must not be a generalization, realization, aggregation or composition otherwise this method do nothing
	 */
	public void resetLeftNavigability() {
		if (this.relation.getLeftAdornment().isNavigabilityAdornment()) {
			this.relation.setLeftAdornment(LinkAdornment.NONE);
		}
	}

	/**
	 * Reset the navigability of the right side to unknown <br />
	 * The right side must not be a generalization, realization, aggregation or composition otherwise this method do nothing
	 */
	public void resetRightNavigability() {
		if (this.relation.getRightAdornment().isNavigabilityAdornment()) {
			this.relation.setRightAdornment(LinkAdornment.NONE);
		}
	}

	/**
	 * Setter for the left and right cardinalities in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param leftCardinality
	 *            The left cardinality text to be set
	 * @param rightCardinality
	 *            The right cardinality text to be set
	 */
	public void setCardinalities(final String leftCardinality, final String rightCardinality) {
		this.relation.setLeftCardinality(leftCardinality);
		this.relation.setRightCardinality(rightCardinality);
	}

	/**
	 * Setter for the relation left {@link LinkArtifact.LinkAdornment}
	 *
	 * @param leftAdornment
	 *            The left {@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setLeftAdornment(final LinkAdornment leftAdornment) {
		this.relation.setLeftAdornment(leftAdornment);
	}

	/**
	 * Setter for the leftCardinality in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param leftCardinality
	 *            The leftCardinality text to be set
	 */
	public void setLeftCardinality(final String leftCardinality) {
		this.relation.setLeftCardinality(leftCardinality);
	}

	/**
	 * Setter for the leftConstraint in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param leftConstraint
	 *            The leftConstraint text to be set
	 */
	public void setLeftConstraint(final String leftConstraint) {
		this.relation.setLeftConstraint(leftConstraint);
	}

	/**
	 * Set the state of left navigability <br />
	 * The left side must not be a generalization, realization, aggregation or composition otherwise this method do nothing <br />
	 * To set the unknown state see {@link ClassRelationLinkArtifact#resetLeftNavigability()}
	 *
	 * @param isNavigable
	 *            If true set the link's side to navigable otherwise set it to NOT navigable
	 *
	 */
	public void setLeftNavigability(final boolean isNavigable) {
		if (this.relation.getLeftAdornment().isNavigabilityAdornment()) {
			this.relation.setLeftAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
		}

	}

	/**
	 * Setter for the leftRole in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param leftRole
	 *            The leftRole text to be set
	 */
	public void setLeftRole(final String leftRole) {
		this.relation.setLeftRole(leftRole);
	}

	/**
	 * Setter for the relation {@link LinkArtifact.LinkStyle}
	 *
	 * @param linkStyle
	 *            The {@link LinkArtifact.LinkStyle} to be set
	 */
	public void setLinkStyle(final LinkStyle linkStyle) {
		this.relation.setLinkStyle(linkStyle);
	}

	/**
	 * Setter for the name in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param name
	 *            The name text to be set
	 */
	public void setName(final String name) {
		this.relation.setName(name);
	}

	public String getName() {
		return this.relation.getName();
	}

	/**
	 * Setter for the relation {@link LinkKind}
	 *
	 * @param relationKind
	 *            The {@link LinkKind} to be set
	 */
	public void setRelationKind(final LinkKind relationKind) {
		this.relation.setLinkKind(relationKind);
	}

	/**
	 * Setter for the relation right {@link LinkArtifact.LinkAdornment}
	 *
	 * @param rightAdornment
	 *            The right{@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setRightAdornment(final LinkAdornment rightAdornment) {
		this.relation.setRightAdornment(rightAdornment);
	}

	/**
	 * Setter for the rightCardinality in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param rightCardinality
	 *            The rightCardinality text to be set
	 */
	public void setRightCardinality(final String rightCardinality) {
		this.relation.setRightCardinality(rightCardinality);
	}

	/**
	 * Setter for the rightConstraint in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param rightConstraint
	 *            The rightConstraint text to be set
	 */
	public void setRightConstraint(final String rightConstraint) {
		this.relation.setRightConstraint(rightConstraint);
	}

	/**
	 * Set the state of right navigability <br />
	 * The right side must not be a generalization, realization, aggregation or composition otherwise this method do nothing <br />
	 * To set the unknown state see {@link ClassRelationLinkArtifact#resetRightNavigability()}
	 *
	 * @param isNavigable
	 *            If true set the link's side to navigable otherwise set it to NOT navigable
	 *
	 */
	public void setRightNavigability(final boolean isNavigable) {
		if (this.relation.getRightAdornment().isNavigabilityAdornment()) {
			this.relation.setRightAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
		}
	}

	/**
	 * Setter for the rightRole in {@link UMLRelation} This does not update the graphical object
	 *
	 * @param rightRole
	 *            The rightRole text to be set
	 */
	public void setRightRole(final String rightRole) {
		this.relation.setRightRole(rightRole);
	}

}

