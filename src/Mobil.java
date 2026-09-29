/**
 * Class Mobil (Subclass dari Kendaraan)
 * Merepresentasikan armada mobil penumpang / kendaraan roda empat dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Inheritance (extends): Mewarisi atribut armada dasar dan komponen Mesin dari Superclass Kendaraan.
 * 2. Keyword super: Memanggil constructor superclass via super(...) pada baris pertama.
 * 3. Method Overriding:
 *    - {@code tampilkanSpesifikasi()}: Menampilkan spesifikasi kenyamanan kabin, AC, audio, dan kursi.
 *    - {@code hitungEstimasiBiaya(int durasiHari)}: Mengkalkulasi tarif sewa mobil (biaya unit + surcharge kenyamanan AC + diskon multi-hari).
 *    - {@code getTipeKendaraan()}: Mengembalikan tipe spesifik armada mobil penumpang.
 *    - {@code lakukanServis()}: Melakukan servis berkala mesin, freon AC, dan sistem pengereman ABS.
 * 4. Method Overloading:
 *    - {@code pasangAksesoris(String namaAksesoris)} vs {@code pasangAksesoris(String namaAksesoris, double biayaTambahan)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Mobil extends Kendaraan {
    // Atribut Khusus Subclass Mobil
    private int kapasitasPenumpang;
    private String tipeMobil; // "MPV", "SUV", "Sedan", "City Car"
    private boolean fasilitasAC;
    private String fiturEntertain;

    /**
     * Parameterized Constructor Subclass Mobil
     *
     * @param nomorPlat          Plat nomor kendaraan
     * @param merk              Merk pabrikan mobil
     * @param model             Model/Seri mobil
     * @param tahunProduksi     Tahun perakitan
     * @param tarifPerHari      Tarif harian dasar
     * @param nomorRangka       Nomor rangka mesin
     * @param kapasitasCC       Kapasitas silinder mesin (CC)
     * @param tipeBahanBakar    Jenis bahan bakar (Bensin / Diesel / Hybrid)
     * @param transmisi         Transmisi mesin (Manual / Automatic)
     * @param kapasitasPenumpang Jumlah kursi penumpang (minimal 1)
     * @param tipeMobil         Kategori mobil ("MPV", "SUV", "Sedan")
     * @param fasilitasAC       Ketersediaan AC Kabin
     * @param fiturEntertain    Fitur multimedia / audio
     */
    public Mobil(String nomorPlat, String merk, String model, int tahunProduksi, double tarifPerHari,
                 String nomorRangka, int kapasitasCC, String tipeBahanBakar, String transmisi,
                 int kapasitasPenumpang, String tipeMobil, boolean fasilitasAC, String fiturEntertain) {
        // Baris pertama: Memanggil constructor Superclass Kendaraan
        super(nomorPlat, merk, model, tahunProduksi, tarifPerHari, nomorRangka, kapasitasCC, tipeBahanBakar, transmisi);

        if (kapasitasPenumpang <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas kursi penumpang mobil minimal 1 orang!");
        }
        this.kapasitasPenumpang = kapasitasPenumpang;
        this.tipeMobil = (tipeMobil != null && !tipeMobil.trim().isEmpty()) ? tipeMobil.trim() : "City Car";
        this.fasilitasAC = fasilitasAC;
        this.fiturEntertain = (fiturEntertain != null && !fiturEntertain.trim().isEmpty()) ? fiturEntertain.trim() : "Standard Audio";

        System.out.println("  [CONSTRUCTOR CHAIN] -> Subclass Mobil(" + nomorPlat + " - " + this.tipeMobil + ") berhasil diinstansiasi.");
    }

    // ==========================================
    // GETTER & SETTER KHUSUS MOBIL
    // ==========================================

    public int getKapasitasPenumpang() {
        return kapasitasPenumpang;
    }

    public void setKapasitasPenumpang(int kapasitasPenumpang) {
        if (kapasitasPenumpang <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas kursi penumpang mobil minimal 1 orang!");
        }
        this.kapasitasPenumpang = kapasitasPenumpang;
    }

    public String getTipeMobil() {
        return tipeMobil;
    }

    public void setTipeMobil(String tipeMobil) {
        if (tipeMobil == null || tipeMobil.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Tipe mobil tidak boleh kosong!");
        }
        this.tipeMobil = tipeMobil.trim();
    }

    public boolean isFasilitasAC() {
        return fasilitasAC;
    }

    public void setFasilitasAC(boolean fasilitasAC) {
        this.fasilitasAC = fasilitasAC;
    }

    public String getFiturEntertain() {
        return fiturEntertain;
    }

    public void setFiturEntertain(String fiturEntertain) {
        this.fiturEntertain = fiturEntertain;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Memasang aksesoris standar tanpa biaya tambahan.
     *
     * @param namaAksesoris Nama perangkat tambahan (misal: "Car Seat Anak", "Phone Holder Mobil")
     */
    public void pasangAksesoris(String namaAksesoris) {
        if (namaAksesoris == null || namaAksesoris.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Nama aksesoris tidak boleh kosong!");
        }
        this.fiturEntertain += " + " + namaAksesoris.trim();
        System.out.println("  [AKSESORIS MOBIL] Aksesoris '" + namaAksesoris + "' berhasil dipasang pada unit " + getNomorPlat());
    }

    /**
     * Overload 2: Memasang aksesoris premium dengan biaya upgrade yang mempengaruhi tarif harian.
     *
     * @param namaAksesoris Nama perangkat premium (misal: "Roof Box 400L", "Dashcam 4K Dual")
     * @param biayaTambahan Tambahan penyesuaian tarif harian unit (Rp)
     */
    public void pasangAksesoris(String namaAksesoris, double biayaTambahan) {
        pasangAksesoris(namaAksesoris);
        if (biayaTambahan > 0) {
            setTarifPerHari(getTarifPerHari() + biayaTambahan);
            System.out.println("  [AKSESORIS MOBIL] Tarif harian disesuaikan: +Rp " + String.format("%,.2f", biayaTambahan)
                    + " -> Tarif Baru: Rp " + String.format("%,.2f", getTarifPerHari()));
        }
    }

    // ==========================================
    // METHOD OVERRIDING (MODUL 6 - BAGIAN B & F)
    // ==========================================

    /**
     * Meng-override {@code hitungEstimasiBiaya(int durasiHari)} dari Superclass Kendaraan.
     * Mengimplementasikan formula bisnis spesifik armada mobil:
     * - Tarif dasar sewa harian.
     * - Surcharge kenyamanan AC kabin (Rp 25.000 / hari jika AC aktif).
     * - Diskon sewa durasi panjang (diskon 10% jika sewa >= 7 hari).
     *
     * @param durasiHari Durasi hari penyewaan
     * @return Total biaya sewa mobil tervalidasi
     */
    @Override
    public double hitungEstimasiBiaya(int durasiHari) {
        if (durasiHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Durasi sewa mobil harus > 0 hari!");
        }
        double biayaDasar = getTarifPerHari() * durasiHari;
        double surchargeAC = fasilitasAC ? (25000.0 * durasiHari) : 0.0;
        double subtotal = biayaDasar + surchargeAC;

        // Diskon otomatis 10% untuk sewa mingguan (>= 7 hari)
        if (durasiHari >= 7) {
            double diskonMingguan = subtotal * 0.10;
            return subtotal - diskonMingguan;
        }
        return subtotal;
    }

    /**
     * Meng-override {@code tampilkanSpesifikasi()} dari Superclass Kendaraan.
     * Menampilkan spesifikasi kabin, kapasitas kursi, transmisi, dan status kelengkapan mobil.
     */
    @Override
    public void tampilkanSpesifikasi() {
        System.out.println("================================================================================");
        System.out.println(" [SPESIFIKASI MOBIL PENUMPANG - DYNAMIC BINDING]");
        System.out.println(" Plat Nomor     : " + getNomorPlat());
        System.out.println(" Kategori Armada: " + getTipeKendaraan());
        System.out.println(" Unit Kendaraan : " + getMerk() + " " + getModel() + " (" + getTahunProduksi() + ")");
        System.out.println(" Kapasitas Kursi: " + kapasitasPenumpang + " Penumpang (Termasuk Pengemudi)");
        System.out.println(" Fasilitas AC   : " + (fasilitasAC ? "Double Blower / Full Cabin Climate Control" : "Non-AC / Standar"));
        System.out.println(" Entertainment  : " + fiturEntertain);
        System.out.println(" Tarif Dasar/Hr : Rp " + String.format("%,.2f", getTarifPerHari()));
        System.out.println(" Estimasi 3 Hari: Rp " + String.format("%,.2f", hitungEstimasiBiaya(3)));
        System.out.println(" Status Unit    : " + (isStatusTersedia() ? "TERSEDIA DI GARASI" : "SEDANG DISEWA"));
        getMesin().tampilkanStatusMesin();
        System.out.println("================================================================================");
    }

    /**
     * Meng-override {@code getTipeKendaraan()} dari Superclass Kendaraan.
     *
     * @return String kategori mobil
     */
    @Override
    public String getTipeKendaraan() {
        return "Mobil Penumpang (" + tipeMobil + ")";
    }

    /**
     * Meng-override {@code lakukanServis()} dari Superclass Kendaraan.
     * Melakukan servis spesifik mobil: tune-up mesin + cek filter AC + cek kampas rem 4 roda.
     */
    @Override
    public void lakukanServis() {
        System.out.println("  [SERVIS MOBIL] Memulai inspeksi unit mobil " + getNomorPlat() + " (" + getMerk() + " " + getModel() + "):");
        servisMesinKendaraan();
        System.out.println("   └─ [INSPEKSI KABIN] Penggantian Filter Kabin AC & Flushing Freon selesai.");
        System.out.println("   └─ [INSPEKSI KESELAMATAN] Pemeriksaan Kampas Rem ABS 4 Roda & Tekanan Ban 32 PSI Optimal.");
    }

    /**
     * Backward-compatible alias untuk praktikum terdahulu.
     */
    public void tampilkanDetailMobil() {
        tampilkanSpesifikasi();
    }
}
