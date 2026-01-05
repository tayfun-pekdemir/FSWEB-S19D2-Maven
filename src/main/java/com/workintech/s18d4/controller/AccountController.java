package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll().stream()
                .map(account -> new AccountResponse(
                        account.getId(),
                        account.getAccountName(),
                        account.getMoneyAmount(),
                        new CustomerResponse(
                                account.getCustomer().getId(),
                                account.getCustomer().getEmail(),
                                account.getCustomer().getSalary()
                        )
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable long id) {
        Account account = accountService.find(id);
        if (account == null) return null;

        return new AccountResponse(
                account.getId(),
                account.getAccountName(),
                account.getMoneyAmount(),
                new CustomerResponse(
                        account.getCustomer().getId(),
                        account.getCustomer().getEmail(),
                        account.getCustomer().getSalary()
                )
        );
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        if (customer == null) throw new RuntimeException("Customer Not Found");

        account.setCustomer(customer);
        Account savedAccount = accountService.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getAccountName(),
                savedAccount.getMoneyAmount(),
                new CustomerResponse(
                        customer.getId(),
                        customer.getEmail(),
                        customer.getSalary()
                )
        );
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable long customerId, @RequestBody Account account) {

        Customer customer = customerService.find(customerId);
        if (customer == null) throw new RuntimeException("Customer Not Found");

        Account existingAccount = accountService.find(account.getId());
        if (existingAccount == null) throw new RuntimeException("Account Not Found or Update Failed");

        existingAccount.setAccountName(account.getAccountName());
        existingAccount.setMoneyAmount(account.getMoneyAmount());
        existingAccount.setCustomer(customer);

        Account updatedAccount = accountService.save(existingAccount);

        return new AccountResponse(
                updatedAccount.getId(),
                updatedAccount.getAccountName(),
                updatedAccount.getMoneyAmount(),
                new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary())
        );
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable long id) {
        Account account = accountService.find(id);
        if (account == null) return null;
        Account deletedAccount = accountService.delete(id);
        return new AccountResponse(
                deletedAccount.getId(),
                deletedAccount.getAccountName(),
                deletedAccount.getMoneyAmount(),
                new CustomerResponse(
                        deletedAccount.getCustomer().getId(),
                        deletedAccount.getCustomer().getEmail(),
                        deletedAccount.getCustomer().getSalary()
                )
        );
    }


}
