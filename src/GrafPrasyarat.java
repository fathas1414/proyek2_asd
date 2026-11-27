/**
 * Graf berarah (adjacency list) untuk relasi prasyarat antar mata kuliah.
 *
 *  - Setiap MK = satu simpul (SimpulMK).
 *  - Sisi berarah: MK prasyarat  --->  MK yang membutuhkannya.
 *    Contoh baris "PDAS;PLAN" di prasyarat.csv = sisi PDAS ---> PLAN.
 *  - Tiap simpul menyimpan dua daftar tetangga:
 *      prasyarat : MK yang HARUS lulus dulu (sisi masuk)
 *      lanjutan  : MK yang terbuka setelah MK ini lulus (sisi keluar)
 *
 * Tidak memakai java.util sama sekali.
 * Dibuat oleh: Ilham
 */
public class GrafPrasyarat {

    /** Satu simpul graf: sebuah MK beserta daftar tetangganya. */
    private static class SimpulMK {
        final Matakuliah mk;
        final DaftarTaut<Matakuliah> prasyarat = new DaftarTaut<>();
        final DaftarTaut<Matakuliah> lanjutan = new DaftarTaut<>();

        SimpulMK(Matakuliah mk) {
            this.mk = mk;
        }
    }

    // Semua simpul, urutan sesuai urutan masuk (= urutan matakuliah.csv)
    private final DaftarTaut<SimpulMK> semuaSimpul = new DaftarTaut<>();
    // Daftar MK saja, disimpan terpisah supaya bisa dikembalikan ke luar
    private final DaftarTaut<Matakuliah> semuaMK = new DaftarTaut<>();

    // ---------- Membangun graf (dipakai Nadya saat membaca file) ----------

    /** Tambah satu MK. Return false jika kode MK sudah ada (tidak ditambahkan dua kali). */
    public boolean tambahMK(Matakuliah mk) {
        if (cariSimpul(mk.getKode()) != null) return false;
        semuaSimpul.tambah(new SimpulMK(mk));
        semuaMK.tambah(mk);
        return true;
    }

    /**
     * Tambah sisi prasyarat. Urutan parameter SAMA dengan kolom prasyarat.csv:
     * kolom 1 = prasyarat, kolom 2 = mata kuliah yang membutuhkannya.
     * Return false jika salah satu kode tidak dikenal atau sisi sudah ada.
     */
    public boolean tambahPrasyarat(String kodePrasyarat, String kodeMK) {
        SimpulMK asal = cariSimpul(kodePrasyarat);
        SimpulMK tujuan = cariSimpul(kodeMK);
        if (asal == null || tujuan == null) return false;

        // cegah sisi ganda
        for (DaftarTaut.Simpul<Matakuliah> p = tujuan.prasyarat.getKepala(); p != null; p = p.berikut) {
            if (p.data == asal.mk) return false;
        }
        tujuan.prasyarat.tambah(asal.mk);
        asal.lanjutan.tambah(tujuan.mk);
        return true;
    }

    // ---------- Query (dipakai Fathan & Talitha) ----------

    /** Cari MK berdasarkan kode. Return null jika tidak ada. */
    public Matakuliah getMK(String kode) {
        SimpulMK s = cariSimpul(kode);
        return (s == null) ? null : s.mk;
    }

    /** Daftar MK prasyarat langsung dari sebuah MK. Return null jika kode tidak dikenal. */
    public DaftarTaut<Matakuliah> getDaftarPrasyarat(String kode) {
        SimpulMK s = cariSimpul(kode);
        return (s == null) ? null : s.prasyarat;
    }

    /** Daftar MK yang membutuhkan MK ini sebagai prasyarat. Return null jika kode tidak dikenal. */
    public DaftarTaut<Matakuliah> getDaftarLanjutan(String kode) {
        SimpulMK s = cariSimpul(kode);
        return (s == null) ? null : s.lanjutan;
    }

    /** Semua MK, urut sesuai matakuliah.csv. */
    public DaftarTaut<Matakuliah> getSemuaMK() {
        return semuaMK;
    }

    public int jumlahMK() {
        return semuaSimpul.ukuran();
    }

    // ---------- Internal ----------

    private SimpulMK cariSimpul(String kode) {
        if (kode == null) return null;
        String k = kode.trim();
        for (DaftarTaut.Simpul<SimpulMK> p = semuaSimpul.getKepala(); p != null; p = p.berikut) {
            if (p.data.mk.getKode().equalsIgnoreCase(k)) return p.data;
        }
        return null;
    }
}