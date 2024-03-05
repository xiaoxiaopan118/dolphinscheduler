package org.apache.dolphinscheduler.server.master.events;

import org.apache.dolphinscheduler.common.enums.WorkflowExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.ProcessInstance;
import org.apache.dolphinscheduler.dao.repository.ProcessInstanceDao;
import org.apache.dolphinscheduler.server.master.dag.IWorkflowExecutionRunnable;
import org.apache.dolphinscheduler.server.master.dag.WorkflowExecuteRunnableRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkflowFailedEventOperator
        implements
            IWorkflowEventOperator<WorkflowFailedEvent> {

    @Autowired
    private WorkflowExecuteRunnableRepository workflowExecuteRunnableRepository;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    @Override
    public void handleEvent(WorkflowFailedEvent event) {
        Integer workflowInstanceId = event.getWorkflowInstanceId();
        IWorkflowExecutionRunnable workflowExecutionRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnableById(workflowInstanceId);

        ProcessInstance workflowInstance = workflowExecutionRunnable.getWorkflowInstance();
        workflowInstance.setState(WorkflowExecutionStatus.FAILURE);
        processInstanceDao.updateById(workflowInstance);
        log.info("Handle WorkflowExecutionRunnableFailedEvent success, set workflowInstance status to {}",
                workflowInstance.getState());

        eventDispatcher.dispatchEvent(new WorkflowFinalizeEvent(workflowInstanceId));
    }
}
