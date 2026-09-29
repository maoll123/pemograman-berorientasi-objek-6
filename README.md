# AutoRent System — Praktikum PBO Modul 6

> **Polymorphism, Method Overriding, Method Overloading, dan Dynamic Binding**  
> Repository Praktikum Pemrograman Berorientasi Obyek (PBO) — Modul 6 (Sprint 6)

---

## 👨‍🎓 Informasi Akademik
- **Nama Mahasiswa** : Ahmad Yasar Maulana Dais
- **NRP**            : 3125522018
- **Program Studi**  : D3 Teknik Informatika
- **Institusi**      : Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep (2026)
- **Dosen Pengampu** : Nirwana Haidar Hari, S.Pd., M.Kom.
- **Nama Proyek**    : **AutoRent System (Integrated Vehicle Rental Platform)**

---

## 📌 Ringkasan Modul 6
Modul 6 berfokus pada pengembangan perilaku dinamis objek (*dynamic object behavior*) sebagai kelanjutan dari arsitektur *Inheritance* Modul 5:
1. **Method Overriding (`@Override`)**: Mengkustomisasi implementasi method superclass pada subclass untuk mengeksekusi logika bisnis yang terspesialisasi.
2. **Method Overloading (Compile-Time Polymorphism)**: Menyediakan variasi signature parameter method untuk meningkatkan fleksibilitas pemanggilan (*API usability*).
3. **Upcasting & Polymorphic Reference**: Menggunakan reference bertipe *Superclass* untuk menunjuk instance konkrit *Subclass*.
4. **Dynamic Binding (Runtime Method Dispatch)**: Resolusi pemanggilan method virtual pada *Heap Memory* saat waktu eksekusi program (*runtime*).
5. **Heterogeneous Polymorphic Collection**: Mengelola berbagai tipe subclass (`Mobil`, `Motor`, `Minibus`) dalam satu wadah seragam (`Kendaraan[]` / `ArrayList<Kendaraan>`).

---

## 🏛️ Struktur Arsitektur & Daftar File Sumber (`src/`)

| No | File | Peran & Deskripsi OOP |
|:---|:---|:---|
| 1 | [`src/Person.java`](src/Person.java) | **Superclass Person**: Base reference polimorfis aktor sistem & method virtual profil. |
| 2 | [`src/Pelanggan.java`](src/Pelanggan.java) | **Subclass Pelanggan**: Overriding profil, reward loyalty & overloading deposit saldo. |
| 3 | [`src/Pegawai.java`](src/Pegawai.java) | **Subclass Pegawai**: Overriding profil, kompensasi take-home pay & overloading bonus kasir. |
| 4 | [`src/Kendaraan.java`](src/Kendaraan.java) | **Superclass Kendaraan**: Base reference polimorfis armada, relasi komposisi Mesin & virtual methods. |
| 5 | [`src/Mobil.java`](src/Mobil.java) | **Subclass Mobil**: Overriding spesifikasi kabin, surcharge AC kabin, diskon mingguan & overload aksesoris. |
| 6 | [`src/Motor.java`](src/Motor.java) | **Subclass Motor**: Overriding spesifikasi bagasi helm, promo city tour & overload fasilitas riding. |
| 7 | [`src/Minibus.java`](src/Minibus.java) | **Subclass Minibus**: Overriding spesifikasi travel rombongan, asuransi kursi & overload konfigurasi kursi. |
| 8 | [`src/Mesin.java`](src/Mesin.java) | **Komponen Komposisi (◆)**: Spesifikasi mekanikal, odometer, dan servis tune-up mesin. |
| 9 | [`src/GarasiArmada.java`](src/GarasiArmada.java) | **Agregasi Polimorfis (◇)**: Pool koleksi heterogen armada & 4 variasi overload pencarian. |
| 10 | [`src/DetailItemBiaya.java`](src/DetailItemBiaya.java) | **Komposisi Transaksi (◆)**: Rincian item baris biaya sewa dan add-on. |
| 11 | [`src/TransaksiSewa.java`](src/TransaksiSewa.java) | **Orkestrator Asosiasi**: Dynamic dispatch perhitungan tarif & overloaded payment processing. |
| 12 | [`src/KuitansiPrinter.java`](src/KuitansiPrinter.java) | **Utility Dependency (- - >)**: Renderer struk thermal resmi kasir AutoRent. |
| 13 | [`src/LayananNotifikasi.java`](src/LayananNotifikasi.java) | **Utility Dependency (- - >)**: Gateway pengiriman notifikasi SMS/WA booking. |
| 14 | [`src/Main.java`](src/Main.java) | **Driver & Test Suite**: Eksekusi komprehensif 8 Fase Pengujian Modul 6. |

---

## 📊 Matriks Dynamic Binding (Tabel Pengujian Bab 18)

| Tipe Reference | Objek Aktual (Heap) | Method Dipanggil | Output yang Dihasilkan | Resolusi Binding |
| :--- | :--- | :--- | :--- | :--- |
| `Person` | `Pelanggan` | `tampilkanProfil()` | NIK, Tier Member VIP, Kuota Sewa, Saldo Deposit | **Dynamic Binding (Pelanggan)** |
| `Person` | `Pegawai` | `tampilkanProfil()` | NIP, Jabatan Kasir, Shift, Take-Home Pay | **Dynamic Binding (Pegawai)** |
| `Kendaraan` | `Mobil` | `tampilkanSpesifikasi()` | MPV Kabin, AC Double Blower, 7 Penumpang | **Dynamic Binding (Mobil)** |
| `Kendaraan` | `Motor` | `tampilkanSpesifikasi()` | Bagasi 30L, 2 Helm SNI, Jas Hujan, Phone Holder | **Dynamic Binding (Motor)** |
| `Kendaraan` | `Minibus` | `tampilkanSpesifikasi()` | 16 Seat Rombongan, Bagasi 12 Koper, Karaoke TV | **Dynamic Binding (Minibus)** |
| `Kendaraan` | `Mobil` | `hitungEstimasiBiaya(5)` | Rp 3.125.000,00 (Tarif + Surcharge AC Kabin) | **Dynamic Binding (Mobil)** |
| `Kendaraan` | `Motor` | `hitungEstimasiBiaya(5)` | Rp 735.000,00 (Tarif - Promo City Tour Rp 15rb) | **Dynamic Binding (Motor)** |
| `Kendaraan` | `Minibus` | `hitungEstimasiBiaya(5)` | Rp 5.192.000,00 (Tarif + Asuransi Kursi - Diskon 12%) | **Dynamic Binding (Minibus)** |
| `Kendaraan` | `Mobil` | `lakukanServis()` | Servis Mesin + Filter AC Freon + Rem ABS 4 Roda | **Dynamic Binding (Mobil)** |
| `Kendaraan` | `Motor` | `lakukanServis()` | Servis Mesin + Kuras Oli Gardan CVT + Rem Cakram | **Dynamic Binding (Motor)** |
| `Kendaraan` | `Minibus` | `lakukanServis()` | Servis Mesin Diesel + Kompresor Rem Angin + Suspensi | **Dynamic Binding (Minibus)** |

---

## 🛠️ Panduan Kompilasi & Eksekusi

Jalankan perintah berikut pada terminal PowerShell / Command Prompt:

```powershell
# 1. Kompilasi seluruh kode sumber Java ke direktori bin/
javac -encoding UTF-8 -d bin src/*.java

# 2. Jalankan Main Driver Test Suite (8 Fase Pengujian)
java -cp bin Main
```

---

## 📑 Laporan Praktikum Resmi
Dokumen laporan praktikum akademik lengkap dalam format Microsoft Word tersedia pada root direktori:
- 📄 **`Laporan_Praktikum_PBO_Modul_6_AutoRent.docx`**
