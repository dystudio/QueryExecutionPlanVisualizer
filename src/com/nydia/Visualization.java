package com.nydia;

import com.nydia.model.Node;
import com.nydia.model.QEP;
import com.nydia.model.Response;
import com.nydia.repository.DbRepository;
import com.nydia.util.DbUtil;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Iterator;

import javax.swing.*;
import java.util.Scanner;

public class Visualization extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;

    private static final int SCALE_X = 30;
    private static final int SCALE_Y = 30;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int windowWidth = screenSize.width * 2 /3;
    private static final int windowHeight = screenSize.height;

    private static QEP qep;

    public static void main(String[] args) {
        String userInput = "";
        String s;

        Scanner scanner = new Scanner(System.in);

        do {
            s = scanner.nextLine();
            userInput += s + "\n";
        } while (!s.contains(";"));
        scanner.close();

        System.out.println(userInput.toLowerCase());

        //Connect to database
        DbUtil.getConnection();
        DbRepository dbRepository = new DbRepository();

        //Query to database
        Response r = dbRepository.getResponse(userInput);
        QEP qep = buildQEP(r, userInput);

        //Visualization
        Visualization panel = new Visualization(qep);
        panel.setPreferredSize(new Dimension(windowWidth+500, 2000));
        JFrame window = new JFrame();

        //window.setSize(windowWidth, windowHeight);
        window.setLocationRelativeTo(null);
        //panel.add(new Visualization(qep));
        //panel.setVisible(true);
        JScrollPane jsp = new JScrollPane(panel);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setBounds(0, 0, windowWidth+500, windowHeight);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(windowWidth, windowHeight-200));
        contentPane.add(jsp);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(contentPane);
        //window.setContentPane(new Visualization(qep));
        //window.add(jsp);
        //window.add(panel);
        window.pack();


        window.setVisible(true);
       //new Visualization(qep);
    }

    public static QEP buildQEP(Response r, String query) {
        //Build the QEP tree
        Node root = new Node(r.getPlan());
        QEP qep = new QEP(root);
        qep.build(r.getPlan());
        qep.printTree(root, query);
        return qep;
    }

    public Visualization(QEP qep)  {
        this.qep = qep;
    }

    private void fillRect(Graphics graphics, int x, int y) {
        graphics.fillRect(SCALE_X * x, SCALE_Y * y, SCALE_X, SCALE_Y);
    }

    private static int tambahY;
    private static int tambahX;

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        //graphics.fillRect(0, 0, SCALE_X * WIDTH, SCALE_Y * HEIGHT);

        Node root = qep.getRoot();
        int n = qep.getLevel(root, 0);
        System.out.println(n);
        int temp = n*(n+1)/2;
        tambahY = 1000/(temp);
        tambahX = 160;
        System.out.println(tambahX);
        paintNode(graphics, root, (windowWidth)/2, 50, n);
        //paintNode(graphics, root, n);
    }

    private void paintNode(Graphics g, Node root, int level) {
        int leaves = 2^level;
        int centerX = 75;
        int centerY = 0;
        for (int i = 0; i < leaves; i ++) {
            //centerY += 150;
            int ovalWidth = 150, ovalHeight = 50;

            // Draw oval
            g.setColor(Color.BLUE);
            g.fillRect(centerX - ovalWidth / 2, centerY - ovalHeight / 2,
                    ovalWidth, ovalHeight);
            centerX += 160;
        }
    }

    private void paintNode(Graphics g, Node root, int x, int y, int level) {
        if (root == null)
            return;
        if (level <= 0) {
            System.out.println("negative");
        }
        g.setColor(Color.black);

        String text = root.getType();
        int centerX = x, centerY = y;
        int ovalWidth = 150, ovalHeight = 50;

        // Draw oval
        g.setColor(Color.BLUE);
        g.fillRect(centerX-ovalWidth/2, centerY-ovalHeight/2,
                ovalWidth, ovalHeight);

        // Draw centered text
        FontMetrics fm = g.getFontMetrics();
        double textWidth = fm.getStringBounds(text, g).getWidth();
        g.setColor(Color.WHITE);
        g.drawString(text, (int) (centerX - textWidth/2),
                (int) (centerY + fm.getMaxAscent() / 2));

        if (root.getRightChild() != null) {
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x - (tambahX), y+(level+1)*tambahY +ovalHeight/2);
            paintNode(g, root.getLeftChild(), x - (tambahX), y+(level+1)*tambahY, level-1);

            //System.out.println(level);
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x + tambahX, y+(level+1)*tambahY +ovalHeight/2);
            paintNode(g, root.getRightChild(), x + tambahX, y+(level+1)*tambahY, level-1);
        }
        //Draw straight
        else if (root.getLeftChild() != null) {
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x, y+(level+1)*tambahY +ovalHeight/2);
            paintNode(g, root.getLeftChild(), x, y+(level+1)*tambahY, level);

        }
    }


}