package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //test passed
    public Account addAccount(Account account){
        account = accountDAO.registerAccount(account);
        if (account != null) {
        return account; }
        else { return null; }
    }

    //Verify Account Login
    public Account VerifyAccountbyUserPass(Account account) {
        return accountDAO.getAccountByUserPass(account);
    }







}