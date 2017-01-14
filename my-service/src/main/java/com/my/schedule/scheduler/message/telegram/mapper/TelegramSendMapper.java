package com.my.schedule.scheduler.message.telegram.mapper;

import java.util.List;

/**
 * Mybatis Mapper Template - TelegramSendMapper (MyBatis 3.X)
 * 
 * @version 1.0 (2016.06.13) 
 * @author  Development by Ryu
 */
public interface TelegramSendMapper {

	public List selectMassageList();

	public void updateStatment(Object object);

}
