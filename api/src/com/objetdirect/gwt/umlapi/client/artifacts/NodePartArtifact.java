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
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;

/**
 * This abstract class represent a part of a {@link NodeArtifact}
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodePartArtifact extends BoxArtifact {

	protected NodeArtifact	nodeArtifact;
	protected int			nodeWidth;
	protected int			height;
	protected GfxObject		textVirtualGroup;
	protected int			width;

	/**
	 * Constructor of NodePartArtifact
	 *
	 */
	public NodePartArtifact() {
		super(false);
	}

	@Override
	public void buildGfxObjectWithAnimation() {
		this.buildGfxObject();
	}

	/**
	 * Like {@link UMLArtifact#edit(GfxObject)} this method requests an edition but for a new object
	 */
	public abstract void edit();

	/**
	 * Getter for the nodeArtifact
	 *
	 * @return the nodeArtifact
	 */
	public NodeArtifact getNodeArtifact() {
		return this.nodeArtifact;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	/**
	 * This method creates the graphical object for the text to determine the size of this part
	 */
	abstract void computeBounds();

	/**
	 * Setter for the nodeArtifact
	 *
	 * @param nodeArtifact
	 *            the nodeArtifact to set
	 */
	void setNodeArtifact(final NodeArtifact nodeArtifact) {
		this.nodeArtifact = nodeArtifact;
	}

	abstract void setNodeWidth(int width);

	// add Yamazaki
	//クラスを生成した時の色
	protected void setStroke_BLACK(GfxObject element,GfxObject beforeGfxObject)
	{
		String currentColor = GfxManager.getPlatform().getCurrentStrokeColor(beforeGfxObject);	// 初期の読み込みでbiforeGfxObjectを使うとバグる

		if(currentColor != null)
		{
			ArrayList<Integer> rgbList = getRGB(currentColor);
			int r = rgbList.get(0);
			int g = rgbList.get(1);
			int b = rgbList.get(2);
			GfxManager.getPlatform().setStroke(element, new GfxColor(r,g,b), 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}
		else
		{
			// 色が取得できなかったら黒で代用
			GfxManager.getPlatform().setStroke(element, GfxColor.BLACK, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}

		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}

	public void setStroke_BLACK(GfxObject element)
	{
		System.out.print("black");
		// DBから初期で読み込んだ時とか要素を追加したときにに前のGfxObjectはないからその時だけオーバロードした塗りつぶし初期化用のメソッドを使う
		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(element, GfxColor.BLACK, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}
	

//	ここのsetStroke_REDは多分使わない
//	protected void setStroke_RED(GfxObject element)
//	{
//		// 違反個所を赤で塗る用
//		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
//		GfxManager.getPlatform().setStroke(element, GfxColor.RED, 1); //ThemeManager.getTheme().getClassBackgroundColor()
//		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
//	}

	private ArrayList<Integer> getRGB(String currentColor)
	{
		ArrayList<Integer> rgbList = new ArrayList<Integer>();

		int startkakko = currentColor.toString().indexOf("(") + 1;
		int endkakko = currentColor.toString().indexOf(")");
		String subst = currentColor.toString().substring(startkakko,endkakko);
		String[] split = subst.split(",");

		// strokecolorはalphaまであるから4固定でいいと思う
		int[] rgtColor = new int[4];
		for(int i = 0; i < split.length; i++)
		{
			rgbList.add(Integer.parseInt(split[i]));
		}

		return rgbList;


	}
	
	//saito
	public void setStroke_RED(GfxObject element)
	{
		System.out.print("red");
		//上のBlackのコピペsaito
		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(element, GfxColor.RED, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}
	
	public void setStroke_BLUE(GfxObject element)
	{
		//上のBlackのコピペsaito
		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(element, GfxColor.BLUE, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}
	
	protected void setStroke_RED(GfxObject element,GfxObject beforeGfxObject)
	{
		String currentColor = GfxManager.getPlatform().getCurrentStrokeColor(beforeGfxObject);	// 初期の読み込みでbiforeGfxObjectを使うとバグる

		if(currentColor != null)
		{
			ArrayList<Integer> rgbList = getRGB(currentColor);
			int r = rgbList.get(0);
			int g = rgbList.get(1);
			int b = rgbList.get(2);
			GfxManager.getPlatform().setStroke(element, new GfxColor(r,g,b), 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}
		else
		{
			// 色が取得できなかったら黒で代用
			GfxManager.getPlatform().setStroke(element, GfxColor.BLACK, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}

		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}
	
	protected void setStroke_BLUE(GfxObject element,GfxObject beforeGfxObject)
	{
		String currentColor = GfxManager.getPlatform().getCurrentStrokeColor(beforeGfxObject);	// 初期の読み込みでbiforeGfxObjectを使うとバグる

		if(currentColor != null)
		{
			ArrayList<Integer> rgbList = getRGB(currentColor);
			int r = rgbList.get(0);
			int g = rgbList.get(1);
			int b = rgbList.get(2);
			GfxManager.getPlatform().setStroke(element, new GfxColor(r,g,b), 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}
		else
		{
			// 色が取得できなかったら黒で代用
			GfxManager.getPlatform().setStroke(element, GfxColor.BLACK, 1); //ThemeManager.getTheme().getClassBackgroundColor()
		}

		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}
}
