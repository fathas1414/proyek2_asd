/**
 * Linked list buatan sendiri (tanpa java.util).
 * Elemen baru ditambahkan di BELAKANG, jadi urutan input terjaga.
 * Dibuat oleh: fathan
 */
public class DaftarTaut<T> {

    /** Satu simpul di dalam linked list. */
    public static class Simpul<T> {
        public final T data;
        public Simpul<T> berikut;

        Simpul(T data) {
            this.data = data;
            this.berikut = null;
        }
    }

    private Simpul<T> kepala;
    private Simpul<T> ekor;
    private int ukuran;

    public DaftarTaut() {
        kepala = null;
        ekor = null;
        ukuran = 0;
    }

    /** Tambah di belakang. O(1). */
    public void tambah(T data) {
        Simpul<T> baru = new Simpul<>(data);
        if (kepala == null) {
            kepala = baru;
            ekor = baru;
        } else {
            ekor.berikut = baru;
            ekor = baru;
        }
        ukuran++;
    }

    /** Ambil elemen ke-i (mulai dari 0). O(n). */
    public T get(int indeks) {
        if (indeks < 0 || indeks >= ukuran) {
            throw new IndexOutOfBoundsException("Indeks " + indeks + ", ukuran " + ukuran);
        }
        Simpul<T> p = kepala;
        for (int i = 0; i < indeks; i++) {
            p = p.berikut;
        }
        return p.data;
    }

    public int ukuran()      { return ukuran; }
    public boolean kosong()  { return ukuran == 0; }

    /**
     * Simpul pertama, untuk iterasi cepat O(n):
     *   for (DaftarTaut.Simpul<X> p = daftar.getKepala(); p != null; p = p.berikut) { p.data ... }
     */
    public Simpul<T> getKepala() { return kepala; }
}
