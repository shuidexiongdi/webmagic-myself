package com.myself.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class MeituanWaimaiPageProcessor extends ImagePageProcessor implements PageProcessor {
	
	private final static String URL_DISTRICT = "http://www.waimai.meituan.com/shenzhen";
	private final static String URL_RESTAURANT = "http://www.waimai.meituan.com/home/\\w+";
	private final static String URL_RESTAURANT2 = "http://www.waimai.meituan.com/geo/geohash\\?.*";
	private final static String URL_FOOD = "http://www.waimai.meituan.com/restaurant/\\w+";
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setUserAgent(
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		super.process(page);
		if(page.getUrl().regex(URL_DISTRICT).match()) {
			districtProcess(page);
		}
		if(page.getUrl().regex(URL_RESTAURANT).match() || page.getUrl().regex(URL_RESTAURANT2).match()) {
			restaurantProcess(page);
		}
		if(page.getUrl().regex(URL_FOOD).match()) {
			foodProcess(page);
		}
	}
	
	private void districtProcess(Page page) {
		//地区
		List<String> districtUrls = page.getHtml().css("ul.district-list").links().regex(".*/geo/geohash\\?.*").all();
		page.addTargetRequests(districtUrls);
//		page.putField("district", page.getHtml().xpath("//ul[@class='district-list']/li/a/text()").all().toString());
	}
	
	private void restaurantProcess(Page page) {
//		//商家
		List<String> restaurantUrls = page.getHtml().css("a.rest-atag").links().regex(".*/restaurant/.*").all();
		page.addTargetRequests(restaurantUrls);		
//		page.putField("restaurant", page.getHtml().xpath("//div[@class='ori-foodtype-nav']/ul/li/a/title").all());
	}
	
	private void foodProcess(Page page) {
		//菜品
		page.putField("foodtypes", page.getHtml().xpath("//div[@class='ori-foodtype-nav']/ul/li/a/span/@title/text()").all());
		page.putField("foodtype", page.getHtml().xpath("//div[@class='food-nav']//div[@class='category']/h3/span/text()").all());
		page.putField("food", page.getHtml().css("div.fl.description").css("div.na.nodesc").all());
//		page.putField("food", page.getHtml().xpath("//div[@class='text-food-count']//div[@class='text-food.clearfix']//div[@class='fl.description']//div[@class='na.nodesc']/text()").all());
	
	}

	@Override
	public Site getSite() {
		 return site;
	}
	
}
