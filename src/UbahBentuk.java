import java.util.*;

public class UbahBentuk {
  public static Bentuk putar90(Bentuk bentuk) {
    List<int[]> koordinatBaru = new ArrayList<>();
    for (int[] k : bentuk.getKoordinat()) {
      koordinatBaru.add(new int[] { -k[1], k[0] });
    }

    return new Bentuk(normalisasi(koordinatBaru), bentuk.getHuruf());
  }

  public static Bentuk putar180(Bentuk bentuk) {
    List<int[]> koordinatBaru = new ArrayList<>();
    for (int[] k : bentuk.getKoordinat()) {
      koordinatBaru.add(new int[] { -k[0], -k[1] });
    }

    return new Bentuk(normalisasi(koordinatBaru), bentuk.getHuruf());
  }

  public static Bentuk putar270(Bentuk bentuk) {
    List<int[]> koordinatBaru = new ArrayList<>();
    for (int[] k : bentuk.getKoordinat()) {
      koordinatBaru.add(new int[] { k[1], -k[0] });
    }

    return new Bentuk(normalisasi(koordinatBaru), bentuk.getHuruf());
  }

  public static Bentuk cerminAtas(Bentuk bentuk) {
    List<int[]> koordinatBaru = new ArrayList<>();
    for (int[] k : bentuk.getKoordinat()) {
      koordinatBaru.add(new int[] { k[0], -k[1] });
    }

    return new Bentuk(normalisasi(koordinatBaru), bentuk.getHuruf());
  }

  public static Bentuk cerminSamping(Bentuk bentuk) {
    List<int[]> koordinatBaru = new ArrayList<>();
    for (int[] k : bentuk.getKoordinat()) {
      koordinatBaru.add(new int[] { -k[0], k[1] });
    }

    return new Bentuk(normalisasi(koordinatBaru), bentuk.getHuruf());
  }

  private static List<int[]> normalisasi(List<int[]> koordinat) {
    int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
    for (int[] k : koordinat) {
      minX = Math.min(minX, k[0]);
      minY = Math.min(minY, k[1]);
    }

    List<int[]> hasilNormalisasi = new ArrayList<>();
    for (int[] k : koordinat) {
      hasilNormalisasi.add(new int[] { k[0] - minX, k[1] - minY });
    }

    hasilNormalisasi.sort(Comparator.comparingInt((int[] k) -> k[0])
        .thenComparingInt(k -> k[1]));
    return hasilNormalisasi;
  }
}
