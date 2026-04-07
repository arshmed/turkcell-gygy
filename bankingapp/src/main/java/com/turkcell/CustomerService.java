package com.turkcell;

import java.math.BigDecimal;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void depositMoney(String customerId, BigDecimal amount) {
        validateAmount(amount);

        Customer customer = customerRepository.findById(customerId);
        customer.setBalance(customer.getBalance().add(amount));

        customerRepository.update(customer);
    }

    public void withdrawMoney(String customerId, BigDecimal amount) {
        validateAmount(amount);

        Customer customer = customerRepository.findById(customerId);

        if (customer.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Yetersiz bakiye");
        }

        customer.setBalance(customer.getBalance().subtract(amount));
        customerRepository.update(customer);
    }

    public void transferMoney(String fromId, String toId, BigDecimal amount) {
        validateAmount(amount);

        Customer from = customerRepository.findById(fromId);
        Customer to = customerRepository.findById(toId);

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Transfer için yetersiz bakiye");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        customerRepository.update(from);
        customerRepository.update(to);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Geçersiz tutar");
        }
    }
}