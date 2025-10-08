package com.objetdirect.gwt.umldrawer.server.evaluater;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
import com.objetdirect.gwt.umlapi.client.artifacts.ActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.AssetAndMisUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.AssetArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.InstantiationRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkAssetArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkUseCaseRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisActorArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisAndMisActorRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisAndSecurityUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MisUseCaseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.SecurityUseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndActorRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndMisUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseAndSecurityUseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCaseArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UseCaseRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLActor;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAsset;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisActor;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMisUseCase;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLSecurityUseCase;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLUseCase;

/**
* @author tanaka
*
*/
public class DiagramParser {
	private List<UMLArtifact> artifactList;

	public DiagramParser (){
		artifactList = new LinkedList<UMLArtifact>();
	}

	public List<UMLArtifact> fromURL(final String url, final boolean isForPasting) {

		return  fromURL(url, isForPasting, false,
				Point.getOrigin(), Point.getOrigin(), Point.getOrigin(), null);
	}

	public UMLArtifact getUMLArtifactById(String url, int artifactId){
		fromURL(url, false);
		for(UMLArtifact artifact : this.artifactList){
			if(artifact.getId() == artifactId) return artifact;
		}
		return null;
	}

	public List<UMLArtifact> fromURL(final String url, final boolean isForPasting, final boolean wasACopy,
			Point currentMousePosition, Point copyMousePosition, Point canvasOffset, UMLCanvas canvas) {
		//try {
		artifactList.clear();

		if (!url.equals("AA==")) {
			String diagram = isForPasting ? url : new String (Base64.decodeBase64(url) );
			Point pasteShift = isForPasting ? Point.substract(Point.substract(currentMousePosition, copyMousePosition), canvasOffset) : Point.getOrigin();

			diagram = diagram.substring(0, diagram.lastIndexOf(";"));
			final String[] diagramArtifacts = diagram.split(";");
			System.out.println(diagramArtifacts);

			for (final String artifactWithParameters : diagramArtifacts) {
				if (!artifactWithParameters.equals("")) {
					final String[] artifactAndParameters = artifactWithParameters.split("\\$");
					if (artifactAndParameters.length > 1) {
						final String[] artifactAndId = artifactAndParameters[0].split("]");
						final String[] parameters = artifactAndParameters[1].split("!", -1);
						final String artifact = artifactAndId[1];
						int id = 0;
						//System.out.println("fromURL : "+artifactAndParameters[1]);
						try {
							id = Integer.parseInt(artifactAndId[0].replaceAll("[<>]", ""));
						} catch (final Exception ex) {
							Logger.getGlobal().severe("Parsing url, artifact id is NaN : " + artifactWithParameters + " : " + ex);
						}
						UMLArtifact newArtifact = null;
						if (artifact.equals("Class")) {
							newArtifact = new ClassArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLClass.parseNameOrStereotype(parameters[1]), UMLClass.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] classAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : classAttributes) {
									((ClassArtifact) newArtifact).addAttribute(UMLClassAttribute.parseAttribute(attribute));
								}
							}
							if (parameters[4].length() > 1) {
								final String[] classMethods = parameters[4].substring(0, parameters[4].lastIndexOf("%")).split("%");
								for (final String method : classMethods) {
									((ClassArtifact) newArtifact).addMethod(UMLClassMethod.parseMethod(method));
								}
							}
//TODO MisUc
						} else if (artifact.equals("Uc")) {
							newArtifact = new UseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLUseCase.parseNameOrStereotype(parameters[1]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("MisUc")) {
							newArtifact = new MisUseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLMisUseCase.parseNameOrStereotype(parameters[1]) , UMLMisUseCase.parseNameOrStereotype(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("SecurityUc")) {
							newArtifact = new SecurityUseCaseArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLSecurityUseCase.parseNameOrStereotype(parameters[1]) , UMLSecurityUseCase.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Actor")) {
							newArtifact = new ActorArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLActor.parseNameOrStereotype(parameters[1]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("MisActor")) {
							newArtifact = new MisActorArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLMisActor.parseNameOrStereotype(parameters[1]),  Integer.parseInt(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Asset")) {
							newArtifact = new AssetArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLAsset.parseNameOrStereotype(parameters[1]), UMLAsset.parseNameOrStereotype(parameters[2]) );
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Object")) {
							newArtifact = new ObjectArtifact(UMLObject.parseName(parameters[1]).get(0), (isForPasting && wasACopy ? "CopyOf" : "") + UMLObject.parseName(parameters[1]).get(1),
									UMLObject.parseStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] objectAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : objectAttributes) {
									((ObjectArtifact) newArtifact).addAttribute(UMLObjectAttribute.parseAttribute(attribute));
								}
							}

						} else if (artifact.equals("LifeLine")) {
							newArtifact = new LifeLineArtifact((isForPasting && wasACopy ? "CopyOf" : "") + UMLLifeLine.parseName(parameters[1]).get(1), UMLLifeLine.parseName(parameters[1]).get(0));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Note")) {
							newArtifact = new NoteArtifact(parameters[1]);
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("LinkNote")) {
							Integer noteId = 0;
							Integer targetId = 0;
							try {
								noteId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkNoteArtifact((NoteArtifact) UMLArtifact.getArtifactById(noteId), UMLArtifact.getArtifactById(targetId));

						} else if (artifact.equals("LinkAsset")) {
							Integer assetId = 0;
							Integer targetId = 0;
							try {
								assetId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkAssetArtifact((AssetArtifact) UMLArtifact.getArtifactById(assetId), UMLArtifact.getArtifactById(targetId));

						}else if (artifact.equals("LinkClassRelation")) {
							Integer classId = 0;
							Integer relationId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkClassRelationArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ClassRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));

						}  else if (artifact.equals("LinkUseCaseRelation")) {
							Integer useCaseId = 0;
							Integer relationId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkUseCaseRelationArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(UseCaseRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));

						}  else if (artifact.equals("ClassRelationLink")) {
							Integer classLeftId = 0;
							Integer classRigthId = 0;
							try {
								classLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								classRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ClassRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classLeftId),
									(ClassArtifact) UMLArtifact.getArtifactById(classRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ClassRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ClassRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ClassRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ClassRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((ClassRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((ClassRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((ClassRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((ClassRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((ClassRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((ClassRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("UseCaseRelationLink")) {
							Integer useCaseLeftId = 0;
							Integer useCaseRigthId = 0;
							try {
								useCaseLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								useCaseRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseCaseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseLeftId),
									(UseCaseArtifact) UMLArtifact.getArtifactById(useCaseRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseCaseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseCaseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseCaseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseCaseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseCaseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseCaseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseCaseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseCaseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("MisUseCaseRelationLink")) {
							Integer MisUseCaseLeftId = 0;
							Integer MisUseCaseRigthId = 0;
							try {
								MisUseCaseLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								MisUseCaseRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisUseCaseRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(MisUseCaseLeftId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(MisUseCaseRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisUseCaseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("UseAndSecurityUseRelationLink")) {
							Integer useCaseId = 0;
							Integer securityUseCaseId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								securityUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndSecurityUseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(SecurityUseCaseArtifact) UMLArtifact.getArtifactById(securityUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndSecurityUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("MisAndSecurityUseRelationLink")) {
							Integer misUseCaseId = 0;
							Integer securityUseCaseId = 0;
							try {
								misUseCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								securityUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisAndSecurityUseRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId),
									(SecurityUseCaseArtifact) UMLArtifact.getArtifactById(securityUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisAndSecurityUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("UseAndActorRelationLink")) {
							Integer useCaseId = 0;
							Integer actorId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								actorId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndActorRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(ActorArtifact) UMLArtifact.getArtifactById(actorId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndActorRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndActorRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndActorRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndActorRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}  else if (artifact.equals("MisAndMisActorRelationLink")) {
							Integer misUseCaseId = 0;
							Integer misActorId = 0;
							try {
								misUseCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misActorId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MisAndMisActorRelationLinkArtifact((MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId),
									(MisActorArtifact) UMLArtifact.getArtifactById(misActorId), LinkKind.getRelationKindFromName(parameters[2]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((MisAndMisActorRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("AssetAndMisUseRelationLink")) {
							Integer assetId = 0;
							Integer misUseCaseId = 0;
							try {
								assetId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new AssetAndMisUseRelationLinkArtifact((AssetArtifact) UMLArtifact.getArtifactById(assetId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((AssetAndMisUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						}else if (artifact.equals("UseAndMisUseRelationLink")) {
							Integer useCaseId = 0;
							Integer misUseCaseId = 0;
							try {
								useCaseId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								misUseCaseId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new UseAndMisUseRelationLinkArtifact((UseCaseArtifact) UMLArtifact.getArtifactById(useCaseId),
									(MisUseCaseArtifact) UMLArtifact.getArtifactById(misUseCaseId), LinkKind.getRelationKindFromName(parameters[2]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((UseAndMisUseRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("ObjectRelationLink")) {
							Integer objectLeftId = 0;
							Integer objectRigthId = 0;
							try {
								objectLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ObjectRelationLinkArtifact((ObjectArtifact) UMLArtifact.getArtifactById(objectLeftId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ObjectRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ObjectRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ObjectRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ObjectRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("MessageLink")) {
							Integer lifeLineLeftId = 0;
							Integer lifeLineRigthId = 0;
							try {
								lifeLineLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								lifeLineRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MessageLinkArtifact((LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineLeftId),
									(LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MessageLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MessageLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MessageLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MessageLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("InstantiationRelationLink")) {
							Integer classId = 0;
							Integer objectId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new InstantiationRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectId), LinkKind.INSTANTIATION);
						}
						if (newArtifact != null) {
							newArtifact.setId(id);
							//canvas.add(newArtifact);
							this.artifactList.add(newArtifact);
						}
						if(isForPasting) {
							//canvas.selectArtifact(newArtifact);

						}
					}
				}
			}
		}
		//} catch (final Exception ex) {
		//	Logger.getGlobal().severe("There was a problem reading diagram in url : ");
		//}
		return artifactList;
	}
}
