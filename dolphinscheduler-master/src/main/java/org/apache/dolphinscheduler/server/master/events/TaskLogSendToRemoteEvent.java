package org.apache.dolphinscheduler.server.master.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLogSendToRemoteEvent implements ITaskEvent {

    private Integer workflowInstanceId;
    private Integer taskInstanceId;

    private String taskType;
    private String logPath;
}
