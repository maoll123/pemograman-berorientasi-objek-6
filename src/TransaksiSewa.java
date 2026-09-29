import java.util.ArrayList;

/**
 * Class TransaksiSewa (Sentral Orkestrasi Relasi & Eksekusi Polimorfis)
 * Merepresentasikan transaksi persewaan kendaraan antara Pelanggan, Pegawai, dan Kendaraan.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Polymorphic Association: Menghubungkan Superclass Kendaraan (yang secara dinamis dapat berupa Mobil, Motor, atau Minibus)
 *    serta Subclass Pelanggan dan Pegawai.
 * 2. Dynamic Method Dispatch: Menghitung tarif dasar menggunakan pemanggilan dinamis {@code kendaraan.hitungEstimasiBiaya(durasiHari)}.
 * 3. Composition (◆): Mengelola rincian baris biaya dalam {@code ArrayList<DetailItemBiaya>}.
 * 4. Dependency (- - >): Memanfaatkan KuitansiPrinter dan LayananNotifikasi.
 * 5. Method Overloading:
 *    - {@code prosesPembayaran(double nominalBayar)} vs {@code prosesPembayaran(double nominalBayar, String metode, String ref)}.
 *    - {@code tambahLayananTambahan(String nama, double tarif)} vs {@code tambahLayananTambahan(String nama, double tarif, int kuantitas)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class TransaksiSewa {
    private String kodeTransaksi;
    private String tanggalSewa;
    private Pelanggan pelanggan;      // Relasi Asosiasi
    private Pegawai petugas;          // Relasi Asosiasi
    private Kendaraan kendaraan;      // Relasi Asosiasi Polimorfis (Superclass Reference)
    private int durasiHari;
    private String statusTransaksi;   // "DIPESAN", "AKTIF / DISEWA", "SELESAI", "DIBATALKAN"
    private ArrayList<DetailItemBiaya> rincianBiaya; // Komposisi (◆)
    private double totalBiaya;
    private double uangDibayar;
    private double uangKembalian;
    private boolean lunas;

    public TransaksiSewa(String kodeTransaksi, String tanggalSewa, Pelanggan pelanggan, Pegawai petugas, Kendaraan kendaraan, int durasiHari) {
        if (kodeTransaksi == null || kodeTransaksi.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Kode Transaksi tidak boleh kosong!");
        }
        if (pelanggan == null || petugas == null || kendaraan == null) {
            throw new IllegalArgumentException("[Defensive Error] Aktor transaksi (Pelanggan/Petugas/Kendaraan) tidak boleh bernilai null!");
        }
        if (durasiHari <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Durasi sewa hari minimal 1 hari!");
        }

        this.kodeTransaksi = kodeTransaksi.trim().toUpperCase();
        this.tanggalSewa = (tanggalSewa != null) ? tanggalSewa.trim() : "2026-09-28";
        this.pelanggan = pelanggan;
        this.petugas = petugas;
        this.kendaraan = kendaraan;
        this.durasiHari = durasiHari;
        this.statusTransaksi = "DIPESAN";
        this.rincianBiaya = new ArrayList<>();
        this.uangDibayar = 0.0;
        this.uangKembalian = 0.0;
        this.lunas = false;

        // Dynamic Binding: Memanggil hitungEstimasiBiaya(durasiHari) sesuai subclass konkret kendaraan
        double biayaUnitPolimorfis = this.kendaraan.hitungEstimasiBiaya(durasiHari);
        this.rincianBiaya.add(new DetailItemBiaya("Biaya Sewa Unit (" + this.kendaraan.getTipeKendaraan() + ")",
                this.kendaraan.getTarifPerHari(), durasiHari));

        hitungTotalBiayaInternal();
        System.out.println("  [TRANSAKSI CREATED] " + this.kodeTransaksi + " berhasil dibentuk untuk "
                + pelanggan.getNama() + " menyewa " + kendaraan.getNomorPlat() + " (" + kendaraan.getTipeKendaraan() + ")");
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Menambahkan 1 kuantitas layanan tambahan / add-on.
     *
     * @param namaLayanan Nama add-on (misal: "Asuransi All-Risk", "Driver Tambahan")
     * @param tarifSatuan Biaya add-on
     */
    public void tambahLayananTambahan(String namaLayanan, double tarifSatuan) {
        tambahLayananTambahan(namaLayanan, tarifSatuan, 1);
    }

    /**
     * Overload 2: Menambahkan layanan tambahan dengan spesifikasi kuantitas tertentu.
     *
     * @param namaLayanan Nama add-on
     * @param tarifSatuan Biaya add-on per unit
     * @param kuantitas   Jumlah unit yang dipesan
     */
    public void tambahLayananTambahan(String namaLayanan, double tarifSatuan, int kuantitas) {
        if (namaLayanan == null || namaLayanan.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Nama layanan tambahan tidak boleh kosong!");
        }
        DetailItemBiaya item = new DetailItemBiaya(namaLayanan, tarifSatuan, kuantitas);
        this.rincianBiaya.add(item);
        hitungTotalBiayaInternal();
        System.out.println("  [ADD-ON TRANSAKSI] Ditambahkan: " + item.toString());
    }

    /**
     * Overload 1: Memproses pembayaran tunai / cash di kasir.
     *
     * @param nominalBayar Jumlah uang kas yang diserahkan penyewa
     * @return True jika pembayaran mencukupi dan lunas
     */
    public boolean prosesPembayaran(double nominalBayar) {
        if (nominalBayar < this.totalBiaya) {
            System.err.println("[Pembayaran Gagal] Nominal tunai (Rp " + String.format("%,.2f", nominalBayar)
                    + ") kurang dari total tagihan (Rp " + String.format("%,.2f", this.totalBiaya) + ")!");
            return false;
        }
        this.uangDibayar = nominalBayar;
        this.uangKembalian = nominalBayar - this.totalBiaya;
        this.lunas = true;
        System.out.println("  [PAYMENT CASH] Pembayaran tunai lunas! Nominal: Rp " + String.format("%,.2f", nominalBayar)
                + " | Kembalian: Rp " + String.format("%,.2f", uangKembalian));
        return true;
    }

    /**
     * Overload 2: Memproses pembayaran non-tunai (Transfer Bank / E-Wallet / QRIS) dengan verifikasi referensi.
     *
     * @param nominalBayar    Nominal transfer yang diverifikasi
     * @param metodeTransfer  Metode kanal (misal: "QRIS", "BCA Virtual Account", "Mandiri Transfer")
     * @param nomorReferensi  Nomor referensi pembayaran perbankan
     * @return True jika verifikasi pembayaran elektronik sukses
     */
    public boolean prosesPembayaran(double nominalBayar, String metodeTransfer, String nomorReferensi) {
        if (nomorReferensi == null || nomorReferensi.trim().length() < 6) {
            throw new IllegalArgumentException("[Defensive Error] Nomor referensi transfer digital tidak valid (min. 6 digit)!");
        }
        if (nominalBayar < this.totalBiaya) {
            System.err.println("[Pembayaran Gagal] Nominal transfer tidak sesuai tagihan!");
            return false;
        }
        this.uangDibayar = nominalBayar;
        this.uangKembalian = 0.0; // Non-tunai uang pas
        this.lunas = true;
        System.out.println("  [PAYMENT DIGITAL] Verifikasi sukses via " + metodeTransfer + " (Ref: "
                + nomorReferensi.trim() + ") | Nominal: Rp " + String.format("%,.2f", nominalBayar) + " [LUNAS]");
        return true;
    }

    // ==========================================
    // DOMAIN LIFECYCLE METHODS
    // ==========================================

    private void hitungTotalBiayaInternal() {
        double subtotal = 0.0;
        for (DetailItemBiaya item : rincianBiaya) {
            subtotal += item.getSubtotal();
        }
        double diskon = subtotal * pelanggan.getDiskonMember(durasiHari);
        this.totalBiaya = Math.max(0, subtotal - diskon);
    }

    public boolean prosesPeminjaman(LayananNotifikasi notifier) {
        if (!pelanggan.canRent()) {
            System.err.println("[Validasi Gagal] Pelanggan telah mencapai batas limit sewa aktif!");
            return false;
        }
        if (!kendaraan.isStatusTersedia()) {
            System.err.println("[Validasi Gagal] Unit kendaraan " + kendaraan.getNomorPlat() + " sedang tidak tersedia / dalam sewa!");
            return false;
        }

        pelanggan.tambahSewaAktif(1);
        kendaraan.catatPenyewaan(0);
        petugas.catatTransaksiDitangani();
        this.statusTransaksi = "AKTIF / DISEWA";

        System.out.println("  [STATUS UPDATE] Peminjaman unit " + kendaraan.getNomorPlat() + " AKTIF.");
        if (notifier != null) {
            notifier.kirimNotifikasiBooking(pelanggan, kodeTransaksi, kendaraan.getNomorPlat(),
                    kendaraan.getMerk() + " " + kendaraan.getModel(), totalBiaya);
        }
        return true;
    }

    public void prosesPengembalian(int hariKeterlambatan, int kmPemakaian, KuitansiPrinter printer, LayananNotifikasi notifier) {
        if (!"AKTIF / DISEWA".equalsIgnoreCase(this.statusTransaksi)) {
            System.err.println("[Peringatan] Status transaksi bukan dalam masa sewa aktif!");
            return;
        }

        if (hariKeterlambatan > 0) {
            double denda = (kendaraan.getTarifPerHari() * 1.5) * hariKeterlambatan;
            rincianBiaya.add(new DetailItemBiaya("Denda Keterlambatan (" + hariKeterlambatan + " Hari @ 150%)",
                    kendaraan.getTarifPerHari() * 1.5, hariKeterlambatan));
            System.out.println("  [DENDA] Denda keterlambatan ditambahkan: Rp " + String.format("%,.2f", denda));
        }

        kendaraan.selesaikanPenyewaan();
        kendaraan.getMesin().tambahKilometer(kmPemakaian);
        pelanggan.selesaikanSewaAktif(1);
        this.statusTransaksi = "SELESAI";

        hitungTotalBiayaInternal();
        System.out.println("  [STATUS UPDATE] Transaksi " + kodeTransaksi + " telah SELESAI. Unit kembali ke garasi.");

        if (printer != null) {
            double subtotal = 0.0;
            for (DetailItemBiaya item : rincianBiaya) {
                subtotal += item.getSubtotal();
            }
            double diskon = subtotal * pelanggan.getDiskonMember(durasiHari);
            printer.cetakKuitansiResmi(kodeTransaksi, tanggalSewa, statusTransaksi, pelanggan, petugas,
                    kendaraan, durasiHari, rincianBiaya, subtotal, diskon, totalBiaya,
                    (uangDibayar > 0 ? uangDibayar : totalBiaya), uangKembalian);
        }

        if (notifier != null) {
            notifier.kirimNotifikasiPengembalian(pelanggan, kodeTransaksi, "Baik / Diterima", totalBiaya);
        }
    }

    public void tampilkanRingkasanTransaksi() {
        System.out.println("================================================================================");
        System.out.println(" RINGKASAN TRANSAKSI SEWA: " + kodeTransaksi);
        System.out.println("================================================================================");
        System.out.println(" Tanggal Sewa     : " + tanggalSewa);
        System.out.println(" Status Transaksi : " + statusTransaksi + " (" + (lunas ? "LUNAS" : "BELUM LUNAS") + ")");
        System.out.println(" Pelanggan        : " + pelanggan.getNama() + " [" + pelanggan.getLevelMember() + "]");
        System.out.println(" Kasir / Petugas  : " + petugas.getNama() + " [" + petugas.getNip() + "]");
        System.out.println(" Armada Disewa    : " + kendaraan.getMerk() + " " + kendaraan.getModel() + " (" + kendaraan.getNomorPlat() + ")");
        System.out.println(" Kategori Armada  : " + kendaraan.getTipeKendaraan());
        System.out.println(" Durasi Sewa      : " + durasiHari + " Hari");
        System.out.println(" Total Biaya Final: Rp " + String.format("%,.2f", totalBiaya));
        System.out.println("================================================================================");
    }

    // ==========================================
    // GETTER
    // ==========================================

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public String getTanggalSewa() {
        return tanggalSewa;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Pegawai getPetugas() {
        return petugas;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public int getDurasiHari() {
        return durasiHari;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public boolean isLunas() {
        return lunas;
    }
}
