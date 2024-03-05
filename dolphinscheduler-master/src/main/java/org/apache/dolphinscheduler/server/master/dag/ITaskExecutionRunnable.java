package org.apache.dolphinscheduler.server.master.dag;

public interface ITaskExecutionRunnable {

    void dispatch();

    void kill();

    void pause();

    TaskExecutionContext getTaskExecutionContext();

}
