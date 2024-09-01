package br.com.jdbc.studies.domain.account;

import br.com.jdbc.studies.domain.client.Client;
import br.com.jdbc.studies.domain.exception.BusinessRulesException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AccountService {

    private final ArrayList<Account> accounts = new ArrayList<>();

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public BigDecimal getBalance(int accountNumber) {
        var account = accounts.get(accountNumber);
        return account.getBalance();
    }

    public void addAccount(DateAccount newAccount) {
        var client = new Client(newAccount.dateClient());
        var account = new Account(newAccount.number(), client);

        if (accounts.contains(account)) {
            throw new BusinessRulesException("There is another account with same number!");
        }

        accounts.add(account);
    }

    public void withdraw(int accountNumber, BigDecimal amount) {
        var account = accounts.get(accountNumber);

        if (account.getBalance().compareTo(amount) <= 0) {
            throw new BusinessRulesException("You do not have enough money!");
        }
        account.withdraw(amount);
    }

    public void deposit(int accountNumber, BigDecimal amount) {
        var account = accounts.get(accountNumber);

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRulesException("Deposited amount is less than zero!");
        }
        account.deposit(amount);
    }

    public void closeAccount(int accountNumber) {
        var account = accounts.get(accountNumber);

        if(account.isThereSameMoney()){
            throw new BusinessRulesException("Account cannot be closed, because it's have money!");
        }

        accounts.remove(accountNumber);
    }
}
