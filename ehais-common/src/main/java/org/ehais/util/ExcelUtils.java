package org.ehais.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class ExcelUtils<E> {
	
    public static String NO_DEFINE = "no_define";//未定义的字段
    public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;
    /**
     * 导出Excel 97(.xls)格式 ，少量数据
     * @param title 标题行 
     * @param headMap 属性-列名
     * @param jsonArray 数据集
     * @param datePattern 日期格式，null则用默认日期格式
     * @param colWidth 列宽 默认 至少17个字节
     * @param out 输出流
     */
    public static void exportExcel(String title,Map<String, String> headMap,JSONArray jsonArray,String datePattern,int colWidth, OutputStream out) {
        if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createInformationProperties();
        workbook.getDocumentSummaryInformation().setCompany("广州易海司信息科技有限公司");
        SummaryInformation si = workbook.getSummaryInformation();
        si.setAuthor("ehais");  //填加xls文件作者信息
        si.setApplicationName("导出程序"); //填加xls文件创建程序信息
        si.setLastAuthor("最后保存者信息"); //填加xls文件最后保存者信息
        si.setComments("tyler is a programmer!"); //填加xls文件作者信息
        si.setTitle("POI导出Excel"); //填加xls文件标题信息
        si.setSubject("POI导出Excel");//填加文件主题信息
        si.setCreateDateTime(new Date());
         //表头样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
//        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        // 列头样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        headerStyle.setBorderBottom(BorderStyle.DOTTED);
//        headerStyle.setBorderLeft(BorderStyle.DOTTED);
//        headerStyle.setBorderRight(BorderStyle.DOTTED);
//        headerStyle.setBorderTop(BorderStyle.DOTTED);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // 单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(BorderStyle.DOTTED);
//        cellStyle.setBorderLeft(BorderStyle.DOTTED);
//        cellStyle.setBorderRight(BorderStyle.DOTTED);
//        cellStyle.setBorderTop(BorderStyle.DOTTED);
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont cellFont = workbook.createFont();
        cellFont.setBold(true);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        HSSFSheet sheet = workbook.createSheet();
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("ehais");
        //设置列宽
        int minBytes = colWidth<DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        String[] titleLabel = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter
                .hasNext();) {
            String fieldName = iter.next();
//            String fieldName = headMap.get(iter.next());

            properties[ii] = fieldName;
            headers[ii] = fieldName;
            titleLabel[ii] = headMap.get(fieldName);

            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(ii,arrColWidth[ii]*256);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        for (Object obj : jsonArray) {
            if(rowIndex == 65535 || rowIndex == 0){
                if ( rowIndex != 0 ) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示

                HSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

                HSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                for(int i=0;i<headers.length;i++)
                {
                    headerRow.createCell(i).setCellValue(titleLabel[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);

                }
                rowIndex = 2;//数据内容从 rowIndex=2开始
            }
            JSONObject jo = (JSONObject) JSONObject.fromObject(obj);
            HSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                HSSFCell newCell = dataRow.createCell(i);

                Object o =  jo.get(properties[i]);
                String cellValue = ""; 
                if(o==null) cellValue = "";
                else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                else cellValue = o.toString();

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     * @param title 标题行
     * @param headMap 属性-列头
     * @param jsonArray 数据集
     * @param datePattern 日期格式，传null值则默认 年月日
     * @param colWidth 列宽 默认 至少17个字节
     * @param out 输出流
     */
    public static void exportExcelX(String title,Map<String, String> headMap,JSONArray jsonArray,String datePattern,int colWidth, OutputStream out) {
        if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
//        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        XSSFWorkbook workbook = new XSSFWorkbook();
        
//        workbook.setCompressTempFiles(true);
         //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
//        titleFont.setBoldweight((short) 700);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        headerStyle.setBorderBottom(BorderStyle.DOTTED);
//        headerStyle.setBorderLeft(BorderStyle.DOTTED);
//        headerStyle.setBorderRight(BorderStyle.DOTTED);
//        headerStyle.setBorderTop(BorderStyle.DOTTED);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(BorderStyle.SLANTED_DASH_DOT);
//        cellStyle.setBorderLeft(BorderStyle.SLANTED_DASH_DOT);
//        cellStyle.setBorderRight(BorderStyle.SLANTED_DASH_DOT);
//        cellStyle.setBorderTop(BorderStyle.SLANTED_DASH_DOT);
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
//        SXSSFSheet sheet = workbook.createSheet();
        XSSFSheet sheet = workbook.createSheet();
        //设置列宽
        int minBytes = colWidth<DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (Iterator<String> iter = headMap.keySet().iterator(); iter
                .hasNext();) {
            String fieldName = iter.next();

            properties[ii] = fieldName;
            headers[ii] = headMap.get(fieldName);

            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(ii,arrColWidth[ii]*256);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        for (Object obj : jsonArray) {
            if(rowIndex == 65535 || rowIndex == 0){
                if ( rowIndex != 0 ) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示

//                SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                XSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

//                SXSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                XSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                for(int i=0;i<headers.length;i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);

                }
                rowIndex = 2;//数据内容从 rowIndex=2开始
            }
            JSONObject jo = (JSONObject) JSONObject.fromObject(obj);
//            SXSSFRow dataRow = sheet.createRow(rowIndex);
            XSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
//                SXSSFCell newCell = dataRow.createCell(i);
                XSSFCell newCell = dataRow.createCell(i);

                Object o =  jo.get(properties[i]);
                String cellValue = ""; 
                if(o==null) cellValue = "";
                else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                else if(o instanceof Float || o instanceof Double) 
                    cellValue= new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                else cellValue = o.toString();

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
        try {
            workbook.write(out);
            workbook.close();
//            workbook.dispose();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Web 导出excel
    public static void downloadExcelFile(String title,Map<String,String> headMap,JSONArray ja,HttpServletResponse response){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ExcelUtils.exportExcel(title,headMap,ja,null,0,os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xls").getBytes(), "iso-8859-1"));
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);

            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        int count = 100000;
        JSONArray ja = new JSONArray();
//        for(int i=0;i<100000;i++){
//            Student s = new Student();
//            s.setName("POI"+i);
//            s.setAge(i);
//            s.setBirthday(new Date());
//            s.setHeight(i);
//            s.setWeight(i);
//            s.setSex(i/2==0?false:true);
//            ja.add(s);
//        }
        Map<String,String> headMap = new LinkedHashMap<String,String>();
//        headMap.put("name","姓名");
//        headMap.put("age","年龄");
//        headMap.put("birthday","生日");
//        headMap.put("height","身高");
//        headMap.put("weight","体重");
//        headMap.put("sex","性别");

        String title = "测试";
        /*
        OutputStream outXls = new FileOutputStream("E://a.xls");
        System.out.println("正在导出xls....");
        Date d = new Date();
        ExcelUtil.exportExcel(title,headMap,ja,null,outXls);
        System.out.println("共"+count+"条数据,执行"+(new Date().getTime()-d.getTime())+"ms");
        outXls.close();*/
        //
        OutputStream outXlsx = new FileOutputStream("E://b.xlsx");
        System.out.println("正在导出xlsx....");
        Date d2 = new Date();
        ExcelUtils.exportExcelX(title,headMap,ja,null,0,outXlsx);
        System.out.println("共"+count+"条数据,执行"+(new Date().getTime()-d2.getTime())+"ms");
        outXlsx.close();

    }
    
    
//	private E e;
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private int etimes = 0;
// 
//    public ExcelUtils(E e) {
//        this.e = e;
//    }
// 
//    @SuppressWarnings("unchecked")
//    public E get() throws InstantiationException, IllegalAccessException {
//        return (E) e.getClass().newInstance();
//    }
//    
//    /**
//     * 将数据写入到Excel文件
//     * 
//     * @param filePath
//     *            文件路径
//     * @param sheetName
//     *            工作表名称
//     * @param title
//     *            工作表标题栏
//     * @param data
//     *            工作表数据
//     * @throws FileNotFoundException
//     *             文件不存在异常
//     * @throws IOException
//     *             IO异常
//     */
//    public static void writeToFile(String filePath, String[] sheetName, List<? extends Object[]> title, List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException {
//        // 创建并获取工作簿对象
//        Workbook wb = getWorkBook(sheetName, title, data);
//        // 写入到文件
//        FileOutputStream out = new FileOutputStream(filePath);
//        wb.write(out);
//        out.close();
//    }
// 
//    /**
//     * 创建工作簿对象<br>
//     * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br>
//     * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br>
//     * <b> 注意：<br>
//     * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br>
//     * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br>
//     * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 </b>
//     * 
//     * @param sheetName
//     *            工作表名称的数组
//     * @param title
//     *            每个工作表名称的数组集合
//     * @param data
//     *            每个工作表数据的集合的集合
//     * @return Workbook工作簿
//     * @throws FileNotFoundException
//     *             文件不存在异常
//     * @throws IOException
//     *             IO异常
//     */
//    public static Workbook getWorkBook(String[] sheetName, List<? extends Object[]> title, List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException {
// 
//        // 创建工作簿
//        Workbook wb = new SXSSFWorkbook();
//        // 创建一个工作表sheet
//        Sheet sheet = null;
//        // 申明行
//        Row row = null;
//        // 申明单元格
//        Cell cell = null;
//        // 单元格样式
//        CellStyle titleStyle = wb.createCellStyle();
//        CellStyle cellStyle = wb.createCellStyle();
//        // 字体样式
//        Font font = wb.createFont();
//        // 粗体
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        titleStyle.setFont(font);
//        // 水平居中
//        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        // 垂直居中
//        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
// 
//        // 水平居中
//        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        // 垂直居中
//        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
// 
//        cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
// 
//        // 标题数据
//        Object[] title_temp = null;
// 
//        // 行数据
//        Object[] rowData = null;
// 
//        // 工作表数据
//        List<? extends Object[]> sheetData = null;
// 
//        // 遍历sheet
//        for (int sheetNumber = 0; sheetNumber < sheetName.length; sheetNumber++) {
//            // 创建工作表
//            sheet = wb.createSheet();
//            // 设置默认列宽
//            sheet.setDefaultColumnWidth(18);
//            // 设置工作表名称
//            wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
//            // 设置标题
//            title_temp = title.get(sheetNumber);
//            row = sheet.createRow(0);
// 
//            // 写入标题
//            for (int i = 0; i < title_temp.length; i++) {
//                cell = row.createCell(i);
//                cell.setCellStyle(titleStyle);
//                cell.setCellValue(title_temp[i].toString());
//            }
// 
//            try {
//                sheetData = data.get(sheetNumber);
//            } catch (Exception e) {
//                continue;
//            }
//            // 写入行数据
//            for (int rowNumber = 0; rowNumber < sheetData.size(); rowNumber++) {
//                // 如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
//                row = sheet.createRow(title_temp == null ? rowNumber : (rowNumber + 1));
//                rowData = sheetData.get(rowNumber);
//                for (int columnNumber = 0; columnNumber < rowData.length; columnNumber++) {
//                    cell = row.createCell(columnNumber);
//                    cell.setCellStyle(cellStyle);
//                    cell.setCellValue(rowData[columnNumber] + "");
//                }
//            }
//        }
//        return wb;
//    }
// 
//    /**
//     * 将数据写入到EXCEL文档
//     * 
//     * @param list
//     *            数据集合
//     * @param edf
//     *            数据格式化，比如有些数字代表的状态，像是0:女，1：男，或者0：正常，1：锁定，变成可读的文字
//     *            该字段仅仅针对Boolean,Integer两种类型作处理
//     * @param filePath
//     *            文件路径
//     * @throws Exception
//     */
//    public static <T> void writeToFile(List<T> list, ExcelDataFormatter edf, String filePath) throws Exception {
//        // 创建并获取工作簿对象
//        Workbook wb = getWorkBook(list, edf);
//        // 写入到文件
//        FileOutputStream out = new FileOutputStream(filePath);
//        wb.write(out);
//        out.close();
//    }
// 
//    /**
//     * 获得Workbook对象
//     * 
//     * @param list
//     *            数据集合
//     * @return Workbook
//     * @throws Exception
//     */
//    public static <T> Workbook getWorkBook(List<T> list, ExcelDataFormatter edf) throws Exception {
//        // 创建工作簿
//        Workbook wb = new SXSSFWorkbook();
// 
//        if (list == null || list.size() == 0)
//            return wb;
// 
//        // 创建一个工作表sheet
//        Sheet sheet = wb.createSheet();
//        // 申明行
//        Row row = sheet.createRow(0);
//        // 申明单元格
//        Cell cell = null;
// 
//        CreationHelper createHelper = wb.getCreationHelper();
// 
//        Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(list.get(0).getClass());
// 
//        XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
//        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        // 设置前景色
//        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(159, 213, 183)));
//        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
// 
//        Font font = wb.createFont();
//        font.setColor(HSSFColor.BROWN.index);
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        // 设置字体
//        titleStyle.setFont(font);
// 
//        int columnIndex = 0;
//        Excel excel = null;
//        for (Field field : fields) {
//            field.setAccessible(true);
//            excel = field.getAnnotation(Excel.class);
//            if (excel == null || excel.skip() == true) {
//                continue;
//            }
//            // 列宽注意乘256
//            sheet.setColumnWidth(columnIndex, excel.width() * 256);
//            // 写入标题
//            cell = row.createCell(columnIndex);
//            cell.setCellStyle(titleStyle);
//            cell.setCellValue(excel.name());
// 
//            columnIndex++;
//        }
// 
//        int rowIndex = 1;
// 
//        CellStyle cs = wb.createCellStyle();
// 
//        for (T t : list) {
//            row = sheet.createRow(rowIndex);
//            columnIndex = 0;
//            Object o = null;
//            for (Field field : fields) {
// 
//                field.setAccessible(true);
// 
//                // 忽略标记skip的字段
//                excel = field.getAnnotation(Excel.class);
//                if (excel == null || excel.skip() == true) {
//                    continue;
//                }
//                // 数据
//                cell = row.createCell(columnIndex);
// 
//                o = field.get(t);
//                // 如果数据为空，跳过
//                if (o == null)
//                    continue;
// 
//                // 处理日期类型
//                if (o instanceof Date) {
//                    cs.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//                    cell.setCellStyle(cs);
//                    cell.setCellValue((Date) field.get(t));
//                } else if (o instanceof Double || o instanceof Float) {
//                    cell.setCellValue((Double) field.get(t));
//                } else if (o instanceof Boolean) {
//                    Boolean bool = (Boolean) field.get(t);
//                    if (edf == null) {
//                        cell.setCellValue(bool);
//                    } else {
//                        Map<String, String> map = edf.get(field.getName());
//                        if (map == null) {
//                            cell.setCellValue(bool);
//                        } else {
//                            cell.setCellValue(map.get(bool.toString().toLowerCase()));
//                        }
//                    }
// 
//                } else if (o instanceof Integer) {
// 
//                    Integer intValue = (Integer) field.get(t);
// 
//                    if (edf == null) {
//                        cell.setCellValue(intValue);
//                    } else {
//                        Map<String, String> map = edf.get(field.getName());
//                        if (map == null) {
//                            cell.setCellValue(intValue);
//                        } else {
//                            cell.setCellValue(map.get(intValue.toString()));
//                        }
//                    }
//                } else {
//                    cell.setCellValue(field.get(t).toString());
//                }
// 
//                columnIndex++;
//            }
// 
//            rowIndex++;
//        }
// 
//        return wb;
//    }
    
}
