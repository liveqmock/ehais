package org.ehais.shop.controller.media;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.thread.FfmpegThread;
import org.ehais.util.ECommon;
import org.ehais.util.EncryptUtils;
import org.ehais.util.FSO;
import org.ehais.util.FfmpegUtil;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MediaFtpController extends CommonController {

	// 视频保存路径配置
	protected String video_folder = ResourceUtil.getProValue("video.folder");
	// 视频是否中转
	protected boolean video_transfer_bool = Boolean.parseBoolean(ResourceUtil.getProValue("video.transfer.bool"));
	// 视频中转地址
	protected String video_transfer_posturl = ResourceUtil.getProValue("video.transfer.posturl");
	// 视频访问地址
	protected String video_transfer_website = ResourceUtil.getProValue("video.transfer.website");
	// 视频上传格式
	protected String video_postfix = ResourceUtil.getProValue("video.postfix");
	// 视频截图尺寸
	protected String video_pic_size = ResourceUtil.getProValue("video.pic.size");
	// ffmpeg的路径
	protected String video_ffmpeg_path = ResourceUtil.getProValue("video.ffmpeg.path");
	// ftp的地址
	protected String video_ftp_path = ResourceUtil.getProValue("video.ftp.folder");
	//上传相对的图片
	protected String upload_images = ResourceUtil.getProValue("upload.images");
	// ffmpeg转码图片的地址
	private String images_path;
	Integer store_id = 1000;

	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
//	@Autowired
//	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private ArticleCatService articleCatService;
	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@RequestMapping("/index.ftp")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		images_path = request.getRealPath(upload_images);

		try {
			// 读取excel
			String fileName = "/home/tyler/workspace/VodData.xls";
			FileInputStream fis = new FileInputStream(fileName);
			Workbook workbook = null;
			// 判断excel的两种格式xls,xlsx
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}

			// 得到sheet的总数
			int numberOfSheets = workbook.getNumberOfSheets();

			System.out.println("一共" + numberOfSheets + "个sheet");

			// 循环每一个sheet
			for (int i = 0; i < numberOfSheets; i++) {

				// 得到第i个sheet
				Sheet sheet = workbook.getSheetAt(i);
				System.out.println(sheet.getSheetName() + "  sheet");

				// 得到行的迭代器
				Iterator<Row> rowIterator = sheet.iterator();

				int rowCount = 0;
				// 循环每一行
				while (rowIterator.hasNext()) {
//					System.out.print("第" + (rowCount++) + "行  ");

					// 得到一行对象
					Row row = rowIterator.next();

					// 得到列对象
					Iterator<Cell> cellIterator = row.cellIterator();

					int columnCount = 0;

					String catName = row.getCell(0).getStringCellValue();
					String parentName = row.getCell(1).getStringCellValue();
					String title = row.getCell(5).getStringCellValue();
					String path = row.getCell(8).getStringCellValue();
					String mediaName = row.getCell(9).getStringCellValue();

//					System.out.print(catName + "   ");
//					System.out.print(parentName + "   ");
//					System.out.print(title + "   ");
//					System.out.print(path + "   ");
//					System.out.print(mediaName + "   ");

					ffmpegMedia(catName, parentName, title, path, mediaName, images_path);
					

					System.out.println();

				} // end of rows iterator

			} // end of sheets for loop

			System.out.println("\nread excel successfully...");

			// close file input stream
			fis.close();
			// 解释ffmpeg的视频路径与图片路径

			// 将信息保存在数据库

			//
		} catch (Exception e) {
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		return "";
	}
	
	
	private void ffmpegMedia(String catName, String parentName, String title, String path, String mediaName,
				String imagesPath) throws Exception {
		String ftpPath = "/home/ftp/" + path + "/" + mediaName;// 视频原路径
		
		String md5MediaName = EncryptUtils.md5(ftpPath);
		String videoPath = md5MediaName + ".mp4";

		FfmpegUtil.executeCodecs(video_ffmpeg_path, 
				ftpPath, 
				video_folder +"/"+ md5MediaName + ".mp4",
				images_path + "/" + md5MediaName + ".png", 
				video_pic_size);

		// 找到分类ID
/*
 * 		//暂时屏一屏
 * 
 		EHaiArticleCat cate = new EHaiArticleCat();
		cate.setCatName(catName);
		cate = articleCatService.articleCatSave(cate, null, 1);
		// 找到标题的ID
		EHaiArticle article = new EHaiArticle();
		article.setTitle(title);
		article.setStoreId(store_id);
		article.setArticleImages("/eUploads/images/"+md5MediaName+".png");
		article.setVideoUrl(video_path + md5MediaName + ".mp4");
		article.setLink("");
		article.setContent("");
		articleService.articleSave( cate, article);
		
		*/
	}


	
	@ResponseBody
	@RequestMapping("/index.media")
	public String index_ftp(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			images_path = request.getRealPath(upload_images);
			String ftpPath = "/home/ftp/";
//			String ftpPath = "F:\\广技师视频";
			List<String> list = new ArrayList<String>();
			FSO.ReadfileList(list, ftpPath);
			for (String string : list) {
				
				try {
					
					
					string = new String(string.getBytes("ISO-8859-1"),"gb2312");
					System.out.println(string);
					string = string.replace("\\", "/");
					String title = string.substring(string.lastIndexOf("/")+1, string.lastIndexOf("."));
					title = new String(title.getBytes("ISO-8859-1"),"gbk");//ISO-8859-1
					System.out.println("title:"+title);
					String suffix = string.substring(string.lastIndexOf(".")+1);//后缀名
					
					if(suffix.equals("flv") || string.equals("avi") || string.equals("mp4") ) {
						
						
						FfmpegThread thread = new FfmpegThread(string,video_folder,images_path);
						thread.run();
						String filePath = thread.getFilePath();
						String picPath = upload_images + thread.getPicPath();
						
						System.out.println(filePath+"||"+picPath);
						
						EHaiArticle article = new EHaiArticle();
						article.setTitle(title);
						article.setCatId(-1);
						article.setModule(EArticleModuleEnum.MEDIA);
						article.setContent("");
						article.setArticleDate(new Date());
						article.setOpenType(true);
						article.setCreateDate(new Date());
						article.setStoreId(store_id);
						article.setArticleThumb(picPath);
						article.setVideoUrl(filePath);
						article.setAuthor("机器");
						article.setAuthorEmail("");
						article.setKeywords("");
						article.setFileUrl("");
						article.setOpenType(true);
						article.setLink("");
						article.setIsOpen(true);
						
						EHaiArticleExample example = new EHaiArticleExample();
						example.createCriteria().andTitleEqualTo(title).andStoreIdEqualTo(store_id);
						
						long c = eHaiArticleMapper.countByExample(example);
						if(c==0) {
							eHaiArticleMapper.insert(article);
						}
						
						
					}
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				

				
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String upFilePath = "F:\\广技师视频\\125青协志愿日新闻稿.mp4";
//		String pre = upFilePath.substring(upFilePath.indexOf("."));
//		System.out.println(pre);
		
		
		
		upFilePath = upFilePath.replace("\\", "/");
		System.out.println(upFilePath);
		System.out.println(upFilePath.lastIndexOf("/")+1);
		System.out.println(upFilePath.lastIndexOf("."));
		
		
		String title = upFilePath.substring(upFilePath.lastIndexOf("/")+1, upFilePath.lastIndexOf("."));
		System.out.println(title);
	}

	
	
}
