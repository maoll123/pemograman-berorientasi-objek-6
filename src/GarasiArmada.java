import java.util.ArrayList;

/**
 * Class GarasiArmada (Agregasi Polimorfis Pool Kendaraan)
 * Merepresentasikan pool sentral penyimpanan dan manajemen armada rental.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Aggregation (◇): Mengoleksi objek-objek Kendaraan yang memiliki siklus hidup independen.
 * 2. Heterogeneous Polymorphic Collection: Menampung berbagai turunan Kendaraan (Mobil, Motor, Minibus)
 *    secara seragam dalam struktur data {@code ArrayList<Kendaraan>}.
 * 3. Dynamic Binding Iteration: Mengeksekusi method {@code tampilkanSpesifikasi()}, {@code hitungEstimasiBiaya()},
 *    dan {@code lakukanServis()} secara polimorfis pada seluruh armada garasi.
 * 4. Method Overloading: Menyediakan variasi pencarian armada berdasarkan plat, merk/model, budget tarif, dan status.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class GarasiArmada {
    private String idGarasi;
    private String namaGarasi;
    private String lokasi;
    private int kapasitasMaksimum;
    private ArrayList<Kendaraan> daftarKendaraan; // Relasi Agregasi Polimorfis (◇)

    public GarasiArmada(String idGarasi, String namaGarasi, String lokasi, int kapasitasMaksimum) {
        if (idGarasi == null || idGarasi.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] ID Garasi tidak boleh kosong!");
        }
        if (kapasitasMaksimum <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas maksimum garasi harus > 0 unit!");
        }
        this.idGarasi = idGarasi.trim().toUpperCase();
        this.namaGarasi = (namaGarasi != null && !namaGarasi.trim().isEmpty()) ? namaGarasi.trim() : "Garasi Utama";
        this.lokasi = (lokasi != null && !lokasi.trim().isEmpty()) ? lokasi.trim() : "Pool Pusat";
        this.kapasitasMaksimum = kapasitasMaksimum;
        this.daftarKendaraan = new ArrayList<>();
    }

    public String getIdGarasi() {
        return idGarasi;
    }

    public String getNamaGarasi() {
        return namaGarasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public int getKapasitasMaksimum() {
        return kapasitasMaksimum;
    }

    public int getJumlahUnitTersimpan() {
        return daftarKendaraan.size();
    }

    public ArrayList<Kendaraan> getDaftarKendaraan() {
        return new ArrayList<>(daftarKendaraan); // Defensive copying
    }

    public boolean tambahKendaraan(Kendaraan unit) {
        if (unit == null) {
            throw new IllegalArgumentException("[Defensive Error] Objek kendaraan tidak boleh null!");
        }
        if (daftarKendaraan.size() >= kapasitasMaksimum) {
            System.err.println("[Garasi Penuh] Tidak dapat menambah unit " + unit.getNomorPlat()
                    + " karena garasi telah mencapai batas (" + kapasitasMaksimum + " unit)!");
            return false;
        }
        for (Kendaraan k : daftarKendaraan) {
            if (k.getNomorPlat().equalsIgnoreCase(unit.getNomorPlat())) {
                System.err.println("[Duplikasi Plat] Kendaraan dengan nomor plat " + unit.getNomorPlat() + " sudah ada di dalam garasi!");
                return false;
            }
        }
        daftarKendaraan.add(unit);
        System.out.println("  [GARASI AGGREGATION] +1 Unit Polimorfis Berhasil Ditambahkan: " + unit.getNomorPlat()
                + " (" + unit.getTipeKendaraan() + ")");
        return true;
    }

    public boolean hapusKendaraan(String nomorPlat) {
        if (nomorPlat == null || nomorPlat.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            if (daftarKendaraan.get(i).getNomorPlat().equalsIgnoreCase(nomorPlat.trim())) {
                Kendaraan dihapus = daftarKendaraan.remove(i);
                System.out.println("  [GARASI AGGREGATION] Unit " + dihapus.getNomorPlat() + " dikeluarkan dari garasi.");
                return true;
            }
        }
        return false;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Mencari satu unit kendaraan spesifik berdasarkan Nomor Plat registrasi.
     *
     * @param nomorPlat Plat nomor target (misal: "M 1234 AB")
     * @return Objek Kendaraan jika ditemukan, atau null
     */
    public Kendaraan cariKendaraan(String nomorPlat) {
        if (nomorPlat == null || nomorPlat.trim().isEmpty()) {
            return null;
        }
        for (Kendaraan k : daftarKendaraan) {
            if (k.getNomorPlat().equalsIgnoreCase(nomorPlat.trim())) {
                return k;
            }
        }
        return null;
    }

    /**
     * Overload 2: Mencari daftar armada berdasarkan kriteria Merk dan Model.
     *
     * @param merk  Merk pabrikan (misal "Toyota")
     * @param model Model kendaraan (misal "Avanza")
     * @return List kendaraan yang cocok
     */
    public ArrayList<Kendaraan> cariKendaraan(String merk, String model) {
        ArrayList<Kendaraan> hasil = new ArrayList<>();
        if (merk == null || model == null) return hasil;

        for (Kendaraan k : daftarKendaraan) {
            if (k.getMerk().equalsIgnoreCase(merk.trim()) && k.getModel().equalsIgnoreCase(model.trim())) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    /**
     * Overload 3: Mencari daftar armada yang memiliki tarif sewa <= budget batas maksimal.
     *
     * @param maxTarif Batas tarif sewa harian tertinggi
     * @return List armada terjangkau sesuai budget
     */
    public ArrayList<Kendaraan> cariKendaraan(double maxTarif) {
        ArrayList<Kendaraan> hasil = new ArrayList<>();
        for (Kendaraan k : daftarKendaraan) {
            if (k.getTarifPerHari() <= maxTarif) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    /**
     * Overload 4: Memfilter daftar armada berdasarkan status ketersediaan siap sewa.
     *
     * @param hanyaTersedia True jika hanya menampilkan unit yang ready
     * @return List kendaraan sesuai status
     */
    public ArrayList<Kendaraan> cariKendaraan(boolean hanyaTersedia) {
        ArrayList<Kendaraan> hasil = new ArrayList<>();
        for (Kendaraan k : daftarKendaraan) {
            if (k.isStatusTersedia() == hanyaTersedia) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    // ==========================================
    // POLYMORPHIC OPERATIONS (MODUL 6 - BAGIAN E & F)
    // ==========================================

    /**
     * Menampilkan seluruh isi pool garasi menggunakan perulangan polimorfis.
     * Setiap objek (Mobil, Motor, Minibus) akan mengeksekusi method {@code tampilkanSpesifikasi()} masing-masing (Dynamic Binding).
     */
    public void tampilkanDaftarArmada() {
        System.out.println("================================================================================");
        System.out.println(" DAFTAR HETEROGENEOUS FLEET POOL: " + namaGarasi.toUpperCase() + " (" + lokasi + ")");
        System.out.println(" Okupansi: " + daftarKendaraan.size() + " / " + kapasitasMaksimum + " Unit");
        System.out.println("================================================================================");
        if (daftarKendaraan.isEmpty()) {
            System.out.println(" (Garasi saat ini kosong)");
        } else {
            int no = 1;
            for (Kendaraan k : daftarKendaraan) {
                System.out.printf(" %2d. %s\n", no++, k.toString());
            }
        }
        System.out.println("================================================================================");
    }

    /**
     * Menjalankan prosedur servis massal pada seluruh armada dalam garasi.
     * Menunjukkan pembuktian nyata Dynamic Method Dispatch saat runtime.
     */
    public void lakukanServisMasal() {
        System.out.println("\n>>> [BATCH OPERATION] Memulai Servis Rutin Masal untuk Seluruh Armada Garasi <<<");
        for (Kendaraan k : daftarKendaraan) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("Unit Plat: " + k.getNomorPlat() + " (Tipe Polimorfis: " + k.getClass().getSimpleName() + ")");
            // Dynamic Binding: Memanggil method lakukanServis() sesuai subclass konkret
            k.lakukanServis();
        }
        System.out.println("--------------------------------------------------");
        System.out.println(">>> Seluruh Armada di Garasi Telah Selesai Diservis Secara Polimorfis <<<\n");
    }

    /**
     * Menghitung total estimasi omset sewa seluruh armada untuk durasi hari tertentu.
     *
     * @param durasiHari Durasi hari estimasi
     * @return Total nominal omset sewa polimorfis
     */
    public double hitungTotalEstimasiPendapatan(int durasiHari) {
        double total = 0.0;
        for (Kendaraan k : daftarKendaraan) {
            // Dynamic Binding: Memanggil kalkulasi tarif spesifik subclass
            total += k.hitungEstimasiBiaya(durasiHari);
        }
        return total;
    }

    public void kosongkanGarasi() {
        this.daftarKendaraan.clear();
        System.out.println("  [GARASI AGGREGATION] Seluruh armada dalam garasi telah dikosongkan.");
    }
}
