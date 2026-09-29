import java.util.regex.Pattern;

/**
 * Class Kendaraan (Superclass)
 * Merepresentasikan abstraksi umum dari seluruh armada kendaraan dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Generalization: Menyediakan fondasi atribut umum (plat nomor, merk, model, tahun, tarif sewa, status ketersediaan)
 *    yang diwarisi oleh Subclass Mobil, Motor, dan Minibus.
 * 2. Composition (◆): Memiliki komponen objek Mesin internal yang dirakit langsung dalam konstruktor.
 * 3. Polymorphic Base Reference: Menjadi tipe reference universal (Superclass Reference) untuk Upcasting
 *    dan Heterogeneous Polymorphic Collection.
 * 4. Method Overriding Base: Mendeklarasikan method virtual {@code tampilkanSpesifikasi()}, {@code hitungEstimasiBiaya()},
 *    {@code lakukanServis()}, dan {@code getTipeKendaraan()} yang di-override dengan perilaku dinamis oleh subclass.
 * 5. Method Overloading: Menyediakan variasi signature {@code hitungEstimasiBiaya(...)} untuk berbagai skenario bisnis.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Kendaraan {
    // Atribut Umum Kendaraan (private)
    private String nomorPlat;
    private String merk;
    private String model;
    private int tahunProduksi;
    private double tarifPerHari;
    private boolean statusTersedia;
    private Mesin mesin; // Relasi Komposisi (◆)
    private int totalKaliDisewa;

    // Regex Plat Nomor Indonesia (contoh: L 1234 AB, M 9876 XYZ, B 8888 CD)
    private static final Pattern REGEX_PLAT = Pattern.compile("^[A-Z]{1,2}\\s[0-9]{1,4}\\s[A-Z]{1,3}$");

    /**
     * Default Constructor (Tanpa Argumen)
     * Mendemonstrasikan alur Constructor Chaining default.
     */
    public Kendaraan() {
        this.nomorPlat = "M 0000 XX";
        this.merk = "Standar";
        this.model = "Base Model";
        this.tahunProduksi = 2024;
        this.tarifPerHari = 100000.0;
        this.statusTersedia = true;
        this.mesin = new Mesin("RANGKA-BASE-000", 1000, "Bensin", "Manual");
        this.totalKaliDisewa = 0;
        System.out.println("  [CONSTRUCTOR CHAIN] -> Superclass Kendaraan() default dipanggil.");
    }

    /**
     * Parameterized Constructor Superclass Kendaraan
     *
     * @param nomorPlat       Nomor registrasi kendaraan (misal "M 1234 AB")
     * @param merk           Nama produsen kendaraan (misal "Toyota", "Honda")
     * @param model          Model/Seri kendaraan (misal "Avanza Veloz", "PCX 160")
     * @param tahunProduksi  Tahun perakitan unit (2000 - 2026)
     * @param tarifPerHari   Tarif sewa harian (Rp)
     * @param nomorRangka    Nomor rangka mesin internal
     * @param kapasitasCC    Kapasitas silinder mesin (CC)
     * @param tipeBahanBakar Jenis bahan bakar
     * @param transmisi      Jenis transmisi
     */
    public Kendaraan(String nomorPlat, String merk, String model, int tahunProduksi, double tarifPerHari,
                     String nomorRangka, int kapasitasCC, String tipeBahanBakar, String transmisi) {
        setNomorPlat(nomorPlat);
        setMerk(merk);
        setModel(model);
        setTahunProduksi(tahunProduksi);
        setTarifPerHari(tarifPerHari);
        this.statusTersedia = true;
        this.totalKaliDisewa = 0;

        // Inisialisasi Komposisi Internal Mesin
        this.mesin = new Mesin(nomorRangka, kapasitasCC, tipeBahanBakar, transmisi);

        System.out.println("  [CONSTRUCTOR CHAIN] -> Superclass Kendaraan(Plat=" + this.nomorPlat + ", " + this.merk + " " + this.model + ") siap dioperasikan.");
    }

    // ==========================================
    // GETTER & SETTER (ENCAPSULATION DEFENSIVE)
    // ==========================================

    public String getNomorPlat() {
        return nomorPlat;
    }

    public void setNomorPlat(String nomorPlat) {
        if (nomorPlat == null || !REGEX_PLAT.matcher(nomorPlat.trim().toUpperCase()).matches()) {
            throw new IllegalArgumentException("[Defensive Error] Format Plat Nomor tidak valid! Gunakan format standar Indonesia (Contoh: 'M 1234 AB' atau 'L 567 XYZ').");
        }
        this.nomorPlat = nomorPlat.trim().toUpperCase();
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        if (merk == null || merk.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Merk kendaraan tidak boleh kosong!");
        }
        this.merk = merk.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Model kendaraan tidak boleh kosong!");
        }
        this.model = model.trim();
    }

    public int getTahunProduksi() {
        return tahunProduksi;
    }

    public void setTahunProduksi(int tahunProduksi) {
        if (tahunProduksi < 2000 || tahunProduksi > 2026) {
            throw new IllegalArgumentException("[Defensive Error] Tahun produksi kendaraan harus dalam rentang 2000 s/d 2026!");
        }
        this.tahunProduksi = tahunProduksi;
    }

    public double getTarifPerHari() {
        return tarifPerHari;
    }

    public void setTarifPerHari(double tarifPerHari) {
        if (tarifPerHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Tarif sewa per hari harus > Rp 0!");
        }
        this.tarifPerHari = tarifPerHari;
    }

    public boolean isStatusTersedia() {
        return statusTersedia;
    }

    public void setStatusKetersediaan(boolean statusTersedia) {
        this.statusTersedia = statusTersedia;
    }

    public Mesin getMesin() {
        return mesin;
    }

    public int getTotalKaliDisewa() {
        return totalKaliDisewa;
    }

    // ==========================================
    // DOMAIN BEHAVIOR METHODS
    // ==========================================

    public void catatPenyewaan(int kmBertambah) {
        this.totalKaliDisewa++;
        this.statusTersedia = false;
        this.mesin.tambahKilometer(kmBertambah);
    }

    public void selesaikanPenyewaan() {
        this.statusTersedia = true;
    }

    public void servisMesinKendaraan() {
        this.mesin.servisTuneUp();
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Menghitung estimasi biaya sewa standar berdasarkan durasi hari.
     * Method ini dirancang untuk di-override oleh Subclass.
     *
     * @param durasiHari Durasi hari penyewaan
     * @return Total biaya estimasi sewa (Rp)
     */
    public double hitungEstimasiBiaya(int durasiHari) {
        if (durasiHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Durasi sewa hari harus > 0!");
        }
        return this.tarifPerHari * durasiHari;
    }

    /**
     * Overload 2: Menghitung estimasi biaya sewa dengan potongan diskon kupon promo.
     *
     * @param durasiHari   Durasi hari penyewaan
     * @param diskonPersen Persentase diskon (misal: 0.10 untuk 10%)
     * @return Total biaya setelah diskon promo
     */
    public double hitungEstimasiBiaya(int durasiHari, double diskonPersen) {
        double biayaDasar = hitungEstimasiBiaya(durasiHari);
        if (diskonPersen < 0.0 || diskonPersen > 0.50) {
            throw new IllegalArgumentException("[Defensive Error] Diskon promo harus dalam rentang 0% - 50% (0.0 s/d 0.50)!");
        }
        double potongan = biayaDasar * diskonPersen;
        return biayaDasar - potongan;
    }

    /**
     * Overload 3: Menghitung estimasi biaya sewa dengan tambahan opsi supir profesional.
     *
     * @param durasiHari         Durasi hari penyewaan
     * @param includeLayananSupir Status apakah menyewa jasa supir
     * @param tarifSupirPerHari  Biaya supir per hari (Rp)
     * @return Total biaya sewa unit + jasa supir
     */
    public double hitungEstimasiBiaya(int durasiHari, boolean includeLayananSupir, double tarifSupirPerHari) {
        double biayaDasar = hitungEstimasiBiaya(durasiHari);
        if (includeLayananSupir) {
            if (tarifSupirPerHari < 0) {
                throw new IllegalArgumentException("[Defensive Error] Tarif jasa supir tidak boleh negatif!");
            }
            return biayaDasar + (tarifSupirPerHari * durasiHari);
        }
        return biayaDasar;
    }

    // ==========================================
    // POLYMORPHIC BEHAVIOR METHODS (MODUL 6)
    // ==========================================

    /**
     * Menampilkan spesifikasi armada kendaraan ke konsol.
     * Method virtual yang dioverride oleh seluruh subclass untuk mendemonstrasikan Dynamic Binding.
     */
    public void tampilkanSpesifikasi() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" [SPESIFIKASI ARMADA - SUPERCLASS KENDARAAN]");
        System.out.println(" Kategori Unit : " + getTipeKendaraan());
        System.out.println(" Plat Nomor    : " + nomorPlat);
        System.out.println(" Merk & Model  : " + merk + " " + model + " (" + tahunProduksi + ")");
        System.out.println(" Tarif / Hari  : Rp " + String.format("%,.2f", tarifPerHari));
        System.out.println(" Ketersediaan  : " + (statusTersedia ? "READY FOR RENT" : "SEDANG DISEWA"));
        System.out.println(" Total Sewa    : " + totalKaliDisewa + " kali");
        this.mesin.tampilkanStatusMesin();
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Mengembalikan klasifikasi tipe armada.
     *
     * @return String kategori kendaraan
     */
    public String getTipeKendaraan() {
        return "Kendaraan Armada Standar";
    }

    /**
     * Melakukan prosedur pemeliharaan teknis / servis berkala armada.
     * Dioverride oleh masing-masing subclass sesuai karakteristik fisik kendaraan.
     */
    public void lakukanServis() {
        System.out.println("  [SERVIS UMUM] Menjalankan inspeksi berkala pada unit " + nomorPlat + "...");
        servisMesinKendaraan();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s (%d) | Tipe: %s | Rp %,.0f/hari [%s]",
                nomorPlat, merk, model, tahunProduksi, getTipeKendaraan(), tarifPerHari, (statusTersedia ? "TERSEDIA" : "DISEWA"));
    }
}
