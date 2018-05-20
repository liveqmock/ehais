package org.ehais.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ehais.annotation.ExcelVOAttribute;



public class ExcelUtils<E> {
	
    public static String NO_DEFINE = "no_define";//未定义的字段
    public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;
    
    
    private Class<E> clazz;  
    public ExcelUtils() {  
    } 
    
    public ExcelUtils(Class<E> clazz) {  
        this.clazz = clazz;  
    } 
    
    
    
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
    
    
//    第二版
    public List<E> importExcel(String sheetName, InputStream input) {  
        int maxCol = 0;  
        List<E> list = new ArrayList<E>();  
        try {  
            HSSFWorkbook workbook = new HSSFWorkbook(input);  
            HSSFSheet sheet = workbook.getSheet(sheetName);  
            if (!sheetName.trim().equals("")) {  
                sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.  
            }  
            if (sheet == null) {  
                sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.  
            }  
            int rows = sheet.getPhysicalNumberOfRows();  

            if (rows > 0) {// 有数据时才处理  
                // Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.  
                List<Field> allFields = getMappedFiled(clazz, null);  

                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.  
                for (Field field : allFields) {  
                    // 将有注解的field存放到map中.  
                    if (field.isAnnotationPresent(ExcelVOAttribute.class)) {  
                        ExcelVOAttribute attr = field  
                                .getAnnotation(ExcelVOAttribute.class);  
                        int col = getExcelCol(attr.column());// 获得列号  
                        maxCol = Math.max(col, maxCol);  
                        // System.out.println(col + "====" + field.getName());  
                        field.setAccessible(true);// 设置类的私有字段属性可访问.  
                        fieldsMap.put(col, field);  
                    }  
                }  
                for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.  
                    HSSFRow row = sheet.getRow(i);  
                    // int cellNum = row.getPhysicalNumberOfCells();  
                    // int cellNum = row.getLastCellNum();  
                    int cellNum = maxCol;  
                    E entity = null;  
                    for (int j = 0; j < cellNum; j++) {  
                        HSSFCell cell = row.getCell(j);  
                        if (cell == null) {  
                            continue;  
                        }  
                        int cellType = cell.getCellType();  
                        String c = "";  
                        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {  
                            c = String.valueOf(cell.getNumericCellValue());  
                        } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
                            c = String.valueOf(cell.getBooleanCellValue());  
                        } else {  
                            c = cell.getStringCellValue();  
                        }  
                        if (c == null || c.equals("")) {  
                            continue;  
                        }  
                        entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.  
                        // System.out.println(cells[j].getContents());  
                        Field field = fieldsMap.get(j);// 从map中得到对应列的field.  
                        if (field==null) {  
                            continue;  
                        }  
                        // 取得类型,并根据对象类型设置值.  
                        Class<?> fieldType = field.getType();  
                        if (String.class == fieldType) {  
                            field.set(entity, String.valueOf(c));  
                        } else if ((Integer.TYPE == fieldType)  
                                || (Integer.class == fieldType)) {  
                            field.set(entity, Integer.parseInt(c));  
                        } else if ((Long.TYPE == fieldType)  
                                || (Long.class == fieldType)) {  
                            field.set(entity, Long.valueOf(c));  
                        } else if ((Float.TYPE == fieldType)  
                                || (Float.class == fieldType)) {  
                            field.set(entity, Float.valueOf(c));  
                        } else if ((Short.TYPE == fieldType)  
                                || (Short.class == fieldType)) {  
                            field.set(entity, Short.valueOf(c));  
                        } else if ((Double.TYPE == fieldType)  
                                || (Double.class == fieldType)) {  
                            field.set(entity, Double.valueOf(c));  
                        } else if (Character.TYPE == fieldType) {  
                            if ((c != null) && (c.length() > 0)) {  
                                field.set(entity, Character  
                                        .valueOf(c.charAt(0)));  
                            }  
                        }  

                    }  
                    if (entity != null) {  
                        list.add(entity);  
                    }  
                }  
            }  

        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        }  
        return list;  
    }  
    
    
    
    /** 
     * 对list数据源将其里面的数据导入到excel表单 
     *  
     * @param sheetName 
     *            工作表的名称 
     * @param output 
     *            java输出流 
     */  
    public boolean exportExcel(List<E> lists[], String sheetNames[],  
            OutputStream output) {  
        if (lists.length != sheetNames.length) {  
            System.out.println("数组长度不一致");  
            return false;  
        }  

        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象  

        for (int ii = 0; ii < lists.length; ii++) {  
            List<E> list = lists[ii];  
            String sheetName = sheetNames[ii];  

            List<Field> fields = getMappedFiled(clazz, null);  

            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象  

            workbook.setSheetName(ii, sheetName);  

            HSSFRow row;  
            HSSFCell cell;// 产生单元格  
            HSSFCellStyle style = workbook.createCellStyle();  
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
            style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);  
            row = sheet.createRow(0);// 产生一行  
            // 写入各个字段的列头名称  
            for (int i = 0; i < fields.size(); i++) {  
                Field field = fields.get(i);  
                ExcelVOAttribute attr = field  
                        .getAnnotation(ExcelVOAttribute.class);  
                int col = getExcelCol(attr.column());// 获得列号  
                cell = row.createCell(col);// 创建列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型  
                cell.setCellValue(attr.name());// 写入列名  

                // 如果设置了提示信息则鼠标放上去提示.  
                if (!attr.prompt().trim().equals("")) {  
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.  
                }  
                // 如果设置了combo属性则本列只能选择不能输入  
                if (attr.combo().length > 0) {  
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.  
                }  
                cell.setCellStyle(style);  
            }  

            int startNo = 0;  
            int endNo = list.size();  
            // 写入各条记录,每条记录对应excel表中的一行  
            for (int i = startNo; i < endNo; i++) {  
                row = sheet.createRow(i + 1 - startNo);  
                E vo = (E) list.get(i); // 得到导出对象.  
                for (int j = 0; j < fields.size(); j++) {  
                    Field field = fields.get(j);// 获得field.  
                    field.setAccessible(true);// 设置实体类私有属性可访问  
                    ExcelVOAttribute attr = field  
                            .getAnnotation(ExcelVOAttribute.class);  
                    try {  
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.  
                        if (attr.isExport()) {  
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell  
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                            cell.setCellValue(field.get(vo) == null ? ""  
                                    : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.  
                        }  
                    } catch (IllegalArgumentException e) {  
                        e.printStackTrace();  
                    } catch (IllegalAccessException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  

        try {  
            output.flush();  
            workbook.write(output);  
            output.close();  
            return true;  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("Output is closed ");  
            return false;  
        }  

    }  

    /** 
     * 对list数据源将其里面的数据导入到excel表单 
     *  
     * @param sheetName 
     *            工作表的名称 
     * @param sheetSize 
     *            每个sheet中数据的行数,此数值必须小于65536 
     * @param output 
     *            java输出流 
     * @throws IOException 
     */  
    @SuppressWarnings("unchecked")
    public boolean exportExcel(List<E> list, String sheetName,  
    		HttpServletResponse response) throws IOException {
    	
    	OutputStream output = response.getOutputStream();
    	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((sheetName+".xls").getBytes(), "iso-8859-1"));
          
        //此处 对类型进行转换
        List<E> ilist = new ArrayList<>();
        for (E t : list) {
            ilist.add(t);
        }
        List<E>[] lists = new ArrayList[1];  
        lists[0] = ilist;  

        String[] sheetNames = new String[1];  
        sheetNames[0] = sheetName;  

        return exportExcel(lists, sheetNames, output);  
    }  

    /** 
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3 
     *  
     * @param col 
     */  
    public static int getExcelCol(String col) {  
        col = col.toUpperCase();  
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。  
        int count = -1;  
        char[] cs = col.toCharArray();  
        for (int i = 0; i < cs.length; i++) {  
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);  
        }  
        return count;  
    }  

    /** 
     * 设置单元格上提示 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param promptTitle 
     *            标题 
     * @param promptContent 
     *            内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */  
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,  
            String promptContent, int firstRow, int endRow, int firstCol,  
            int endCol) {  
        // 构造constraint对象  
        DVConstraint constraint = DVConstraint  
                .createCustomFormulaConstraint("DD1");  
        // 四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);  
        // 数据有效性对象  
        HSSFDataValidation data_validation_view = new HSSFDataValidation( regions, constraint);  
        data_validation_view.createPromptBox(promptTitle, promptContent);  
        sheet.addValidationData(data_validation_view);  
        return sheet;  
    }  

    /** 
     * 设置某些列的值只能输入预制的数据,显示下拉框. 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param textlist 
     *            下拉框显示的内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */  
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,  
            String[] textlist, int firstRow, int endRow, int firstCol,  
            int endCol) {  
        // 加载下拉列表内容  
        DVConstraint constraint = DVConstraint  
                .createExplicitListConstraint(textlist);  
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,  
                endRow, firstCol, endCol);  
        // 数据有效性对象  
        HSSFDataValidation data_validation_list = new HSSFDataValidation(  
                regions, constraint);  
        sheet.addValidationData(data_validation_list);  
        return sheet;  
    }  

    /** 
     * 得到实体类所有通过注解映射了数据表的字段 
     *  
     * @param map 
     * @return 
     */  
    @SuppressWarnings("rawtypes")
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {  
        if (fields == null) {  
            fields = new ArrayList<Field>();  
        }  

        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段  
        // 得到所有field并存放到一个list中.  
        for (Field field : allFields) {  
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {  
                fields.add(field);  
            }  
        }  
        if (clazz.getSuperclass() != null  
                && !clazz.getSuperclass().equals(Object.class)) {  
            getMappedFiled(clazz.getSuperclass(), fields);  
        }  

        return fields;  
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
    
    
    
}
