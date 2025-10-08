package com.objetdirect.gwt.umldrawer.client.viewer;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;

/**
 * @author tanaka
 *
 */
public class EditEventCell_old extends AbstractCell<EditEvent>{
		private String width = "150";
		private String hight = "50";

		@Override
		public void render(Context context, EditEvent value, SafeHtmlBuilder sb) {
			if(value == null){
				return;
				}
			if(value.getEventType().equals("Create")){
				sb.appendHtmlConstant("<div style=\"background-color:#99cc00; width: "+width+"; border: 1px solid #000000\">");
			} else if(value.getEventType().equals("Remove") || value.getEventType().equals("RemoveArtifacts")){
				sb.appendHtmlConstant("<div style = \"background-color:#ffd700; width:  "+width+"; border: 1px solid #000000\">");
			} else if(value.getEventType().equals("Edit")){
				sb.appendHtmlConstant("<div style = \"background-color:#EDF7FF; width:  "+width+"; border: 1px solid #000000\">");
			} else if(value.getEventType().equals("Submit")){
				sb.appendHtmlConstant("<div style = \"background-color:#ff4500; width:  "+width+"; border: 1px solid #000000\">");
			} else if(value.getEventType().equals("Undo")){
				sb.appendHtmlConstant("<div style = \"background-color:#ee82ee; width:  "+width+"; border: 1px solid #000000\">");
			}else{
				sb.appendHtmlConstant("<div style = \" width: 200; border: 1px solid #000000\">");
			}
			System.out.println("EditEventCell:"+value.getEditEvent());
			sb.appendHtmlConstant("<table><tr><td>");
			sb.appendHtmlConstant(Integer.toString(value.getEditEventId()));
			sb.appendHtmlConstant("</td><td>");
			sb.appendHtmlConstant(value.getEventType());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			if( value.getAfterEdit() != null){
				sb.appendHtmlConstant("<div style=\"color: red\">"+value.getBeforeEdit() + "</div>");
				sb.appendHtmlConstant( "<div style=\"color:black\">↓↓↓</div>");
				sb.appendHtmlConstant("<div style=\"color: red\">"+value.getAfterEdit() + "</div>");
			}
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendHtmlConstant( value.getEditDatetime().toString());
			sb.appendHtmlConstant("</td></tr></table>");
			sb.appendHtmlConstant("</div>");

		}
}
