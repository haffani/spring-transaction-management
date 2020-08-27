package com.affani.datatranx.service;

import java.util.List;
import java.util.Optional;

import com.affani.datatranx.model.Account;

public interface IAccountService {
    void addAccount(Account account);
    List<Account> getAllAccounts();
    void transferMoney(Account from, Account to, double amount, double fee);
	Optional<Account> getAccount(int accountId);
}
