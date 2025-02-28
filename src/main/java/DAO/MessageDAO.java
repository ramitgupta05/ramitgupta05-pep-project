package DAO;

import Model.Message;

import Util.ConnectionUtil;
import Util.FileUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {


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


    // Retrieve All Messages Test
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;"; //Select all of the rows of the message table.
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }

    //Retrieve Message by Message ID Test
    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    //Retrieve All Messages for User Test
    public List<Message> getAllMessagesByUser(int posted_by){    // tried using account_iD instead of posted_by
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?;"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1,posted_by);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Delete Message by Message ID Test
    public Message deleteMessage(int message_id) {  // replaced void with Flight
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = null;
        try {
            //Write SQL logic here
            String sql1 = "DELETE FROM message WHERE message_id=?;"; 
            PreparedStatement deletedStatement = connection.prepareStatement(sql1);

            //write PreparedStatement setString and setInt methods here.
            deletedStatement.setInt(1,message_id);

            String sql2 = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1,message_id);

            ResultSet rs = preparedStatement.executeQuery();
            deletedStatement.executeUpdate();
            
            while(rs.next()) {
            deletedMessage = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch") 
            );}

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return deletedMessage; //added a return statement
    }


    //Update Message Text in message Table
    public Message updateMessage(int message_id, Message message){  
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,message_id);
            preparedStatement.executeUpdate();
            if (message.getMessage_text() != "") {
                return getMessageByID(message_id); }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        //return new Message(message_id, message.message_text, existingTimePostedEpoch); //added a return statement
    }

    //Create New Message in Table Test
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        Message insertedMessage = null;
        try {
            //Write SQL logic here. When inserting, you only need to define the message_id and posted_by
            //values (two columns total!)
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            int rowsUpdated = preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                if (rowsUpdated > 0 && message.getMessage_text() != "") {
                    insertedMessage = new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }
                return insertedMessage;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    //Create Message Test
    


    //User Login Test using Account Table



    //User Registration test Using Account Table

}