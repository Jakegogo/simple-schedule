package com.jake.common.schedule.monitor.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jake.common.schedule.thread.ScheduledThreadPoolManager;
import com.jake.common.util.profile.Profileable;
import com.jake.common.util.thread.ThreadUtils;

/**
 * 定时器性能分析日志
 * @author Jake
 */
@Component
public class ScheduleProfiler implements Profileable {
	
	@Autowired
	private ScheduledThreadPoolManager threadPoolManager;
	
	@Override
	public void profile() {
		ThreadUtils.dumpThreadPool("定时任务线程池", threadPoolManager.getExecutor());
	}
	
}
