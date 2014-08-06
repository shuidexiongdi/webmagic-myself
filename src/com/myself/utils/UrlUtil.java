package com.myself.utils;


public class UrlUtil {
	
	//�Ժ�׺Ϊjpg|jpeg|bmp|gif|png�����ģ����Ҳ����ִ�Сд�ģ�(?i:xxxxxx���������Ҳ���ǰ������.��
	public static final String PICTURE_REGEX = ".*?\\.(?i:(jpg|jpeg|bmp|gif|png))$";  
	
	//��http��ͷ���м����://���������1�������ַ���
	public static final String ABSOLUTE_REGEX = "^[http://|https://].*?";//[//w//.//-/:]+
	
	/**
	 * �Ƿ�Ϊ���Ե�ַ����http://��ͷ
	 * @param url
	 * @return
	 */
	public static boolean isAbsoluteUrl(String url) {
		return url.matches(ABSOLUTE_REGEX);
	}
	
	/**
	 * �Ƿ���һ��ͼƬ��ַ���ԡ�.��׺��������
	 * @param url
	 * @return
	 */
	public static boolean isAPictureUrl(String url) {
		return url.matches(PICTURE_REGEX);
	}
	
	/**
	 * �Ƿ���һ��ͼƬ�ľ��Ե�ַ
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
