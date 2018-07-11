package org.ehais.enums;

public class EStoreStateEnum {

	public static Integer invalid = 0;//无效的
	public static Integer valid = 1;//生效，永久有效
	public static Integer probation = 2;//临时性/试用期，有效时间需要加上indate_start与indate_end判断
	public static Integer lease = 3;//租用型，有效时间需要加上indate_start与indate_end判断
	
}
