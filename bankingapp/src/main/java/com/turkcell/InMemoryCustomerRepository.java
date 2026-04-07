package com.turkcell;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<>();

    @Override
    public Customer add(Customer customer) {
        if (customers.stream().anyMatch(c -> c.getEmail().equals(customer.getEmail()))) {
            throw new RuntimeException("Bu email zaten kayıtlı");
        }
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer findById(String id) {
        return customers.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
    }

    @Override
    public Customer findByEmail(String email) {
        return customers.stream()
                .filter(c -> email.equals(c.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
    }

    @Override
    public Customer update(Customer customer) {
        Customer existing = findById(customer.getId());
        existing.setName(customer.getName());
        existing.setPhoneNumber(customer.getPhoneNumber());
        existing.setBalance(customer.getBalance());
        return existing;
    }

    @Override
    public void delete(String id) {
        Customer existing = findById(id);
        customers.remove(existing);
    }
}