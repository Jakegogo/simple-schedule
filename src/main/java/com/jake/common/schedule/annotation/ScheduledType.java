package com.jake.common.schedule.annotation;

/**
 * 计划任务触发条件值类型
 * @author Jake
 */
public enum ScheduledType {
	
	/** 直接为字符串表达式 */
	EXPRESSION,
	/** 属性名 */
	FIELD_NAME;
}
