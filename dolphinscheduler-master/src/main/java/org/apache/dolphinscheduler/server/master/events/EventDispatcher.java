package org.apache.dolphinscheduler.server.master.events;

import org.apache.dolphinscheduler.server.master.dag.IWorkflowExecutionRunnable;
import org.apache.dolphinscheduler.server.master.dag.WorkflowExecuteRunnableRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventDispatcher implements IEventDispatcher<IEvent> {

    @Autowired
    private EventEngine eventEngine;

    @Autowired
    private WorkflowExecuteRunnableRepository workflowExecuteRunnableRepository;

    @Override
    public void start() {
        log.info(getClass().getName() + " started");
    }

    @Override
    public void dispatchEvent(IEvent iEvent) {
        Integer workflowInstanceId;
        if (iEvent instanceof IWorkflowEvent) {
            workflowInstanceId = ((IWorkflowEvent) iEvent).getWorkflowInstanceId();
        } else if (iEvent instanceof ITaskEvent) {
            workflowInstanceId = ((ITaskEvent) iEvent).getWorkflowInstanceId();
        } else {
            throw new IllegalArgumentException("Unsupported event type: " + iEvent.getClass().getName());
        }

        IWorkflowExecutionRunnable workflowExecuteRunnable =
                workflowExecuteRunnableRepository.getWorkflowExecutionRunnableById(workflowInstanceId);
        if (workflowExecuteRunnable == null) {
            log.error("Cannot find the IWorkflowExecutionRunnable for event: {}", iEvent);
            return;
        }
        workflowExecuteRunnable.getEventRepository().storeEventToTail(iEvent);
        log.debug("Success dispatch event {} to EventRepository", iEvent);
        eventEngine.notify();
    }

    @Override
    public void stop() {
        log.info(getClass().getName() + " stopped");
    }

}
