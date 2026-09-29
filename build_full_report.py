import os
import sys
import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import parse_xml
from docx.oxml.ns import nsdecls

from docx_helpers import (
    set_cell_background,
    set_cell_margins,
    set_cell_borders,
    add_callout,
    add_code_block,
    add_terminal_output_block,
    format_heading,
    format_body,
    create_table_styled
)

def generate_laporan_modul_6():
    print("Starting generation of Laporan Praktikum Modul 6...")
    doc = docx.Document()
    
    # Page setup - Standard Letter / A4 with 1 inch margins
    section = doc.sections[0]
    section.page_width = Inches(8.5)
    section.page_height = Inches(11.0)
    section.left_margin = Inches(1.0)
    section.right_margin = Inches(1.0)
    section.top_margin = Inches(1.0)
    section.bottom_margin = Inches(1.0)
    
    # =========================================================================
    # TITLE & METADATA
    # =========================================================================
    p_title = doc.add_paragraph()
    p_title.paragraph_format.space_before = Pt(0)
    p_title.paragraph_format.space_after = Pt(2)
    p_title.alignment = WD_ALIGN_PARAGRAPH.CENTER
    
    run_t1 = p_title.add_run("LAPORAN PRAKTIKUM PEMROGRAMAN BERORIENTASI OBYEK\n")
    run_t1.font.name = "Calibri"
    run_t1.font.size = Pt(15.0)
    run_t1.font.bold = True
    run_t1.font.color.rgb = RGBColor(16, 44, 87) # 102C57
    
    run_t2 = p_title.add_run("MODUL 6: POLYMORPHISM, METHOD OVERRIDING, METHOD OVERLOADING, DAN DYNAMIC BINDING")
    run_t2.font.name = "Calibri"
    run_t2.font.size = Pt(13.0)
    run_t2.font.bold = True
    run_t2.font.color.rgb = RGBColor(53, 89, 143) # 35598F
    
    p_space = doc.add_paragraph()
    p_space.paragraph_format.space_before = Pt(0)
    p_space.paragraph_format.space_after = Pt(4)
    
    # Metadata Table
    meta_headers = ["Informasi Akademik", "Keterangan Terperinci"]
    meta_data = [
        ["Mata Kuliah", "Praktikum Pemrograman Berorientasi Obyek (PBO)"],
        ["Dosen Pengampu", "Nirwana Haidar Hari, S.Pd., M.Kom."],
        ["Nama Mahasiswa / NRP", "Ahmad Yasar Maulana Dais / 3125522018"],
        ["Program Studi & Kampus", "D3 Teknik Informatika - PENS PSDKU Sumenep (2026)"],
        ["Nama Proyek & Modul", "AutoRent System — Sprint 6: Dynamic Polymorphism, Overriding, Overloading & Dynamic Binding"]
    ]
    create_table_styled(doc, meta_headers, meta_data, [2.2, 4.6])
    
    # =========================================================================
    # BAB I: TUJUAN PRAKTIKUM & FONDASI AGILE SPRINT (SPRINT 6)
    # =========================================================================
    p = doc.add_paragraph()
    format_heading(p, "BAB I: TUJUAN PRAKTIKUM & FONDASI AGILE SPRINT (SPRINT 6)", 1)
    
    p = doc.add_paragraph()
    format_heading(p, "1.1 Tujuan Praktikum (Capaian Pembelajaran Modul 6)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Berdasarkan Modul Praktikum Pemrograman Berorientasi Obyek Modul 6 yang disusun oleh Nirwana Haidar Hari, S.Pd., M.Kom., tujuan instruksional dan kompetensi teknis yang wajib dikuasai oleh mahasiswa pada praktikum ini adalah sebagai berikut:\n"
                   "1. Mampu menjelaskan konsep Polymorphism (Polimorfisme) dalam paradigma Object-Oriented Programming secara mendalam dan komprehensif.\n"
                   "2. Mampu membedakan secara tegas mekanisme Method Overloading (Compile-Time / Static Polymorphism) dan Method Overriding (Runtime / Dynamic Polymorphism).\n"
                   "3. Mampu menerapkan anotasi @Override pada Subclass untuk mengubah dan mengkustomisasi perilaku (behavior) method yang diwarisi dari Superclass.\n"
                   "4. Mampu menggunakan Superclass Reference untuk menunjuk objek konkrit Subclass (mekanisme Upcasting / Polymorphic Reference).\n"
                   "5. Mampu menjelaskan proses Dynamic Binding (Dynamic Method Dispatch) yang dijalankan oleh Java Virtual Machine (JVM) pada saat waktu eksekusi (runtime).\n"
                   "6. Mampu menerapkan Polymorphic Behavior dan Heterogeneous Polymorphic Collection pada arsitektur proyek perangkat lunak.\n"
                   "7. Mampu melakukan refactoring fungsional terhadap proyek hasil Pertemuan 5 (AutoRent System) tanpa merusak struktur relasi objek yang telah dibangun.\n"
                   "8. Mampu menghasilkan project increment berkualitas tinggi melalui kerangka kerja Agile Sprint (Sprint 6).")
    
    p = doc.add_paragraph()
    format_heading(p, "1.2 Review P5 dan Jembatan Arsitektural Menuju P6", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Pada praktikum Pertemuan 5 (P5), proyek AutoRent System telah berhasil direfaktor dengan menerapkan konsep Inheritance dan Generalization. "
                   "Struktur entitas sistem telah diabstraksikan menjadi dua hierarki Superclass utama, yakni Superclass Person (dengan Subclass Pelanggan dan Pegawai) "
                   "serta Superclass Kendaraan (dengan Subclass Mobil dan Motor), di samping mempertahankan seluruh relasi kompleks P4 seperti Composition (Mesin dan DetailItemBiaya), "
                   "Aggregation (GarasiArmada), Association (TransaksiSewa), dan Dependency (KuitansiPrinter dan LayananNotifikasi).\n\n"
                   "Meskipun hierarki pewarisan pada P5 telah berhasil mengeliminasi redundansi atribut dan menyediakan mekanisme Constructor Chaining melalui keyword super(...), "
                   "perilaku (behavior) objek pada P5 masih bersifat statis dan kaku. Method-method pada Superclass hanya dieksekusi secara seragam atau ditimpa secara manual menggunakan nama method yang berbeda-beda (misalnya tampilkanProfil(), tampilkanDetailPelanggan(), tampilkanDetailMobil(), dan tampilkanDetailMotor()). "
                   "Pendekatan tersebut belum memanfaatkan potensi penuh Object-Oriented Programming, di mana satu antarmuka method yang sama seharusnya dapat merespons secara adaptif dan dinamis sesuai tipe objek konkrit yang sedang diproses.\n\n"
                   "Sesuai instruksi resmi Modul 6 Bab 12, mahasiswa TIDAK membuat proyek baru dari nol, melainkan melanjutkan dan menyempurnakan proyek AutoRent System hasil P5. "
                   "Fokus utama Sprint 6 adalah melakukan evolusi arsitektural dari sekadar pewarisan struktur data statis (Inheritance) menuju penerapan perilaku dinamis polimorfis (Dynamic Polymorphism), "
                   "sehingga seluruh armada rental dan aktor sistem dapat dioperasikan secara fleksibel melalui satu antarmuka pemanggilan yang seragam namun mengeksekusi logika bisnis yang terspesialisasi.")
    
    p = doc.add_paragraph()
    format_heading(p, "1.3 Agile Sprint Framework (Sprint Goal & Sprint Backlog Modul 6)", 2)
    
    add_callout(doc, 
                "Sprint Goal P6: Mengembangkan behavior object pada proyek AutoRent System melalui method overriding, "
                "method overloading, upcasting, polymorphic collection, dan dynamic binding sehingga subclass dapat merespons "
                "pemanggilan method yang sama dengan perilaku spesifik yang berbeda secara dinamis saat runtime, "
                "sekaligus memperkaya fleksibilitas operasional sistem rental terintegrasi.",
                "📌 SPRINT GOAL SPRINT 6 (MODUL 6)")
    
    p = doc.add_paragraph()
    format_body(p, "Tabel berikut menyajikan Sprint Backlog terperinci yang dieksekusi sepanjang iterasi Sprint 6 untuk mencapai Definition of Done:")
    
    sprint_headers = ["ID Backlog", "Deskripsi Aktivitas Sprint Backlog", "Target Deliverable", "Status Akhir"]
    sprint_data = [
        ["SB-01", "Audit Superclass & Subclass P5: Identifikasi behavior yang relevan untuk polimorfisme", "Matriks Audit Behavior & Refactoring Plan", "DONE (100%)"],
        ["SB-02", "Menentukan behavior yang dioverride & dirancang virtual pada Superclass", "Spesifikasi Method Overriding & Signature", "DONE (100%)"],
        ["SB-03", "Implementasi Method Overriding dengan anotasi @Override pada Subclass", "Subclass Mobil, Motor, Minibus, Pelanggan, Pegawai", "DONE (100%)"],
        ["SB-04", "Implementasi Method Overloading dengan variasi parameter yang relevan", "Overloaded Methods di Kendaraan, Garasi, Transaksi", "DONE (100%)"],
        ["SB-05", "Implementasi Upcasting & Polymorphic Reference pada Driver Main", "Superclass Reference menunjuk Objek Konkrit Subclass", "DONE (100%)"],
        ["SB-06", "Membuat Heterogeneous Polymorphic Collection (Array & ArrayList)", "Koleksi Heterogen Kendaraan[] & Person[]", "DONE (100%)"],
        ["SB-07", "Melakukan pengujian Dynamic Binding & Defensive Boundary Test Suite", "Test Suite Main.java 8 Fase Eksekusi Sukses 100%", "DONE (100%)"]
    ]
    create_table_styled(doc, sprint_headers, sprint_data, [1.0, 2.5, 2.3, 1.0], align_center=True)
    
    p = doc.add_paragraph()
    format_heading(p, "1.4 Bagian A – Audit Behavior pada Hierarki Proyek P5", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Sesuai arahan Bagian A Modul 6 (Bab 13), dilakukan audit menyeluruh terhadap hierarki class hasil P5 untuk mengidentifikasi perilaku (behavior/method) "
                   "yang dimiliki oleh Superclass namun memiliki implementasi kalkulasi bisnis, penyajian data, atau prosedur teknis yang berbeda secara spesifik pada masing-masing Subclass:")
    
    audit_headers = ["Hierarki Entitas", "Superclass", "Subclass Target", "Method yang Dioverride", "Perbedaan Behavior & Alasan Desain Bisnis"]
    audit_data = [
        ["Armada Rental", "Kendaraan", "Mobil", "hitungEstimasiBiaya(int)", "Menghitung tarif dasar + surcharge AC kabin (Rp 25rb/hr) + diskon otomatis 10% untuk sewa mingguan (>= 7 hari)."],
        ["Armada Rental", "Kendaraan", "Motor", "hitungEstimasiBiaya(int)", "Menghitung tarif dasar sewa motor + potongan diskon flat paket wisata city tour Rp 15rb untuk durasi >= 3 hari."],
        ["Armada Rental", "Kendaraan", "Minibus", "hitungEstimasiBiaya(int)", "Menghitung tarif dasar travel + asuransi keselamatan rombongan (Rp 5rb/kursi/hr) + diskon 12% sewa tour >= 5 hari."],
        ["Armada Rental", "Kendaraan", "Mobil, Motor, Minibus", "tampilkanSpesifikasi()", "Menampilkan spesifikasi teknis kabin/kursi/bagasi/fitur unik masing-masing kategori unit secara terperinci."],
        ["Armada Rental", "Kendaraan", "Mobil, Motor, Minibus", "lakukanServis()", "Mobil mengecek AC/rem ABS; Motor mengecek CVT/v-belt; Minibus mengecek rem angin/suspensi ganda rombongan."],
        ["Aktor Manusia", "Person", "Pelanggan", "tampilkanProfil()", "Menampilkan identitas NIK, level member (VIP/Gold/Reguler), saldo deposit, limit kuota sewa, dan hak reward cashback."],
        ["Aktor Manusia", "Person", "Pegawai", "tampilkanProfil()", "Menampilkan identitas NIP, jabatan kerja, jadwal shift, status tugas, performa transaksi kasir, dan rincian take-home pay."],
        ["Aktor Manusia", "Person", "Pelanggan & Pegawai", "hitungKompensasi()", "Pelanggan mengkalkulasi reward loyalty cashback; Pegawai mengkalkulasi akumulasi Gaji Pokok + Bonus Performa Kasir."]
    ]
    create_table_styled(doc, audit_headers, audit_data, [1.0, 1.0, 1.0, 1.4, 2.4])
    
    # =========================================================================
    # BAB II: KAJIAN TEORI & PERANCANGAN ARSITEKTUR POLIMORFISME
    # =========================================================================
    p = doc.add_paragraph()
    format_heading(p, "BAB II: KAJIAN TEORI & PERANCANGAN ARSITEKTUR POLIMORFISME", 1)
    
    p = doc.add_paragraph()
    format_heading(p, "2.1 Konsep Polymorphism (Compile-time vs Runtime Polymorphism)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Secara etimologis, Polymorphism berasal dari bahasa Yunani, yaitu poly (banyak) dan morph (bentuk). Dalam konteks pemrograman berorientasi obyek, "
                   "polymorphism adalah kemampuan suatu antarmuka pemanggilan (reference atau method signature) untuk merujuk pada bentuk atau perilaku yang berbeda-beda "
                   "tergantung pada konteks objek yang mengoperasikannya. Dalam ekosistem Java, polimorfisme diklasifikasikan menjadi dua jenis utama:\n\n"
                   "1. Compile-Time Polymorphism (Static Polymorphism): Polimorfisme yang diikat dan diselesaikan oleh compiler pada saat proses kompilasi kode sumber. "
                   "Bentuk utama dari tipe ini adalah Method Overloading. Compiler menentukan method mana yang akan dipanggil berdasarkan kesesuaian signature parameter (jumlah, tipe data, dan urutan argumen).\n\n"
                   "2. Runtime Polymorphism (Dynamic Polymorphism): Polimorfisme yang diselesaikan oleh Java Virtual Machine (JVM) saat program sedang berjalan (runtime). "
                   "Bentuk utama dari tipe ini adalah Method Overriding melalui mekanisme Dynamic Method Dispatch. Tipe reference superclass digunakan untuk mengakses objek subclass, "
                   "dan JVM secara dinamis menentukan implementasi method subclass yang paling spesifik berdasarkan objek aktual yang berada di memory heap.")
    
    p = doc.add_paragraph()
    format_heading(p, "2.2 Method Overriding & Anotasi @Override", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Method Overriding terjadi ketika sebuah Subclass mendefinisikan ulang method yang telah dideklarasikan pada Superclass-nya dengan nama method, daftar parameter, "
                   "dan return type (atau covariant return type) yang identik. Aturan baku overriding dalam bahasa Java meliputi:\n"
                   "• Signature Method Identik: Nama method, jumlah parameter, dan tipe data parameter pada subclass wajib persis sama dengan yang ada di superclass.\n"
                   "• Access Modifier: Akses modifier pada method subclass tidak boleh lebih restriktif daripada method superclass (misal method public di superclass tidak boleh diubah menjadi protected atau private di subclass).\n"
                   "• Anotasi @Override: Penggunaan anotasi @Override sangat dianjurkan (best practice) karena menginstruksikan compiler untuk memvalidasi bahwa method tersebut benar-benar meng-override method milik superclass. "
                   "Apabila terdapat kesalahan pengetikan nama atau perbedaan tipe parameter, compiler akan segera mengeluarkan pesan error sehingga mencegah timbulnya bug tersembunyi.\n"
                   "• Constructor dan Static Method: Konstruktor tidak diwarisi sehingga tidak dapat dioverride. Method static terikat pada class (bukan instance) sehingga hanya dapat di-hide (method hiding), bukan dioverride.")
    
    p = doc.add_paragraph()
    format_heading(p, "2.3 Method Overloading & Resolusi Signature Compile-Time", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Method Overloading terjadi ketika dalam satu class (atau dalam hierarki inheritance) terdapat beberapa method yang memiliki nama yang sama persis, "
                   "tetapi memiliki daftar parameter yang berbeda. Perbedaan parameter dapat berupa:\n"
                   "1. Jumlah parameter yang berbeda (contoh: hitungEstimasiBiaya(int) vs hitungEstimasiBiaya(int, double)).\n"
                   "2. Tipe data parameter yang berbeda (contoh: cariKendaraan(String) vs cariKendaraan(double)).\n"
                   "3. Urutan tipe data parameter yang berbeda.\n\n"
                   "Catatan penting: Perbedaan return type saja TANPA adanya perbedaan parameter BUKAN merupakan overloading yang valid dan akan menyebabkan kesalahan kompilasi. "
                   "Overloading memberikan fleksibilitas tinggi bagi pemanggil method (caller) untuk menyediakan argumen dalam berbagai format yang paling sesuai dengan skenario bisnis tanpa harus mengingat banyak nama method yang berbeda.")
    
    p = doc.add_paragraph()
    format_heading(p, "2.4 Upcasting, Downcasting, dan Polymorphic Reference", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Mekanisme pengubahan tipe reference dalam hierarki inheritance terdiri dari:\n\n"
                   "1. Upcasting (Pengecoran ke Atas): Konversi reference dari tipe Subclass ke tipe Superclass. Upcasting bersifat otomatis (implicit widening) dan selalu aman (type-safe) "
                   "karena Subclass selalu memenuhi relasi semantik is-a terhadap Superclass-nya.\n"
                   "   Contoh: Kendaraan k = new Mobil(...); // Objek Mobil diperlakukan sebagai Kendaraan\n\n"
                   "2. Downcasting (Pengecoran ke Bawah): Konversi reference dari tipe Superclass kembali ke tipe konkrit Subclass. Downcasting memerlukan casting eksplisit "
                   "karena reference superclass belum tentu merujuk pada subclass yang dituju.\n"
                   "   Contoh: Mobil m = (Mobil) k; // Diperlukan agar dapat memanggil method khusus Mobil seperti pasangAksesoris()\n\n"
                   "3. Polymorphic Reference: Penggunaan variabel bertipe Superclass untuk menampung referensi objek-objek Subclass yang berbeda-beda. "
                   "Polymorphic reference memungkinkan penulisan kode program yang sangat modular, generik, dan extensible (mudah diperluas tanpa mengubah kode yang sudah ada).")
    
    p = doc.add_paragraph()
    format_heading(p, "2.5 Mekanisme Dynamic Binding (Dynamic Method Dispatch & vtable)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Dynamic Binding (Dynamic Method Dispatch) adalah mekanisme di mana pemanggilan method yang dioverride diselesaikan saat runtime, bukan saat compile time. "
                   "Ketika sebaris kode program seperti k.hitungEstimasiBiaya(5) dieksekusi:\n"
                   "1. Compiler memeriksa tipe reference variabel k (yaitu Kendaraan) di Stack Memory untuk memastikan bahwa method hitungEstimasiBiaya(int) memang dideklarasikan dan dapat diakses secara legal.\n"
                   "2. Pada saat runtime, JVM memeriksa objek aktual yang dialokasikan di Heap Memory yang ditunjuk oleh reference k (misalnya instance Mobil atau Minibus).\n"
                   "3. JVM menggunakan Virtual Method Table (vtable)—suatu tabel penunjuk fungsi internal yang melekat pada setiap class—untuk menavigasi ke implementasi method milik objek aktual tersebut.\n"
                   "4. Bytecode Java yang dihasilkan adalah instruksi invokevirtual, yang secara otomatis melompat ke alamat memori method milik subclass konkrit, bukan method milik superclass.\n\n"
                   "Dengan demikian, meskipun tipe referensinya sama-sama Kendaraan, pemanggilan k.tampilkanSpesifikasi() pada objek Mobil akan memunculkan informasi kabin dan AC, "
                   "sedangkan pada objek Motor akan memunculkan informasi bagasi helm dan jas hujan.")
    
    p = doc.add_paragraph()
    format_heading(p, "2.6 Matriks Perbandingan Komprehensif: Overloading vs Overriding", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Tabel perbandingan berikut menyajikan perbedaan fundamental antara Method Overloading dan Method Overriding berdasarkan 8 dimensi arsitektural:")
    
    compare_headers = ["Dimensi Pembanding", "Method Overloading (Static Polymorphism)", "Method Overriding (Dynamic Polymorphism)"]
    compare_data = [
        ["Definisi & Esensi", "Mendefinisikan beberapa method bernama sama dengan parameter berbeda.", "Mendefinisikan ulang method superclass pada subclass dengan signature sama."],
        ["Lokasi Deklarasi", "Umumnya terjadi dalam satu class yang sama (atau diwariskan).", "Wajib terjadi antara Superclass dan Subclass (Hierarki Inheritance)."],
        ["Signature Parameter", "Wajib Berbeda (jumlah parameter, tipe data, atau urutan tipe).", "Wajib Persis Sama (nama method, jumlah, dan tipe data parameter identik)."],
        ["Tipe Return (Return Type)", "Boleh sama maupun berbeda (tidak mempengaruhi keabsahan overload).", "Wajib sama atau merupakan tipe turunannya (Covariant Return Type)."],
        ["Access Modifier", "Bebas diubah tanpa batasan aksesibilitas.", "Tidak boleh lebih restriktif dari method Superclass."],
        ["Private / Static Methods", "Dapat dioverload secara bebas dalam class yang sama.", "Tidak dapat dioverride (Private terisolasi, Static hanya bisa di-hide)."],
        ["Waktu Resolusi Binding", "Compile-Time Binding (Static Binding oleh Java Compiler).", "Runtime Binding (Dynamic Method Dispatch oleh Java Virtual Machine)."],
        ["Tujuan & Nilai Desain", "Menyediakan variasi pemanggilan method yang fleksibel bagi caller.", "Menghasilkan spesialisasi perilaku yang berbeda sesuai objek aktual."]
    ]
    create_table_styled(doc, compare_headers, compare_data, [1.5, 2.6, 2.7])
    
    p = doc.add_paragraph()
    format_heading(p, "2.7 Pembaruan UML Class Diagram (Modul 6 Architecture)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Sesuai arahan Bagian F & Update Class Diagram Modul 6 (Bab 19), class diagram AutoRent System diperbarui secara komprehensif. "
                   "Diagram berikut menampilkan seluruh Superclass, Subclass, notasi Generalization (▲), method yang dioverride (@Override), method overloading, "
                   "serta relasi komposisi, agregasi, asosiasi, dan dependensi P4-P5 yang tetap terjaga utuh:")
    
    uml_diagram_text = (
        "+---------------------------------------------------------------------------------------------------------+\n"
        "|                                     AUTORENT SYSTEM - UML CLASS DIAGRAM                                 |\n"
        "|                                (MODUL 6: DYNAMIC POLYMORPHISM & OVERLOADING)                            |\n"
        "+---------------------------------------------------------------------------------------------------------+\n\n"
        "     +----------------------------------------------------+       +------------------------------------------------+\n"
        "     |                 <<Superclass>>                     |       |                <<Superclass>>                  |\n"
        "     |                     Person                         |       |                  Kendaraan                     |\n"
        "     +----------------------------------------------------+       +------------------------------------------------+\n"
        "     | - id: String                                       |       | - nomorPlat: String                            |\n"
        "     | - nama: String                                     |       | - merk: String                                 |\n"
        "     | - nomorTelepon: String                             |       | - model: String                                |\n"
        "     | - alamat: String                                   |       | - tahunProduksi: int                           |\n"
        "     | - email: String                                    |       | - tarifPerHari: double                         |\n"
        "     +----------------------------------------------------+       | - statusTersedia: boolean                      |\n"
        "     | + tampilkanProfil(): void                          |       | - mesin: Mesin [1]                             |\n"
        "     | + getDeskripsiPeran(): String                      |       | - totalKaliDisewa: int                         |\n"
        "     | + hitungKompensasi(): double                       |       +------------------------------------------------+\n"
        "     +----------------------------------------------------+       | + tampilkanSpesifikasi(): void                 |\n"
        "               ▲                                ▲                 | + hitungEstimasiBiaya(durasi: int): double     |\n"
        "               │                                │                 | + hitungEstimasiBiaya(dur, diskon): double     |\n"
        "        (Generalization)                 (Generalization)         | + hitungEstimasiBiaya(dur, supir, trf): double |\n"
        "               │                                │                 | + getTipeKendaraan(): String                   |\n"
        "     +---------┴--------------+     +-----------┴-------------+   | + lakukanServis(): void                        |\n"
        "     |       Pelanggan        |     |         Pegawai         |   +------------------------------------------------+\n"
        "     +------------------------+     +-------------------------+            ▲               ▲               ▲\n"
        "     | - nomorKtp: String     |     | - nip: String           |            │ (Gen)         │ (Gen)         │ (Gen)\n"
        "     | - levelMember: String  |     | - jabatan: String       |     +------┴-------+ +-----┴-------+ +-----┴-------+\n"
        "     | - depositSaldo: double |     | - gajiPokok: double     |     |    Mobil     | |    Motor     | |   Minibus    |\n"
        "     | - jumlahSewaAktif: int |     | - shiftKerja: String    |     +--------------+ +-------------+ +--------------+\n"
        "     +------------------------+     | - totalTransaksi: int   |     | - kapasitas  | | - bagasi    | | - kursi      |\n"
        "     | + @Override tampilkan  |     +-------------------------+     | - tipeMobil  | | - tipeMotor | | - bagasiKoper|\n"
        "     | + @Override peran      |     | + @Override tampilkan   |     | - fasilitasAC| | - helm      | | - karaoke    |\n"
        "     | + @Override kompensasi |     | + @Override peran       |     | - entertain  | | - jasHujan  | | - wajibSupir |\n"
        "     | + tambahDeposit(nom)   |     | + @Override kompensasi  |     +--------------+ +-------------+ +--------------+\n"
        "     | + tambahDeposit(n, met)|     | + catatTransaksi()      |     | + @Override  | | + @Override | | + @Override  |\n"
        "     | + getDiskonMember()    |     | + catatTransaksi(batch) |     |   spesifikasi| |  spesifikasi| |   spesifikasi|\n"
        "     | + getDiskonMember(dur) |     | + hitungBonus()         |     | + @Override  | | + @Override | | + @Override  |\n"
        "     +------------------------+     | + hitungBonus(insentif) |     |   hitungBiaya| |  hitungBiaya| |   hitungBiaya|\n"
        "                                    +-------------------------+     | + @Override  | | + @Override | | + @Override  |\n"
        "                                                                    |   servis()   | |   servis()  | |   servis()   |\n"
        "                                                                    | + pasangAks()| | + tambahFas()| | + konfigurasi|\n"
        "                                                                    +--------------+ +-------------+ +--------------+\n\n"
        "     RELASI OBJEK TERINTEGRASI (COMPOSITION, AGGREGATION, ASSOCIATION, DEPENDENCY):\n"
        "     • Kendaraan   ◆───────> 1 Mesin (Composition: Mesin dirakit internal di dalam konstruktor Kendaraan)\n"
        "     • GarasiArmada ◇───────> 0..* Kendaraan (Heterogeneous Aggregation: Pool penampung polimorfis)\n"
        "     • TransaksiSewa ◆──────> 1..* DetailItemBiaya (Composition: Rincian item tagihan sewa)\n"
        "     • TransaksiSewa ───────> 1 Pelanggan & 1 Pegawai & 1 Kendaraan (Polymorphic Association)\n"
        "     • TransaksiSewa - - - - > KuitansiPrinter & LayananNotifikasi (Dependency Utility Execution)\n"
    )
    add_callout(doc, uml_diagram_text, "📌 UML CLASS DIAGRAM REFACTORING STRUCTURE (MODUL 6)")
    
    # =========================================================================
    # BAB III: IMPLEMENTASI KODE PROGRAM JAVA (SRC)
    # =========================================================================
    p = doc.add_paragraph()
    format_heading(p, "BAB III: IMPLEMENTASI KODE PROGRAM JAVA (SRC)", 1)
    
    p = doc.add_paragraph()
    format_heading(p, "3.1 Arsitektur Hierarki & Implementasi Overriding pada Superclass & Subclass", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Arsitektur kode program pada direktori src/ dirancang dengan mematuhi prinsip Clean Code, SOLID Principles, dan Defensiveness:\n"
                   "• Superclass Person & Kendaraan: Berperan sebagai tipe reference universal (Polymorphic Reference) yang mendefinisikan method-method virtual "
                   "seperti tampilkanProfil(), getDeskripsiPeran(), hitungKompensasi(), tampilkanSpesifikasi(), hitungEstimasiBiaya(int), dan lakukanServis().\n"
                   "• Subclass Pelanggan, Pegawai, Mobil, Motor, dan Minibus: Meng-override method superclass dengan menambahkan anotasi @Override. "
                   "Masing-masing subclass mengimplementasikan logika spesialisasi domain (contoh: Mobil menambahkan kalkulasi AC dan diskon mingguan; "
                   "Motor menambahkan diskon paket city tour; Minibus menambahkan proteksi asuransi kursi rombongan; Pelanggan mengkalkulasi cashback loyalitas; "
                   "dan Pegawai menghitung kompensasi take-home pay dari gaji pokok dan bonus transaksi).")
    
    p = doc.add_paragraph()
    format_heading(p, "3.2 Implementasi Overloading yang Relevan dan Berorientasi Solusi", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Sesuai Bagian C Modul 6 (Bab 15), method overloading diimplementasikan dengan pertimbangan arsitektur dan kegunaan bisnis yang nyata:\n"
                   "1. Kendaraan / Mobil / Motor / Minibus: hitungEstimasiBiaya(int durasi), hitungEstimasiBiaya(int durasi, double diskonVoucher), dan hitungEstimasiBiaya(int durasi, boolean supir, double tarifSupir).\n"
                   "2. Pelanggan: tambahDeposit(double nominal) untuk tunai dan tambahDeposit(double nominal, String metode) untuk channel QRIS/Transfer dengan cashback otomatis.\n"
                   "3. Pegawai: catatTransaksiDitangani() (+1 transaksi) dan catatTransaksiDitangani(int batch) untuk penanganan transaksi rombongan.\n"
                   "4. GarasiArmada: cariKendaraan(String nomorPlat), cariKendaraan(String merk, String model), cariKendaraan(double maxTarif), dan cariKendaraan(boolean hanyaTersedia).\n"
                   "5. TransaksiSewa: prosesPembayaran(double cash) dan prosesPembayaran(double nominal, String metode, String refNo) untuk pembayaran elektronik.")
    
    p = doc.add_paragraph()
    format_heading(p, "3.3 Dokumentasi Listing Kode Sumber Java (14 File Source Code)", 2)
    
    # Read and insert all 14 java files
    src_dir = os.path.join(os.getcwd(), 'src')
    file_order = [
        ('Person.java', 'Superclass Person — Fondasi Atribut, Validasi Defensif & Virtual Methods'),
        ('Pelanggan.java', 'Subclass Pelanggan extends Person — Overriding Profil & Overloading Deposit'),
        ('Pegawai.java', 'Subclass Pegawai extends Person — Overriding Kompensasi & Overloading Bonus'),
        ('Kendaraan.java', 'Superclass Kendaraan — Fondasi Armada, Komposisi Mesin & Virtual Methods'),
        ('Mobil.java', 'Subclass Mobil extends Kendaraan — Overriding Spesifikasi, Surcharge AC & Diskon'),
        ('Motor.java', 'Subclass Motor extends Kendaraan — Overriding Spesifikasi & Promo City Tour'),
        ('Minibus.java', 'Subclass Minibus extends Kendaraan — Overriding Travel Rombongan & Asuransi Kursi'),
        ('Mesin.java', 'Komponen Komposisi Mesin — Melekat Eksklusif pada Kendaraan'),
        ('GarasiArmada.java', 'Entitas Agregasi GarasiArmada — Heterogeneous Pool & Overloading Pencarian'),
        ('DetailItemBiaya.java', 'Komponen Komposisi DetailItemBiaya — Rincian Komponen Tagihan Rental'),
        ('TransaksiSewa.java', 'Sentral Orkestrasi TransaksiSewa — Polymorphic Association & Overloading Bayar'),
        ('KuitansiPrinter.java', 'Service Utility KuitansiPrinter — Format Struk Thermal POS Kasir'),
        ('LayananNotifikasi.java', 'Service Utility LayananNotifikasi — Gateway SMS / WhatsApp Notifier'),
        ('Main.java', 'Driver & Comprehensive Test Suite Main.java (8 Fase Pengujian Modul 6)')
    ]
    
    for fname, fdesc in file_order:
        fpath = os.path.join(src_dir, fname)
        if os.path.exists(fpath):
            with open(fpath, 'r', encoding='utf-8') as src_f:
                code_content = src_f.read()
            p_desc = doc.add_paragraph()
            format_body(p_desc, fdesc, f"File: src/{fname} — ")
            add_code_block(doc, f"src/{fname}", code_content)
        else:
            print(f"Warning: File {fpath} not found!")

    # =========================================================================
    # BAB IV: PENGUJIAN SISTEM, TABEL DYNAMIC BINDING & LOG EKSEKUSI
    # =========================================================================
    p = doc.add_paragraph()
    format_heading(p, "BAB IV: PENGUJIAN SISTEM, TABEL DYNAMIC BINDING & LOG EKSEKUSI", 1)
    
    p = doc.add_paragraph()
    format_heading(p, "4.1 Skenario Pengujian Komprehensif (8 Fase Pengujian)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Pengujian sistem AutoRent pada Modul 6 dirancang secara terstruktur dan terotomatisasi ke dalam 8 Fase Pengujian menyeluruh pada Main.java:\n"
                   "• Fase 1: Pembuktian Upcasting & Polymorphic Reference pada hierarki Person dan Kendaraan.\n"
                   "• Fase 2: Pembuktian Method Overriding & Dynamic Binding saat runtime (tampilkanProfil(), tampilkanSpesifikasi(), hitungEstimasiBiaya(5), lakukanServis()).\n"
                   "• Fase 3: Pembuktian Method Overloading dengan variasi parameter (diskon kupon, jasa supir, deposit digital promo, bonus performa pegawai).\n"
                   "• Fase 4: Pembuktian Heterogeneous Polymorphic Collection & Iterasi Dinamis (Kendaraan[] dan Person[] menampung objek heterogen).\n"
                   "• Fase 5: Pembuktian Siklus Transaksi Sewa Terintegrasi (Pemesanan Minibus oleh VIP, Add-on BBM/Driver, Pembayaran Digital Overload, Pengembalian & Cetak Kuitansi).\n"
                   "• Fase 6: Pembuktian Agregasi GarasiArmada dengan Multi-Criteria Overloaded Searches dan Servis Masal Polimorfis.\n"
                   "• Fase 7: Pembuktian Defensive Boundary Validation & Negative Testing (Menolak diskon ilegal > 50%, deposit negatif, kursi minibus < 8, plat nomor tidak baku, dan no referensi cacat).\n"
                   "• Fase 8: Rekapitulasi Tabel Verifikasi Dynamic Binding Modul 6.")
    
    p = doc.add_paragraph()
    format_heading(p, "4.2 Matriks & Tabel Pengujian Dynamic Binding (Modul 6 Bab 18)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Berdasarkan arahan resmi Modul 6 Bab 18 (Bagian F – Pengujian Dynamic Binding), tabel berikut membuktikan secara empiris bahwa method yang dieksekusi "
                   "ditentukan berdasarkan objek aktual yang berada di Heap Memory (Dynamic Binding), bukan tipe variabel reference di Stack:")
    
    db_headers = ["Tipe Reference", "Objek Aktual (Heap)", "Method yang Dipanggil", "Output yang Dihasilkan", "Tipe Resolusi Binding"]
    db_data = [
        ["Person", "Pelanggan", "tampilkanProfil()", "Menampilkan data NIK, level member VIP, kuota sewa, dan saldo deposit.", "Dynamic Binding (Pelanggan)"],
        ["Person", "Pegawai", "tampilkanProfil()", "Menampilkan data NIP, jabatan kasir, shift kerja, dan total take-home pay.", "Dynamic Binding (Pegawai)"],
        ["Person", "Pelanggan", "hitungKompensasi()", "Rp 75.000,00 (Kalkulasi reward cashback loyalitas 3% dari deposit VIP).", "Dynamic Binding (Pelanggan)"],
        ["Person", "Pegawai", "hitungKompensasi()", "Rp 4.575.000,00 (Kalkulasi Gaji Pokok + Bonus Performa Transaksi Kasir).", "Dynamic Binding (Pegawai)"],
        ["Kendaraan", "Mobil", "tampilkanSpesifikasi()", "Menampilkan spesifikasi kabin MPV, AC Double Blower, dan 7 kursi penumpang.", "Dynamic Binding (Mobil)"],
        ["Kendaraan", "Motor", "tampilkanSpesifikasi()", "Menampilkan bagasi 30L, 2 helm SNI gratis, jas hujan, dan phone holder.", "Dynamic Binding (Motor)"],
        ["Kendaraan", "Minibus", "tampilkanSpesifikasi()", "Menampilkan kapasitas 16 kursi rombongan, bagasi 12 koper, dan karaoke TV.", "Dynamic Binding (Minibus)"],
        ["Kendaraan", "Mobil", "hitungEstimasiBiaya(5)", "Rp 3.125.000,00 (Tarif 600rb x 5 + Surcharge AC 25rb x 5).", "Dynamic Binding (Mobil)"],
        ["Kendaraan", "Motor", "hitungEstimasiBiaya(5)", "Rp 735.000,00 (Tarif 150rb x 5 - Promo City Tour Rp 15rb).", "Dynamic Binding (Motor)"],
        ["Kendaraan", "Minibus", "hitungEstimasiBiaya(5)", "Rp 5.192.000,00 (Tarif 1.1jt x 5 + Asuransi Kursi - Diskon Tour 12%).", "Dynamic Binding (Minibus)"],
        ["Kendaraan", "Mobil", "lakukanServis()", "Inspeksi mesin + Penggantian Filter Kabin AC + Cek Rem ABS 4 Roda.", "Dynamic Binding (Mobil)"],
        ["Kendaraan", "Motor", "lakukanServis()", "Inspeksi mesin + Kuras Oli Gardan CVT + Pembersihan Roller & Rem Cakram.", "Dynamic Binding (Motor)"],
        ["Kendaraan", "Minibus", "lakukanServis()", "Inspeksi mesin diesel + Uji Kompresor Rem Angin + Kalibrasi Suspensi Daun.", "Dynamic Binding (Minibus)"]
    ]
    create_table_styled(doc, db_headers, db_data, [1.0, 1.1, 1.4, 2.3, 1.0])
    
    p = doc.add_paragraph()
    format_heading(p, "4.3 Log Output Eksekusi Terminal Java Konsol Lengkap", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Berikut adalah rekaman output terminal riil tanpa pemotongan dari hasil kompilasi javac -encoding UTF-8 -d bin src/*.java dan eksekusi java -cp bin Main:")
    
    # Read output_p6.txt
    output_file = os.path.join(os.getcwd(), 'output_p6.txt')
    if os.path.exists(output_file):
        with open(output_file, 'r', encoding='utf-8', errors='ignore') as log_f:
            full_log = log_f.read()
    else:
        full_log = "[Log Output Execution Not Found]"
        
    add_terminal_output_block(doc, full_log)
    
    p = doc.add_paragraph()
    format_heading(p, "4.4 Analisis & Pembahasan Mendalam Hasil Pengujian", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Berdasarkan log eksekusi pada 8 fase pengujian di atas, dapat ditarik beberapa poin analisis krusial:\n\n"
                   "1. Pembuktian Dynamic Method Dispatch (Fase 1, 2, dan 8): "
                   "Tampak jelas pada log pengujian bahwa variabel reference refKendaraan1, refKendaraan2, dan refKendaraan3 seluruhnya dideklarasikan dengan tipe Superclass Kendaraan. "
                   "Namun, ketika method tampilkanSpesifikasi() dan hitungEstimasiBiaya(5) dipanggil, output yang muncul secara otomatis mengeksekusi logika Subclass Mobil, Motor, dan Minibus. "
                   "Hal ini membuktikan bahwa JVM berhasil melakukan dynamic lookup pada runtime table (vtable) sesuai instance konkrit di Heap Memory.\n\n"
                   "2. Efektivitas Method Overloading (Fase 3 & 6): "
                   "Method hitungEstimasiBiaya(...) pada Mobil berhasil dipanggil dengan 3 variasi parameter (durasi saja, durasi + voucher promo 15%, dan durasi + paket driver). "
                   "Pada GarasiArmada, metode cariKendaraan(...) berhasil membedakan pencarian berdasarkan Plat Nomor (1 unit), Merk/Model (list), Range Budget Tarif (list), dan Status Ketersediaan "
                   "secara transparan tanpa konflik signature.\n\n"
                   "3. Ketahanan Heterogeneous Collection (Fase 4 & 6): "
                   "Array Kendaraan[] dan struktur data ArrayList<Kendaraan> pada GarasiArmada terbukti mampu mengelola berbagai tipe subclass (Mobil, Motor, Minibus) dalam satu koleksi terpadu. "
                   "Saat metode lakukanServisMasal() dieksekusi, JVM mengeksekusi prosedur servis yang tepat untuk masing-masing tipe kendaraan secara berurutan.\n\n"
                   "4. Keandalan Defensive Validation (Fase 7): "
                   "Seluruh uji batas negatif (diskon ilegal > 50%, top-up deposit negatif, kursi minibus < 8, nomor plat cacat, dan referensi transfer tidak valid) berhasil ditangkap 100% "
                   "oleh blok validasi defensif dengan melempar IllegalArgumentException yang informatif.")
    
    # =========================================================================
    # BAB V: SPRINT EVALUATION, DEFINITION OF DONE & PENUTUP
    # =========================================================================
    p = doc.add_paragraph()
    format_heading(p, "BAB V: SPRINT EVALUATION, DEFINITION OF DONE & PENUTUP", 1)
    
    p = doc.add_paragraph()
    format_heading(p, "5.1 Sprint Review (Format Resmi Modul 6 Bab 21)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Tabel berikut menyajikan evaluasi pencapaian hasil Sprint 6 sesuai format resmi Modul 6 Bab 21:")
    
    review_headers = ["Item Evaluasi Sprint Review", "Hasil Verifikasi & Bukti Implementasi"]
    review_data = [
        ["Superclass dan subclass tersedia", "Superclass Person & Kendaraan; Subclass Pelanggan, Pegawai, Mobil, Motor, Minibus tersedia lengkap."],
        ["Method overriding berhasil", "Seluruh method virtual (@Override tampilkanProfil, tampilkanSpesifikasi, hitungEstimasiBiaya, lakukanServis) berjalan sukses."],
        ["Minimal 2 subclass memiliki behavior berbeda", "Terpenuhi 100% (3 Subclass Kendaraan: Mobil, Motor, Minibus; 2 Subclass Person: Pelanggan, Pegawai memiliki kalkulasi & output unik)."],
        ["Method overloading tersedia", "Tersedia overload di Kendaraan/Mobil (hitungEstimasiBiaya 3 variasi), Pelanggan (tambahDeposit 2 variasi), Garasi (cari 4 variasi), Transaksi (bayar 2 variasi)."],
        ["Upcasting berhasil", "Berhasil dibuktikan pada Fase 1: Superclass reference Person dan Kendaraan menunjuk objek subclass konkrit."],
        ["Polymorphic collection berhasil", "Berhasil dibuktikan pada Fase 4 & 6: Array Kendaraan[], Person[], dan ArrayList<Kendaraan> di GarasiArmada."],
        ["Dynamic binding dapat dibuktikan", "Berhasil dibuktikan pada Fase 2 & 8: Pemanggilan method seragam menghasilkan eksekusi spesifik objek aktual runtime."],
        ["Program berjalan", "Program 100% terkompilasi bersih tanpa error dan berhasil mengeksekusi seluruh 8 fase pengujian dengan status PASSED."],
        ["Kendala", "Tidak ditemukan kendala struktural yang menghambat; seluruh konsep polimorfisme terintegrasi mulus dengan relasi objek P4-P5."]
    ]
    create_table_styled(doc, review_headers, review_data, [2.5, 4.3])
    
    p = doc.add_paragraph()
    format_heading(p, "5.2 Sprint Retrospective (Format Resmi Modul 6 Bab 22)", 2)
    
    add_callout(doc,
                "• What Went Well?\n"
                "  Penerapan method overriding pada hierarki Kendaraan (kalkulasi tarif spesifik AC untuk Mobil, promo wisata untuk Motor, dan proteksi asuransi kursi untuk Minibus) "
                "  serta hierarki Person (cashback untuk Pelanggan dan take-home pay untuk Pegawai) berhasil diimplementasikan secara elegan menggunakan anotasi @Override. "
                "  Heterogeneous Polymorphic Collection dan Dynamic Method Dispatch terbukti sangat memudahkan otomatisasi batch operation seperti servis rutin masal pada GarasiArmada.\n\n"
                "• What Went Wrong?\n"
                "  Tantangan utama yang dihadapi adalah memastikan method overloading tidak menimbulkan ambiguitas tipe data parameter saat dipanggil oleh compiler, "
                "  serta memastikan bahwa return type pada method overriding tetap konsisten dan aman dari type casting error saat downcasting diperlukan.\n\n"
                "• Improvement (Rencana Peningkatan Arsitektur Menuju P7):\n"
                "  Pada Sprint berikutnya (P7), Superclass Person dan Kendaraan sebaiknya dideklarasikan sebagai Abstract Class karena keduanya merupakan konsep generik yang tidak seharusnya diinstansiasi langsung. "
                "  Selain itu, fungsionalitas seperti Servis dan Notifikasi dapat diabstraksikan menjadi Interface untuk menerapkan kontrak perilaku yang lebih modular dan decoupling.",
                "📌 SPRINT RETROSPECTIVE (SPRINT 6 / P6)")
    
    p = doc.add_paragraph()
    format_heading(p, "5.3 Checklist Ketercapaian Definition of Done (17 Butir Modul 6 Bab 23)", 2)
    
    dod_headers = ["No", "Kriteria Definition of Done (Modul 6 Bab 23)", "Status Ketercapaian"]
    dod_data = [
        ["1", "Menggunakan basis project P5 (AutoRent System kelanjutan P4-P5)", "TERPENUHI (100%)"],
        ["2", "Minimal 1 superclass digunakan (Diimplementasikan 2 Superclass: Person & Kendaraan)", "TERPENUHI (100%)"],
        ["3", "Minimal 2 subclass digunakan (Diimplementasikan 5 Subclass: Pelanggan, Pegawai, Mobil, Motor, Minibus)", "TERPENUHI (100%)"],
        ["4", "Terdapat method pada superclass yang dioverride (tampilkanProfil, tampilkanSpesifikasi, hitungEstimasiBiaya, lakukanServis)", "TERPENUHI (100%)"],
        ["5", "Minimal 2 subclass melakukan overriding (Dilakukan oleh seluruh 5 Subclass)", "TERPENUHI (100%)"],
        ["6", "Menggunakan annotation @Override secara eksplisit dan konsisten", "TERPENUHI (100%)"],
        ["7", "Terdapat minimal 1 contoh method overloading yang relevan (Diimplementasikan > 10 variasi overload)", "TERPENUHI (100%)"],
        ["8", "Terdapat penggunaan upcasting (Superclass Reference menunjuk Objek Subclass)", "TERPENUHI (100%)"],
        ["9", "Terdapat polymorphic reference yang aktif dan valid", "TERPENUHI (100%)"],
        ["10", "Minimal 3 object digunakan dalam pengujian polymorphism (Diuji > 7 objek heterogen)", "TERPENUHI (100%)"],
        ["11", "Dynamic binding berhasil ditunjukkan dan dibuktikan melalui log terminal & tabel pengujian", "TERPENUHI (100%)"],
        ["12", "Class Diagram diperbarui dengan notasi overriding, overloading, dan relasi P4-P5", "TERPENUHI (100%)"],
        ["13", "Program berhasil dikompilasi (javac) tanpa error dan tanpa warning", "TERPENUHI (100%)"],
        ["14", "Program berhasil dijalankan (java) dengan output log yang sesuai dan tervalidasi", "TERPENUHI (100%)"],
        ["15", "Sprint Backlog Modul 6 diperbarui lengkap (SB-01 s/d SB-07 status DONE)", "TERPENUHI (100%)"],
        ["16", "Sprint Review tersedia dan terisi lengkap sesuai format Modul 6 Bab 21", "TERPENUHI (100%)"],
        ["17", "Sprint Retrospective tersedia dan terisi komprehensif sesuai format Modul 6 Bab 22", "TERPENUHI (100%)"]
    ]
    create_table_styled(doc, dod_headers, dod_data, [0.6, 5.0, 1.2], align_center=True)
    
    p = doc.add_paragraph()
    format_heading(p, "5.4 Target Sprint Berikutnya (P7 – Abstract Class dan Interface)", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Hasil implementasi Dynamic Polymorphism, Method Overriding, dan Method Overloading pada Modul 6 ini menjadi pijakan mutlak untuk melangkah ke Praktikum Pertemuan 7 (P7 – Abstraction). "
                   "Pada iterasi Sprint 7 mendatang, arsitektur AutoRent System akan ditingkatkan lebih lanjut dengan:\n"
                   "• Penerapan Abstract Class: Mengevaluasi Superclass Kendaraan dan Person agar dideklarasikan sebagai abstract class sehingga mencegah instansiasi objek dasar secara langsung.\n"
                   "• Penerapan Abstract Method: Mengubah method virtual hitungEstimasiBiaya() dan tampilkanSpesifikasi() menjadi abstract method murni pada Superclass untuk mewajibkan implementasi di tingkat Subclass.\n"
                   "• Penerapan Interface: Mengekstrak antarmuka kontrak perilaku umum seperti interface ServisAble, Printable, dan Notifiable guna mendukung Multiple Interface Realization dan loosely-coupled architecture.")
    
    p = doc.add_paragraph()
    format_heading(p, "5.5 Kesimpulan Akhir", 2)
    
    p = doc.add_paragraph()
    format_body(p, "Praktikum Pemrograman Berorientasi Obyek Modul 6 (Polymorphism, Method Overriding, Method Overloading, dan Dynamic Binding) pada proyek AutoRent System telah berhasil diselesaikan dengan hasil sempurna (100% Definition of Done tercapai). "
                   "Melalui pemanfaatan Dynamic Method Dispatch, sistem rental kini memiliki fleksibilitas tinggi dalam menangani berbagai kategori armada (Mobil, Motor, Minibus) dan berbagai tipe pengguna (Pelanggan, Pegawai) "
                   "melalui antarmuka pemanggilan seragam yang adaptif, modular, defensif, dan siap dikembangkan menuju tahapan Abstraction pada Modul 7.")
    
    # Save Document
    output_docx_path = os.path.join(os.getcwd(), 'Laporan_Praktikum_PBO_Modul_6_AutoRent.docx')
    doc.save(output_docx_path)
    print(f"Laporan Praktikum Modul 6 successfully saved to: {output_docx_path}")

if __name__ == '__main__':
    generate_laporan_modul_6()
