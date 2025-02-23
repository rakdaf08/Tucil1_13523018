import java.util.*;

public class Wadah {
  private Map<String, Character> mapWadah;
  private int baris, kolom;
  private static final String[] COLORS = {
      "\u001B[30m", // BLACK
      "\u001B[31m", // RED
      "\u001B[32m", // GREEN
      "\u001B[33m", // YELLOW
      "\u001B[34m", // BLUE
      "\u001B[35m", // MAGENTA
      "\u001B[36m", // CYAN
      "\u001B[37m", // WHITE
      "\u001B[90m", // BRIGHT BLACK (GRAY)
      "\u001B[91m", // BRIGHT RED
      "\u001B[92m", // BRIGHT GREEN
      "\u001B[93m", // BRIGHT YELLOW
      "\u001B[94m", // BRIGHT BLUE
      "\u001B[95m", // BRIGHT MAGENTA
      "\u001B[96m", // BRIGHT CYAN
      "\u001B[97m", // BRIGHT WHITE
      "\u001B[1;30m", // BOLD BLACK
      "\u001B[1;31m", // BOLD RED
      "\u001B[1;32m", // BOLD GREEN
      "\u001B[1;33m", // BOLD YELLOW
      "\u001B[1;34m", // BOLD BLUE
      "\u001B[1;35m", // BOLD MAGENTA
      "\u001B[1;36m", // BOLD CYAN
      "\u001B[1;37m", // BOLD WHITE
      "\u001B[3;30m", // ITALIC BLACK
      "\u001B[3;31m" // ITALIC RED
  };
  private static final String RESET = "\u001B[0m";
  private Map<Character, String> mapWarna;

  public Wadah(int baris, int kolom) {
    this.baris = baris;
    this.kolom = kolom;
    this.mapWadah = new HashMap<>();
    this.mapWarna = new HashMap<>();
  }

  public void printWadah() {
    Set<Character> uniqueChars = new HashSet<>(mapWadah.values());
    int colorIdx = mapWarna.size();

    for (Character c : uniqueChars) {
      if (!mapWarna.containsKey(c)) {
        mapWarna.put(c, COLORS[colorIdx % COLORS.length]);
        colorIdx++;
      }
    }

    for (int i = 0; i < baris; i++) {
      for (int j = 0; j < kolom; j++) {
        char c = getCharAt(i, j);
        if (c != '.') {
          String color = mapWarna.get(c);
          System.out.print(color + c + RESET + " ");
        } else {
          System.out.print(c + " ");
        }
      }
      System.out.println();
    }
  }

  public boolean cekBentuk(Bentuk bentuk, int x, int y) {
    for (int[] k : bentuk.getKoordinat()) {
      int newX = x + k[0];
      int newY = y + k[1];
      if (newX < 0 || newX >= baris || newY < 0 || newY >= kolom || sudahTerisi(newX, newY)) {
        return false;
      }
    }
    return true;
  }

  public boolean sudahTerisi(int x, int y) {
    return mapWadah.containsKey(x + "," + y);
  }

  public void place(Bentuk bentuk, int x, int y) {
    for (int[] k : bentuk.getKoordinat()) {
      String pos = (x + k[0]) + "," + (y + k[1]);
      mapWadah.put(pos, bentuk.getHuruf());
    }
  }

  public void hapus(Bentuk bentuk, int x, int y) {
    for (int[] k : bentuk.getKoordinat()) {
      String pos = (x + k[0]) + "," + (y + k[1]);
      mapWadah.remove(pos);
    }
  }

  public int getRows() {
    return baris;
  }

  public int getCols() {
    return kolom;
  }

  public char getCharAt(int x, int y) {
    String pos = x + "," + y;
    return mapWadah.getOrDefault(pos, '.');
  }
}