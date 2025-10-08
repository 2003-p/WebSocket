/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.editors;


import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCasePartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisUseCase;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class MisUseCasePartNameFieldEditor extends FieldEditor {

	private final boolean	isTheStereotype;

	public MisUseCasePartNameFieldEditor(final UMLCanvas canvas, final MisUseCasePartNameArtifact misUseCasePartNameArtifact, final boolean isTheStereotype) {
		super(canvas, misUseCasePartNameArtifact);
		this.isTheStereotype = isTheStereotype;
	}

	@Override
	protected void next() {
		// No next part to edit
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		final String newContentWithoutSpaces = newContent.replaceAll(" ", "_");

		if (this.isTheStereotype) {
			final String newStereotype = UMLMisUseCase.parseNameOrStereotype(newContentWithoutSpaces.replaceAll("[«»]", ""));
			if (newStereotype.equals("")) {
				((MisUseCasePartNameArtifact) this.artifact).setStereotype("«Architecture»");
			} else {
				String oldContent = ((MisUseCasePartNameArtifact) this.artifact).getMisUseCaseName();
				MisUseCasePartNameArtifact cpna = (MisUseCasePartNameArtifact) this.artifact;
				MisUseCaseArtifact misuseCaseArtifact = (MisUseCaseArtifact)cpna.getNodeArtifact();

				((MisUseCasePartNameArtifact) this.artifact).setStereotype("«" + newStereotype + "»");

				MyLoggerExecute.registEditEvent(-1, "MisuseCaseStereotype", "Edit",
						misuseCaseArtifact.getClass().getName(), misuseCaseArtifact.getId(), null, -1, -1,
						null, oldContent, newStereotype, this.canvas.toUrl(), UMLArtifact.getIdCount());
			}
		} else {
			final String newName = UMLMisUseCase.parseNameOrStereotype(newContentWithoutSpaces);
			if (newName.equals("")) {
				((MisUseCasePartNameArtifact) this.artifact).setMisUseCaseName("MisUC");
			} else {
				//TODO takafumi Class Name Changeed Log
				String oldContent = ((MisUseCasePartNameArtifact) this.artifact).getMisUseCaseName();
				MisUseCasePartNameArtifact cpna = (MisUseCasePartNameArtifact) this.artifact;
				MisUseCaseArtifact misuseCaseArtifact = (MisUseCaseArtifact)cpna.getNodeArtifact();

				((MisUseCasePartNameArtifact) this.artifact).setMisUseCaseName(newName);


				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = misusecaseArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "MisuseCaseName", "Edit",
						misuseCaseArtifact.getClass().getName(), misuseCaseArtifact.getId(), null, -1, -1,
						null, oldContent, newName, this.canvas.toUrl(), UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}
		((MisUseCasePartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
