package com.my.schedule.scheduler.message.telegram;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.my.schedule.scheduler.common.util.CommonUtils;


/**
 * TelegramSendScheduler 
 * 
 * @version 1.0 (2016.06.13) 
 * @author  Innostick Development Team by Ryu
 */

@Component
public class TelegramSendScheduler {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("#{telegramProp['telegram.dossa.chat.id']}")
	private String dossaChatId;
	@Value("#{telegramProp['telegram.dossa.bot.token']}")
	private String dossaBotToken;
	@Value("#{telegramProp['telegram.test.chat.id']}")
	private String chatId;
	@Value("#{telegramProp['telegram.test.bot.token']}")
	private String botToken;
	
	
	@Autowired
	private TelegramSendService telegramSendService;
	
	public void dossaTelegramSend() {
		try {
			String methodName = "dossaTelegramSend :::::: [" + chatId + "]";
			CommonUtils.startTimeLogger(methodName);
			
			telegramSendService.dossaTelegramSendMassage(dossaChatId, dossaBotToken);
			
			CommonUtils.endTimeLogger(methodName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void telegramSend(String chatId, String message) {
		String methodName = "telegramSend :::::: [" + chatId + "]";
		CommonUtils.startTimeLogger(methodName);
		
		telegramSendService.telegramSendMassage(chatId, dossaBotToken, message);
		
		CommonUtils.endTimeLogger(methodName);
	}
}
