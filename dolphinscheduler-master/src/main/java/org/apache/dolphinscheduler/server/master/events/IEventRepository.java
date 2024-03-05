package org.apache.dolphinscheduler.server.master.events;

/**
 * The event repository interface used to store event.
 */
public interface IEventRepository {

    void storeEventToTail(IEvent event);

    void storeEventToHead(IEvent event);

    IEvent poolEvent();

    int getEventSize();

}
