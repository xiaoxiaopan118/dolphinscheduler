package org.apache.dolphinscheduler.server.master.dag;

public interface ITaskExecutionRunnableFactory {

    TaskExecutionRunnable createTaskExecutionRunnable(TaskExecutionContext taskExecutionContext);

}
