/**
 * 
 */
package com.common.util.profile;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.common.util.console.ConsoleMethod;
import com.common.schedule.scheduler.ScheduledTask;
import com.common.schedule.scheduler.Scheduler;


/**
 * 性能分析者
 * @author Jake
 *
 */
@Component
public class Profiler {

	private static final Logger logger = LoggerFactory.getLogger("PROFILE");
	
	private final Logger commonLogger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private Scheduler scheduler;
	
	
	/**
	 * 一个请求处理这么多毫秒视为慢了
	 */
	@Autowired(required = false)
	@Qualifier("profile_service_limit_millis")
	private long serviceLimitMillis = 1000L;
	
	
	public long getServiceLimitMillis() {
		return serviceLimitMillis;
	}

	public void setServiceLimitMillis(long serviceLimitMillis) {
		this.serviceLimitMillis = serviceLimitMillis;
	}

	/**
	 * 输出日志
	 * @param format
	 * @param infos
	 */
	public static void trace(String message, Object...items){
		if(logger.isInfoEnabled()){
			logger.info(message, items);
		}
	}
	
	/**
	 * 输出错误
	 * @param format
	 * @param infos
	 */
	public static void error(String message, Exception e){
		if(logger.isErrorEnabled()){
			logger.error(message, e);
		}
	}
	
	
	private ScheduledFuture<?> future;
	
	/**
	 * 开启
	 */
	public synchronized void start(){
		
		this.close();

		future = scheduler.scheduleWithFixedDelay(new ScheduledTask() {
			
			@Override
			public void run() {
				if(isOpen()){
					profile();
				}
			}
			
			@Override
			public String getName() {
				return "性能分析定时输出";
			}
			
		}, Util.dateAdd(new Date(), 0, 1, 0), 3 * 60 * 1000L);
	
		
	}
	
	
	/**
	 * 关闭
	 */
	public synchronized  void close(){
		if(future != null){
			try {
				future.cancel(true);
			} catch (Exception e) {
			}
		}
	}
	
	@ConsoleMethod(name = "profile", description = "性能分析输出")
	private void profile(){
		Map<String, Profileable> beanMap = applicationContext.getBeansOfType(Profileable.class);
		for(Profileable profileable : beanMap.values()){
			try {
				profileable.profile();
			} catch (Exception e) {
				commonLogger.error("性能分析出错", e);
			}
		}
	}
	
	@PostConstruct
	private void init(){
		if(isOpen()){
			this.start();
		} else {
			this.close();
		}
	}
	
	/**
	 * 总开关 
	 */
	private static boolean open = false;
	
	public static boolean isOpen() {
		return open;
	}

	public static void setOpen(boolean open) {
		Profiler.open = open;
	}
	
}
