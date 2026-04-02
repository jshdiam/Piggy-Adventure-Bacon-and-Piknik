import javax.swing.*;
import java.awt.*;

public class Coin {
    private Image image;
    private int x, y, width, height;

    public Coin(int x, int y, String character) {
        String coinImage = character.equals("bacon") ? "assets/bacon_coin.png" : "assets/piknik_coin.png";
        image = new ImageIcon(coinImage).getImage();
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
