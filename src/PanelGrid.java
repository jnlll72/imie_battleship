import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by julien on 14/12/2016.
 */
public class PanelGrid extends JPanel {
    private int x;
    private int y;

    private Grid grid;

    private JPanel[][] aryPanel;

    public PanelGrid(int x, int y, Grid g) {
        this.x = x;
        this.y = y;
        this.grid = g;

        this.aryPanel = new JPanel[x][y];

        GridLayout GL = new GridLayout(x, y);
        GL.setHgap(0);
        GL.setVgap(0);
        this.setLayout(GL);


        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                JPanel panel = new JPanel();
                Border grayBorder;

                if (i == 0 && j == 0) {
                    grayBorder = BorderFactory.createMatteBorder(2, 2, 1, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (j == 9 && i == 0) {
                    grayBorder = BorderFactory.createMatteBorder(2, 1, 1, 2, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (j == 0 && i == 9) {
                    grayBorder = BorderFactory.createMatteBorder(1, 2, 2, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (i == 9 && j == 9) {
                    grayBorder = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (i == 0 && (j > 0 && j < 9)) {
                    grayBorder = BorderFactory.createMatteBorder(2, 1, 1, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (i == 9 && (j > 0 && j < 9)) {
                    grayBorder = BorderFactory.createMatteBorder(1, 1, 2, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (j == 0 && (i > 0 && i < 9)) {
                    grayBorder = BorderFactory.createMatteBorder(1, 2, 1, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else if (j == 9 && (i > 0 && i < 9)) {
                    grayBorder = BorderFactory.createMatteBorder(1, 1, 1, 2, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                } else {
                    grayBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY);
                    panel.setBorder(grayBorder);
                }

                this.aryPanel[i][j] = panel;
                this.add(panel);
            }
        }

        this.setVisible(false);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        this.updateGrid();
    }

    public JPanel[][] getAryPanel() {
        return aryPanel;
    }

    public void setAryPanel(JPanel[][] aryPanel) {
        this.aryPanel = aryPanel;
    }

    private void updateGrid() {
        for (int i = 0; i < this.grid.getW(); i++) {
            for (int j = 0; j < this.grid.getH(); j++) {
                if (this.grid.getBox(i, j).getState() == 2) {
                    this.aryPanel[i][j].setBackground(Color.BLUE);
                }
            }
        }
    }

    public void updateYesBox(Box b) {
        this.aryPanel[b.getX()][b.getY()].setBackground(Color.RED);
    }

    public void updateNoBox(Box b) {
        /*JLabel label = new JLabel();
        label.setText("x");
        label.setBackground(Color.DARK_GRAY);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.aryPanel[b.getX()][b.getY()].add(label);*/

        this.aryPanel[b.getX()][b.getY()].setBackground(Color.LIGHT_GRAY);
    }
}
