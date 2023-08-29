package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// You will need to design and create your own DAO classes from scratch. 
// You should refer to prior mini-project lab examples and course material for guidance.

// Please refrain from using a 'try-with-resources' block when connecting to your database. 
// The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.

public class AccountDAO {

    // Create JDBC connection object to connect to database
    Connection connection = ConnectionUtil.getConnection();

    // Fetch a username from account table, identified by its username
    public String findUsernameInDatabase(String username) {
        try {
            String sql = "SELECT username FROM account WHERE username = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String foundUsername = new String(
                    rs.getString("username")
                );
                return foundUsername;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Fetch an account from account table, identified by its username and password
    public Account getAccountByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Insert a new account into account table
    public Account insertAccount(Account account) {
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ps.executeUpdate();
            ResultSet accountIdResultSet = ps.getGeneratedKeys();
            if (accountIdResultSet.next()) {
                int generatedAccountId = (int) accountIdResultSet.getLong(1);
                return new Account(generatedAccountId, account.username, account.password);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
