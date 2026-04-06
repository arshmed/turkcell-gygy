package com.turkcell;

public class Car extends Vehicle {

    private boolean hasSunroof;
    private String[] specs;

    public Car(boolean hasSunroof, String brand) {
        this.hasSunroof = hasSunroof;
        super.setBrand(brand);
    }

    public Car() {

    }

    public boolean isHasSunroof() {
        return hasSunroof;
    }
    public void setHasSunroof(boolean hasSunroof) {
        this.hasSunroof = hasSunroof;
    }
    public String[] getSpecs() {
        return specs.clone();
    }
    public void setSpecs(String[] specs) {
        this.specs = specs.clone();
    }

    
    
}
