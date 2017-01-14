package com.my.schedule.scheduler.crawling.dossa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.schedule.scheduler.common.env.ParamCode;
import com.my.schedule.scheduler.common.util.ShortnerUtils;
import com.my.schedule.scheduler.crawling.dossa.mapper.DossaCrawlingMapper;


/**
 * CrawlingService
 * 
 * @version 1.0 (2016.06.13) 
 * @author  dhryu
 */

@SuppressWarnings("unchecked")
@Service("dossaCrawlingService")
public class DossaCrawlingService {
	
	protected final Log logger = LogFactory.getLog(getClass());	

	@Autowired
	private DossaCrawlingMapper dossaCrawlingMapper;
	
	public void setCrawling(Map<String, String> crawlingMap) throws Exception{
		
		String mainUrl		= crawlingMap.get(ParamCode.MAIN_URL);
		String boardUrl		= crawlingMap.get(ParamCode.TARGET_URL);
		String shortenerUrl	= crawlingMap.get(ParamCode.SHORTENER_URL);
		String apiKey		= crawlingMap.get(ParamCode.SHORTENER_APIKEY);
		
		String title, url, gId, tId, boardNo, userName = "";
		
		Document doc		= Jsoup.connect(mainUrl).get();
		Elements tables		= doc.select("form[name=form_list]");
		Elements targetTags	= tables.select("a");
		
		for (Element element : targetTags) {
			title		= element.attr("title");
			url			= boardUrl + StringUtils.substring(element.attr("href"), 1);
			gId			= StringUtils.substring(element.attr("href"), 17, 23);
			tId			= StringUtils.substring(element.attr("href"), 29, 39);
			boardNo		= StringUtils.substring(url, -5);
			userName	= element.parent().nextElementSibling().child(0).text();
			
			Map<String, String> resultMap = new HashMap();
			// insert된 내용이면 pass 없으면 insert
			if(dbInsertCheck(boardNo, gId, tId)) {
				resultMap.put("title"		, title);
				resultMap.put("url"			, ShortnerUtils.getShortUrl(shortenerUrl, apiKey, url));
				resultMap.put("gId"			, gId);
				resultMap.put("tId"			, tId);
				resultMap.put("boardNo"		, boardNo);
				resultMap.put("userName"	, userName);

				dossaCrawlingMapper.insertBoardInfo(resultMap);
			}
			// 기존 insert된 내용의 제목이 변경되면 update
			if(dbUpdateCheck(boardNo, title, gId, tId)) {
				resultMap.put("title"		, title);
				resultMap.put("url"			, ShortnerUtils.getShortUrl(shortenerUrl, apiKey, url));
				resultMap.put("gId"			, gId);
				resultMap.put("tId"			, tId);
				resultMap.put("boardNo"		, boardNo);
				resultMap.put("userName"	, userName);

				dossaCrawlingMapper.updateBoardInfo(resultMap);
			}
		}
	}
	
	private boolean dbInsertCheck(String boardNo, String gId, String tId) {
		boolean result = false;
		
		Map param = new HashMap<>();
		param.put("boardNo"	, boardNo);
		param.put("gId"		, gId);
		param.put("tId"		, tId);
		
		// 해당 boardNo와 gId, tId 정보가 있는지 확인
		// 있으면 false, 없으면 true 반환
		List resultList = dossaCrawlingMapper.getBoardInfo(param);
		
		if(resultList.isEmpty()) {
			result = true;
		}
		return result;
	}

	private boolean dbUpdateCheck(String boardNo, String title, String gId, String tId) {
		boolean result = true;
		
		Map param = new HashMap<>();
		param.put("boardNo"	, boardNo);
		param.put("title"	, title);
		param.put("gId"		, gId);
		param.put("tId"		, tId);
		
		// 해당 boardNo와 gId, tId 정보가 있는지 확인
		// 있으면 true, 없으면 false 반환
		List resultList = dossaCrawlingMapper.getBoardInfo(param);
		
		if(resultList.isEmpty()) {
			result = false;
		}
		return result;
	}
}
