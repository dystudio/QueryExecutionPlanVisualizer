package com.nydia;

import com.nydia.model.Node;
import com.nydia.model.QEP;
import com.nydia.model.Response;
import com.nydia.repository.DbRepository;
import com.nydia.util.DbUtil;

import java.util.Scanner;


public class Main {

    public static void main(String[] argv) {

        String userInput = "";
        String s;

        Scanner scanner = new Scanner(System.in);

        do {
            s = scanner.nextLine();
            userInput += s + "\n";
        } while (!s.contains(";"));
        scanner.close();

        //System.out.println(userInput);

        //Connect to database
        DbUtil.getConnection();
        DbRepository dbRepository = new DbRepository();

        //Query to database
        Response r = dbRepository.getResponse(userInput);
        buildQEP(r, userInput);

    }

    public static void buildQEP(Response r, String query) {
        //Build the QEP tree
        Node root = new Node(r.getPlan());
        QEP qep = new QEP(root);
        qep.build(r.getPlan());
        qep.printTree(root, query);
    }
}
