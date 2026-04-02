import java.awt.*;

public class Obstacle {
    public int x, y, width = 60, height = 50;

    public Obstacle(int x, int y) {
        this.x = x; this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x, y, width - 10, height);
        g.fillRect(x + width - 10, y + height / 4, 30, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}