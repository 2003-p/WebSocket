package com.objetdirect.gwt.umlapi.client.editors;


import com.objetdirect.gwt.umlapi.client.artifacts.MisActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisActorPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLActor;

public class MisActorPartNameFieldEditor extends FieldEditor {

	public MisActorPartNameFieldEditor(final UMLCanvas canvas, final MisActorPartNameArtifact misactorPartNameArtifact) {
		super(canvas, misactorPartNameArtifact);
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
				((MisActorPartNameArtifact) this.artifact).setMisActorName("MisActor");
			} else {
				//TODO takafumi Class Name Changeed Log
				String oldContent = ((MisActorPartNameArtifact) this.artifact).getMisActorName();
				MisActorPartNameArtifact cpna = (MisActorPartNameArtifact) this.artifact;
				MisActorArtifact misactorArtifact = (MisActorArtifact)cpna.getNodeArtifact();

				((MisActorPartNameArtifact) this.artifact).setMisActorName(newName);

				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = misactorArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "MisActorName", "Edit",
						misactorArtifact.getClass().getName(), misactorArtifact.getId(), null, -1, -1,
						null, oldContent, newName, this.canvas.toUrl(), UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}

		((MisActorPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
