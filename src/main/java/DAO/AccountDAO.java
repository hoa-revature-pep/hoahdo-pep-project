package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {

    /**
     * Instantiating a connection object to connect to the database.
     */
    Connection conn = ConnectionUtil.getConnection();

    /**
     * Fetch a username from the account table.
     * 
     * @param username The username of an account.
     * @return The username found in the table.
     */
    public String findUsername(String username) {
        try {
            String sql = "SELECT username FROM account WHERE username = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String foundUsername = new String(
                        rs.getString("username"));
                return foundUsername;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Fetch an account from the account table by its username and password.
     * 
     * @param username The username of an account.
     * @param password The password of an account.
     * @return The account object found in the table.
     */
    public Account findAccountByUserInfo(String username, String password) {
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account foundAccount = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return foundAccount;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Add an account to the account table.
     * 
     * @param account An Account object.
     * @return The account object added to the table.
     */
    public Account insertAccount(Account account) {
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ps.executeUpdate();
            ResultSet rsAccountID = ps.getGeneratedKeys();
            if (rsAccountID.next()) {
                int generatedAccountID = (int) rsAccountID.getLong(1);
                Account insertedAccount = new Account(
                        generatedAccountID,
                        account.username,
                        account.password);
                return insertedAccount;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
