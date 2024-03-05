package org.apache.dolphinscheduler.server.master.dag;

public interface IWorkflowExecutionDAGFactory {

    IWorkflowExecutionDAG createWorkflowExecutionDAG(WorkflowExecutionContext workflowExecutionContext);
}
