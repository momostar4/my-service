package com.my.schedule.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.schedule.scheduler.message.telegram.TelegramSendService;

@Component
public class TestController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	TelegramSendService telegramSendService;
	
	public void testSend() {
		String chatId = "37055787";
		String botToken = "242282429:AAGs5Ei21exLTaLODG4qNIz0Q74HAoig_8c";
		String message = "전송함!!!!";
		
		logger.debug(":::::::::::::::::::::::::::::::: TestController ::::::::::::::::::::::::::::::::"+chatId + "," + botToken);
		
		telegramSendService.testTelegramSendMessage(chatId, botToken, message);
	}
}
