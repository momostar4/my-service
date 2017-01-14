package com.my.schedule.scheduler.crawling.dossa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.my.schedule.scheduler.common.env.ParamCode;
import com.my.schedule.scheduler.common.util.CommonUtils;


/**
 * CrawlingScheduler 
 * 
 * @version 1.0 (2016.06.13) 
 * @author  dhryu
 */

@Component
public class DossaCrawlingScheduler {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("#{shortnerProp['google.shortner.url']}")
	private String shortenerUrl;
	@Value("#{shortnerProp['google.shortner.api.key']}")
	private String shortnerApiKey;
	@Value("#{prop['dossa.main.url']}")
	private String dossaMainUrl;
	@Value("#{prop['dossa.board.url']}")
	private String dossaBoardUrl;
	
	
	@Autowired
	private DossaCrawlingService dossaCrawlingService;
	
	public void crawlingList(Map<String, String> prm) {
		// scheduler 시작
		CommonUtils.startTimeLogger(prm.get(ParamCode.METHOD_NAME));
		
		List<Map<String, String>> resultList;
		try {
			
			// url crowling and DB insert
			dossaCrawlingService.setCrawling(prm);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		// scheduler 종료
		CommonUtils.endTimeLogger(prm.get(ParamCode.METHOD_NAME));
	}

	public void dossaCrawlingList() {
		String methodName		= "dossaCrowlingList";
		//String[] dossaMainUrls	= dossaMainUrl.split(":::");
		
		//for (String dossaMainUrl : dossaMainUrls) {
			// Parameter Setting
		Map<String, String> crawlingMap = new HashMap<String, String>();
		crawlingMap.put(ParamCode.SHORTENER_URL		, shortenerUrl);
		crawlingMap.put(ParamCode.SHORTENER_APIKEY	, shortnerApiKey);
		crawlingMap.put(ParamCode.MAIN_URL			, dossaMainUrl);
		crawlingMap.put(ParamCode.TARGET_URL		, dossaBoardUrl);
		crawlingMap.put(ParamCode.METHOD_NAME		, methodName);
		
		this.crawlingList(crawlingMap);
		//}
	}
	
}
