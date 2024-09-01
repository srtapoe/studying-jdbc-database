package br.com.jdbc.studies;

import br.com.jdbc.studies.domain.account.Account;
import br.com.jdbc.studies.domain.account.AccountService;
import br.com.jdbc.studies.domain.account.DateAccount;
import br.com.jdbc.studies.domain.client.DateClient;
import br.com.jdbc.studies.domain.exception.BusinessRulesException;

import java.util.Scanner;

public class CashBank {
    private static final AccountService accountService = new AccountService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        var option = viewMenu();
        while (option != 7) {
            try {
                switch (option) {
                    case 1 -> getAllAccounts();
                    case 2 -> openAccount();
                    case 3 -> closeAccount();
                    case 4 -> viewBalance();
                    case 5 -> withdraw();
                    case 6 -> deposition();
                    default -> throw new IllegalArgumentException("Invalid option: " + option);
                }
            } catch (BusinessRulesException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press any key and ENTER to return to the menu");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            option = viewMenu();
        }

        System.out.println("Finishing the application.");
    }

    private static int viewMenu(){
        System.out.println("""
                CASHBANK - CHOOSE AN OPTION:
                1 - List open accounts
                2 - Open an account
                3 - Close an account
                4 - Check account balance
                5 - Withdraw from an account
                6 - Deposit into an account
                7 - Exit
                """);
        return scanner.nextInt();
    }

    private static void getAllAccounts(){
        System.out.println("Available accounts");
        var accounts = accountService.getAccounts();
        accounts.forEach(System.out::println);

        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }

    private static void openAccount(){
        System.out.println("Enter the client's name:");
        var name = scanner.next();

        System.out.println("Enter the client's CPF:");
        var cpf = scanner.next();

        System.out.println("Enter the client's email:");
        var email = scanner.next();

        var accountNumber = Account.generateAccountNumber();

        accountService.addAccount(new DateAccount(accountNumber, new DateClient(name, cpf, email)));

        System.out.println("Account opened successfully.");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }

    private static void closeAccount(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        accountService.closeAccount(accountNumber);

        System.out.println("Account closed successfully.");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }

    private static void viewBalance(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();
        var balance = accountService.getBalance(accountNumber);
        System.out.println("Balance: " + balance);

        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }

    private static void withdraw(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        System.out.println("Enter the value: ");
        var value = scanner.nextBigDecimal();

        accountService.withdraw(accountNumber, value);
        System.out.println("Withdrawal successful!");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }

    private static void deposition(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        System.out.println("Enter the value: ");
        var deposit = scanner.nextBigDecimal();

        accountService.deposit(accountNumber, deposit);
        System.out.println("Deposit made successfully!");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.next();
    }
}
