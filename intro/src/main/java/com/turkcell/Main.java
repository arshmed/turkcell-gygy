package com.turkcell;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        
    }

    public static void grade() {
            int grade = 85;
    
            if (grade >= 90) {
                System.out.println("A");
            } else if (grade >= 80) {
                System.out.println("B");
            } else if (grade >= 70) {
                System.out.println("C");
            } else if (grade >= 60) {
                System.out.println("D");
            } else {
                System.out.println("F");
            }
    }

    public static void printNumbers(int... numbers) {
        for (int number : numbers) {
            System.out.println(number);
        }
    }    

}