package org.ehais.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.ehais.thread.PrintStream;

public class FfmpegUtil {

	/**
     * 视频转码v
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @param mediaPicSize    截图尺寸
     * @param isVideoChange 是否转视频
     * @param isPicChange 是否换图片
     * @return
     * @throws Exception
     */
    public static boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
            String mediaPicPath,String mediaPicSize,boolean isVideoChange,boolean isPicChange) throws Exception {
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        
    	int type = checkContentType(upFilePath);

    	List<String> command = new ArrayList<String>();  
        
        if (type == 1) {  
        	command.add(ffmpegPath);  
        	command.add("-i");  
        	command.add(upFilePath);  
        	command.add("-y");
        	command.add("-c:v");  
        	command.add("libx264");  
        	command.add("-x264opts");  
        	command.add("force-cfr=1");  
        	command.add("-c:a");  
        	command.add("mp2");  
        	command.add("-b:a");  
        	command.add("256k");  
        	command.add("-vsync");  
        	command.add("cfr");  
        	command.add("-f");  
        	command.add("mpegts");  
        	command.add(codcFilePath);  
        } else if(type==0){  
        	command.add(ffmpegPath);  
        	command.add("-i");  
        	command.add(upFilePath);  
        	command.add("-y");
        	command.add("-c:v");  
        	command.add("libx264");  
        	command.add("-x264opts");  
        	command.add("force-cfr=1");  
        	command.add("-vsync");  
        	command.add("cfr");  
        	command.add("-vf");  
        	command.add("idet,yadif=deint=interlaced");  
        	command.add("-filter_complex");  
        	command.add("aresample=async=1000");  
        	command.add("-c:a");  
        	command.add("libmp3lame");  
        	command.add("-b:a");  
        	command.add("192k");  
        	command.add("-pix_fmt");  
        	command.add("yuv420p");  
        	command.add("-f");  
        	command.add("mpegts");  
        	command.add(codcFilePath);  
        } else if(type==2){
        	command.add(ffmpegPath);  
        	command.add("-i");  
        	command.add(upFilePath); 
        	command.add("-y");
        	command.add("-c:v");  
        	command.add("libx264");  
        	command.add("-strict");  
        	command.add("-2");
        	command.add(codcFilePath); 
    	}else{  
    		 return false;
    	} 
    
        
//        command.add(ffmpegPath);  
//        command.add("-i");  
//        command.add(upFilePath);  
//        command.add("-y");
//        command.add("-ab");  
//        command.add("56");  
//        command.add("-ar");  
//        command.add("22050");  
//        command.add("-qscale");  
//        command.add("8");  
//        command.add("-r");
//        command.add("25");  
//        command.add("-s");  
//        command.add(mediaPicSize);  
//        command.add(codcFilePath);  
        
        
        
//        commend.add("E:/ffmpeg.exe");  
//        commend.add("-i");  
////      commend.add("E:\\server\\red5-0.8.0-java5\\webapps\\webcam\\streams\\"+filename+".flv");  
//        commend.add("E:/test.flv");  
//        commend.add("-s");  
//        commend.add("320x240");  
//        commend.add("-r");  
//        commend.add("30000/1001");  
//        commend.add("-b");  
//        commend.add("200k");  
//        commend.add("-vcodec");  
//        commend.add("libx264");  
//        commend.add("-acodec");  
//        commend.add("libvo_aacenc");  
//        commend.add("-ac");  
//        commend.add("2");  
//        commend.add("-ar");  
//        commend.add("48000");  
//        commend.add("-ab");  
//        commend.add("192k");  
//        commend.add("-y");  
////      commend.add("E:\\server\\apache-tomcat-6.0.37\\webapps\\czwx-web\\resources\\download\\"+filename+".mp4");  
//        commend.add("E:/test.mp4"); 
        
//        if(upFilePath.indexOf(".mp4") < 0) {
        	StringBuilder sb = new StringBuilder();
            for (String string : command) {
    			System.out.print(string+" ");
    			sb.append(string + " " );
    		}
            File ffmpeg_cmd_file = new File(codcFilePath+".txt");
            if(ffmpeg_cmd_file.exists()) {//存在的话，就删除
            	ffmpeg_cmd_file.delete();
            }
            FSO.WriteTextFile(codcFilePath +".txt"+"_"+ System.currentTimeMillis()/1000, sb.toString());
            
//        }
        
        
        System.out.println(" & ");

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("17"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(mediaPicSize); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        for (String string : cutpic) {
			System.out.print(string+" ");
		}
        
        System.out.println(" & ");
        
        
        boolean mark = true;
        
        try {
        	
        	
//        	是否换图片
        	if(isPicChange) {
        		ProcessBuilder builderImg = new ProcessBuilder();
        		builderImg.command(cutpic);
            	builderImg.redirectErrorStream(true);
                //如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
                //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            	builderImg.start();
        	}
        	
        	
        	//是否转视频
        	if(isVideoChange && !FSO.TextFileExists(codcFilePath)) {
        		if(upFilePath.indexOf(".mp4") < 0) {
//            		Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            		
            		Process videoProcess = new ProcessBuilder(command)
            				.redirectErrorStream(true).start();
            		
            		InputStream error = videoProcess.getErrorStream();  
                    InputStream is = videoProcess.getInputStream(); 
                    
            		new PrintStream(error).start();
            		new PrintStream(is).start();
            		            
            		videoProcess.waitFor();
            		
            		
            	}else {
            		//直接接mp4文件复制到文件夹
            		FSO.copyFile(upFilePath, codcFilePath);
            	}
        	}
        	
            
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }
    
    
    public static void FfmpegMp4Task(List<String> cmd) throws Exception {
    	Process videoProcess = new ProcessBuilder(cmd).redirectErrorStream(true).start();
		
		InputStream error = videoProcess.getErrorStream();  
        InputStream is = videoProcess.getInputStream(); 
        
		new PrintStream(error).start();
		new PrintStream(is).start();
		            
		videoProcess.waitFor();
    }
    
    
    
    private static int checkContentType(String inputPath) {  
    	String type =inputPath.substring(inputPath.lastIndexOf(".") + 1,inputPath.length()).toLowerCase();  
    	//ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）  
    	if (type.equals("avi")) {  
    		return 1;  
    	} else if (type.equals("mpg")){  
    		return 1;  
    	} else if (type.equals("wmv")){  
    		return 1;  
    	} else if (type.equals("3gp")){  
    		return 1;  
    	} else if (type.equals("mov")){  
    		return 1;  
    	} else if (type.equals("mp4")){  
    		return 1;  
    	} else if(type.equals("mkv")){  
    		return 1;  
    	}else if (type.equals("asf")){  
    		return 0;  
    	} else if (type.equals("flv")){  
    		return 2;  
    	}else if (type.equals("rm")){  
    		return 0;  
    	} else if (type.equals("rmvb")){  
    		return 1;  
    	}  
    		return 9;  
    }
    
    
}
