import java.util.*;

public class Bentuk {
  private final List<int[]> koordinat;
  private final char huruf;

  public Bentuk(List<int[]> koordinatBaru, char huruf) {
    this.koordinat = new ArrayList<>(koordinatBaru.size());
    for (int[] k : koordinatBaru) {
      this.koordinat.add(new int[] { k[0], k[1] });
    }
    this.huruf = huruf;
  }

  public List<int[]> getKoordinat() {
    return koordinat;
  }

  public char getHuruf() {
    return huruf;
  }

  public List<Bentuk> buatVariasi() {
    Set<String> bentukUnique = new HashSet<>();
    List<Bentuk> variasi = new ArrayList<>();
    Bentuk reflectedX = UbahBentuk.cerminAtas(this);
    Bentuk reflectedY = UbahBentuk.cerminSamping(this);

    addIfUnique(this, bentukUnique, variasi);
    generateRotations(this, bentukUnique, variasi);
    generateRotations(reflectedX, bentukUnique, variasi);
    generateRotations(reflectedY, bentukUnique, variasi);

    return variasi;
  }

  private void generateRotations(Bentuk awal, Set<String> bentukUnique, List<Bentuk> variasi) {
    if (!addIfUnique(awal, bentukUnique, variasi)) {
      return;
    }

    Bentuk current = awal;
    for (int i = 0; i < 3; i++) {
      current = UbahBentuk.putar90(current);
      addIfUnique(current, bentukUnique, variasi);
    }
  }

  private boolean addIfUnique(Bentuk bentuk, Set<String> bentukUnique, List<Bentuk> variasi) {
    String key = getUniqueKey(bentuk);
    if (bentukUnique.add(key)) {
      variasi.add(bentuk);
      return true;
    }
    return false;
  }

  private String getUniqueKey(Bentuk bentuk) {
    StringBuilder sb = new StringBuilder();
    List<int[]> koordinat = new ArrayList<>(bentuk.getKoordinat());

    koordinat.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

    for (int[] k : koordinat) {
      sb.append(k[0]).append(',').append(k[1]).append(';');
    }
    return sb.toString();
  }
}