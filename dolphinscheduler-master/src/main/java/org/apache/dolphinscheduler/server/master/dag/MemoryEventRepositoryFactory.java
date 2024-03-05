package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.server.master.events.IEventRepository;
import org.apache.dolphinscheduler.server.master.events.MemoryEventRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoryEventRepositoryFactory implements IEventRepositoryFactory {

    @Override
    public IEventRepository createEventRepository() {
        return new MemoryEventRepository();
    }
}
