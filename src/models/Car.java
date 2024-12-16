package models;

public class Car {
    private String plateNumber;
    private String type;
    private String color;
    private int hargaPerHari;
    private boolean isRented;

    // Constructor untuk membuat objek mobil
    public Car(String plateNumber, String type, String color, int hargaPerHari) {
        this.plateNumber = plateNumber;
        this.type = type;
        this.color = color;
        this.hargaPerHari = hargaPerHari;
        this.isRented = false;
    }

    // Getter dan Setter (Enkapsulasi)
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHargaPerHari() {
        return hargaPerHari;
    }

    public void setHargaPerHari(int hargaPerHari) {
        this.hargaPerHari = hargaPerHari;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    // Method untuk menghitung biaya sewa (Method berparameter)
    public int calculateRentalCost(int days) {
        return days * hargaPerHari;
    }

    // Polimorfisme: Method untuk menampilkan informasi mobil
    public String getInfo() {
        return plateNumber + " - " + type + " (" + color + ")";
    }
}