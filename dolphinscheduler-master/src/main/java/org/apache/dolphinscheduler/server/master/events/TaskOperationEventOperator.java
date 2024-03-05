package org.apache.dolphinscheduler.server.master.events;

import org.apache.dolphinscheduler.server.master.dag.IWorkflowExecutionDAG;
import org.apache.dolphinscheduler.server.master.dag.IWorkflowExecutionRunnable;
import org.apache.dolphinscheduler.server.master.dag.TaskExecutionRunnable;
import org.apache.dolphinscheduler.server.master.dag.WorkflowExecuteRunnableRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskOperationEventOperator implements ITaskEventOperator<TaskOperationEvent> {

    @Autowired
    private WorkflowExecuteRunnableRepository workflowExecuteRunnableRepository;

    @Override
    public void handleEvent(TaskOperationEvent event) {
        IWorkflowExecutionRunnable workflowExecutionRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnableById(event.getWorkflowInstanceId());
        if (workflowExecutionRunnable == null) {
            log.error("Cannot find the IWorkflowExecutionRunnable for event: {}", event);
            return;
        }
        IWorkflowExecutionDAG workflowExecutionDAG = workflowExecutionRunnable.getDagEngine().getWorkflowExecutionDAG();
        TaskExecutionRunnable taskExecutionRunnable =
                workflowExecutionDAG.getTaskExecutionRunnableById(event.getTaskInstanceId());
        if (taskExecutionRunnable == null) {
            log.error("Cannot find the ITaskExecutionRunnable for event: {}", event);
        }
        switch (event.getTaskOperationType()) {
            case DISPATCH:
                taskExecutionRunnable.dispatch();
                break;
            case KILL:
                taskExecutionRunnable.kill();
                break;
            case PAUSE:
                taskExecutionRunnable.pause();
                break;
            default:
                log.error("Unknown TaskOperationType for event: {}", event);
        }
    }
}
