import javax.swing.*;
import java.awt.*;

public class CharacterSelect extends JFrame {

    private static final int W = 1280, H = 720;

    public CharacterSelect() {

        ImagePanel bg = new ImagePanel("assets/character_select.png");
        bg.setPreferredSize(new Dimension(W, H));
        bg.setLayout(null);

        // ── Name labels just above each sprite ──────────────────────────
        JLabel baconLabel = new JLabel("BACON", SwingConstants.CENTER);
        baconLabel.setFont(new Font("Arial", Font.BOLD, 28));
        baconLabel.setForeground(Color.BLACK);
        baconLabel.setBounds(355, 375, 170, 38);
        bg.add(baconLabel);

        JLabel piknikLabel = new JLabel("PIKNIK", SwingConstants.CENTER);
        piknikLabel.setFont(new Font("Arial", Font.BOLD, 28));
        piknikLabel.setForeground(Color.BLACK);
        piknikLabel.setBounds(755, 375, 170, 38);
        bg.add(piknikLabel);

        // ── Sprite buttons ───────────────────────────────────────────────
        // Background has a grass strip at ~y=590 (bottom ~60px is soil+grass).
        // Sprites are 160×160 → top at y=420 so feet sit on the grass strip.
        int spriteW = 160, spriteH = 160;
        int groundTop = 420;   // sprites' top-left y

        JButton baconBtn  = makeImageButton("assets/bacon_select.png",  spriteW, spriteH);
        JButton piknikBtn = makeImageButton("assets/piknik_select.png", spriteW, spriteH);

        // Place Bacon at ~29% from left, Piknik at ~58% — matching PDF positions
        baconBtn.setBounds(370,  groundTop, spriteW, spriteH);
        piknikBtn.setBounds(750, groundTop, spriteW, spriteH);

        baconBtn.addActionListener(e  -> { dispose(); new LevelScreen("bacon",  1, 0); });
        piknikBtn.addActionListener(e -> { dispose(); new LevelScreen("piknik", 1, 0); });

        bg.add(baconBtn);
        bg.add(piknikBtn);

        setContentPane(bg);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton makeImageButton(String path, int w, int h) {
        Image scaled = new ImageIcon(path).getImage()
                           .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}