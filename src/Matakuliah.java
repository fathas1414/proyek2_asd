/**
 * Data satu mata kuliah (satu baris matakuliah.csv).
 * Dibuat oleh: Ilham
 */
public class Matakuliah {
    private final String kode;
    private final String nama;
    private final int sks;
    private final int semester;
    private final String tipe; // "Ganjil", "Genap", atau "Keduanya"

    public Matakuliah(String kode, String nama, int sks, int semester, String tipe) {
        this.kode = kode.trim().toUpperCase();
        this.nama = nama.trim();
        this.sks = sks;
        this.semester = semester;
        this.tipe = tipe.trim();
    }

    public String getKode()    { return kode; }
    public String getNama()    { return nama; }
    public int getSks()        { return sks; }
    public int getSemester()   { return semester; }
    public String getTipe()    { return tipe; }

    /**
     * Apakah MK ini ditawarkan di semester berikutnya?
     * @param semesterBerikutnya "ganjil" atau "genap" (huruf besar/kecil bebas)
     * MK bertipe "Keduanya" (mis. Skripsi) selalu ditawarkan.
     */
    public boolean ditawarkan(String semesterBerikutnya) {
        if (tipe.equalsIgnoreCase("Keduanya")) return true;
        return tipe.equalsIgnoreCase(semesterBerikutnya.trim());
    }

    /** Format sesuai contoh output soal: "SO Sistem Operasi 5 SKS" */
    @Override
    public String toString() {
        return kode + " " + nama + " " + sks + " SKS";
    }
}