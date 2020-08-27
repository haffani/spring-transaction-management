package com.affani.datatranx.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.affani.datatranx.dao.IAccountDAO;
import com.affani.datatranx.model.Account;

/**
 * @author Hamza AFFANI
 * @version 1.0
 * 
 *          This class represents the second implementation of the programmatic
 *          transaction management using spring transaction template
 * 
 */

@Service
@Qualifier("progTrxTemplateBean")
public class ProgTrxTemplateAccountService implements IAccountService {

	private final NumberFormat fmt = NumberFormat.getCurrencyInstance();

	@Autowired
	private IAccountDAO accountDAO;

	private final TransactionTemplate transactionTemplate;

	public ProgTrxTemplateAccountService(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
		this.transactionTemplate.setReadOnly(true);

	}

	@Override
	public List<Account> getAllAccounts() {
		return accountDAO.findAll();
	}

	@Override
	public void addAccount(Account account) {
		accountDAO.save(account);
	}

	@Override
	public Optional<Account> getAccount(int accountId) {
		return accountDAO.findById((long) accountId);
	}

	@Override
	public void transferMoney(Account from, Account to, double amount, double fee) {
		/**
		 * Transaction consists of two steps: 1.) withdraw from the sender account an
		 * amount 2.) deposit the same amount to the beneficiary's account
		 */
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			public void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					withdraw(from, amount, fee);
					deposit(to, amount);
				} catch (NoSuchElementException exception) { // in case of no such account found
					exception.printStackTrace();
					status.setRollbackOnly();
				}

			}
		});
	}

	/**
	 * Deposits the specified amount into the account.
	 */
	private void deposit(Account to, double amount) {
		Account accountToCredit = getAccount(to.getId().intValue()).get();

		if (amount < 0) // deposit value is negative
		{
			throw new RuntimeException(
					"Error: Deposit amount is invalid." + accountToCredit.getAcctNumber() + " " + fmt.format(amount));

		} else {
			accountToCredit.setBalance(accountToCredit.getBalance() + amount);
			accountToCredit.setLast_operation("Credited");
		}
	}

	/**
	 * Withdraws the specified amount from the account and returns the new balance.
	 */

	private void withdraw(Account from, double amount, double fee) {
		Account accountToDebit = getAccount(from.getId().intValue()).get();

		amount += fee;
		if (amount < 0) // withdraw value is negative
		{
			throw new RuntimeException("Error: Withdraw amount is invalid. for the Account: "
					+ accountToDebit.getAcctNumber() + " Name: " + accountToDebit.getName());
		} else if (amount > accountToDebit.getBalance()) // withdraw value exceeds balance
		{
			throw new RuntimeException(
					"Error: Insufficient funds.\n Account: " + accountToDebit.getAcctNumber() + "\n" + "Requested:"
							+ fmt.format(amount) + "Available: " + "\n" + fmt.format(accountToDebit.getBalance()));
		} else {
			accountToDebit.setBalance(accountToDebit.getBalance() - amount);
			accountToDebit.setLast_operation("Debited");
		}
	}

}
