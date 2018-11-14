package com.nydia.model;

import java.util.List;

public class QEP {
    private Node root;
    private List<Node> nodes;

    public QEP(Node root) {
        this.root = root;
    }

    public void build(Plan p) {
        root = addNode(root, p.getPlans().get(0));
    }

    public Node addNode(Node current, Plan p) {
        if (current == null){
            return null;
        }
        Node newNode = new Node(p.getNodeType(), p.getRelationName(), p.getGroupKey(), p.getSortKey());
        System.out.println("Inserting " + newNode.getType());
        try {
            current.leftChild = addNode(newNode, p.getPlans().get(0));
            System.out.println("Root:" + current.getType());
            System.out.println("Left child: " + current.getLeftChild().getType());
            current.rightChild = addNode(newNode, p.getPlans().get(1));
            System.out.println("Right child: " + current.getRightChild().getType());
        } catch (IndexOutOfBoundsException e) {

        } catch (NullPointerException e) {
            System.out.println("Current:" + current.getType());
            current.leftChild = newNode;
        }
        return current;
    }

    public void printTree(Node node) {
        if (node == null){
            return;
        }
        System.out.printf("%s ", node.getType());
        System.out.print("Left child: ");
        printTree(node.getLeftChild());
        System.out.print("Right child: ");
        printTree(node.getRightChild());
        System.out.println();
    }

    public void visualize() {}


}
