package com.my.schedule.scheduler.crawling.webtoon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.my.schedule.scheduler.common.env.ParamCode;
import com.my.schedule.scheduler.common.util.CommonUtils;


/**
 * CrawlingScheduler 
 * 
 * @version 1.0 (2016.06.13) 
 * @author  Innostick Development Team by Ryu
 */

@Component
public class WebtoonCrawlingScheduler {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("#{prop['webtoon.naver.save.dir']}")
	private static String saveDir;
	
	public static void crawlingList() {
		// scheduler 시작
//		CommonUtils.startTimeLogger(prm.get(ParamCode.METHOD_NAME));
		
//		List<Map<String, String>> resultList;
		try {
			parse("http://comic.naver.com/webtoon/detail.nhn?titleId=183559&no=200", "200");
			/*for (int i = 1; i < 283; i++) {
				this.parse("http://comic.naver.com/webtoon/detail.nhn?titleId=183559&no=" + i, String.valueOf(i));
			}*/
			// url crowling and DB insert
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		// scheduler 종료
//		CommonUtils.endTimeLogger(prm.get(ParamCode.METHOD_NAME));
	}
	
	public static void parse(String url, String tableId) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements images = doc.select("div.wt_viewer > img"); // 만화 이미지를 가져온다.
			 
			for(Element e : images) {
				String img = e.attr("src");
				String imageDir = saveDir+img.substring(img.indexOf(tableId), img.lastIndexOf("/"));
				String imageName = img.substring(img.lastIndexOf("/"));
				 
				Thread t = new ImageSaver(img, imageDir, imageName);
				t.start();
			} // end for
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}
	
	public static void main(String[] args) {
		crawlingList();
	}
	
}
