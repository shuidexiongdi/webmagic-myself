package com.myself.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class FileSaver {

	private static final Log log = LogFactory.getLog(FileSaver.class);

	private String dir;

	private String fileName;

	private String url;
	
	public static FileSaver getFileSaver(String dir, String fileName, String url) {
		return new FileSaver(dir,fileName,url);
	}
	
	FileSaver(String dir, String fileName, String url) {
		this.dir = dir.replace("*", "");
		this.fileName = fileName;
		this.url = url;
	}

	public void save() throws Exception {
		mkDirDeep(dir);
		String file = dir + "/" + fileName;
		BufferedOutputStream out = null;
		byte[] bit = getByte(url);
		if (bit.length > 0) {
			try {
				out = new BufferedOutputStream(new FileOutputStream(file));
				out.write(bit);
				out.flush();
				log.info("Create File success! [" + file + "]");
			} finally {
				if (out != null)
					out.close();
			}
		}
	}

	private void mkDirDeep(String dirPath) {
		String[] paths = dirPath.split("/");
		String path = null;
		for(int i = 0, j = paths.length; i < j; i++) {
			if(i == 0) {
				path = paths[i];
			}else {
				path += "/" + paths[i];
			}
			File pageDirParent = new File(path);
			if (!pageDirParent.exists()) {
				pageDirParent.mkdir();
			}
		}
	}
	
	/**
	 * 取得网络资源
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	static byte[] getByte(String uri) throws Exception {
		//模拟多个客户端，还是一个呢？自己选择吧！
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);
		try {
			HttpResponse resonse = client.execute(get);
			if (resonse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resonse.getEntity();
				if (entity != null) {
					return EntityUtils.toByteArray(entity);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
		}
		log.error("获取文件失败！[url=" + uri + "]");
		return new byte[0];
	}
}
