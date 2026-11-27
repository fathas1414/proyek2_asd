/**
 * Uji cepat bagian Ilham. Jalankan: java UjiGraf
 * Semua baris harus berawalan "OK".
 */
public class UjiGraf {
    static int gagal = 0;

    static void cek(String nama, boolean kondisi) {
        System.out.println((kondisi ? "OK    " : "GAGAL ") + nama);
        if (!kondisi) gagal++;
    }

    public static void main(String[] args) {
        GrafPrasyarat g = new GrafPrasyarat();

        cek("tambah PDAS",  g.tambahMK(new Matakuliah("PDAS", "Pemrograman Dasar", 5, 1, "Ganjil")));
        cek("tambah PLAN",  g.tambahMK(new Matakuliah("PLAN", "Pemrograman Lanjut", 5, 2, "Genap")));
        cek("tambah ASD",   g.tambahMK(new Matakuliah("ASD", "Algoritma dan Struktur Data", 3, 3, "Ganjil")));
        cek("tambah SKR",   g.tambahMK(new Matakuliah("SKR", "Skripsi", 6, 8, "Keduanya")));
        cek("MK ganda ditolak", !g.tambahMK(new Matakuliah("PDAS", "Dobel", 1, 1, "Ganjil")));
        cek("jumlah MK = 4", g.jumlahMK() == 4);

        cek("PDAS -> PLAN",  g.tambahPrasyarat("PDAS", "PLAN"));
        cek("PLAN -> ASD",   g.tambahPrasyarat("PLAN", "ASD"));
        cek("sisi ganda ditolak", !g.tambahPrasyarat("PDAS", "PLAN"));
        cek("kode tak dikenal ditolak", !g.tambahPrasyarat("XXX", "PLAN"));

        cek("getMK PLAN", g.getMK("plan") != null && g.getMK("plan").getNama().equals("Pemrograman Lanjut"));
        cek("getMK tak ada = null", g.getMK("ZZZ") == null);

        DaftarTaut<Matakuliah> pra = g.getDaftarPrasyarat("PLAN");
        cek("prasyarat PLAN = [PDAS]", pra.ukuran() == 1 && pra.get(0).getKode().equals("PDAS"));
        cek("prasyarat PDAS kosong", g.getDaftarPrasyarat("PDAS").kosong());
        cek("lanjutan PDAS = [PLAN]", g.getDaftarLanjutan("PDAS").get(0).getKode().equals("PLAN"));

        cek("PLAN ditawarkan genap",  g.getMK("PLAN").ditawarkan("genap"));
        cek("PLAN tidak ganjil",      !g.getMK("PLAN").ditawarkan("ganjil"));
        cek("SKR ditawarkan keduanya", g.getMK("SKR").ditawarkan("ganjil") && g.getMK("SKR").ditawarkan("genap"));

        cek("toString format soal", g.getMK("PLAN").toString().equals("PLAN Pemrograman Lanjut 5 SKS"));
        cek("urutan semua MK", g.getSemuaMK().get(0).getKode().equals("PDAS") && g.getSemuaMK().get(3).getKode().equals("SKR"));

        System.out.println(gagal == 0 ? "\nSEMUA LULUS" : "\n" + gagal + " TES GAGAL");
    }
}