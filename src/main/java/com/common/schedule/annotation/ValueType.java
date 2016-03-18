package com.common.schedule.annotation;

/**
 * 定时执行表达式类型
 * @author Jake
 */
public enum ValueType {
	
	/** 直接为字符串表达式 */
	EXPRESSION,
	/** Bean名 */
	BEANNAME,
	/** Spring EL 表达式*/
	SPEL;
}
