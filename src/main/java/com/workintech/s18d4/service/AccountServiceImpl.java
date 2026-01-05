package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account find(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account delete(long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            accountRepository.delete(accountOptional.get());
            return accountOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public Account update(Account account) {
        Account existingAccount = accountRepository.findById(account.getId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        existingAccount.setAccountName(account.getAccountName());
        existingAccount.setMoneyAmount(account.getMoneyAmount());

        existingAccount.setCustomer(account.getCustomer());

        return accountRepository.save(existingAccount);
    }
}
