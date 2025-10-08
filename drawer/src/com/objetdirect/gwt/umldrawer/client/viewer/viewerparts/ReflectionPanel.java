package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;

public class ReflectionPanel extends FocusPanel{
	private ReflectionListPanel reflectionListPanel;
	private VerticalPanel base;
	private HorizontalPanel hp;
	private int index;
	private Label indexLabel;
	private Label situationLabel;
	private Label idLabel;

	private int width;
	private int heignt;
	private boolean isSelected;
	private Reflection reflection;
	private DropController reflectionDropController;
	private DoubleClickHandler doubleClickHandler;
	private HandlerRegistration handlerRegistration;
	private String DEFAULT_BG_COLOR = "#88ff88";
	private String SELECTED_BG_COLOR = "#88ffff";
	private String WHITE = "ffffff";

	public ReflectionPanel(){
	}

	public ReflectionPanel(ReflectionListPanel reflectionListPanel, Reflection reflection, int width, int heignt) {
		super();
		this.reflectionListPanel = reflectionListPanel;
		this.base = new VerticalPanel();
		base.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		base.setSpacing(2);
		this.add(base);
		this.width = width;
		this.heignt = heignt;
		this.isSelected = false;
		this.reflection = reflection;
		this.setPixelSize(this.width, this.heignt);
		base.setPixelSize(this.width, this.heignt);

		base.getElement().getStyle().setBackgroundColor(DEFAULT_BG_COLOR);

		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(isSelected) {
					deselect();
				}
				else {
					select(false, true);
				}
			}
		});
		handlerRegistration = this.addDoubleClickHandler(doubleClickHandler = new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				select(false, true);
				System.out.println("onDoubleClick "+ReflectionPanel.this.reflection.getReflectionId());
				ReflectionEditor editor = new ReflectionEditor(ReflectionPanel.this.reflection, ReflectionPanel.this, 300, 500);
				editor.setAutoHideOnHistoryEventsEnabled(true);
				editor.setPopupPosition(350, 10);
				editor.show();
			}
		});
		updateReflectionPanel(ReflectionPanel.this.reflection);
	}

	public void select(boolean withScroll, boolean withSelectEvent) {
		reflectionListPanel.selectionChange( this.reflectionListPanel.getReflectionList().indexOf(this.reflection), withScroll );
		EventListPanel elp = this.reflectionListPanel.getViewerForStudentBase().getEventListPanel();
		if(withSelectEvent){
			elp.setScrollWithSelect(false);
			elp.getEventPanelByEditEventId( this.reflection.getOccurrencePoint().getEditEventId() ).select(true);
		}
		base.getElement().getStyle().setBackgroundColor(SELECTED_BG_COLOR);
		this.isSelected = true;
		reflectionListPanel.getViewerForStudentBase().getDrawerPanelForViewer().setReflectionPanelToUMLCanvas(this);
	}

	public void deselect(){
		base.getElement().getStyle().setBackgroundColor(DEFAULT_BG_COLOR);
		this.isSelected = false;
		reflectionListPanel.getViewerForStudentBase().getDrawerPanelForViewer().setReflectionPanelToUMLCanvas(null);
	}
	public boolean isSeledted(){
		return this.isSelected;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeignt() {
		return heignt;
	}
	public void setHeignt(int heignt) {
		this.heignt = heignt;
	}

	private void updateReflectionPanel(Reflection reflection){

		this.reflection = reflection;
		index=reflectionListPanel.indexOfReflectionId(reflection.getReflectionId());
		base.clear();
		indexLabel = new Label(Integer.toString(index));
		base.add(indexLabel);

		//base.add( new Label( DrawerTextResource.SC_REASON.getMessage() ) );
		//ScrollPanel sp = new ScrollPanel();
		//sp.setHeight("100px");
		//VerticalPanel vp = new VerticalPanel();
		//vp.setSpacing(2);
		//vp.getElement().getStyle().setBackgroundColor(WHITE);
		//sp.add(vp);
		//base.add(sp);
//		if( this.reflection.getReasonList() != null){
//			for(OccurrenceReason or : this.reflection.getReasonList()){
//				vp.add(new Label("・"+or.getReason()) );
//			}
//		}
//		base.add(new Label(reflection.getOtherReason()));

		base.add( new Label( DrawerTextResource.SC_CHECK_ITEM.getMessage()) );
		ScrollPanel sp_ = new ScrollPanel();
		sp_.setHeight("200px");
		VerticalPanel vp_ = new VerticalPanel();
		vp_.setSpacing(2);
		vp_.getElement().getStyle().setBackgroundColor(WHITE);
		sp_.add(vp_);

		base.add(sp_);
		if( this.reflection.getReasonList() != null){
			for(CheckItem ci : this.reflection.getCheckedItemList()){
				vp_.add(new Label("・"+ci.getCheckItem()) );
			}
		}

		base.add( new Label( DrawerTextResource.SC_THINKINGS.getMessage() ) );
		base.add(new Label(reflection.getImagedSituation()));

		base.add( new Label( DrawerTextResource.SC_ERROR_DETECTION.getMessage()+reflection.isThereProblem()) );
		base.add(new Label(reflection.getDiscoveredProblems()));

//		hp = new HorizontalPanel();
//		base.add(hp);
//		VerticalPanel vp00 = new VerticalPanel();
//		VerticalPanel vp01 = new VerticalPanel();
//		hp.add(vp00);
//		hp.add(vp01);
//		vp00.add( new Label( DrawerTextResource.SC_ERROR_CORRECTION_EVENT.getMessage() ) );
//		vp01.add( new Label( DrawerTextResource.SC_TARGET_ELEMENTS.getMessage() ) );
//		ScrollPanel sp1 = new ScrollPanel();
//		ScrollPanel sp2 = new ScrollPanel();
//		sp1.setHeight("130px");
//		sp2.setHeight("130px");
//		VerticalPanel vp1 = new VerticalPanel();
//		VerticalPanel vp2 = new VerticalPanel();
//		vp1.setSpacing(2);
//		vp2.setSpacing(2);
//		sp1.add(vp1);
//		sp2.add(vp2);
//
//		vp00.add(sp1);
//		vp01.add(sp2);


//		if(this.reflection.getModificationEventList() != null){
//			for(final EditEvent e : this.reflection.getModificationEventList()){
//				Button eButton = new Button( e.getEventType()+ ":" + abbriviate( e.getAfterEdit(), 5, true )){
//				    public void onBrowserEvent(Event event){
//				        DOM.eventCancelBubble( event, true );
//				        super.onBrowserEvent( event );
//				    }
//				};
//				eButton.addClickHandler(new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						ReflectionPanel.this.reflectionListPanel.getViewerForStudentBase().getEventListPanel().getEventPanelByEditEventId(e.getEditEventId()).select(true);
//					}
//				});
//				vp1.add( eButton );
//			}
//		}


//		if(this.reflection.getTargetPartIdList() != null){
//			for(final Integer id : this.reflection.getTargetPartIdList()){
//				String type="";
//				String name="";
//				CanvasUrlManager cum = new CanvasUrlManager();
//				UMLArtifact artifact = cum.getUMLArtifactById(this.reflection.getOccurrencePoint().getCanvasUrl(), id);
//				assert (artifact!=null);
//				String classNameOfArtifact = artifact.getClass().getName();
//
//				System.out.println(classNameOfArtifact);
//				String fullPackageName = "com.objetdirect.gwt.umlapi.client.artifacts.";
//				if( classNameOfArtifact.equals(fullPackageName+"ClassArtifact") ){
//					type = "";
//					name = ((ClassArtifact) artifact).getName();
//				}
//				if( classNameOfArtifact.equals(fullPackageName+"ClassRelationLinkArtifact") ){
//					type = "Rel.: ";
//					name = ((ClassRelationLinkArtifact) artifact).getName();
//				}
//
//				final TargetButton targetButton = new TargetButton( id, type + abbriviate(name, 5, true )){
//				    public void onBrowserEvent(Event event){
//				        DOM.eventCancelBubble( event, true );
//				        super.onBrowserEvent( event );
//				    }
//				};
//				targetButton.addClickHandler(new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						int idx = ReflectionPanel.this.reflection.getTargetPartIdList().indexOf(targetButton.getId() );
//						ReflectionPanel.this.reflection.getTargetPartIdList().remove(idx);
//						updateByNewReflection(ReflectionPanel.this.reflection);
//					}
//				});
//				vp2.add( targetButton );
//			}
//		}
		handlerRegistration.removeHandler();
		handlerRegistration = this.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				select(false, true);
				System.out.println("onDoubleClick "+ReflectionPanel.this.reflection.getReflectionId());
				ReflectionEditor editor = new ReflectionEditor(ReflectionPanel.this.reflection, ReflectionPanel.this, 300, 500);
				editor.setAutoHideOnHistoryEventsEnabled(true);
				editor.setPopupPosition(350, 10);
				editor.show();
			}
		});

		System.out.println("updated="+this.reflection.getReflectionId());
	}

	public void updateByNewReflection(Reflection reflection){
		reflection.setStudentId(reflectionListPanel.getViewerForStudentBase().getStudentId());
		reflection.setExerciseId(reflectionListPanel.getViewerForStudentBase().getExerciseId());
		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "addReflection";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<Reflection> callback = new AsyncCallback<Reflection>(){
			public void onSuccess(Reflection result){
				int idx = reflectionListPanel.indexOfReflectionId( ReflectionPanel.this.reflection.getReflectionId() );
				System.out.println("ReflectionPanel.this.reflection.getReflectionId() "+ReflectionPanel.this.reflection.getReflectionId()+"; "+idx);
				updateReflectionPanel(result);
				if(idx >= 0){
					reflectionListPanel.setReflection(idx, result);
					System.out.println("@updateByNewReflection if"+result.getReflectionId());
				}
				else{
					reflectionListPanel.addReflection(result);
					System.out.println("@updateByNewReflection else"+result.getReflectionId());
				}

				System.out.println("updateBy "+result.getReflectionId());
			}
			public void onFailure(Throwable caught){
			}
		};
		System.out.println("@beforSendReflection "+reflection.getReflectionId());
		async.addReflection(reflection, callback );
	}

	class TargetButton extends Button{
		private int id;

		public TargetButton(int id, String string) {
			super(string);
			setId(id);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	private String abbriviate(String s, int n, boolean withPoint){
		if( s != null){
			if ( s.length() > n){
				s = (String) s.subSequence(0, n);
				if( withPoint ) s=s+"...";
			}

		}
		return s;
	}

	public ReflectionListPanel getReflectionListPanel() {
		return reflectionListPanel;
	}

	public void setReflectionListPanel(ReflectionListPanel parent) {
		this.reflectionListPanel = parent;
	}

	public Reflection getReflection() {
		return reflection;
	}

	public void setReflection(Reflection reflection) {
		this.reflection = reflection;
	}

	public DropController getReflectionDropController() {
		return reflectionDropController;
	}

	public void setReflectionDropController(DropController reflectionDropController) {
		this.reflectionDropController = reflectionDropController;
	}

	public VerticalPanel getBase() {
		return base;
	}

	public void setBase(VerticalPanel base) {
		this.base = base;
	}



}
