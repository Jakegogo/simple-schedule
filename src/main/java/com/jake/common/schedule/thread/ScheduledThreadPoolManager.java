package com.jake.common.schedule.thread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jake.common.util.thread.NamedThreadFactory;

/**
 * 定时器执行线程池管理器
 * @author Jake
 */
@Component
public class ScheduledThreadPoolManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledThreadPoolManager.class);
	
	@Autowired(required = false)
	@Qualifier("scheduling_delay_time")
	private Long delayTime = 60000L;
	@Autowired(required = false)
	@Qualifier("scheduling_pool_size")
	private Integer poolSize = 5;

	private FixScheduledThreadPoolExecutor executor;
	
	
	public FixScheduledThreadPoolExecutor getExecutor() {
		return executor;
	}

	@PostConstruct
	protected void init() {
		if (logger.isInfoEnabled()) {
			logger.info("定时任务线程池大小:{}，修正时间延迟:{}", poolSize, delayTime);
		}
		ThreadGroup group = new ThreadGroup("定时任务");
		NamedThreadFactory threadFactory = new NamedThreadFactory(group, "处理");
		executor = new FixScheduledThreadPoolExecutor(poolSize, delayTime, threadFactory);
	}
	
	@PreDestroy
	protected void destory() {
		if (executor != null) {
			executor.shutdownNow();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("定时任务调度器实例[" + this + "]已经关闭");
		}
	}
	
	/**
	 * 定时任务池大小
	 */
	public int getSchedulerQueueSize() {
		return executor.getQueue().size();
	}
	
	/**
	 * 池正在执行的线程数
	 */
	public int getPoolActiveCount() {
		return executor.getActiveCount();
	}
	
	
	
}
