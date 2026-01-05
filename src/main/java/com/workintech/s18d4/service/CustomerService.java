package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer find(long id);
    List<Customer> findAll();
    Customer save(Customer customer);
    Customer delete(long id);
    Customer update(long id,Customer customer);
}
