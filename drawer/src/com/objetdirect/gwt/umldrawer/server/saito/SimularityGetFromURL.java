/**
 *
 */
package com.objetdirect.gwt.umldrawer.server.saito;


import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;
import com.objetdirect.gwt.umldrawer.server.AnalysisServiceImpl;



/**
 * @author saito
 *
 */
public class SimularityGetFromURL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String inputFilePath = "excel/input.xlsx";   
        String csvOutputPath = "excel/output.csv";
        
        
        try (FileInputStream fis = new FileInputStream(inputFilePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

               Sheet sheet = workbook.getSheetAt(0);
               
               // CSVファイルに書き込むためのWriterを開く
               try (PrintWriter writer = new PrintWriter(new FileWriter(csvOutputPath))) {

                   for (Row row : sheet) {
                       StringBuilder csvRow = new StringBuilder();

                       for (Cell cell : row) {
                           if (cell.getCellType() == CellType.STRING) {
                               String originalValue = cell.getStringCellValue();
                               System.out.println(originalValue);
                               if (originalValue.startsWith("PD")) {
                                   System.out.println(urlChange(originalValue)[0] + ", " + urlChange(originalValue)[1]);
                                   // セルを数値型に変更する
                                   double newValue = urlChange(originalValue)[0] + urlChange(originalValue)[1];
                                   csvRow.append(newValue).append(",");
                               } else {
                                   csvRow.append(originalValue).append(",");
                               }
                           } else if (cell.getCellType() == CellType.NUMERIC) {
                               csvRow.append(cell.getNumericCellValue()).append(",");
                           }
                       }
                       
                       // 最後のカンマを削除して改行を追加
                       if (csvRow.length() > 0) {
                           csvRow.setLength(csvRow.length() - 1);
                       }
                       writer.println(csvRow.toString());
                   }
                   
               } catch (IOException e) {
                   e.printStackTrace();
               }

               // Excelファイルを更新する（必要があれば）
//               try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
//                   workbook.write(fos);
//               }

               System.out.println("CSVファイルの生成とExcelファイルの更新が完了しました。");

           } catch (IOException e) {
               e.printStackTrace();
           }


	}
	
	private static byte[] stringToBytes(String str) {
		try {
			//String -> byte[]
			byte [] bytes = str.getBytes("UTF-8");  //String.getBytes();    or      String.getBytes(encoding);

			//byte [] -> String
			String xx = new String(bytes, "UTF-8"); //
			return bytes;
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
	
	private static String base64ToString(String base64){
		String decodedString=null;
		if(base64!=null){
			byte[] bytes = stringToBytes(base64);
			byte[] decoded = Base64.decodeBase64(bytes);
			try {
				decodedString = new String(decoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return decodedString;
	}
	
	private static double[] urlChange(String studentUrl) {
		SimilarityManager sm = new SimilarityManager();
		AnalysisServiceImpl asi = new AnalysisServiceImpl();

		//		演習19の答え（印鑑）授業演習3　支援あり
		String answerUrl = "PDQ+XUNsYXNzJCg2MDUsMTMxKSHmm7jpoZ4hIS3lkI3liY0lITs8NT5dQ2xhc3MkKDI4OCwxMzMpIeWNsOmRkSEhLeaWh+WtlyUhOzw2Pl1DbGFzcyQoMjg4LDM1OSkh5Y2wISEhOzw3Pl1DbGFzcyQoNjA3LDM1OSkh5oq85Y2w566H5omAISEhOzwxMj5dQ2xhc3NSZWxhdGlvbkxpbmskPDU+ITw2PiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMSEhIU5vbmUhMC4uKiEhOzwxMz5dQ2xhc3NSZWxhdGlvbkxpbmskPDY+ITw3PiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMC4uMSEhIU5vbmUhMSEhOzwyMD5dQ2xhc3NSZWxhdGlvbkxpbmskPDc+ITw0PiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMC4uKiEhIU5vbmUhMSEhOw==";

		//		演習20の答え（タイムテーブル）授業演習1　支援なし
//		String answerUrl = "PDA+XUNsYXNzJCg0NTMsMjAwKSHjgrPjg54hISE7PDE+XUNsYXNzJCgxMzksMTkyKSHmmYLplpPluK8hIS3plovlp4vmmYLliLslITs8Mj5dQ2xhc3MkKDc2MiwxODApIeODiOODqeODg+OCryEhLeODhuODvOODniUt5Lya5aC0JSE7PDM+XUNsYXNzJCg0NTgsMzcyKSHjg5fjg63jgrDjg6njg6AhIS3jgr/jgqTjg4jjg6slLeism+W4qyUhOzw0Pl1DbGFzc1JlbGF0aW9uTGluayQ8Mz4hPDA+IVNpbXBsZVJlbGF0aW9uIU5hbWUhU29saWQhTm9uZSEwLi4xISEhTm9uZSExISE7PDU+XUNsYXNzUmVsYXRpb25MaW5rJDwwPiE8MT4hU2ltcGxlUmVsYXRpb24hTmFtZSFTb2xpZCFOb25lITEuLiohISFOb25lITEhITs8Nj5dQ2xhc3NSZWxhdGlvbkxpbmskPDI+ITwwPiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMSEhIU5vbmUhMS4uKiEhOw==";

		//		演習21の答え（辞令）授業演習4　強制支援
//		String answerUrl = "PDA+XUNsYXNzJCg0NTQsNjUpIeekvuWToSEhLeekvuWToeeVquWPtyUt5rCP5ZCNJSE7PDI+XUNsYXNzJCg0NTcsMjI4KSHovp7ku6QhIS3nmbrku6Tml6UlITs8NT5dQ2xhc3MkKDMwNiwzNzgpIemFjeWxnui+nuS7pCEhITs8MTE+XUNsYXNzJCg2MDYsMzgwKSHmmIfmoLzovp7ku6QhISE7PDE2Pl1DbGFzc1JlbGF0aW9uTGluayQ8Mj4hPDA+IVNpbXBsZVJlbGF0aW9uIU5hbWUhU29saWQhTm9uZSEwLi4qISEhTm9uZSExISE7PDE3Pl1DbGFzc1JlbGF0aW9uTGluayQ8NT4hPDI+IVNpbXBsZVJlbGF0aW9uIU5hbWUhU29saWQhTm9uZSExISEhTm9uZSExISE7PDE4Pl1DbGFzc1JlbGF0aW9uTGluayQ8MTE+ITwyPiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMSEhIU5vbmUhMSEhOw==";

		//		演習22の答え（定食）授業演習2　支援なし
//		String answerUrl = "PDA+XUNsYXNzJCg0NDEsMTA2KSHlrprpo58hIS3lkI3liY0lITs8MT5dQ2xhc3MkKDEzMiwzMjMpIeS4u+mjnyEhITs8Mj5dQ2xhc3MkKDMyOCwzMjEpIeaxgeeJqSEhITs8Mz5dQ2xhc3MkKDUzNywzMjApIeS4u+iPnCEhITs8ND5dQ2xhc3MkKDc1NSwzMTkpIeWJr+iPnCEhITs8NT5dQ2xhc3NSZWxhdGlvbkxpbmskPDA+ITwyPiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMSEhIU5vbmUhMSEhOzw2Pl1DbGFzc1JlbGF0aW9uTGluayQ8MD4hPDE+IVNpbXBsZVJlbGF0aW9uIU5hbWUhU29saWQhTm9uZSExISEhTm9uZSExISE7PDc+XUNsYXNzUmVsYXRpb25MaW5rJDwzPiE8MD4hU2ltcGxlUmVsYXRpb24hTmFtZSFTb2xpZCFOb25lITEhISFOb25lITEhITs8OD5dQ2xhc3NSZWxhdGlvbkxpbmskPDQ+ITwwPiFTaW1wbGVSZWxhdGlvbiFOYW1lIVNvbGlkIU5vbmUhMC4uKiEhIU5vbmUhMSEhOw==";
		
		
		String encodeStudentUrl = base64ToString(studentUrl);
		String encodeAnswerUrl = base64ToString(answerUrl);

		
		List<UMLArtifact> diaList1 = asi.fromURL(encodeStudentUrl);
		List<UMLArtifact> diaList2 = asi.fromURL(encodeAnswerUrl);
		
				    
		double cs = sm.getClassSimilarity(diaList1, diaList2);    
		double rs = sm.getRelationSimilarity(diaList1, diaList2);
		
		return new double[]{cs, rs};
	}
	
}
