package com.objetdirect.gwt.umldrawer.client.drawerparts.yamazaki;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.yamazaki.replace.AbstDrawReplaceAddDelete;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.RecordCheckBoxStateService;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.RecordCheckBoxStateServiceAsync;



public class DiffReplacePanel extends DialogBox {

	private Map<CheckBox,AbstDrawReplaceAddDelete> checkboxreplaceMap;
	private Map<TextBox,AbstDrawReplaceAddDelete> reason;

	public DiffReplacePanel(List<AbstDrawReplaceAddDelete> replaceelementsList,
							 List<AbstDrawReplaceAddDelete> addementsList,
							 List<AbstDrawReplaceAddDelete> removeList)
	{
		super();
		checkboxreplaceMap = new HashMap<CheckBox,AbstDrawReplaceAddDelete>();
		reason = new HashMap<TextBox,AbstDrawReplaceAddDelete>();
		createDialogBox(replaceelementsList,addementsList,removeList);
		this.center();

	}

	private void createDialogBox(List<AbstDrawReplaceAddDelete> replaceelementsList,
								   List<AbstDrawReplaceAddDelete> addementsList,
								   List<AbstDrawReplaceAddDelete> removeList) {
	    // Create a dialog box and set the caption text
		VerticalPanel base;
	    this.setText("UMLDS");

	    /*
	     * 	検知した数ぶんcheckBoxのリストを作りCheckBoxを縦に追加
	     */
	    base = getVerticalPanel(replaceelementsList,addementsList,removeList);

	    /*
	     *	  ボタン入れる用のHorizontal
	     */
		base.add(getHorizontalPanel());

		this.setWidget(base);


	}

	private VerticalPanel getVerticalPanel(List<AbstDrawReplaceAddDelete> replaceelements,
											List<AbstDrawReplaceAddDelete> addelments,
											List<AbstDrawReplaceAddDelete> removes)
	{
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(2);

	    // Add a checkbox for each day of the week
		/*
		 * 2022/6/13
		 * CheckBoxのコンストラクタにメッセージ入れているから　message用のLavelはない
		 */
		for(final AbstDrawReplaceAddDelete remove : removes)
	    {
	    	final CheckBox checkboxReplace = remove.getCheckBox();
	    	final TextBox textbox = remove.getResonComponent();
	    	vPanel.add(checkboxReplace);
	    	vPanel.add(textbox);
	    	checkboxreplaceMap.put(checkboxReplace,remove);
	    	reason.put(textbox, remove);
	    }

		for(final AbstDrawReplaceAddDelete add : addelments)
	    {
	    	final CheckBox checkboxReplace = add.getCheckBox();
	    	final TextBox textbox = add.getResonComponent();
	    	vPanel.add(checkboxReplace);
	    	vPanel.add(textbox);
	    	checkboxreplaceMap.put(checkboxReplace,add);
	    	reason.put(textbox, add);
	    }

		for(final AbstDrawReplaceAddDelete replace : replaceelements)
	    {
	    	final CheckBox checkboxReplace = replace.getCheckBox();
	    	final TextBox textbox = replace.getResonComponent();
	    	vPanel.add(checkboxReplace);
	    	vPanel.add(textbox);
	    	checkboxreplaceMap.put(checkboxReplace,replace);
	    	reason.put(textbox, replace);
	    }

	    return vPanel;
	}

	private HorizontalPanel getHorizontalPanel()
	{
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(2);
		hPanel.add(this.makeReplaceButton());
		hPanel.add(this.makeCloseButton());
		return hPanel;
	}

	private Button makeReplaceButton(){

		// ダイアログが開いた時のタイムスタンプ
		final Date openDate = new Date();
		final DateTimeFormat pushdateFormate = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		final String opentime = pushdateFormate.format(openDate);

		Button replaceButton = new Button(DrawerTextResource.REPLACE.getMessage());


		replaceButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// replace start
				HashMap<String,Integer> tmpRecordState = new HashMap<String,Integer>(); // メッセージ,チェックボックスの状態
				HashMap<String,String> tmpReason = new HashMap<String,String>();

				// HACK：Dateの変換が正確には正しくないらしい
				//java.sql.Date tmpdate = new java.sql.Date(openDate2.getTime());

				Window.alert("click");
				for(CheckBox checkboxReReplace : checkboxreplaceMap.keySet())
			    {
					
					AbstDrawReplaceAddDelete replaceobject = checkboxreplaceMap.get(checkboxReReplace);
					
			    	if(checkboxReReplace.getValue())
			    	{
			    		replaceobject.drawReplaceAddDelete();
			    	}
			    	// 理由用のMapが欲しいかも
			    	int bool = checkboxReReplace.getValue() ? 1 : 0;
			    	tmpReason.put(replaceobject.getMessage(), replaceobject.getResonText());
			    	tmpRecordState.put(replaceobject.getMessage(), bool);
			    }

				System.out.println("ハイドされないよ");
				DiffReplacePanel.this.hide();

				//　チェックボックスの内容を保存　2月19日
		    	RecordCheckBoxStateServiceAsync async = GWT.create(RecordCheckBoxStateService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "record";
				entryPoint.setServiceEntryPoint(entryURL);

				async.recordCheckboxState(Session.studentId,
										  Session.exerciseId,
										  tmpRecordState,
										  opentime,
										  tmpReason,
										  new AsyncCallback<Boolean>()
				{

					@Override
					public void onFailure(Throwable arg0) {
						// TODO 自動生成されたメソッド・スタブ

					}

					@Override
					public void onSuccess(Boolean arg0) {
						// TODO 自動生成されたメソッド・スタブ
						//Window.alert("レコード完了 in 置換ボタン押した後");
					}

				});
			}
		});
		return replaceButton;
	}

	private Button makeCloseButton(){
		Button closeButton = new Button(DrawerTextResource.CLOSE.getMessage());
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DiffReplacePanel.this.hide();
			}
		});
		return closeButton;
	}
}
