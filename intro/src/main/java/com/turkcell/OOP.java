package com.turkcell;

public class OOP {
    public static void main(String[] args) {
        Car car1 = new Car();
        car1.setBrand("Toyota");;
        car1.setModel("Corolla");
        car1.setYear(2020);
        car1.setPricePerDay(-500.0);

        System.out.println("Car Brand: " + car1.brand);
        System.out.println("Price per day: " + car1.getPricePerDay());

        Car car2 = new Car(true, "Honda");
        System.out.println("Car 2 Brand: " + car2.getBrand());
        System.out.println("Car 2 Price per day: " + car2.getPricePerDay());
    }

// Yeni bir proje oluşturmak (aynı klasör içinde olabilir)
// isim: BankingApplication 

// Bir Bankacılık uygulaması => İçerik tamamen size ait.
// Min. 3+ özellik.

// In-Memory Repository => Veriler RAM'de değişkende tutulabilir.

// Tek bir main classda simülasyon yeterli.

}