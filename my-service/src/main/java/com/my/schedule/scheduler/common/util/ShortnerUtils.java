package com.my.schedule.scheduler.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class ShortnerUtils {
	protected final static Log logger = LogFactory.getLog(ShortnerUtils.class.getClass());

	/**
	 * google의 shortner api를 이용해 short URL을 생성한다.
	 * @param shortenerUrl	// google shortnerUrl api 
	 * @param apiKey		// google shortnerUrl api key
	 * @param originUrl		// 변경할 Original URL
	 * @return
	 */
	public static String getShortUrl(String shortenerUrl, String apiKey, String originUrl) {
		
		String resultUrl = shortenerUrl;
		logger.debug("##### " + shortenerUrl + apiKey);
		
		logger.debug("##### ShortnerUtils Start originUrl is [ " + originUrl + " ]");
		
		String originUrlJsonStr = "{\"longUrl\":\"" + originUrl + "\"}";
		logger.debug("##### 1. originUrlJsonStr is [ " + originUrlJsonStr + " ]");
		
		// Google에 변환 요청을 보내기위해 java.net.URL, java.net.HttpURLConnection 사용
		URL					url			= null;
		HttpURLConnection	connection	= null;
		OutputStreamWriter	osw			= null;
		BufferedReader		br			= null;
		StringBuffer		sb			= null;		// Google short URL서비스 결과 JSON String Data
		JSONObject			jsonObj		= null;		// 결과 JSON String Data로 생성할 JSON Object
		
		// get방식으로 key(사용자키) 파라미터와, JSON 데이터로 longUrl(단축시킬 원본 URL이 담긴 JSON 데이터) 를 셋팅하여 전송
		try {
			url = new URL(shortenerUrl + apiKey);
			logger.debug("##### 2. DESTINATION_URL : " + url.toString() );
		}catch(Exception e){
			logger.debug("[ERROR] URL set Failed");
			e.printStackTrace();
			return resultUrl;
		}
		
		// 지정된 URL로 연결 설정
		try{
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "toolbar");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
		}catch(Exception e){
			logger.debug("[ERROR] Connection open Failed");
			e.printStackTrace();
			return resultUrl;
		}
		
		//  결과 JSON String 데이터를 StringBuffer에 저장
		//  필요에 따라 JSON Obejct 혹은 Map으로 셋팅 (현재 Map은 주석처리)
		try{
			// Google 단축URL 서비스 요청
			osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write(originUrlJsonStr);
			osw.flush();
 
			// BufferedReader에 Google에서 받은 데이터를 넣어줌
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			 
			// BufferedReader 내 데이터 StringBuffer에 저장
			sb = new StringBuffer();
			
			String buf = "";
			
			while ((buf = br.readLine()) != null) {
				sb.append(buf);
			}
			
			logger.debug("[DEBUG] RESULT_JSON_DATA : " + sb.toString());
			 
			// Google에서 받은 JSON String을 JSONObject로 변환
			jsonObj = new JSONObject(sb.toString());
			 
			// 결과 JSON Object의 데이터 확인 (주석처리)
			/*
			String[] strs = JSONObject.getNames(jsonObj);
			for( String idx : strs ){
				System.out.println("[DEBUG] PARSING_JSON_DATA : [" + idx + "] - [" + jsonObj.getString(idx) + "]");
			}
			*/
			
			// 수신받은 JSON String 데이터를 필요시 Map에 저장
			// return 타입을 Map 으로 받고자 할 때 사용 (현재는 결과 url만 String으로 리턴할 것이므로 주석처리)
			// ObjectMapper mapper = new ObjectMapper();
			// Map<String,String> map = mapper.readValue(sb.toString(), new TypeReference<HashMap<String, String>>() {});
			// resultUrl = (String) map.get("id"); // Map 으로 저장했을 때
			 
			resultUrl = jsonObj.getString("id");
		
		}catch (Exception e) {
			logger.debug("[ERROR] Result JSON Data(From Google) set JSONObject Failed");
			e.printStackTrace();
			return resultUrl;
		}finally{
			if (osw != null)	try{ osw.close();   } catch(Exception e) { e.printStackTrace(); }
			if (br  != null)	try{ br.close();	} catch(Exception e) { e.printStackTrace(); }
		}
		 
		logger.debug("##### ShortnerUtils End resultUrl is [ " + resultUrl + " ]");
		return resultUrl;
	}
}
