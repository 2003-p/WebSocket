package com.objetdirect.gwt.umlapi.client.editors;


import com.objetdirect.gwt.umlapi.client.artifacts.ActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ActorPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLActor;

public class ActorPartNameFieldEditor extends FieldEditor {

	public ActorPartNameFieldEditor(final UMLCanvas canvas, final ActorPartNameArtifact actorPartNameArtifact) {
		super(canvas, actorPartNameArtifact);
	}

	@Override
	protected void next() {
		// No next part to edit
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		final String newContentWithoutSpaces = newContent.replaceAll(" ", "_");

			final String newName = UMLActor.parseNameOrStereotype(newContentWithoutSpaces);
			if (newName.equals("")) {
				((ActorPartNameArtifact) this.artifact).setActorName("Actor");
			} else {
				//TODO takafumi Class Name Changeed Log
				String oldContent = ((ActorPartNameArtifact) this.artifact).getActorName();
				ActorPartNameArtifact cpna = (ActorPartNameArtifact) this.artifact;
				ActorArtifact actorArtifact = (ActorArtifact)cpna.getNodeArtifact();

				((ActorPartNameArtifact) this.artifact).setActorName(newName);

				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = actorArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "ActorName", "Edit",
						actorArtifact.getClass().getName(), actorArtifact.getId(), null, -1, -1,
						null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}

		((ActorPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
