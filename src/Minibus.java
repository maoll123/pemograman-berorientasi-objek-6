/**
 * Class Minibus (Subclass dari Kendaraan)
 * Merepresentasikan armada travel / kendaraan komersial berkapasitas besar dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Inheritance (extends): Menjadi Subclass ketiga dari Kendaraan (memperkaya Polymorphic Collection).
 * 2. Keyword super: Memanggil constructor superclass via super(...) pada baris pertama.
 * 3. Method Overriding:
 *    - {@code tampilkanSpesifikasi()}: Menampilkan kapasitas rombongan, sistem audio karaoke, dan bagasi koper besar.
 *    - {@code hitungEstimasiBiaya(int durasiHari)}: Mengkalkulasi tarif minibus (biaya unit + surcharge asuransi kursi rombongan).
 *    - {@code getTipeKendaraan()}: Mengembalikan tipe armada minibus komersial.
 *    - {@code lakukanServis()}: Melakukan servis mesin diesel heavy-duty, rem angin, dan suspensi ganda.
 * 4. Method Overloading:
 *    - {@code konfigurasiKursi(int totalKursi)} vs {@code konfigurasiKursi(int totalKursi, boolean ruangBagasiExtra)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Minibus extends Kendaraan {
    // Atribut Khusus Subclass Minibus
    private int kapasitasKursi;
    private int kapasitasBagasiKoper;
    private boolean fasilitasKaraoke;
    private boolean wajibSupir;

    /**
     * Parameterized Constructor Subclass Minibus
     *
     * @param nomorPlat           Plat nomor minibus
     * @param merk               Merk pabrikan (misal: "Toyota", "Isuzu")
     * @param model              Model/Seri (misal: "HiAce Commuter", "Elf Long")
     * @param tahunProduksi      Tahun perakitan
     * @param tarifPerHari       Tarif harian dasar
     * @param nomorRangka        Nomor rangka mesin
     * @param kapasitasCC        Kapasitas silinder mesin diesel (CC)
     * @param tipeBahanBakar     Jenis bahan bakar (Diesel / Solar)
     * @param transmisi          Transmisi (Manual / Automatic)
     * @param kapasitasKursi     Kapasitas tempat duduk (misal 14 - 19 kursi)
     * @param kapasitasBagasiKoper Kapasitas koper bagasi belakang (buah koper)
     * @param fasilitasKaraoke   Ketersediaan mic & Android TV karaoke
     * @param wajibSupir         Ketentuan sewa wajib menggunakan supir resmi
     */
    public Minibus(String nomorPlat, String merk, String model, int tahunProduksi, double tarifPerHari,
                   String nomorRangka, int kapasitasCC, String tipeBahanBakar, String transmisi,
                   int kapasitasKursi, int kapasitasBagasiKoper, boolean fasilitasKaraoke, boolean wajibSupir) {
        // Baris pertama: Memanggil constructor Superclass Kendaraan
        super(nomorPlat, merk, model, tahunProduksi, tarifPerHari, nomorRangka, kapasitasCC, tipeBahanBakar, transmisi);

        if (kapasitasKursi < 8) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas kursi minibus minimal 8 tempat duduk!");
        }
        this.kapasitasKursi = kapasitasKursi;

        if (kapasitasBagasiKoper < 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas bagasi koper tidak boleh negatif!");
        }
        this.kapasitasBagasiKoper = kapasitasBagasiKoper;
        this.fasilitasKaraoke = fasilitasKaraoke;
        this.wajibSupir = wajibSupir;

        System.out.println("  [CONSTRUCTOR CHAIN] -> Subclass Minibus(" + nomorPlat + " - " + kapasitasKursi + " Kursi) berhasil diinstansiasi.");
    }

    // ==========================================
    // GETTER & SETTER KHUSUS MINIBUS
    // ==========================================

    public int getKapasitasKursi() {
        return kapasitasKursi;
    }

    public void setKapasitasKursi(int kapasitasKursi) {
        if (kapasitasKursi < 8) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas kursi minibus minimal 8 tempat duduk!");
        }
        this.kapasitasKursi = kapasitasKursi;
    }

    public int getKapasitasBagasiKoper() {
        return kapasitasBagasiKoper;
    }

    public void setKapasitasBagasiKoper(int kapasitasBagasiKoper) {
        if (kapasitasBagasiKoper < 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas bagasi koper tidak boleh negatif!");
        }
        this.kapasitasBagasiKoper = kapasitasBagasiKoper;
    }

    public boolean isFasilitasKaraoke() {
        return fasilitasKaraoke;
    }

    public void setFasilitasKaraoke(boolean fasilitasKaraoke) {
        this.fasilitasKaraoke = fasilitasKaraoke;
    }

    public boolean isWajibSupir() {
        return wajibSupir;
    }

    public void setWajibSupir(boolean wajibSupir) {
        this.wajibSupir = wajibSupir;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Melakukan rekonfigurasi jumlah kursi penumpang standar.
     *
     * @param totalKursi Jumlah kursi baru
     */
    public void konfigurasiKursi(int totalKursi) {
        setKapasitasKursi(totalKursi);
        System.out.println("  [MINIBUS LAYOUT] Rekonfigurasi kapasitas kursi: " + this.kapasitasKursi + " kursi.");
    }

    /**
     * Overload 2: Melakukan rekonfigurasi kursi dengan penyesuaian ruang bagasi koper ekstra (melipat baris belakang).
     *
     * @param totalKursi         Jumlah kursi baru
     * @param ruangBagasiExtra   Apakah baris belakang dilipat untuk memperbesar koper
     */
    public void konfigurasiKursi(int totalKursi, boolean ruangBagasiExtra) {
        setKapasitasKursi(totalKursi);
        if (ruangBagasiExtra) {
            this.kapasitasBagasiKoper += 6; // Menambah space 6 koper tambahan
            System.out.println("  [MINIBUS LAYOUT] Mode Ekstra Bagasi Aktif: " + this.kapasitasKursi
                    + " kursi | Kapasitas Bagasi: " + this.kapasitasBagasiKoper + " koper.");
        } else {
            System.out.println("  [MINIBUS LAYOUT] Rekonfigurasi kursi standar: " + this.kapasitasKursi + " kursi.");
        }
    }

    // ==========================================
    // METHOD OVERRIDING (MODUL 6 - BAGIAN B & F)
    // ==========================================

    /**
     * Meng-override {@code hitungEstimasiBiaya(int durasiHari)} dari Superclass Kendaraan.
     * Formula khusus minibus travel:
     * - Tarif dasar sewa harian.
     * - Tambahan asuransi keselamatan rombongan (Rp 5.000/kursi per hari).
     * - Jika durasi sewa >= 5 hari, diberikan potongan diskon paket tour 12%.
     *
     * @param durasiHari Durasi hari penyewaan
     * @return Total estimasi sewa minibus
     */
    @Override
    public double hitungEstimasiBiaya(int durasiHari) {
        if (durasiHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Durasi sewa minibus harus > 0 hari!");
        }
        double biayaDasar = getTarifPerHari() * durasiHari;
        double asuransiRombongan = (kapasitasKursi * 5000.0) * durasiHari;
        double subtotal = biayaDasar + asuransiRombongan;

        if (durasiHari >= 5) {
            double diskonTour = subtotal * 0.12; // Diskon 12% paket tour
            return subtotal - diskonTour;
        }
        return subtotal;
    }

    /**
     * Meng-override {@code tampilkanSpesifikasi()} dari Superclass Kendaraan.
     * Menampilkan spesifikasi armada minibus travel dan fasilitas rombongan.
     */
    @Override
    public void tampilkanSpesifikasi() {
        System.out.println("================================================================================");
        System.out.println(" [SPESIFIKASI MINIBUS KOMERSIAL - DYNAMIC BINDING]");
        System.out.println(" Plat Nomor     : " + getNomorPlat());
        System.out.println(" Kategori Armada: " + getTipeKendaraan());
        System.out.println(" Unit Kendaraan : " + getMerk() + " " + getModel() + " (" + getTahunProduksi() + ")");
        System.out.println(" Kapasitas Kursi: " + kapasitasKursi + " Seat Rombongan");
        System.out.println(" Kapasitas Bagasi: " + kapasitasBagasiKoper + " Koper Besar");
        System.out.println(" Entertainment  : " + (fasilitasKaraoke ? "Android TV Karaoke + Wireless Mic" : "Standard Audio"));
        System.out.println(" Ketentuan Sewa : " + (wajibSupir ? "WAJIB DENGAN DRIVER RESMI" : "BISA LEPAS KUNCI"));
        System.out.println(" Tarif Dasar/Hr : Rp " + String.format("%,.2f", getTarifPerHari()));
        System.out.println(" Estimasi 3 Hari: Rp " + String.format("%,.2f", hitungEstimasiBiaya(3)));
        System.out.println(" Status Unit    : " + (isStatusTersedia() ? "TERSEDIA DI GARASI" : "SEDANG DISEWA"));
        getMesin().tampilkanStatusMesin();
        System.out.println("================================================================================");
    }

    /**
     * Meng-override {@code getTipeKendaraan()} dari Superclass Kendaraan.
     *
     * @return String kategori minibus komersial
     */
    @Override
    public String getTipeKendaraan() {
        return "Minibus Komersial / Travel (" + kapasitasKursi + " Seat)";
    }

    /**
     * Meng-override {@code lakukanServis()} dari Superclass Kendaraan.
     * Melakukan servis armada minibus: tune-up mesin diesel + cek rem angin + kalibrasi suspensi.
     */
    @Override
    public void lakukanServis() {
        System.out.println("  [SERVIS MINIBUS] Memulai inspeksi komersial " + getNomorPlat() + " (" + getMerk() + " " + getModel() + "):");
        servisMesinKendaraan();
        System.out.println("   └─ [INSPEKSI REM ANGIN] Pengecekan Kompresor Udara Pneumatic & Kampas Rem Tromol Ganda selesai.");
        System.out.println("   └─ [INSPEKSI KELAYAKAN] Kalibrasi Suspensi Daun/Gardan Belakang & Uji Emisi Diesel lolos.");
    }
}
