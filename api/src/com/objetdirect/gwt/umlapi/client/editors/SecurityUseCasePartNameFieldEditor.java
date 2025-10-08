package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.SecurityUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.SecurityUseCasePartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLSecurityUseCase;


public class SecurityUseCasePartNameFieldEditor extends FieldEditor {

	private final boolean	isTheStereotype;

	/**
	 * Constructor of
	 *
	 * @param canvas
	 * @param securityusecasePartNameArtifact
	 * @param isTheStereotype
	 */
	public SecurityUseCasePartNameFieldEditor(final UMLCanvas canvas, final SecurityUseCasePartNameArtifact securityUseCasePartNameArtifact, final boolean isTheStereotype) {
		super(canvas, securityUseCasePartNameArtifact);
		this.isTheStereotype = isTheStereotype;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next() No next part to edit
	 */
	@Override
	protected void next() {
		// No next part to edit
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		final String newContentWithoutSpaces = newContent.replaceAll(" ", "_");

		if (this.isTheStereotype) {
			final String newStereotype = UMLSecurityUseCase.parseNameOrStereotype(newContentWithoutSpaces.replaceAll("[«»]", ""));
			if (newStereotype.equals("")) {
				((SecurityUseCasePartNameArtifact) this.artifact).setStereotype("«Architecture»");
			} else {
				String oldContent = ((SecurityUseCasePartNameArtifact) this.artifact).getSecurityUseCaseName();
				SecurityUseCasePartNameArtifact cpna = (SecurityUseCasePartNameArtifact) this.artifact;
				SecurityUseCaseArtifact securityUseCaseArtifact = (SecurityUseCaseArtifact)cpna.getNodeArtifact();


				((SecurityUseCasePartNameArtifact) this.artifact).setStereotype("«" + newStereotype + "»");

				MyLoggerExecute.registEditEvent(-1, "SecurityUseCaseStereotype", "Edit",
						securityUseCaseArtifact.getClass().getName(), securityUseCaseArtifact.getId(), null, -1, -1,
						null, oldContent, newStereotype, this.canvas.toUrl(), UMLArtifact.getIdCount());

			}
		} else {
			final String newName = UMLSecurityUseCase.parseNameOrStereotype(newContentWithoutSpaces);
			if (newName.equals("")) {
				((SecurityUseCasePartNameArtifact) this.artifact).setSecurityUseCaseName("SecurityUC");
			} else {
				//TODO takafumi Class Name Changeed Log
				String oldContent = ((SecurityUseCasePartNameArtifact) this.artifact).getSecurityUseCaseName();
				SecurityUseCasePartNameArtifact cpna = (SecurityUseCasePartNameArtifact) this.artifact;
				SecurityUseCaseArtifact securityUseCaseArtifact = (SecurityUseCaseArtifact)cpna.getNodeArtifact();

				((SecurityUseCasePartNameArtifact) this.artifact).setSecurityUseCaseName(newName);


				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = securityusecaseArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "SecurityUseCaseName", "Edit",
						securityUseCaseArtifact.getClass().getName(), securityUseCaseArtifact.getId(), null, -1, -1,
						null, oldContent, newName, this.canvas.toUrl(), UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}
		((SecurityUseCasePartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
