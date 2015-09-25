package org.cl.service;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class MyRejectHandler implements RejectedExecutionHandler {

	public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
		// log.error("thread pool is full");
	}

}
