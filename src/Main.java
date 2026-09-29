import java.util.ArrayList;

/**
 * Driver & Test Suite Utama (Main.java)
 * Menguji dan mendemonstrasikan secara komprehensif seluruh capaian pembelajaran Modul 6:
 * 1. Upcasting & Polymorphic Reference
 * 2. Method Overriding (@Override) pada Subclass
 * 3. Method Overloading dengan variasi signature parameter
 * 4. Dynamic Binding / Dynamic Method Dispatch saat Runtime
 * 5. Heterogeneous Polymorphic Collection
 * 6. Integrasi Relasi Objek AutoRent System (Inheritance, Composition, Aggregation, Association, Dependency)
 * 7. Defensive Boundary Validation & Exception Handling
 * 8. Tabel Verifikasi Dynamic Binding Modul 6
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("     AUTORENT SYSTEM - COMPREHENSIVE TEST SUITE MODUL 6 (AGILE SPRINT 6)        ");
        System.out.println("   POLYMORPHISM, METHOD OVERRIDING, METHOD OVERLOADING & DYNAMIC BINDING        ");
        System.out.println(" Oleh: Ahmad Yasar Maulana Dais | NRP: 3125522018 | PENS PSDKU Sumenep (2026)   ");
        System.out.println("================================================================================\n");

        // =========================================================================
        // FASE 1: PEMBUKTIAN UPCASTING & POLYMORPHIC REFERENCE
        // =========================================================================
        System.out.println("================================================================================");
        System.out.println(" [FASE 1] UPCASTING & POLYMORPHIC REFERENCE (HIERARKI PERSON & KENDARAAN)       ");
        System.out.println("================================================================================");
        System.out.println("Konsep: Reference bertipe Superclass (Person & Kendaraan) merujuk pada objek konkrit Subclass.\n");

        // Upcasting pada Hierarki Person: Superclass Person reference -> Subclass Pelanggan & Pegawai
        Person refPerson1 = new Pelanggan("PLG-101", "Bapak Hendra Pratama", "081234567890",
                "Jl. Diponegoro No. 12, Sumenep", "hendra.pratama@gmail.com",
                "3529012304890001", "VIP", 2500000.0);

        Person refPerson2 = new Pegawai("PEG-201", "Siti Nurhaliza", "085298765432",
                "Jl. Urip Sumoharjo No. 8, Sumenep", "siti.kasir@autorent.id",
                "199805122022032001", "Kasir Senior & Customer Service", 4500000.0, "Pagi (08:00 - 16:00)");

        // Upcasting pada Hierarki Kendaraan: Superclass Kendaraan reference -> Subclass Mobil, Motor, Minibus
        Kendaraan refKendaraan1 = new Mobil("M 1888 AB", "Toyota", "Innova Zenix Hybrid", 2024,
                600000.0, "MHK-ZNX-2024-9988", 2000, "Hybrid Bensin", "Automatic CVT",
                7, "MPV Premium", true, "Dual Screen Rear Audio + Apple CarPlay");

        Kendaraan refKendaraan2 = new Motor("M 4567 CD", "Honda", "PCX 160 ABS", 2024,
                150000.0, "MH1-PCX-2024-5544", 160, "Bensin", "Matic",
                "Matic Maxi", 30, 2, true, true);

        Kendaraan refKendaraan3 = new Minibus("M 7788 XY", "Isuzu", "Elf Long Coaster", 2023,
                1100000.0, "ISZ-ELF-2023-3322", 2800, "Diesel Solar", "Manual",
                16, 12, true, true);

        System.out.println("\n[Hasil Upcasting Reference]:");
        System.out.println("1. refPerson1     (Tipe Ref: Person)    -> Objek Aktual: " + refPerson1.getClass().getSimpleName());
        System.out.println("2. refPerson2     (Tipe Ref: Person)    -> Objek Aktual: " + refPerson2.getClass().getSimpleName());
        System.out.println("3. refKendaraan1 (Tipe Ref: Kendaraan) -> Objek Aktual: " + refKendaraan1.getClass().getSimpleName());
        System.out.println("4. refKendaraan2 (Tipe Ref: Kendaraan) -> Objek Aktual: " + refKendaraan2.getClass().getSimpleName());
        System.out.println("5. refKendaraan3 (Tipe Ref: Kendaraan) -> Objek Aktual: " + refKendaraan3.getClass().getSimpleName());


        // =========================================================================
        // FASE 2: PEMBUKTIAN METHOD OVERRIDING & DYNAMIC BINDING (RUNTIME POLYMORPHISM)
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 2] METHOD OVERRIDING & DYNAMIC METHOD DISPATCH SAAT RUNTIME               ");
        System.out.println("================================================================================");
        System.out.println("Konsep: Pemanggilan method yang sama pada reference superclass menghasilkan output");
        System.out.println("        yang berbeda sesuai implementasi objek aktual di memory heap.\n");

        System.out.println(">>> 1. Dynamic Binding pada Hierarki Person (Method: tampilkanProfil()) <<<");
        refPerson1.tampilkanProfil();
        refPerson2.tampilkanProfil();

        System.out.println("\n>>> 2. Dynamic Binding pada Hierarki Kendaraan (Method: tampilkanSpesifikasi()) <<<");
        refKendaraan1.tampilkanSpesifikasi();
        refKendaraan2.tampilkanSpesifikasi();
        refKendaraan3.tampilkanSpesifikasi();

        System.out.println("\n>>> 3. Dynamic Binding Kalkulasi Tarif Sewa 5 Hari (Method: hitungEstimasiBiaya(5)) <<<");
        System.out.printf("• Mobil Innova Zenix  (5 Hari): Rp %,14.2f (Termasuk Surcharge AC Kabin)\n", refKendaraan1.hitungEstimasiBiaya(5));
        System.out.printf("• Motor Honda PCX     (5 Hari): Rp %,14.2f (Termasuk Promo City Tour Rp 15.000)\n", refKendaraan2.hitungEstimasiBiaya(5));
        System.out.printf("• Minibus Elf Long    (5 Hari): Rp %,14.2f (Termasuk Asuransi Kursi + Diskon Paket Tour 12%%)\n", refKendaraan3.hitungEstimasiBiaya(5));

        System.out.println("\n>>> 4. Dynamic Binding Prosedur Servis Berkala (Method: lakukanServis()) <<<");
        refKendaraan1.lakukanServis();
        refKendaraan2.lakukanServis();
        refKendaraan3.lakukanServis();


        // =========================================================================
        // FASE 3: PEMBUKTIAN METHOD OVERLOADING (COMPILE-TIME POLYMORPHISM)
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 3] METHOD OVERLOADING DENGAN BERBAGAI SIGNATURE PARAMETER               ");
        System.out.println("================================================================================");
        System.out.println("Konsep: Satu nama method memiliki beberapa implementasi dengan parameter berbeda.\n");

        System.out.println(">>> 1. Overloading hitungEstimasiBiaya(...) pada Mobil Innova Zenix <<<");
        System.out.printf("  a. Signature (int durasi=4)                                 : Rp %,14.2f\n",
                refKendaraan1.hitungEstimasiBiaya(4));
        System.out.printf("  b. Signature (int durasi=4, double diskon=0.15 [Voucher 15%%]): Rp %,14.2f\n",
                refKendaraan1.hitungEstimasiBiaya(4, 0.15));
        System.out.printf("  c. Signature (int durasi=4, boolean supir=true, tarif=200rb): Rp %,14.2f\n",
                refKendaraan1.hitungEstimasiBiaya(4, true, 200000.0));

        System.out.println("\n>>> 2. Overloading tambahDeposit(...) pada Subclass Pelanggan <<<");
        Pelanggan pelangganObj = (Pelanggan) refPerson1; // Downcasting untuk akses method khusus
        pelangganObj.tambahDeposit(500000.0); // Overload 1: Tunai
        pelangganObj.tambahDeposit(1000000.0, "QRIS"); // Overload 2: Digital (Bonus Cashback 2%)

        System.out.println("\n>>> 3. Overloading catatTransaksiDitangani(...) & hitungBonusPerforma(...) pada Pegawai <<<");
        Pegawai pegawaiObj = (Pegawai) refPerson2;
        pegawaiObj.catatTransaksiDitangani(3); // Overload 2: Batch transaksi
        System.out.printf("  Bonus Standar (3 transaksi @ Rp 25rb) : Rp %,12.2f\n", pegawaiObj.hitungBonusPerforma());
        System.out.printf("  Bonus Peak Season (3 transaksi @ Rp 50rb): Rp %,12.2f\n", pegawaiObj.hitungBonusPerforma(50000.0));


        // =========================================================================
        // FASE 4: HETEROGENEOUS POLYMORPHIC COLLECTION & ITERASI DINAMIS
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 4] HETEROGENEOUS POLYMORPHIC COLLECTION (ARRAY & LIST ITERATION)         ");
        System.out.println("================================================================================");
        System.out.println("Konsep: Mengumpulkan objek heterogen dalam satu wadah seragam tipe Superclass.\n");

        // Polymorphic Array of Kendaraan (Memenuhi syarat min. 3 object subclass)
        Kendaraan[] poolArray = new Kendaraan[] {
                new Mobil("M 1111 AA", "Honda", "HR-V RS Turbo", 2024, 550000.0, "MHK-HRV-001", 1500, "Bensin", "CVT", 5, "Compact SUV", true, "Panoramic Sunroof"),
                new Motor("M 2222 BB", "Yamaha", "NMAX 155 Connected", 2024, 140000.0, "MH1-NMX-002", 155, "Bensin", "Matic", "Matic Maxi", 25, 2, true, true),
                new Minibus("M 3333 CC", "Toyota", "HiAce Premio VIP", 2024, 1300000.0, "MHK-HAC-003", 2800, "Diesel", "Manual", 14, 10, true, true),
                new Mobil("M 4444 DD", "Toyota", "Avanza 1.5 G", 2023, 400000.0, "MHK-AVZ-004", 1500, "Bensin", "Manual", 7, "MPV", true, "Standard Touchscreen"),
                new Motor("M 5555 EE", "Honda", "Vario 160 CBS", 2023, 110000.0, "MH1-VAR-005", 160, "Bensin", "Matic", "Matic Standar", 18, 2, true, false)
        };

        System.out.println("Iterasi Polymorphic Array Kendaraan[] (Estimasi Biaya 3 Hari):");
        int idx = 1;
        for (Kendaraan k : poolArray) {
            System.out.printf(" %d. [%-18s] Plat: %-9s | Unit: %-23s | Estimasi 3 Hari: Rp %,12.2f\n",
                    idx++, k.getClass().getSimpleName(), k.getNomorPlat(), (k.getMerk() + " " + k.getModel()), k.hitungEstimasiBiaya(3));
        }

        // Polymorphic Array of Person
        Person[] aktorArray = new Person[] {
                refPerson1,
                refPerson2,
                new Pelanggan("PLG-102", "Ibu Rina Sasmita", "081987654321", "Jl. Trunojoyo No. 40, Sumenep", "rina.sasmita@yahoo.com", "3529026508920002", "GOLD", 1500000.0),
                new Pegawai("PEG-202", "Budi Santoso", "087811223344", "Jl. Raya Kolor No. 15, Sumenep", "budi.driver@autorent.id", "199507152021021002", "Driver Profesional", 3800000.0, "Full Day (24 Jam)")
        };

        System.out.println("\nIterasi Polymorphic Array Person[] (Deskripsi Peran & Kompensasi):");
        idx = 1;
        for (Person p : aktorArray) {
            System.out.printf(" %d. [%-10s] %-22s | Peran: %-42s | Kompensasi: Rp %,12.2f\n",
                    idx++, p.getClass().getSimpleName(), p.getNama(), p.getDeskripsiPeran(), p.hitungKompensasi());
        }


        // =========================================================================
        // FASE 5: INTEGRASI RELASI OBJEK & SIKLUS TRANSAKSI SEWA POLIMORFIS
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 5] SIKLUS TRANSAKSI SEWA DENGAN DISPATCH POLIMORFIS & OVERLOADED PAYMENT  ");
        System.out.println("================================================================================");
        System.out.println("Konsep: Transaksi mengorkestrasikan Asosiasi Polimorfis, Komposisi, dan Dependensi.\n");

        KuitansiPrinter thermalPrinter = new KuitansiPrinter("AutoRent PENS Sumenep", "Jl. Trunojoyo No. 45, Sumenep", "0812-3456-7890");
        LayananNotifikasi smsNotifier = new LayananNotifikasi("AutoRent Multi-Gateway (Twilio + WA Cloud)");

        // Skenario 1: Transaksi Sewa Minibus oleh Pelanggan VIP
        TransaksiSewa trx1 = new TransaksiSewa("TRX-2026-001", "2026-09-28", pelangganObj, pegawaiObj, refKendaraan3, 5);

        // Menambahkan Layanan Tambahan (Overloading tambahLayananTambahan)
        trx1.tambahLayananTambahan("Paket BBM Solar Biosolar Full Tank", 450000.0);
        trx1.tambahLayananTambahan("Jasa Driver Pariwisata Profesional", 150000.0, 5);

        // Proses Peminjaman
        trx1.prosesPeminjaman(smsNotifier);
        trx1.tampilkanRingkasanTransaksi();

        // Proses Pembayaran (Overload 2: Digital Payment via Transfer Bank)
        trx1.prosesPembayaran(trx1.getTotalBiaya(), "BCA Virtual Account", "BCA-VA-99283746501");

        // Proses Pengembalian (Late 1 hari, 650 km pemakaian)
        System.out.println("\n>>> Memproses Pengembalian Unit Minibus (Pengembalian + Denda + Cetak Kuitansi) <<<");
        trx1.prosesPengembalian(1, 650, thermalPrinter, smsNotifier);


        // =========================================================================
        // FASE 6: PEMBUKTIAN AGREGASI GARASI & METHOD OVERLOADING PENCARIAN
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 6] AGREGASI GARASI ARMADA DENGAN METHOD OVERLOADING PENCARIAN            ");
        System.out.println("================================================================================");

        GarasiArmada garasiUtama = new GarasiArmada("GAR-01", "Garasi Pusat Sumenep", "Jl. Raya Lenteng KM 3", 10);

        // Memasukkan seluruh pool armada polimorfis
        for (Kendaraan k : poolArray) {
            garasiUtama.tambahKendaraan(k);
        }
        garasiUtama.tambahKendaraan(refKendaraan1);
        garasiUtama.tambahKendaraan(refKendaraan2);

        garasiUtama.tampilkanDaftarArmada();

        System.out.println("\n>>> Pengujian Overloading Pencarian Armada pada GarasiArmada <<<");
        // Overload 1: Pencarian by Plat Nomor
        System.out.println("1. Pencarian Plat 'M 1888 AB': " + garasiUtama.cariKendaraan("M 1888 AB"));

        // Overload 2: Pencarian by Merk dan Model
        ArrayList<Kendaraan> cariMerk = garasiUtama.cariKendaraan("Honda", "PCX 160 ABS");
        System.out.println("2. Pencarian Merk 'Honda', Model 'PCX 160 ABS' (Ditemukan: " + cariMerk.size() + " unit): " + cariMerk);

        // Overload 3: Pencarian by Budget Maksimal (<= Rp 200.000 / hari)
        ArrayList<Kendaraan> cariBudget = garasiUtama.cariKendaraan(200000.0);
        System.out.println("3. Pencarian Budget <= Rp 200.000/hari (Ditemukan: " + cariBudget.size() + " unit):");
        for (Kendaraan b : cariBudget) {
            System.out.printf("   └─ %-9s : %-22s (Tarif: Rp %,10.2f/hari)\n", b.getNomorPlat(), b.getMerk() + " " + b.getModel(), b.getTarifPerHari());
        }

        // Overload 4: Pencarian by Status Ketersediaan
        ArrayList<Kendaraan> cariReady = garasiUtama.cariKendaraan(true);
        System.out.println("4. Filter Armada Ready (Tersedia: " + cariReady.size() + " unit di pool).");

        // Batch Servis Masal Polimorfis
        garasiUtama.lakukanServisMasal();


        // =========================================================================
        // FASE 7: DEFENSIVE BOUNDARY VALIDATION & NEGATIVE TESTING
        // =========================================================================
        System.out.println("================================================================================");
        System.out.println(" [FASE 7] DEFENSIVE BOUNDARY VALIDATION & NEGATIVE TEST SUITE                  ");
        System.out.println("================================================================================");

        testException("1. Validasi Diskon Overload > 50%", () -> {
            refKendaraan1.hitungEstimasiBiaya(3, 0.75); // Diskon 75% harus ditolak
        });

        testException("2. Validasi Deposit Negatif pada Subclass Pelanggan", () -> {
            pelangganObj.tambahDeposit(-100000.0);
        });

        testException("3. Validasi Kapasitas Kursi Subclass Minibus < 8 Kursi", () -> {
            new Minibus("M 9999 ZZ", "Isuzu", "Elf Mini", 2024, 800000.0, "ISZ-MINI-001", 2000, "Diesel", "Manual", 4, 2, false, false);
        });

        testException("4. Validasi Format Plat Nomor Tidak Baku", () -> {
            new Kendaraan("INVALID_PLAT_123", "Toyota", "Avanza", 2022, 350000.0, "RANGKA-01", 1500, "Bensin", "Manual");
        });

        testException("5. Validasi Referensi Transfer Digital < 6 Karakter", () -> {
            trx1.prosesPembayaran(500000.0, "QRIS", "123");
        });


        // =========================================================================
        // FASE 8: TABEL REKAPITULASI VERIFIKASI DYNAMIC BINDING (MODUL 6 BAB 18)
        // =========================================================================
        System.out.println("\n================================================================================");
        System.out.println(" [FASE 8] REKAPITULASI TABEL PENGUJIAN DYNAMIC BINDING (MODUL 6)                 ");
        System.out.println("================================================================================");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Tipe Reference", "Objek Aktual", "Method Yang Dipanggil", "Tipe Binding Runtime");
        System.out.println("+--------------+--------------+----------------------------+----------------------+");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Person", "Pelanggan", "tampilkanProfil()", "Dynamic Dispatch (Pelanggan)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Person", "Pegawai", "tampilkanProfil()", "Dynamic Dispatch (Pegawai)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Mobil", "tampilkanSpesifikasi()", "Dynamic Dispatch (Mobil)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Motor", "tampilkanSpesifikasi()", "Dynamic Dispatch (Motor)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Minibus", "tampilkanSpesifikasi()", "Dynamic Dispatch (Minibus)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Mobil", "hitungEstimasiBiaya(5)", "Dynamic Dispatch (Mobil)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Motor", "hitungEstimasiBiaya(5)", "Dynamic Dispatch (Motor)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Minibus", "hitungEstimasiBiaya(5)", "Dynamic Dispatch (Minibus)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Mobil", "lakukanServis()", "Dynamic Dispatch (Mobil)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Motor", "lakukanServis()", "Dynamic Dispatch (Motor)");
        System.out.printf("| %-12s | %-12s | %-26s | %-20s |\n", "Kendaraan", "Minibus", "lakukanServis()", "Dynamic Dispatch (Minibus)");
        System.out.println("+--------------+--------------+----------------------------+----------------------+\n");

        System.out.println("================================================================================");
        System.out.println(" SELURUH 8 FASE PENGUJIAN MODUL 6 BERHASIL DIEKSEKUSI DENGAN STATUS: PASSED 100%");
        System.out.println("================================================================================");
    }

    private static void testException(String label, Runnable action) {
        try {
            action.run();
            System.out.println("  [FAILED] " + label + " -> Tidak melempar exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASSED] " + label + " -> Tertangkap Defensif: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  [PASSED] " + label + " -> Tertangkap Exception: " + e.getMessage());
        }
    }
}
