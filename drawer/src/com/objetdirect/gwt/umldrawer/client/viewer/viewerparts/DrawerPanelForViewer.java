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
package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.Mouse;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager.Theme;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;

/**
 * This panel is an intermediate panel that contains the graphic canvas <br>
 * And can draw a shadow around it
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class DrawerPanelForViewer extends AbsolutePanel {

	private SimplePanel								bottomLeftCornerShadow;

	private SimplePanel								bottomRightCornerShadow;

	private SimplePanel								bottomShadow;
	private final UMLCanvasForViewer						uMLCanvas;

	private int									height;
	private SimplePanel								rightShadow;
	private SimplePanel								topRightCornerShadow;
	private int									width;

	FocusPanel										topLeft			= new FocusPanel();
	FocusPanel										top				= new FocusPanel();
	FocusPanel										topRight		= new FocusPanel();
	FocusPanel										right			= new FocusPanel();
	FocusPanel										bottomRight		= new FocusPanel();
	FocusPanel										bottom			= new FocusPanel();
	FocusPanel										bottomLeft		= new FocusPanel();
	FocusPanel										left			= new FocusPanel();

	//private VerticalPanel								basePanel      	= new VerticalPanel();

	private final HashMap<FocusPanel, Direction>	directionPanels	= new HashMap<FocusPanel, Direction>() {
																		{
																			this.put(DrawerPanelForViewer.this.topLeft, Direction.UP_LEFT);
																			this.put(DrawerPanelForViewer.this.top, Direction.UP);
																			this.put(DrawerPanelForViewer.this.topRight, Direction.UP_RIGHT);
																			this.put(DrawerPanelForViewer.this.right, Direction.RIGHT);
																			this.put(DrawerPanelForViewer.this.bottomRight, Direction.DOWN_RIGHT);
																			this.put(DrawerPanelForViewer.this.bottom, Direction.DOWN);
																			this.put(DrawerPanelForViewer.this.bottomLeft, Direction.DOWN_LEFT);
																			this.put(DrawerPanelForViewer.this.left, Direction.LEFT);
																		}
																	};

	private final ResizeHandler					resizeHandler;
	/**
	 * Default constructor of a DrawerPanel
	 *
	 */
	public DrawerPanelForViewer(){
		this(350, 100);
	}
	public DrawerPanelForViewer(final int widthDiffBetweenWindow, final int heightDiffBetweenWindow) {
		super();
		ThemeManager.setCurrentTheme((Theme.getThemeFromIndex(OptionsManager.get("Theme"))));
		GfxManager.setPlatform(OptionsManager.get("GraphicEngine"));
		GeometryManager.setPlatform(OptionsManager.get("GeometryStyle"));
		if (OptionsManager.get("AutoResolution") == 0) {
			this.width = OptionsManager.get("Width");
			this.height = OptionsManager.get("Height");
		} else {
			//TODO window size
			this.width = (int)(Window.getClientWidth())-widthDiffBetweenWindow;
			this.height = (int)(Window.getClientHeight())-heightDiffBetweenWindow;
		}

		final boolean isShadowed = OptionsManager.get("Shadowed") == 1;
		Logger.getGlobal().info("Creating drawer");

			//TODO
		this.uMLCanvas = new UMLCanvasForViewer(new UMLDiagram(UMLDiagram.Type.getUMLDiagramFromIndex(OptionsManager.get("DiagramType"))),
				this.width, this.height);
		//TODO

		this.add(this.uMLCanvas);


		final int directionPanelSizes = OptionsManager.get("DirectionPanelSizes");

		final HashMap<FocusPanel, Point> panelsSizes = this.makeDirectionPanelsSizes(directionPanelSizes);
		final HashMap<FocusPanel, Point> panelsPositions = this.makeDirectionPanelsPositions(directionPanelSizes);

		for (final Entry<FocusPanel, Direction> panelEntry : this.directionPanels.entrySet()) {
			final FocusPanel panel = panelEntry.getKey();
			final Direction direction = panelEntry.getValue();
			DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
			DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(((double) OptionsManager.get("DirectionPanelOpacity")) / 100));
			panel.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(final MouseDownEvent event) {

					for (double d = ((double) OptionsManager.get("DirectionPanelOpacity")) / 100; d <= ((double) OptionsManager.get("DirectionPanelMaxOpacity")) / 100; d += 0.05) {
						final double opacity = Math.ceil(d * 100) / 100;

						new Scheduler.Task("Opacifying") {
							@Override
							public void process() {
								DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(opacity));
							}
						};
					}
					new Scheduler.Task("MovingAllArtifacts") {
						@Override
						public void process() {
							Scheduler.cancel("MovingAllArtifactsRecursive");
							DrawerPanelForViewer.this.uMLCanvas.moveAll(direction.withSpeed(Direction.getDependingOnQualityLevelSpeed()), true);
						}
					};
				}
			});
			panel.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(final MouseOutEvent event) {
					Scheduler.cancel("Opacifying");
					Scheduler.cancel("MovingAllArtifacts");
					Scheduler.cancel("MovingAllArtifactsRecursive");
					for (final FocusPanel onePanel : DrawerPanelForViewer.this.directionPanels.keySet()) {
						double currentOpacity = 0;
						try {
							currentOpacity = Math.ceil(Double.parseDouble(DOM.getStyleAttribute(onePanel.getElement(), "opacity")) * 100) / 100;
						} catch (final Exception ex) {
							Logger.getGlobal().severe("Unable to parse element opacity : " + ex);
						}
						for (double d = currentOpacity; d >= ((double) OptionsManager.get("DirectionPanelOpacity")) / 100; d -= 0.05) {
							final double opacity = Math.ceil(d * 100) / 100;

							new Scheduler.Task("Desopacifying") {
								@Override
								public void process() {
									DOM.setStyleAttribute(onePanel.getElement(), "opacity", Double.toString(opacity));
								}
							};
						}
					}
					DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
				}
			});
			panel.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(final MouseDownEvent event) {
					DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelPressedColor().toString());
					Scheduler.cancel("MovingAllArtifactsRecursive");
				}
			});
			panel.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(final MouseUpEvent event) {
					//					DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
					//					DrawerPanel.this.uMLCanvas.moveAll(direction.withSpeed(Math.min(DrawerPanel.this.uMLCanvas.getOffsetHeight(), DrawerPanel.this.uMLCanvas
					//							.getOffsetWidth())), false);
					Scheduler.cancel("Opacifying");
					Scheduler.cancel("MovingAllArtifacts");
					Scheduler.cancel("MovingAllArtifactsRecursive");
					for (final FocusPanel onePanel : DrawerPanelForViewer.this.directionPanels.keySet()) {
						double currentOpacity = 0;
						try {
							currentOpacity = Math.ceil(Double.parseDouble(DOM.getStyleAttribute(onePanel.getElement(), "opacity")) * 100) / 100;
						} catch (final Exception ex) {
							Logger.getGlobal().severe("Unable to parse element opacity : " + ex);
						}
						for (double d = currentOpacity; d >= ((double) OptionsManager.get("DirectionPanelOpacity")) / 100; d -= 0.05) {
							final double opacity = Math.ceil(d * 100) / 100;

							new Scheduler.Task("Desopacifying") {
								@Override
								public void process() {
									DOM.setStyleAttribute(onePanel.getElement(), "opacity", Double.toString(opacity));
								}
							};
						}
					}
					DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
				}
			});

			panel.addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(final MouseMoveEvent event) {
					Mouse.move(new Point(event.getClientX(), event.getClientY()), event.getNativeButton(), event.isControlKeyDown(), event.isAltKeyDown(),
							event.isShiftKeyDown(), event.isMetaKeyDown());
				}
			});
			final Point panelPosition = panelsPositions.get(panel);
			final Point panelSize = panelsSizes.get(panel);
			panel.setPixelSize(panelSize.getX(), panelSize.getY());
			this.add(panel, panelPosition.getX(), panelPosition.getY());

		}

		Logger.getGlobal().info("Canvas added");
		if (isShadowed) {
			Logger.getGlobal().info("Making shadow");
			this.makeShadow();
		} else {
			this.uMLCanvas.setStylePrimaryName("canvas");
		}

		this.resizeHandler = new ResizeHandler() {
			public void onResize(final ResizeEvent resizeEvent) {
				if (OptionsManager.get("AutoResolution") == 1) {
					// DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");
					DrawerPanelForViewer.this.width = (int) (resizeEvent.getWidth() - widthDiffBetweenWindow);
					DrawerPanelForViewer.this.height = (int) (resizeEvent.getHeight() - heightDiffBetweenWindow);
					DrawerPanelForViewer.this.setPixelSize(DrawerPanelForViewer.this.width, DrawerPanelForViewer.this.height);
					DrawerPanelForViewer.this.uMLCanvas.setPixelSize(DrawerPanelForViewer.this.width, DrawerPanelForViewer.this.height);
					DrawerPanelForViewer.this.uMLCanvas.setSize(DrawerPanelForViewer.this.width, DrawerPanelForViewer.this.height);
					GfxManager.getPlatform().setSize(Session.getActiveCanvas().getDrawingCanvas(), DrawerPanelForViewer.this.width, DrawerPanelForViewer.this.height);
					DrawerPanelForViewer.this.clearShadow();
					DrawerPanelForViewer.this.makeShadow();
					final HashMap<FocusPanel, Point> panelsNewSizes = DrawerPanelForViewer.this.makeDirectionPanelsSizes(directionPanelSizes);
					final HashMap<FocusPanel, Point> panelsNewPositions = DrawerPanelForViewer.this.makeDirectionPanelsPositions(directionPanelSizes);
					for (final FocusPanel panel : DrawerPanelForViewer.this.directionPanels.keySet()) {
						final Point panelPosition = panelsNewPositions.get(panel);
						final Point panelSize = panelsNewSizes.get(panel);
						panel.setPixelSize(panelSize.getX(), panelSize.getY());
						DrawerPanelForViewer.this.setWidgetPosition(panel, panelPosition.getX(), panelPosition.getY());
					}
					DrawerPanelForViewer.this.uMLCanvas.clearArrows();
					DrawerPanelForViewer.this.uMLCanvas.makeArrows();
				}
			}

		};
		Window.addResizeHandler(this.resizeHandler);

		/***************************************************************************************************************************/





		/***************************************************************************************************************************/



		// TODO : under chrome redraw doesn't work if the canvas is at a
		// different point than (0,0) tatami ? dojo ? chrome ?
		// example : this.setSpacing(50);
		Logger.getGlobal().info("Setting active canvas");
		Session.setActiveCanvas(this.uMLCanvas);
		Logger.getGlobal().info("Disabling browser events");
		GWTUMLDrawerHelper.disableBrowserEvents();
		Logger.getGlobal().info("Init end");
	}

	public void setReflectionPanelToUMLCanvas(ReflectionPanel reflectionPanel){
		System.out.println("Set reflection panel.");
		this.uMLCanvas.setReflectionPanel(reflectionPanel);
	}

	/**
	 * Getter for the height
	 *
	 * @return the height
	 */
	public final int getHeight() {
		return this.height;
	}

	/**
	 * Getter for the uMLCanvas
	 *
	 * @return the uMLCanvas
	 */
	public final UMLCanvas getUMLCanvas() {
		return this.uMLCanvas;
	}

	/**
	 * Getter for the width
	 *
	 * @return the width
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * Setter for the height
	 *
	 * @param height
	 *            the height to set
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Setter for the width
	 *
	 * @param width
	 *            the width to set
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	void addDefaultNode() {
		final Type type = UMLDiagram.Type.getUMLDiagramFromIndex(OptionsManager.get("DiagramType"));
		if (type.isClassType()) {
			final ClassArtifact defaultclass = new ClassArtifact("Class1");
			defaultclass.setLocation(new Point(this.width / 2, this.height / 2));
			this.uMLCanvas.add(defaultclass);
		}
		if (type.isObjectType()) {
			final ObjectArtifact defaultobject = new ObjectArtifact("obj1", "Object1");
			defaultobject.setLocation(new Point(this.width / 3, this.height / 3));
			this.uMLCanvas.add(defaultobject);
		}
		if (type == Type.SEQUENCE) {
			final LifeLineArtifact defaultLifeLineArtifact = new LifeLineArtifact("LifeLine1", "ll1");
			defaultLifeLineArtifact.setLocation(new Point(this.width / 2, this.height / 2));
			this.uMLCanvas.add(defaultLifeLineArtifact);
		}
	}

	void clearShadow() {
		this.remove(this.bottomShadow);
		this.remove(this.rightShadow);
		this.remove(this.bottomRightCornerShadow);
		this.remove(this.topRightCornerShadow);
		this.remove(this.bottomLeftCornerShadow);
	}

	void makeShadow() {
		final int shadowSize = 8;

		this.setWidth(this.width + shadowSize + this.getAbsoluteLeft() + "px");
		this.setHeight(this.height + shadowSize + this.getAbsoluteTop() + "px");

		this.bottomShadow = new SimplePanel();
		this.bottomShadow.setPixelSize(this.width - shadowSize, shadowSize);
		this.bottomShadow.setStylePrimaryName("bottomShadow");
		this.add(this.bottomShadow, shadowSize, this.height);

		this.rightShadow = new SimplePanel();
		this.rightShadow.setPixelSize(shadowSize, this.height - shadowSize);
		this.rightShadow.setStylePrimaryName("rightShadow");
		this.add(this.rightShadow, this.width, shadowSize);

		this.bottomRightCornerShadow = new SimplePanel();
		this.bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		this.bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
		this.add(this.bottomRightCornerShadow, this.width, this.height);

		this.topRightCornerShadow = new SimplePanel();
		this.topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		this.topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
		this.add(this.topRightCornerShadow, this.width, 0);

		this.bottomLeftCornerShadow = new SimplePanel();
		this.bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
		this.bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
		this.add(this.bottomLeftCornerShadow, 0, this.height);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();

	}

	private HashMap<FocusPanel, Point> makeDirectionPanelsPositions(final int directionPanelSizes) {
		return new HashMap<FocusPanel, Point>() {
			{
				this.put(DrawerPanelForViewer.this.topLeft, Point.getOrigin());
				this.put(DrawerPanelForViewer.this.top, new Point(directionPanelSizes, 0));
				this.put(DrawerPanelForViewer.this.topRight, new Point(DrawerPanelForViewer.this.getWidth() - directionPanelSizes, 0));
				this.put(DrawerPanelForViewer.this.right, new Point(DrawerPanelForViewer.this.getWidth() - directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottomRight, new Point(DrawerPanelForViewer.this.getWidth() - directionPanelSizes, DrawerPanelForViewer.this.getHeight()
						- directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottom, new Point(directionPanelSizes, DrawerPanelForViewer.this.getHeight() - directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottomLeft, new Point(0, DrawerPanelForViewer.this.getHeight() - directionPanelSizes));
				this.put(DrawerPanelForViewer.this.left, new Point(0, directionPanelSizes));
			}
		};
	}

	private HashMap<FocusPanel, Point> makeDirectionPanelsSizes(final int directionPanelSizes) {
		return new HashMap<FocusPanel, Point>() {
			{
				this.put(DrawerPanelForViewer.this.topLeft, new Point(directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.top, new Point(DrawerPanelForViewer.this.getWidth() - 2 * directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.topRight, new Point(directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.right, new Point(directionPanelSizes, DrawerPanelForViewer.this.getHeight() - 2 * directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottomRight, new Point(directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottom, new Point(DrawerPanelForViewer.this.getWidth() - 2 * directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.bottomLeft, new Point(directionPanelSizes, directionPanelSizes));
				this.put(DrawerPanelForViewer.this.left, new Point(directionPanelSizes, DrawerPanelForViewer.this.getHeight() - 2 * directionPanelSizes));
			}
		};
	}

	public void clearCanvas() {
		this.uMLCanvas.clearCanvas();
	}

	public void fromURL(String url, boolean  isForPasting) {
		System.out.println(Session.mode+"DPF");
		this.uMLCanvas.fromURL(url, isForPasting);
	}
}
