package com.objetdirect.gwt.umldrawer.client.helpers;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JSNIを使ってdiff-match-patchのJavaScriptライブラリを呼び出すためのラッパークラス。
 */
public class DiffMatchPatchGwt {

    private JavaScriptObject dmp;

    public DiffMatchPatchGwt() {
        this.dmp = createDmp();
    }

    /**
     * JavaScriptの世界でdiff_match_patchのインスタンスを生成する。
     */
    private native JavaScriptObject createDmp() /*-{
        return new diff_match_patch();
    }-*/;

    /**
     * サーバーから受け取ったパッチテキストを、JavaScriptのパッチオブジェクトに変換する。
     * @param patchText サーバーから送られてきたパッチ文字列
     * @return JavaScriptのパッチオブジェクト
     */
    public native JavaScriptObject patchFromText(String patchText) /*-{
        return this.@com.objetdirect.gwt.umldrawer.client.helpers.DiffMatchPatchGwt::dmp.patch_fromText(patchText);
    }-*/;

    /**
     * 現在のテキストにパッチを適用し、新しいテキストを返す。
     * @param patches patchFromTextで生成したJavaScriptのパッチオブジェクト
     * @param currentText 現在のテキスト
     * @return パッチ適用後の新しいテキスト
     */
    public native String patchApply(JavaScriptObject patches, String currentText) /*-{
        var results = this.@com.objetdirect.gwt.umldrawer.client.helpers.DiffMatchPatchGwt::dmp.patch_apply(patches, currentText);
        return results[0]; // 結果の配列の0番目に、新しいテキストが入っている
    }-*/;
}