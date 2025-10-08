package com.objetdirect.gwt.umldrawer.client.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
	/* MD5脆弱性のため使用中止　代わりにしたのものを使います byこんの
	//	文字列からダイジェスト(暗号化したもの)を生成する
	public String getPassword_encryption(String data) {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] dat = data.getBytes();
			md.update(dat);//dat配列からダイジェストを計算する
			byte[] dig = md.digest();
			String user_pass="";
			//一文字ずつStringに変換していく
			for (int g = 0; g < 16; g++) {
				user_pass += dig[g];
			}
			return user_pass;

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return "";
			}
	}
	*/



	public static String getPassword_encryption(String data) {
		String salt = "KIfU";
		int stretchCount = 10;
		String encryptedPassword="";
		for(int i=0;i<stretchCount;i++){
			encryptedPassword = getSha256(salt + data);
		}
		return encryptedPassword;
	}


	private static String getSha256(String text){
		MessageDigest md = null;
		StringBuffer buf = new StringBuffer();
		try{
			md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			byte[] digest = md.digest();

			for(int i=0;i<digest.length; i++){
				buf.append(String.format("%02x", digest[i]));
			}
		}catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return buf.toString();
	}

}