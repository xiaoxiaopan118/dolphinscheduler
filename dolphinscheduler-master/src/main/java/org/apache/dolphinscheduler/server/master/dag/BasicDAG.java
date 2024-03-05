package org.apache.dolphinscheduler.server.master.dag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuperBuilder
public abstract class BasicDAG<E> implements DAG<E> {

    // todo: build DAG
    protected Map<String, DAGNode<E>> dagNodeMap;

    @Override
    public List<DAGNode<E>> getAllPostNodes(DAGNode<E> dagNode) {
        final String nodeName = dagNode.getNodeName();
        if (!dagNodeMap.containsKey(nodeName)) {
            return Collections.emptyList();
        }
        DAGNode<E> node = dagNodeMap.get(nodeName);
        List<DAGNode<E>> dagNodes = new ArrayList<>();
        for (DAGEdge edge : node.getOutDegrees()) {
            if (dagNodeMap.containsKey(edge.getToNodeName())) {
                dagNodes.add(dagNodeMap.get(edge.getToNodeName()));
            }
        }
        return dagNodes;
    }

    @Override
    public List<DAGNode<E>> getAllParentNodes(DAGNode<E> dagNode) {
        final String nodeName = dagNode.getNodeName();
        if (!dagNodeMap.containsKey(nodeName)) {
            return Collections.emptyList();
        }
        DAGNode<E> node = dagNodeMap.get(nodeName);
        List<DAGNode<E>> dagNodes = new ArrayList<>();
        for (DAGEdge edge : node.getOutDegrees()) {
            if (dagNodeMap.containsKey(edge.getFromNodeName())) {
                dagNodes.add(dagNodeMap.get(edge.getFromNodeName()));
            }
        }
        return dagNodes;
    }

    @Override
    public DAGNode<E> getDAGNode(String nodeName) {
        return dagNodeMap.get(nodeName);
    }

}
