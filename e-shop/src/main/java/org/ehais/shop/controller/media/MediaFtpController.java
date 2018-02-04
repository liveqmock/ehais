package org.ehais.shop.controller.media;

import java.io.FileInputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.util.EncryptUtils;
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
	protected String video_path = ResourceUtil.getProValue("video.path");
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
	protected String video_ftp_path = ResourceUtil.getProValue("video.ftp.path");
	// ffmpeg转码图片的地址
	private String images_path;
	Integer store_id = 1;

//	@Autowired
//	private EHaiArticleMapper eHaiArticleMapper;
//	@Autowired
//	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private ArticleCatService articleCatService;
	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@RequestMapping("/index.ftp")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		images_path = request.getRealPath("/eUploads/images");

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
					System.out.print("第" + (rowCount++) + "行  ");

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

					System.out.print(catName + "   ");
					System.out.print(parentName + "   ");
					System.out.print(title + "   ");
					System.out.print(path + "   ");
					System.out.print(mediaName + "   ");

					FfmpegThread ft = new FfmpegThread(catName, parentName, title, path, mediaName, images_path);
					ft.start();

					// 循环每一列
					// while (cellIterator.hasNext()) {
					// // System.out.print("第"+(columnCount++)+"列: ");
					//
					// // 得到单元格对象
					// Cell cell = cellIterator.next();
					//
					// // 检查数据类型
					// switch (cell.getCellType()) {
					// case Cell.CELL_TYPE_STRING:
					// System.out.print(cell.getStringCellValue() + " ");
					// break;
					// case Cell.CELL_TYPE_NUMERIC:
					// System.out.print(cell.getNumericCellValue() + " ");
					// break;
					//
					// }
					// } // end of cell iterator

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

	class FfmpegThread extends Thread {
		String catName;
		String parentName;
		String title;
		String path;
		String mediaName;
		String imagesPath;

		public FfmpegThread(String catName, String parentName, String title, String path, String mediaName,
				String imagesPath) {
			this.catName = catName;
			this.parentName = parentName;
			this.title = title;
			this.path = path;
			this.mediaName = mediaName;
			this.imagesPath = imagesPath;
		}

		public void run() {
			System.out.println("============转码开始==================");
			try {

				String ftpPath = "/home/ftp/" + path + "/" + mediaName;// 视频原路径
				
				String md5MediaName = EncryptUtils.md5(ftpPath);
				String videoPath = md5MediaName + ".mp4";

				FfmpegUtil.executeCodecs(video_ffmpeg_path, 
						ftpPath, 
						video_path +"/"+ md5MediaName + ".mp4",
						images_path + "/" + md5MediaName + ".png", 
						video_pic_size);

				// 找到分类ID
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
				// 更新

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
