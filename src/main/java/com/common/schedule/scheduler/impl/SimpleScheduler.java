package com.common.schedule.scheduler.impl;

import java.util.Date;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.schedule.scheduler.ScheduledTask;
import com.common.schedule.scheduler.Scheduler;
import com.common.schedule.scheduler.Trigger;
import com.common.schedule.thread.ScheduledThreadPoolManager;

/**
 * 定时任务调度器
 * @author Jake
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SimpleScheduler implements Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(SimpleScheduler.class);
	
	@Autowired
	private ScheduledThreadPoolManager threadPoolManager;
	
	
	@Override
	public ScheduledFuture schedule(ScheduledTask task, Trigger trigger) {
		try {
			task = new LogDecorateTask(task);
			return new SchedulingRunner(task, trigger, this.threadPoolManager.getExecutor()).schedule();
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture schedule(ScheduledTask task, Date startTime) {
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.threadPoolManager.getExecutor().schedule(task, initialDelay, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> schedule(ScheduledTask task, String cron) {
		CronTrigger trigger = new CronTrigger(cron);
		return schedule(task, trigger);
	}

	@Override
	public ScheduledFuture scheduleAtFixedRate(ScheduledTask task, Date startTime, long period) {
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.threadPoolManager.getExecutor().scheduleAtFixedRate(task, initialDelay, period,
					TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture scheduleAtFixedRate(ScheduledTask task, long period) {
		try {
			task = new LogDecorateTask(task);
			return this.threadPoolManager.getExecutor().scheduleAtFixedRate(task, 0, period, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture scheduleWithFixedDelay(ScheduledTask task, Date startTime, long delay) {
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.threadPoolManager.getExecutor().scheduleWithFixedDelay(task, initialDelay, delay,
					TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture scheduleWithDelay(ScheduledTask task, long delay) {
		try {
			task = new LogDecorateTask(task);
			return this.threadPoolManager.getExecutor().schedule(task, delay, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	/**
	 * 用于做日志记录的任务装饰类
	 * @author Frank
	 */
	private static class LogDecorateTask implements ScheduledTask {

		private ScheduledTask task;

		public LogDecorateTask(ScheduledTask task) {
			this.task = task;
		}

		public String getName() {
			return task.getName();
		}

		public void run() {
			if (logger.isDebugEnabled()) {
				logger.debug("任务[{}]开始运行时间[{}]", task.getName(), new Date());
			}
			try {
				task.run();
			} catch (RuntimeException e) {
				logger.error("任务[" + task.getName() + "]在执行时出现异常!", e);
				throw e;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("任务[{}]结束运行时间[{}]", task.getName(), new Date());
			}
		}

	}

}
