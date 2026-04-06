package com.turkcell;

public class Vehicle {

    public String brand;
    public String model;
    public int year;
    private Double pricePerDay;

        public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        if (pricePerDay < 0) {
            this.pricePerDay = 0.0;
        } else {
            this.pricePerDay = pricePerDay;
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
