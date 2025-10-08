package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCasePartNameArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLUseCase;


public class UseCasePartNameFieldEditor extends FieldEditor{


	public UseCasePartNameFieldEditor(final UMLCanvas canvas,final UseCasePartNameArtifact usecasePartNameArtifact){
		super(canvas , usecasePartNameArtifact);
	}

	@Override
	protected void next() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected boolean updateUMLArtifact(String newContent) {
		final String newContentWithoutSpaces = newContent.replaceAll(" ", "_");
		final String newName = UMLUseCase.parseNameOrStereotype(newContentWithoutSpaces);
		if (newName.equals("")) {
			((UseCasePartNameArtifact) this.artifact).setUseCaseName("UseCase");
		} else {
			//TODO takafumi Class Name Changeed Log
			String oldContent = ((UseCasePartNameArtifact) this.artifact).getUseCaseName();
			UseCasePartNameArtifact cpna = (UseCasePartNameArtifact) this.artifact;
			UseCaseArtifact useCaseArtifact = (UseCaseArtifact)cpna.getNodeArtifact();

			((UseCasePartNameArtifact) this.artifact).setUseCaseName(newName);//入力された名前を格納する

			//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
			//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//			int classId = classArtifact.getId();
			MyLoggerExecute.registEditEvent(-1, "ClassName", "Edit",
					useCaseArtifact.getClass().getName(), useCaseArtifact.getId(), null, -1, -1,
					null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
		((UseCasePartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}

}