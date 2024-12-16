package models;

public class Rental extends Car {
    private String customerName; // Nama penyewa
    private String address; // Alamat penyewa
    private String phone; // Nomor telepon penyewa
    private int rentalDays; // Lama sewa dalam hari

    // Constructor untuk membuat objek penyewaan
    public Rental(String plateNumber, String type, String color, String customerName, int hargaPerHari,
                  String address, String phone, int rentalDays) {
        super(plateNumber, type, color, hargaPerHari); // Memanggil constructor superclass (Inheritance)
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.rentalDays = rentalDays;
        setRented(true); // Menandai mobil sebagai disewa
    }

    // Getter dan Setter (Enkapsulasi)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    // Override method untuk menghitung total harga sewa (Polimorfisme)
    @Override
    public int calculateRentalCost(int days) {
        return super.calculateRentalCost(days); // Menggunakan method dari superclass
    }

    // Polimorfisme: Override method getInfo untuk menampilkan informasi sewa
    @Override
    public String getInfo() {
        return super.getInfo() + " - Disewa oleh: " + customerName +
                ", Alamat: " + address +
                ", Nomor HP: " + phone +
                ", Lama: " + rentalDays + " hari" +
                ", Total Harga: " + calculateRentalCost(rentalDays);
    }
}