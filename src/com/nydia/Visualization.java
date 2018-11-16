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
    private static String userInput;
    private int scaleX;

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
        Visualization panel = new Visualization(qep,userInput);
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

    public static void initQEP(String userInput) {
        DbUtil.getConnection();
        DbRepository dbRepository = new DbRepository();

        //Query to database
        Response r = dbRepository.getResponse(userInput);
        QEP qep = buildQEP(r, userInput);

        //Visualization
        Visualization panel = new Visualization(qep,userInput);
        panel.setPreferredSize(new Dimension(panel.calculateDimensions(), windowHeight));
        System.out.println(panel.calculateDimensions());
        JFrame window = new JFrame();

        window.setLocationRelativeTo(null);
        JScrollPane jsp = new JScrollPane(panel);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setBounds(0, 0, windowWidth, windowHeight-100);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(windowWidth, windowHeight-100));
        contentPane.add(jsp);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(contentPane);
        //window.setContentPane(new Visualization(qep));
        //window.add(jsp);
        //window.add(panel);
        window.pack();

        window.revalidate();
        window.repaint();
        window.setVisible(true);
    }

    public static QEP buildQEP(Response r, String query) {
        //Build the QEP tree
        Node root = new Node(r.getPlan());
        QEP qep = new QEP(root);
        qep.build(r.getPlan());
        qep.printTree(root, query);
        return qep;
    }

    public Visualization(QEP qep, String userInput)  {
        this.qep = qep;
        this.userInput = userInput;
    }

    private void fillRect(Graphics graphics, int x, int y) {
        graphics.fillRect(SCALE_X * x, SCALE_Y * y, SCALE_X, SCALE_Y);
    }

    private static int tambahY;
    private static int tambahX;

    public int calculateDimensions() {
        int result = windowWidth;
        Node root = qep.getRoot();
        int n = qep.getLevel(root, 0);
        int temp = (int) Math.pow(2, n)-1;
        System.out.println(n);
        System.out.println(temp);
        tambahY = (windowHeight-50)/(n);
        tambahX = windowWidth/temp;
        while (tambahX < 80) {
            result += 160;
            System.out.println(result);
            tambahX = result/temp;
        }
        tambahX = tambahX*n;
        return result;
    }

    @Override
    public void paint(Graphics graphics) {
        Dimension dim = getSize ();
        graphics.setColor(Color.LIGHT_GRAY);
        //graphics.fillRect(0, 0, SCALE_X * WIDTH, SCALE_Y * HEIGHT);

        Node root = qep.getRoot();
        int n = qep.getLevel(root, 0);
        int temp = (int) Math.pow(2, n)-1;
        tambahY = (dim.height-50)/(n);
        /*while (tambahX < 160) {
            dim.setSize(dim.width + 160, dim.height);
            dim = getSize ();
            System.out.println(dim.width);
            tambahX = dim.width/temp;
            System.out.println(tambahX);
        }*/

        paintNode(graphics, root, dim.width/2, 50, tambahX);
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
        g.setFont(new Font("default",Font.BOLD,12));
        g.setColor(Color.black);

        String text = root.getType();
        String text2 = root.getCorrespondingQuery(userInput); 
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
                (int) (centerY + fm.getMaxAscent()/2));
        FontMetrics fm2 = g.getFontMetrics();
        double textWidth2 = fm.getStringBounds(text2, g).getWidth();
        g.setColor(Color.RED);
        g.drawString(text2, (int) (centerX - textWidth2/2),
                (int) (centerY - fm2.getMaxAscent()/1.5));

        if (root.getRightChild() != null) {
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x - (level), y+tambahY +ovalHeight/2);
            paintNode(g, root.getLeftChild(), x - (level), y+tambahY, tambahX/2);

            //System.out.println(level);
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x + level, y+tambahY +ovalHeight/2);
            paintNode(g, root.getRightChild(), x + level, y+tambahY, tambahX/2);
        }
        //Draw straight
        else if (root.getLeftChild() != null) {
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x, y+tambahY +ovalHeight/2);
            paintNode(g, root.getLeftChild(), x, y+tambahY, level);

        }
    }


}