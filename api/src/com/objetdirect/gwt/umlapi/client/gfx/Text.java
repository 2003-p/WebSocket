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
package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.Window;



class Text extends IncubatorGfxObject {
	private final String	text;

	public Text(final String text) {
		super();
		this.text = text;
	}

	@Override
	public void draw() {
		// if (!isVisible)
		return;
		/*
		 * Logger.getGlobal().info("{Incubator} Drawing " + this); canvas.saveContext(); if (fillColor != null) canvas.setFillStyle(fillColor); if (strokeColor != null)
		 * canvas.setStrokeStyle(strokeColor); if (strokeWidth != 0) canvas.setLineWidth(strokeWidth); for (int i = 0; i < text.length(); i++) {
		 * canvas.strokeRect(getX() + 10 * i, getY() - 8, 8, 10); } canvas.restoreContext();
		 */
	}

	@Override
	public int getHeight() {
		return 8;
	}

	@Override
	public int getWidth() {
		final String MATCH_ASCII = "^[\\u0020-\\u007E]+$";
		String str = this.text;
		double length = 0;
		char charArray[] = str.toCharArray();
		for(int i = 0 ; i < charArray.length ; i++){
			if ( ((Character)charArray[i]).toString().matches(MATCH_ASCII)) {
				System.out.println("一致");
				length+=0.25;

			} else {
				System.out.println("不一致");
				length+=0.5;
			}
		}

		//TODO 長さはこれ？じゃない
		//return (int)length;
		//return this.text.getBytes("UTF-8").length;
		//return this.text.length();
		Window.alert("length");
		return 1;
	}

	@Override
	public boolean isPointed(final int xp, final int yp) {
		return false;
	}
}
