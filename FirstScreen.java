import javax.swing.*;
import java.awt.*;

public class FirstScreen extends JFrame {

    public FirstScreen() {
        setTitle("Piggy Adventure");

        ImagePanel bg = new ImagePanel("assets/first.png");
        bg.setPreferredSize(new Dimension(1920, 1080));

        JButton nextBtn = new JButton("NEXT");
        nextBtn.setFont(new Font("Arial", Font.BOLD, 30));
        nextBtn.setFocusPainted(false);
        nextBtn.setBounds(1300, 500, 200, 80);

        nextBtn.addActionListener(e -> {
            dispose();
            new SecondScreen();
        });

        bg.add(nextBtn);

        setContentPane(bg);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}