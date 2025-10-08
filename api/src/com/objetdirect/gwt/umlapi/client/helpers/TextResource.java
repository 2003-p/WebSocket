/**
 *
 */
package com.objetdirect.gwt.umlapi.client.helpers;

/**
 * @author tanaka
 *
 */
public enum TextResource {
	SUPER_HYBRID_TR("Super hybrid", "スーパーハイブリッド"),
	CLASS_AND_MISUSECASE_TR("Class and MisuseCase", "クラス図とミスユースケース図"),
	EDIT_MISUSECASE_DIAGRAM("Edit Misuse Case Diaram", "ミスユースケース図を編集"),
	MISUSECASE_DIAGRAM("MisuseCase Diagram", "ミスユースケース図"),
	CLASS_DIAGRAM("Class Diagram", "クラス図"),
	ASSET_LINK("Asset link", "アセットリンク"),
	ADD_NEW_USECASE("Add new usecase", "新しいユースケース"),
	ADD_NEW_MISUSECASE("Add new misusecase", "新しいミスユースケース"),
	ADD_NEW_SECURITYUSECASE("Add new securityusecase", "新しいセキュリティユースケース"),
	ADD_NEW_ASSET("Add new asset", "新しい資産"),
	ADD_NEW_ACTOR("Add new actor", "新しいアクター"),
	ADD_NEW_MISACTOR("Add new actor", "新しいミスアクター"),
	ADD_NEW_MISTHIRDPARTY("Add new misthirdparty","第三者"),
	ADD_NEW_MISPRINCIPAL("Add new misprincipal","本人"),
	ADD_NEW_MISEXPRINCIPAL("Add new misexprincipal","本人以外"),
	ADD_NEW_CLASS("Add new class", "新しいクラス"),
	ADD_NEW_OBJECT("Add new object", "Add new object"),
	ADD_NEW_LIFELINE("Add new life line", "Add new life line"),
	ADD_NEW_NOTE("Add new note", "新しいノート"),
	ADD_RELATION("Add relation", "関連を追加"),
	CUT("Cut", "切り取り"),
	COPY("Copy", "コピー"),
	PASTE("Paste", "貼り付け"),
	SWITCH_LINK_STYLE("Switch link style", "関連線の繋ぎ方を変更"),
	REMOVE("Remove", "選択対象を削除"),
	CLEAR_DIAGRAM("Clear diagram", "全削除"),
	HOTKEYS("Hotkeys...","ショートカット一覧..."),
	CLASS("Class","クラス"),
	NAME("Name","名前"),
	EDIT_NAME("Edit Name","名前を編集"),
	ADD_STEREOTYPE("Add Stereotype","ステレオタイプを追加"),
	EDIT_STEREOTYPE("Edit Stereotype","ステレオタイプを編集"),
	DELETE_STEREOTYPE("Delete Stereotype","ステレオタイプを削除"),
	ATTRIBUTES("Attributes","属性"),
	EDIT("Edit ","編集 "),
	DELETE("Delete ","削除 "),
	ADD_NEW("Add new ","新規追加"),
	METHODS("Methods","メソッド"),
	LIFE_LINE("LifeLine","ライフライン"),
	CLASS_RELATION_LINK("Class relation link ","関連クラス用の関連"),
	REVERSE("Reverse ","関連の向きを反転"),
	CHANGE_TO("Change to ","変更"),
	NOTE("Note ","ノート"),
	EDIT_CONTENT("Edit content","編集"),
	CREATE("Create","作成"),
	AGGREGATION("Aggregation","集約"),
	ASSOSIATION("Association","矢印付き関連"),
	COMPOSITION("Composition","合成集約"),
	DEPENDENCY("Dependency","依存"),
	GENERALIZATION("Generalization","汎化"),
	REALIZATION("Realization","実現"),
	ASYNCRONOUS("Asynchronous","非同期"),
	SYNCRONOUS("Synchronous","同期"),
	REPLY("Reply","リプライ"),
	OBJECT_CREATION("Object Creation","オブジェクト生成"),
	LOST("Lost","ロスト"),
	FOUND("Found","ファウンド"),
	NOTE_LINK("Note link","ノートリンク"),
	CLASS_RELATION("Class Relation","関連クラス"),
	INSTANTIATION_TR("Instantiation","インスタンス化"),
	NAVIGATABLE("Navigable","導出可"),
	NOT_NAVIGATABLE("Not Navigable","導出不可"),
	UNKNOWN("Unknown","不明"),
	CARDINALITY("Cardinality","多重度"),
	CONSTRAINT("Constraint","制約"),
	ROLE("Role","役割"),
	NAME_TR("Name","名前"),
	SIDE(" side"," 側"),
	CLASS_TR("class","クラス"),
	OBJEXT_TR("object","オブジェクト"),
	HYBLID_TR("class and object","クラス＆オブジェクト"),
	SEQUENCE_TR("sequence","シーケンス"),
	RELATIONSHIP("Relationship","関連"),
	MISUSECASE_TR("MisuseCase", "ミスユースケース"),

	//　山崎
	FIELD_ADD_STRING("Do you want to add lemon field in the Strawberry class?","Strawberryクラスにフィールドlemonを追加しますか"),
	CLASS_ADD_STRING("Do you want to add Strawberry class?","Strawberryクラスを追加しますか"),
	CLASS_TYPE_ADD_STRING("Do you want to add grape in the Strawberry class?","Strawberryクラスにgrapeを追加しますか"),
	METHOD_ADD_STRING("Do you want to add banana method in the Strawberry class?","Strawberryクラスにメソッドbananaを追加しますか"),
	PARAMETER_ADD_STRING("Add parameter apple to banana method of Strawberry class?","Strawberryのメソッドbananaにパラメータappleを追加しますか"),

	FIELD_REMOVE_STRING("Do you want to remove lemon in Strawberry class?","Strawberryクラスのフィールドlemonを削除しますか"),
	CLASS_REMOVE_STRING("Do you want to remove Strawberry class?","Strawberryクラスを削除しますか"),
	CLASS_TYPE_REMOVE_STRING("Do you want to remove the Strawberry class grape?","Strawberryクラスの型grapeを削除しますか"),
	METHOD_REMOVE_STRING("Do you want to remove the banana in the Strawberry class?","Strawberryクラスのメソッドbananaを削除しますか"),
	PARAMETER_REMOVE_STRING("Do you want to remove the parameter apple of method banana in the Strawberry class?","Strawberryクラスのメソッドbananaにあるパラメータappleを削除しますか"),

	REPLACE_FIELD_STRING("Convert the Peach of field lemon in the Strawberry class to cherry?","StrawberryクラスのフィールドlemonのPeachをcherryに変換しますか？"),

	REPLACE_METHOD_TYPE_STRING("Convert the Peach of method banana in the Strawberry class to cherry?","StrawberryクラスのメソッドbananaのPeachをcherryに変換しますか？"),
	REPLACE_METHOD_MODEFIER_STRING("Convert the Peach of method banana in the Strawberry class to cherry?","StrawberryクラスのメソッドbananaのPeachをcherryに変換しますか？"),

	REPLACE_CLASS_TYPE_STRING("Convert Strawberry class grape to cherry?","Strawberryクラスの型をgrapeからcherryに変換しますか？"),

	//これだめパラメータの名前が必要
	REPLACE_PARAMETER_STRING("Convert the parameter apple type of the method banana in the Strawberry class to cherry?","Strawberryクラスのメソッドbananaにあるパラメータをcherryに変換しますか？"),

	REPLACE_CONSTRACT_STRING("Convert the method banana in the Strawberry class to cherry?","Strawberryクラスのメソッドbananaをcherryに変換しますか？"),

	CONSTRACTAR("Untyped constructor","型なしのコンストラクタ"),
	VISIBILITY("visibility","修飾子"),
	TYPE("type","型"),
	;
	//　いちご　クラス
	//　レモン　フィールド
	//　ばなな　メソッド
	//　ブドウ　クラスの型
	//　りんご　パラメータ　
	//　マンゴー　Mango　修飾子
	//　もも　　Peach　型
	//　さくらんぼ　置換対象　

	private String english;
	private String japanese;
	private TextResource(String english, String japanese) {
		this.english = english;
		this.japanese = japanese;
	}

	public String getMessage() {
		if(Session.language.equals("jp")){
			return japanese;
		}
		else{
			return english;
		}
	}
}
