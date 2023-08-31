package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    /**
     * A no-args contructor for AccountService that creates an AccountDAO object.
     */
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Uses AccountDAO to add a user account to the database.
     * 
     * @param account An Account object.
     * @return The account object if it was added to the database.
     */
    public Account addAccount(Account account) {
        boolean usernameNotEmpty = !account.username.isEmpty();
        boolean passwordLengthCorrect = account.password.length() >= 4;
        boolean usernameNotTaken = accountDAO.findUsername(account.username) == null;

        if (usernameNotEmpty &&
            passwordLengthCorrect &&
            usernameNotTaken) {
            return accountDAO.insertAccount(account);
        }

        return null;
    }

    /**
     * Uses AccountDAO to fetch an account from the database that matches the
     * username and password.
     * 
     * @param account An Account object.
     * @return The account object if it was found in the database.
     */
    public Account verifyAccount(Account account) {
        Account verifiedAccount = accountDAO.findAccountByUserInfo(
                account.username,
                account.password);

        if (verifiedAccount != null) {
            return verifiedAccount;
        }

        return null;
    }

}
