package com.myself;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;

import com.myself.processor.MeituanWaimaiPageProcessor;
import com.myself.processor.TestImagePageProcessor;
import com.myself.utils.FileSaver;
import com.myself.utils.UrlUtil;

public class SpiderManager {

	private static final String SAVE_DIR = "E:/spider";
	static ExecutorService executorService = Executors.newFixedThreadPool(10);

	static void runMeituanWaimai() {
		final MeituanWaimaiPageProcessor mwPageProcessor = new MeituanWaimaiPageProcessor();
		List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
		SpiderListener spiderListener = new SpiderListener() {

			@Override
			public void onSuccess(Request request) {
				Set<String> srcs = mwPageProcessor.getImageSrcs();
				for (String imgSrc : srcs) {
					System.out.println(imgSrc);
				}
			}

			@Override
			public void onError(Request request) {
				// TODO Auto-generated method stub

			}
		};
		spiderListeners.add(spiderListener);

		Spider.create(mwPageProcessor)
				.addPipeline(new FilePipeline("E:/spider/meituan"))
				.setSpiderListeners(spiderListeners)
				.addUrl("http://www.waimai.meituan.com/shenzhen").thread(5)
				.run();
	}

	static void runTest() {

		final TestImagePageProcessor testPageProcessor = new TestImagePageProcessor();
		List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
		SpiderListener spiderListener = new SpiderListener() {

			@Override
			public void onSuccess(Request request) {
				Set<String> srcs = testPageProcessor.getImageSrcs();
				for (final String imgSrc : srcs) {
					System.out.println(imgSrc);
					if(!UrlUtil.isAPictureAbsoluteUrl(imgSrc)) {
						continue;
					}
					
					final String fileName = imgSrc.substring(imgSrc
							.lastIndexOf("."));
					try {
						executorService.execute(new Runnable() {

							@Override
							public void run() {
								try {
									FileSaver.getFileSaver(
											SAVE_DIR + "/" + "test",
											UUID.randomUUID().toString()
													+ fileName, imgSrc).save();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(Request request) {
				// TODO Auto-generated method stub

			}
		};
		spiderListeners.add(spiderListener);

		String[] urls = {"http://x7751.com/bbs/thread.php?fid=57"};

		Spider.create(testPageProcessor)
				// .addPipeline(new FilePipeline("E:/spider/meituan"))
				.setSpiderListeners(spiderListeners)
				.setScheduler(
						//ШЅжи
						new QueueScheduler()
								.setDuplicateRemover(new BloomFilterDuplicateRemover(
										10000))).addUrl(urls).thread(5)
				.run();
	}

	public static void main(String[] args) {
		// runMeituanWaimai();
		runTest();
	}

}
