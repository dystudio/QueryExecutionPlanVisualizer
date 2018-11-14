package com.nydia.model;

import java.util.List;

public class Node {

    private String type;
    private String relation;
    private double executionTime;
    private List<String> groupKey;
    private List<String> sortKey;

    protected Node leftChild;
    protected Node rightChild;

    //Constructor for scan
    public Node (String nodeType, String relationName, List<String> groupKey, List<String> sortKey) {
        this.type = nodeType;
        this.relation = relationName;
        this.groupKey = groupKey;
        this.sortKey = sortKey;
    }

    public void setLeftChild (Node left) {
        leftChild = left;
    }

    public void setRightChild (Node right) {
        rightChild = right;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
