
package com.objetdirect.gwt.umlapi.client.editors;


import com.objetdirect.gwt.umlapi.client.artifacts.AssetArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.AssetPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAsset;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class AssetPartNameFieldEditor extends FieldEditor {

	private final boolean	isTheStereotype;

	/**
	 * Constructor of
	 *
	 * @param canvas
	 * @param assetPartNameArtifact
	 * @param isTheStereotype
	 */
	public AssetPartNameFieldEditor(final UMLCanvas canvas, final AssetPartNameArtifact assetPartNameArtifact, final boolean isTheStereotype) {
		super(canvas, assetPartNameArtifact);
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
			final String newStereotype = UMLAsset.parseNameOrStereotype(newContentWithoutSpaces.replaceAll("[«»]", ""));
			if (newStereotype.equals("")) {
				((AssetPartNameArtifact) this.artifact).setStereotype("«SecurityProperty»");
			} else {
				String oldContent = ((AssetPartNameArtifact) this.artifact).getStereotype();
				AssetPartNameArtifact cpna = (AssetPartNameArtifact) this.artifact;
				AssetArtifact assetArtifact = (AssetArtifact)cpna.getNodeArtifact();

				((AssetPartNameArtifact) this.artifact).setStereotype("«" + newStereotype + "»");
				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = assetArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "AssetStereoType", "Edit",
						assetArtifact.getClass().getName(), assetArtifact.getId(), null, -1, -1,
						null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
			}
		} else {
			final String newName = UMLAsset.parseNameOrStereotype(newContentWithoutSpaces);
			if (newName.equals("")) {
				((AssetPartNameArtifact) this.artifact).setAssetName("Asset");
			} else {
				//TODO takafumi Class Name Changeed Log
				String oldContent = ((AssetPartNameArtifact) this.artifact).getAssetName();
				AssetPartNameArtifact cpna = (AssetPartNameArtifact) this.artifact;
				AssetArtifact assetArtifact = (AssetArtifact)cpna.getNodeArtifact();

				((AssetPartNameArtifact) this.artifact).setAssetName(newName);
				//MyLoggerExecute.registEditEvent("ClassName:"+classArtifact.getId()+":"+cpna.getClassName()+":"+oldContent+":"+newName, canvas.toUrl());
				//System.out.println("EditClassName:"+oldContent+" ==> "+newName);
//				int classId = assetArtifact.getId();
				MyLoggerExecute.registEditEvent(-1, "AssetName", "Edit",
						assetArtifact.getClass().getName(), assetArtifact.getId(), null, -1, -1,
						null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}
		((AssetPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
