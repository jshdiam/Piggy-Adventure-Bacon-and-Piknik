import javax.swing.*;
import java.awt.*;

public class WinScreen extends JFrame {

    public WinScreen() {
        setTitle("CONGRATULATIONS!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use win background image if available
        ImagePanel bg = new ImagePanel("assets/win.png");
        bg.setPreferredSize(new Dimension(1280, 720));
        bg.setLayout(null);

        // CHOOSE CHARACTER AGAIN button
        JButton charBtn = makeBtn("CHOOSE\nCHARACTER\nAGAIN");
        charBtn.setBounds(280, 420, 280, 130);
        charBtn.addActionListener(e -> {
            dispose();
            new CharacterSelect();
        });
        bg.add(charBtn);

        // EXIT button
        JButton exitBtn = makeBtn("EXIT");
        exitBtn.setBounds(620, 420, 280, 130);
        exitBtn.addActionListener(e -> System.exit(0));
        bg.add(exitBtn);

        setContentPane(bg);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JButton makeBtn(String text) {
        // Use HTML to support multi-line
        String htmlText = "<html><center>" + text.replace("\n", "<br>") + "</center></html>";
        JButton btn = new JButton(htmlText);
        btn.setFont(new Font("Arial", Font.BOLD, 26));
        btn.setBackground(new Color(80, 80, 80, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}