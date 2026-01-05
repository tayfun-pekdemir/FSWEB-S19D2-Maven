package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer find(long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return  customerRepository.save(customer);
    }

    @Override
    public Customer update(long id, Customer customer) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setSalary(customer.getSalary());
        existingCustomer.setAddress(customer.getAddress());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer delete(long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            customerRepository.delete(customerOptional.get());
            return customerOptional.get();
        }
        return null;
    }
}
