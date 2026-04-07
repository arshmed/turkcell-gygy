package com.turkcell;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        CustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);

        Customer c1 = new Customer("Muhammed", "muhammed@example.com", "123");
        Customer c2 = new Customer("Ahmet", "ahmet@example.com", "456");

        repository.add(c1);
        repository.add(c2);

        System.out.println("Initial balances:");
        System.out.println("Muhammed: " + c1.getBalance());
        System.out.println("Ahmet: " + c2.getBalance());

        // Para yatırma
        service.depositMoney(c1.getId(), new BigDecimal("1000"));

        // Para çekme (withdraw)
        service.withdrawMoney(c1.getId(), new BigDecimal("200"));

        // Transfer
        service.transferMoney(c1.getId(), c2.getId(), new BigDecimal("300"));
        System.out.println("300 transferred from Muhammed to Ahmet.");

        // Bakiye sorgulama
        System.out.println("Balance inquiry:");
        System.out.println("Muhammed: " + c1.getBalance());
        System.out.println("Ahmet: " + c2.getBalance());

        System.out.println("Final balances:");
        System.out.println("Muhammed: " + c1.getBalance());
        System.out.println("Ahmet: " + c2.getBalance());
    }
}

/*
ÖRNEK ÇIKTI:

Initial balances:
Muhammed: 0
Ahmet: 0

300 transferred from Muhammed to Ahmet.

Balance inquiry:
Muhammed: 500
Ahmet: 300

Final balances:
Muhammed: 500
Ahmet: 300
*/

/*
    VALIDATION TEST SENARYOLARI:

    // 1. Negatif / sıfır amount
    service.depositMoney(c1.getId(), new BigDecimal("-10")); // Exception
    service.withdrawMoney(c1.getId(), BigDecimal.ZERO);      // Exception

    // 2. Yetersiz bakiye
    service.withdrawMoney(c1.getId(), new BigDecimal("9999")); // Exception

    // 3. Null amount
    service.depositMoney(c1.getId(), null); // Exception

    // 4. Var olmayan customer
    service.depositMoney("invalid-id", new BigDecimal("100")); // Exception

    // 5. Duplicate email (repository level)
    Customer c3 = new Customer("Ali", "muhammed@example.com", "999");
    repository.add(c3); // Exception
*/