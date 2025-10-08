/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.ActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.AssetAndMisUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.AssetArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.InstantiationRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkAssetArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkUseCaseRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisAndMisActorRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisAndSecurityUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCaseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.SecurityUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndActorRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndMisUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndSecurityUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCaseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.editors.FieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLActor;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAsset;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisActor;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisUseCase;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLSecurityUseCase;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLUseCase;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.AbstDrawReplaceAddDelete;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.NotHasAttributeElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.NotHasClassElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.NotHasClassTypeElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.NotHasMethodElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.NotHasParameterElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.ReplaceClassStereoType;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.ReplaceElements;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.SurplusAttributeElement;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.SurplusClassElement;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.SurplusClassTypeElement;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.SurplusMethodElement;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.SurplusParameterElement;
/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLCanvas extends AbsolutePanel {

	private enum DragAndDropState {
		TAKING, DRAGGING, NONE, PREPARING_SELECT_BOX, SELECT_BOX;
	}

	private static long										classCount						= 1;
	private static long										objectCount						= 1;
	private static long										lifeLineCount					= 1;
	private String											copyBuffer 						= "";
	public static WebSocketSender webSocketSender;
	private long											noteCount;
	private LinkKind										activeLinking;
	private Point											selectBoxStartPoint;
	private GfxObject										selectBox;
	private final Label										helpText						= new Label("");
	private final Widget									drawingCanvas;																	// Drawing canvas
	private DragAndDropState								dragAndDropState				= DragAndDropState.NONE;
	private boolean 										wasACopy;
	private Point											currentMousePosition			= new Point(-200, -200);						// In case of the

	private int												height									=GfxPlatform.DEFAULT_CANVAS_HEIGHT;
	private int												width									=GfxPlatform.DEFAULT_CANVAS_WIDTH;
	// mouse hasn't been
	// moved before
	// adding an
	// artifact
	private  Point										canvasOffset					= Point.getOrigin();
	private Point											duringDragOffset				= Point.getOrigin();
	private HashMap<UMLArtifact, ArrayList<Point>>			previouslySelectedArtifacts;

	private final UMLDiagram								uMLDiagram;
	private boolean											isMouseEnabled					= true;
	private final GfxObjectListener							gfxObjectListener				= new GfxObjectListener() {

		@Override
		public void mouseDoubleClicked(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.doubleClick(graphicObject, new Point(event
						.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

		@Override
		public void mouseMoved(final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.move(new Point(event.getClientX(), event
						.getClientY()), event.getButton(), event
						.getCtrlKey(), event.getAltKey(), event
						.getShiftKey(), event.getMetaKey());
			}
		}

		@Override
		public void mousePressed(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.press(graphicObject, new Point(
						event.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

		@Override
		public void mouseReleased(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.release(graphicObject, new Point(event
						.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

	};

	private boolean											mouseIsPressed					= false;										// Manage mouse
	// state when
	// releasing outside
	// the listener

	private final GfxObject									movingLines						= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									movingOutlineDependencies		= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									outlines						= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									allObjects						= GfxManager.getPlatform().buildVirtualGroup();

	private final Map<GfxObject, UMLArtifact>					objects							= new HashMap<GfxObject, UMLArtifact>();	// Map of
	// UMLArtifact with
	// corresponding
	// Graphical objects
	// (group)

	private final Set<UMLArtifact>							objectsToBeAddedWhenAttached	= new LinkedHashSet<UMLArtifact>();

	private final HashMap<UMLArtifact, ArrayList<Point>>	selectedArtifacts				= new HashMap<UMLArtifact, ArrayList<Point>>(); // Represent the
	// selected
	// UMLArtifacts and
	// his moving
	// dependencies
	// points

	private final ArrayList<GWTUMLEventListener>			uMLEventListenerList			= new ArrayList<GWTUMLEventListener>();
	private Point											dragOffset;
	private Point											totalDragShift					= Point.getOrigin();
	private GfxObject										arrowsVirtualGroup;
	private Point	copyMousePosition;

	// add Yamazaki
	private final String SURPLUS = "余剰";
	private final String NOTHAS = "欠損";
	private final String NOTHASSURPLUS = "欠損差分";

	/**
	 * Constructor of an {@link UMLCanvas} with default size
	 *
	 * @param uMLDiagram
	 *            The {@link UMLDiagram} this {@link UMLCanvas} is drawing
	 *
	 */
	public UMLCanvas(final UMLDiagram uMLDiagram) {
		super();
		Logger.getGlobal().info("Making Canvas");
		this.drawingCanvas = GfxManager.getPlatform().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH, GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		this.height = GfxPlatform.DEFAULT_CANVAS_HEIGHT;
		this.width  = GfxPlatform.DEFAULT_CANVAS_WIDTH;
		this.initCanvas();
		this.uMLDiagram = uMLDiagram;
	}

	/**
	 * Constructor of an {@link UMLCanvas} with the specified size
	 *
	 * @param uMLDiagram
	 *            The {@link UMLDiagram} this {@link UMLCanvas} is drawing
	 * @param width
	 *            The uml canvas width
	 * @param height
	 *            The uml canvas height
	 */
	public UMLCanvas(final UMLDiagram uMLDiagram, final int width, final int height) {
		super();
		Logger.getGlobal().info("Making " + width + " x " + height + " Canvas");
		this.drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height, ThemeManager.getTheme().getCanvasColor());
		this.drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");

		//TODO 20140714
		this.addUMLEventListener(new GWTUMLEventListenerForLog());

		this.setPixelSize(width, height);
		this.height = height;
		this.width = width;
		this.initCanvas();
		this.uMLDiagram = uMLDiagram;

		//TODO 20150531コメントアウト
//		Window.addWindowClosingHandler(new Window.ClosingHandler() {
//		    public void onWindowClosing(Window.ClosingEvent closingEvent) {
//		    	MyLoggerExecute.registEditEvent(-1,null, "CanvasClose",
//						null, -1, null, -1, -1,
//						null, null, null, null, UMLArtifact.getIdCount());
////				int preEventId, String editEvent, String eventType,
////				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
////				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
//		    }
//		});
		
	}

	/**
	 * Add an {@link UMLArtifact} to this canvas
	 *
	 * @param artifact
	 *            The {@link UMLArtifact} to add
	 */
	public void add(final UMLArtifact artifact) {
		if (artifact == null) {
			Logger.getGlobal().info("Adding null element to canvas");
			return;
		}
		if (this.isAttached()) {
			artifact.setCanvas(this);
			final long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToVirtualGroup(this.allObjects, artifact.initializeGfxObject());

			GfxManager.getPlatform().translate(artifact.getGfxObject(), artifact.getLocation());
			this.objects.put(artifact.getGfxObject(), artifact);
			
			Logger.getGlobal().info("([" + (System.currentTimeMillis() - t) + "ms]) to add " + artifact);
			System.out.println("add artifact into canvas");

		} else {
			Logger.getGlobal().info("Canvas not attached, queuing " + artifact);
			this.objectsToBeAddedWhenAttached.add(artifact);
		}
	}

	/**
	 * Add a new class with default values to this canvas to an the current mouse position
	 */
	public void addNewClass() {
		this.addNewClass(this.currentMousePosition);

	}

	/**
	 * Add a new lifeLine with default values to this canvas to the current mouse position
	 */
	public void addNewLifeLine() {
		this.addNewLifeLine(this.currentMousePosition);

	}

	/**
	 * Add a new object with default values to this canvas to the current mouse position
	 */
	public void addNewObject() {
		this.addNewObject(this.currentMousePosition);

	}

	/**
	 * Add an {@link GWTUMLEventListener} to this canvas
	 *
	 * @param uMLEventListener
	 *            The {@link GWTUMLEventListener} to add to this canvas
	 */
	public void addUMLEventListener(final GWTUMLEventListener uMLEventListener) {
		this.uMLEventListenerList.add(uMLEventListener);
	}



	/**
	 * Remove the direction arrows from the canvas
	 *
	 */
	public void clearArrows() {
		GfxManager.getPlatform().clearVirtualGroup(this.arrowsVirtualGroup);

	}

	/**
	 * Deselect an artifact and put it in the artifact list
	 *
	 * @param toDeselect
	 *            The artifact to be deselected
	 */
	public void deselectArtifact(final UMLArtifact toDeselect) {
		this.selectedArtifacts.remove(toDeselect);

		toDeselect.unselect();
		//MyLoggerExecute.registEditEvent("Deselect"+this.toUrl());
	}

	public void fromURL(final String url, final boolean isForPasting) {
		CanvasUrlManager cum = new CanvasUrlManager();
		cum.fromURL(url, isForPasting, wasACopy,
				this.currentMousePosition, this.copyMousePosition, this.canvasOffset, this);
	}

	/**
	 * Create a diagram from the URL {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 *
	 * @param url
	 *            The {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 *
	 * @param isForPasting True if the parsing is for copy pasting
	 *
	 */
	public void fromURL_old(final String url, final boolean isForPasting) {
		//try {
		if (!url.equals("AA==")) {
			String diagram = isForPasting ? url : GWTUMLDrawerHelper.decodeBase64(url);
			Point pasteShift = isForPasting ? Point.substract(Point.substract(this.currentMousePosition, this.copyMousePosition), this.canvasOffset) : Point.getOrigin();

			diagram = diagram.substring(0, diagram.lastIndexOf(";"));
			final String[] diagramArtifacts = diagram.split(";");

			for (final String artifactWithParameters : diagramArtifacts) {
				if (!artifactWithParameters.equals("")) {
					final String[] artifactAndParameters = artifactWithParameters.split("\\&");
					if (artifactAndParameters.length > 1) {
						final String[] artifactAndId = artifactAndParameters[0].split("]");
						final String[] parameters = artifactAndParameters[1].split("!", -1);
						final String artifact = artifactAndId[1];
						int id = 0;
						System.out.println("fromURL : "+artifactAndParameters[1]);
						try {
							id = Integer.parseInt(artifactAndId[0].replaceAll("[<>]", ""));
						} catch (final Exception ex) {
							Logger.getGlobal().severe("Parsing url, artifact id is NaN : " + artifactWithParameters + " : " + ex);
						}
						UMLArtifact newArtifact = null;
						if (artifact.equals("Class")) {
							newArtifact = new ClassArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLClass.parseNameOrStereotype(parameters[1]), UMLClass.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] classAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : classAttributes) {
									((ClassArtifact) newArtifact).addAttribute(UMLClassAttribute.parseAttribute(attribute));
								}
							}
							if (parameters[4].length() > 1) {
								final String[] classMethods = parameters[4].substring(0, parameters[4].lastIndexOf("%")).split("%");
								for (final String method : classMethods) {
									((ClassArtifact) newArtifact).addMethod(UMLClassMethod.parseMethod(method));
								}
							}
//TODO MisUc
						} else if (artifact.equals("Uc")) {
							newArtifact = new UseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLUseCase.parseNameOrStereotype(parameters[1]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("MisUc")) {
							newArtifact = new MisUseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLMisUseCase.parseNameOrStereotype(parameters[1]) , UMLMisUseCase.parseNameOrStereotype(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("SecurityUc")) {
							newArtifact = new SecurityUseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLSecurityUseCase.parseNameOrStereotype(parameters[1]) , UMLSecurityUseCase.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Actor")) {
							newArtifact = new ActorArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLActor.parseNameOrStereotype(parameters[1]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("MisActor")) {
							newArtifact = new MisActorArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLMisActor.parseNameOrStereotype(parameters[1]),  Integer.parseInt(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Asset")) {
							newArtifact = new AssetArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLAsset.parseNameOrStereotype(parameters[1]), UMLAsset.parseNameOrStereotype(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Object")) {
							newArtifact = new ObjectArtifact(UMLObject.parseName(parameters[1]).get(0), (isForPasting && wasACopy ? "CopyOf" : "") + UMLObject.parseName(parameters[1]).get(1),
									UMLObject.parseStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] objectAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : objectAttributes) {
									((ObjectArtifact) newArtifact).addAttribute(UMLObjectAttribute.parseAttribute(attribute));
								}
							}

						} else if (artifact.equals("LifeLine")) {
							newArtifact = new LifeLineArtifact((isForPasting && wasACopy ? "CopyOf" : "") + UMLLifeLine.parseName(parameters[1]).get(1), UMLLifeLine.parseName(parameters[1]).get(0));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Note")) {
							newArtifact = new NoteArtifact(parameters[1]);
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("LinkNote")) {
							Integer noteId = 0;
							Integer targetId = 0;
							try {
								noteId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkNoteArtifact((NoteArtifact) UMLArtifact.getArtifactById(noteId), UMLArtifact.getArtifactById(targetId));

						} else if (artifact.equals("LinkAsset")) {
							Integer assetId = 0;
							Integer targetId = 0;
							try {
								assetId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkAssetArtifact((AssetArtifact) UMLArtifact.getArtifactById(assetId), UMLArtifact.getArtifactById(targetId));

						}else if (artifact.equals("LinkClassRelation")) {
							Integer classId = 0;
							Integer relationId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkClassRelationArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ClassRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));

						}  else if (artifact.equals("LinkUseCaseRelation")) {
							Integer useCaseId = 0;
							Integer relationId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkUseCaseRelationArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(UseCaseRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));

						}  else if (artifact.equals("ClassRelationLink")) {
							Integer classLeftId = 0;
							Integer classRigthId = 0;
							try {
								classLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								classRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ClassRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classLeftId),
									(ClassArtifact) UMLArtifact.getArtifactById(classRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ClassRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ClassRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ClassRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ClassRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((ClassRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((ClassRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((ClassRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((ClassRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((ClassRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((ClassRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("UseCaseRelationLink")) {
							Integer useCaseLeftId = 0;
							Integer useCaseRigthId = 0;
							try {
								useCaseLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								useCaseRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseCaseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseLeftId),
									(UseCaseArtifact) UMLArtifact.getArtifactById(useCaseRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseCaseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseCaseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseCaseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseCaseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseCaseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseCaseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseCaseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("MisUseCaseRelationLink")) {
							Integer MisUseCaseLeftId = 0;
							Integer MisUseCaseRigthId = 0;
							try {
								MisUseCaseLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								MisUseCaseRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisUseCaseRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(MisUseCaseLeftId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(MisUseCaseRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("UseAndSecurityUseRelationLink")) {
							Integer useCaseId = 0;
							Integer securityUseCaseId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								securityUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndSecurityUseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(SecurityUseCaseArtifact) UMLArtifact.getArtifactById(securityUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("MisAndSecurityUseRelationLink")) {
							Integer misUseCaseId = 0;
							Integer securityUseCaseId = 0;
							try {
								misUseCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								securityUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisAndSecurityUseRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId),
									(SecurityUseCaseArtifact) UMLArtifact.getArtifactById(securityUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("UseAndActorRelationLink")) {
							Integer useCaseId = 0;
							Integer actorId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								actorId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndActorRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(ActorArtifact) UMLArtifact.getArtifactById(actorId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndActorRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndActorRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}  else if (artifact.equals("MisAndMisActorRelationLink")) {
							Integer misUseCaseId = 0;
							Integer misActorId = 0;
							try {
								misUseCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misActorId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisAndMisActorRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId),
									(MisActorArtifact) UMLArtifact.getArtifactById(misActorId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("AssetAndMisUseRelationLink")) {
							Integer assetId = 0;
							Integer misUseCaseId = 0;
							try {
								assetId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new AssetAndMisUseRelationLinkArtifact((AssetArtifact) UMLArtifact.getArtifactById(assetId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("UseAndMisUseRelationLink")) {
							Integer useCaseId = 0;
							Integer misUseCaseId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndMisUseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("ObjectRelationLink")) {
							Integer objectLeftId = 0;
							Integer objectRigthId = 0;
							try {
								objectLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ObjectRelationLinkArtifact((ObjectArtifact) UMLArtifact.getArtifactById(objectLeftId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ObjectRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ObjectRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ObjectRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ObjectRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("MessageLink")) {
							Integer lifeLineLeftId = 0;
							Integer lifeLineRigthId = 0;
							try {
								lifeLineLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								lifeLineRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MessageLinkArtifact((LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineLeftId),
									(LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MessageLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MessageLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MessageLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MessageLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("InstantiationRelationLink")) {
							Integer classId = 0;
							Integer objectId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new InstantiationRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectId), LinkKind.INSTANTIATION);
						}
						if (newArtifact != null) {
							newArtifact.setId(id);
							this.add(newArtifact);
						}
						if(isForPasting) {
							selectArtifact(newArtifact);

						}
					}
				}
			}
		}
		//} catch (final Exception ex) {
		//	Logger.getGlobal().severe("There was a problem reading diagram in url : ");
		//}
	}

	/**
	 * Return the first artifact found at the specified location
	 *
	 * @param location
	 *            The location to look for an artifact
	 *
	 * @return The first artifact found at the specified location or null if there is no artifact there
	 */
	public GfxObject getArtifactAt(final Point location) {
		for (final UMLArtifact artifact : this.objects.values()) {
			if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), location, location)) {
				Logger.getGlobal().info("Artifact : " + artifact + " found");
				return artifact.getGfxObject();
			}
		}
		Logger.getGlobal().info("No Artifact Found !");
		return null;
	}

	/**
	 * Getter for the canvasOffset
	 *
	 * @return the canvasOffset
	 */
	public Point getCanvasOffset() {
		return this.canvasOffset;
	}

	/**
	 * Getter for the graphic canvas
	 *
	 * @return The graphic canvas
	 */
	public Widget getDrawingCanvas() {
		return this.drawingCanvas;
	}

	/**
	 * Getter for the uMLDiagram
	 *
	 * @return the uMLDiagram
	 */
	public UMLDiagram getUMLDiagram() {
		return this.uMLDiagram;
	}

	/**
	 * Draw the directions arrow on the canvas
	 *
	 */
	public void makeArrows() {
		final int arrowSize = 6;
		int width = this.getOffsetWidth();
		int height = this.getOffsetHeight();
		this.arrowsVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.arrowsVirtualGroup, Point.getOrigin());
		final ArrayList<GfxObject> arrowList = new ArrayList<GfxObject>();
		for (float f = 0; f < 360; f += 45) {
			final GfxObject arrow = GfxManager.getPlatform().buildPath();
			arrowList.add(arrow);
			GfxManager.getPlatform().moveTo(arrow, Point.getOrigin());
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 0));
			GfxManager.getPlatform().lineTo(arrow, new Point(2 * arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(0, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, Point.getOrigin());
			GfxManager.getPlatform().setFillColor(arrow, ThemeManager.getTheme().getDefaultForegroundColor());
			GfxManager.getPlatform().setStroke(arrow, ThemeManager.getTheme().getDefaultForegroundColor(), 1);
			GfxManager.getPlatform().rotate(arrow, f, new Point(arrowSize, arrowSize));
			GfxManager.getPlatform().addToVirtualGroup(this.arrowsVirtualGroup, arrow);
		}

		GfxManager.getPlatform().translate(arrowList.get(0), new Point(width - 2 * arrowSize - 2, height / 2 - arrowSize - 2)); // right
		GfxManager.getPlatform().translate(arrowList.get(1), new Point(width - 2 * arrowSize - 2, height - 2 * arrowSize - 2)); // bottom right
		GfxManager.getPlatform().translate(arrowList.get(2), new Point(width / 2 - arrowSize - 2, height - 2 * arrowSize - 2)); // bottom
		GfxManager.getPlatform().translate(arrowList.get(3), new Point(2, height - 2 * arrowSize - 2)); // bottom left
		GfxManager.getPlatform().translate(arrowList.get(4), new Point(2, height / 2 - arrowSize - 2)); // left
		GfxManager.getPlatform().translate(arrowList.get(5), new Point(2, 2)); // up left
		GfxManager.getPlatform().translate(arrowList.get(6), new Point(width / 2 - arrowSize - 2, 2)); // up
		GfxManager.getPlatform().translate(arrowList.get(7), new Point(width - 2 * arrowSize - 2, 2)); // up right
	}

	/**
	 * Move all artifact in the specified {@link Direction}
	 *
	 * @param direction
	 *            The {@link Direction} to move the artifact by
	 * @param isRecursive
	 *            True if the method call is recursive
	 */
	public void moveAll(final Direction direction, final boolean isRecursive) {
		new Scheduler.Task("MovingAllArtifacts") {
			@Override
			public void process() {
				final Point translation = new Point(-direction.getXShift(), -direction.getYShift());
				GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, translation);
				UMLCanvas.this.canvasOffset.translate(translation);
				UMLCanvas.this.duringDragOffset.translate(translation);
				UMLCanvas.this.mouseMoved(UMLCanvas.this.currentMousePosition, false, false);
				GfxManager.getPlatform().translate(UMLCanvas.this.movingLines, translation);
				GfxManager.getPlatform().translate(UMLCanvas.this.movingOutlineDependencies, translation);
				if (FieldEditor.getEditField() != null) {
					UMLCanvas.this.setWidgetPosition(FieldEditor.getEditField(), (int) (FieldEditor.getEditField().getAbsoluteLeft() - direction.getXShift()),
							(int) (FieldEditor.getEditField().getAbsoluteTop() - direction.getYShift()));
				}
			}
		};

		if (isRecursive) {
			new Scheduler.Task("MovingAllArtifactsRecursive", 5) {
				@Override
				public void process() {
					UMLCanvas.this.moveAll(direction, true);
				}
			};
		}
	}

	/**
	 * Remove the specified artifact from the canvas
	 *
	 * @param umlArtifact
	 */
	//Removeが発生すると最初に呼ばれる。このメソッドの最後でRemoveArtifactsのログを取る
	//removeRecursiveから呼ばれる。
		public void remove(final UMLArtifact umlArtifact) {
			if (this.fireDeleteArtifactEvent(umlArtifact)) {
				this.removeRecursive(umlArtifact);
				if (umlArtifact.isALink()) {
					((LinkArtifact) umlArtifact).removeCreatedDependency();
				}
			}
		}
	private void removeRecursive(final UMLArtifact element) {
		GfxManager.getPlatform().removeFromVirtualGroup(this.allObjects, element.getGfxObject(), false);
		
		this.objects.remove(element.getGfxObject());
		UMLArtifact.removeArtifactById(element.getId());
		element.setCanvas(null);
		this.selectedArtifacts.remove(element);


		if (element.isALink()) {
			UMLArtifact right = ((LinkArtifact) element).getRightUMLArtifact();
			UMLArtifact left = ((LinkArtifact) element).getLeftUMLArtifact();
			MyLoggerExecute.registEditEvent(-1,element.toString(), "Remove",
					element.getClass().getName(), element.getId(), null, right.getId(), left.getId(),
					null, null, null, null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}else {
			MyLoggerExecute.registEditEvent(-1,element.toString(), "Remove",
					element.getClass().getName(), element.getId(), null, -1, -1,
					null, null, null, null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

		for (final Entry<LinkArtifact, UMLArtifact> entry : element.getDependentUMLArtifacts().entrySet()) {
			if (entry.getValue().isALink() && (entry.getKey().getClass() != LinkNoteArtifact.class)) {
				this.remove(entry.getValue());
			}
			entry.getValue().removeDependency(entry.getKey());
			this.removeRecursive(entry.getKey());
		}

	}

	//For Viewer clearCanvas
	public void removeSilent(final UMLArtifact umlArtifact) {
		
		if (this.fireDeleteArtifactEvent(umlArtifact)) {
			this.removeRecursive(umlArtifact);
			if (umlArtifact.isALink()) {
				((LinkArtifact) umlArtifact).removeCreatedDependency();
			}
		}
		//TODO takafumi


	}

	/**
	 *Remove the specified {@link GWTUMLEventListener} from this canvas
	 *
	 * @param uMLEventListener
	 *            The {@link GWTUMLEventListener} to remove from this canvas
	 */
	public void removeUMLEventListener(final GWTUMLEventListener uMLEventListener) {
		
		this.uMLEventListenerList.remove(uMLEventListener);
	}

	/**
	 * Select an artifact and put it in the artifact list
	 *
	 * @param toSelect
	 *            The artifact to be selected
	 */
	public void selectArtifact(final UMLArtifact toSelect) {
		this.selectedArtifacts.put(toSelect, new ArrayList<Point>());
		System.out.println("select:"+toSelect.toURL());
		toSelect.select(true);

	}

	/**
	 * Set the mouse state
	 *
	 * @param isMouseEnabled
	 *            True to enable Mouse <br />
	 *            False to disable Mouse
	 */
	public void setMouseEnabled(final boolean isMouseEnabled) {
		this.isMouseEnabled = isMouseEnabled;
	}

	/**
	 * This method calls {@link UMLArtifact#toURL()} on all artifacts of this canvas and concatenate it in a String separated by a semicolon
	 *
	 * @return The concatenated String from all {@link UMLArtifact#toURL()}
	 */
	public String toUrl() {
		
		final StringBuilder url = new StringBuilder();
		if (UMLArtifact.getArtifactList().isEmpty()){
			url.append("AA==");
		}
		else{
			for (final Entry<Integer, UMLArtifact> uMLArtifactEntry : UMLArtifact.getArtifactList().entrySet()) {
				final String artifactString = uMLArtifactEntry.getValue().toURL();
				if ((artifactString != null) && !artifactString.equals("")) {
					url.append("<");
					url.append(uMLArtifactEntry.getKey());
					url.append(">]");
					url.append(artifactString);
					url.append(";");
				}
			}
		}

		return GWTUMLDrawerHelper.encodeBase64(url.toString());
	}

	/**
	 * Add a new class with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewClass(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final ClassArtifact newClass = new ClassArtifact("Class" + ++UMLCanvas.classCount);
		if (this.fireNewArtifactEvent(newClass)) {
			this.helpText.setText("Adding a new class");
			this.add(newClass);
			newClass.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newClass.getGfxObject(), false, false);
			this.selectedArtifacts.put(newClass, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newClass.toString(), "Create",
					newClass.getClass().getName(), newClass.getId(), null, -1, -1,
					null, null, newClass.getLocation().getX()+","+newClass.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

		System.out.println("addNewClass");

	}

	/**
	 * Add a new life life with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial life line location
	 *
	 */
	void addNewLifeLine(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final LifeLineArtifact newLifeLine = new LifeLineArtifact("LifeLine" + ++UMLCanvas.lifeLineCount, "ll" + UMLCanvas.lifeLineCount);
		if (this.fireNewArtifactEvent(newLifeLine)) {
			this.helpText.setText("Adding a new life line");
			this.add(newLifeLine);
			newLifeLine.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newLifeLine.getGfxObject(), false, false);
			this.selectedArtifacts.put(newLifeLine, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
		}
	}

	void addNewLink(final UMLArtifact newSelected) {
		int linkOkCount = 0;
		for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
			final LinkArtifact newLink = LinkArtifact.makeLinkBetween(selectedArtifact, newSelected, this.activeLinking);

			if (newLink != null) {
				linkOkCount++;
			}
			this.add(newLink);

			//TODO takafumi
			System.out.println("addNewLink:"+newLink.toURL());
			MyLoggerExecute.registEditEvent(-1, newLink.toString(), "Create",
					newLink.getClass().getName(), newLink.getId(), null, selectedArtifact.getId(), newSelected.getId(),
					null, null, newLink.getLocation().getX()+","+newLink.getLocation().getY(), this.toUrl(), UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
		if (linkOkCount != 0) {
			this.linkingModeOff();
		}
		//System.out.println("addNewLink");
	}

	void addNewNote() {
		this.addNewNote(this.currentMousePosition);

	}

	void addNewNote(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final NoteArtifact newNote = new NoteArtifact("Note " + ++this.noteCount);
		if (this.fireNewArtifactEvent(newNote)) {
			this.helpText.setText("Adding a new note");
			this.add(newNote);
			newNote.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newNote.getGfxObject(), false, false);
			this.selectedArtifacts.put(newNote, new ArrayList<Point>());
			this.dragOffset = location;
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);

			MyLoggerExecute.registEditEvent(-1, newNote.toString(), "Create",
					newNote.getClass().getName(), newNote.getId(), null, -1, -1,
					null, null, newNote.getLocation().getX()+","+newNote.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
		//System.out.println("addNewNote");
	}

	/**
	 * Add a new object with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial object location
	 *
	 */
	void addNewObject(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final ObjectArtifact newObject = new ObjectArtifact("obj" + ++UMLCanvas.objectCount, "Object" + UMLCanvas.objectCount);
		if (this.fireNewArtifactEvent(newObject)) {
			this.helpText.setText("Adding a new object");
			this.add(newObject);
			newObject.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newObject.getGfxObject(), false, false);
			this.selectedArtifacts.put(newObject, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
		}
		//System.out.println("addNewObject");
	}

	//For MisuseCase
	void addNewUseCase(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final UseCaseArtifact newUseCase = new UseCaseArtifact("UseCase" + ++UMLCanvas.classCount);
		if (this.fireNewArtifactEvent(newUseCase)) {
			this.helpText.setText("Adding a new usecase");
			this.add(newUseCase);
			newUseCase.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newUseCase.getGfxObject(), false, false);
			this.selectedArtifacts.put(newUseCase, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 15, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewUseCase
			MyLoggerExecute.registEditEvent(-1, newUseCase.toString(), "Create",
					newUseCase.getClass().getName(), newUseCase.getId(), null, -1, -1,
					null, null, newUseCase.getLocation().getX()+","+newUseCase.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
	}
	/**
	 * Add a new misusecase with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewMisUseCase(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final MisUseCaseArtifact newMisUseCase = new MisUseCaseArtifact("MisUseCase" + ++UMLCanvas.classCount);
		if (this.fireNewArtifactEvent(newMisUseCase)) {
			this.helpText.setText("Adding a new misusecase");
			this.add(newMisUseCase);
			newMisUseCase.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newMisUseCase.getGfxObject(), false, false);
			this.selectedArtifacts.put(newMisUseCase, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 15, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewUseCase
			MyLoggerExecute.registEditEvent(-1, newMisUseCase.toString(), "Create",
					newMisUseCase.getClass().getName(), newMisUseCase.getId(), null, -1, -1,
					null, null, newMisUseCase.getLocation().getX()+","+newMisUseCase.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
	}
	/**
	 * Add a new asset with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewSecurityUseCase(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final SecurityUseCaseArtifact newSecurityUseCase = new SecurityUseCaseArtifact("SecurityUseCase" + ++UMLCanvas.classCount, "Architecture");
		if (this.fireNewArtifactEvent(newSecurityUseCase)) {
			this.helpText.setText("Adding a new securityusecase");
			this.add(newSecurityUseCase);
			newSecurityUseCase.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newSecurityUseCase.getGfxObject(), false, false);
			this.selectedArtifacts.put(newSecurityUseCase, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 15, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewUseCase
			MyLoggerExecute.registEditEvent(-1, newSecurityUseCase.toString(), "Create",
					newSecurityUseCase.getClass().getName(), newSecurityUseCase.getId(), null, -1, -1,
					null, null, newSecurityUseCase.getLocation().getX()+","+newSecurityUseCase.getLocation().getY(), null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
	}
	/**
	 * Add a new asset with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewAsset(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final AssetArtifact newAsset = new AssetArtifact("Asset" + ++UMLCanvas.classCount, "SecurityProperty");
		if (this.fireNewArtifactEvent(newAsset)) {
			this.helpText.setText("Adding a new asset");
			this.add(newAsset);
			newAsset.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newAsset.getGfxObject(), false, false);
			this.selectedArtifacts.put(newAsset, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newAsset.toString(), "Create",
					newAsset.getClass().getName(), newAsset.getId(), null, -1, -1,
					null, null, newAsset.getLocation().getX()+","+newAsset.getLocation().getY(), null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

	}

	/**
	 * Add a new actor with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewActor(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final ActorArtifact newActor = new ActorArtifact("Actor" + ++UMLCanvas.classCount);
		if (this.fireNewArtifactEvent(newActor)) {
			this.helpText.setText("Adding a new actor");
			this.add(newActor);
			newActor.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newActor.getGfxObject(), false, false);
			this.selectedArtifacts.put(newActor, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newActor.toString(), "Create",
					newActor.getClass().getName(), newActor.getId(), null, -1, -1,
					null, null, newActor.getLocation().getX()+","+newActor.getLocation().getY(), null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

	}

	/**
	 * Add a new misactor with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewMisActor(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final MisActorArtifact newMisActor = new MisActorArtifact("MisActor" + ++UMLCanvas.classCount,1);
		if (this.fireNewArtifactEvent(newMisActor)) {
			this.helpText.setText("Adding a new misactor");
			this.add(newMisActor);
			newMisActor.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newMisActor.getGfxObject(), false, false);
			this.selectedArtifacts.put(newMisActor, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newMisActor.toString(), "Create",
					newMisActor.getClass().getName(), newMisActor.getId(), null, -1, -1,
					null, null, newMisActor.getLocation().getX()+","+newMisActor.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

	}

	/**
	 * Add a new misExPrincipal with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewMisExPrincipal(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final MisActorArtifact newMisExPrincipal = new MisActorArtifact("MisExPrincipal" + ++UMLCanvas.classCount,2);
		if (this.fireNewArtifactEvent(newMisExPrincipal)) {
			this.helpText.setText("Adding a new misExPrincipal");
			this.add(newMisExPrincipal);
			newMisExPrincipal.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newMisExPrincipal.getGfxObject(), false, false);
			this.selectedArtifacts.put(newMisExPrincipal, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newMisExPrincipal.toString(), "Create",
					newMisExPrincipal.getClass().getName(), newMisExPrincipal.getId(), null, -1, -1,
					null, null, newMisExPrincipal.getLocation().getX()+","+newMisExPrincipal.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

	}
	/**
	 * Add a new misPrincipal with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewMisPrincipal(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final MisActorArtifact newMisPrincipal = new MisActorArtifact("MisPrincipal" + ++UMLCanvas.classCount,3);
		if (this.fireNewArtifactEvent(newMisPrincipal)) {
			this.helpText.setText("Adding a new misPrincipal");
			this.add(newMisPrincipal);
			newMisPrincipal.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newMisPrincipal.getGfxObject(), false, false);
			this.selectedArtifacts.put(newMisPrincipal, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newMisPrincipal.toString(), "Create",
					newMisPrincipal.getClass().getName(), newMisPrincipal.getId(), null, -1, -1,
					null, null, newMisPrincipal.getLocation().getX()+","+newMisPrincipal.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

	}







	void cut() {
		this.copy();
		this.wasACopy = false;
		this.removeSelected();

		System.out.println("Cut");
	}
	void copy() {
		if(this.selectedArtifacts.isEmpty()) return;
		this.wasACopy = true;
		final StringBuilder url = new StringBuilder();
		Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		StringBuilder artifacts = new StringBuilder();//

		for (final Entry<Integer, UMLArtifact> uMLArtifactEntry : UMLArtifact.getArtifactList().entrySet()) {
			if(this.selectedArtifacts.containsKey(uMLArtifactEntry.getValue())) {
				final String artifactString = uMLArtifactEntry.getValue().toURL();
				if ((artifactString != null) && !artifactString.equals("")) {
					if(!uMLArtifactEntry.getValue().isALink()
							|| (this.selectedArtifacts.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getLeftUMLArtifact())
									&& this.selectedArtifacts.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getRightUMLArtifact()))) {
						url.append("<");
						url.append(uMLArtifactEntry.getKey());
						url.append(">]");
						url.append(artifactString);
						url.append(";");

						artifacts.append("<");
						artifacts.append(uMLArtifactEntry.getKey());
						artifacts.append(">]");
						artifacts.append(artifactString);
						artifacts.append(";");

						if(!uMLArtifactEntry.getValue().isALink()) {
							lower = Point.min(lower, uMLArtifactEntry.getValue().getCenter());
							higher = Point.max(higher, uMLArtifactEntry.getValue().getCenter());
						}
					}
				}
			}
		}
		this.copyBuffer = url.toString();
		this.copyMousePosition = Point.getMiddleOf(lower, higher);


		System.out.println("Copy");
		MyLoggerExecute.registEditEvent(-1, artifacts.toString(), "Copy",
				artifacts.toString(), -1, null, -1, -1,
				null, null, null, null, UMLArtifact.getIdCount());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		//TODO
		//MyLoggerExecute.registEditEvent("Copy:"+selectedArtifact.toURL(),this.toUrl());
	}
	void paste() {
		if(!this.copyBuffer.equals("") && this.dragAndDropState == DragAndDropState.NONE) {
			// Getting ids :
			deselectAllArtifacts();
			LinkedList<Integer> oldIds = new LinkedList<Integer>();
			String[] slices = this.copyBuffer.split(">");
			for (String stringId : slices) {
				if(stringId.contains("<")) {
					oldIds.add(Integer.parseInt(stringId.split("<")[1]));
				}
			}
			Collections.sort(oldIds);
			// Creating new Ids
			for (Integer oldId : oldIds) {
				this.copyBuffer = this.copyBuffer.replaceAll("<" + oldId + ">", "<" + (UMLArtifact.getIdCount() + oldIds.indexOf(oldId) + 1) + ">");
			}
			UMLArtifact.setIdCount(UMLArtifact.getIdCount() + oldIds.size() + 1);
			fromURL(this.copyBuffer, true);

			MyLoggerExecute.registEditEvent(-1, this.copyBuffer, "Paste",
					this.copyBuffer, -1, null, -1, -1,
					null, null, null, null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			this.dragOffset = this.currentMousePosition;

			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;
		}

		System.out.println("Paste");
	}
	boolean fireDeleteArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onDeleteUMLArtifact(umlArtifact) && isThisOk;
		}
		//System.out.println("Delete");
		Logger.getGlobal().info("Delete artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireEditArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onEditUMLArtifact(umlArtifact) && isThisOk;
		}
		Logger.getGlobal().info("New Artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireLinkKindChange(final LinkArtifact newLink, final LinkKind oldKind, final LinkKind newKind) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			isThisOk = listener.onLinkKindChange(newLink, oldKind, newKind) && isThisOk;
		}
		Logger.getGlobal().info("Link kind chage event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireNewArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
		}
		Logger.getGlobal().info("New Artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	void mouseDoubleClicked(final GfxObject gfxObject, final Point location) {
		this.editItem(gfxObject);
	}

	void mouseLeftPressed(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (this.mouseIsPressed) {
			return;
		}
		this.duringDragOffset = Point.getOrigin();
		final Point realPoint = this.convertToRealPoint(location);
		this.mouseIsPressed = true;
		if (this.dragAndDropState == DragAndDropState.DRAGGING) {
			return;
		}
		if (this.activeLinking == null) {
			if (gfxObject != null) {
				this.dragAndDropState = DragAndDropState.TAKING;
				this.dragOffset = realPoint.clonePoint();
				CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			} else {
				this.selectBoxStartPoint = realPoint.clonePoint();
				this.dragAndDropState = DragAndDropState.PREPARING_SELECT_BOX;
			}
		}
		this.doSelection(gfxObject, isCtrlDown, isShiftDown);
	}

	@SuppressWarnings("fallthrough")
	void mouseMoved(final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		final Point realPoint = this.convertToRealPoint(location);
		if (!this.helpText.getText().equals("")) {
			this.setWidgetPosition(this.helpText, realPoint.getX() + 5, realPoint.getY() - this.helpText.getOffsetHeight() - 5);
		}
		this.currentMousePosition = realPoint;
		switch (this.dragAndDropState) {
			case TAKING:
				this.take();
				UMLCanvas.this.dragAndDropState = DragAndDropState.DRAGGING;
			case DRAGGING:
				this.drag(realPoint);
				break;
			case PREPARING_SELECT_BOX:
				this.dragAndDropState = DragAndDropState.SELECT_BOX;
				this.previouslySelectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			case SELECT_BOX:
				this.boxSelector(Point.add(this.selectBoxStartPoint, this.duringDragOffset), realPoint, isCtrlDown, isShiftDown);
		}

		if ((this.activeLinking != null) && !this.selectedArtifacts.isEmpty()) {
			this.animateLinking(realPoint);

		}
	}

	@SuppressWarnings("fallthrough")
	void mouseReleased(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (!this.mouseIsPressed) {
			return;
		}
		final Point realPoint = this.convertToRealPoint(location);
		this.mouseIsPressed = false;

		if (this.dragAndDropState == DragAndDropState.TAKING) {
			this.unselectOnRelease(gfxObject, isCtrlDown, isShiftDown);
		}
		switch (this.dragAndDropState) {
			case SELECT_BOX:
				if (this.selectBox != null) {
					GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.selectBox);
				}
				this.dragAndDropState = DragAndDropState.NONE;
				break;
			case DRAGGING:
				this.drop(realPoint);
			case TAKING:
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				this.helpText.setText("");
			default:
				this.dragAndDropState = DragAndDropState.NONE;
		}
	}

	void mouseRightPressed(final GfxObject gfxObject, final Point location) {
		final Point realPoint = this.convertToRealPointForContextMenu(location);
		this.dropRightMenu(gfxObject, realPoint);
	}

	void moveSelected(final Direction direction) {
		if (!this.selectedArtifacts.isEmpty()) {
			Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				if(!selectedArtifact.isALink()) {
					lower = Point.min(lower, Point.add(selectedArtifact.getLocation(), new Point(direction.getXShift(), direction.getYShift())));
					higher = Point.max(higher, Point.add(Point.add(selectedArtifact.getLocation(), new Point(selectedArtifact.getWidth(), selectedArtifact.getHeight())), new Point(direction.getXShift(), direction.getYShift())));
				}
				if (selectedArtifact.isDraggable()) {
					new Scheduler.Task("MovingSelected") {
						@Override
						public void process() {
							selectedArtifact.moveTo(new Point(selectedArtifact.getLocation().getX() + direction.getXShift(),
									selectedArtifact.getLocation().getY() + direction.getYShift()));

							selectedArtifact.rebuildGfxObject();
							if (UMLCanvas.webSocketSender != null) {
							    String elementId = "element-" + selectedArtifact.getId();
							    int newX = selectedArtifact.getLocation().getX();
							    int newY = selectedArtifact.getLocation().getY();
							    String message = "{\"action\":\"move\", \"elementId\":\"" + elementId + "\", \"x\":" + newX + ", \"y\":" + newY + "}";
							    UMLCanvas.webSocketSender.send(message);
							}
							// dropメソッドの中のforループに追加

							// --- ここから追加 ---
							
							// --- ここまで追加 ---// ← これを何とかすれば赤のままになる ならなかった
						}
					};
				}
			}
			boolean mustShiftCanvas = false;
			switch(direction) {
				case UP:
					mustShiftCanvas = lower.getY() < -this.canvasOffset.getY();
					break;
				case DOWN:
					mustShiftCanvas = higher.getY() > -this.canvasOffset.getY() + this.getOffsetHeight();
					break;
				case LEFT:
					mustShiftCanvas = lower.getX() < -this.canvasOffset.getX();
					break;
				case RIGHT:
					mustShiftCanvas = higher.getX() > -this.canvasOffset.getX() + this.getOffsetWidth();
					break;
			}
			if(mustShiftCanvas) {
				moveAll(direction, false);
			}
		}
		System.out.println(this.toUrl());
		System.out.println("");
	}
	//public add saito
	public void rebuildAllLinks() {
		for (final UMLArtifact artifact : this.objects.values()) {
				artifact.rebuildGfxObject();
		}
	}

	// checnge Yamazaki 修飾子なしからpublicに変更
	public void removeSelected() {
		if (!this.selectedArtifacts.isEmpty()) {
			final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			for (final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
				this.remove(selectedArtifact);
			}
			//TODO takafumi
			//RemoveArtifactsのログをとる。
			MyLoggerExecute.registEditEvent(-1,"RemoveArtifacts", "RemoveArtifacts",
					null, -1, null, -1, -1,
					null, null, null, this.toUrl(), UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl

		}
	}

	void selectAll() {
		for (final UMLArtifact artifact : this.objects.values()) {
			this.selectArtifact(artifact);
		}
	}

	//public for drawer base
	public void toLinkMode(final LinkKind linkType) {
		this.activeLinking = linkType;
		final int selectedToLink = this.selectedArtifacts.keySet().size();

		this.helpText.setText("Adding " + (selectedToLink == 0 ? "a" : selectedToLink) + " new " + this.activeLinking.getName());

		CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
		//TODO menu visible=false

	}

	@Override
	protected void onAttach() {
		super.onAttach();
		Logger.getGlobal().info("Attaching");
	}

	@Override
	protected void onLoad() {
		
		super.onLoad();
		Logger.getGlobal().info("Loading");
		for (final UMLArtifact elementNotAdded : this.objectsToBeAddedWhenAttached) {
			Logger.getGlobal().info("Adding queued " + elementNotAdded);
			elementNotAdded.setCanvas(this);
			final long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToVirtualGroup(this.allObjects, elementNotAdded.initializeGfxObject());
			GfxManager.getPlatform().translate(elementNotAdded.getGfxObject(), elementNotAdded.getLocation());
			
			this.objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
			Logger.getGlobal().info("([" + (System.currentTimeMillis() - t) + "ms]) to add queued " + elementNotAdded);
		}
		
		this.objectsToBeAddedWhenAttached.clear();
		this.makeArrows();
		MyLoggerExecute.registEditEvent(-1, null, "CanvasLoad",
				null, -1, null, -1, -1,
				null, null, null, null, UMLArtifact.getIdCount());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

	}

	private void animateLinking(final Point location) {
		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				final GfxObject movingLine = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), Point.substract(location, this.canvasOffset));
				GfxManager.getPlatform().addToVirtualGroup(this.movingLines, movingLine);
				GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.LONGDASHDOTDOT);
			}
			GfxManager.getPlatform().setStroke(this.movingLines, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			GfxManager.getPlatform().moveToBack(this.movingLines);
		}
	}

	private void boxSelect(final UMLArtifact artifact, final boolean isSelecting) {
		if (isSelecting) {
			this.selectedArtifacts.put(artifact, new ArrayList<Point>());
			artifact.select(false);
		} else {
			this.selectedArtifacts.remove(artifact);
			artifact.unselect();
		}
	}

	private void boxSelector(final Point startPoint, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (this.selectBox != null) {
			GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.selectBox);
		}

		this.selectBox = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(this.selectBox, startPoint);
		GfxManager.getPlatform().lineTo(this.selectBox, new Point(location.getX(), startPoint.getY()));
		GfxManager.getPlatform().lineTo(this.selectBox, location);
		GfxManager.getPlatform().lineTo(this.selectBox, new Point(startPoint.getX(), location.getY()));
		GfxManager.getPlatform().lineTo(this.selectBox, startPoint);
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.selectBox, Point.getOrigin());
		GfxManager.getPlatform().setStroke(this.selectBox, ThemeManager.getTheme().getSelectBoxForegroundColor(), 2);
		GfxManager.getPlatform().setFillColor(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor());
		GfxManager.getPlatform().setOpacity(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor().getAlpha(), true);
		final Point min = Point.substract(Point.min(startPoint, location), this.canvasOffset);
		final Point max = Point.substract(Point.max(startPoint, location), this.canvasOffset);

		for (final UMLArtifact artifact : this.objects.values()) {
			if (artifact.isDraggable()) {
				if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), min, max)) {
					this.boxSelect(artifact, !(this.previouslySelectedArtifacts.containsKey(artifact) && isCtrlDown));
				} else {
					this.boxSelect(artifact, (isShiftDown || isCtrlDown) && this.previouslySelectedArtifacts.containsKey(artifact));
				}
			}
		}
	}

	private Point convertToRealPoint(final Point location) {
		return Point.add(location, new Point(RootPanel.getBodyElement().getScrollLeft() - this.getAbsoluteLeft(), RootPanel.getBodyElement().getScrollTop()
				- this.getAbsoluteTop()));
		//return Point.add(location, new Point(RootPanel.getBodyElement().getScrollLeft(), RootPanel.getBodyElement().getScrollTop()));
	}
	private Point convertToRealPointForContextMenu(final Point location) {
//		return Point.add(location, new Point(RootPanel.getBodyElement().getScrollLeft() - this.getAbsoluteLeft(), RootPanel.getBodyElement().getScrollTop()
//				- this.getAbsoluteTop()));
		return Point.add(location, new Point(RootPanel.getBodyElement().getScrollLeft(), RootPanel.getBodyElement().getScrollTop()));
	}

	private void deselectAllArtifacts() {

		for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		this.selectedArtifacts.clear();
	}

	private void doSelection(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {
		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		Logger.getGlobal().info("Selecting : " + newSelected + " (" + gfxObject + ")");
		// New selection is null -> deselecting all selected artifacts and disabling linking mode
		if (newSelected == null) {
			this.linkingModeOff();
			this.deselectAllArtifacts();
		} else { // New selection is not null
			if (this.selectedArtifacts.containsKey(newSelected)) { // New selection is already selected -> deselecting it if ctrl is down
				if (this.activeLinking != null) {// if linking mode on
					this.addNewLink(newSelected);
				}
				if (this.selectedArtifacts.size() != 1) { // New selection isn't the only one selected
					if (isCtrlKeyDown) { // If ctrl down deselecting only this one
						this.deselectArtifact(newSelected);
					}
				}
			} else { // New selection is not selected
				if (this.selectedArtifacts.isEmpty()) { // If nothing is selected -> selecting
					this.selectArtifact(newSelected);
				} else { // Other artifacts are selected
					if (this.activeLinking != null) {// if linking mode on
						this.addNewLink(newSelected);
					}
					if (!isCtrlKeyDown && !isShiftKeyDown) { // If selection is not greedy ->deselect all
						this.deselectAllArtifacts();
					}
					this.selectArtifact(newSelected);
				}
			}
		}
	}

	private void drag(final Point location) {
		final Point shift = Point.substract(location, this.dragOffset);
		this.totalDragShift.translate(shift);

		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);
		}
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			if (selectedArtifact.isDraggable()) {
				final Point outlineOfSelectedCenter = Point.substract(Point.add(selectedArtifact.getCenter(), this.totalDragShift), this.duringDragOffset);
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					for (final Point dependantArtifactCenter : selectedArtifactEntry.getValue()) {
						final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(outlineOfSelectedCenter, dependantArtifactCenter);
						GfxManager.getPlatform().addToVirtualGroup(this.movingOutlineDependencies, outlineDependency);
						GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
					}
					GfxManager.getPlatform().setStroke(this.movingOutlineDependencies, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
					GfxManager.getPlatform().moveToBack(this.movingOutlineDependencies);
				}
				GfxManager.getPlatform().setStroke(this.outlines, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			}
		}
		GfxManager.getPlatform().translate(this.outlines, shift);
		this.dragOffset = location.clonePoint();
	}

	private void drop(final Point location) {
		boolean placeFlag =false;//20141203
		for (final UMLArtifact selectedArtifact : UMLCanvas.this.selectedArtifacts.keySet()) {
			if (selectedArtifact.isDraggable()) {
				placeFlag=true;
				Point oldPoint = selectedArtifact.getLocation();
				selectedArtifact.moveTo(Point.substract(Point.add(selectedArtifact.getLocation(), this.totalDragShift), this.duringDragOffset));

				selectedArtifact.rebuildGfxObject();


				//TODO dropEvent
				//writeLog(selectedArtifact);
				//MyLogger.operationLog("DropArtifact"+":"+selectedArtifact.toURL());
				System.out.println("DropArtifact"+":"+selectedArtifact.toString());
				MyLoggerExecute.registEditEvent(-1, selectedArtifact.toString(), "Place",
						selectedArtifact.getClass().getName(), selectedArtifact.getId(), null, -1, -1,
						null, oldPoint.getX()+","+oldPoint.getY(), selectedArtifact.getLocation().getX()+","+selectedArtifact.getLocation().getY(), null, UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}
		this.totalDragShift = Point.getOrigin();
		this.duringDragOffset = Point.getOrigin();
		GfxManager.getPlatform().clearVirtualGroup(this.outlines);
		GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);

		if(placeFlag){
			MyLoggerExecute.registEditEvent(-1, "PlaceArtifacts", "Place",
					null, -1, null, -1, -1,
					null, null, null, this.toUrl(), UMLArtifact.getIdCount());
		}
		//TODO takafumi
		System.out.println(this.toString());

		//MyLoggerExecute.registEditEvent("DropArtifacts",this.toUrl());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

	}

	private void dropRightMenu(final GfxObject gfxObject, final Point location) {
		this.doSelection(gfxObject, false, false);
		final UMLArtifact elem = this.getUMLArtifact(gfxObject);
		ContextMenu contextMenu;
		if (elem != null) {
			contextMenu = new ContextMenu(location, this, elem.getRightMenu());
		} else {
			contextMenu = new ContextMenu(location, this);
		}
		contextMenu.show();

	}

	private void editItem(final GfxObject gfxObject) {
		Logger.getGlobal().info("Edit request on " + gfxObject);
		final UMLArtifact uMLArtifact = this.getUMLArtifact(gfxObject);
		if (uMLArtifact != null) {
			Logger.getGlobal().info("Edit started on " + uMLArtifact);
			if (this.fireEditArtifactEvent(uMLArtifact)) {
				//TODO takafumi

				//MyLoggerExecute.registEditEvent("EditStart:"+uMLArtifact.toURL(), this.toUrl());
//				System.out.println("EditStart:"+uMLArtifact.toURL());
				uMLArtifact.edit(gfxObject);
			}
		}
	}

	private UMLArtifact getUMLArtifact(final GfxObject gfxObject) {
		if (gfxObject == null) {
			Logger.getGlobal().info("No Object");
			return null;
		}
		GfxObject currentGfxObject = gfxObject;
		GfxObject gfxOParentGroup = GfxManager.getPlatform().getGroup(currentGfxObject);
		while ((gfxOParentGroup != null) && !gfxOParentGroup.equals(this.allObjects)) {
			currentGfxObject = gfxOParentGroup;
			gfxOParentGroup = GfxManager.getPlatform().getGroup(currentGfxObject);
		}
		final UMLArtifact UMLArtifact = this.objects.get(currentGfxObject);
		if (UMLArtifact == null) {
			Logger.getGlobal().info("Artifact not found");
		}
		return UMLArtifact;
	}

	private void initCanvas() {
		Logger.getGlobal().info("Adding Canvas");
		this.add(this.drawingCanvas, 0, 0);
		this.helpText.setStylePrimaryName("contextual-help");
		this.add(this.helpText, 0, 0);
		Logger.getGlobal().info("Adding object listener");
		GfxManager.getPlatform().addObjectListenerToCanvas(this.drawingCanvas, this.gfxObjectListener);
		this.noteCount = 0;
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.allObjects, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingLines, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.outlines, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingOutlineDependencies, Point.getOrigin());
		Logger.getGlobal().info("Init canvas done");
	}

	private boolean isIn(final Point artifactMin, final Point artifactMax, final Point selectMin, final Point selectMax) {
		return (selectMax.isSuperiorTo(artifactMin) && artifactMax.isSuperiorTo(selectMin));
	}

	private void linkingModeOff() {
		this.activeLinking = null;
		GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		this.helpText.setText("");
	}



	private void take() {
		GfxManager.getPlatform().translate(this.outlines, Point.substract(this.canvasOffset, GfxManager.getPlatform().getLocationFor(this.outlines)));
		final HashMap<UMLArtifact, UMLArtifact> alreadyAdded = new HashMap<UMLArtifact, UMLArtifact>();
		this.duringDragOffset = Point.getOrigin();
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			Scheduler.cancel("RebuildingDependencyFor" + selectedArtifact);
			if (selectedArtifact.isDraggable()) {
				final GfxObject outline = selectedArtifact.getOutline();
				GfxManager.getPlatform().addToVirtualGroup(this.outlines, outline);
				GfxManager.getPlatform().translate(outline, selectedArtifact.getLocation());
				selectedArtifact.destroyGfxObjectWhithDependencies();
				Logger.getGlobal().info("Adding outline for " + selectedArtifact);
				// Drawing lines only translating during with drag
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					selectedArtifactEntry.getValue().clear();

					for (final UMLArtifact dependantArtifact : selectedArtifact.getDependentUMLArtifacts().values()) {
						if (this.selectedArtifacts.containsKey(dependantArtifact)) {
							if ((alreadyAdded.get(selectedArtifact) == null) || !alreadyAdded.get(selectedArtifact).equals(dependantArtifact)) {
								final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(),
										dependantArtifact.getCenter());
								GfxManager.getPlatform().addToVirtualGroup(this.outlines, outlineDependency);
								GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
								GfxManager.getPlatform().moveToBack(outlineDependency);
								alreadyAdded.put(dependantArtifact, selectedArtifact);
							}
						} else {
							selectedArtifactEntry.getValue().add(dependantArtifact.getCenter());
						}
					}
				}
			}
		}
	}

	private void unselectOnRelease(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {

		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		if (newSelected != null) {
			if (this.selectedArtifacts.containsKey(newSelected)) {
				if (this.selectedArtifacts.size() != 1) {
					if (!isShiftKeyDown && !isCtrlKeyDown) { // Doing what have not been done on mouse press to allow multiple dragging
						this.deselectAllArtifacts();
						this.selectArtifact(newSelected);
					}
				}
			}
		}
	}

	//TODO clearCanvas
	public void clearCanvas() {
		this.selectAll();
		if (!this.selectedArtifacts.isEmpty()) {
			final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			for (final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
				this.removeSilent(selectedArtifact);
			}
		}
	}


	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height セットする height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width セットする width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	public void setSize(int height, int width){
		this.height = height;
		this.width = width;
	}

	// add Yamazaki
//	public ArrayList<AbstDrawReplaceAddDelete> addYamazakiDiffSurplus(Map<String,String> diffMap)
//	{
//		Window.alert("py");
//		ArrayList<AbstDrawReplaceAddDelete> surplusList = new ArrayList<AbstDrawReplaceAddDelete>();
//		
//		return surplusList;
//	}

	public ArrayList<AbstDrawReplaceAddDelete> addYamazakiDiffSurplus(Map<String,String> diffMap)
	{
//		Window.alert("Yamazaki");
		Map<String,String> classDiff  	= new HashMap<String,String>();
		Map<String,String> fieldDiff  	= new HashMap<String,String>();
		Map<String,String> methodDiff  	= new HashMap<String,String>();
		Map<String,String> paraDiff  	= new HashMap<String,String>();
		
		ArrayList<AbstDrawReplaceAddDelete> surplusList = new ArrayList<AbstDrawReplaceAddDelete>();
		
		// Mapの整理
		for(String diffkey : diffMap.keySet())
		{
			if(diffkey.contains("!")) 								// フィールド
			{
				fieldDiff.put(diffkey,diffMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&")) // パラメータ
			{

				paraDiff.put(diffkey,diffMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 							//　メソッド
			{
				methodDiff.put(diffkey,diffMap.get(diffkey));
			}
			else 													// クラス名
			{
				classDiff.put(diffkey,diffMap.get(diffkey));
			}
		}
		
		for(GfxObject gfxobject : objects.keySet())
		{
			ClassArtifact artifact = (ClassArtifact) objects.get(gfxobject);

			// クラス
			UMLClass umlclass = artifact.getUMLClass();
			if(!classDiff.isEmpty())
			{
				for(String classDiffKey : classDiff.keySet())
				{
					System.out.println("classDiffKey：:"+ classDiffKey);   //-----------
					
					String[] splitDiffClassKey = classDiffKey.split(";");
					String classname = splitDiffClassKey[0];
					//Window.alert("kifu:" + umlclass.getName() + " surplus:" + classDiffKey);
					if(umlclass.getName().equals(classname))
					{
						umlclass.setStrokeRED(classDiffKey);

						String attributename = splitDiffClassKey[splitDiffClassKey.length - 1];
						if(splitDiffClassKey.length > 1)
						{
							AbstDrawReplaceAddDelete addElements = new SurplusClassTypeElement(classDiffKey,classDiff.get(classDiffKey),artifact.getClassPartNameArtifact(),artifact);
							surplusList.add(addElements);

						}
						else
						{
							AbstDrawReplaceAddDelete addElements = new SurplusClassElement(classDiffKey,classDiff.get(classDiffKey),this,artifact);
							surplusList.add(addElements);
						}
					}

				}
			}
			// フィールド
			if(!fieldDiff.isEmpty())
			{
				for(UMLClassAttribute attribute : artifact.getAttributes())
				{
					for(String fieldDiffKey : fieldDiff.keySet())
					{
						String[] splitDiffAttributeClassname= fieldDiffKey.split("!");
						String classname = splitDiffAttributeClassname[0];

						GWT.log(splitDiffAttributeClassname[0] + "!" + splitDiffAttributeClassname[1] + " diffクラス名：" + classname + " umlclass" + umlclass.getName());
						if(umlclass.getName().equals(classname))
						{
							// 差分検知したものと今見ているKIfUのクラス図でクラス名が一致してたらその中のフィールドをみる
							attribute.setStrokeRED(splitDiffAttributeClassname[splitDiffAttributeClassname.length - 1]);

							String attributename = splitDiffAttributeClassname[splitDiffAttributeClassname.length - 1];
							if(!attributename.contains("{") && !attributename.contains("}") && attribute.getDiffNameKey().equals(attributename))
							{
								AbstDrawReplaceAddDelete addElements = new SurplusAttributeElement(fieldDiffKey,fieldDiff.get(fieldDiffKey),artifact,attribute);
								surplusList.add(addElements);
							}
						}
					}
				}
			}
		
			// メソッド
			if(!methodDiff.isEmpty())
			{
				for(UMLClassMethod method : artifact.getMethods())
				{
					for(String methodDiffKey : methodDiff.keySet())
					{
						String[] splitDiffMethodKey = methodDiffKey.split("&");
						String classname = splitDiffMethodKey[0];

						if(umlclass.getName().equals(classname))
						{

							method.setStrokeRED(splitDiffMethodKey[splitDiffMethodKey.length - 1]);

							String methodname = splitDiffMethodKey[splitDiffMethodKey.length - 1];
							if(!methodname.contains("{") && !methodname.contains("}") && method.getDiffNameKey().equals(methodname))
							{
								AbstDrawReplaceAddDelete addElements = new SurplusMethodElement(methodDiffKey,methodDiff.get(methodDiffKey),artifact,method);
								surplusList.add(addElements);
							}
						}
					}
				}
			}

			if(!paraDiff.isEmpty())
			{
				for(UMLClassMethod method : artifact.getMethods())
				{
					for(UMLParameter para : method.getParameters())
					{
						for(String paraDiffKey : paraDiff.keySet())
						{
							String[] splitDiffMethodKey = paraDiffKey.split("&");
							String classname = splitDiffMethodKey[0];
							if(classname.equals(umlclass.getName()))
							{
								String[] splitDiffParaKey = paraDiffKey.split("%");
								para.setStrokeRED(splitDiffParaKey[splitDiffParaKey.length - 1]);

								String paradname = splitDiffParaKey[splitDiffParaKey.length - 1];
								if(!paradname.contains("}") && para.getDiffNameKey().equals(paradname))
								{
									AbstDrawReplaceAddDelete addElements = new SurplusParameterElement(paraDiffKey,paraDiff.get(paraDiffKey),method,para,artifact);
									surplusList.add(addElements);
								}
							}
						}
					}
				}
			}
		}

		return surplusList;
	}


	public ArrayList<AbstDrawReplaceAddDelete> addYamazakiDiffNotHas(Map<String,String> diffMap)
	{
//		Window.alert("Yamazaki");
		Map<String,String> classDiff  = new HashMap<String,String>();
		Map<String,String> fieldDiff  = new HashMap<String,String>();
		Map<String,String> methodDiff  = new HashMap<String,String>();
		Map<String,String> paraDiff  = new HashMap<String,String>();

		ArrayList<AbstDrawReplaceAddDelete> nothasList = new ArrayList<AbstDrawReplaceAddDelete>();

		// Mapの整理
		for(String diffkey : diffMap.keySet())
		{
			if(diffkey.contains("!")) 								// フィールド
			{
				fieldDiff.put(diffkey,diffMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&")) // パラメータ
			{
				paraDiff.put(diffkey,diffMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 							//　メソッド
			{
				methodDiff.put(diffkey,diffMap.get(diffkey));
			}
			else 													// クラス名
			{
				classDiff.put(diffkey,diffMap.get(diffkey));
			}
		}

		for(GfxObject gfxobject : objects.keySet())
		{
			ClassArtifact artifact = (ClassArtifact) objects.get(gfxobject);

			// クラス
			UMLClass umlclass = artifact.getUMLClass();

			// フィールド
			if(!fieldDiff.isEmpty())
			{
				for(String fieldDiffKey : fieldDiff.keySet())
				{
					String[] splitDiffAttributeClassname= fieldDiffKey.split("!");
					String classname = splitDiffAttributeClassname[0];
					String attributename = splitDiffAttributeClassname[splitDiffAttributeClassname.length - 1];

					if(umlclass.getName().equals(classname) && !attributename.contains("{") && !attributename.contains("}"))
					{
						// フィールド追加
						AbstDrawReplaceAddDelete addElements = new NotHasAttributeElements(fieldDiffKey,fieldDiff.get(fieldDiffKey),artifact);
						nothasList.add(addElements);

						//artifact.addAttribute(new UMLClassAttribute(UMLVisibility.getVisibilityFromToken('+'),"void",attributename));
					}
				}
			}

			// メソッド
			if(!methodDiff.isEmpty())
			{
				for(String methodDiffKey : methodDiff.keySet())
				{
					String[] splitDiffMethodKey = methodDiffKey.split("&");
					String classname = splitDiffMethodKey[0];
					String methodname = splitDiffMethodKey[splitDiffMethodKey.length - 1];

					if(umlclass.getName().equals(classname) && !methodname.contains("{") && !methodname.contains("}"))
					{
						AbstDrawReplaceAddDelete addElements = new NotHasMethodElements(methodDiffKey,methodDiff.get(methodDiffKey),artifact);
						nothasList.add(addElements);
						//artifact.addMethod(new UMLClassMethod(UMLVisibility.getVisibilityFromToken('+'), "void", methodname, new ArrayList<UMLParameter>()));
					}

				}
			}

			// パラメータ
			if(!paraDiff.isEmpty())
			{
				for(String paraDiffKey : paraDiff.keySet())
				{
					String[] splitDiffMethodKey = paraDiffKey.split("&");
					String classname = splitDiffMethodKey[0];
					int methodEndIndex = splitDiffMethodKey[splitDiffMethodKey.length - 1].indexOf("%");
					String methodname = splitDiffMethodKey[splitDiffMethodKey.length - 1].substring(0,methodEndIndex);

					String[] splitDiffPara = paraDiffKey.split("%");
					String paraname = splitDiffPara[splitDiffPara.length - 1];

					if(umlclass.getName().equals(classname))
					{
						for(UMLClassMethod method : artifact.getMethods())
						{
							if(method.getName().equals(methodname) && !paraname.contains("}"))
							{
								ClassPartMethodsArtifact roleRebuild = artifact.getClassPartMethodArtifact();
								AbstDrawReplaceAddDelete addElements = new NotHasParameterElements(paraDiffKey,paraDiff.get(paraDiffKey),method,artifact);
								nothasList.add(addElements);
								//method.addParametar(new UMLParameter("void", paraname));
							}

						}
					}
				}
			}
		}

		if(!classDiff.isEmpty())
		{
			for(String classDiffKey : classDiff.keySet())
			{
				String[] splitDiffClassKey = classDiffKey.split(";");
				String classname = splitDiffClassKey[0];

				boolean isClassName = false;
				for(GfxObject gfxobject : objects.keySet())
				{
					ClassArtifact artifact = (ClassArtifact) objects.get(gfxobject);

					// クラス
					UMLClass umlclass = artifact.getUMLClass();
					if(umlclass.getName().equals(classname))
					{
						if(splitDiffClassKey.length > 1)
						{
							String type = classDiff.get(classDiffKey);
							if(!type.equals(umlclass.getStereotype()))
							{

								AbstDrawReplaceAddDelete addElements = new NotHasClassTypeElements(classDiffKey,classDiff.get(classDiffKey),artifact);
								nothasList.add(addElements);
							}
						}
						isClassName = true;
					}
				}

				if(!isClassName)
				{
					AbstDrawReplaceAddDelete addElements = new NotHasClassElements(classDiffKey,classDiff.get(classDiffKey),this);
					nothasList.add(addElements);
				}
			}
		}

		return nothasList;
	}

	public ArrayList<AbstDrawReplaceAddDelete> addYamazakiReplaceDiff(Map<String,Map<String,String>> diffMap)
	{
//		Window.alert("Yamazaki");
		Map<String,String> surplusMap = diffMap.get(this.SURPLUS);
		Map<String,String> nothasMap = diffMap.get(this.NOTHAS);

		Map<String,String> replacetoNothas = new HashMap<String,String>();
		ArrayList<AbstDrawReplaceAddDelete> replaceList = new ArrayList<AbstDrawReplaceAddDelete>();

		Map<String,String> classDiffSurplus  	= new HashMap<String,String>();
		Map<String,String> fieldDiffSurplus  	= new HashMap<String,String>();
		Map<String,String> methodDiffSurplus  	= new HashMap<String,String>();
		Map<String,String> paraDiffSurplus  	= new HashMap<String,String>();

		// Mapの整理
		for(String diffkey : surplusMap.keySet())
		{
			if(diffkey.contains("!")) 								// フィールド
			{
				fieldDiffSurplus.put(diffkey,surplusMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&")) // パラメータ
			{
				//Window.alert("surplus paranothasKey:" + diffkey);
				paraDiffSurplus.put(diffkey,surplusMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 							//　メソッド
			{
				methodDiffSurplus.put(diffkey,surplusMap.get(diffkey));
			}
			else 													// クラス名
			{
				classDiffSurplus.put(diffkey,surplusMap.get(diffkey));
			}
		}

		Map<String,String> classDiffNothas  	= new HashMap<String,String>();
		Map<String,String> fieldDiffNothas  	= new HashMap<String,String>();
		Map<String,String> methodDiffNothas  	= new HashMap<String,String>();
		Map<String,String> paraDiffNothas 	= new HashMap<String,String>();

		// Mapの整理
		for(String diffkey : nothasMap.keySet())
		{
			//Window.alert("nothas paranothasKey:" + diffkey);
			if(diffkey.contains("!")) 								// フィールド
			{
				fieldDiffNothas.put(diffkey,nothasMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&")) // パラメータ
			{
				//Window.alert("nothas paranothasKey:" + diffkey);
				paraDiffNothas.put(diffkey,nothasMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 							//　メソッド
			{
				methodDiffNothas.put(diffkey,nothasMap.get(diffkey));
			}
			else 													// クラス名
			{
				classDiffNothas.put(diffkey,nothasMap.get(diffkey));
			}
		}



		for(GfxObject gfxobject : objects.keySet())
		{
			ClassArtifact artifact = (ClassArtifact) objects.get(gfxobject);
			//Window.alert(artifact.toURL());


			// クラス
			UMLClass umlclass = artifact.getUMLClass();
			if(!classDiffSurplus.isEmpty() && !classDiffNothas.isEmpty())
			{
				for(String surplusKey : classDiffSurplus.keySet())
				{
					for(String nothasKey : classDiffNothas.keySet())
					{
						if(surplusKey.equals(nothasKey))
						{
							String[] splitDiffAttributeClassname= nothasKey.split(";");
							String classname = splitDiffAttributeClassname[0];

							if(umlclass.hasGfxObjectKey(nothasKey) && splitDiffAttributeClassname.length > 1)
							{
								ReplaceClassStereoType tmpReplaceObject = new ReplaceClassStereoType(surplusKey,nothasMap.get(nothasKey),artifact);
								replaceList.add(tmpReplaceObject);
								replacetoNothas.put(nothasKey,nothasMap.get(nothasKey));
							}
						}
					}
				}
			}

			// フィールド
			if(!fieldDiffSurplus.isEmpty() && !fieldDiffNothas.isEmpty())
			{

				for(String surplusKey : fieldDiffSurplus.keySet())
				{
					for(String nothasKey : fieldDiffNothas.keySet())
					{

						if(surplusKey.equals(nothasKey))
						{
							for(UMLClassAttribute attribute : artifact.getAttributes())
							{

								String[] splitDiffAttributeClassname= nothasKey.split("!");
								String classname = splitDiffAttributeClassname[0];
								String element = splitDiffAttributeClassname[1];

								GWT.log(splitDiffAttributeClassname[0] + "!" + splitDiffAttributeClassname[1] + " diffクラス名：" + classname + " umlclass" + umlclass.getName());
								if(attribute.hasGfxObjectKey(element) && classname.equals(umlclass.getName()))
								{
									//UMLArtifact roleRebuild = artifact.getClassPartAttributesArtifact();

									// 差分検知したものと今見ているKIfUのクラス図でクラス名が一致してたらその中のフィールドをみる
									ReplaceElements tmpReplaceObject = new ReplaceElements(surplusKey,nothasMap.get(nothasKey),attribute,artifact);
									replaceList.add(tmpReplaceObject);
									replacetoNothas.put(nothasKey,nothasMap.get(nothasKey));
								}
							}
						}

					}
				}
			}

			// メソッド

			if(!methodDiffSurplus.isEmpty() && !methodDiffNothas.isEmpty())
			{
				for(String surplusKey : methodDiffSurplus.keySet())
				{
					for(String nothasKey : methodDiffNothas.keySet())
					{
						if(surplusKey.equals(nothasKey))
						{
							for(UMLClassMethod method : artifact.getMethods())
							{

								String[] splitDiffMethodKey = nothasKey.split("&");
								String classname = splitDiffMethodKey[0];
								String element = splitDiffMethodKey[1];

								if(method.hasGfxObjectKey(element) && classname.equals(umlclass.getName()))
								{
									//UMLArtifact roleRebuild = artifact.getClassPartMethodArtifact();
									AbstDrawReplaceAddDelete tmpReplaceObject = new ReplaceElements(surplusKey,nothasMap.get(nothasKey),method,artifact);
									replaceList.add(tmpReplaceObject);
									replacetoNothas.put(nothasKey,nothasMap.get(nothasKey));


								}
							}
						}
					}
				}
			}

			if(!paraDiffSurplus.isEmpty() && !paraDiffNothas.isEmpty())
			{
				for(String parasurplusKey : paraDiffSurplus.keySet())
				{
					for(String paranothasKey : paraDiffNothas.keySet())
					{
						if(parasurplusKey.equals(paranothasKey))
						{
							for(UMLClassMethod method : artifact.getMethods())
							{
								for(UMLParameter para : method.getParameters())
								{
									//Window.alert("paranothasKey:" + paranothasKey + " kifu:paranametype:" + para.getType());
									String[] splitDiffParaKey = paranothasKey.split("%");
									String classname = paranothasKey.split("&")[0];
									String paraelement = splitDiffParaKey[1];

									if(para.hasGfxObjectKey(paraelement) && classname.equals(umlclass.getName()))
									{
										//UMLArtifact roleRebuild = artifact.getClassPartMethodArtifact();
										ReplaceElements tmpparaReplaceObject = new ReplaceElements(parasurplusKey,nothasMap.get(paranothasKey),para,artifact);
										replaceList.add(tmpparaReplaceObject);
										replacetoNothas.put(paranothasKey,nothasMap.get(paranothasKey));
									}
								}
							}
						}
					}
				}
			}
		}

		return replaceList;
	}
	
	//add saito    *+*++*+****+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*
	
	public void addSaitoSimMatch(Map<String,String> matchMap){
		Map<String,String> classDiff  	= new HashMap<String,String>();
		Map<String,String> fieldDiff  	= new HashMap<String,String>();
		Map<String,String> methodDiff  	= new HashMap<String,String>();
		Map<String,String> paraDiff  	= new HashMap<String,String>();
	
		for(String diffkey : matchMap.keySet()){
			if(diffkey.contains("!")) 
			{
				fieldDiff.put(diffkey,matchMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&"))
			{
				paraDiff.put(diffkey,matchMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 
			{
				methodDiff.put(diffkey,matchMap.get(diffkey));
			}
			else 
			{
				classDiff.put(diffkey,matchMap.get(diffkey));			
			}
		}
		
		for(GfxObject gfxobject : objects.keySet()){
			
			Object gfxObject = objects.get(gfxobject);
			
			if (gfxObject instanceof ClassArtifact) {
				ClassArtifact artifact = (ClassArtifact) gfxObject;  
			      
				UMLClass umlclass = artifact.getUMLClass();
				
				if(!classDiff.isEmpty()){
					
					for(String classDiffKey : classDiff.keySet()){
						
						String[] splitDiffClassKey = classDiffKey.split(";");
						String classname = splitDiffClassKey[0];
						
						if(umlclass.getName().equals(classname))
						{
							umlclass.setStrokeRED(classDiffKey);
						}
					}
				}
				
				if(!fieldDiff.isEmpty()){
					
					for(UMLClassAttribute attribute : artifact.getAttributes())
					{
						for(String fieldDiffKey : fieldDiff.keySet())
						{
							String[] splitDiffAttributeClassname= fieldDiffKey.split("!");						
				
							if(attribute.getName().equals(splitDiffAttributeClassname[1])) {
								attribute.setStrokeRED(splitDiffAttributeClassname[1]);
							}	
						}
					}
				}
			} else {
			      GWT.log("キャスト失敗");
			}
		}
	}
	
	public void addSaitoSimMisMatch(Map<String,String> misMatchMap){
		Map<String,String> classDiff  	= new HashMap<String,String>();
		Map<String,String> fieldDiff  	= new HashMap<String,String>();
		Map<String,String> methodDiff  	= new HashMap<String,String>();
		Map<String,String> paraDiff  	= new HashMap<String,String>();
		
		for(String diffkey : misMatchMap.keySet()){
			if(diffkey.contains("!")) 
			{
				fieldDiff.put(diffkey,misMatchMap.get(diffkey));
			}
			else if(diffkey.contains("%") && diffkey.contains("&"))
			{
				paraDiff.put(diffkey,misMatchMap.get(diffkey));
			}
			else if(diffkey.contains("&")) 
			{
				methodDiff.put(diffkey,misMatchMap.get(diffkey));
			}
			else 
			{
				classDiff.put(diffkey,misMatchMap.get(diffkey));			
			}
		}
		
		for(GfxObject gfxobject : objects.keySet()){
			
			Object gfxObject = objects.get(gfxobject);
			
			if (gfxObject instanceof ClassArtifact) {
				ClassArtifact artifact = (ClassArtifact) gfxObject;  
			      
				UMLClass umlclass = artifact.getUMLClass();
				
				if(!classDiff.isEmpty()){
					
					for(String classDiffKey : classDiff.keySet()){
						
						String[] splitDiffClassKey = classDiffKey.split(";");
						String classname = splitDiffClassKey[0];
						
						if(umlclass.getName().equals(classname))
						{
							umlclass.setStrokeBLUE(classDiffKey);
						}
					}
				}
				
				if(!fieldDiff.isEmpty()){
					
					for(UMLClassAttribute attribute : artifact.getAttributes())
					{
						for(String fieldDiffKey : fieldDiff.keySet())
						{
							String[] splitDiffAttributeClassname= fieldDiffKey.split("!");						
				
							if(attribute.getName().equals(splitDiffAttributeClassname[1])) {
								attribute.setStrokeBLUE(splitDiffAttributeClassname[1]);
							}	
						}
					}
				}
			} else {
			      GWT.log("キャスト失敗2");
			}
		}
	}
}

