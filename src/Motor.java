/**
 * Class Motor (Subclass dari Kendaraan)
 * Merepresentasikan armada sepeda motor / kendaraan roda dua dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Inheritance (extends): Mewarisi atribut armada dasar dan komponen Mesin dari Superclass Kendaraan.
 * 2. Keyword super: Memanggil constructor superclass via super(...) pada baris pertama.
 * 3. Method Overriding:
 *    - {@code tampilkanSpesifikasi()}: Menampilkan spesifikasi roda dua, bagasi helm, jas hujan, dan phone holder.
 *    - {@code hitungEstimasiBiaya(int durasiHari)}: Mengkalkulasi tarif sewa motor (biaya unit + insentif city tour).
 *    - {@code getTipeKendaraan()}: Mengembalikan tipe spesifik armada sepeda motor.
 *    - {@code lakukanServis()}: Melakukan servis berkala CVT, oli gardan, v-belt, dan sistem rem cakram.
 * 4. Method Overloading:
 *    - {@code tambahFasilitas(String namaFasilitas)} vs {@code tambahFasilitas(String namaFasilitas, int kuantitas)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Motor extends Kendaraan {
    // Atribut Khusus Subclass Motor
    private String tipeMotor; // "Matic Maxi", "Sport", "Bebek", "Trail"
    private int kapasitasBagasiHelm; // Kapasitas bagasi (Liter)
    private int jumlahHelmGratis;
    private boolean adaJasHujan;
    private boolean adaPhoneHolder;

    /**
     * Parameterized Constructor Subclass Motor
     *
     * @param nomorPlat           Plat nomor motor
     * @param merk               Merk pabrikan motor
     * @param model              Model/Seri motor
     * @param tahunProduksi      Tahun perakitan
     * @param tarifPerHari       Tarif harian dasar
     * @param nomorRangka        Nomor rangka mesin
     * @param kapasitasCC        Kapasitas silinder mesin (CC)
     * @param tipeBahanBakar     Jenis bahan bakar
     * @param transmisi          Transmisi (Matic / Manual / Semi-Auto)
     * @param tipeMotor          Kategori motor ("Matic Maxi", "Sport")
     * @param kapasitasBagasiHelm Kapasitas bagasi (Liter)
     * @param jumlahHelmGratis   Jumlah helm SNI yang disediakan
     * @param adaJasHujan        Fasilitas jas hujan ganda
     * @param adaPhoneHolder     Fasilitas holder HP stang
     */
    public Motor(String nomorPlat, String merk, String model, int tahunProduksi, double tarifPerHari,
                 String nomorRangka, int kapasitasCC, String tipeBahanBakar, String transmisi,
                 String tipeMotor, int kapasitasBagasiHelm, int jumlahHelmGratis,
                 boolean adaJasHujan, boolean adaPhoneHolder) {
        // Baris pertama: Memanggil constructor Superclass Kendaraan
        super(nomorPlat, merk, model, tahunProduksi, tarifPerHari, nomorRangka, kapasitasCC, tipeBahanBakar, transmisi);

        this.tipeMotor = (tipeMotor != null && !tipeMotor.trim().isEmpty()) ? tipeMotor.trim() : "Matic Standar";
        if (kapasitasBagasiHelm < 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas bagasi motor tidak boleh negatif!");
        }
        this.kapasitasBagasiHelm = kapasitasBagasiHelm;

        if (jumlahHelmGratis < 0) {
            throw new IllegalArgumentException("[Defensive Error] Jumlah helm gratis tidak boleh negatif!");
        }
        this.jumlahHelmGratis = jumlahHelmGratis;
        this.adaJasHujan = adaJasHujan;
        this.adaPhoneHolder = adaPhoneHolder;

        System.out.println("  [CONSTRUCTOR CHAIN] -> Subclass Motor(" + nomorPlat + " - " + this.tipeMotor + ") berhasil diinstansiasi.");
    }

    // ==========================================
    // GETTER & SETTER KHUSUS MOTOR
    // ==========================================

    public String getTipeMotor() {
        return tipeMotor;
    }

    public void setTipeMotor(String tipeMotor) {
        if (tipeMotor == null || tipeMotor.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Tipe motor tidak boleh kosong!");
        }
        this.tipeMotor = tipeMotor.trim();
    }

    public int getKapasitasBagasiHelm() {
        return kapasitasBagasiHelm;
    }

    public void setKapasitasBagasiHelm(int kapasitasBagasiHelm) {
        if (kapasitasBagasiHelm < 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas bagasi tidak boleh negatif!");
        }
        this.kapasitasBagasiHelm = kapasitasBagasiHelm;
    }

    public int getJumlahHelmGratis() {
        return jumlahHelmGratis;
    }

    public void setJumlahHelmGratis(int jumlahHelmGratis) {
        if (jumlahHelmGratis < 0) {
            throw new IllegalArgumentException("[Defensive Error] Jumlah helm gratis tidak boleh negatif!");
        }
        this.jumlahHelmGratis = jumlahHelmGratis;
    }

    public boolean isAdaJasHujan() {
        return adaJasHujan;
    }

    public void setAdaJasHujan(boolean adaJasHujan) {
        this.adaJasHujan = adaJasHujan;
    }

    public boolean isAdaPhoneHolder() {
        return adaPhoneHolder;
    }

    public void setAdaPhoneHolder(boolean adaPhoneHolder) {
        this.adaPhoneHolder = adaPhoneHolder;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Menambahkan perlengkapan riding tunggal ke fasilitas motor.
     *
     * @param namaFasilitas Nama fasilitas perlengkapan (misal: "Gembok Cakram Alarm", "Sarung Tangan Riding")
     */
    public void tambahFasilitas(String namaFasilitas) {
        if (namaFasilitas == null || namaFasilitas.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Nama fasilitas motor tidak boleh kosong!");
        }
        System.out.println("  [FASILITAS MOTOR] Menambahkan fasilitas: " + namaFasilitas.trim() + " pada unit " + getNomorPlat());
    }

    /**
     * Overload 2: Menambahkan perlengkapan riding berkuantitas (misal tambahan helm SNI).
     *
     * @param namaFasilitas      Nama fasilitas
     * @param kuantitasTambahan  Jumlah kuantitas yang ditambahkan
     */
    public void tambahFasilitas(String namaFasilitas, int kuantitasTambahan) {
        if (kuantitasTambahan <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kuantitas tambahan fasilitas harus > 0!");
        }
        if ("Helm SNI".equalsIgnoreCase(namaFasilitas) || "Helm".equalsIgnoreCase(namaFasilitas)) {
            this.jumlahHelmGratis += kuantitasTambahan;
            System.out.println("  [FASILITAS MOTOR] Kuantitas Helm SNI diupdate menjadi: " + this.jumlahHelmGratis + " unit.");
        } else {
            System.out.println("  [FASILITAS MOTOR] Menambahkan " + kuantitasTambahan + "x " + namaFasilitas + " pada unit " + getNomorPlat());
        }
    }

    // ==========================================
    // METHOD OVERRIDING (MODUL 6 - BAGIAN B & F)
    // ==========================================

    /**
     * Meng-override {@code hitungEstimasiBiaya(int durasiHari)} dari Superclass Kendaraan.
     * Mengimplementasikan formula bisnis sewa sepeda motor:
     * - Tarif dasar sewa per hari.
     * - Bonus diskon flat Rp 15.000 untuk paket sewa city tour 3 hari ke atas.
     *
     * @param durasiHari Durasi hari penyewaan motor
     * @return Total biaya sewa sepeda motor
     */
    @Override
    public double hitungEstimasiBiaya(int durasiHari) {
        if (durasiHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Durasi sewa motor harus > 0 hari!");
        }
        double totalBiaya = getTarifPerHari() * durasiHari;

        // Diskon flat city tour Rp 15.000 untuk sewa >= 3 hari
        if (durasiHari >= 3) {
            totalBiaya = Math.max(0, totalBiaya - 15000.0);
        }
        return totalBiaya;
    }

    /**
     * Meng-override {@code tampilkanSpesifikasi()} dari Superclass Kendaraan.
     * Menampilkan spesifikasi roda dua, bagasi, helm gratis, jas hujan, dan phone holder.
     */
    @Override
    public void tampilkanSpesifikasi() {
        System.out.println("================================================================================");
        System.out.println(" [SPESIFIKASI SEPEDA MOTOR - DYNAMIC BINDING]");
        System.out.println(" Plat Nomor     : " + getNomorPlat());
        System.out.println(" Kategori Armada: " + getTipeKendaraan());
        System.out.println(" Unit Kendaraan : " + getMerk() + " " + getModel() + " (" + getTahunProduksi() + ")");
        System.out.println(" Kapasitas Bagasi: " + kapasitasBagasiHelm + " Liter");
        System.out.println(" Fasilitas Helm : " + jumlahHelmGratis + " Pcs Helm SNI Free");
        System.out.println(" Jas Hujan Ganda: " + (adaJasHujan ? "TERSEDIA (2 Set)" : "TIDAK TERSEDIA"));
        System.out.println(" Phone Holder   : " + (adaPhoneHolder ? "TERPASANG DI STANG" : "TIDAK ADA"));
        System.out.println(" Tarif Dasar/Hr : Rp " + String.format("%,.2f", getTarifPerHari()));
        System.out.println(" Estimasi 3 Hari: Rp " + String.format("%,.2f", hitungEstimasiBiaya(3)) + " (Promo City Tour Aktif)");
        System.out.println(" Status Unit    : " + (isStatusTersedia() ? "TERSEDIA DI GARASI" : "SEDANG DISEWA"));
        getMesin().tampilkanStatusMesin();
        System.out.println("================================================================================");
    }

    /**
     * Meng-override {@code getTipeKendaraan()} dari Superclass Kendaraan.
     *
     * @return String kategori sepeda motor
     */
    @Override
    public String getTipeKendaraan() {
        return "Sepeda Motor (" + tipeMotor + ")";
    }

    /**
     * Meng-override {@code lakukanServis()} dari Superclass Kendaraan.
     * Melakukan servis spesifik motor: tune-up mesin + kuras oli gardan/CVT + pembersihan rantai & kampas rem.
     */
    @Override
    public void lakukanServis() {
        System.out.println("  [SERVIS MOTOR] Memulai inspeksi unit motor " + getNomorPlat() + " (" + getMerk() + " " + getModel() + "):");
        servisMesinKendaraan();
        System.out.println("   └─ [INSPEKSI TRANSMISI] Penggantian Oli Mesin, Oli Gardan & Pembersihan Roller CVT selesai.");
        System.out.println("   └─ [INSPEKSI KESELAMATAN] Pengecekan Kampas Rem Cakram & Kalibrasi Stang Kemudi.");
    }

    /**
     * Backward-compatible alias untuk praktikum terdahulu.
     */
    public void tampilkanDetailMotor() {
        tampilkanSpesifikasi();
    }
}
