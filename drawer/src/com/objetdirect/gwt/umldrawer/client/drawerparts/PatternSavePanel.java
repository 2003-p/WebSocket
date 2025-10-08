package com.objetdirect.gwt.umldrawer.client.drawerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.DesignPattern;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternService;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.SavePatternItemPanel;

public class PatternSavePanel extends PopupPanel {
	private VerticalPanel base;
	private TextBox tb_name = new TextBox();
	private TextBox tb_id = new TextBox();
	private DesignPattern pattern = new DesignPattern();
	private SavePatternItemPanel savePatternItemPanel;
	List<String> checkedClassItemList = new ArrayList();
	
	public PatternSavePanel(int listSize) {
		super();
		base = new VerticalPanel();
		base.setSpacing(2);
		base.setWidth("300px");
		this.setWidth("300px");
		this.add(base);
		Label l = new Label();
		l.setText(DrawerTextResource.INPUT_PATTERN.getMessage());
		base.add(l);
		base.add(tb_name);
/*
 		Label l2 = new Label(); 
		l2.setText("pattern_idを設定してください");
		base.add(l2);
		base.add(tb_id);
*/
		pattern.setPatternListSize(listSize + 1);
		savePatternItemPanel = new SavePatternItemPanel();
		base.add(savePatternItemPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(3);
		hp.add(makeSendButton());
		hp.add(makeCloseButton());
		base.add(hp);
	}
	
	private Button makeSendButton(){
		Button b = new Button(DrawerTextResource.RECORD.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				checkedClassItemList = savePatternItemPanel.getCheckedItemList();
				//savePatternItemPanel.setCheckedItemList(checkedClassItemList);
				//checkedClassItemList = savePatternItemPanel.getCheckedItemList();
/*
for(String str : checkedClassItemList) {
	Window.alert(str);
}
*/			
				if(tb_name.getText().equals("") && tb_id.getText().equals("")) {
					Window.alert(DrawerTextResource.INPUT_PATTERN2.getMessage());
				}
				else {
					savePattern();
					//Window.alert(tb.getText() + "を登録しました");
					PatternSavePanel.this.hide();
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
				PatternSavePanel.this.hide();
			}
		});
		return b;
	}
	
	public void savePattern() {
		List<String> checkedSpotItemList = new ArrayList<String>();
		checkedSpotItemList = savePatternItemPanel.getCheckedItemList();
		if( ! (checkedSpotItemList.isEmpty())) {
			PatternServiceAsync async = (PatternServiceAsync)GWT.create(PatternService.class);
			ServiceDefTarget entryPoint = (ServiceDefTarget) async;
			String entryURL = GWT.getModuleBaseURL() + "savePatternList";
			entryPoint.setServiceEntryPoint(entryURL);
			
			AsyncCallback callback = new AsyncCallback() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO 自動生成されたメソッド・スタブ
					System.out.println(caught);
				}

				@Override
				public void onSuccess(Object result) {
					// TODO 自動生成されたメソッド・スタブ
					Window.alert((String) result + "を登録しました");
				}
			};
			
			async.savePatternList(Session.studentId, Session.exerciseId, tb_name.getText(), pattern.getPatternListSize(), savePatternItemPanel.getCheckedItemList(), callback);
		}
		else {
			Window.alert(DrawerTextResource.NOT_SELECT_SAVE_SPOT_ITEMS_MESSAGE.getMessage() + "\n" + DrawerTextResource.NOT_SELECT_SAVE_SPOT_ITEMS_MESSAGE2.getMessage());
		}
	}

}
