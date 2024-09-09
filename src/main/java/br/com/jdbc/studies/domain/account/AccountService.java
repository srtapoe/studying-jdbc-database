package br.com.jdbc.studies.domain.account;

import br.com.jdbc.studies.ConnectionFactory;
import br.com.jdbc.studies.domain.exception.BusinessRulesException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class AccountService {
    private final ConnectionFactory connection;

    public AccountService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Account> getAccounts() {
        Connection conn = connection.savedConnection();
        return new AccountDAO(conn).showList();
    }

    public BigDecimal getBalance(int accountNumber) {
        var account = searchAccountForNumber(accountNumber);
        return account.getBalance();
    }

    public void addAccount(DateAccount newAccount) {
       Connection conn = connection.savedConnection();
       new AccountDAO(conn).insert(newAccount);
    }

    public void withdraw(int accountNumber, BigDecimal amount) {
        var account = searchAccountForNumber(accountNumber);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRulesException("Valor do saque deve ser superior a zero!");
        }

        if (amount.compareTo(account.getBalance()) > 0) {
            throw new BusinessRulesException("Saldo insuficiente!");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        changeValue(account, newBalance);

    }

    public void deposit(int accountNumber, BigDecimal deposit) {
        var account = searchAccountForNumber(accountNumber);

        if(deposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRulesException("Deposited amount is less than zero!");
        }
        BigDecimal newBalance = account.getBalance().add(deposit);
        changeValue(account, newBalance);
    }

    public void transfer(int accountLeave, Integer accountReceipt, BigDecimal value) {
        this.withdraw(accountLeave, value);
        this.deposit(accountReceipt, value);
    }

    public void closeAccount(int accountNumber) {
        var account = searchAccountForNumber(accountNumber);

        if (account.isThereSameMoney()) {
            throw new BusinessRulesException("Account cannot be closed, because it's have money!");
        }

        Connection conn = connection.savedConnection();
        new AccountDAO(conn).changeStatusAccount(accountNumber);
    }

    private Account searchAccountForNumber(int number) {
        Connection conn = connection.savedConnection();
        Account account = new AccountDAO(conn).listForNumber(number);
        if(account != null) {
            return account;
        } else {
            throw new BusinessRulesException("There is not register account with this number!");
        }
    }

    private void changeValue(Account account, BigDecimal value) {
        Connection conn = connection.savedConnection();
        new AccountDAO(conn).alteration(account.getNumber(), value);
    }

}
