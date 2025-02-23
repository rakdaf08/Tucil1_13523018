import java.io.*;
import java.util.*;

public class PuzzleInput {
  public static class PuzzleData {
    public int baris, kolom;
    public List<Bentuk> bentukList;

    public PuzzleData(int baris, int kolom, List<Bentuk> bentukList) {
      this.baris = baris;
      this.kolom = kolom;
      this.bentukList = bentukList;
    }

    public int getCountBentuk() {
      Set<Character> uniqueChars = new HashSet<>();
      for (Bentuk bentuk : bentukList) {
        uniqueChars.add(bentuk.getHuruf());
      }
      return uniqueChars.size();
    }
  }

  public static PuzzleData bacaPuzzle(String fileName) throws IOException {
    List<Bentuk> bentuk = new ArrayList<>();
    int panjang = 0;
    int lebar = 0;
    int jumlahBentuk = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String baris1 = br.readLine();
      if (baris1 == null) {
        throw new IllegalArgumentException("Baris pertama kosong");
      }

      String[] ukuran = baris1.split(" ");
      if (ukuran.length != 3) {
        throw new IllegalArgumentException("Komponen baris pertama kurang");
      }

      try {
        panjang = Integer.parseInt(ukuran[0]);
        lebar = Integer.parseInt(ukuran[1]);
        jumlahBentuk = Integer.parseInt(ukuran[2]);

        if (panjang <= 0 || lebar <= 0 || jumlahBentuk <= 0) {
          throw new IllegalArgumentException("Setiap angka harus positif");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Input Ukuran Salah");
      }

      String baris2 = br.readLine();
      if (baris2 == null || !baris2.equals("DEFAULT")) {
        throw new IllegalArgumentException("Hanya bisa DEFAULT");
      }

      List<int[]> listBentuk = new ArrayList<>();
      String line;
      char currentChar = ' ';
      int row = 0;

      while ((line = br.readLine()) != null) {
        if (line.trim().isEmpty()) {
          row++;
          continue;
        }

        char charBentuk = ' ';
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) != ' ') {
            charBentuk = line.charAt(i);
            break;
          }
        }

        if (charBentuk == ' ') {
          row++;
          continue;
        }

        if (charBentuk != currentChar && !listBentuk.isEmpty()) {
          bentuk.add(new Bentuk(listBentuk, currentChar));
          listBentuk = new ArrayList<>();
        }
        currentChar = charBentuk;

        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) == currentChar) {
            listBentuk.add(new int[] { row, i });
          }
        }
        row++;
      }

      if (!listBentuk.isEmpty()) {
        bentuk.add(new Bentuk(listBentuk, currentChar));
      }

      PuzzleData puzzleData = new PuzzleData(panjang, lebar, bentuk);
      if (puzzleData.getCountBentuk() != jumlahBentuk) {
        throw new IllegalArgumentException("Jumlah bentuk tidak sesuai");
      }
    }

    return new PuzzleData(panjang, lebar, bentuk);
  }
}