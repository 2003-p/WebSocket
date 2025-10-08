package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.DesignPattern;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternService;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternServiceAsync;

public class SelectSpotItemPanel extends HorizontalPanel {
	private VerticalPanel left = new VerticalPanel();
	private Label l = new Label(DrawerTextResource.SELECT_SPOT_ITEMS_MESSAGE.getMessage());
	private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
	private List<String> allSpotItemList;
	private DesignPattern pattern = new DesignPattern();
	private List<ListBox> classList = new ArrayList<ListBox>();
	private List<String> checkedItemList = new ArrayList<String>();
/*
	public SavePatternItemPanel(ReflectionEditor reflectionEditor){
		this.reflection = reflectionEditor.getReflection();
		this.setSpacing(2);
		this.setWidth("350px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();
		this.add(left);
	}
*/
	
	public SelectSpotItemPanel(int patternIndex){
		this.setSpacing(2);
		this.setWidth("350px");
		pattern.setPatternIndex(patternIndex);
		left.setSpacing(2);
		left.add(l);
		addCheckBoxList();
		this.add(left);
	}

	private void addCheckBoxList() {
		this.clear();
		classList.clear();
		getAllSpotItemList();

	}

	private void getAllSpotItemList() {
		PatternServiceAsync async = (PatternServiceAsync)GWT.create(PatternService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getPatternSpotList";
		entryPoint.setServiceEntryPoint(entryURL);
		
		//final String str = "<0>]Class$(186,196)!Client!!!;<1>]Class$(357,184)!Prototype!!!+ConcreteClone() : void%;<2>]Class$(369,344)!ConcretePrototype!!!+ConcreteClone() : void%;<15>]ClassRelationLink$<1>!<2>!SimpleRelation!Name!Solid!None!None!!!None!None!!;<16>]ClassRelationLink$<0>!<1>!SimpleRelation!Name!Solid!None!None!!!None!None!!;";

		@SuppressWarnings("rawtypes")
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>(){
			@SuppressWarnings({ "unchecked", "deprecation" })
			public void onSuccess(List<String> result){
				//Window.alert("1");
				allSpotItemList = result;
				
				for(int i = 0; i < allSpotItemList.size(); i ++) {
					checkBoxList.add(new CheckBox( allSpotItemList.get(i)));
					left.add( checkBoxList.get(i) );
					classList.add(new ListBox());
					setClassList(classList.get(i));
					left.add(classList.get(i));
				}
				setCheckedItemList(allSpotItemList);
			}

			public void onFailure(Throwable caught){
				String s = String.valueOf(caught);
				Window.alert(s);
				System.out.println(caught);
			}
		};
		async.getPatternSpotList(pattern.getPatternIndex(), callback);
	}

	private ListBox setClassList(final ListBox listBox) {
		PatternServiceAsync async = (PatternServiceAsync)GWT.create(PatternService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getUMLClassDiagramList";
		entryPoint.setServiceEntryPoint(entryURL);
		
		//final String str = "<0>]Class$(186,196)!Client!!!;<1>]Class$(357,184)!Prototype!!!+ConcreteClone() : void%;<2>]Class$(369,344)!ConcretePrototype!!!+ConcreteClone() : void%;<15>]ClassRelationLink$<1>!<2>!SimpleRelation!Name!Solid!None!None!!!None!None!!;<16>]ClassRelationLink$<0>!<1>!SimpleRelation!Name!Solid!None!None!!!None!None!!;";

		@SuppressWarnings("rawtypes")
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>(){
			@SuppressWarnings({ "unchecked", "deprecation" })
			public void onSuccess(List<String> result){
				
				for(String str : result) {
					listBox.addItem(str);
				}
			}
			public void onFailure(Throwable caught){
				String s = String.valueOf(caught);
				Window.alert(s);
				System.out.println(caught);
			}
		};
		async.getUMLClassDiagramList(Session.studentId, Session.exerciseId, callback);
		return listBox;
	}
	
	public Map<String, String> getCheckedItem(){
		Map<String, String> combineMap = new LinkedHashMap<String, String>();
		for( CheckBox cb : this.getCheckBoxList()){
			if( cb.getValue() ){
				for(int i = 0; i < allSpotItemList.size(); i ++){
					if(allSpotItemList.get(i).equals(cb.getText())) {
						int index = classList.get(i).getSelectedIndex();
						//Window.alert("index : " + String.valueOf(index));
						if(combineMap.containsValue(classList.get(i).getItemText(index))) {
							combineMap.put(allSpotItemList.get(i), null);
						}
						else {
							combineMap.put(allSpotItemList.get(i), classList.get(i).getItemText(index));
						}
						setCheckedItemList(classList.get(i).getItemText(index));
						//Window.alert(String.valueOf(combineMap.entrySet()));
					}
				}
				
			}
			else {
				//Window.alert("残念でした");
			}
		}
		return combineMap;
	}

	public void setCheckedItemList(List<String> spotItemList){
		if (spotItemList == null) return;
		for (String ci : spotItemList){
			for( CheckBox cb : this.getCheckBoxList()){
				cb.setValue(false);
			}
		}
	}
		
	public VerticalPanel getLeft() {
		return left;
	}

	public void setLeft(VerticalPanel left) {
		this.left = left;
	}

	public Label getQuestion() {
		return l;
	}

	public void setQuestion(Label l) {
		this.l = l;
	}

	public List<CheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(List<CheckBox> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}
	
	public void setCheckedItemList(String checkedItem) {
		checkedItemList.add(checkedItem);
	}
	
	public List<String> getCheckedItemList() {
		return checkedItemList;
	}

}
