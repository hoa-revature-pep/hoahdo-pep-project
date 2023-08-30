package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {

    // Create Account & Message service objects
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    // Create Jackson ObjectMapper object
    ObjectMapper mapper = new ObjectMapper();

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // User Registration Endpoint
        app.post("/register", this::postAccountHandler);

        // User Login Endpoint
        app.post("/login", this::postLoginHandler);

        // Create New Message Endpoint
        app.post("/messages", this::postNewMessageHandler);

        // Get All Messages Endpoint
        app.get("messages", this::getAllMessagesHandler);

        // Get Message By Id Endpoint
        app.get("/messages/{message_id}", this::getMessageByIdHandler);

        // Delete Message By Id Endpoint
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);

        // Update Message By Id Endpoint
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);

        // Get All Messages By Account Id Endpoint
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /***********************************/
    /********** USER HANDLERS **********/
    /***********************************/

    // User Registration Handler
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(newAccount);

        if (addedAccount != null) {
            ctx.status(200);
            ctx.json(addedAccount);
        } else {
            ctx.status(400);
        }
    }

    // User Login Handler
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        Account userAccount = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.getAccount(userAccount.username, userAccount.password);

        if (verifiedAccount != null) {
            ctx.status(200);
            ctx.json(verifiedAccount);
        } else {
            ctx.status(401);
        }
    }

    /***********************************/
    /******** MESSAGE HANDLERS *********/
    /***********************************/

    // Create New Message Handler
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(newMessage);

        if (addedMessage != null) {
            ctx.status(200);
            ctx.json(addedMessage);
        } else {
            ctx.status(400);
        }
    }

    // Get All Messages Handler
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // Get Message By Id Handler
    private void getMessageByIdHandler(Context ctx) {
        String messageIdString = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdString);
        Message messageFoundById = messageService.getMessageById(messageId);

        if (messageFoundById != null) {
            ctx.json(messageFoundById);
        }

        ctx.status(200);
    }

    // Delete Message By Id Handler
    private void deleteMessageByIdHandler(Context ctx) {
        String messageIdString = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdString);
        Message messageDeletedById = messageService.deleteMessageById(messageId);

        if (messageDeletedById != null) {
            ctx.status(200);
            ctx.json(messageDeletedById);
        } else {
            ctx.status(200);
        }
    }

    // Update Message By Id Handler
    private void updateMessageByIdHandler(Context ctx) {
    }

    // Get All Messages By Account Id
    private void getAllMessagesByAccountIdHandler(Context ctx) {
    }

}
