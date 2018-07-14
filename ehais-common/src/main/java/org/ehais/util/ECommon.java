package org.ehais.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

public class ECommon {

	/**
	 * 判断bigDecimal数据是否为空，如果传入参数是null，则返回"0"
	 * 
	 * @param bd
	 *            需要判断的bigDecimal数据
	 * @return 返回参数对应的bigDecimal数据
	 */
	public static BigDecimal checkBigDecimal(BigDecimal bd) {
		if (bd == null) {
			bd = new BigDecimal(0);
		}
		return bd;
	}

	/**
	 * 判断bigDecimal数据是否为空，如果传入参数是null，则返回"0"
	 * 
	 * @param bd
	 *            需要判断的bigDecimal数据
	 * @return 返回参数对应的bigDecimal数据
	 */
	public static Double checkDouble(Double bd) {
		if (bd == null) {
			bd = new Double(0);
		}
		return bd;
	}

	/**
	 * 取给定的字符串长度和传入长度比较，相等返回TRUE，否则返回FALSE.
	 * 
	 * @param str1
	 *            待比较字符串
	 * @param length
	 *            串长
	 * @return
	 */
	public static Boolean checkStringLength(String str1, int length) {
		if (null == str1 || length < 0) {
			return Boolean.FALSE;
		}
		return Boolean.valueOf(str1.length() == length);
	}

	/**
	 * 将给定的字符串长度转换成4位长度字符串，不足4位前边补零。
	 * 
	 * @param strLen
	 * @return
	 */
	public static String getLength(int strLen) {
		String str = String.valueOf(strLen);
		for (int i = str.length(); i < 4; i++) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 判断bigDecimal数据是否为空，如果传入参数是null，则返回"0"
	 * 
	 * @param bd
	 *            需要判断的bigDecimal数据
	 * @return 返回参数对应的double数据
	 */
	public static double convertBDtoD(BigDecimal bd) {
		if (bd == null) {
			bd = new BigDecimal(0);
		}
		return bd.doubleValue();
	}

	/**
	 * 将给定的字符串数组转换成特定格式的字符串
	 * 
	 * @param info
	 *            字符串数组
	 * @return
	 */
	public static String getSocketOutputInfo(String[] info) {
		StringBuffer sb = new StringBuffer(1024);
		// 将数组中的数据存入字符串缓冲区
		for (int i = 0; i < info.length; i++) {
			sb.append("|");
			sb.append(info[i]);
		}

		// 计算纯字符串数据长度
		// by zhangwd 20070105
		// int length = sb.toString().trim().length();
		int length = sb.toString().length();
		// 将长度补到生成的串前
		sb.insert(0, getLength(length + 4));

		return sb.toString();
	}

	/**
	 * 将字符串以某一个特定字符串分离开
	 * 
	 * @param tmpMsg
	 *            待分离字符串
	 * @param separater
	 *            分隔符
	 * @return
	 */
	public static String[] getStringArrayBySomeString(String tmpMsg, String separater) {
		List msgFieldList = new ArrayList();
		while (tmpMsg != null && !tmpMsg.equals("")) {
			int firstD = tmpMsg.indexOf(separater);
			if (firstD == -1) {
				msgFieldList.add(tmpMsg);
				tmpMsg = null;
			} else if (firstD == 0) {
				msgFieldList.add("");
				tmpMsg = tmpMsg.substring(firstD + 1);
			} else {
				msgFieldList.add(tmpMsg.substring(0, firstD));
				tmpMsg = tmpMsg.substring(firstD + 1);
			}
		}

		String[] fields = new String[msgFieldList.size()];
		int ii = 0;
		for (Iterator it = msgFieldList.iterator(); it.hasNext();) {
			fields[ii++] = (String) it.next();
		}

		return fields;
	}

	/**
	 * 固定字符串填充
	 * 
	 * @param str
	 *            源串
	 * @param length
	 *            固定长度
	 * @param c
	 *            填充位置
	 * @param fillChar
	 *            填充字符
	 * @return
	 */
	public static String getCertainLengthString(String str, int length, Character c, Character fillChar) {
		char temStr[] = new char[length];
		for (int i = 0; i < temStr.length; i++)
			temStr[i] = fillChar.charValue();

		if (str == null || str.trim().length() == 0)
			return String.valueOf(temStr);
		if (str.getBytes().length > length)
			return str.substring(0, length);
		char inputStr[] = new char[length - str.getBytes().length];
		for (int j = 0; j < length - str.getBytes().length; j++)
			inputStr[j] = fillChar.charValue();

		if (c.charValue() == 'L')
			return str.concat(String.valueOf(inputStr));
		if (c.charValue() == 'R')
			return String.valueOf(inputStr).concat(str);
		else
			return null;
	}

	/**
	 * 获取当前系统时间，精确到纳秒
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Date dnow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSSSSS");
		return formatter.format(dnow);
	}

	public static String getStrFromByteArray(byte[] src, int srcPos, int length) {
		byte[] tempArray = new byte[length];
		System.arraycopy(src, srcPos, tempArray, 0, length);
		return new String(tempArray);
	}

	/**
	 * 将金额转换成不带小数点的字符串，如30.56经过转换后变为3056
	 * 
	 * @param bd
	 * @return
	 */
	public static String convertMoneytoString(Double bd) {
		DecimalFormat df = new DecimalFormat("######.00");
		String result = String.valueOf(df.format(100D * checkDouble(bd).doubleValue()));
		if (result.indexOf(".") != -1)
			return result.substring(0, result.indexOf("."));
		else
			return result;
	}

	/**
	 * 将金额转换成小数点两位小数的字符串，如30.5经过转换后变为30.50 0.1 经过转换后变为 0.10
	 * 
	 * @param bd
	 * @return
	 */
	public static String convertMoneytoString2(Double bd) {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = String.valueOf(df.format(checkDouble(bd).doubleValue()));
		return result;
	}

	/**
	 * 判断字符串是否数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 日期正则表达式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate(String dateStr) {
		Pattern p = Pattern.compile(
				"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = p.matcher(dateStr);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 产生len位的随机数
	 * 
	 * @param len
	 * @return
	 */
	public static String nonceStr(int len) {
		return RandomStringUtils.random(len, "abcdefthijklmnopqrstuvwxyz1234567890");
	}

	public static String nonceStrUpper(int len) {
		return RandomStringUtils.random(len, "abcdefthijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	public static String nonceInt(int len) {
		return RandomStringUtils.random(len, "1234567890");
	}

	/**
	 * 获取两个数之间的随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRand(int min, int max) {
		Random random = new Random();
		int i = random.nextInt(max + 1) % (max + 1 - min + 1) + min;
		return i;
	}

	// 判断一个字符是否是中文
	public static boolean isChinese(char c) {
		return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
	}

	// 判断一个字符串是否含有中文
	public static boolean isHasChinese(String str) {
		if (str == null)
			return false;
		for (char c : str.toCharArray()) {
			if (isChinese(c))
				return true;// 有一个中文字符就返回
		}
		return false;
	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @param str
	 *            待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	// 金额验证
	public static boolean isMoney(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Integer formatNumber(Integer num) {
		if(num == null) return 0;
		return num;
	}
	public static Integer formatNumber(Integer num,Integer def) {
		if(def == null) def = 0;
		if(num == null) return def;
		return num;
	}

	public static void main(String[] args) {

		String str = "F:\\cc\\aa.mp4";
		if (ECommon.isContainChinese(str)) {
			System.out.println("存在中文");
		} else {
			System.out.println("无中文");
		}

		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
		// System.out.println(ECommon.getRand(0, 4));
	}

}
