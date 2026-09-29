import java.util.ArrayList;

/**
 * Class KuitansiPrinter (Service Utility Dependency)
 * Bertanggung jawab mencetak struk bukti pembayaran dan kuitansi resmi sistem rental.
 * 
 * Menerapkan Prinsip OOP:
 * 1. Dependency (- - >): Dimanfaatkan oleh TransaksiSewa untuk mencetak rincian tagihan.
 * 2. Polymorphic Rendering: Menampilkan rincian kendaraan dan identitas pelanggan secara dinamis.
 * 
 * @author Ahmad Yasar Maulana Dais (3125522018)
 * Dosen Pengampu: Nirwana Haidar Hari, S.Pd., M.Kom.
 * Politeknik Elektronika Negeri Surabaya (PENS) - PSDKU Sumenep
 */
public class KuitansiPrinter {
    private String namaRental;
    private String alamatCabang;
    private String nomorTelepon;

    public KuitansiPrinter(String namaRental, String alamatCabang, String nomorTelepon) {
        this.namaRental = (namaRental != null) ? namaRental : "AUTORENT INDONESIA";
        this.alamatCabang = (alamatCabang != null) ? alamatCabang : "Jl. Trunojoyo No. 45, Sumenep";
        this.nomorTelepon = (nomorTelepon != null) ? nomorTelepon : "0812-9988-7766";
    }

    public void cetakKuitansiResmi(String kodeTransaksi, String tanggalSewa, String statusTransaksi,
                                  Pelanggan pelanggan, Pegawai petugas, Kendaraan kendaraan,
                                  int durasiHari, ArrayList<DetailItemBiaya> rincianBiaya,
                                  double subtotal, double diskon, double totalAkhir,
                                  double uangDibayar, double uangKembalian) {
        System.out.println("\n+--------------------------------------------------------------------------------+");
        System.out.println("|                     *** KUITANSI RESMI PEMBAYARAN AUTORENT ***                 |");
        System.out.println("| " + String.format("%-78s", namaRental.toUpperCase()) + " |");
        System.out.println("| " + String.format("%-78s", alamatCabang + " | Telp: " + nomorTelepon) + " |");
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.printf("| No. Transaksi : %-25s Tanggal   : %-26s |\n", kodeTransaksi, tanggalSewa);
        System.out.printf("| Kasir Petugas : %-25s Shift     : %-26s |\n", petugas.getNama() + " (" + petugas.getNip() + ")", petugas.getShiftKerja());
        System.out.printf("| Pelanggan     : %-25s Tier/NIK  : %-26s |\n", pelanggan.getNama(), pelanggan.getLevelMember() + " / " + pelanggan.getNomorKtp());
        System.out.printf("| Unit Disewa   : %-25s Kategori  : %-26s |\n", kendaraan.getMerk() + " " + kendaraan.getModel() + " [" + kendaraan.getNomorPlat() + "]", kendaraan.getTipeKendaraan());
        System.out.printf("| Durasi Sewa   : %-25s Status    : %-26s |\n", durasiHari + " Hari", statusTransaksi);
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("| RINCIAN KOMPONEN BIAYA:                                                        |");
        for (DetailItemBiaya item : rincianBiaya) {
            System.out.printf("| %-78s |\n", item.toString());
        }
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.printf("| Subtotal Estimasi Biaya                                  : Rp %,16.2f |\n", subtotal);
        System.out.printf("| Diskon Loyalitas Pelanggan (%2d%%)                         : -Rp %,15.2f |\n", (int)(pelanggan.getDiskonMember() * 100), diskon);
        System.out.printf("| TOTAL AKHIR TAGIHAN                                      : Rp %,16.2f |\n", totalAkhir);
        System.out.printf("| Jumlah Dibayarkan                                        : Rp %,16.2f |\n", uangDibayar);
        System.out.printf("| Sisa / Uang Kembalian                                    : Rp %,16.2f |\n", uangKembalian);
        System.out.println("+--------------------------------------------------------------------------------+");
        System.out.println("|        Terima kasih atas kepercayaan Anda menyewa armada di AutoRent!          |");
        System.out.println("+--------------------------------------------------------------------------------+\n");
    }
}
