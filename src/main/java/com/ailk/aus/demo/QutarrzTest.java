package com.ailk.aus.demo;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QutarrzTest {

	public static void main(String[] args) throws SchedulerException, ParseException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		JobDetail jobDetail = new JobDetail("ds", "jobDetailGroupOK", WDeTEst.class);
		CronTrigger cronTrigger = new CronTrigger("dd", "triggerGroupOk");
		CronExpression cexp = new CronExpression("0/5 * * * * ?");
		cronTrigger.setCronExpression(cexp);

		JobDetail jobDetail1 = new JobDetail("ds1", "jobDetailGroupOK1", WDeTEst.class);
		CronTrigger cronTrigger1 = new CronTrigger("dd1", "triggerGroupOk1");
		CronExpression cexp1 = new CronExpression("0/10 * * * * ?");
		cronTrigger1.setCronExpression(cexp1);

		sched.scheduleJob(jobDetail, cronTrigger);
		sched.scheduleJob(jobDetail1, cronTrigger1);
		sched.start();
	}

}
