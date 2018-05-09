package org.ehais.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.util.FSO;
import org.ehais.util.FfmpegUtil;
import org.ehais.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MediaFFMpegTaskAnnotation {

	protected static Logger log = LoggerFactory.getLogger(MediaFFMpegTaskAnnotation.class); 

	//视频保存路径配置
	protected String video_path = ResourceUtil.getProValue("video.folder");
	
	
	@Scheduled(cron = "0 0/10 *  * * ? ")
	public void vtuShareRemind() {

		try {
			List<String> list = new ArrayList<String>();
			FSO.ReadfileList(list, video_path);
			for (String string : list) {
				if(string.indexOf("mp4.txt_")>0) {
					String time = string.substring(string.indexOf("mp4.txt_")+8);
					Long ltime = Long.valueOf(time);
					Long ntime = System.currentTimeMillis() / 1000;
					
					if( (ntime - ltime) > 10*60 ) {//大于10分钟的才进行执行
						String content = FSO.ReadFileName(string);
						log.info(content + "========FfmpegMp4Task");
						List<String> c = new ArrayList<String>();
						
						String[] cmd = content.split(" ");
						for (String string2 : cmd) {
							c.add(string2);
						}
						
						FfmpegUtil.FfmpegMp4Task(c);
						FSO.renameFile(video_path, 
								string.replace(video_path, ""), 
								string.substring(0, string.indexOf("mp4.txt_") + 7 ).replace(video_path, "").substring(1)
								);
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		String video_path = "D:/apache-tomcat-8.0.41/webapps/ROOT/";
		
	}
	
}
