package br.com.jdbc.studies;

import br.com.jdbc.studies.domain.account.Account;
import br.com.jdbc.studies.domain.account.AccountService;
import br.com.jdbc.studies.domain.account.DateAccount;
import br.com.jdbc.studies.domain.client.DateClient;
import br.com.jdbc.studies.domain.exception.BusinessRulesException;

import java.util.Scanner;

import static br.com.jdbc.studies.domain.client.Client.isValidCPF;
import static br.com.jdbc.studies.domain.client.Client.isValidEmail;

public class CashBank {
    private static final AccountService accountService = new AccountService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        var option = viewMenu();
        while (option != 0) {
            try {
                switch (option) {
                    case 1 -> getAllAccounts();
                    case 2 -> openAccount();
                    case 3 -> closeAccount();
                    case 4 -> viewBalance();
                    case 5 -> withdraw();
                    case 6 -> deposition();
                    case 7 -> makeTransfer();
                    default -> throw new IllegalArgumentException("Invalid option: " + option);
                }
            } catch (BusinessRulesException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press any key and ENTER to return to the menu");
                scanner.nextLine();
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
                7 - Transfer
                0 - Exit
                """);
        return scanner.nextInt();
    }

    private static void getAllAccounts(){
        System.out.println("Available accounts");
        var accounts = accountService.getAccounts();
        accounts.forEach(System.out::println);

        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void openAccount(){
        scanner.nextLine();

        String name;
        while (true) {
            System.out.println("Enter the client's name:");
            name = scanner.nextLine();
            if (name.length() >= 4) {
                break;
            } else {
                System.out.println("Name must have at least 4 characters. Please try again.");
            }
        }

        String cpf;
        while (true) {
            System.out.println("Enter the client's CPF:");
            cpf = scanner.nextLine();
            if (isValidCPF(cpf)) {
                cpf = cpf.replaceAll("\\D", "");
                break;
            } else {
                System.out.println("Invalid CPF. Please enter a valid CPF in the format ###.###.###-## or ###########.");
            }
        }

        String email;
        while (true) {
            System.out.println("Enter the client's email:");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        var accountNumber = Account.generateAccountNumber();

        accountService.addAccount(new DateAccount(accountNumber, new DateClient(name, cpf, email)));

        System.out.println("Account opened successfully.");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void closeAccount(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        accountService.closeAccount(accountNumber);

        System.out.println("Account closed successfully.");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void viewBalance(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();
        var balance = accountService.getBalance(accountNumber);
        System.out.println("Balance: " + balance);

        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void withdraw(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        System.out.println("Enter the value: ");
        var value = scanner.nextBigDecimal();

        accountService.withdraw(accountNumber, value);
        System.out.println("Withdrawal successful!");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void deposition(){
        System.out.println("Enter the client's account number:");
        int accountNumber = scanner.nextInt();

        System.out.println("Enter the value: ");
        var deposit = scanner.nextBigDecimal();

        accountService.deposit(accountNumber, deposit);
        System.out.println("Deposit made successfully!");
        System.out.println("Press any key and ENTER to return to the menu");
        scanner.nextLine();
    }

    private static void makeTransfer() {
        System.out.println("Enter the source account number:");
        var sourceAccount = scanner.nextInt();

        System.out.println("Enter the destination account number:");
        var destinationAccount = scanner.nextInt();

        System.out.println("Enter the amount to be transferred:");
        var valueTransferred = scanner.nextBigDecimal();

        accountService.transfer(sourceAccount, destinationAccount, valueTransferred);

        System.out.println("Transfer completed successfully!");
        System.out.println("Press any key and ENTER to return to the main menu");
        scanner.nextLine();
    }
}
