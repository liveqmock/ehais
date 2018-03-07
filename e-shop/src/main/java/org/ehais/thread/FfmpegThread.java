package org.ehais.thread;

import org.ehais.util.ECommon;
import org.ehais.util.EncryptUtils;
import org.ehais.util.FSO;
import org.ehais.util.FfmpegUtil;
import org.ehais.util.ResourceUtil;

public class FfmpegThread extends Thread{
	String upFilePath;//视频的源路径
	String codcFileFolder;//视频转码存放文件夹
	String mediaPicFolder;//图片提出存放文件夹
	String watchPicPath;//水印地址，如果为空，则不加水印
	String filePath;//视频文件路径
	String picPath;//图片文件路径
	
	// ffmpeg的路径
	protected String video_ffmpeg_path = ResourceUtil.getProValue("video.ffmpeg.path");
	//文件临时存放路径
	protected String video_tmp_path = ResourceUtil.getProValue("video.tmp.folder");
	// 视频截图尺寸
	protected String video_pic_size = ResourceUtil.getProValue("video.pic.size");
		
	public FfmpegThread(String upFilePath, String codcFileFolder, String mediaPicFolder) {
		this.upFilePath = upFilePath;
		this.codcFileFolder = codcFileFolder;
		this.mediaPicFolder = mediaPicFolder;
	}

	public void run() {
		System.out.println("============转码开始==================");
		try {

			String md5MediaName = EncryptUtils.md5(upFilePath);
			//获取后缀名
			String pre = upFilePath.substring(upFilePath.indexOf("."));
			//如果路径或文件名是中文的，要转放到一个非中文的路径
			if(ECommon.isContainChinese(upFilePath)) {				
				//复制文件到另一文件夹
				if(!FSO.TextFileExists(video_tmp_path+md5MediaName+pre))
				FSO.copyFile(upFilePath, video_tmp_path+md5MediaName+pre);
				upFilePath = video_tmp_path+md5MediaName+pre;
			}
			
			filePath = md5MediaName + ".mp4";
			picPath = md5MediaName+".png";
			
			FfmpegUtil.executeCodecs(video_ffmpeg_path, 
					upFilePath, 
					codcFileFolder+filePath,
					mediaPicFolder+md5MediaName+".png", 
					video_pic_size,false,true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public String getPicPath() {
		return picPath;
	}
	
	
	
	
	
	
	
}
