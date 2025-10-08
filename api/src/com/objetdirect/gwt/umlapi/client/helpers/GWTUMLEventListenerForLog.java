package com.objetdirect.gwt.umlapi.client.helpers;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

public class GWTUMLEventListenerForLog implements GWTUMLEventListener {

	public GWTUMLEventListenerForLog(){
	}

	@Override
	public boolean onDeleteUMLArtifact(UMLArtifact umlArtifact) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("FromEventListener onDelete:"+umlArtifact.toURL());
		return true;
	}

	@Override
	public boolean onEditUMLArtifact(UMLArtifact umlArtifact) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("FromEventListener onEdit:"+umlArtifact.toURL());
		return true;
	}

	@Override
	public boolean onLinkKindChange(LinkArtifact linkArtifact, LinkKind oldKind, LinkKind newKind) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("FromEventListener onLinkKindChange:"+linkArtifact.toURL()+" "+oldKind.toString()+"==>"+newKind.toString());
		return true;
	}

	@Override
	public boolean onNewUMLArtifact(UMLArtifact umlArtifact) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("FromEventListener onNewUMLArtifact:"+umlArtifact.toURL());
		return true;
	}

}
