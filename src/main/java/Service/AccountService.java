package Service;

import Model.Account;
import DAO.AccountDAO;

// You will need to design and create your own Service classes from scratch.
// You should refer to prior mini-project lab examples and course material for guidance.

public class AccountService {

    // Create account DAO object
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    // Add an account to the database
    public Account addAccount(Account account) {
        // check for empty username
        boolean usernameEmpty = account.username.isEmpty();
        // check for correct password length
        boolean passwordLength = account.password.length() >= 4;
        // check for pre-existing account name
        boolean usernameNotTaken = accountDAO.findUsernameInDatabase(account.username) == null;

        if (!usernameEmpty && passwordLength && usernameNotTaken) {
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    // Get an account from a database that matches the username and password
    public Account getAccount(String username, String password) {
        Account fetchedAccount = accountDAO.getAccountByUsernameAndPassword(username, password);

        if (fetchedAccount != null) {
            return fetchedAccount;
        }

        return null;
    }


}
