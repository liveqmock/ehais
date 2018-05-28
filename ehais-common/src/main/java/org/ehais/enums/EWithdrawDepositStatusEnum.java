package org.ehais.enums;

public class EWithdrawDepositStatusEnum {

	public final static Integer INIT = 0;//初始化插入
	public final static Integer SUCCESS = 1;//成功提现
	public final static Integer CONTINUED = 2;//余额不足等待续
	public final static Integer INVALID = 3;//作废，少出现
	
}
