package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.server.master.events.IEventRepository;

public interface IDAGEngineFactory {

    IDAGEngine createDAGEngine(WorkflowExecutionContext workflowExecutionContext, IEventRepository eventRepository);

}
