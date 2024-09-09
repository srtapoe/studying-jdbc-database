package br.com.jdbc.studies.domain.account;

import br.com.jdbc.studies.domain.client.Client;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

public class Account {

    private int number;
    private BigDecimal balance;
    private Client client;
    private boolean isActive;

    public Account(int number, BigDecimal balance, Client client) {
        this.number = number;
        this.balance = balance;
        this.client = client;
        this.isActive = true;
    }

    public int getNumber() {
        return number;
    }

    public static int generateAccountNumber() {
        Random random = new Random();
        return  100000 + random.nextInt(900000);
    }

    public boolean isThereSameMoney(){
        return balance.compareTo(BigDecimal.ZERO) != 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number && Objects.equals(balance, account.balance) && Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, balance, client);
    }


    @Override
    public String toString() {
        return "Account : " +
                "NUMBER: " + number +
                ", BALANCE: " + balance +
                ", CLIENT: " + client +
                ", STATUS: " + isActive;
    }


    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
