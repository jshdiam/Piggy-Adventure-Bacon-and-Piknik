import javax.swing.*;
import java.awt.*;

public class SecondScreen extends JFrame {

    public SecondScreen() {
        setTitle("Second Screen");

        ImagePanel bg = new ImagePanel("assets/second.png");
        bg.setPreferredSize(new Dimension(1920, 1080));

        JLabel question = new JLabel("ARE YOU READY?");
        question.setFont(new Font("Arial", Font.BOLD, 50));
        question.setForeground(Color.BLACK);
        question.setBounds(600, 350, 800, 80);

        JButton yesBtn = new JButton("YES");
        JButton noBtn = new JButton("NO");

        yesBtn.setFont(new Font("Arial", Font.BOLD, 25));
        noBtn.setFont(new Font("Arial", Font.BOLD, 25));

        int centerX = 960;
        yesBtn.setBounds(centerX - 260, 500, 220, 90);
        noBtn.setBounds(centerX + 40, 500, 220, 90);

        yesBtn.addActionListener(e -> {
            dispose();
            new ThirdScreen();
        });

        noBtn.addActionListener(e -> {
            dispose();
            new FirstScreen();
        });

        bg.add(question);
        bg.add(yesBtn);
        bg.add(noBtn);

        setContentPane(bg);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}