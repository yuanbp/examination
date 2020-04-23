package com.chieftain.examination;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConditionCancelScheduler {
 
	public static void main(String[] args) throws Exception {
 
		ConcurrentHashMap<String, Future> futureMap = new ConcurrentHashMap<String, Future>();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
 
		JobTest job_1 = new JobTest("job_1", futureMap);
 
		Future future = scheduler.scheduleAtFixedRate(job_1, 1, 5, TimeUnit.SECONDS);
		futureMap.put(job_1.getJobId(), future);
		//Thread.sleep(10000);
		JobTest job_2 = new JobTest("job_22", futureMap);
 
		Future future_2 = scheduler.scheduleAtFixedRate(job_2, 1, 5, TimeUnit.SECONDS);
		futureMap.put(job_2.getJobId(), future_2);
		Thread.sleep(20000);
		
		System.out.println("test over");
		scheduler.shutdown();
	}
}