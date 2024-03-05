package org.apache.dolphinscheduler.server.master.dag;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The Directed Acyclic Graph class
 *
 * @param <E> type of the node content.
 */
public interface DAG<E> {

    List<DAGNode<E>> getAllPostNodes(DAGNode<E> dagNode);

    /**
     * Return the post task name of given parentTaskName.
     *
     * @param dagNodeName parent task name, can be null.
     * @return post task name list, sort by priority.
     * @throws IllegalArgumentException if the dagNodeName is not null and cannot find the node in DAG.
     */
    default List<DAGNode<E>> getAllPostNodes(String dagNodeName) {
        DAGNode<E> dagNode = getDAGNode(dagNodeName);
        if (dagNodeName != null && dagNode == null) {
            throw new IllegalArgumentException("Cannot find the Node: " + dagNodeName + " in DAG");
        }
        return getAllPostNodes(dagNode);
    }

    default List<String> getAllPostNodeNames(String dagNodeName) {
        DAGNode<E> dagNode = getDAGNode(dagNodeName);
        if (dagNodeName != null && dagNode == null) {
            throw new IllegalArgumentException("Cannot find the Node: " + dagNodeName + " in DAG");
        }
        return getAllPostNodes(dagNode).stream().map(DAGNode::getNodeName).collect(Collectors.toList());
    }

    List<DAGNode<E>> getAllParentNodes(DAGNode<E> dagNode);

    default List<DAGNode<E>> getAllParentNodes(String dagNodeName) {
        DAGNode<E> dagNode = getDAGNode(dagNodeName);
        if (dagNodeName != null && dagNode == null) {
            throw new IllegalArgumentException("Cannot find the Node: " + dagNodeName + " in DAG");
        }
        return getAllParentNodes(dagNode);
    }

    default List<String> getAllParentNodeNames(String dagNodeName) {
        DAGNode<E> dagNode = getDAGNode(dagNodeName);
        if (dagNodeName != null && dagNode == null) {
            throw new IllegalArgumentException("Cannot find the Node: " + dagNodeName + " in DAG");
        }
        return getAllParentNodes(dagNode).stream().map(DAGNode::getNodeName).collect(Collectors.toList());
    }

    DAGNode<E> getDAGNode(String node);

    /**
     * The node of the DAG.
     *
     * @param <E> content type of the node.
     */
    @Data
    @Builder
    @AllArgsConstructor
    class DAGNode<E> {

        private String nodeName;
        private E nodeContent;

        private List<DAGEdge> inDegrees;
        private List<DAGEdge> outDegrees;
    }

    /**
     * The edge of the DAG.
     */
    @Data
    @AllArgsConstructor
    class DAGEdge {

        private String fromNodeName;
        private String toNodeName;
    }

}
