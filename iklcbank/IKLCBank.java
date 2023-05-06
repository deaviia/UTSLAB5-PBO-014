/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iklcbank;

import java.util.Date; //untuk mempresentasikan tanggal dan waktu dalam program
import java.util.Scanner; //untuk membaca masukkan input dri pengguna

/**
 *
 * @author Asus
 */
public class IKLCBank {
    static final int MAX_AKUN = 200;
    static Akun[] akunS = new Akun[MAX_AKUN];
    static int numAkun = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int menu;

        boolean quit = false;
        while (!quit) {
            System.out.println();
            System.out.println("+----------------- Bank IKLC ------------------+");
            System.out.println("        Mengutamakan Kepuasan Nasabah ");
            System.out.println("+----------------------------------------------+");
            System.out.println("|1. Registrasi Akun                            |");
            System.out.println("|2. Transfer                                   |");
            System.out.println("|3. Rekening Deposito/Setor Tunai              |");
            System.out.println("|4. Cek Saldo                                  |");
            System.out.println("|5. Keluar                                     |");
            System.out.println("+----------------------------------------------+");
            System.out.print("Pilih Jenis Transaksi (1-5): ");
            menu = input.nextInt();
            
            switch (menu) {
                case 1:
                    daftarAkun();
                    break;
                case 2:
                    kirimAkun();
                    break;
                case 3:
                    rekeningDeposito();
                    break;
                case 4:
                    cekSaldo();
                    break;
                case 5:
                    System.out.println("Transaksi anda telah selesai. Terimakasih atas kepercayaan anda..");
                    quit = true;
                    break;
                default:
                    System.out.println("Pilihan Anda tidak tersedia!");
                    break;
            }
        }
    }

    private static void daftarAkun() {
        System.out.print("Masukkan Nama: ");
        String nama = input.next();
        Date tanggalRegistrasi = new Date();
        String nomorAkun;//digunakan untuk menyimpan nomor akun yang digenerate.
        do {
            nomorAkun = generateNomorAkun();
        } while (cariAkun(nomorAkun) != null); //untuk memeriksa apakah nomor akun tersebut sudah pernah digunakan sebelumnya
        
        System.out.print("Masukkan Balance: ");
        double balance = input.nextDouble();
        Akun akun = new Akun(nama, nomorAkun, balance, tanggalRegistrasi);
        akunS[numAkun++] = akun;

        System.out.println("Nomor Akun Anda : " + nomorAkun);
        System.out.println("Akun " + nomorAkun + " berhasil didaftarkan pada : " + tanggalRegistrasi); //the format of registration date 
    }

    private static void kirimAkun() {
        System.out.print("Masukkan Nomor Akun Pengirim: ");
        String nomorAkunPengirim = input.next();
        
        System.out.print("Masukkan Nomor Akun Tujuan: ");
        String nomorAkunPenerima = input.next();
        
        System.out.print("Masukkan jumlah transfer: ");
        double jumlah = input.nextDouble();

        Akun akunPengirim = cariAkun(nomorAkunPengirim);
        Akun akunPenerima = cariAkun(nomorAkunPenerima);
        
        //memeriksa apakah nomor akun tersebut sudah pernah digunakan sebelumnya
        if (akunPengirim == null) {
            System.out.println("Nomor Akun Pengirim tidak ditemukan!");
        } else if (akunPenerima == null) {
            System.out.println("Nomor Akun Penerima tidak ditemukan!");
        } else if (akunPengirim.getBalance() < jumlah) {
            System.out.println("Transaksi gagal, Saldo tidak mencukupi");
        } else {
            akunPengirim.setBalance( akunPengirim.getBalance() - jumlah);
            akunPenerima.setBalance(akunPenerima.getBalance() + jumlah);
            System.out.println("Transaksi berhasil dilakukan!");
        }
    }

    private static void rekeningDeposito() {
        System.out.println("SETORAN TUNAI");
        System.out.print("Masukkan Nomor Akun: ");
        String nomorAkun = input.next();
        
        Akun akunDeposit = cariAkun(nomorAkun);
        if (akunDeposit == null) {
            System.out.println("Nomor akun tidak ditemukan!");
                       
                    }
                    System.out.print("Masukkan jumlah uang yang ingin disetor: ");
                    double jumlahSetoran;
                    while (!input.hasNextDouble()) {
                        System.out.println("Jumlah uang harus angka!");
                        System.out.print("Masukkan jumlah uang yang ingin disetor: ");
                        input.next();
                    }
                    jumlahSetoran = input.nextDouble();
                    
                    // Cek apakah jumlah uang yang dimasukkan adalah angka lebih besar dari 0
                    if (jumlahSetoran <= 0) {
                        System.out.println("Jumlah uang harus lebih besar dari 0!");
                       
                    }
                    akunDeposit.setBalance(akunDeposit.getBalance() + jumlahSetoran); //setbalace yaitu jumlah uang yang dimasukkan oleh user akan ditambahkan pada saldo akun yang bersangkutan 
                    System.out.println("Setoran anda telah diterima!");
    }
    
    //digunakan untuk menampilkan saldo dari sebuah akun berdasarkan nomor akun yang dimasukkan oleh pengguna
    private static void cekSaldo() {
        System.out.println("INFORMASI SALDO");
        System.out.print("Masukkan Nomor Akun: ");
                    String cekNomorAkun = input.next();
                    Akun cekAkun = cariAkun(cekNomorAkun);
                    if (cekAkun == null) {
                        System.out.println("Nomor akun tidak ditemukan!");
                    }
                    System.out.println("Saldo akun anda " + ": " + cekAkun.getBalance());
    }
    
    static String generateNomorAkun() {
        //method untuk menghasilkan nomor acak dengan 6 digit
        int number = (int) (Math.random() * 900000) + 100000; 
        return String.valueOf(number);
    }


    static Akun cariAkun(String nomorAkun) {
        for (int i = 0; i < numAkun; i++) {
            if (akunS[i].getNomorAkun().equals(nomorAkun)) {
                return akunS[i];
            }
        }
        return null;
    }
}

class Akun {
    private String nama;
    private String nomorAkun;
    private double balance;

    public Akun(String nama, String nomorAkun, double balance, Date tanggalRegistrasi) {
        this.nama = nama;
        this.nomorAkun = nomorAkun;
        this.balance = balance;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorAkun() {
        return nomorAkun;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

