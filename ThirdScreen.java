import javax.swing.*;
import java.awt.*;

public class ThirdScreen extends JFrame {

    public ThirdScreen() {
        setTitle("Start Game");

        ImagePanel bg = new ImagePanel("assets/third.png");
        bg.setPreferredSize(new Dimension(1920, 1080));
        bg.setLayout(null);

        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font("Arial", Font.BOLD, 40));
        startBtn.setFocusPainted(false);
        startBtn.setBounds(860, 600, 200, 100);

        // After clicking START -> go to CharacterSelect
        startBtn.addActionListener(e -> {
            dispose();
            new CharacterSelect();
        });

        bg.add(startBtn);

        setContentPane(bg);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}