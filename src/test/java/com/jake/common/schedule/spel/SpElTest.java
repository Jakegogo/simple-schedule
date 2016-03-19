package com.jake.common.schedule.spel;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jake.common.schedule.annotation.Scheduled;
import com.jake.common.schedule.annotation.ValueType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Component
public class SpElTest {

	@Autowired
	private MyBean myBean;
	
	@Scheduled(name = "SpringEL定时任务", value = "@myBean.cron", type = ValueType.SPEL)
	public void task() {
		myBean.increase();
	}
	
	@Test
	public void test() throws InterruptedException {
		Thread.sleep(15000);
		Assert.assertThat(myBean.getCount() > 2, CoreMatchers.is(true));
	}

}
