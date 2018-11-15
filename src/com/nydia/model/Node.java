package com.nydia.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {

    private String type;
    private String relation;
    private double executionTime;
    private List<String> groupKey;
    private List<String> sortKey;
    private Plan plan;

    private String correspondingQuery;

    protected Node leftChild;
    protected Node rightChild;

    //Constructor for scan
    public Node (Plan p) {
        this.plan = p;
        this.type =  p.getNodeType();
        this.relation = p.getRelationName();
        this.groupKey = p.getGroupKey();
        this.sortKey = p.getSortKey();
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

    public String getCorrespondingQuery(String query) {
        if (this.type.contains("Join")) {
            //Find substring that contains join
            String pattern = "join";
            Pattern limitPattern = Pattern.compile(pattern);
            Matcher m = limitPattern.matcher(query.toLowerCase());
            if (m.find()) {
                System.out.println(query.substring(m.start(), m.end()));
            }
            return "Join";
        }
        else if (this.type.contains("Loop")) {
            //Find substring
            return "Loop";
        }
        else if (this.type.contains("Limit")){
            //Find substring that contains "limit [number]"
            Pattern limitPattern = Pattern.compile("limit " + plan.getPlanRows());
            Matcher m = limitPattern.matcher(query.toLowerCase());
            if (m.find()) {
                System.out.println(query.substring(m.start(), m.end()));
            }
            return "Limit";
        }
        else if (this.type.contains("Sort")){
            //Find substring that contains "order by [sortkey]"
            //String pattern = "order by";
            String tempQuery = "order by";
            for (int i = 0; i < plan.getSortKey().size(); i++) {
                StringBuilder strbldr = new StringBuilder(plan.getSortKey().get(0));

                try {
                    int firstBracket = plan.getSortKey().get(0).indexOf("(");
                    strbldr = strbldr.deleteCharAt(firstBracket);
                    int lastBracket = strbldr.toString().lastIndexOf(")");
                    strbldr = strbldr.deleteCharAt(lastBracket);
                } catch (StringIndexOutOfBoundsException e) {
                    //Do nothing
                }
                tempQuery += " " + strbldr.toString().replace("(", "\\(").replace(")", "\\)");
            }

            Pattern limitPattern = Pattern.compile(tempQuery.toLowerCase());
            Matcher m = limitPattern.matcher(query.toLowerCase());

            if (m.find()) {
                System.out.println(query.substring(m.start(), m.end()));
            }
            return "Sort";
        }
        else if (this.type.contains("Aggregate")){
            //Find substring that contains "Group by [x]"
            String pattern = "group by";
            for (int i = 0; i < plan.getGroupKey().size(); i++) {
                pattern += " " + plan.getGroupKey().get(i);
            }
            Pattern limitPattern = Pattern.compile(pattern);
            Matcher m = limitPattern.matcher(query.toLowerCase());
            if (m.find()) {
                System.out.println(query.substring(m.start(), m.end()));
            }
            return "Aggregate";
        }
        else if (this.type.contains("Scan")) {
            //Find substring that contains WHERE
            String pattern = "where";
            StringBuilder strbldr = new StringBuilder(plan.getSortKey().get(0));

            try {
                int firstBracket = plan.getSortKey().get(0).indexOf("(");
                strbldr = strbldr.deleteCharAt(firstBracket);
                int lastBracket = strbldr.toString().lastIndexOf(")");
                strbldr = strbldr.deleteCharAt(lastBracket);
            } catch (StringIndexOutOfBoundsException e) {
                //Do nothing
            }
            pattern += " " + strbldr.toString().replace("(", "\\(").replace(")", "\\)");
        }
        return null;
    }
}
