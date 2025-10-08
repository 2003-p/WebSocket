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
package com.objetdirect.gwt.umlapi.client.umlcomponents;
import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;
/**
 * This class represents an UML diagram
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UMLDiagram {

	/**
	 * This enum lists all types of uml diagram that the application can draw
	 *
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 *
	 */
	public enum Type {
		/**
		 * For a class diagram
		 */
		CLASS("class",CLASS_TR.getMessage(), true, false, false, false, 0),
		/**
		 * For an object diagram
		 */
		OBJECT("object",OBJEXT_TR.getMessage(), false, true, false, false, 1),
		/**
		 * For a class and object diagram
		 */
		HYBRID("class and objecct",HYBLID_TR.getMessage(), true, true, false,false, 2),
		/**
		 * For a sequence diagram
		 */
		SEQUENCE("sequence",SEQUENCE_TR.getMessage(), false, false, true, false, 3),

		/**
		 * For a misuse case diagram
		 */
		MISUSECASE("misusecase",MISUSECASE_TR.getMessage(), false, false, false, true, 4),

		CLASS_AND_MISUSECASE("class and misusecase",CLASS_AND_MISUSECASE_TR.getMessage(), true, false, false, true, 5),

		SUPER_HYBRID("super hybrid",SUPER_HYBRID_TR.getMessage(), true, true, false, true, 6);

		//TODO add MissUsecase


		/**
		 * Return a the UMLDiagram type that corresponds to the index
		 *
		 * @param uMLDiagramIndex
		 *            the index corresponding to the diagram to retrieve
		 * @return The {@link UMLDiagram.Type} corresponding to the index
		 */
		public static UMLDiagram.Type getUMLDiagramFromIndex(final int uMLDiagramIndex) {
			for (final UMLDiagram.Type type : UMLDiagram.Type.values()) {
				if (type.index == uMLDiagramIndex) {
					return type;
				}
			}
			return UMLDiagram.Type.SUPER_HYBRID;
		}

		private boolean	isClassType;
		private boolean	isObjectType;
		private boolean	isSequenceType;
		private boolean	isMisuseCaseType;
		private String	name;
		private String nameInMenu;

		private Integer	index;


		private Type(final String name, final boolean isClassType, final boolean isObjectType, final boolean isSequenceType, final boolean isMisuseCaseType, final Integer index) {
			this.name = name;
			this.isClassType = isClassType;
			this.isObjectType = isObjectType;
			this.isSequenceType = isSequenceType;
			this.isMisuseCaseType = isMisuseCaseType;
			this.index = index;
		}
		private Type(final String name, final String nameInMenu, final boolean isClassType, final boolean isObjectType, final boolean isSequenceType, final boolean isMisuseCaseType, final Integer index) {
			this.name = name;
			this.nameInMenu=nameInMenu;
			this.isClassType = isClassType;
			this.isObjectType = isObjectType;
			this.isSequenceType = isSequenceType;
			this.isMisuseCaseType = isMisuseCaseType;
			this.index = index;
		}

		/**
		 * Getter for the index
		 *
		 * @return the index
		 */
		public Integer getIndex() {
			return this.index;
		}

		/**
		 * Getter for the name
		 *
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		public String getNameInMenu(){
			return this.nameInMenu;
		}

		/**
		 * This method allows to know if a diagram can draw class diagram and object diagram type objects
		 *
		 * @return True if the diagram can draw class diagram and object diagram objects
		 */
		public boolean isClassOrObjectType() {
			return this.isClassType || this.isObjectType;
		}

		/**
		 * This method allows to know if a diagram can draw class diagram type objects
		 *
		 * @return True if the diagram can draw class diagram objects
		 */
		public boolean isClassType() {
			return this.isClassType;
		}

		/**
		 * This method allows to know if a diagram can draw class diagram and object diagram type objects
		 *
		 * @return True if the diagram can draw class diagram and object diagram objects
		 */
		public boolean isHybridType() {
			return this.isClassType && this.isObjectType;
		}

		public boolean isSuperHybridType() {
			return this.isClassType && this.isObjectType && this.isMisuseCaseType;
		}

		/**
		 * This method allows to know if a diagram can draw object diagram type objects
		 *
		 * @return True if the diagram can draw object diagram objects
		 */
		public boolean isObjectType() {
			return this.isObjectType;
		}

		/**
		 * This method allows to know if a diagram can draw sequence diagram type objects
		 *
		 * @return True if the diagram can draw sequence diagram objects
		 */
		public boolean isSequenceType() {
			return this.isSequenceType;
		}
		public boolean isMisuseCaseType() {
			return this.isMisuseCaseType;
		}
	}

	private final Type	type;

	/**
	 * Constructor of {@link UMLDiagram}
	 *
	 * @param type
	 *            The {@link Type} of the uml diagram
	 */
	public UMLDiagram(final Type type) {
		super();
		this.type = type;
	}

	/**
	 * Getter for the {@link Type}
	 *
	 * @return the {@link Type}
	 */
	public Type getType() {
		return this.type;
	}
}
