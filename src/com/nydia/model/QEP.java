package com.nydia.model;

import java.util.List;

public class QEP {
    private Node root;
    private List<Node> nodes;

    public QEP(Node root) {
        this.root = root;
    }

    public void build(Plan p) {
        root = addNode(root, p);
    }

    public Node addNode(Node current, Plan p) {
        if (p == null){
            return null;
        }
        Node newNode;
        //System.out.println("Inserting " + newNode.getType());
        try {
            newNode = new Node(p.getPlans().get(0));
            current.leftChild = addNode(newNode, p.getPlans().get(0));
            //System.out.println("Root:" + current.getType());
            //System.out.println("Left child: " + current.getLeftChild().getType());
            newNode = new Node(p.getPlans().get(1));
            current.rightChild = addNode(newNode, p.getPlans().get(1));
            //System.out.println("Right child: " + current.getRightChild().getType());
        } catch (IndexOutOfBoundsException e) {

        } catch (NullPointerException e) {
            //System.out.println("Current:" + current.getType());
            //current.leftChild = newNode;
        }
        return current;
    }

    public void printTree(Node node, String query) {
        if (node == null){
            return;
        }
        System.out.printf("Node: %s \n", node.getType());
        System.out.println(node.getCorrespondingQuery(query));
        //System.out.print("Left child: ");
        printTree(node.getLeftChild(), query);
        //System.out.print("Right child: ");
        printTree(node.getRightChild(), query);
        //System.out.println();
    }

    public Node getRoot() {
        return root;
    }

    public int getLevel(Node root, int level) {
        if (root == null)
            return level;
        int level1 = getLevel(root.getLeftChild(), level+1);
        int level2 = getLevel(root.getRightChild(), level + 1);
        return max(level1, level2);
    }

    private int max(int level1, int level2) {
        if (level1 >= level2)
            return level1;
        else return level2;
    }

    public void visualize() {}


}
