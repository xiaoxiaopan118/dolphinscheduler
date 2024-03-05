package org.apache.dolphinscheduler.server.master.dag;

import org.apache.dolphinscheduler.dao.entity.ProcessTaskRelationLog;
import org.apache.dolphinscheduler.dao.entity.TaskDefinition;
import org.apache.dolphinscheduler.dao.entity.TaskDefinitionLog;
import org.apache.dolphinscheduler.dao.repository.ProcessTaskRelationLogDao;
import org.apache.dolphinscheduler.dao.repository.TaskDefinitionLogDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkflowDAGFactory implements IWorkflowDAGFactory {

    @Autowired
    private TaskDefinitionLogDao taskDefinitionLogDao;

    @Autowired
    private ProcessTaskRelationLogDao processTaskRelationLogDao;

    @Override
    public IWorkflowDAG createWorkflowDAG(WorkflowIdentify workflowIdentify) {
        Map<String, DAG.DAGNode<TaskDefinition>> dagNodeMap = createDAGMap(workflowIdentify);
        return WorkflowDAG.builder().dagNodeMap(dagNodeMap).build();
    }

    private Map<String, DAG.DAGNode<TaskDefinition>> createDAGMap(WorkflowIdentify workflowIdentify) {
        Map<String, DAG.DAGNode<TaskDefinition>> dagNodeMap = new HashMap<>();

        long workflowCode = workflowIdentify.getWorkflowCode();
        int workflowVersion = workflowIdentify.getWorkflowVersion();
        List<TaskDefinitionLog> taskDefinitions =
                taskDefinitionLogDao.queryByWorkflowDefinitionCodeAndVersion(workflowCode, workflowVersion);
        Map<Long, TaskDefinitionLog> taskDefinitionMap =
                taskDefinitions.stream().collect(Collectors.toMap(TaskDefinitionLog::getCode, Function.identity()));

        List<ProcessTaskRelationLog> taskRelations =
                processTaskRelationLogDao.queryByWorkflowDefinitionCodeAndVersion(workflowCode, workflowVersion);

        Map<String, List<DAG.DAGEdge>> inRelations = new HashMap<>();
        Map<String, List<DAG.DAGEdge>> outRelations = new HashMap<>();
        for (ProcessTaskRelationLog taskRelation : taskRelations) {
            TaskDefinitionLog preTaskDefinitionTaskDefinition = taskDefinitionMap.get(taskRelation.getPreTaskCode());
            TaskDefinitionLog postTaskDefinitionTaskDefinition = taskDefinitionMap.get(taskRelation.getPostTaskCode());
            String fromName =
                    preTaskDefinitionTaskDefinition == null ? null : preTaskDefinitionTaskDefinition.getName();
            String toName =
                    postTaskDefinitionTaskDefinition == null ? null : postTaskDefinitionTaskDefinition.getName();
            if (fromName != null) {
                if (!outRelations.containsKey(fromName)) {
                    outRelations.put(fromName, new ArrayList<>());
                }
                if (toName != null) {
                    outRelations.get(fromName).add(new DAG.DAGEdge(fromName, toName));
                }
            }
            if (toName != null) {
                if (!inRelations.containsKey(toName)) {
                    inRelations.put(toName, new ArrayList<>());
                }
                if (fromName != null) {
                    inRelations.get(toName).add(new DAG.DAGEdge(fromName, toName));
                }
            }

        }

        // Build DAG MAP
        for (TaskDefinition taskDefinition : taskDefinitions) {
            String nodeName = taskDefinition.getName();
            List<DAG.DAGEdge> inDegrees = inRelations.get(nodeName);
            List<DAG.DAGEdge> outDegrees = outRelations.get(nodeName);
            dagNodeMap.put(nodeName, new DAG.DAGNode<>(nodeName, taskDefinition, inDegrees, outDegrees));
        }
        return dagNodeMap;
    }

}
