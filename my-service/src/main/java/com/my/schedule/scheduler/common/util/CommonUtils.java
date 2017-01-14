package com.my.schedule.scheduler.common.util;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonUtils {
	protected final static Log logger = LogFactory.getLog(CommonUtils.class.getClass());

	private static long startTime, endTime;

	/*
	 * 집계날짜 체크
	 */
	public static String getTargetDate() {
		LocalDate today = LocalDate.now();
		String targetDate = today.minusDays(1).toString().replace("-", "");
		return targetDate;
	}

	/*
	 * 시작시간 체크
	 */
	public static void startTimeLogger(String methodName) {
		LocalTime now = LocalTime.now();
		startTime = System.currentTimeMillis();
		logger.debug("##################################################################################################################################");
		logger.info("##### [START] [ " + methodName + " ] :::: START TIME :::: [ " + now.toString() + " ]");
	}

	/*
	 * 종료시간 체크
	 */
	public static void endTimeLogger(String methodName) {
		LocalTime now = LocalTime.now();
		endTime = System.currentTimeMillis();
		logger.info("##### [END] [ " + methodName + " ] :::: END TIME :::: [ " + now.toString() + " ]");
		logger.info("##### CountTime(sec.0f) : " + ( endTime - startTime )/1000.0f +"sec");
		logger.info("##################################################################################################################################");
	}
}
