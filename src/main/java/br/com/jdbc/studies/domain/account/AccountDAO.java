package br.com.jdbc.studies.domain.account;

import br.com.jdbc.studies.domain.client.Client;
import br.com.jdbc.studies.domain.client.DateClient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AccountDAO {

    private final Connection connection;

    AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(DateAccount dateAccount) {
        var client = new Client(dateAccount.dateClient());
        var account = new Account(dateAccount.number(), BigDecimal.ZERO, client);

        String sql = "INSERT INTO account (number, balance, client_name, client_cpf, client_email)" +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account.getNumber());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dateAccount.dateClient().name());
            preparedStatement.setString(4, dateAccount.dateClient().cpf());
            preparedStatement.setString(5, dateAccount.dateClient().email());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Account> showList() {
        PreparedStatement ps;
        ResultSet resultSet;
        Set<Account> accounts = new HashSet<>();

        String sql = "SELECT * FROM account WHERE is_active = true";

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int number = resultSet.getInt(1);
                BigDecimal balance = resultSet.getBigDecimal(2);
                String name = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);

                DateClient dateClient =
                        new DateClient(name, cpf, email);
                Client client = new Client(dateClient);

                accounts.add(new Account(number, balance, client));
            }
            resultSet.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    public Account listForNumber(Integer number) {
        String sql = "SELECT * FROM account WHERE number = ? AND is_active = true";

        PreparedStatement ps;
        ResultSet resultSet;
        Account account = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, number);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int numberSaved = resultSet.getInt(1);
                BigDecimal balance = resultSet.getBigDecimal(2);
                String name = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);

                DateClient dateClient =
                        new DateClient(name, cpf, email);
                Client client = new Client(dateClient);

                account = new Account(numberSaved, balance, client);
            }
            resultSet.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public void alteration(Integer number, BigDecimal value) {
        PreparedStatement ps;
        String sql = "UPDATE account SET balance = ? WHERE number = ?";

        try {
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, value);
            ps.setInt(2, number);

            ps.execute();
            ps.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void changeStatusAccount(Integer numeroDaConta) {
        String sql = "UPDATE account SET is_active = false WHERE number = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, numeroDaConta);

            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
