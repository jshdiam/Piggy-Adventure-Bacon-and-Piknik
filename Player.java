import java.awt.*;

public class Player {
    public int x, y, width = 70, height = 70;
    public int velocityY = 0;
    public boolean onGround = false;

    public Player(int x, int y) {
        this.x = x; this.y = y;
    }

    public void update() {
        velocityY += 1;
        y += velocityY;
        if (y >= 800) {
            y = 800; velocityY = 0; onGround = true;
        }
    }

    public void jump() {
        if (onGround) { velocityY = -18; onGround = false; }
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}