import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private String character;
    private int level;
    private int score;

    private Timer timer;

    private Image bg, playerImg;

    private Player player;

    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<FryingPan> obstacles = new ArrayList<>();

    public GamePanel(String character, int level, int score) {
        this.character = character;
        this.level = level;
        this.score = score;

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();

        bg = new ImageIcon("assets/level" + level + ".png").getImage();
        playerImg = new ImageIcon("assets/" + character + ".png").getImage();

        player = new Player(100, 600);

        // Platforms (adjust to your PDF)
        platforms.add(new Platform(0, 850, 1920, 200));
        platforms.add(new Platform(300, 650, 200, 30));
        platforms.add(new Platform(700, 550, 200, 30));
        platforms.add(new Platform(1100, 450, 200, 30));

        // Coins
        for (Platform p : platforms) {
            coins.add(new Coin(p.x + 80, p.y - 50, character));
        }

        // Obstacle
        obstacles.add(new FryingPan(900, 800));

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // Platforms
        g.setColor(Color.GREEN);
        for (Platform p : platforms) {
            g.fillRect(p.x, p.y, p.width, p.height);
        }

        // Coins
        for (Coin c : coins) {
            c.draw(g);
        }

        // Obstacles
        for (FryingPan f : obstacles) {
            f.draw(g);
        }

        // Player
        g.drawImage(playerImg, player.x, player.y, 100, 100, null);

        // Score
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("SCORE: " + score, 50, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        player.update();

        // Platform collision
        player.onGround = false;
        for (Platform p : platforms) {
            if (player.getBounds().intersects(p.getBounds())) {
                player.y = p.y - player.height;
                player.velocityY = 0;
                player.onGround = true;
            }
        }

        // Coins
        for (int i = 0; i < coins.size(); i++) {
            if (player.getBounds().intersects(coins.get(i).getBounds())) {
                coins.remove(i);
                score += 10;
                i--;
            }
        }

        // Obstacle
        for (FryingPan f : obstacles) {
            if (player.getBounds().intersects(f.getBounds())) {
                timer.stop();
                SwingUtilities.getWindowAncestor(this).dispose();
                new GameOverScreen(character, level, score);
            }
        }

        // Next level
        if (coins.isEmpty()) {
            timer.stop();
            SwingUtilities.getWindowAncestor(this).dispose();

            if (level < 3) {
                new LevelScreen(character, level + 1, score);
            } else {
                new WinScreen();
            }
        }

        repaint();
    }

    // Controls
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) player.jump();
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.x -= 10;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.x += 10;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}