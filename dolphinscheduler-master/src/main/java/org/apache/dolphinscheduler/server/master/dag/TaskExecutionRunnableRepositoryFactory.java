package org.apache.dolphinscheduler.server.master.dag;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskExecutionRunnableRepositoryFactory {

    public TaskExecutionRunnableRepository createTaskExecutionRunnableRepository() {
        return new TaskExecutionRunnableRepository();
    }

}
