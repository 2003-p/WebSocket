package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;
import com.objetdirect.gwt.umldrawer.client.saito.SimService;
import com.objetdirect.gwt.umldrawer.server.AnalysisServiceImpl;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class SimServiceImpl extends RemoteServiceServlet implements SimService {
	
	@Override
	public Map<String,Map<String,String>> urlToCompare(String studentId, int exerciseId) {
//		System.out.println("urlToCompare");
		Dao dao = new Dao();
		AnalysisServiceImpl asi = new AnalysisServiceImpl();
		SimilarityManager sm = new SimilarityManager();
		com.objetdirect.gwt.umlapi.server.dao.Dao apiDao = new com.objetdirect.gwt.umlapi.server.dao.Dao();
	
		Map<String,Map<String,String>> sendSimMap = new HashMap<String,Map<String,String>>();
		
		//クラス図のURLを使えるように変換
		String encodeStudentUrl = dao.base64ToString(dao.getStudent(studentId, exerciseId));
		String encodeAnswerUrl = dao.base64ToString(dao.getAnswer(exerciseId));
		StudentElements studentE = new StudentElements(encodeStudentUrl);
		AnswerElements answerE = new AnswerElements(encodeAnswerUrl);
		
		//色を変えるときにクライアント側で必要なMap作り
		SimElements sim = new SimElements();
		sim.simCheck(studentE ,answerE);
		Map<String, String> simMisMatchMap = sim.getSimMatchMap();
		Map<String, String> simMatchMap = sim.getSimMisMatchMap();
		sendSimMap.put("一致",simMatchMap);
		sendSimMap.put("不一致",simMisMatchMap);
		
		//類似度登録 CSall RSall    CDSは「SELECT*((RSall+CSall)/2)AS CDS　FROM similarity WHERE 〇〇」を使ってください
		List<UMLArtifact>diaList1 = asi.fromURL(encodeStudentUrl);
	    List<UMLArtifact>diaList2 = asi.fromURL(encodeAnswerUrl);
	    
//	    double cds = sm.getSimilarity(diaList1, diaList2);
//	    System.out.println("CDS::"+cds);
	    
	    double cs = sm.getClassSimilarity(diaList1, diaList2);
	    
	    double rs = sm.getRelationSimilarity(diaList1, diaList2);
	    
	    dao.registerSimDB(studentId, exerciseId, cs, rs, 1);
	    
	     
	  //URLを書き換える（関連）
//	    List<Integer> relationNum = sm.getRelationPairNum(diaList1, diaList2);
//	  	ArrayList<String> relationList = extractRelations(encodeStudentUrl);
//	    String cutUrl = removeRelationUrl(relationList, relationNum, encodeStudentUrl);
//	    String baseCutUrl = dao.base64ToString(cutUrl);
	    
	  //URLを書き換える（座標）
	    Map<String, String> classNamePair = sm.getLayoutPair(diaList1, diaList2);
	    String reURL = layoutChange(encodeStudentUrl, encodeAnswerUrl, classNamePair);
//	    System.out.println("reURL::"+reURL);
	    apiDao.registEditEvent(studentId, exerciseId, -1, "Update", "Place", null, -1, null, -1, -1, null, null, null, 1, reURL, 1, 111);
	    
		return sendSimMap;
	}
	
	@Override
	public Map<String,Map<String,String>> removeRelation(String studentId, int exerciseId) {
//		System.out.println("removeRelation");
		Dao dao = new Dao();
		AnalysisServiceImpl asi = new AnalysisServiceImpl();
		SimilarityManager sm1 = new SimilarityManager();
//		SimilarityManager sm2 = new SimilarityManager();
//		SimilarityManager sm3 = new SimilarityManager();
		com.objetdirect.gwt.umlapi.server.dao.Dao apiDao = new com.objetdirect.gwt.umlapi.server.dao.Dao();
	
		Map<String,Map<String,String>> sendSimMap = new HashMap<String,Map<String,String>>();
		
		//クラス図のURLを使えるように変換
		String encodeStudentUrl = dao.base64ToString(dao.getStudent(studentId, exerciseId));
		String encodeAnswerUrl = dao.base64ToString(dao.getAnswer(exerciseId));
		StudentElements studentE = new StudentElements(encodeStudentUrl);
		AnswerElements answerE = new AnswerElements(encodeAnswerUrl);
		
		//色を変えるときにクライアント側で必要なMap作り
		SimElements sim = new SimElements();
		sim.simCheck(studentE ,answerE);
		Map<String, String> simMisMatchMap = sim.getSimMatchMap();
		Map<String, String> simMatchMap = sim.getSimMisMatchMap();
		sendSimMap.put("一致",simMatchMap);
		sendSimMap.put("不一致",simMisMatchMap);
		
		//類似度登録 CSall RSall    CDSは「SELECT*,((RSall+CSall)/2)AS CDS FROM similarity WHERE 〇〇」を使ってください
//		List<UMLArtifact>diaList1 = asi.fromURL(encodeStudentUrl);
//	    List<UMLArtifact>diaList2 = asi.fromURL(encodeAnswerUrl);
//	    dao.registerSimDB(studentId, exerciseId, sm1.getClassSimilarity(diaList1, diaList2), sm1.getRelationSimilarity(diaList1, diaList2));
	    
	    //URLを書き換える(関連削除)
//	    Map<String, String>relationMap = sm1.getLayoutRelationPair(diaList1, diaList2);
//	    System.out.println("Relation Map Contents:");
//        for (Map.Entry<String, String> entry : relationMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println("Key: " + key + ", Value: " + value);
//        }
//	    
//	    List<Integer> relationNum = sm1.getRelationPairNum(diaList1, diaList2);
//	  	ArrayList<String> relationList = extractRelations(encodeStudentUrl);
//	    String cutUrl = removeRelationUrl(relationList, relationNum, encodeStudentUrl);
//	    apiDao.registEditEvent(studentId, exerciseId, -1, "Update", "Place", null, -1, null, -1, -1, null, null, null, 1, cutUrl, 1, 111);
//	    System.out.println("URL::"+cutUrl);
	    
		return sendSimMap;
	}
	
	@Override  
	public Map<String,Map<String,String>> addColor(String studentId, int exerciseId) {
		Map<String,Map<String,String>> sendSimMap = new HashMap<String,Map<String,String>>();
//		System.out.println("addColor");
		Dao dao = new Dao();
	
		//クラス図のURLを使えるように変換
		String encodeStudentUrl = dao.base64ToString(dao.getStudent(studentId, exerciseId));
		String encodeAnswerUrl = dao.base64ToString(dao.getAnswer(exerciseId));
		StudentElements studentE = new StudentElements(encodeStudentUrl);
		AnswerElements answerE = new AnswerElements(encodeAnswerUrl);
		
		//色を変えるときにクライアント側で必要なMap作り
		SimElements sim = new SimElements();
		sim.simCheck(studentE ,answerE);
		Map<String, String> simMisMatchMap = sim.getSimMatchMap();
		Map<String, String> simMatchMap = sim.getSimMisMatchMap();
		sendSimMap.put("一致",simMatchMap);
		sendSimMap.put("不一致",simMisMatchMap);

		return sendSimMap;
	}

	//URLを書き換える
	public String layoutChange(String studentUrl, String answerUrl ,Map<String, String> pairClass ) {
		Dao dao = new Dao();
		
		String remakeUrl = ":<0>]Class$(355,120)!エラー発生!!;";
		String coordinate;
		String studentClassName;
		String answerClassName;
		
		//すべての座標を(0,0)にする
		Pattern allCoordinate = Pattern.compile("\\(\\d+,\\d+\\)");
        Matcher allMatcher = allCoordinate.matcher(studentUrl);
        StringBuffer allBuffer = new StringBuffer();
        
        while (allMatcher.find()) {
            String resetCoordinate = "(0,0)";
            allMatcher.appendReplacement(allBuffer, Matcher.quoteReplacement(resetCoordinate));
        }
        
        allMatcher.appendTail(allBuffer);
        String resetUrl = allBuffer.toString();
//   	 	System.out.println("座標初期化：" + resetUrl);
		
		//対応があるクラスに座標を入れる
		for (Map.Entry<String, String> entry : pairClass.entrySet()) {
//            System.out.println("成果物：" + entry.getKey() + ", 解答例：" + entry.getValue() + ", 座標：" + dao.exclusionclass(answerUrl, entry.getValue()));
            
            studentClassName = entry.getKey();
            answerClassName = entry.getValue();
            coordinate = dao.exclusionclass(answerUrl, answerClassName);
            
            Pattern pattern = Pattern.compile("\\(\\d+,\\d+\\)!" + Pattern.quote(studentClassName) + "!!");
            Matcher matcher = pattern.matcher(resetUrl);
            boolean foundMatch = false;
            StringBuffer result = new StringBuffer();
            
            if(dao.exclusionclass(answerUrl, answerClassName) == null) {
            	System.out.println("answerの座標取得が上手くいってない");
            }
            else {
            	while (matcher.find()) {
	            	foundMatch = true; 
	               
	                String replacedText = "(" + coordinate + ")!" + studentClassName + "!!";
	                matcher.appendReplacement(result, replacedText);
	            }
	            matcher.appendTail(result);
            }
            
            if (!foundMatch) {	
            	if(coordinate == null) {
            		System.out.println("マッチング失敗");
            		resetUrl = null;
            	}
            	
            } else {
            	 resetUrl = result.toString();
            }
            
       	 	remakeUrl = dao.encodebase64ToString(resetUrl);
//       	 	System.out.println("変換後URL：" + remakeUrl);

        }
   	 	
		return remakeUrl;
	}
	
	//URLから関連の部分を削除する
	public String removeRelationUrl (ArrayList<String> relationList, List<Integer> matchNumList, String studentUrl){
		Dao dao = new Dao();
		
		String removeRelationUrl = studentUrl;
        String relationNumber;
        Map<Integer, String> relationMap = new HashMap<>();  //key = artifactID, value = 関連部分のURL 
        Pattern pattern = Pattern.compile("<(\\d+)>\\]");
        
        
        for(String cutRelation : relationList) {
        	Matcher mapMatcher = pattern.matcher(cutRelation);
        	while (mapMatcher.find()) {
                relationNumber = mapMatcher.group(1);
                relationMap.put(Integer.parseInt(relationNumber), cutRelation);
            }
        }
        
        Pattern relaPattern = Pattern.compile("<(\\d+)>\\]ClassRelation");
        Matcher matcher = relaPattern.matcher(removeRelationUrl);
        while(matcher.find()) {
    	   String artifactNumber = matcher.group(1);
    	   int artNum = Integer.parseInt(artifactNumber);
    	   for(int matchNum : matchNumList) {
       
	       		if(artNum == matchNum) {
	       			break;
	       		}else {
	       			removeRelationUrl = removeRelationUrl.replace(relationMap.get(artNum), "");
		       		System.out.println(removeRelationUrl);
	       		}
       		}
        }
		String sendRemoveRelationUrl = dao.encodebase64ToString(removeRelationUrl);
        
        return sendRemoveRelationUrl;
	}
	
	
	
	//kokubu
	// 関連部分を抽出するメソッド
    private ArrayList<String> extractRelations(String Url) {
        // 関連部分を抽出する正規表現パターン
        Pattern relationPattern = Pattern.compile("<\\d+>]ClassRelationLink\\$[^;]+;");

        // マッチャーを作成
        Matcher relationMatcher = relationPattern.matcher(Url);

        // 関連部分を格納するArrayList
        ArrayList<String> relationList = new ArrayList<>();

        // マッチする部分を配列に格納
        while (relationMatcher.find()) {
            String relation = relationMatcher.group();
            relationList.add(relation);
        }

//         結果を返す
        return relationList;
    }
    
	
    //類似度をかえす
    @Override
    public double getSim(String studentId, int exerciseId) {
    	Dao dao = new Dao();
		AnalysisServiceImpl asi = new AnalysisServiceImpl();
		SimilarityManager sm = new SimilarityManager();
		com.objetdirect.gwt.umlapi.server.dao.Dao apiDao = new com.objetdirect.gwt.umlapi.server.dao.Dao();
	
		double classDiagramSim = 0.0;
		Map<String,Map<String,String>> sendSimMap = new HashMap<String,Map<String,String>>();
		
		//クラス図のURLを使えるように変換
		String encodeStudentUrl = dao.base64ToString(dao.getStudent(studentId, exerciseId));
		String encodeAnswerUrl = dao.base64ToString(dao.getAnswer(exerciseId));
		StudentElements studentE = new StudentElements(encodeStudentUrl);
		AnswerElements answerE = new AnswerElements(encodeAnswerUrl);
		
		//類似度登録 CSall RSal
		List<UMLArtifact>diaList1 = asi.fromURL(encodeStudentUrl);
	    List<UMLArtifact>diaList2 = asi.fromURL(encodeAnswerUrl);
			    
		double cs = sm.getClassSimilarity(diaList1, diaList2);    
		double rs = sm.getRelationSimilarity(diaList1, diaList2);
			    
		dao.registerSimDB(studentId, exerciseId, cs, rs , 0);
		classDiagramSim = cs + rs;
    	
    	return classDiagramSim;
    }
    
    
    
    
}
