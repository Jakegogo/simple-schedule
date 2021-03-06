package com.jake.common.schedule.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 计划任务触发条件声明注释(默认使用 Cron 表达式)
 * @author Jake
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

	/** 任务名 */
	String name();

	/** 表达式值 */
	String value();
	
	/** 表达式值类型 */
	ValueType type() default ValueType.EXPRESSION;
	
	/** 当无法获取表达式值时使用的默认值 */
	String defaultValue() default "";
	
	/** 是否默认启动 */
	boolean defaultStart() default true;
	
}
