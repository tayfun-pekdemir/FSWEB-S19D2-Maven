package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Account;

import java.util.List;

public interface AccountService {
    Account find(long id);
    List<Account> findAll();
    Account save(Account account);
    Account delete(long id);
    Account update(Account account);
}
