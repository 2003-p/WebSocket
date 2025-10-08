package com.objetdirect.gwt.umldrawer.server.evaluater;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.search.spell.LevensteinDistance;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class DiagramEvaluater {
	private static List<UMLArtifact> answer = new ArrayList<UMLArtifact>();
	private static List<UMLArtifact> student = new ArrayList<UMLArtifact>();

	public static void main(String[] args) {
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		Dao dao = new Dao();
		DiagramParser dp = new DiagramParser();
		int exerciseId = 14;
		//int exerciseId = 14;
		//List<EditEvent> eList = dao.getEditEventListForReplay("tanaka", 10);
		//List<EditEvent> eList = dao.getAnswer(24);
		//answer = dp.fromURL( eList.get( eList.size()-1 ).getCanvasUrl(), false );
		if ( dao.getAnswer(exerciseId) != null ){
			answer = dp.fromURL( dao.getAnswer(exerciseId), false );
		}
		else {
			System.out.println("No answer.");
			return;
		}

		List<String> studentList = dao.getUserList();

		boolean isFirstLoop = true;
		for(String studentId: studentList){
			List<EditEvent> eList2 = dao.getEditEventListForReplay(studentId, exerciseId);

			if(eList2.size() == 0){
				if(studentId.startsWith("c")){
					System.out.println(studentId);
					System.out.println("--------------------------");
				}
				continue;
			}

			dp = new DiagramParser();// ここ大事
			student = dp.fromURL( eList2.get( eList2.size()-1 ).getCanvasUrl(), false );

			//解答に存在するクラスに対応する学生のクラスを見つけ、対応表を作る
			Map<Integer, Integer> correspondElement = new HashMap<Integer, Integer>();
			correspondElement = getCorrespondElement(answer, student);

			//結果の出力
			for(UMLArtifact ansArtifact : answer){
				
//				System.out.println("-------------");   saito
//				System.out.println(correspondElement);
//				System.out.println("-------------");
				
				if( correspondElement.containsKey( ansArtifact.getId() ) ){
					UMLArtifact studArtifact = null;
					for(UMLArtifact art: student){
						if( art.getId() == correspondElement.get(ansArtifact.getId()) ) {
							studArtifact = art;
							break;
						}
					}//for(UMLArtifact art: student)

					String  type = ansArtifact.toURL().split("\\$")[0];
					if(type.equals("Class")){
						ClassArtifact ansClassArtifact = (ClassArtifact) ansArtifact;
						ClassArtifact studClassArtifact = (ClassArtifact) studArtifact;
						outputClassComparationResult(result, ansClassArtifact, studClassArtifact);
					}
					else if(type.equals("ClassRelationLink")) {
						ClassRelationLinkArtifact ansRelationArtifact = (ClassRelationLinkArtifact) ansArtifact;
						ClassRelationLinkArtifact studRelationArtifact = (ClassRelationLinkArtifact) studArtifact;
						outputRelationComparationResult(result, correspondElement ,ansRelationArtifact, studRelationArtifact);
					}
					else{
						System.out.print("ansArtifact.toURL()");
						System.out.println(ansArtifact.toURL());
					}
				}//if( correspondElement.containsKey( ansArtifact.getId() ) )
				else{
					outputNoPairExistArtifact(result, ansArtifact);
				}
			}
			printResult(isFirstLoop, studentId, result);
			isFirstLoop = false;
		}//for(String studentId: studentList)
	}

	public static Map<Integer, Integer> getCorrespondElement(
			List<UMLArtifact> ans, List<UMLArtifact> stud) {
		Map<Integer, Integer> ce = new HashMap<Integer, Integer>();
		Map<Integer, Map<Integer, Double>> scoreMap = makeScoreMap(ans, stud);
		//printScoreMap(scoreMap);
		//----------------------------------------
		int maxIdx1 = -1, maxIdx2 = -1;
		double maxVal = 10;
		double t = 0.4;
		while(maxVal >= t){
			maxVal=0;
			for(Entry<Integer, Map<Integer, Double>> e1 : scoreMap.entrySet()){
				for(Entry<Integer, Double> e2 : scoreMap.get(e1.getKey()).entrySet()){
					if ( e2.getValue() >maxVal ) {
						maxVal = e2.getValue();
						maxIdx1 = e1.getKey();
						maxIdx2 = e2.getKey();
					}
				}
			}
			if(maxVal >= t){
				//System.out.println("To CE "+maxIdx1+" : "+ maxIdx2 +"=>"+scoreMap.get(maxIdx1).get(maxIdx2));
				ce.put(maxIdx1, maxIdx2);
				for(Entry<Integer, Map<Integer, Double>> e1 : scoreMap.entrySet()){
					for(Entry<Integer, Double> e2 : e1.getValue().entrySet()){
						if ( e1.getKey() == maxIdx1 || e2.getKey() == maxIdx2) {
							e2.setValue(-1.0);
						}
					}
				}
			}//if(maxVal > t)
		}//while(maxVal > t)

		//		//------------対応する実体が抽出できた-------------------

		//------------対応する関連を抽出する---------------------
		for(UMLArtifact a : ans){
			String  type = a.toURL().split("\\$")[0];
			if(type.equals("ClassRelationLink")) {
				int aRightId = ((ClassRelationLinkArtifact) (a)).getRightClassArtifact().getId();
				int aLeftId = ((ClassRelationLinkArtifact) (a)).getLeftClassArtifact().getId();
				for(UMLArtifact s : stud){
					String  sType = s.toURL().split("\\$")[0];
					if(sType.equals("ClassRelationLink")) {
						int sRightId = ((ClassRelationLinkArtifact) (s)).getRightClassArtifact().getId();
						int sLeftId = ((ClassRelationLinkArtifact) (s)).getLeftClassArtifact().getId();
						if(ce != null && ce.containsKey(aRightId) && ce.containsKey(aLeftId) && ce.containsKey(aRightId) && ce.containsKey(aLeftId) ){
							if( ( ce.get(aRightId) ==  sRightId && ce.get(aLeftId) == sLeftId)
									|| ( ce.get(aRightId) == sLeftId && ce.get(aLeftId) == sRightId) ){
								ce.put(a.getId(), s.getId());
							}//if( (aRightId == sRightId && aLeftId == sLeftId) || (aRightId == sLeftId && aLeftId == sRightId) )
						}//if(ce != null && ce.containsKey(aRightId) && ce.containsKey(aLeftId) && ce.containsKey(aRightId) && ce.containsKey(aLeftId) )
					}//if(sType.equals("ClassRelationLink"))
				}//for(UMLArtifact s : stud)
			}//if(type.equals("ClassRelationLink"))
		}//for(UMLArtifact a : ans)

		return ce;
	}

	private static Map<Integer, Map<Integer, Double>> makeScoreMap(List<UMLArtifact> ans, List<UMLArtifact> stud){
		Map<Integer, Map<Integer, Double>> sm = new LinkedHashMap<Integer, Map<Integer, Double>>();
		for(UMLArtifact a : ans){
			String  type = a.toURL().split("\\$")[0];
			if(type.equals("Class")){
				sm.put(a.getId(), new LinkedHashMap<Integer, Double>());
				for(UMLArtifact s : stud){
					String  ts = s.toURL().split("\\$")[0];
					if(ts.equals("Class")){
						sm.get(a.getId()).put(s.getId(), compareClass( (ClassArtifact) a, (ClassArtifact) s) );
					}//if(type.equals("Class"))
				}//for(UMLArtifact s : stud)
			}//if(type.equals("Class"))
		}//for(UMLArtifact a : ans)

		return sm;
	}

	private static double compareClass(ClassArtifact a, ClassArtifact s) {
		Map<Integer, Double> result= new LinkedHashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> am = new LinkedHashMap<Integer, Map<Integer, Double>>();
		LevensteinDistance ld = new LevensteinDistance();
		int i = 0;
		for(UMLClassAttribute ansAttr : a.getAttributes()){
			am.put(i, new LinkedHashMap<Integer, Double>());
			int j = 0;
			 for(UMLClassAttribute stuAttr : s.getAttributes()){
//				am.get(i).put(j, (double) ld.getDistance(Normalizer.normalize(ansAttr.getName(), Normalizer.Form.NFKC), Normalizer.normalize(stuAttr.getName(), Normalizer.Form.NFKC)));
				am.get(i).put(j, getDistanceScore(ansAttr.getName(), "@", stuAttr.getName()));

								System.out.println(ansAttr.getName()+": "+stuAttr.getName());   ///
								System.out.println(ld.getDistance(ansAttr.getName(), stuAttr.getName()));  ///
				j++;
			}//for(UMLClassAttribute stuAttr : s.getAttributes())
			i++;
		}//for(UMLClassAttribute ansAttr : a.getAttributes())
		//printScoreMap(am);


		///対応する属性の組をなんちゃって貪欲法で見つける
		int maxIdx1 = -1, maxIdx2 = -1;
		double maxVal = 10;
		double t = 0.3;
//		System.out.println();
		
		//比較する2つの単語
		System.out.println(a.getName()+", "+s.getName() );    
		while(maxVal >= t){
			maxVal=0;
			for(Entry<Integer, Map<Integer, Double>> e1 : am.entrySet()){
				for(Entry<Integer, Double> e2 : am.get(e1.getKey()).entrySet()){
					if ( e2.getValue() >maxVal ) {
						maxVal = e2.getValue();
						maxIdx1 = e1.getKey();
						maxIdx2 = e2.getKey();
					}
				}
			}
			if(maxVal >= t){
				//System.out.println(a.getAttributes().get(maxIdx1).getName()+", "+s.getAttributes().get(maxIdx2).getName()+" : "+am.get(maxIdx1).get(maxIdx2));
				result.put(maxIdx1, maxVal);//対応する属性の組を結果に格納
				for(Entry<Integer, Map<Integer, Double>> e1 : am.entrySet()){
					for(Entry<Integer, Double> e2 : e1.getValue().entrySet()){
						if ( e1.getKey() == maxIdx1 || e2.getKey() == maxIdx2) {
							e2.setValue(-1.0);
						}
					}
				}
			}//if(maxVal > t)
		}//while(maxVal > t)

		//クラス名の対応を見る
		double nameDistance = 0;
		//if(ld.getDistance(a.getName(), s.getName()) >= 0.5){//t=0.5
//		if(getDistanceScore( a.getName(), "@", s.getName() ) >= 0.5){//t=0.5
//			nameDistance = 1.0;
//		}
//		else{
//			nameDistance = 0;
//		}
		
		//名前の類似度がそのまま出るようにした saito
		nameDistance = getDistanceScore( a.getName(), "@", s.getName() );

		//結果を出す
		double r = 0;
		if(a.getAttributes().size() == 0){//答えの属性が空だったらrはクラス名の類似度をもとに決める
			r = nameDistance;
//			System.out.println("nameDistance  r="+r);
			System.out.println("nameSim="+r);
		}
		else{
			r = ( (double)result.size()/(double)a.getAttributes().size() )*6.0/10.0 + nameDistance*4.0/10.0;
			System.out.println("else r="+r);
		}
		return r;
	}

	private static double getDistanceScore(String ans, String separator, String stud){
		double result = 0;
		LevensteinDistance ld = new LevensteinDistance();
		String [] ansList = ans.split(separator);
		for(int i = 0; i < ansList.length ; i++){
			double tmp =  (double) ld.getDistance(Normalizer.normalize(ansList[i], Normalizer.Form.NFKC), Normalizer.normalize(stud, Normalizer.Form.NFKC));
			if( tmp > result){
				result = tmp;
			}
		}
		return result;
	}

	private static void printScoreMap(Map<Integer, Map<Integer, Double>> scoreMap){
		for(Entry<Integer, Map<Integer, Double>> e1 : scoreMap.entrySet()){
			System.out.print(e1.getKey()+": ");
			for(Entry<Integer, Double> e2 : scoreMap.get(e1.getKey()).entrySet()){
				System.out.print("("+e1.getKey()+","+ e2.getKey()+")=>"+e2.getValue()+", ");
			}
			System.out.println("####");
			System.out.println();
		}
	}

	private static void printResult(boolean toPrintColumnName, String studentId, Map<String, Integer> resultMap){
		if(toPrintColumnName){
			System.out.print("学生ID,");
			for(Entry<String, Integer> e : resultMap.entrySet()){
				System.out.print(e.getKey()+",");
			}
			System.out.print("合計");
			System.out.println("");
		}

		int score = 0;
		System.out.print(studentId+",");
		for(Entry<String, Integer> e : resultMap.entrySet()){
			System.out.print(e.getValue()+",");
			score += e.getValue();
		}
		System.out.print(score);
		System.out.println("");
	}

	//ペアがない要素は、全部0点を出力
	public static void outputNoPairExistArtifact(Map<String, Integer> result, UMLArtifact ansArtifact) {
		String  type = ansArtifact.toURL().split("\\$")[0];
		if(type.equals("Class")){
			ClassArtifact ansClassArtifact = (ClassArtifact) ansArtifact;
			//対応するクラスがあるか
			result.put(ansClassArtifact.getName(), 0);
			System.out.println(ansClassArtifact.getName()+","+0);  ///

			//クラス名がとれたか
			result.put("Name of "+ansClassArtifact.getName(), 0);
			System.out.println(ansClassArtifact.getName()+","+0); ///

			for(UMLClassAttribute attr : ansClassArtifact.getAttributes()){
				result.put(ansClassArtifact.getName()+"."+attr.getName(), 0);
				System.out.println(ansClassArtifact.getName()+"."+attr.getName()+","+0);  ///
			}
		}
		else if(type.equals("ClassRelationLink")) {
			ClassRelationLinkArtifact ansRelationArtifact = (ClassRelationLinkArtifact) ansArtifact;
			//存在、多重度、多重度
			String relationExist = ((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
					+"--"
					+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName();

			result.put( relationExist, 0);
			//System.out.println(relationExist+","+0);
			System.out.println("####多重度");

			//答えの右側の多重度
			result.put( relationExist+"."+((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
					+" side", 0);
						System.out.println(relationExist+"."+((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
								+" side"+","+0);

			//答えの左側の多重度
			result.put( relationExist+"."+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName()
					+" side", 0);
						System.out.println(relationExist+"."+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName()
								+" side"+","+0);
		}
		else{
			System.out.println(ansArtifact.toURL());
		}
	}

	public static void outputClassComparationResult(Map<String, Integer> result, ClassArtifact a, ClassArtifact s) {
		Map<Integer, Map<Integer, Double>> am = new LinkedHashMap<Integer, Map<Integer, Double>>();
		LevensteinDistance ld = new LevensteinDistance();
		Map<Integer, Integer> correspondAttribute = new LinkedHashMap<Integer, Integer> ();
		int i = 0;
		for(UMLClassAttribute ansAttr : a.getAttributes()){
			am.put(i, new LinkedHashMap<Integer, Double>());
			int j = 0;
			for(UMLClassAttribute stuAttr : s.getAttributes()){
//				am.get(i).put(j, (double) ld.getDistance(Normalizer.normalize(ansAttr.getName(), Normalizer.Form.NFKC), Normalizer.normalize(stuAttr.getName(), Normalizer.Form.NFKC)));
				am.get(i).put(j, getDistanceScore(ansAttr.getName(), "@", stuAttr.getName()));
				j++;
			}//for(UMLClassAttribute stuAttr : s.getAttributes())
			i++;
		}//for(UMLClassAttribute ansAttr : a.getAttributes())

		
		//対応するクラスの存在を出力
		result.put(a.getName(), 1);
		System.out.println(a.getName()+","+1);   ///

		//クラス名の対応を見る
		if(ld.getDistance(a.getName(), s.getName()) > 0.7){//t=0.7
			result.put("Name of "+a.getName(), 1);
			System.out.println(a.getName()+","+1);  ///
		}
		else{
			result.put("Name of "+a.getName(), 0);
			System.out.println(a.getName()+","+0);   ///
		}

		///対応する属性の組をなんちゃって貪欲法で見つける
		int maxIdx1 = -1, maxIdx2 = -1;
		double maxVal = 10;
		double t = 0.5;
		while(maxVal > t){
			maxVal=0;
			for(Entry<Integer, Map<Integer, Double>> e1 : am.entrySet()){
				for(Entry<Integer, Double> e2 : am.get(e1.getKey()).entrySet()){
					if ( e2.getValue() >maxVal ) {
						maxVal = e2.getValue();
						maxIdx1 = e1.getKey();
						maxIdx2 = e2.getKey();
					}
				}
			}
			if(maxVal > t){
				correspondAttribute.put(maxIdx1, maxIdx2);
				for(Entry<Integer, Map<Integer, Double>> e1 : am.entrySet()){
					for(Entry<Integer, Double> e2 : e1.getValue().entrySet()){
						if ( e1.getKey() == maxIdx1 || e2.getKey() == maxIdx2) {
							e2.setValue(-1.0);
						}
					}
				}
			}//if(maxVal > t)
		}//while(maxVal > t)

		//resultに出力する

		for(int idx = 0 ; idx < a.getAttributes().size(); idx++){
			if(correspondAttribute.containsKey( idx )){
				result.put(a.getName()+"."+a.getAttributes().get( idx ).getName(), 1);//対応する属性が存在する
				System.out.println(a.getName()+"."+a.getAttributes().get( idx ).getName()+","+1);  ///
			}
			else{
				result.put(a.getName()+"."+a.getAttributes().get( idx ).getName(), 0);//対応する属性が存在しない
				System.out.println(a.getName()+"."+a.getAttributes().get( idx ).getName()+","+0); ///
			}
		}
	}

	public static void outputRelationComparationResult(
			Map<String, Integer> result,
			Map<Integer, Integer> ce,
			ClassRelationLinkArtifact ansRelationArtifact,
			ClassRelationLinkArtifact studRelationArtifact) {
		int aRightId = ansRelationArtifact.getRightUMLArtifact().getId();
		int sRightId = studRelationArtifact.getRightUMLArtifact().getId();

		String aRightCard = ansRelationArtifact.getPartContent(RelationLinkArtifactPart.RIGHT_CARDINALITY);
		String aLeftCard = ansRelationArtifact.getPartContent(RelationLinkArtifactPart.LEFT_CARDINALITY);
		String sRightCard = "";
		String sLeftCard = "";

		if( ce.get(aRightId) == sRightId ){//右と右で対応してたら
			sRightCard = studRelationArtifact.getPartContent(RelationLinkArtifactPart.RIGHT_CARDINALITY);
			sLeftCard = studRelationArtifact.getPartContent(RelationLinkArtifactPart.LEFT_CARDINALITY);
		}
		else{//右と左が対応してたら
			sRightCard = studRelationArtifact.getPartContent(RelationLinkArtifactPart.LEFT_CARDINALITY);
			sLeftCard = studRelationArtifact.getPartContent(RelationLinkArtifactPart.RIGHT_CARDINALITY);
		}

		//結果出力
		//関連の存在
		String relationExist = ((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
				+"--"
				+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName();
		result.put( relationExist, 1);
		System.out.print("関連の存在：");
				System.out.println(
						((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
						+"--"+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName()+","+1);

		//答えの右側の多重度
		result.put( relationExist+"."+((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
				+" side", compareCard(aRightCard, sRightCard));
		System.out.print("右側の多重度：");
				System.out.println(((ClassArtifact)(ansRelationArtifact.getRightUMLArtifact() ) ).getName()
						+" side"+","+compareCard(aRightCard, sRightCard));

		//答えの左側の多重度
		result.put( relationExist+"."+((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName()
				+" side", compareCard(aLeftCard, sLeftCard));
		System.out.print("左側の多重度：");
				System.out.println(((ClassArtifact)(ansRelationArtifact.getLeftUMLArtifact() ) ).getName()
						+" side"+","+compareCard(aLeftCard, sLeftCard));
	}

	private static Integer compareCard(String cardinality1, String cardinality2) {
		String card1 = cardinality1;
		String min1="", max1="";
		String card2 = cardinality2;
		String min2="", max2="";

		card1 = card1.replaceAll(" ", "");
		card2 = card2.replaceAll(" ", "");
		card1 = Normalizer.normalize(card1, Normalizer.Form.NFKC);
		card2 = Normalizer.normalize(card2, Normalizer.Form.NFKC);
		card1 = card1.toLowerCase();
		card2 = card2.toLowerCase();

		//区切り文字「...」,「..」, を「,」に統一
		card1 = card1.replaceAll("\\.\\.\\.", ",");
		card1 = card1.replaceAll("\\.\\.", ",");

		card2 = card2.replaceAll("\\.\\.\\.", ",");
		card2 = card2.replaceAll("\\.\\.", ",");

		//多数の表現をnに統一
		card1 = card1.replaceAll("\\*", "n");
		card1 = card1.replaceAll("m", "n");

		card2 = card2.replaceAll("\\*", "n");
		card2 = card2.replaceAll("m", "n");

		//noneだったら空にする
		card1 = card1.replaceAll("none", "");
		card2 = card2.replaceAll("none", "");

//		System.out.println("card1 =( "+card1+" )");
//		System.out.println("card2 =( "+card2+" )");

		//多重度の正誤判定
		//上限が「多」同士ならば正解とする。また、どちらも上限が「多」でなければ、それも正解とする。
		if( card1.contains("n") && card2.contains("n") ){
			return 1;
		}
		else if( !card1.matches("^.*[2-9]+.*$")
				&& card2.matches("^.*[2-9]+.*$")   ){
			return 0;
		}
		else if( (  (card1.contains("0") || card1.contains("1") )&&  !card1.contains("n")  ) //0or1を含んでいて、かつnを含まない
				&& (  (card2.contains("0") || card2.contains("1") ) &&  !card2.contains("n")  ) ){//0or1を含んでいて、かつnを含まない
			return 1;
		}
		else{
			return 0;
		}
	}
}
