package org.apache.dolphinscheduler.server.master.dag;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class TaskExecutionRunnable implements ITaskExecutionRunnable {

    private TaskExecutionContext taskExecutionContext;

    @Override
    public void dispatch() {

    }

    @Override
    public void kill() {

    }

    @Override
    public void pause() {

    }
}
