/**
 * Class Mesin (Komponen Komposisi Kendaraan)
 * Merepresentasikan spesifikasi mekanikal dan status performa internal mesin kendaraan.
 * 
 * Menerapkan Prinsip OOP:
 * 1. Composition (◆): Bagian integral tak terpisahkan dari Superclass Kendaraan.
 * 2. Encapsulation: Menjaga integritas data teknis mesin dengan validasi defensif.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class Mesin {
    private String nomorRangka;
    private int kapasitasCC;
    private String tipeBahanBakar;
    private String transmisi;
    private int kilometerTempuh;
    private String statusPerforma; // "Sangat Baik / Optimal", "Perlu Servis", "Kritis"

    public Mesin(String nomorRangka, int kapasitasCC, String tipeBahanBakar, String transmisi) {
        if (nomorRangka == null || nomorRangka.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Nomor Rangka Mesin tidak boleh kosong!");
        }
        if (kapasitasCC <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kapasitas CC Mesin harus > 0!");
        }
        this.nomorRangka = nomorRangka.trim().toUpperCase();
        this.kapasitasCC = kapasitasCC;
        this.tipeBahanBakar = (tipeBahanBakar != null && !tipeBahanBakar.trim().isEmpty()) ? tipeBahanBakar.trim() : "Bensin";
        this.transmisi = (transmisi != null && !transmisi.trim().isEmpty()) ? transmisi.trim() : "Automatic";
        this.kilometerTempuh = 0;
        this.statusPerforma = "Sangat Baik / Optimal";
    }

    public String getNomorRangka() {
        return nomorRangka;
    }

    public int getKapasitasCC() {
        return kapasitasCC;
    }

    public String getTipeBahanBakar() {
        return tipeBahanBakar;
    }

    public String getTransmisi() {
        return transmisi;
    }

    public int getKilometerTempuh() {
        return kilometerTempuh;
    }

    public String getStatusPerforma() {
        return statusPerforma;
    }

    public void tambahKilometer(int km) {
        if (km < 0) {
            throw new IllegalArgumentException("[Defensive Error] Penambahan kilometer tidak boleh negatif!");
        }
        this.kilometerTempuh += km;
        if (this.kilometerTempuh > 10000) {
            this.statusPerforma = "Perlu Servis";
        }
        if (this.kilometerTempuh > 25000) {
            this.statusPerforma = "Kritis - Segera Tune-Up";
        }
    }

    public void servisTuneUp() {
        this.kilometerTempuh = 0;
        this.statusPerforma = "Sangat Baik / Optimal (Freshly Tuned)";
        System.out.println("  [MAINTENANCE] Tune-up mesin " + nomorRangka + " selesai. Performa kembali optimal!");
    }

    public void tampilkanStatusMesin() {
        System.out.println("   └─ [DATA MESIN] Rangka: " + nomorRangka + " | " + kapasitasCC + " CC ("
                + tipeBahanBakar + " / " + transmisi + ") | Odo: " + kilometerTempuh + " km | Status: " + statusPerforma);
    }
}
