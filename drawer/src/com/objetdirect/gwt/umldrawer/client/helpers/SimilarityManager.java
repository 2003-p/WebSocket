package com.objetdirect.gwt.umldrawer.client.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.CanvasUrlManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;

public class SimilarityManager {

	private List<UMLArtifact> diagram1;
	private List<UMLArtifact> diagram2;
	private SimularityPairList classPairList;
	private SimularityPairList relationPairList;
	private int classNumS;
	private int classNumA;
	private int relationNumS;
	private int relationNumA;
	private int missingClassNum;
	private int missingRelationNum;
//	private final double NAME_SIM_T = 0.6;   //もとの値
	private final double NAME_SIM_T = 0.4;   //saito



	public SimilarityManager() {
		this.classPairList = new SimularityPairList();
		this.relationPairList = new SimularityPairList();
	}

	public void SimilarityManager(List<UMLArtifact> diagram1, List<UMLArtifact> diagram2) {
		this.diagram1 = diagram1;
		this.diagram2 = diagram2;
		this.classPairList = new SimularityPairList();
		this.relationPairList = new SimularityPairList();
	}

	public double getClassSimilarity(List<UMLArtifact> snapShot, List<UMLArtifact> answer){
		double sim = 0;
//		classPairList = null;   //saito
		makeClassPairList(snapShot, answer);
		ASandCSi(this.classPairList);	
		sim = CSall(this.classPairList);
//		System.out.println("sim:---------------------------:"+sim);
		return sim;

	}

	public double getRelationSimilarity(List<UMLArtifact> snapShot, List<UMLArtifact> answer){
		double sim=0;
//		System.out.println("get関連類似度");
		makeClassPairList(snapShot, answer);
		
		makeRelationPairList(snapShot, answer);
		
		for( SimularityPair pair : this.relationPairList.getPairList()){
			ClassRelationLinkArtifact artifact1 = (ClassRelationLinkArtifact)pair.getArtifact1();
			ClassRelationLinkArtifact artifact2 = (ClassRelationLinkArtifact)pair.getArtifact2();
			sim += relationSim(artifact1, artifact2);
			System.out.println("###:::"+sim);
		}
//		System.out.println(sim +"/" +relationNumS + "+"+missingRelationNum);
		sim = sim / (double)(relationNumS + missingRelationNum);
//		System.out.println("関連の類似度："+sim);
		
		return sim;
	}

	public double getSimilarity(List<UMLArtifact> snapShot, List<UMLArtifact> answer){
		return (getClassSimilarity(snapShot,  answer) + getRelationSimilarity(snapShot,  answer) )/2.0;
	}

	public double nameSim(String name1, String name2){
		double sim = 0;
		int len1 = name1.length(), len2 = name2.length();
		
		if (len1 == 0 || len2 == 0) {
	        return 0.0;
	    }
		
		int[][] lcs_table = new int[len1][len2]; // LCS長を格納した表

//		for (int i = 0; i < len1; i++) {
//			for (int j = 0; j < len2; j++) {
//				if (name1.charAt(i) == name2.charAt(j))
//					lcs_table[i][j] = ((i == 0 || j == 0) ? 0 : lcs_table[i-1][j-1]) + 1;
//				else if (name1.charAt(i) != name2.charAt(j))
//					lcs_table[i][j] = Math.max(
//							i == 0 ? 0 : lcs_table[i-1][j],
//									j == 0 ? 0 : lcs_table[i][j-1]);
//			}
//		}
		
		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				if (name1.charAt(i) == name2.charAt(j)) {
					lcs_table[i][j] = (i > 0 && j > 0) ? lcs_table[i-1][j-1] + 1 : 1;
				} else {
					lcs_table[i][j] = Math.max(i > 0 ? lcs_table[i-1][j] : 0, j > 0 ? lcs_table[i][j-1] : 0);
				}
			}
		}


		// LCS表の表示
		//		for (int i = 0; i < len1; i++) {
		//			for (int j = 0; j < len2; j++)
		//				System.out.print(lcs_table[i][j] + " ");
		//			System.out.println();
		//		}

		// LCS表の最も右下にLCS長が格納されている
		sim = ( ( lcs_table[len1-1][len2-1] )*2.0 ) / (double)( len1 + len2);
		return sim;
	}

	private void makeClassPairList( List<UMLArtifact> diagram1, List<UMLArtifact> diagram2){
		double maxNameSim = 0;
		UMLArtifact maxArtifact = null;
		Set<Integer> rejectSetS = new HashSet<Integer>();
		Set<Integer> rejectSetA = new HashSet<Integer>();

		//同じ名前を探す
		for (UMLArtifact artifact1 : diagram1){
			
			maxNameSim = 0;
			maxArtifact = null;
			if(artifact1.getClass()!=ClassArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) continue;

			for(UMLArtifact artifact2 : diagram2){
				
				if(artifact2.getClass()!=ClassArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;
				if( ( ((ClassArtifact) artifact1).getName() ).equals( ((ClassArtifact) artifact2).getName() )){
					maxNameSim = 1.0;
					maxArtifact = artifact2;
				}
			}
			if(maxNameSim == 1.0){
				rejectSetA.add( maxArtifact.getId() );
				rejectSetS.add( artifact1.getId());
				SimularityPair pair = new SimularityPair(artifact1, maxArtifact, maxNameSim);
				classPairList.getPairList().add( pair );
			}
		}

		//似た名前を探す
		for (UMLArtifact artifact1 : diagram1){
			
			maxNameSim = 0;
			maxArtifact = null;
			if(artifact1.getClass()!=ClassArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) continue;
			
			for(UMLArtifact artifact2 : diagram2){
				
				if(artifact2.getClass()!=ClassArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;
				double tempSim = this.nameSim( ((ClassArtifact) artifact1).getName(), ((ClassArtifact) artifact2).getName());
				if( tempSim > maxNameSim){
					maxNameSim = tempSim;
					maxArtifact = artifact2;
				}
			}
			if(maxNameSim > NAME_SIM_T){
				rejectSetA.add( maxArtifact.getId() );
				SimularityPair pair = new SimularityPair(artifact1, maxArtifact, maxNameSim);
				classPairList.getPairList().add( pair );
			}
		}
//		以下3行でpairの中身を見ている saito
//		for (SimularityPair pair : classPairList.getPairList()) {
//            System.out.println("pair："+pair); 
//        }
		
		this.classNumS = classCount(diagram1);
		this.classNumA = classCount(diagram2);

		this.missingClassNum = this. classNumA - rejectSetA.size();
	}
	
	
	//DaoのmakeElementMapで使う saito
	public void makeClassPairMap( List<UMLArtifact> diagram1, List<UMLArtifact> diagram2){
		double maxNameSim = 0;
		UMLArtifact maxArtifact = null;
		Set<Integer> rejectSetS = new HashSet<Integer>();
		Set<Integer> rejectSetA = new HashSet<Integer>();

		//同じ名前を探す
		for (UMLArtifact artifact1 : diagram1){
			maxNameSim = 0;
			maxArtifact = null;
			if(artifact1.getClass()!=ClassArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) continue;

			for(UMLArtifact artifact2 : diagram2){
				if(artifact2.getClass()!=ClassArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;
				if( ( ((ClassArtifact) artifact1).getName() ).equals( ((ClassArtifact) artifact2).getName() )){
					maxNameSim = 1.0;
					maxArtifact = artifact2;
				}
			}
			if(maxNameSim == 1.0){
				rejectSetA.add( maxArtifact.getId() );
				rejectSetS.add( artifact1.getId());
				SimularityPair pair = new SimularityPair(artifact1, maxArtifact, maxNameSim);
				classPairList.getPairList().add( pair );
			}
		}

		//似た名前を探す
		for (UMLArtifact artifact1 : diagram1){
			maxNameSim = 0;
			maxArtifact = null;
			if(artifact1.getClass()!=ClassArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) continue;
			for(UMLArtifact artifact2 : diagram2){
				if(artifact2.getClass()!=ClassArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;
				double tempSim = this.nameSim( ((ClassArtifact) artifact1).getName(), ((ClassArtifact) artifact2).getName());
				if( tempSim > maxNameSim){
					maxNameSim = tempSim;
					maxArtifact = artifact2;
				}
			}
			if(maxNameSim > NAME_SIM_T){
				rejectSetA.add( maxArtifact.getId() );
				SimularityPair pair = new SimularityPair(artifact1, maxArtifact, maxNameSim);
				classPairList.getPairList().add( pair );
			}
		}

		this.classNumS = classCount(diagram1);
		this.classNumA = classCount(diagram2);

		this.missingClassNum = this. classNumA - rejectSetA.size();
	}


	private int classCount(List<UMLArtifact> diagram) {
		int classCount = 0;
		for(UMLArtifact artifact : diagram){
			if(artifact.getClass()==ClassArtifact.class){
				classCount++;
			}
		}
		return classCount;
	}

	private void ASandCSi(SimularityPairList pair){
		double sim = 0;
		double sumOfSim = 0;

		for(SimularityPair sp : pair.getPairList()){
			ClassArtifact artifact1 = (ClassArtifact)sp.getArtifact1();
			ClassArtifact artifact2 = (ClassArtifact)sp.getArtifact2();
			double maxNameSim = 0;
			sumOfSim = 0;
			UMLClassAttribute maxAttribute = null;
			Set<String> rejectSetA = new HashSet<String>();
			Set<String> rejectSetS = new HashSet<String>();

			//同じ名前の属性を探して類似度を計算
			for (UMLClassAttribute attribute1 : artifact1.getAttributes() ){
				maxNameSim = 0;
				maxAttribute = null;
				if(rejectSetS.contains( attribute1.getName() )) continue;
				for(UMLClassAttribute attribute2 : artifact2.getAttributes()){
					if(rejectSetA.contains( attribute2.getName() )) continue;
					if( (((UMLClassAttribute) attribute1).getName()).equals(((UMLClassAttribute) attribute2).getName())){
						maxNameSim = 1.0;
						maxAttribute = attribute2;
//						System.out.println("maxAttribute"+maxAttribute);
					}
				}
				if(maxNameSim == 1.0){
					rejectSetA.add( maxAttribute.getName() );
					rejectSetS.add( attribute1.getName() );
					sumOfSim += maxNameSim;
				}
			}

			//似た名前の属性を探して類似度を計算
			for (UMLClassAttribute attribute1 : artifact1.getAttributes() ){
				maxNameSim = 0;
				maxAttribute = null;
				
//				System.out.println("artifact1:"+artifact1.getAttributes());
				
				if(rejectSetS.contains( attribute1.getName() )) continue;
				for(UMLClassAttribute attribute2 : artifact2.getAttributes()){
					if(rejectSetA.contains( attribute2.getName() )) continue;
					double tempSim = this.nameSim( ((UMLClassAttribute) attribute1).getName(), ((UMLClassAttribute) attribute2).getName());
					if( tempSim > maxNameSim){
						maxNameSim = tempSim;
						maxAttribute = attribute2;
					}
				}
				if(maxNameSim > NAME_SIM_T){
					rejectSetS.add( attribute1.getName() );
					rejectSetA.add( maxAttribute.getName() );
					sumOfSim += maxNameSim;

				}
			}


			double nameSim = sp.getSimularity();
			//CSiを計算
			sim = ( nameSim + sumOfSim ) / (double)( 1.0 + artifact1.getAttributes().size() + (artifact2.getAttributes().size() - rejectSetA.size() ) );
//			System.out.println(artifact1.getName()+" and "+artifact2.getName() +" = "+ sim );
			
			

			sp.setSimularity( sim ); //pairの類似度をCSiで更新

		}//for(SimularityPair sp : pair.getPairList())


	}

	private double CSall(SimularityPairList classPairList){
		double sumOfSim = 0;
		for(SimularityPair sp : classPairList.getPairList()){
			sumOfSim += sp.getSimularity();
		}
		double sim = sumOfSim / ( classNumS + missingClassNum );
//		System.out.println("MCN:"+missingClassNum);
		return sim;
	}

//	private void makeRelationPairList(List<UMLArtifact> diagram1, List<UMLArtifact> diagram2){
//		double beingSim = 0;
//		UMLArtifact pairRelation = null;
//		Set<Integer> rejectSetS = new HashSet<Integer>();
//		Set<Integer> rejectSetA = new HashSet<Integer>();
//
//		//左右端がペアになってる関連を探す
//		for (UMLArtifact artifact1 : diagram1){
//			beingSim = 0;
//			pairRelation = null;
//			if(artifact1.getClass() != ClassRelationLinkArtifact.class) continue;
//			if(rejectSetS.contains( artifact1.getId() )) {
//				System.out.println("getId:"+artifact1.getId());
//				continue;
//			}
//
//			for(UMLArtifact artifact2 : diagram2){
//				if(artifact2.getClass() != ClassRelationLinkArtifact.class) continue;
//				if(rejectSetA.contains( artifact2.getId() )) continue;
//
//
//				int pairOfartifact1LeftClassId = -1;
//				int pairOfartifact1RightClassId = -1;
//
//				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true) != null) {
//					pairOfartifact1LeftClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true).getId();
//					System.out.println("pair1:"+pairOfartifact1LeftClassId );
//				}
//				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true) != null) {
//					pairOfartifact1RightClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true).getId();
//					System.out.println("pair2:"+pairOfartifact1RightClassId);
//				}
//					
//				int artifact2LeftClassId = ((ClassRelationLinkArtifact) artifact2).getLeftClassArtifact().getId();
//				int artifact2RightClassId =  ((ClassRelationLinkArtifact) artifact2).getRightClassArtifact().getId();
//				System.out.println("left1:"+artifact2LeftClassId );
//				System.out.println("right1:"+artifact2RightClassId);
//				
//				System.out.println(pairOfartifact1LeftClassId + "==" + artifact2LeftClassId + "&&"+ pairOfartifact1RightClassId +"=="+ artifact2RightClassId );
//				System.out.println(pairOfartifact1LeftClassId + "==" + artifact2RightClassId + "&&"+ pairOfartifact1RightClassId +"=="+ artifact2LeftClassId );
//				System.out.println("    ");
//				
//				if( (pairOfartifact1LeftClassId == artifact2LeftClassId && pairOfartifact1RightClassId == artifact2RightClassId )
//						|| ( pairOfartifact1LeftClassId ==  artifact2RightClassId && pairOfartifact1RightClassId == artifact2LeftClassId) ){
//					System.out.println("おけまる水産");
//					beingSim = 1.0;
//					pairRelation = artifact2;
//				}
//			
//			}
//			if(beingSim == 1.0){
//				rejectSetS.add( artifact1.getId());
//				rejectSetA.add( pairRelation.getId() );
////				System.out.println("addRjectSetA" + pairRelation.getId());
//				SimularityPair pair = new SimularityPair(artifact1, pairRelation, beingSim);
//				relationPairList.getPairList().add( pair );
//				
//				
//				System.out.println("Snp.: "+artifact1.toString());
//				System.out.println("Ans.: "+pairRelation.toString());
//			}
//		}
//		
//		this.relationNumS = relationCount(diagram1);
//		this.relationNumA = relationCount(diagram2);
//
//		this.missingRelationNum = this. relationNumA - rejectSetA.size();
//
//	}

	
	private void makeRelationPairList(List<UMLArtifact> diagram1, List<UMLArtifact> diagram2){
		double beingSim = 0;
		UMLArtifact pairRelation = null;
		Set<Integer> rejectSetS = new HashSet<Integer>();
		Set<Integer> rejectSetA = new HashSet<Integer>();
		
		
		for (SimularityPair pair : classPairList.getPairList()) {
//	        System.out.println("Pair: " + pair.toString());
	    }

		//左右端がペアになってる関連を探す
		for (UMLArtifact artifact1 : diagram1){
			beingSim = 0;
			pairRelation = null;
			if(artifact1.getClass() != ClassRelationLinkArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) {
				System.out.println("getId:"+artifact1.getId());
				continue;
			}

			for(UMLArtifact artifact2 : diagram2){
				if(artifact2.getClass() != ClassRelationLinkArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;


				int pairOfartifact1LeftClassId = -1;
				int pairOfartifact1RightClassId = -1;
				
				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true) != null) {
					pairOfartifact1LeftClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true).getId();
				}
				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true) != null) {
					pairOfartifact1RightClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true).getId();
				}
					
				int artifact2LeftClassId = ((ClassRelationLinkArtifact) artifact2).getLeftClassArtifact().getId();
				int artifact2RightClassId =  ((ClassRelationLinkArtifact) artifact2).getRightClassArtifact().getId();
				
//				System.out.println(pairOfartifact1LeftClassId + "==" + artifact2LeftClassId + "&&"+ pairOfartifact1RightClassId +"=="+ artifact2RightClassId );
//				System.out.println(pairOfartifact1LeftClassId + "==" + artifact2RightClassId + "&&"+ pairOfartifact1RightClassId +"=="+ artifact2LeftClassId );
//				System.out.println("    ");
				
				if( (pairOfartifact1LeftClassId == artifact2LeftClassId && pairOfartifact1RightClassId == artifact2RightClassId )
						|| ( pairOfartifact1LeftClassId ==  artifact2RightClassId && pairOfartifact1RightClassId == artifact2LeftClassId) ){
					System.out.println("おけまる水産");
					System.out.println("    ");
					beingSim = 1.0;
					pairRelation = artifact2;
				}
			
			}
			if(beingSim == 1.0){
				rejectSetS.add( artifact1.getId());
				rejectSetA.add( pairRelation.getId() );
//				System.out.println("addRjectSetA" + pairRelation.getId());
				SimularityPair pair = new SimularityPair(artifact1, pairRelation, beingSim);
				relationPairList.getPairList().add( pair );
				
				
				System.out.println("Snp.: "+artifact1.toString());
				System.out.println("Ans.: "+pairRelation.toString());
			}
		}
		
		this.relationNumS = relationCount(diagram1);
		this.relationNumA = relationCount(diagram2);

		this.missingRelationNum = this. relationNumA - rejectSetA.size();

	}
	
	private int relationCount(List<UMLArtifact> diagram) {
		int relCount = 0;
		for(UMLArtifact artifact : diagram){
			if(artifact.getClass()==ClassRelationLinkArtifact.class){
				relCount++;
			}
		}
		return relCount;
	}

	private double relationSim(ClassRelationLinkArtifact artifact1, ClassRelationLinkArtifact artifact2){
		double sim=0;
		double nameSim =0;
		double cardSim = 0;
		String name1 = artifact1.getName();
		String name2 = artifact2.getName();
		String LeftCardinality1;
		String RightCardinality1;
		String LeftCardinality2;
		String RightCardinality2;
		HashMap<String, HashMap<String, Double> > cardSimMap = createCardSimMap();
		//Hashmap("leftCard", Hashmap("rightCard", simurality) )

		int pairOfartifact1LeftClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true).getId();
		int artifact2LeftClassId = ((ClassRelationLinkArtifact) artifact2).getLeftClassArtifact().getId();
		
		System.out.println("pairOfartifactLeftClassId:::::"+pairOfartifact1LeftClassId);
		System.out.println("artifact2LeftClassId::::"+artifact2LeftClassId);

//		System.out.println("relationSim");
		if( pairOfartifact1LeftClassId == artifact2LeftClassId){
			LeftCardinality1 = artifact1.getRelation().getLeftCardinality();
			RightCardinality1 = artifact1.getRelation().getRightCardinality();
			LeftCardinality2 = artifact2.getRelation().getLeftCardinality();
			RightCardinality2 = artifact2.getRelation().getRightCardinality();
		}
		else{
			LeftCardinality1 = artifact1.getRelation().getLeftCardinality();
			RightCardinality1 = artifact1.getRelation().getRightCardinality();
			LeftCardinality2 = artifact2.getRelation().getRightCardinality();
			RightCardinality2 = artifact2.getRelation().getLeftCardinality();
		}
		nameSim = nameSim(name1, name2);

		//-----------------------------------------------------------------
		if(nameSim < this.NAME_SIM_T) nameSim = 0;
		
		double leftCardSim = 0, rightCardSim = 0;
		if( !cardSimMap.keySet().contains(LeftCardinality1) || !cardSimMap.keySet().contains(LeftCardinality2)){
			leftCardSim = 0;
		}
		else{
			leftCardSim = cardSimMap.get(LeftCardinality1).get(LeftCardinality2);
		}
		if( !cardSimMap.keySet().contains(RightCardinality1) || !cardSimMap.keySet().contains(RightCardinality2)){
			rightCardSim = 0;
		}
		else{
			rightCardSim = cardSimMap.get(RightCardinality1).get(RightCardinality2);
		}
		cardSim = ( leftCardSim + rightCardSim )/2.0;
		sim = 0.5 + nameSim*0.1 + cardSim*0.4; //0.5 存在自体の類似度
//		System.out.println(artifact1.toString() + " :between: "+artifact2.toString() +" = "+sim);
		System.out.println("relationSim::"+sim);
		return sim;
	}

	private 		HashMap<String, HashMap<String, Double> > createCardSimMap(){
		HashMap<String, HashMap<String, Double> > cardSimMap  = new HashMap<String, HashMap<String, Double> >();
		cardSimMap.put("0..1", new HashMap<String, Double>());
		cardSimMap.get("0..1").put("0..1", 1.0);
		cardSimMap.get("0..1").put("1", 0.8);
		cardSimMap.get("0..1").put("1..*", 0.0);
		cardSimMap.get("0..1").put("*", 0.0);

		cardSimMap.put("1", new HashMap<String, Double>());
		cardSimMap.get("1").put("0..1", 0.8);
		cardSimMap.get("1").put("1", 1.0);
		cardSimMap.get("1").put("1..*", 0.0);
		cardSimMap.get("1").put("*", 0.0);

		cardSimMap.put("1..*", new HashMap<String, Double>());
		cardSimMap.get("1..*").put("0..1", 0.0);
		cardSimMap.get("1..*").put("1", 0.0);
		cardSimMap.get("1..*").put("1..*", 1.0);
		cardSimMap.get("1..*").put("*", 0.8);

		cardSimMap.put("*", new HashMap<String, Double>());
		cardSimMap.get("*").put("0..1", 0.0);
		cardSimMap.get("*").put("1", 0.0);
		cardSimMap.get("*").put("1..*", 0.8);
		cardSimMap.get("*").put("*", 1.0);

		return cardSimMap;
	}
	public DataTable createDataForGraph(List<EditEvent> eventList, List<UMLArtifact> answer){
		ArrayList<Date> dateList = new ArrayList<Date>();
		ArrayList<Double> simularityList = new ArrayList<Double>();
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATETIME, "Date Time");
		data.addColumn(ColumnType.NUMBER, "Similarity");


		for(EditEvent ev : eventList){
			if(ev.getEventType().equals("Start")) continue;

			SimilarityManager sm = new SimilarityManager();
			CanvasUrlManager cum = new CanvasUrlManager();
			dateList.add( ev.getEditDatetime() );
			//simularityList.add( sm.getClassSimilarity( cum.fromURL(ev.getCanvasUrl(), false), answer) );
			simularityList.add( sm.getRelationSimilarity( cum.fromURL(ev.getCanvasUrl(), false), answer) );
		}
		System.out.println("CreateData");
		data.addRows(dateList.size());
		for(int i = 0 ; i<dateList.size() ; i++ ){
			System.out.println(dateList.get(i)+", "+simularityList.get(i));
			data.setValue( i ,0, dateList.get(i));
			data.setValue( i ,1, simularityList.get(i));
		}

		return data;
	}


	public int getMissingClassNum() {
		return missingClassNum;
	}

	public void setMissingClassNum(int missingClassNum) {
		this.missingClassNum = missingClassNum;
	}

	public int getMissingRelationNum() {
		return missingRelationNum;
	}

	public void setMissingRelationNum(int missingRelationNum) {
		this.missingRelationNum = missingRelationNum;
	}


	private class SimularityPairList{
		private List<SimularityPair> pairList;

		public SimularityPairList() {
			this.pairList = new ArrayList<SimilarityManager.SimularityPair>();
		}

		public List<SimularityPair> getPairList() {
			return pairList;
		}

		public void setPairList(List<SimularityPair> pairList) {
			this.pairList = pairList;
		}

		public UMLArtifact getPair(UMLArtifact key, boolean target){
			if(target){
				for (SimularityPair pair : pairList){
					if (pair.getArtifact1().getId() == key.getId() ){
						return pair.getArtifact2();
					}
				}
			}
			else{
				for (SimularityPair pair : pairList){
					if (pair.getArtifact1().getId() == key.getId() ){
						return pair.getArtifact1();
					}
				}
			}
			return null;
		}
	}
	private class SimularityPair{

		UMLArtifact artifact1;
		UMLArtifact artifact2;
		double simularity;

		public SimularityPair(UMLArtifact artifact1, UMLArtifact artifact2) {
			this( artifact1, artifact2, -1.0);
		}
		public SimularityPair(UMLArtifact artifact1, UMLArtifact artifact2,
				double simularity) {
			super();
			this.artifact1 = artifact1;
			this.artifact2 = artifact2;
			this.simularity = simularity;
		}

		public UMLArtifact getArtifact1() {
			return artifact1;
		}
		public void setArtifact1(UMLArtifact artifact1) {
			this.artifact1 = artifact1;
		}
		public UMLArtifact getArtifact2() {
			return artifact2;
		}
		public void setArtifact2(UMLArtifact artifact2) {
			this.artifact2 = artifact2;
		}
		public double getSimularity() {
			return simularity;
		}
		public void setSimularity(double simularity) {
			this.simularity = simularity;
		}
		
	}
	
	//saito
	
	public Map<String, String> getLayoutPair(List<UMLArtifact> student, List<UMLArtifact> answer){
		makeClassPairList(student, answer);
		Map<String, String> ClassNamePair = getClassNamePair(this.classPairList);	
		return ClassNamePair;

	}
	
	private Map<String, String> getClassNamePair(SimularityPairList pair){
		Map<String, String> classNamePair = new HashMap<String, String>();

		for(SimularityPair sp : pair.getPairList()){
			ClassArtifact artifact1 = (ClassArtifact)sp.getArtifact1();
			ClassArtifact artifact2 = (ClassArtifact)sp.getArtifact2();
			
			// name1=student class name, name2=answer class name
			String name1 = artifact1.getName();
			String name2 = artifact2.getName();
			
			classNamePair.put(name1, name2);

		}

		return classNamePair;
	}
	
	public Map<String, String> getLayoutRelationPair(List<UMLArtifact> student, List<UMLArtifact> answer){
//		makeRelationPairList(student, answer);
		makeRelationPairList(answer, student);
		Map<String, String> relationIdPair = getRelationIdPair(this.classPairList);	
		return relationIdPair;

	}
	
	private Map<String, String> getRelationIdPair(SimularityPairList pair){
		Map<String, String> relationIdPair = new HashMap<String, String>();

		for(SimularityPair sp : pair.getPairList()){
			ClassArtifact artifact1 = (ClassArtifact)sp.getArtifact1();
			ClassArtifact artifact2 = (ClassArtifact)sp.getArtifact2();
			
			// name1=student class name, name2=answer class name
			String id1 = artifact1.getName();
			String id2 = artifact2.getName();
			
			relationIdPair.put(id1, id2);

		}

		return relationIdPair;
	}
	
	public List<Integer> getRelationPairNum(List<UMLArtifact> diagram1, List<UMLArtifact> diagram2){
		double beingSim = 0;
		UMLArtifact pairRelation = null;
		Set<Integer> rejectSetS = new HashSet<Integer>();
		Set<Integer> rejectSetA = new HashSet<Integer>();
		List<Integer> matchNum = new ArrayList<Integer>();

		//左右端がペアになってる関連を探す
		for (UMLArtifact artifact1 : diagram1){
			beingSim = 0;
			pairRelation = null;
			if(artifact1.getClass() != ClassRelationLinkArtifact.class) continue;
			if(rejectSetS.contains( artifact1.getId() )) continue;

			for(UMLArtifact artifact2 : diagram2){
				if(artifact2.getClass() != ClassRelationLinkArtifact.class) continue;
				if(rejectSetA.contains( artifact2.getId() )) continue;


				int pairOfartifact1LeftClassId = -1;
				int pairOfartifact1RightClassId = -1;

				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true) != null)
					pairOfartifact1LeftClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getLeftClassArtifact(), true).getId();
				if(classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true) != null)
					pairOfartifact1RightClassId = classPairList.getPair(((ClassRelationLinkArtifact) artifact1).getRightClassArtifact(), true).getId();

				int artifact2LeftClassId = ((ClassRelationLinkArtifact) artifact2).getLeftClassArtifact().getId();
				int artifact2RightClassId =  ((ClassRelationLinkArtifact) artifact2).getRightClassArtifact().getId();

				if( (pairOfartifact1LeftClassId == artifact2LeftClassId && pairOfartifact1RightClassId == artifact2RightClassId )
						|| ( pairOfartifact1LeftClassId ==  artifact2RightClassId && pairOfartifact1RightClassId == artifact2LeftClassId) ){
					beingSim = 1.0;
					pairRelation = artifact2;
				}
			}
			if(beingSim == 1.0){
				System.out.println("rejectSetS:"+artifact1.getId());
				rejectSetS.add( artifact1.getId());
				System.out.println("rejectSetA:"+pairRelation.getId());
				rejectSetA.add( pairRelation.getId() );
				
				System.out.println("artifact1:"+artifact1);
				System.out.println("pairRelation:"+pairRelation);
				System.out.println("beingSim:"+beingSim);
				SimularityPair pair = new SimularityPair(artifact1, pairRelation, beingSim);
				
				
				relationPairList.getPairList().add( pair );
//				System.out.println("pair:"+pair);
	
				System.out.println("matchNum:"+pair.getArtifact1().getId());
				matchNum.add(pair.getArtifact1().getId());
			}
		}
		this.relationNumS = relationCount(diagram1);
		this.relationNumA = relationCount(diagram2);

		this.missingRelationNum = this. relationNumA - rejectSetA.size();
		
		return matchNum;
	}
	
}
