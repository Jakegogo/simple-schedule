package com.jake.common.schedule.scheduler;

/**
 * 定时任务接口
 * @author Jake
 */
public interface ScheduledTask extends Runnable {

	/**
	 * 获取当前任务的任务名
	 * @return
	 */
	String getName();
}
