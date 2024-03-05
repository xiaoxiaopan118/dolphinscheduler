package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.dao.entity.TaskInstance;

import lombok.Data;

@Data
public class TaskExecutionContext {

    private TaskInstance taskInstance;

}
