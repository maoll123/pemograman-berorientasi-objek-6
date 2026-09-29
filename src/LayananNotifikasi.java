/**
 * Class LayananNotifikasi (Service Gateway Dependency)
 * Bertanggung jawab mengirimkan notifikasi status transaksi kepada pelanggan via SMS / WhatsApp gateway.
 * 
 * Menerapkan Prinsip OOP:
 * 1. Dependency (- - >): Dimanfaatkan oleh TransaksiSewa untuk mendistribusikan notifikasi event-driven.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class LayananNotifikasi {
    private String providerGateway;

    public LayananNotifikasi(String providerGateway) {
        this.providerGateway = (providerGateway != null) ? providerGateway : "AutoRent Cloud Notifier";
    }

    public void kirimNotifikasiBooking(Pelanggan pelanggan, String kodeTransaksi, String platNomor, String modelKendaraan, double totalBiaya) {
        System.out.println("  [GATEWAY NOTIFIKASI - " + providerGateway + "]");
        System.out.println("   └─ [SMS/WA Terkirim ke " + pelanggan.getNomorTelepon() + " (" + pelanggan.getNama() + ")]: "
                + "Booking " + kodeTransaksi + " untuk unit " + modelKendaraan + " (" + platNomor + ") sukses! Total: Rp "
                + String.format("%,.2f", totalBiaya));
    }

    public void kirimNotifikasiPengembalian(Pelanggan pelanggan, String kodeTransaksi, String statusUnit, double finalBiaya) {
        System.out.println("  [GATEWAY NOTIFIKASI - " + providerGateway + "]");
        System.out.println("   └─ [SMS/WA Terkirim ke " + pelanggan.getNomorTelepon() + " (" + pelanggan.getNama() + ")]: "
                + "Pengembalian transaksi " + kodeTransaksi + " telah selesai. Status Unit: " + statusUnit
                + ". Tagihan Final: Rp " + String.format("%,.2f", finalBiaya));
    }
}
