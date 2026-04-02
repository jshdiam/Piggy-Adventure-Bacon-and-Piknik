import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LevelScreen extends JFrame implements KeyListener {

    private static final int W = 1280, H = 720;

    private String  character;
    private int     level;
    private int     score;

    private int     playerX, playerY;
    private int     velX = 0, velY = 0;
    private boolean onGround  = false;
    private boolean moveLeft  = false;
    private boolean moveRight = false;

    private static final int GRAVITY    =  1;
    private static final int JUMP_FORCE = -18;
    private static final int SPEED      =  5;
    private static final int PW         =  70;
    private static final int PH         =  70;

    private List<Rectangle> platforms = new ArrayList<>();
    private List<Rectangle> coins     = new ArrayList<>();
    private List<Rectangle> obstacles = new ArrayList<>();
    private int totalCoins;
    private int coinsCollected = 0;

    private Image bgImage, playerImg, coinImg, panImg, platImg;

    private Timer     gameTimer;
    private GamePanel gamePanel;
    private boolean   alive = true;

    public LevelScreen(String character, int level, int score) {
        this.character = character;
        this.level     = level;
        this.score     = score;

        setTitle("Level " + level);
        setSize(W, H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        loadImages();
        buildLevel();

        gamePanel = new GamePanel();
        setContentPane(gamePanel);
        addKeyListener(this);
        setFocusable(true);

        gameTimer = new Timer(16, e -> tick());
        gameTimer.start();
        setVisible(true);
    }

    private void loadImages() {
        bgImage   = load("assets/level" + level + ".png");
        playerImg = load("assets/" + character + ".png");
        coinImg   = load("assets/" + character + "_coin.png");
        panImg    = load("assets/pan.png");
        platImg   = load("assets/platform.png");
    }

    private Image load(String path) {
        return new ImageIcon(path).getImage();
    }

    private void buildLevel() {
        platforms.clear(); coins.clear(); obstacles.clear();

        switch (level) {
            case 1  -> buildLevel1();
            case 2  -> buildLevel2();
            default -> buildLevel3();
        }

        Rectangle spawnPlat = null;
        for (Rectangle p : platforms) {
            if (p.x == 0) {
                if (spawnPlat == null || p.y > spawnPlat.y) spawnPlat = p;
            }
        }
        if (spawnPlat == null) spawnPlat = platforms.get(0);

        playerX        = spawnPlat.x + 60;
        playerY        = spawnPlat.y - PH;
        velX           = 0;
        velY           = 0;
        onGround       = true;
        totalCoins     = coins.size();
        coinsCollected = 0;
    }

    private void buildLevel1() {
        platforms.add(rect(0,   590, 640, 30));
        platforms.add(rect(950, 590, 330, 30));
        platforms.add(rect(750, 450, 530, 50));

        // Ground top=590: coins at 590-40=550, pan at 590-48=542
        coins.add(rect(300, 542, 40, 40));
        coins.add(rect(390, 542, 40, 40));
        coins.add(rect(480, 542, 40, 40));
        // Elevated platform top=450: coin at 450-40=410, pan at 450-48=402
        coins.add(rect(870, 402, 40, 40));

        obstacles.add(rect(200, 542, 60, 48));
        obstacles.add(rect(1080, 402, 60, 48));
    }

    private void buildLevel2() {
        // ── Platforms ──────────────────────────────────────────────
        // Ground left — player spawns here
        // Width 320 so player has open space above to jump without hitting mid-left platform
        platforms.add(rect(0,   640, 320, 30));

        // Ground right
        platforms.add(rect(960, 640, 320, 30));

        // Mid-left — starts at x=200 so left edge is NOT above the spawn area.
        // Player walks right under the open gap first, then jumps up onto it.
        platforms.add(rect(200, 500, 500, 50));

        // Mid-right — starts at x=730, reaches right edge
        platforms.add(rect(730, 500, 550, 50));

        // Top center — reachable by jumping from either mid platform
        platforms.add(rect(350, 340, 580, 50));

        // ── Coins ──────────────────────────────────────────────────
        // Left ground top = y=640, coins sit at 640-40 = 600
        coins.add(rect(100, 592, 40, 40));
        // Mid-left platform top = y=500, coins sit at 500-40 = 460
        coins.add(rect(250, 452, 40, 40));
        // Mid-right platform top = y=500
        coins.add(rect(1100, 452, 40, 40));
        // Top platform top = y=340, coins sit at 340-40 = 300
        coins.add(rect(530, 292, 40, 40));
        coins.add(rect(650, 292, 40, 40));

        // ── Obstacles ──────────────────────────────────────────────
        // Ground left pan: ground top=640, pan sits at 640-48 = 592
        obstacles.add(rect(185, 592, 60, 48));
        // Top platform pan: top=340, pan sits at 340-48 = 292
        obstacles.add(rect(840, 292, 60, 48));
        // Mid-right platform pan: top=500, pan sits at 500-48 = 452
        obstacles.add(rect(1190, 452, 60, 48));
    }

    private void buildLevel3() {
        platforms.add(rect(0,    650, 820, 30));
        platforms.add(rect(1260, 650, 196, 30));
        platforms.add(rect(180,  545, 530, 50));
        platforms.add(rect(860,  610, 380, 50));
        platforms.add(rect(300,  375, 640, 50));  

        coins.add(rect(735, 602, 40, 40));

        coins.add(rect(450, 497, 40, 40));
        coins.add(rect(555, 497, 40, 40));
        coins.add(rect(660, 497, 40, 40));

        coins.add(rect(450, 327, 40, 40));
        coins.add(rect(555, 327, 40, 40));
        coins.add(rect(660, 327, 40, 40));

        obstacles.add(rect(470, 602, 60, 48));
        obstacles.add(rect(365, 497, 60, 48));
        obstacles.add(rect(1330, 570, 60, 48));
    }

    private Rectangle rect(int x, int y, int w, int h) {
        return new Rectangle(x, y, w, h);
    }

    private void tick() {
        if (!alive) return;

        velX = moveLeft ? -SPEED : (moveRight ? SPEED : 0);
        playerX += velX;
        if (playerX < 0)      playerX = 0;
        if (playerX + PW > W) playerX = W - PW;

        velY    += GRAVITY;
        playerY += velY;

        onGround = false;

        for (Rectangle plat : platforms) {
            boolean hOverlap = (playerX + PW > plat.x + 2) && (playerX < plat.x + plat.width - 2);
            if (!hOverlap) continue;

            int platTop    = plat.y;
            int platBottom = plat.y + plat.height;

            int footY     = playerY + PH;   // foot this frame
            int footYPrev = footY - velY;   // foot last frame
            int headY     = playerY;        // head this frame
            int headYPrev = headY - velY;   // head last frame


            if (velY >= 0 && footYPrev < platTop + 60 && footY >= platTop) {
                playerY  = platTop - PH;
                velY     = 0;
                onGround = true;
            }


            if (velY < 0 && headYPrev > platBottom - 10 && headY <= platBottom) {
                playerY = platBottom;
                velY    = 2;
            }
        }

        if (playerY > H + 60) { triggerGameOver(); return; }

        Rectangle pRect = new Rectangle(playerX, playerY, PW, PH);
        List<Rectangle> grabbed = new ArrayList<>();
        for (Rectangle c : coins) {
            if (pRect.intersects(c)) { grabbed.add(c); coinsCollected++; score += 10; }
        }
        coins.removeAll(grabbed);

        if (coins.isEmpty() && coinsCollected == totalCoins) {
            alive = false;
            gameTimer.stop();
            SwingUtilities.invokeLater(this::goNext);
            return;
        }

        for (Rectangle obs : obstacles) {
            if (pRect.intersects(obs)) { triggerGameOver(); return; }
        }

        gamePanel.repaint();
    }

    private void triggerGameOver() {
        if (!alive) return;
        alive = false;
        gameTimer.stop();
        SwingUtilities.invokeLater(() -> { dispose(); new GameOverScreen(character, level, score); });
    }

    private void goNext() {
        dispose();
        if (level < 3) new LevelScreen(character, level + 1, score);
        else           new WinScreen();
    }

    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT  || k == KeyEvent.VK_A) moveLeft  = true;
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) moveRight = true;
        if ((k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP || k == KeyEvent.VK_W) && onGround) {
            velY = JUMP_FORCE; onGround = false;
        }
    }
    @Override public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT  || k == KeyEvent.VK_A) moveLeft  = false;
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) moveRight = false;
    }
    @Override public void keyTyped(KeyEvent e) {}

    private class GamePanel extends JPanel {
        GamePanel() { setPreferredSize(new Dimension(W, H)); setLayout(null); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bgImage != null) g2.drawImage(bgImage, 0, 0, W, H, this);
            else { g2.setColor(new Color(230, 160, 80)); g2.fillRect(0, 0, W, H); }

            for (Rectangle p : platforms) {
                if (platImg != null) {
                    g2.drawImage(platImg, p.x, p.y, p.width, p.height, this);
                } else {
                    g2.setColor(new Color(34, 139, 34));
                    g2.fillRect(p.x, p.y, p.width, 10);
                    g2.setColor(new Color(139, 90, 43));
                    g2.fillRect(p.x, p.y + 10, p.width, p.height - 10);
                }
            }

            for (Rectangle c : coins) {
                if (coinImg != null) g2.drawImage(coinImg, c.x, c.y, c.width, c.height, this);
                else {
                    g2.setColor(Color.YELLOW); g2.fillOval(c.x, c.y, c.width, c.height);
                    g2.setColor(Color.ORANGE); g2.drawOval(c.x, c.y, c.width, c.height);
                }
            }

            for (Rectangle obs : obstacles) {
                if (panImg != null) g2.drawImage(panImg, obs.x, obs.y, obs.width, obs.height, this);
                else {
                    g2.setColor(Color.DARK_GRAY);
                    g2.fillOval(obs.x, obs.y, obs.width - 14, obs.height);
                    g2.fillRect(obs.x + obs.width - 14, obs.y + obs.height / 4, 28, 9);
                }
            }

            if (playerImg != null) g2.drawImage(playerImg, playerX, playerY, PW, PH, this);
            else { g2.setColor(Color.PINK); g2.fillOval(playerX, playerY, PW, PH); }

            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(10, 10, 230, 80, 12, 12);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString("SCORE: " + score, 22, 42);
            g2.drawString("LEVEL "  + level,  22, 76);
        }
    }
}