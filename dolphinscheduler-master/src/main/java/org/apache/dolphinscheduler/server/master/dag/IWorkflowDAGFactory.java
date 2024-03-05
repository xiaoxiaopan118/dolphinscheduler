package org.apache.dolphinscheduler.server.master.dag;

public interface IWorkflowDAGFactory {

    IWorkflowDAG createWorkflowDAG(WorkflowIdentify workflowIdentify);

}
