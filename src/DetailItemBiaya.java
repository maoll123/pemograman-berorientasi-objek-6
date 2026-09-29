/**
 * Class DetailItemBiaya (Komponen Komposisi Transaksi)
 * Merepresentasikan baris rincian komponen biaya (sewa pokok, add-on, asuransi, denda) dalam TransaksiSewa.
 * 
 * Menerapkan Prinsip OOP:
 * 1. Composition (◆): Bagian internal yang dikelola seutuhnya di dalam TransaksiSewa.
 * 2. Encapsulation: Menjaga konsistensi nilai nominal biaya dan kalkulasi subtotal.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class DetailItemBiaya {
    private String deskripsiItem;
    private double tarifSatuan;
    private int kuantitas;

    public DetailItemBiaya(String deskripsiItem, double tarifSatuan, int kuantitas) {
        if (deskripsiItem == null || deskripsiItem.trim().isEmpty()) {
            throw new IllegalArgumentException("[Defensive Error] Deskripsi item biaya tidak boleh kosong!");
        }
        if (tarifSatuan < 0) {
            throw new IllegalArgumentException("[Defensive Error] Tarif satuan tidak boleh negatif!");
        }
        if (kuantitas <= 0) {
            throw new IllegalArgumentException("[Defensive Error] Kuantitas item harus > 0!");
        }
        this.deskripsiItem = deskripsiItem.trim();
        this.tarifSatuan = tarifSatuan;
        this.kuantitas = kuantitas;
    }

    public String getDeskripsiItem() {
        return deskripsiItem;
    }

    public double getTarifSatuan() {
        return tarifSatuan;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public double getSubtotal() {
        return this.tarifSatuan * this.kuantitas;
    }

    @Override
    public String toString() {
        return String.format("- %-35s (Rp %,.2f x %d) = Rp %,.2f",
                deskripsiItem, tarifSatuan, kuantitas, getSubtotal());
    }
}
