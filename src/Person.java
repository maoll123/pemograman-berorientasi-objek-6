import java.util.regex.Pattern;

/**
 * Class Person (Superclass)
 * Merepresentasikan abstraksi dasar entitas individu / aktor manusia dalam AutoRent System.
 * 
 * Menerapkan Prinsip OOP & Modul 6:
 * 1. Generalization & Encapsulation: Menyimpan data umum pengguna (id, nama, nomorTelepon, alamat, email)
 *    dengan aksesibilitas private dan mutator tervalidasi defensif.
 * 2. Polymorphic Base Class: Menjadi tipe reference umum (Superclass Reference) untuk Upcasting
 *    pada entitas Pelanggan dan Pegawai.
 * 3. Method Overriding Base: Menyediakan method virtual {@code tampilkanProfil()}, {@code getDeskripsiPeran()},
 *    dan {@code hitungKompensasi()} yang siap dioverride oleh Subclass.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Person {
    // Atribut Umum yang dienkapsulasi (private)
    private String id;
    private String nama;
    private String nomorTelepon;
    private String alamat;
    private String email;

    // Pola Regex untuk validasi defensif
    private static final Pattern REGEX_TELEPON = Pattern.compile("^08[0-9]{8,11}$");
    private static final Pattern REGEX_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    /**
     * Default Constructor (Tanpa Argumen)
     * Mendemonstrasikan alur Constructor Chaining default.
     */
    public Person() {
        this.id = "USR-DEFAULT";
        this.nama = "Anonim";
        this.nomorTelepon = "080000000000";
        this.alamat = "Belum Diatur";
        this.email = "anonim@autorent.id";
        System.out.println("  [CONSTRUCTOR CHAIN] -> Superclass Person() default dipanggil.");
    }

    /**
     * Parameterized Constructor
     * Menginisialisasi atribut dasar person dengan validasi defensif terpusat.
     *
     * @param id           Identifier unik person (contoh: "PLG-001", "PEG-001")
     * @param nama         Nama lengkap person
     * @param nomorTelepon Nomor telepon aktif (format 08xxx, 10-13 digit)
     * @param alamat       Alamat domisili lengkap
     * @param email        Alamat surat elektronik valid
     */
    public Person(String id, String nama, String nomorTelepon, String alamat, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] ID Person tidak boleh kosong!");
        }
        this.id = id.trim().toUpperCase();
        setNama(nama);
        setNomorTelepon(nomorTelepon);
        setAlamat(alamat);
        setEmail(email);

        System.out.println("  [CONSTRUCTOR CHAIN] -> Superclass Person(id=" + this.id + ", nama=" + this.nama + ") berhasil diinisialisasi.");
    }

    // ==========================================
    // GETTER & SETTER (ENCAPSULATION DEFENSIVE)
    // ==========================================

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().length() < 3) {
            throw new IllegalArgumentException("[Defensive Error] Nama harus terdiri dari minimal 3 karakter!");
        }
        this.nama = nama.trim();
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        if (nomorTelepon == null || !REGEX_TELEPON.matcher(nomorTelepon.trim()).matches()) {
            throw new IllegalArgumentException("[Defensive Error] Format nomor telepon tidak valid! Wajib berawalan '08' dengan panjang 10-13 digit angka.");
        }
        this.nomorTelepon = nomorTelepon.trim();
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().length() < 5) {
            throw new IllegalArgumentException("[Defensive Error] Alamat domisili tidak boleh kosong dan minimal 5 karakter!");
        }
        this.alamat = alamat.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !REGEX_EMAIL.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("[Defensive Error] Format email tidak valid (Contoh: nama@domain.com)!");
        }
        this.email = email.trim().toLowerCase();
    }

    // ==========================================
    // POLYMORPHIC BEHAVIOR METHODS (MODUL 6)
    // ==========================================

    /**
     * Menampilkan profil dasar entitas Person ke konsol.
     * Method ini akan dioverride oleh subclass (Pelanggan & Pegawai) untuk mendemonstrasikan Dynamic Binding.
     */
    public void tampilkanProfil() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" [PROFIL PERSON - SUPERCLASS]");
        System.out.println(" ID Person     : " + id);
        System.out.println(" Nama Lengkap  : " + nama);
        System.out.println(" No. Telepon   : " + nomorTelepon);
        System.out.println(" Alamat        : " + alamat);
        System.out.println(" Email         : " + email);
        System.out.println(" Peran Sistem  : " + getDeskripsiPeran());
        System.out.println(" Kompensasi    : Rp " + String.format("%,.2f", hitungKompensasi()));
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Mengembalikan deskripsi peran aktor dalam sistem AutoRent.
     * Method ini dirancang untuk polymorphic overriding.
     *
     * @return String deskripsi peran aktor
     */
    public String getDeskripsiPeran() {
        return "Aktor Sistem / Pengguna Umum AutoRent";
    }

    /**
     * Menghitung nilai kompensasi finansial dasar (misal reward loyalty atau gaji/bonus).
     *
     * @return Nilai finansial kompensasi (default Rp 0.0 pada superclass)
     */
    public double hitungKompensasi() {
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Telp: %s | Email: %s | Peran: %s", id, nama, nomorTelepon, email, getDeskripsiPeran());
    }
}
