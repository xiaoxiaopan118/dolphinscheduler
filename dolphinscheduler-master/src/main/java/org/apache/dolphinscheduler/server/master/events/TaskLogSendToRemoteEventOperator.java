package org.apache.dolphinscheduler.server.master.events;

import org.apache.dolphinscheduler.common.log.remote.RemoteLogUtils;
import org.apache.dolphinscheduler.server.master.utils.TaskUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskLogSendToRemoteEventOperator implements ITaskEventOperator<TaskLogSendToRemoteEvent> {

    @Override
    public void handleEvent(TaskLogSendToRemoteEvent event) {
        if (RemoteLogUtils.isRemoteLoggingEnable() && TaskUtils.isMasterTask(event.getTaskType())) {
            RemoteLogUtils.sendRemoteLog(event.getLogPath());
            log.info("Master sends task log {} to remote storage asynchronously.", event.getLogPath());
        }
    }
}
