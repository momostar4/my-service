package com.my.schedule.scheduler.message.telegram;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.schedule.scheduler.message.telegram.mapper.TelegramSendMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;


/**
 * CrawlingService
 * 
 * @version 1.0 (2016.06.13) 
 * @author  Innostick Development Team
 */

@SuppressWarnings("unchecked")
@Service("telegramSendService")
public class TelegramSendService {
	
	protected final Log logger = LogFactory.getLog(getClass());	

	@Autowired
	private TelegramSendMapper telegramSendMapper;
	
	
	public void dossaTelegramSendMassage(String chatId, String botToken) throws Exception{
		List<Map<String, String>> messageList = this.selectMassageList();
		String message = "";
		for (Map<String, String> param : messageList) {
			if(StringUtils.equals(param.get("update_yn"), "N")) {
				message = "[" + param.get("user_name") + "] 님이 새글을 등록하였습니다.\n" + param.get("title") + "\n" +param.get("url") ;
			} else {
				message = "[" + param.get("user_name") + "] 님이 글을 수정하였습니다.\n" + param.get("title") + "\n" +param.get("url") ;
			}
			
			this.telegramSendMassage(chatId, botToken, message);
			this.updateStatment(param.get("board_seq"));
		}
	}
	
	public void telegramSendMassage(String chatId, String botToken, String message) {
		
		TelegramBot bot = TelegramBotAdapter.build(botToken);
		bot.execute(new SendMessage(chatId, message));
	}
	
	public void testTelegramSendMessage(String chatId, String botToken, String message) {
		String phoneNumber	= "01094044029";
		String firstName	= "DooHyun";
		
		String fileLocation = "C:/Users/momos/Pictures/Google Photos Backup/11340904/_DSC1930.JPG";
		File photo = new File(fileLocation);
		
		TelegramBot bot = TelegramBotAdapter.build(botToken);

		// text send
//		bot.execute(new SendMessage(chatId, message));
		
		// photo send
//		bot.execute(new SendPhoto(chatId, photo));
		
		// contect send
//		bot.execute(new SendContact(chatId, phoneNumber, firstName));
		
	}

	public void updateStatment(String boardSeq) {
		telegramSendMapper.updateStatment(boardSeq);
	}

	public List<Map<String, String>> selectMassageList() {
		return telegramSendMapper.selectMassageList();
	}
}
