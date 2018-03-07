package org.ehais.util;

import java.util.ArrayList;
import java.util.List;

import org.ehais.thread.PrintStream;

public class FfmpegUtil {

	
	/**
     * 视频转码
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
        
        
        List<String> command = new ArrayList<String>();  
        command.add(ffmpegPath);  
        command.add("-i");  
        command.add(upFilePath);  
        command.add("-ab");  
        command.add("56");  
        command.add("-ar");  
        command.add("22050");  
        command.add("-qscale");  
        command.add("8");  
        command.add("-r");  
        command.add("25");  
        command.add("-s");  
        command.add(mediaPicSize);  
        command.add(codcFilePath);  
        
        for (String string : command) {
			System.out.print(string+" ");
		}
        
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
        	if(isVideoChange) {
        		if(upFilePath.indexOf("mp4") < 0) {
//            		Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            		
            		Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            		                         
            		new PrintStream(videoProcess.getInputStream()).start();
            		            
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
    
    
}
