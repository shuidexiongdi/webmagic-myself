package com.myself.processor;

import java.util.Set;
import java.util.TreeSet;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * ´¦ÀíÍ¼Ïñ
 * @author djq
 *
 */
public abstract class ImagePageProcessor implements PageProcessor {
	
//	private Set<String> imageSrcs = new TreeSet<String>();
	private static ThreadLocal<Set<String>> threadLocal = new ThreadLocal<Set<String>>();  

	@Override
	public void process(Page page) {
		Set<String> imageSrcs = new TreeSet<String>();
		imageSrcs.addAll(page.getHtml().xpath("//img/@src").all());
		threadLocal.set(imageSrcs);

	}
	
	public Set<String> getImageSrcs() {
		System.out.println(threadLocal.get().size());
		return threadLocal.get();
	}

}
