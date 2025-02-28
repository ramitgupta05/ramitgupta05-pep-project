package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import Util.FileUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    
    public class CreateTablesandInsertInto {

        public void SocialMedia(){
            //Using given SQL logic in the SocialMedia.sql file.
            String sql = FileUtil.parseSQLFile("SocialMedia.sql");
            
            try {
                Connection connection = ConnectionUtil.getConnection();
                Statement s = connection.createStatement();
                s.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("SocialMedia: " + e.getMessage() + '\n');
            }
        }
    }

    //Register new Account in Account table
    public Account registerAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        Account insertedAccount = null;
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            int rowsUpdated = preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                if (rowsUpdated > 0 && account.getUsername() != "" && account.getPassword().length() > 4) {
                    insertedAccount = new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
                return insertedAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
            
    //Retrieve Account Information to Verify Login Test
    public Account getAccountByUserPass(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
    







