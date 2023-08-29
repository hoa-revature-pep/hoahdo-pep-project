package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    // Create Account, Message service objects
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

    //
}