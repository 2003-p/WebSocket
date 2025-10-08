package com.objetdirect.gwt.umldrawer.client.viewer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;

/**
 * @author tanaka
 *
 */
public class EditEventCell extends AbstractCell<EditEvent>{
	private String width = "140";
	private String height = "100";


	@Override
	public void render(Context context, EditEvent value, SafeHtmlBuilder sb) {
		if(value == null){
			return;
		}
		//文字の色を変えれば選択されたときの背景色変更はデフォルトのが使える？
		if(value.getEventType().equals("Create")){
			sb.appendHtmlConstant("<div style=\"background-color:#99cc00; height = "+height+"; width: "+width+"; border: 1px solid #000000\">");
		} else if(value.getEventType().equals("Remove") || value.getEventType().equals("RemoveArtifacts")){
			sb.appendHtmlConstant("<div style = \"background-color:#ffd700; height = "+height+"; width:  "+width+"; border: 1px solid #000000\">");
		} else if(value.getEventType().equals("Edit")){
			sb.appendHtmlConstant("<div style = \"background-color:#EDF7FF; height = "+height+"; width:  "+width+"; border: 1px solid #000000\">");
		} else if(value.getEventType().equals("Submit")){
			sb.appendHtmlConstant("<div style = \"background-color:#ff4500; height = "+height+"; width:  "+width+"; border: 1px solid #000000\">");
		} else if(value.getEventType().equals("Undo")){
			sb.appendHtmlConstant("<div style = \"background-color:#ee82ee;  height = "+height+"; width:  "+width+"; border: 1px solid #000000\">");
		}else{
			sb.appendHtmlConstant("<div style = \"background-color:#ffffff;  height = "+height+"; width: "+width+"; border: 1px solid #000000\">");
		}
		sb.appendHtmlConstant("<table><tr><td>");
		sb.appendHtmlConstant(Integer.toString(context.getIndex())+" ");
		if(value.getEventType().equals("Remove") || value.getEventType().equals("RemoveArtifacts")){
			sb.appendHtmlConstant("Remove");
		}
		else{
			sb.appendHtmlConstant(value.getEventType());
		}
		sb.appendHtmlConstant("</td><td>");
		sb.appendHtmlConstant(Integer.toString(value.getEditEventId()));
		sb.appendHtmlConstant("</td></tr><tr><td>");
		if( value.getAfterEdit() != null || value.getBeforeEdit() != null){
			//sb.appendHtmlConstant("<div style=\"color: red\">"+this.abbriviate(value.getBeforeEdit(), 10) +"</td></tr><tr><td>=> "+this.abbriviate(value.getBeforeEdit(), 10)+ "</div>");
			sb.appendHtmlConstant("<div style=\"color: red\">"+this.abbriviate(value.getBeforeEdit(), 10)+ "</div>");
		}
		sb.appendHtmlConstant("</td></tr><tr><td></table>");
		sb.appendHtmlConstant( value.getEditDatetime().toString());
		sb.appendHtmlConstant("</div>");
	}

    @Override
    public void onBrowserEvent(Context context, Element parent, EditEvent value, NativeEvent event,
        ValueUpdater<EditEvent> valueUpdater) {
      // Let AbstractCell handle the keydown event.
    	super.onBrowserEvent(context, parent, value, event, valueUpdater);
      // Handle the click event.
      if ("focusin".equals(event.getType())) {
        // Ignore clicks that occur outside of the outermost element.
//        EventTarget eventTarget = event.getEventTarget();
//        if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//          doAction(value, valueUpdater);
//        }
    	  this.setValue(context, parent, value);
    	  System.out.println("focusin");
      }
      if ("focusout".equals(event.getType())) {
          // Ignore clicks that occur outside of the outermost element.
//          EventTarget eventTarget = event.getEventTarget();
//          if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
//            doAction(value, valueUpdater);
//          }
      	  this.setValue(context, parent, value);
      	System.out.println("focusout");
        }
    }


	private String abbriviate(String s, int n){
		if( s != null){
			if ( s.length() > n){
				s = (String) s.subSequence(0, n)+"...";
			}
		}
		return s;
	}
}
