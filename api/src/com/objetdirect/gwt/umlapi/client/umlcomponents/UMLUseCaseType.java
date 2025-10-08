package com.objetdirect.gwt.umlapi.client.umlcomponents;
import java.util.logging.Logger;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;


public class UMLUseCaseType{

	public static UMLUseCaseType parseAttribute(final String attributeToParse) {

		final LexicalAnalyzer lex = new LexicalAnalyzer(attributeToParse);
		String type = "";
		String name = "";
		UMLVisibility visibility = UMLVisibility.PRIVATE; //TODO PACKAGED => PRIVATE
		try {

			LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk != null) && (tk.getType() != LexicalFlag.VISIBILITY)) {
				visibility = UMLVisibility.PRIVATE;
			} else if (tk != null) {
				visibility = UMLVisibility.getVisibilityFromToken(tk.getContent().charAt(0));
				tk = lex.getToken();
			}
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type'");
			}
			name = tk.getContent();
			tk = lex.getToken();

		} catch (final GWTUMLAPIException e) {
			Logger.getGlobal().severe(e.getMessage());
		}
		return new UMLUseCaseType(visibility,type,name);
	}

	protected String name;
	protected String		type;
	protected UMLVisibility visibility;

	protected boolean		validated	= true;


	public UMLUseCaseType(final UMLVisibility visibility , final String type, final String name){ //type==kata name==stereotype
		super();
		this.visibility = visibility;
		this.type = type;
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public UMLVisibility getVisibility() {
		return this.visibility;
	}

	public boolean isValidated() {
		return this.validated;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setValidated(final boolean validated) {
		this.validated = validated;
	}

	public void setVisibility(final UMLVisibility visibility) {
		this.visibility = visibility;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(this.visibility);
		f.append(this.name);
		return f.toString();
	}
}