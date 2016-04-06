package com.zizaike.passport.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
/**
 * 
 * ClassName: AsyncApplicationEventMulticaster <br/>  
 * Function: 监听器实现异步执行. <br/>  
 * date: 2016年4月6日 下午2:28:43 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class AsyncApplicationEventMulticaster extends SimpleApplicationEventMulticaster {
	private TaskExecutor taskExecutor = new TaskExecutor() {
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		
		@Override
		public void execute(Runnable task) {
			executorService.execute(task);
		}
	};
	
	protected TaskExecutor getTaskExecutor() {
		return this.taskExecutor;
	}
}