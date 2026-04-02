import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JFrame {

    public GameOverScreen(String character, int level, int score) {
        setTitle("GAME OVER");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Dark panel
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(20, 20, 20));
        panel.setPreferredSize(new Dimension(500, 320));

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBounds(50, 30, 400, 70);
        panel.add(gameOverLabel);

        JLabel restartLabel = new JLabel("RESTART?", SwingConstants.CENTER);
        restartLabel.setFont(new Font("Arial", Font.BOLD, 30));
        restartLabel.setForeground(Color.WHITE);
        restartLabel.setBounds(100, 110, 300, 50);
        panel.add(restartLabel);

        JButton yesBtn = makeBtn("YES", new Color(0, 180, 80));
        JButton noBtn  = makeBtn("NO",  new Color(200, 50, 50));

        yesBtn.setBounds(60, 190, 170, 70);
        noBtn.setBounds(270, 190, 170, 70);

        // YES → restart same level, preserve score up to this level
        yesBtn.addActionListener(e -> {
            dispose();
            new LevelScreen(character, level, score);
        });

        // NO → back to FirstScreen
        noBtn.addActionListener(e -> {
            dispose();
            new FirstScreen();
        });

        panel.add(yesBtn);
        panel.add(noBtn);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JButton makeBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}