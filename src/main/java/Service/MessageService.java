package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //test passed
    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    //test passed
    public Message getMessagesByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    //test passed
    public List<Message> getAllMessagesByUser(int posted_by) {
        List<Message> messages = messageDAO.getAllMessagesByUser(posted_by);
        if (messages != null) {
            return messages; }
        return new ArrayList<>();
    }

    //test passed
    public Message deleteMessagesByID(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    //test passed
    public Message updateMessages(int message_id, Message message){
        Message existingMessage = messageDAO.updateMessage(message_id,message);
        if (existingMessage != null) {return existingMessage;}
        else { return null; }
        //    return messageDAO.getMessageByID(message_id); }
    }

    //test passed
    public Message addMessage(Message message){
        message = messageDAO.insertMessage(message);
        if (message != null) {
        return message; }
        else { return null; }
    }









}