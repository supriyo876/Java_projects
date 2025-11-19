
import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {
    public WelcomePage(String username) {
        setTitle("Welcome");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Welcome to the login app  " + username + "!", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbl);

        setVisible(true);
    }
}