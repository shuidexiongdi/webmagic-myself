package com.myself.utils;


public class UrlUtil {
	
	//以后缀为jpg|jpeg|bmp|gif|png结束的，并且不区分大小写的（(?i:xxxxxx）），并且不管前面有无.的
	public static final String PICTURE_REGEX = ".*?\\.(?i:(jpg|jpeg|bmp|gif|png))$";  
	
	//以http开头，中间存在://，后面跟着1个以上字符的
	public static final String ABSOLUTE_REGEX = "^[http://|https://].*?";//[//w//.//-/:]+
	
	/**
	 * 是否为绝对地址。以http://开头
	 * @param url
	 * @return
	 */
	public static boolean isAbsoluteUrl(String url) {
		return url.matches(ABSOLUTE_REGEX);
	}
	
	/**
	 * 是否是一个图片地址。以“.后缀名”结束
	 * @param url
	 * @return
	 */
	public static boolean isAPictureUrl(String url) {
		return url.matches(PICTURE_REGEX);
	}
	
	/**
	 * 是否是一个图片的绝对地址
	 * @param url
	 * @return
	 */
	public static boolean isAPictureAbsoluteUrl(String url) {
		return isAbsoluteUrl(url) && isAPictureUrl(url);
	}
	
	
	public static void main(String[] args) {
		System.out.println(isAPictureUrl("33xxx.jpg"));
		System.out.println(isAPictureUrl("33.xxx.jPg"));
		System.out.println(isAbsoluteUrl("http://ddd"));
		System.out.println(isAbsoluteUrl("2http2://ddd"));
		System.out.println(isAPictureUrl("http://affiliates.mature-amateur-interracial.com/free/2398/pictures/38304.jpg"));
		System.out.println(isAbsoluteUrl("http://affiliates.mature-amateur-interracial.com/free/2398/pictures/38304.jpg"));
		System.out.println(isAPictureAbsoluteUrl("http://affiliates.mature-amateur-interracial.com/free/2398/pictures/38304.jpg"));
		System.out.println(isAPictureAbsoluteUrl("http://img123.laodabo.com:808/allimg/c120114/132A20F3J3F-344527.jpg"));
		System.out.println(isAPictureAbsoluteUrl("http://www.zoucaol.com/attachout/Mon_1470/3_1945271_5ccdca58cc9c301.jpg"));
	}
	
}
