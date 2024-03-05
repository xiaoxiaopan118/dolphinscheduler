package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.server.master.events.IEventRepository;

public interface IEventfulExecutionRunnable {

    IEventRepository getEventRepository();
}
