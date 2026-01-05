package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Address;

import java.util.List;

public interface AddressService {
    Address find(long id);
    List<Address> findAll();
    Address save(Address address);
    Address delete(long id);
    Address update(long id, Address address);
}
