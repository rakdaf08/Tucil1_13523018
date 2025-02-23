import java.util.*;

public class Bruteforce {
  private Wadah wadah;
  private List<Bentuk> listBentuk;
  private List<List<Bentuk>> semuaVariasi;
  private int jumlahKasus;

  public Bruteforce(Wadah wadah, List<Bentuk> listBentuk) {
    this.wadah = wadah;
    this.listBentuk = listBentuk;
    this.semuaVariasi = new ArrayList<>();
    this.jumlahKasus = 0;

    for (Bentuk bentuk : listBentuk) {
      semuaVariasi.add(bentuk.buatVariasi());
    }
  }

  public boolean solved() {
    return solveBacktrack(0);
  }

  private boolean solveBacktrack(int bentukIdx) {
    if (bentukIdx == listBentuk.size()) {
      int totalWadah = wadah.getRows() * wadah.getCols();
      int wadahTerisi = 0;

      for (int i = 0; i < wadah.getRows(); i++) {
        for (int j = 0; j < wadah.getCols(); j++) {
          if (wadah.getCharAt(i, j) != '.') {
            wadahTerisi++;
          }
        }
      }

      return wadahTerisi == totalWadah;
    }

    List<Bentuk> variasiSekarang = semuaVariasi.get(bentukIdx);

    for (Bentuk variasi : variasiSekarang) {
      for (int x = 0; x < wadah.getRows(); x++) {
        for (int y = 0; y < wadah.getCols(); y++) {
          jumlahKasus++;

          if (wadah.cekBentuk(variasi, x, y)) {
            wadah.place(variasi, x, y);

            if (solveBacktrack(bentukIdx + 1)) {
              return true;
            }

            wadah.hapus(variasi, x, y);
          }
        }
      }
    }
    return false;
  }

  public int getJumlahKasus() {
    return jumlahKasus;
  }
}