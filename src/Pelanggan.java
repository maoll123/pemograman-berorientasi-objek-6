import java.util.regex.Pattern;

/**
 * Class Pelanggan (Subclass dari Person)
 * Merepresentasikan data penyewa/pelanggan dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Inheritance (extends): Mewarisi atribut identitas dasar dan method dari superclass Person.
 * 2. Keyword super: Memanggil constructor superclass Person via super(...) pada baris pertama.
 * 3. Method Overriding:
 *    - {@code tampilkanProfil()}: Menampilkan detail profil penyewa dengan info keanggotaan & kuota.
 *    - {@code getDeskripsiPeran()}: Mengembalikan peran spesifik pelanggan dan tier keanggotaan.
 *    - {@code hitungKompensasi()}: Menghitung reward loyalty points / estimasi cashback pelanggan.
 * 4. Method Overloading:
 *    - {@code tambahDeposit(double nominal)} vs {@code tambahDeposit(double nominal, String metodePembayaran)}.
 *    - {@code getDiskonMember()} vs {@code getDiskonMember(int durasiHari)}.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Pelanggan extends Person {
    // Atribut Khusus Subclass Pelanggan
    private String nomorKtp;
    private String levelMember; // "REGULAR", "GOLD", "VIP"
    private double depositSaldo;
    private int jumlahSewaAktif;
    private int maksimalSewaAktif;

    private static final Pattern REGEX_KTP = Pattern.compile("^[0-9]{16}$");

    /**
     * Parameterized Constructor Subclass Pelanggan
     * Menggunakan constructor chaining melalui pemanggilan super(...).
     *
     * @param idPelanggan  ID unik pelanggan (misal "PLG-001")
     * @param nama         Nama lengkap pelanggan
     * @param nomorTelepon Nomor HP valid (08xxx)
     * @param alamat       Alamat domisili
     * @param email        Alamat email
     * @param nomorKtp     16 digit nomor NIK/KTP
     * @param levelMember  Tingkat keanggotaan ("REGULAR", "GOLD", "VIP")
     * @param depositSaldo Saldo jaminan awal
     */
    public Pelanggan(String idPelanggan, String nama, String nomorTelepon, String alamat, String email,
                     String nomorKtp, String levelMember, double depositSaldo) {
        // Baris pertama: Memanggil constructor Superclass Person
        super(idPelanggan, nama, nomorTelepon, alamat, email);

        // Inisialisasi atribut spesifik Subclass Pelanggan
        setNomorKtp(nomorKtp);
        setLevelMember(levelMember);

        if (depositSaldo < 0) {
            throw new IllegalArgumentException("[Defensive Error] Deposit saldo awal tidak boleh bernilai negatif!");
        }
        this.depositSaldo = depositSaldo;
        this.jumlahSewaAktif = 0;

        System.out.println("  [CONSTRUCTOR CHAIN] -> Subclass Pelanggan(KTP=" + this.nomorKtp + ", Level=" + this.levelMember + ") selesai dibuat.");
    }

    // ==========================================
    // GETTER & SETTER KHUSUS SUBCLASS
    // ==========================================

    public String getIdPelanggan() {
        return super.getId();
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public void setNomorKtp(String nomorKtp) {
        if (nomorKtp == null || !REGEX_KTP.matcher(nomorKtp.trim()).matches()) {
            throw new IllegalArgumentException("[Defensive Error] Nomor KTP/NIK wajib terdiri dari tepat 16 digit angka!");
        }
        this.nomorKtp = nomorKtp.trim();
    }

    public String getLevelMember() {
        return levelMember;
    }

    public void setLevelMember(String levelMember) {
        if (levelMember == null) {
            this.levelMember = "REGULAR";
            this.maksimalSewaAktif = 2;
            return;
        }
        String lvl = levelMember.trim().toUpperCase();
        switch (lvl) {
            case "VIP":
                this.levelMember = "VIP";
                this.maksimalSewaAktif = 5;
                break;
            case "GOLD":
                this.levelMember = "GOLD";
                this.maksimalSewaAktif = 3;
                break;
            case "REGULAR":
            default:
                this.levelMember = "REGULAR";
                this.maksimalSewaAktif = 2;
                break;
        }
    }

    public double getDepositSaldo() {
        return depositSaldo;
    }

    public int getJumlahSewaAktif() {
        return jumlahSewaAktif;
    }

    public int getMaksimalSewaAktif() {
        return maksimalSewaAktif;
    }

    // ==========================================
    // METHOD OVERLOADING (MODUL 6 - BAGIAN C)
    // ==========================================

    /**
     * Overload 1: Menghitung diskon standar berdasarkan tier keanggotaan member.
     *
     * @return Persentase diskon (0.0 s/d 0.15)
     */
    public double getDiskonMember() {
        switch (levelMember) {
            case "VIP":
                return 0.15; // Diskon 15% untuk VIP
            case "GOLD":
                return 0.10; // Diskon 10% untuk Gold
            case "REGULAR":
            default:
                return 0.0;  // 0% Diskon
        }
    }

    /**
     * Overload 2: Menghitung diskon member dengan tambahan insentif durasi sewa panjang (Long-Term Rental).
     *
     * @param durasiHari Durasi hari penyewaan
     * @return Total persentase diskon terakumulasi
     */
    public double getDiskonMember(int durasiHari) {
        double diskonDasar = getDiskonMember();
        if (durasiHari >= 7) {
            // Tambahan diskon loyalitas 5% untuk sewa mingguan / durasi panjang
            return diskonDasar + 0.05;
        }
        return diskonDasar;
    }

    /**
     * Overload 1: Menambahkan deposit tunai standar tanpa promo.
     *
     * @param nominal Jumlah uang yang ditambahkan ke saldo deposit
     */
    public void tambahDeposit(double nominal) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Penambahan saldo deposit harus bernilai positif!");
        }
        this.depositSaldo += nominal;
        System.out.println("  [DEPOSIT UPDATE] Top-up tunai berhasil: +Rp " + String.format("%,.2f", nominal)
                + " | Saldo Sekarang: Rp " + String.format("%,.2f", this.depositSaldo));
    }

    /**
     * Overload 2: Menambahkan deposit melalui channel digital dengan bonus cashback promo.
     *
     * @param nominal          Jumlah uang top-up
     * @param metodePembayaran Metode pembayaran digital (misal: "QRIS", "TRANSFER_BANK", "KARTU_KREDIT")
     */
    public void tambahDeposit(double nominal, String metodePembayaran) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Penambahan saldo deposit harus bernilai positif!");
        }
        double bonusPromo = 0.0;
        if ("QRIS".equalsIgnoreCase(metodePembayaran)) {
            bonusPromo = nominal * 0.02; // Cashback 2% via QRIS
        } else if ("TRANSFER_BANK".equalsIgnoreCase(metodePembayaran)) {
            bonusPromo = 5000.0; // Flat cashback Rp 5.000 via Transfer Bank
        }
        double totalMasuk = nominal + bonusPromo;
        this.depositSaldo += totalMasuk;
        System.out.println("  [DEPOSIT UPDATE] Top-up via " + metodePembayaran + " (Bonus Cashback: Rp "
                + String.format("%,.2f", bonusPromo) + ") Total Masuk: +Rp " + String.format("%,.2f", totalMasuk)
                + " | Saldo Sekarang: Rp " + String.format("%,.2f", this.depositSaldo));
    }

    // ==========================================
    // DOMAIN BEHAVIOR METHODS
    // ==========================================

    public boolean canRent() {
        return jumlahSewaAktif < maksimalSewaAktif;
    }

    public boolean tambahSewaAktif(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Jumlah sewa yang ditambahkan harus > 0!");
        }
        if (this.jumlahSewaAktif + jumlah > this.maksimalSewaAktif) {
            System.err.println("[Validasi Kuota Gagal] Pelanggan " + getNama() + " (" + levelMember + ") telah mencapai batas kuota (" + maksimalSewaAktif + " unit aktif)!");
            return false;
        }
        this.jumlahSewaAktif += jumlah;
        return true;
    }

    public void selesaikanSewaAktif(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Jumlah sewa selesai harus > 0!");
        }
        this.jumlahSewaAktif = Math.max(0, this.jumlahSewaAktif - jumlah);
    }

    public boolean potongDeposit(double nominal) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Pemotongan deposit harus > 0!");
        }
        if (nominal > this.depositSaldo) {
            System.err.println("[Transaksi Gagal] Saldo deposit pelanggan tidak mencukupi (Tersedia: Rp " + String.format("%,.0f", depositSaldo) + ")!");
            return false;
        }
        this.depositSaldo -= nominal;
        return true;
    }

    // ==========================================
    // METHOD OVERRIDING (MODUL 6 - BAGIAN B & F)
    // ==========================================

    /**
     * Meng-override method {@code tampilkanProfil()} dari Superclass Person.
     * Menampilkan profil terperinci pelanggan mencakup NIK, Tier Member, Kuota Sewa, dan Saldo Jaminan.
     */
    @Override
    public void tampilkanProfil() {
        System.out.println("================================================================================");
        System.out.println(" [PROFIL PELANGGAN - DYNAMIC BINDING]");
        System.out.println(" ID Pelanggan     : " + getId());
        System.out.println(" Nama Lengkap     : " + getNama());
        System.out.println(" No. Telepon      : " + getNomorTelepon());
        System.out.println(" Alamat Domisili  : " + getAlamat());
        System.out.println(" Email            : " + getEmail());
        System.out.println(" NIK / No. KTP    : " + nomorKtp);
        System.out.println(" Peran Sistem     : " + getDeskripsiPeran());
        System.out.println(" Tier Member      : " + levelMember + " (Diskon Reguler: " + (int)(getDiskonMember() * 100) + "%)");
        System.out.println(" Deposit Saldo    : Rp " + String.format("%,.2f", depositSaldo));
        System.out.println(" Kuota Unit Sewa  : " + jumlahSewaAktif + " / " + maksimalSewaAktif + " Unit Aktif");
        System.out.println(" Reward Cashback  : Rp " + String.format("%,.2f", hitungKompensasi()));
        System.out.println(" Status Kelayakan : " + (canRent() ? "BISA MENYEWA UNIT" : "KUOTA PENUH"));
        System.out.println("================================================================================");
    }

    /**
     * Meng-override {@code getDeskripsiPeran()} dari Superclass Person.
     *
     * @return String deskripsi peran penyewa berkeanggotaan
     */
    @Override
    public String getDeskripsiPeran() {
        return "Penyewa Terdaftar AutoRent [Tier: " + levelMember + "]";
    }

    /**
     * Meng-override {@code hitungKompensasi()} untuk mengkalkulasi cashback loyalty reward.
     *
     * @return Nilai reward loyalitas member
     */
    @Override
    public double hitungKompensasi() {
        // Reward loyalitas 3% untuk VIP, 1.5% untuk Gold dari deposit saldo
        if ("VIP".equalsIgnoreCase(levelMember)) {
            return depositSaldo * 0.03;
        } else if ("GOLD".equalsIgnoreCase(levelMember)) {
            return depositSaldo * 0.015;
        }
        return 0.0;
    }

    /**
     * Backward-compatible alias untuk praktikum terdahulu.
     */
    public void tampilkanDetailPelanggan() {
        tampilkanProfil();
    }
}
