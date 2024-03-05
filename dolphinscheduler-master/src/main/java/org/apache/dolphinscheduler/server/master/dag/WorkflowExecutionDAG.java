package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.dao.entity.TaskInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder
public class WorkflowExecutionDAG extends BasicDAG<TaskInstance> implements IWorkflowExecutionDAG {

    private final WorkflowExecutionContext workflowExecutionContext;

    @Getter
    private final IWorkflowDAG workflowDAG;

    private final TaskExecutionRunnableRepository taskExecutionRunnableRepository;

    @Override
    public TaskExecutionRunnable getTaskExecutionRunnableById(Integer taskInstanceId) {
        return taskExecutionRunnableRepository.getTaskExecutionRunnableById(taskInstanceId);
    }

    @Override
    public List<TaskExecutionRunnable> getActiveTaskExecutionRunnables() {
        return new ArrayList<>(taskExecutionRunnableRepository.getActiveTaskExecutionRunnable());
    }

    @Override
    public TaskExecutionRunnable createTaskExecutionRunnable(String taskName) {
        return null;
    }
}
