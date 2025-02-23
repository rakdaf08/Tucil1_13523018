import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PrintGambar {
  public static void saveImageSolution(Wadah wadah, String filename) throws IOException {
    int cellSize = 50; // Ukuran setiap sel dalam pixel
    int width = wadah.getCols() * cellSize;
    int height = wadah.getRows() * cellSize;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = image.createGraphics();

    // Set background putih
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, width, height);

    // Set font
    g2d.setFont(new Font("Arial", Font.BOLD, 20));

    // Map warna untuk setiap karakter
    Map<Character, Color> colorMap = new HashMap<>();
    Color[] colors = {
        Color.RED, Color.GREEN, Color.BLUE,
        Color.MAGENTA, Color.CYAN, Color.ORANGE,
        Color.PINK, Color.DARK_GRAY, Color.LIGHT_GRAY,
        new Color(128, 0, 0), new Color(0, 128, 0),
        new Color(0, 0, 128)
    };

    int colorIndex = 0;

    // Gambar grid dan karakter
    for (int i = 0; i < wadah.getRows(); i++) {
      for (int j = 0; j < wadah.getCols(); j++) {
        char c = wadah.getCharAt(i, j);

        // Set warna untuk karakter
        if (c != '.') {
          if (!colorMap.containsKey(c)) {
            colorMap.put(c, colors[colorIndex % colors.length]);
            colorIndex++;
          }
          g2d.setColor(colorMap.get(c));
        } else {
          g2d.setColor(Color.BLACK);
        }

        // Gambar karakter
        String s = String.valueOf(c);
        int x = j * cellSize + (cellSize - g2d.getFontMetrics().stringWidth(s)) / 2;
        int y = i * cellSize + ((cellSize + g2d.getFontMetrics().getHeight()) / 2);
        g2d.drawString(s, x, y);

        // Gambar grid
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
      }
    }

    g2d.dispose();
    ImageIO.write(image, "PNG", new File(filename));
  }
}
