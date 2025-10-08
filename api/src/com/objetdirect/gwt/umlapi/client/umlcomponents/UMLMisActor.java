package com.objetdirect.gwt.umlapi.client.umlcomponents;
import java.util.logging.Logger;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public class UMLMisActor extends UMLNode{

	public static String parseNameOrStereotype(final String stringToParse){
		if(stringToParse.equals("")){
			return "";
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(stringToParse);
		try{
			final LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid class name/stereotype : " + stringToParse + " doesn't repect uml conventions");
		}
			return tk.getContent();
	}catch(final GWTUMLAPIException e){
		Logger.getGlobal().severe(e.getMessage());
	}
		return "";
	}

	private String name;

	public UMLMisActor(final String name){
		super();
		this.name = name;
	}

	public final String getName(){
		return this.name;
	}

	public final void setName(final String name){
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setType(String type) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setVisibility(UMLVisibility visibility) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean hasGfxObjectKey(String haskey) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void setStrokeBLACK(String key) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setStrokeRED(String key) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	@Override
	public void setStrokeBLUE(String key) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setVisibilityGfxObject(GfxObject visigility) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setNameGfxObject(GfxObject name) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setTypeGfxObject(GfxObject type) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getDiffVisibilityKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getDiffNameKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getDiffTypeKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}