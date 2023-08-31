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

    /**
     * Instantiating a new Jackson ObjectMapper object for use in handler
     * methods to convert JSON into desired target object.
     */
    ObjectMapper mapper = new ObjectMapper();

    AccountService accountService;
    MessageService messageService;

    /**
     * A no-args constructor used for creating a new SocialMediaController with
     * new AccountService and MessageService objects.
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

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

        /**
         * POST
         * User Registration Endpoint
         */
        app.post("/register", this::createAccountHandler);

        /**
         * POST
         * User Login Endpoint
         */
        app.post("/login", this::verifyLoginHandler);

        /**
         * POST
         * Create New Message Endpoint
         */
        app.post("/messages", this::createMessageHandler);

        /**
         * GET
         * Get All Messages Endpoint
         */
        app.get("messages", this::getAllMessagesHandler);

        /**
         * GET
         * Get One Message By Given Message ID Endpoint
         */
        app.get("/messages/{message_id}", this::getOneMessageByIDHandler);

        /**
         * GET
         * Get All Messages By User Given Account ID Endpoint
         */
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIDHandler);

        /**
         * PATCH
         * Update Message By Given Message ID Endpoint
         */
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);

        /**
         * DELETE
         * Delete Message By Given Message ID Endpoint
         */
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);

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

    /*******************************************/
    /************** USER HANDLERS **************/
    /*******************************************/

    /**
     * Handler method used to create a new user account.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     * @throws JsonProcessingException Thrown if there is an issue with converting
     *                                 JSON into an object.
     */
    private void createAccountHandler(Context ctx) throws JsonProcessingException {
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        Account newlyAddedAccount = accountService.addAccount(newAccount);

        if (newlyAddedAccount != null) {
            ctx.status(200);
            ctx.json(newlyAddedAccount);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler method used to verify user login credentials.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     * @throws JsonProcessingException Thrown if there is an issue with converting
     *                                 JSON into an object.
     */
    private void verifyLoginHandler(Context ctx) throws JsonProcessingException {
        Account userInfo = mapper.readValue(ctx.body(), Account.class);
        Account verifiedUser = accountService.verifyAccount(userInfo);

        if (verifiedUser != null) {
            ctx.status(200);
            ctx.json(verifiedUser);
        } else {
            ctx.status(401);
        }
    }

    /*******************************************/
    /************ MESSAGE HANDLERS *************/
    /*******************************************/

    /**
     * Handler method used to create a new message.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     * @throws JsonProcessingException Thrown if there is an issue with converting
     *                                 JSON into an object.
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message newlyAddedMessage = messageService.addMessage(newMessage);

        if (newlyAddedMessage != null) {
            ctx.status(200);
            ctx.json(newlyAddedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler method used to fetch all messages.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();

        ctx.status(200);
        ctx.json(messages);
    }

    /**
     * Handler method used to fetch a message by a message ID.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     */
    private void getOneMessageByIDHandler(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageFoundByID = messageService.getMessageByID(messageID);

        if (messageFoundByID != null) {
            ctx.json(messageFoundByID);
        }

        ctx.status(200);
    }

    /**
     * Handler method used to fetch all messages by an account ID.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     */
    private void getAllMessagesByAccountIDHandler(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountID(accountID);

        ctx.status(200);
        ctx.json(messages);
    }

    /**
     * Handler method used to update a message by a message ID.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     * @throws JsonProcessingException Thrown if there is an issue with converting
     *                                 JSON into an object.
     */
    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageUpdate = mapper.readValue(ctx.body(), Message.class);
        Message messageUpdatedByID = messageService.updateMessageByID(messageID, messageUpdate);

        if (messageUpdatedByID != null) {
            ctx.status(200);
            ctx.json(messageUpdatedByID);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler method used to delete a message by a message ID.
     * 
     * @param ctx The Javalin Context object that contains request and response
     *            information and functionality.
     */
    private void deleteMessageByIDHandler(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageDeletedByID = messageService.deleteMessageByID(messageID);

        if (messageDeletedByID != null) {
            ctx.json(messageDeletedByID);
        }

        ctx.status(200);
    }

}
