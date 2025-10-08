package com.objetdirect.gwt.umldrawer.client.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Browser {

	/** ブラウザ不明 */
	public static String BROWSER_UNKNOWN = "0";
	/** ブラウザIE */
	public static String BROWSER_IE = "1";
	/** ブラウザFirefox */
	public static String BROWSER_FIREFOX = "2";
	/** ブラウザOpera */
	public static String BROWSER_OPERA = "3";
	/** ブラウザChrome */
	public static String BROWSER_CHROME = "4";
	/** ブラウザSafari */
	public static String BROWSER_SAFARI = "5";
	/** ブラウザNetscape */
	public static String BROWSER_NETSCAPE = "6";

	/**
	 * ブラウザの判定を行います。
	 * @param request {@link HttpServletRequest}
	 * @return ブラウザを表す文字列
	 */
	public static String getBrowser(HttpServletRequest request) {

		String sUserAgent = request.getHeader("user-agent");

		if ( isIE(sUserAgent) ) {
			return BROWSER_IE;
		}
		if ( isFirefox(sUserAgent) ) {
			return BROWSER_FIREFOX;
		}
		if ( isOpera(sUserAgent) ) {
			return BROWSER_OPERA;
		}
		if ( isChrome(sUserAgent) ) {
			return BROWSER_CHROME;
		}
		if ( isSafari(sUserAgent) ) {
			return BROWSER_SAFARI;
		}
		if ( isNetscape(sUserAgent) ) {
			return BROWSER_NETSCAPE;
		}
		return BROWSER_UNKNOWN;
	}


	/**
	 * ブラウザがIEであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return IEであるかどうか
	 */
	public static boolean isIE(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [0-9]\\.[0-9]).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}


	/**
	 * ブラウザがFirefoxであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return Firefoxであるかどうか
	 */
	public static boolean isFirefox(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Firefox/)+[0-9]\\.[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}


	/**
	 * ブラウザがOperaであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return Opereであるかどうか
	 */
	public static boolean isOpera(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Opera)+/? ?[0-9]\\.[0-9][0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}


	/**
	 * ブラウザがChromeであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return Chromeであるかどうか
	 */
	public static boolean isChrome(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Chrome)+/?[0-9]\\.?[0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}


	/**
	 * ブラウザがSafariであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return Safariであるかどうか
	 */
	public static boolean isSafari(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Version/)+[0-9]\\.?[0-9]?\\.?[0-9]? Safari).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}


	/**
	 * ブラウザがNetscapeであるかどうかの判定を行います。
	 * @param sUserAgent ユーザエージェント
	 * @return Netscapeであるかどうか
	 */
	public static boolean isNetscape(String sUserAgent) {
		Pattern pattern = Pattern.compile(".*((Netscape)+[0-9]\\.[0-9][0-9]?).*");
		Matcher matcher = pattern.matcher(sUserAgent);
		boolean bMatch = matcher.matches();
		return bMatch;
	}

}