import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
      try {
        new ProcessBuilder("cmd", "/c", "color").inheritIO().start().waitFor();
      } catch (Exception e) {
      }
    }

    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Masukkan nama file: ");
      String filename = scanner.nextLine();

      if (!filename.contains("/")) {
        filename = "test/input/" + filename;
      }

      PuzzleInput.PuzzleData puzzleData = PuzzleInput.bacaPuzzle(filename);

      int rows = puzzleData.baris;
      int cols = puzzleData.kolom;
      List<Bentuk> bentukList = puzzleData.bentukList;

      Wadah wadah = new Wadah(rows, cols);
      Bruteforce solver = new Bruteforce(wadah, bentukList);

      System.out.println("Mencari solusi...\n");
      long waktuMulai = System.currentTimeMillis();
      boolean found = solver.solved();
      long waktuSelesai = System.currentTimeMillis();
      long durasi = waktuSelesai - waktuMulai;

      if (found) {
        System.out.println("Solusi ditemukan!\n");
        wadah.printWadah();
        System.out.println("\nWaktu pencarian: " + durasi + " ms\n");
        System.out.println("Banyak kasus yang ditinjau: " + solver.getJumlahKasus() + "\n");
        System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
        String jawaban = scanner.nextLine().toLowerCase();

        if (jawaban.equals("ya")) {
          saveSolution(wadah, filename);
        }
      } else {
        System.out.println("Tidak ada solusi yang ditemukan.");
        System.out.println("\nWaktu pencarian: " + durasi + " ms\n");
        System.out.println("Banyak kasus yang ditinjau: " + solver.getJumlahKasus() + "\n");
      }

      scanner.close();

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void saveTextSolution(Wadah wadah, String filename) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (int i = 0; i < wadah.getRows(); i++) {
        for (int j = 0; j < wadah.getCols(); j++) {
          writer.print(wadah.getCharAt(i, j) + " ");
        }
        writer.println();
      }
    }
  }

  public static void saveSolution(Wadah wadah, String filename) {
    try {
      String inputfile = filename;
      if (inputfile.contains("/")) {
        inputfile = inputfile.substring(inputfile.lastIndexOf("/") + 1);
      }
      if (inputfile.contains(".")) {
        inputfile = inputfile.substring(0, inputfile.lastIndexOf("."));
      }

      String textFilename = "test/output/solusi_" + inputfile + ".txt";
      saveTextSolution(wadah, textFilename);

      String imageFilename = "test//output/solusi_" + inputfile + ".png";
      PrintGambar.saveImageSolution(wadah, imageFilename);

      System.out.println("Solusi berhasil disimpan di:");
      System.out.println("- Text: " + textFilename);
      System.out.println("- Image: " + imageFilename);
    } catch (IOException e) {
      System.out.println("Error saat menyimpan solusi: " + e.getMessage());
    }
  }
}