package com.my.schedule.scheduler.common.env;

/**
 * ParamCode
 * 
 * @version 1.0 (2016.01.25)
 * @author  Innostick Development Team
 */
public interface ParamCode {
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Common Area
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String CODE					= "code";			// 리턴 코드
	public static final String MESSAGE				= "message";		// 리턴 메세지
	public static final String METHOD_NAME			= "methodName";		// 로깅용 메서드명
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Tomcat catalina.properties Area
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String SERVER_MODE			= "server.mode";	// Properties 설정 서버모드네임
	public static final String SERVER_MODE_LOCAL	= "local";			// 로컬 서버모드
	public static final String SERVER_MODE_DEV		= "dev";			// 개발 서버모드
	public static final String SERVER_MODE_REAL		= "real";			// 운영 서버모드
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// External Area
	////////////////////////////////////////////////////////////////////////////////////////////////////	
	// Google Shortner
	public static final String SHORTENER_URL		= "shortenerUrl";			// google shortner api url
	public static final String SHORTENER_APIKEY		= "shortenerApiKey";		// google shortner api key
	
	// Crowling url
	public static final String MAIN_URL				= "mainUrl";				// 크롤링 할 url
	public static final String TARGET_URL			= "boardUrl";				// 배포 할 url
	

}
