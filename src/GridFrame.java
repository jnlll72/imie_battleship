import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Array;

/**
 * Created by julien on 30/11/2016.
 */
public class GridFrame extends JFrame implements ActionListener {
    private JButton[][] aryButton;
    private JPanel[][] aryPanel;
    private Grid grid = new Grid();

    private PanelGrid panGrid;

    private Client client;

    private boolean ready = false;


    public GridFrame(Client client, int index) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MetalLookAndFeel());

        this.client = client;

        aryButton = new JButton[this.grid.getW()][this.grid.getH()];
        aryPanel = new JPanel[this.grid.getW()][this.grid.getH()];

        panGrid = new PanelGrid(this.grid.getW(), this.grid.getH(), this.grid);

        this.setTitle("BattleShip - Client " + index);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setResizable(false);

        // PANEL MAIN

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        JPanel pMain1 = new JPanel();
        JPanel pMain2 = new JPanel();

        pMain1.setLayout(new BorderLayout());

        pMain2.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.DARK_GRAY));
        pMain2.setPreferredSize(new Dimension(1000, 100));

        // SOUS PANEL MAIN

        JPanel sPan1 = new JPanel();
        JPanel sPan2 = new JPanel();

        sPan1.setLayout(new BorderLayout());

        sPan1.setPreferredSize(new Dimension(700, 700));
        sPan2.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.DARK_GRAY));

        // PANEL GRID

        JPanel panN = new JPanel();
        JPanel panE = new JPanel();
        JPanel panS = new JPanel();
        JPanel panW = new JPanel();

        JPanel panC = new JPanel();

        panN.setPreferredSize(new Dimension(700, 50));
        panE.setPreferredSize(new Dimension(50, 700));
        panW.setPreferredSize(new Dimension(50, 700));
        panS.setPreferredSize(new Dimension(700, 50));

        panC.setPreferredSize(new Dimension(400, 400));

        GridLayout GL = new GridLayout(this.grid.getW() + 1, this.grid.getH() + 1);
        GL.setHgap(0);
        GL.setVgap(0);
        panC.setLayout(GL);

        String[] alpha = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        for (int i = 0; i < this.grid.getW() + 1; i++) {
            for (int j = 0; j < this.grid.getH() + 1; j++) {

                if (i == 0 && j == 0) {
                    panC.add(new JPanel());
                } else if (i == 0) {
                    JLabel label = new JLabel(alpha[j - 1]);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    panC.add(label);
                } else if (j == 0) {
                    JLabel label = new JLabel(Integer.toString(i));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    panC.add(label);
                } else {
                    JButton btn = new JButton();
                    Border grayBorder;

                    if (i == 1 && j == 1) {
                        grayBorder = BorderFactory.createMatteBorder(2, 2, 1, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (j == 10 && i == 1) {
                        grayBorder = BorderFactory.createMatteBorder(2, 1, 1, 2, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (j == 1 && i == 10) {
                        grayBorder = BorderFactory.createMatteBorder(1, 2, 2, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (i == 10 && j == 10) {
                        grayBorder = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (i == 1 && (j > 1 && j < 10)) {
                        grayBorder = BorderFactory.createMatteBorder(2, 1, 1, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (i == 10 && (j > 1 && j < 10)) {
                        grayBorder = BorderFactory.createMatteBorder(1, 1, 2, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (j == 1 && (i > 1 && i < 10)) {
                        grayBorder = BorderFactory.createMatteBorder(1, 2, 1, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else if (j == 10 && (i > 1 && i < 10)) {
                        grayBorder = BorderFactory.createMatteBorder(1, 1, 1, 2, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    } else {
                        grayBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
                        btn.setBorder(grayBorder);
                    }

                    //btn.setText("(" + i + "," + j + ")");
                    btn.setBackground(Color.WHITE);
                    btn.addActionListener(this);
                    this.aryButton[i - 1][j - 1] = btn;
                    panC.add(btn);
                }


            }
        }

        JButton btnReady = new JButton();
        btnReady.setText("Ready");

        btnReady.addActionListener(this);
        panS.add(btnReady);


        sPan1.add(panN, BorderLayout.NORTH);
        sPan1.add(panE, BorderLayout.EAST);
        sPan1.add(panW, BorderLayout.WEST);
        sPan1.add(panS, BorderLayout.SOUTH);
        sPan1.add(panC, BorderLayout.CENTER);


        JPanel panThisGrid = new JPanel();
        JPanel panInfos = new JPanel();

        panThisGrid.setLayout(new BorderLayout());
        panThisGrid.setPreferredSize(new Dimension(300, 300));

        JPanel panThisN = new JPanel();
        JPanel panThisE = new JPanel();
        JPanel panThisS = new JPanel();
        JPanel panThisW = new JPanel();

        JPanel panThisC = new JPanel();

        panThisN.setPreferredSize(new Dimension(300, 25));
        panThisE.setPreferredSize(new Dimension(25, 300));
        panThisW.setPreferredSize(new Dimension(25, 300));
        panThisS.setPreferredSize(new Dimension(300, 25));

        panThisC.setPreferredSize(new Dimension(250, 250));

        //panThisC.setBackground(Color.BLUE);


        //=======================================
        //Ajout du nouveau panel pour voir mes bateaux dans panThisC

        panThisC = this.panGrid;

        //=======================================

        panThisGrid.add(panThisN, BorderLayout.NORTH);
        panThisGrid.add(panThisE, BorderLayout.EAST);
        panThisGrid.add(panThisC, BorderLayout.CENTER);
        panThisGrid.add(panThisS, BorderLayout.SOUTH);
        panThisGrid.add(panThisW, BorderLayout.WEST);


        sPan2.setLayout(new BorderLayout());

        sPan2.add(panThisGrid, BorderLayout.NORTH);
        sPan2.add(panInfos, BorderLayout.CENTER);

        pMain1.add(sPan1, BorderLayout.WEST);
        pMain1.add(sPan2, BorderLayout.CENTER);

        main.add(pMain1, BorderLayout.CENTER);
        main.add(pMain2, BorderLayout.SOUTH);

        this.setContentPane(main);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ready") && !this.ready) {
            boolean ok = this.grid.generateBoat();

            if (ok) {
                System.out.println("GRILLE VALIDE");
                this.panGrid.setGrid(this.grid);
                this.panGrid.setVisible(true);
                ((JButton) e.getSource()).setEnabled(false);
                this.lockGrid();
                try {
                    Object[] ary = new Object[]{true, this.grid};
                    this.client.sendMsg(ary);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.err.println("GRILLE NON VALIDE");
            }

        } else if (!this.ready) {
            Box b = this.isBox(e.getSource());
            //System.out.println(b);
            //System.out.println(this.grid);
        } else if (this.ready) {
            //System.out.println("OK");
            Box b = this.checkBox(e.getSource());
            //System.out.println(b);
            JButton btn = this.aryButton[b.getX()][b.getY()];

            Object[] msg = null;

            //Boat boat = this.grid.touche(b);

            if (b.getState() != 3) {
                if (b.getState() == 2) {
                    btn.setBackground(Color.BLUE);
                    msg = new Object[]{"YES", b};
                } else {
                    btn.setBackground(Color.GRAY);
                    msg = new Object[]{"NO", b};
                }
            }


            btn.setEnabled(false);

            if (this.grid.getBox(b.getX(), b.getY()).getState() != 3) {
                this.grid.getBox(b.getX(), b.getY()).setState(3);

                try {
                    this.client.sendMsg(msg);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private Box checkBox(Object e) {
        for (int i = 0; i < this.grid.getW(); i++) {
            for (int j = 0; j < this.grid.getH(); j++) {
                if (this.aryButton[i][j].equals(e)) {
                    return this.grid.getBox(i, j);
                }
            }
        }
        return null;
    }

    private Box isBox(Object e) {
        if (!this.ready) {
            for (int i = 0; i < this.grid.getW(); i++) {
                for (int j = 0; j < this.grid.getH(); j++) {
                    if (this.aryButton[i][j].equals(e)) {
                        this.grid.setBox(this.grid.getBox(i, j));
                        this.setStateButton(this.grid.getBox(i, j));
                        return this.grid.getBox(i, j);
                    }
                }
            }
        }
        return null;
    }

    private void setStateButton(Box box) {
        int x = box.getX();
        int y = box.getY();
        switch (box.getState()) {
            case 0:
                this.aryButton[x][y].setBackground(Color.WHITE);
                break;
            case 1:
                this.aryButton[x][y].setBackground(Color.CYAN);
                break;
            default:
                break;
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        this.ready = true;
        this.unLockGrid();
        this.resetGrid();
    }

    public void lockGrid() {

        for (int i = 0; i < this.grid.getW(); i++) {
            for (int j = 0; j < this.grid.getH(); j++) {
                this.aryButton[i][j].setEnabled(false);
            }
        }
    }

    public void unLockGrid() {
        for (int i = 0; i < this.grid.getW(); i++) {
            for (int j = 0; j < this.grid.getH(); j++) {
                if (this.grid.getBox(i, j).getState() != 3) {
                    this.aryButton[i][j].setEnabled(true);
                }
            }
        }
    }

    public void resetGrid() {
        for (int i = 0; i < this.grid.getW(); i++) {
            for (int j = 0; j < this.grid.getH(); j++) {
                this.aryButton[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public void setTextBoxYes(Box b) {
        /*this.aryButton[b.getX()][b.getY()].setText("x");
        this.aryButton[b.getX()][b.getY()].setForeground(Color.GREEN);*/

        this.panGrid.updateYesBox(b);


    }

    public void setTextBoxNo(Box b) {
        /*this.aryButton[b.getX()][b.getY()].setText("x");
        this.aryButton[b.getX()][b.getY()].setForeground(Color.RED);*/
        this.panGrid.updateNoBox(b);
    }


    public boolean checkBoxBoat(Box b) {
        if (this.grid.getBox(b.getX(), b.getY()).getState() == 2) {
            return true;
        }
        return false;
    }
}
