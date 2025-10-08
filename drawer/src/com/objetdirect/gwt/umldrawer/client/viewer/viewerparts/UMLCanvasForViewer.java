package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

public class UMLCanvasForViewer extends UMLCanvas{
	private ReflectionPanel reflectionPanel;

	public UMLCanvasForViewer(UMLDiagram uMLDiagram) {
		super(uMLDiagram);
		reflectionPanel = null;
	}
	public UMLCanvasForViewer(final UMLDiagram uMLDiagram, final int width, final int height) {
		super(uMLDiagram, width, height);
		reflectionPanel = null;
	}

	@Override
	public void selectArtifact(final UMLArtifact toSelect) {
		super.selectArtifact(toSelect);
		System.out.println(Session.mode);
		if(reflectionPanel != null && !Session.mode.equals("replay")){
			Reflection reflection = reflectionPanel.getReflection();
			if(DrawerSession.student.getStudentId().equals(reflection.getStudentId())){
				if( reflection.getTargetPartIdList() != null ){
					for(int targetPartId : reflection.getTargetPartIdList()){
						if(targetPartId == toSelect.getId()) return;
					}
				}
				reflection.getTargetPartIdList().add(toSelect.getId());
				//TODO make it
				reflectionPanel.updateByNewReflection(reflection);
			}
			else{
				//Window.alert("許可がありません。");
			}
		}
	}

	public void setReflectionPanel(ReflectionPanel reflectionPanel) {
		this.reflectionPanel = reflectionPanel;
	}
}
