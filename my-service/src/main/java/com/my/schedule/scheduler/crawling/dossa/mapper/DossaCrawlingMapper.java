package com.my.schedule.scheduler.crawling.dossa.mapper;

import java.util.List;
import java.util.Map;

/**
 * Mybatis Mapper Template - CrawlingMapper (MyBatis 3.X)
 * 
 * @version 1.0 (2016.06.13) 
 * @author  dhryu
 */
public interface DossaCrawlingMapper {

	public List getBoardInfo(Map prm);

	public void insertBoardInfo(Map<String, String> prm);

	public List selectMassageList();

	public void updateStatment(Object object);

	public void updateBoardInfo(Map<String, String> resultMap);

	

}
