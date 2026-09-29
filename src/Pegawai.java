/**
 * Class Pegawai (Subclass dari Person)
 * Merepresentasikan staf operasional, kasir front-office, atau admin dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Inheritance (extends): Menjadi subclass kedua dari Person (memenuhi syarat min. 2 subclass pada Modul 6).
 * 2. Keyword super: Memanggil constructor superclass Person(id, nama, nomorTelepon, alamat, email).
 * 3. Method Overriding:
 *    - {@code tampilkanProfil()}: Menampilkan informasi kepegawaian, NIP, shift, dan rincian take-home pay.
 *    - {@code getDeskripsiPeran()}: Mengembalikan peran operasional staf.
 *    - {@code hitungKompensasi()}: Mengkalkulasikan total kompensasi (Gaji Pokok + Bonus Performa Kasir).
 * 4. Method Overloading:
 *    - {@code catatTransaksiDitangani()} vs {@code catatTransaksiDitangani(int jumlahBatch)}.
 *    - {@code hitungBonusPerforma()} vs {@code hitungBonusPerforma(double insentifKhususPerTransaksi)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Pegawai extends Person {
    // Atribut Khusus Subclass Pegawai
    private String nip;
    private String jabatan;
    private double gajiPokok;
    private String shiftKerja;
    private String statusTugas; // "Tersedia / Siap Melayani", "Sedang Bertugas", "Cuti / Off"
    private int totalTransaksiDitangani;

    /**
     * Parameterized Constructor Subclass Pegawai
     *
     * @param idPegawai    ID unik person staf (misal "PEG-001")
     * @param nama         Nama lengkap pegawai
     * @param nomorTelepon Nomor telepon kontak
     * @param alamat       Alamat domisili
     * @param email        Email resmi perusahaan
     * @param nip          Nomor Induk Pegawai (NIP)
     * @param jabatan      Posisi/Divisi staf
     * @param gajiPokok    Besaran gaji pokok bulanan
     * @param shiftKerja   Jadwal shift kerja operasional
     */
    public Pegawai(String idPegawai, String nama, String nomorTelepon, String alamat, String email,
                   String nip, String jabatan, double gajiPokok, String shiftKerja) {
        // Baris pertama: Memanggil constructor Superclass Person
        super(idPegawai, nama, nomorTelepon, alamat, email);

        if (nip == null || nip.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] NIP Pegawai tidak boleh kosong!");
        }
        this.nip = nip.trim();

        setJabatan(jabatan);
        setGajiPokok(gajiPokok);
        setShiftKerja(shiftKerja);
        this.statusTugas = "Tersedia / Siap Melayani";
        this.totalTransaksiDitangani = 0;

        System.out.println("  [CONSTRUCTOR CHAIN] -> Subclass Pegawai(NIP=" + this.nip + ", Jabatan=" + this.jabatan + ") selesai dibuat.");
    }

    // ==========================================
    // GETTER & SETTER KHUSUS PEGAWAI
    // ==========================================

    public String getNip() {
        return nip;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        if (jabatan == null || jabatan.trim().length() < 2) {
            throw new IllegalArgumentException("[Defensive Error] Jabatan pegawai tidak boleh kosong!");
        }
        this.jabatan = jabatan.trim();
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gajiPokok) {
        if (gajiPokok < 0) {
            throw new IllegalArgumentException("[Defensive Error] Gaji pokok tidak boleh bernilai negatif!");
        }
        this.gajiPokok = gajiPokok;
    }

    public String getShiftKerja() {
        return shiftKerja;
    }

    public void setShiftKerja(String shiftKerja) {
        if (shiftKerja == null || shiftKerja.trim().isEmpty()) {
            this.shiftKerja = "Pagi (08:00 - 16:00)";
        } else {
            this.shiftKerja = shiftKerja.trim();
        }
    }

    public String getStatusTugas() {
        return statusTugas;
    }

    public void setStatusTugas(String statusTugas) {
        this.statusTugas = statusTugas;
    }

    public int getTotalTransaksiDitangani() {
        return totalTransaksiDitangani;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Mencatat penambahan 1 transaksi yang berhasil dilayani staf.
     */
    public void catatTransaksiDitangani() {
        this.totalTransaksiDitangani++;
    }

    /**
     * Overload 2: Mencatat penambahan sejumlah batch transaksi sekaligus.
     *
     * @param jumlahBatch Jumlah transaksi yang ditambahkan secara batch
     */
    public void catatTransaksiDitangani(int jumlahBatch) {
        if (jumlahBatch <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Jumlah batch transaksi harus > 0!");
        }
        this.totalTransaksiDitangani += jumlahBatch;
    }

    /**
     * Overload 1: Menghitung bonus performa standar (Rp 25.000 per transaksi yang dilayani).
     *
     * @return Nominal bonus performa
     */
    public double hitungBonusPerforma() {
        return this.totalTransaksiDitangani * 25000.0;
    }

    /**
     * Overload 2: Menghitung bonus performa dengan rate insentif khusus (misal promo peak season).
     *
     * @param insentifKhususPerTransaksi Besaran insentif per transaksi (Rp)
     * @return Nominal bonus performa berinsentif
     */
    public double hitungBonusPerforma(double insentifKhususPerTransaksi) {
        if (insentifKhususPerTransaksi < 0) {
            throw new IllegalArgumentException("[Defensive Error] Insentif transaksi tidak boleh negatif!");
        }
        return this.totalTransaksiDitangani * insentifKhususPerTransaksi;
    }

    public double hitungTotalPendapatan() {
        return this.gajiPokok + hitungBonusPerforma();
    }

    // ==========================================
    // METHOD OVERRIDING (MODUL 6 - BAGIAN B & F)
    // ==========================================

    /**
     * Meng-override method {@code tampilkanProfil()} dari Superclass Person.
     * Menampilkan profil terperinci kepegawaian mencakup NIP, Jabatan, Shift, Transaksi, dan Gaji.
     */
    @Override
    public void tampilkanProfil() {
        System.out.println("================================================================================");
        System.out.println(" [PROFIL PEGAWAI - DYNAMIC BINDING]");
        System.out.println(" ID Pegawai       : " + getId());
        System.out.println(" Nama Staf        : " + getNama());
        System.out.println(" No. Telepon      : " + getNomorTelepon());
        System.out.println(" Email Kantor     : " + getEmail());
        System.out.println(" NIP              : " + nip);
        System.out.println(" Jabatan          : " + jabatan);
        System.out.println(" Peran Sistem     : " + getDeskripsiPeran());
        System.out.println(" Shift Kerja      : " + shiftKerja);
        System.out.println(" Status Tugas     : " + statusTugas);
        System.out.println(" Gaji Pokok       : Rp " + String.format("%,.2f", gajiPokok));
        System.out.println(" Transaksi Handled: " + totalTransaksiDitangani + " transaksi (Bonus: Rp " + String.format("%,.2f", hitungBonusPerforma()) + ")");
        System.out.println(" Total Kompensasi : Rp " + String.format("%,.2f", hitungKompensasi()));
        System.out.println("================================================================================");
    }

    /**
     * Meng-override {@code getDeskripsiPeran()} dari Superclass Person.
     *
     * @return String deskripsi peran kepegawaian
     */
    @Override
    public String getDeskripsiPeran() {
        return "Staf Operasional AutoRent [" + jabatan + " - Shift " + shiftKerja + "]";
    }

    /**
     * Meng-override {@code hitungKompensasi()} untuk mengkalkulasi Total Take Home Pay (Gaji + Bonus).
     *
     * @return Total take home pay staf
     */
    @Override
    public double hitungKompensasi() {
        return hitungTotalPendapatan();
    }

    /**
     * Backward-compatible alias untuk praktikum terdahulu.
     */
    public void tampilkanDetailPegawai() {
        tampilkanProfil();
    }
}
