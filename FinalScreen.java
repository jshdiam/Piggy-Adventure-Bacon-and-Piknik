import javax.swing.*;
import java.awt.*;

// Legacy screen — game now uses WinScreen instead.
public class FinalScreen extends JFrame {

    public FinalScreen() {
        setTitle("Congratulations");

        JLabel label = new JLabel("YOU FINISHED ALL LEVELS!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));

        JButton back = new JButton("BACK TO MENU");

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);

        back.addActionListener(e -> { dispose(); new FirstScreen(); });

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}