package com.turkcell;

public interface CustomerRepository {
    Customer findById(String id);
    Customer add(Customer customer);
    Customer findByEmail(String email);
    Customer update(Customer customer);
    void delete(String id);
}
