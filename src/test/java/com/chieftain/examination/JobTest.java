package com.chieftain.examination;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class JobTest implements Runnable {
		private ConcurrentHashMap<String, Future> futureMap;
		private int count = 0;
		private String jobId;
 
		public JobTest(String jobId,ConcurrentHashMap<String, Future> futureMap){
			this.jobId = jobId;
			this.futureMap = futureMap;
		}
		@Override
		public void run() {
			count++;
			System.out.println("this is " + count);
 
			if (count > 1) {
				try {
					Future future = futureMap.remove(jobId);
					
					future.cancel(true);
				} finally {
					System.out.println("jobId "+jobId +"had cancel");
				}
			}
 
		}
 
		public ConcurrentHashMap<String, Future> getFutureMap() {
			return futureMap;
		}
 
		public void setFutureMap(ConcurrentHashMap<String, Future> futureMap) {
			this.futureMap = futureMap;
		}
 
		public String getJobId() {
			return jobId;
		}
 
		public void setJobId(String jobId) {
			this.jobId = jobId;
		}
 
}