package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerForStudentBase;

public class ReflectionListPanel extends ScrollPanel{
	private List<Reflection> reflectionList;
	private VerticalPanel verticalPanel;
	private int width;
	private int height;
	private ViewerForStudentBase viewerForStudentBase;
	private int panelHeight = 500;
	private int panelSpacing = 2;

	public ReflectionListPanel(ViewerForStudentBase viewerForStudentBase, String studentId, int exerciseId, int width, int height) {
		super();
		this.viewerForStudentBase = viewerForStudentBase;
		this.width = width;
		this.height = height;
		this.setSize(Integer.toString(this.width), Integer.toString(this.height));
		this.verticalPanel = new VerticalPanel();
		this.verticalPanel.setSize(Integer.toString(this.width-20), Integer.toString(this.height));
		this.verticalPanel.setSpacing(panelSpacing);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.add(this.verticalPanel);
		getReflectionList(studentId, exerciseId);
	}

	private void getReflectionList(String studentId, int exerciseId){
		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getReflectionList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<Reflection>> callback = new AsyncCallback<List<Reflection>>(){
			public void onSuccess(List<Reflection> result){
				reflectionList = result;
				for(Reflection r : reflectionList){
					ReflectionPanel rp = new ReflectionPanel(ReflectionListPanel.this, r, width-25,  panelHeight);
					verticalPanel.add( rp );
					createAndRegisterDropControllerForReflection(rp);
				}
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getReflectionList(studentId, exerciseId, callback );
	}


	public ReflectionPanel addReflection(Reflection reflection){
		ReflectionPanel rp = new ReflectionPanel(this, reflection, this.width-25, panelHeight);
		this.reflectionList.add(reflection);
		System.out.println("@addReflection"+reflection.getReflectionId());
		this.verticalPanel.add( rp );
		//Call after add reflectionPanel to the parent
		createAndRegisterDropControllerForReflection(rp);
		return rp;
	}

	public ReflectionPanel addReflection(int index, Reflection reflection){
		ReflectionPanel rp = new ReflectionPanel(this, reflection, this.width-25, panelHeight);
		this.reflectionList.add(index, reflection);
		if(index <= 0){
			this.verticalPanel.add( rp );
		}else{
			this.verticalPanel.insert( rp, index - 1);
		}
		//Call after add reflectionPanel to the parent
		createAndRegisterDropControllerForReflection(rp);
		return rp;
	}
	public void removeReflection(int index){
		this.reflectionList.remove(index);
		ReflectionPanel rp = (ReflectionPanel)(this.verticalPanel.getWidget(index) );
		unregisterDropControllerForReflection(rp);
		this.verticalPanel.remove(index);
	}
	public ReflectionPanel getReflectionPanel(int index){
		return (ReflectionPanel) this.verticalPanel.getWidget(index);
	}
	public Reflection getReflection(int index){
		return this.reflectionList.get(index);
	}
	public void setReflection(int index, Reflection reflection){
		this.reflectionList.set(index, reflection);
		ReflectionPanel rp = new ReflectionPanel(this, reflection, this.width-25, panelHeight);
		this.verticalPanel.insert(rp , index);
		//Call after add reflectionPanel to the parent
		createAndRegisterDropControllerForReflection(rp);
		rp = (ReflectionPanel)(this.verticalPanel.getWidget(index) );
		unregisterDropControllerForReflection(rp);
		this.verticalPanel.remove(index);

	}

	public int getSelectedIndex(){
		for(int i=0 ; i<verticalPanel.getWidgetCount() ; i++){
			ReflectionPanel r = (ReflectionPanel) verticalPanel.getWidget(i);
			if(r.isSeledted()){
				return i;
			}
		}
		return -1;
	}

	public int size(){
		return reflectionList.size();
	}

	public void selectionChange(int selectedIndex, boolean withScroll){
		for(int i = 0 ; i < verticalPanel.getWidgetCount() ; i++){
			if( i == selectedIndex ) continue;
			ReflectionPanel rp = (ReflectionPanel) verticalPanel.getWidget(i);
			rp.deselect();
		}
		if(withScroll){
			if(selectedIndex == 0) this.setVerticalScrollPosition(0);
			else
				this.setVerticalScrollPosition(selectedIndex*(panelHeight+panelSpacing));
		}
	}

	public int indexOfReflectionId(int reflectionId){
		System.out.println("@indexOfReflectionId reflectionList.size="+this.reflectionList.size()+" reflectionId="+reflectionId);
		for (Reflection r : this.reflectionList){
			System.out.println(r.getReflectionId());
			if (r.getReflectionId()==reflectionId) return  this.reflectionList.indexOf(r);
		}
		return -1;
	}


	private void createAndRegisterDropControllerForReflection(ReflectionPanel reflectionPanel){
		ReflectiopnDropController reflectionDropController = new ReflectiopnDropController(reflectionPanel.getBase(), reflectionPanel);
		viewerForStudentBase.getDragController().registerDropController(reflectionDropController);
		reflectionPanel.setReflectionDropController(reflectionDropController);
	}
	private void unregisterDropControllerForReflection(ReflectionPanel reflectionPanel){
		ReflectiopnDropController reflectionDropController = (ReflectiopnDropController) reflectionPanel.getReflectionDropController();
		reflectionPanel.setReflectionDropController(null);
		viewerForStudentBase.getDragController().unregisterDropController(reflectionDropController);
	}

	public ReflectionPanel getReflectionPanelByReflection(Reflection r){
		ReflectionPanel rp;
		int indexOfR = this.reflectionList.indexOf(r);
		rp = (ReflectionPanel) this.verticalPanel.getWidget(indexOfR);
		return rp;
	}

	public boolean isExistReflectionForEvent(EditEvent e){
		boolean exist= false;
		for( Reflection r : this.reflectionList ){
			if(r.getOccurrencePoint().getEditEventId() == e.getEditEventId()){
				exist = true;
			}
		}
		return exist;
	}

	public List<Reflection> getReflectionList() {
		return reflectionList;
	}

	public void setReflectionList(List<Reflection> reflectionList) {
		this.reflectionList = reflectionList;
	}

	public ViewerForStudentBase getViewerForStudentBase() {
		return viewerForStudentBase;
	}

	public void setViewerForStudentBase(ViewerForStudentBase viewerForStudentBase) {
		this.viewerForStudentBase = viewerForStudentBase;
	}

	public void selectByEventId(int eventId, boolean withScroll, boolean withSelectEvent) {
		if(reflectionList != null){
			for(Reflection r : this.reflectionList){
				if(r.getOccurrencePoint().getEditEventId() == eventId){
					this.getReflectionPanelByReflection(r).select(withScroll, withSelectEvent);
					return;
				}
			}
		}
		this.selectionChange(-1, false);

	}

}
