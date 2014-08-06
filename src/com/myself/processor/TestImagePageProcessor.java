package com.myself.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TestImagePageProcessor extends ImagePageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setUserAgent(
		    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		super.process(page);
		page.addTargetRequests( page.getHtml().xpath("//tr[@class='tr3']//td[@class='subject']/a/@href").all());
		page.addTargetRequests( page.getHtml().xpath("//div[@class='pages']/a/@href").all());
		page.addTargetRequests( page.getHtml().xpath("//a[@class='mr10']/@href").all());
//		page.addTargetRequests( page.getHtml().xpath("//dic[@class='masonry']/div/a/@href").all());
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
