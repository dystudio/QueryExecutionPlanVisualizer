package com.nydia;

import com.nydia.model.Node;
import com.nydia.model.QEP;
import com.nydia.model.Response;
import com.nydia.repository.DbRepository;
import com.nydia.util.DbUtil;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

public class Visualization extends JPanel {
    private static final long serialVersionUID = 1L;

    private static int tambahY;
    private static int tambahX;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static String userInput;

    private static final int windowWidth = screenSize.width * 2 /3;
    private static final int windowHeight = screenSize.height;

    private static QEP qep;

    public static void initQEP(String userInput) {
        DbUtil.getConnection();
        DbRepository dbRepository = new DbRepository();

        //Query to database
        Response r = dbRepository.getResponse(userInput);
        QEP qep = buildQEP(r, userInput);

        //Visualization
        Visualization panel = new Visualization(qep, userInput);
        int width = panel.calculateDimensions();
        panel.setPreferredSize(new Dimension(width, windowHeight));
        JFrame window = new JFrame();

        window.setLocationRelativeTo(null);
        JScrollPane jsp = new JScrollPane(panel);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setBounds(0, 0, windowWidth, windowHeight-100);
        jsp.getHorizontalScrollBar().addAdjustmentListener(e ->
        {
            // Repaints the panel each time the horizontal scroll bar is moves, in order to avoid ghosting.
            jsp.revalidate();
            jsp.repaint();
        });
        jsp.getVerticalScrollBar().addAdjustmentListener(e ->
        {
            // Repaints the panel each time the vertical scroll bar is moves, in order to avoid ghosting.
            jsp.revalidate();
            jsp.repaint();
        });


        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(windowWidth, windowHeight-100));
        contentPane.add(jsp);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(contentPane);
        window.pack();

        window.setVisible(true);
    }

    private static QEP buildQEP(Response r, String query) {
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


    public int calculateDimensions() {
        int result = windowWidth;
        Node root = qep.getRoot();
        int n = qep.getLevel(root, 0);
        int depth = qep.getDepth(root);
        int temp = (int) Math.pow(2, depth)-1;
        System.out.println(n);
        System.out.println(depth);
        tambahY = (windowHeight-50)/(n);

        if (temp == 0)
            return result;

        tambahX = windowWidth/temp;
        while (500*temp > result) {
            result += 160;
        }
        tambahX = 160*depth;                                                                           ;
        return result;
    }

    @Override
    public void paint(Graphics graphics) {
        Dimension dim = getSize ();
        graphics.setColor(Color.LIGHT_GRAY);

        Node root = qep.getRoot();

        paintNode(graphics, root, dim.width/2, 50, tambahX);
    }

    private void paintNode(Graphics g, Node root, int x, int y, int level) {
        if (root == null)
            return;
        g.setFont(new Font("default",Font.BOLD,12));
        g.setColor(Color.black);

        String text = root.getType();

        int centerX = x, centerY = y;
        int ovalWidth = 150, ovalHeight = 50;

        // Draw rectangle
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

        //Draw corresponding query, if any
        // Draw rectangle
        String text2 = root.getCorrespondingQuery(userInput);
        if (text2.length() > 0) {
            String[] result = cut(text2);
            centerX = x - 200;
            ovalHeight = 20;
            ovalWidth += 30;
            for (int i = 0; i < result.length; i++) {
                g.setColor(Color.yellow);
                g.fillRect(centerX - ovalWidth / 2, centerY - ovalHeight / 2,
                        ovalWidth, ovalHeight);

                double textWidth2 = fm.getStringBounds(result[i], g).getWidth();
                g.setColor(Color.black);

                g.drawString(result[i], (int) (centerX -  textWidth2 / 2),
                        (int) (centerY + fm.getMaxAscent() / 2));
                centerY += 20;
            }
        }

        if (root.getRightChild() != null) {
            g.setColor(Color.black);
            g.drawLine(x, y + ovalHeight/2, x - level, y+tambahY +ovalHeight/2);
            paintNode(g, root.getLeftChild(), x - level, y+tambahY, tambahX/2);

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

    private String[] cut(String text2) {
        String[] result = text2.split("(?<!\\G\\S+)\\s");
        String text2result = "";
        for (int i = 0; i < result.length; i++) {
            text2result += result[i] + "\n";
        }
        //System.out.println(text2result);
        return result;
    }


}