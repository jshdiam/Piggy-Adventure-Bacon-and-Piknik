import javax.swing.*;
import java.awt.*;

public class FryingPan {
    private Image image;
    private int x, y, width, height;

    public FryingPan(int x, int y) {
        image = new ImageIcon("assets/pan.png").getImage();
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 50;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
