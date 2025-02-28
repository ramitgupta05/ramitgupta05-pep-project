package Controller;

import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService = new MessageService();
    AccountService accountService = new AccountService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        //Message path's
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessagesByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        app.delete("/messages/{message_id}", this::deleteAllMessagesByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.post("/messages", this::addMessageHandler);
        //Account path's
        app.post("/register", this::addAccountHandler);
        app.post("/login", this::VerifyAccountHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    /**
     * Handler to retrieve all messages.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.get method.
     */
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages()); 
    }

    /**
     * Handler to retrieve all messages using a particular message_id.
     * the message_id is retrieved from the path. 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.get method.
     */
    private void getAllMessagesByIDHandler(Context ctx) {
        try {
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessagesByID(message_id);
            if (message == null) {
                ctx.status(200).json("");  //Message not found
            } else {
                ctx.json(message);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid message ID format");
        }
    }

    private void getAllMessagesByUserHandler(Context ctx) {
        try {
            int posted_by = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> messages = messageService.getAllMessagesByUser(posted_by);
            if (messages == null) {
                ctx.status(200).json(""); //Message not found
            } else {
                ctx.json(messages);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid User id format");
        }
    }
    
    private void deleteAllMessagesByIDHandler(Context ctx) {
        try {
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message messages = messageService.deleteMessagesByID(message_id);
            if (messages == null) {
                ctx.status(200).json(""); //Message not found
            } else {
                ctx.json(messages);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid User id format");
        }
    }


    private void updateMessageHandler (Context ctx) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message mappedmessage = mapper.readValue(ctx.body(), Message.class);
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            //mappedmessage.getMessage_id();
            Message updatedMessage = messageService.updateMessages(message_id,mappedmessage);
            if(updatedMessage == null){
                ctx.status(400).json(""); //Message not found
            } else {
                ctx.json(updatedMessage);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid format");
        }
    }

    private void addMessageHandler (Context ctx) throws JsonProcessingException {
    try {
        ObjectMapper mapper = new ObjectMapper();
        Message mappedmessage = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.addMessage(mappedmessage);
        if(updatedMessage == null){
            ctx.status(400).json(""); //Message not found
        } else {
            ctx.json(updatedMessage);
        }
    } catch (NumberFormatException e) {
        ctx.status(400).json("Invalid format");
    }
    }

    private void addAccountHandler (Context ctx) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account mappedaccount = mapper.readValue(ctx.body(), Account.class);
            Account updatedAccount = accountService.addAccount(mappedaccount);
            if(updatedAccount == null){
                ctx.status(400).json(""); //Message not found
            } else {
                ctx.json(updatedAccount);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid format");
        }
    }

    private void VerifyAccountHandler(Context ctx) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account mappedaccount = mapper.readValue(ctx.body(), Account.class);
            Account updatedAccount = accountService.VerifyAccountbyUserPass(mappedaccount);
            if(updatedAccount == null){
                ctx.status(401).json(""); //Message not found
            } else {
                ctx.json(updatedAccount);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json("Invalid format");
        }
    }

}