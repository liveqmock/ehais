package org.ehais.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
//import com.google.gson.GsonBuilder;


public class ExcelUtils<E> {
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
