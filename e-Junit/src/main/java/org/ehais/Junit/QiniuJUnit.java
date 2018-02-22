package org.ehais.Junit;

import java.net.URLEncoder;

import org.ehais.util.FSO;
import org.junit.Test;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class QiniuJUnit {

	@Test
	public void fileList() {
		
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		String accessKey = "j2ZhHmkIkB36HwrgfNDPcpI6-03G8pFxByQZCYbK";
		String secretKey = "NEbia94gRYGjVpN-2gZfkCoK1_4dHavAim5hmaaI";
		String bucket = "children-ehais";
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		//文件名前缀
		String prefix = "";
		//每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "";
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		while (fileListIterator.hasNext()) {
		    //处理获取的file list结果
		    FileInfo[] items = fileListIterator.next();
		    for (FileInfo item : items) {
		        System.out.println(item.key);
		        System.out.println(item.hash);
		        System.out.println(item.fsize);
		        System.out.println(item.mimeType);
		        System.out.println(item.putTime);
		        System.out.println(item.endUser);
		        
		        FSO.WriteTextFile("E:/qiniu.txt", "http://ook3av7gv.bkt.clouddn.com/"+item.key+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "hash:"+item.hash+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "fsize:"+item.fsize+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "mimeType:"+item.mimeType+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "putTime:"+item.putTime+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "endUser:"+item.endUser+"\r\n");
		        FSO.WriteTextFile("E:/qiniu.txt", "====================**********************================="+"\r\n\r\n");
		        
		    }
		}
		
	}
	
	
	@Test
	public void download() {
		try {
			
			String fileName = "o_1bsndnjhv1jgk5ft12cd1nr1apu7.jpg";
			String domainOfBucket = "http://devtools.qiniu.com";
			String encodedFileName = URLEncoder.encode(fileName, "utf-8");
			String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
			System.out.println(finalUrl);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
