import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by julien on 15/12/2016.
 */
public class FrameConnection extends JFrame implements ActionListener {

    private JTextField txt;

    public FrameConnection() throws HeadlessException {
        this.setTitle("Connexion");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 70);
        this.setResizable(false);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("host : ");
        JButton btn = new JButton("GO");

        txt = new JTextField();

        txt.setPreferredSize(new Dimension(150, 24));

        btn.addActionListener(this);

        panel.add(label);
        panel.add(txt);
        panel.add(btn);


        this.add(panel);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(txt.getText());
        String host = this.txt.getText();

    }
}
