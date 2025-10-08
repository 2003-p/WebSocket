package com.objetdirect.gwt.umldrawer.client.drawerparts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.DesignPattern;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternService;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.SelectSpotItemPanel;

public class SpotSelectPanel extends PopupPanel {
	private VerticalPanel base;
	private SelectSpotItemPanel selectSpotItemPanel;
	//private DrawerBase db = new DrawerBase();
	private UMLCanvas uMLCanvas;
	private List<String> checkedItemList = new ArrayList<String>();
	private DesignPattern pattern = new DesignPattern();

	/*
	public SpotSelectPanel() {
		super();
		base = new VerticalPanel();
		this.add(base);
		selectSpotItemPanel = new SelectSpotItemPanel();
		base.add(selectSpotItemPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(3);
		hp.add(makeSendButton());
		hp.add(makeCloseButton());
		base.add(hp);
	}
	*/
	public SpotSelectPanel(int patternIndex, String className) {
		super();
		base = new VerticalPanel();
		this.add(base);
		selectSpotItemPanel = new SelectSpotItemPanel(patternIndex);
		base.add(selectSpotItemPanel);
		pattern.setPatternIndex(patternIndex);
		pattern.setClassName(className);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(3);
		hp.add(makeCombineButton());
		hp.add(makeCloseButton());
		base.add(hp);
	}

	private Button makeSendButton(){
		Button b = new Button(DrawerTextResource.SELECT.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Window.alert(String.valueOf(patternIndex) + ", " + className);
				SpotSelectPanel.this.hide();
			}
		});
		return b;
	}

	private Button makeCombineButton(){
		Button b = new Button(DrawerTextResource.COMBINE.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Window.alert(String.valueOf(patternIndex) + ", " + className);
				combinePattern();
				if(pattern.getFlag()) {
					SpotSelectPanel.this.hide();
				}
			}
		});
		return b;
	}


	private Button makeCloseButton(){
		Button b = new Button(DrawerTextResource.CLOSE.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pattern.setFlag(false);
				SpotSelectPanel.this.hide();
			}
		});
		return b;
	}

	public void combinePattern() {
		Map<String, String> combineMap = new LinkedHashMap<String, String>();
		combineMap = selectSpotItemPanel.getCheckedItem();
		if(pattern.getPatternIndex() != -1 && ! (combineMap.isEmpty())) {
		/*
		for(String str : selectSpotItemPanel.getCheckedItem().keySet()) {
			Window.alert(str);
		}
		for(String str : selectSpotItemPanel.getCheckedItem().values()) {
			Window.alert(str);
		}
		*/
			if(combineMap.containsValue(null)) {
				Window.alert(DrawerTextResource.OVERLAP_MESSAGE.getMessage());
				pattern.setFlag(false);
			}
			else {
				PatternServiceAsync async = (PatternServiceAsync)GWT.create(PatternService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "getCombineUrl2";
				entryPoint.setServiceEntryPoint(entryURL);

				//final String str = "<0>]Class$(186,196)!Client!!!;<1>]Class$(357,184)!Prototype!!!+ConcreteClone() : void%;<2>]Class$(369,344)!ConcretePrototype!!!+ConcreteClone() : void%;<15>]ClassRelationLink$<1>!<2>!SimpleRelation!Name!Solid!None!None!!!None!None!!;<16>]ClassRelationLink$<0>!<1>!SimpleRelation!Name!Solid!None!None!!!None!None!!;";

				@SuppressWarnings("rawtypes")
				AsyncCallback callback = new AsyncCallback(){
					@SuppressWarnings({ "unchecked", "deprecation" })
					public void onSuccess(Object result){
						//Window.alert((String) result);
						if( !( (String) result == null) ){
						Session.setMode("load");
						Session.getActiveCanvas().clearCanvas();
						Session.getActiveCanvas().fromURL( ((String) result) , false);
						uMLCanvas = Session.getActiveCanvas();
						Session.setMode("drawer");
						//							int preEventId, String editEvent, String eventType,
						//							String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
						//							String targetPart, String beforeEdit, String afterEdit, String canvasUrl
						MyLoggerExecute.registEditEvent(-1, "" + ((String) result ), "GetCombineUrl2",
								null, -1, null, -1, -1,
								null, null, null, Session.getActiveCanvas().toUrl(), UMLArtifact.getIdCount());
						}

					}

					public void onFailure(Throwable caught){
						System.out.println(caught);
					}
				};
				async.getCombineUrl2(Session.studentId, Session.exerciseId, pattern.getPatternIndex(), selectSpotItemPanel.getCheckedItem(), callback);
				pattern.setPatternIndex(-1);
				pattern.setFlag(true);
				//Window.alert(String.valueOf(pattern.getPatternIndex()));

				//combine.setEnabled(false);
			}
		}

		else {
			Window.alert(DrawerTextResource.NOT_CHECK_SPOT.getMessage() + "\n" + DrawerTextResource.NOT_CHECK_SPOT2.getMessage());
			pattern.setFlag(false);
		}
	}

}
