package com.common.schedule.job.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.schedule.job.ScheduleJobService;
import com.common.schedule.scheduler.ScheduledTask;
import com.common.schedule.scheduler.Scheduler;
import com.common.schedule.scheduler.Trigger;
import com.common.schedule.scheduler.impl.SchedulingRunner;

/**
 * 定时器管理实现类
 * @author Jake
 */
@Component
public class ScheduleJobServiceImpl implements ScheduleJobService {
	
	@Autowired
	private Scheduler scheduler;

	@Override
	public List<SchedulingRunner> listSchedulingRunner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduledTask> listSchedulingTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelScheduledTask(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedule(ScheduledTask task, Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedule(ScheduledTask task, Date startTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedule(ScheduledTask task, String cron) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduleAtFixedRate(ScheduledTask task, Date startTime,
			long period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduleAtFixedRate(ScheduledTask task, long period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduleWithFixedDelay(ScheduledTask task, Date startTime,
			long delay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduleWithDelay(ScheduledTask task, long delay) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
