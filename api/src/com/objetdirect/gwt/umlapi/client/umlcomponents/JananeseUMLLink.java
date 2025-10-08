/**
 *
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class JananeseUMLLink {
	/**
	 * This enumeration lists all the relations type between two {@link UMLClass}es
	 *
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 *
	 */
	public enum LinkKind {
		/**
		 * Aggregation relation
		 */
		SIMPLE_RELATION(RELATIONSHIP.getMessage(), LinkAdornment.NONE, LinkAdornment.NONE, "1", "0..*", LinkStyle.SOLID, Type.HYBRID),

		AGGREGATION_RELATION(AGGREGATION.getMessage(), LinkAdornment.SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID, Type.HYBRID),
		/**
		 * Association relation
		 */
		ASSOCIATION_RELATION(ASSOSIATION.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "0..*", "0..*", LinkStyle.SOLID, Type.HYBRID),
		/**
		 * Composition relation
		 */
		COMPOSITION_RELATION(COMPOSITION.getMessage(), LinkAdornment.INVERTED_SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID, Type.HYBRID),
		/**
		 * Dependency relation
		 */
		DEPENDENCY_RELATION(DEPENDENCY.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.DASHED, Type.HYBRID),
		/**
		 * Generalization relation
		 */
		GENERALIZATION_RELATION(GENERALIZATION.getMessage(), LinkAdornment.SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.CLASS),
		/**
		 * Realization relation
		 */
		REALIZATION_RELATION(REALIZATION.getMessage(), LinkAdornment.SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.LONG_DASHED, Type.CLASS),
		/**
		 * Asynchronous message
		 */
		ASYNCHRONOUS_MESSAGE(ASYNCRONOUS.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.SEQUENCE),
		/**
		 * Synchronous message
		 */
		SYNCHRONOUS_MESSAGE(SYNCRONOUS.getMessage(), LinkAdornment.INVERTED_SOLID_ARROW, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.SEQUENCE),
		/**
		 * Reply message
		 */
		REPLY_MESSAGE(REPLY.getMessage(), LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.DASHED, Type.SEQUENCE),
		/**
		 * Object Creation message
		 */
		OBJECT_CREATION_MESSAGE(OBJECT_CREATION.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED, Type.SEQUENCE),
		/**
		 * Lost message
		 */
		LOST_MESSAGE(LOST.getMessage(), LinkAdornment.INVERTED_SOLID_CIRCLE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.SEQUENCE),
		/**
		 * Found message
		 */
		FOUND_MESSAGE(FOUND.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.INVERTED_SOLID_CIRCLE, "", "", LinkStyle.SOLID, Type.SEQUENCE),
		/**
		 * Note relation
		 */
		NOTE(NOTE_LINK.getMessage(), LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.HYBRID),
		/**
		 * Class relation
		 */
		CLASSRELATION(CLASS_RELATION.getMessage(), LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID, Type.CLASS),
		/**
		 * Class Object instantiation
		 */
		INSTANTIATION(INSTANTIATION_TR.getMessage(), LinkAdornment.WIRE_ARROW, LinkAdornment.NONE, "", "", LinkStyle.DASHED_DOTTED, Type.HYBRID);

		/**
		 * Static getter of a {@link LinkKind} by its name
		 *
		 * @param relationKindName
		 *            The name of the {@link LinkKind} to retrieve
		 * @return The {@link LinkKind} that has relationKindName for name or null if not found
		 */
		public static LinkKind getRelationKindFromName(final String relationKindName) {
			for (final LinkKind relationKind : LinkKind.values()) {
				if (relationKind.getName().equals(relationKindName)) {
					return relationKind;
				}
			}
			return null;
		}

		private String			name;
		private LinkAdornment	defaultLeftAdornment;
		private LinkAdornment	defaultRightAdornment;
		private String			defaultLeftCardinality;
		private String			defaultRightCardinality;
		private LinkStyle		defaultLinkStyle;

		private Type			requiredType;

		private LinkKind(final String name, final LinkAdornment defaultLeftAdornment, final LinkAdornment defaultRightAdornment,
				final String defaultLeftCardinality, final String defaultRightCardinality, final LinkStyle defaultLinkStyle, final Type requiredType) {

			this.name = name;
			this.defaultLeftAdornment = defaultLeftAdornment;
			this.defaultRightAdornment = defaultRightAdornment;
			this.defaultLeftCardinality = defaultLeftCardinality;
			this.defaultRightCardinality = defaultRightCardinality;
			this.defaultLinkStyle = defaultLinkStyle;
			this.requiredType = requiredType;
		}

		/**
		 * Getter for the defaultLeftAdornment
		 *
		 * @return the defaultLeftAdornment
		 */
		public LinkAdornment getDefaultLeftAdornment() {
			return this.defaultLeftAdornment;
		}

		/**
		 * Getter for the defaultLeftCardinality
		 *
		 * @return the defaultLeftCardinality
		 */
		public String getDefaultLeftCardinality() {
			return this.defaultLeftCardinality;
		}

		/**
		 * Getter for the defaultLinkStyle
		 *
		 * @return the defaultLinkStyle
		 */
		public LinkStyle getDefaultLinkStyle() {
			return this.defaultLinkStyle;
		}

		/**
		 * Getter for the defaultRightAdornment
		 *
		 * @return the defaultRightAdornment
		 */
		public LinkAdornment getDefaultRightAdornment() {
			return this.defaultRightAdornment;
		}

		/**
		 * Getter for the defaultRightCardinality
		 *
		 * @return the defaultRightCardinality
		 */
		public String getDefaultRightCardinality() {
			return this.defaultRightCardinality;
		}

		/**
		 * Getter for the name
		 *
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Tells if a {@link LinkKind} can be on a diagram depending on its type
		 *
		 * @param diagramType
		 *            The current diagram type
		 *
		 * @return True if this {@link LinkKind} can be put on this diagramType
		 */
		public boolean isForDiagram(final Type diagramType) {
			if (this == INSTANTIATION) {
				return diagramType.isHybridType();
			}
			if (this.requiredType.isHybridType()) {
				return diagramType.isClassOrObjectType();
			}
			return diagramType.equals(this.requiredType);
		}
	}

	protected LinkKind	linkKind;

	/**
	 * Constructor of UMLLink
	 *
	 * @param linkKind
	 *            The {@link LinkKind} of the {@link JananeseUMLLink}
	 */
	public JananeseUMLLink(final LinkKind linkKind) {
		this.linkKind = linkKind;
	}

	/**
	 * Getter for the linkKind
	 *
	 * @return the linkKind
	 */
	public LinkKind getLinkKind() {
		return this.linkKind;
	}

	/**
	 * Setter for the linkKind
	 *
	 * @param linkKind
	 *            the linkKind to set
	 */
	public void setLinkKind(final LinkKind linkKind) {
		this.linkKind = linkKind;
	}

}
