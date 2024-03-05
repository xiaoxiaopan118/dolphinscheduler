package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.dao.entity.TaskInstance;

import java.util.List;

public interface IWorkflowExecutionDAG extends DAG<TaskInstance> {

    /**
     * Get the WorkflowDAG.
     */
    IWorkflowDAG getWorkflowDAG();

    /**
     * Get TaskExecutionRunnable by given TaskInstanceId.
     *
     * @param taskInstanceId taskInstanceId.
     * @return
     */
    TaskExecutionRunnable getTaskExecutionRunnableById(Integer taskInstanceId);

    List<TaskExecutionRunnable> getActiveTaskExecutionRunnables();

    /**
     * Create the TaskExecutionRunnable of the given taskName.
     *
     * @param taskName taskName
     * @return TaskExecutionRunnable
     */
    TaskExecutionRunnable createTaskExecutionRunnable(String taskName);
}
