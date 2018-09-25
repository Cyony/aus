package com.ailk.aus.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WDeTEst implements Job {

	int a = 0;
	String test = null;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		a++;
		test = test + "q";
		System.out.println("--------------" + a + "---" + test);
	}

}
