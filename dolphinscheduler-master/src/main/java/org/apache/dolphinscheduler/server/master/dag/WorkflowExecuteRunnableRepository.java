package org.apache.dolphinscheduler.server.master.dag;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkflowExecuteRunnableRepository {

    private final Map<Integer, IWorkflowExecutionRunnable> workflowExecutionRunnableMap = new ConcurrentHashMap<>();

    public void storeWorkflowExecutionRunnable(IWorkflowExecutionRunnable workflowExecutionRunnable) {
        workflowExecutionRunnableMap.put(workflowExecutionRunnable.getWorkflowInstanceId(), workflowExecutionRunnable);
    }

    public IWorkflowExecutionRunnable getWorkflowExecutionRunnableById(Integer workflowInstanceId) {
        return workflowExecutionRunnableMap.get(workflowInstanceId);
    }

    public Collection<IWorkflowExecutionRunnable> getActiveWorkflowExecutionRunnable() {
        return workflowExecutionRunnableMap.values();
    }

    public void removeWorkflowExecutionRunnable(Integer workflowInstanceId) {
        workflowExecutionRunnableMap.remove(workflowInstanceId);
    }

}
