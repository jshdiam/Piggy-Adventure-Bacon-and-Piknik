// ── Platform.java ─────────────────────────────────────────────────────────────
// Kept as a utility in case other classes reference it.
// The actual rendering is done inside LevelScreen's GamePanel.
import java.awt.*;

public class Platform {
    public int x, y, width, height;

    public Platform(int x, int y, int width, int height) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(34, 139, 34));
        g.fillRect(x, y, width, 10);
        g.setColor(new Color(101, 67, 33));
        g.fillRect(x, y + 10, width, height - 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}