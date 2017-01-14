package com.my.schedule.scheduler.crawling.webtoon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class ImageSaver extends Thread {
	private String saveImageName;
	private String imageName;
	private String saveDir;
	private String status;
	public ImageSaver(String imageName, String saveDir, String saveImageName) {
		this.saveDir = saveDir;
		this.saveImageName = saveImageName;
		this.imageName = imageName;
	}
	 
	public void run() {
		// 파일이 존재하는경우 return
		File saveFile = new File(saveDir + File.separator + saveImageName);
		if(saveFile.isFile()) {
			System.out.println(saveImageName + " : 존재함");
			return;
		}
		 
		try {
			URL voImageURL = new URL(imageName);
			//이미지에 해당하는 url을 통하여 커넥션을 진행
			HttpURLConnection connection = (HttpURLConnection) voImageURL.openConnection();
			connection.setRequestProperty("User-Agent", "User-Agent Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
			connection.setRequestProperty("Host", "imgcomic.naver.net");
			connection.setRequestProperty("Referer", "http://comic.naver.com/webtoon/detail.nhn?titleId=183559&no=200");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("DNT", "1");
			 
			//200_OK 응답에 대해서만 처리
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = connection.getInputStream();
				 
				File f = new File(saveDir);
				if(!f.isDirectory()) f.mkdirs();
				 
				//응답 코드를 C:\1.jpg에 저장
				FileOutputStream fos = new FileOutputStream(saveFile);
				BufferedInputStream bis = new BufferedInputStream(is);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				 
				//응답 코드를 1024바이트 단위로 저장
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = bis.read(buf, 0, 1024)) != -1) {
					bos.write(buf, 0, len);
					bos.flush();
				}
				bos.close();
				bis.close();
				fos.close();
				status = "Success";
			} else {
				status = "fail";
				System.out.println("connection failed. code is : " + connection.getResponseCode());
				System.out.println("failed connection url : " + connection.getURL());
			}	   
		} catch(Exception ex) {
			System.out.println("IMAGENAME : " + status);
			ex.printStackTrace();
		} finally {
		}
	}
}
