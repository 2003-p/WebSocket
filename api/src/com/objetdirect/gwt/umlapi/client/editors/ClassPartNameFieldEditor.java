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


import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class ClassPartNameFieldEditor extends FieldEditor {

	private final boolean	isTheStereotype;

	/**
	 * Constructor of
	 *
	 * @param canvas
	 * @param classPartNameArtifact
	 * @param isTheStereotype
	 */
	public ClassPartNameFieldEditor(final UMLCanvas canvas, final ClassPartNameArtifact classPartNameArtifact, final boolean isTheStereotype) {
		super(canvas, classPartNameArtifact);
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
			final String newStereotype = UMLClass.parseNameOrStereotype(newContentWithoutSpaces.replaceAll("[«»]", ""));
			if (newStereotype.equals("")) {
				((ClassPartNameArtifact) this.artifact).setStereotype("");
			} else {
				((ClassPartNameArtifact) this.artifact).setStereotype("«" + newStereotype + "»");
			}
			((ClassPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		} else {
			final String newName = UMLClass.parseNameOrStereotype(newContentWithoutSpaces);
			String oldContent = ((ClassPartNameArtifact) this.artifact).getClassName();
			ClassPartNameArtifact cpna = (ClassPartNameArtifact) this.artifact;
			ClassArtifact classArtifact = (ClassArtifact)cpna.getNodeArtifact();
			int classId = classArtifact.getId();

			((ClassPartNameArtifact) this.artifact).setClassName(newName);
			((ClassPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();

			if (newName.equals("")) {
				((ClassPartNameArtifact) this.artifact).setClassName("Class");
				MyLoggerExecute.registEditEvent(-1, "ClassName", "Remove",
						classArtifact.getClass().getName(), classId, null, -1, -1,
						null, oldContent, newContent, null, UMLArtifact.getIdCount());
				MyLoggerExecute.registEditEvent(-1, "ClassName", "RemoveArtifacts",
						classArtifact.getClass().getName(), classId, null, -1, -1,
						null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
			} else {
				//TODO takafumi Class Name Changeed Log
				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
				if(oldContent.equals(newContent)){
					//Nothing to do
				}
				else if(oldContent.replaceAll("\\s", "").matches("Class"+"[0-9]*")){
				MyLoggerExecute.registEditEvent(-1, "ClassName", "Create",
						cpna.getClass().getName(), classArtifact.getId(), null, -1, -1,
						null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
				}else {
					MyLoggerExecute.registEditEvent(-1, "ClassName", "Edit",
							cpna.getClass().getName(), classArtifact.getId(), null, -1, -1,
							null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
				}

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}

		return false;
	}
}
