package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    /**
     * A no-args contructor for MessageService that creates a MessageDAO object.
     */
    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    /**
     * Uses MessageDAO to add a message to the database.
     * 
     * @param message A Message object.
     * @return The message object if it was added to the database.
     */
    public Message addMessage(Message message) {
        boolean messageTextEmpty = message.message_text.isEmpty();
        boolean messageTextLength = message.message_text.length() < 255;
        boolean messagePosterExists = messageDAO.getPosterId(message.posted_by) > 0;

        if (!messageTextEmpty && messageTextLength && messagePosterExists) {
            return messageDAO.insertMessage(message);
        }

        return null;
    }

    /**
     * Uses MessageDAO to fetch all messages from the database.
     * 
     * @return All messages from the database as a List collection.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Uses MessageDAO to fetch a message by a message ID.
     * 
     * @param id The ID of the message.
     * @return The message object if it was found in the database.
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * Uses MessageDAO to delete a message by a message ID.
     * 
     * @param id The ID of the message.
     * @return The message object if it was deleted from the database.
     */
    public Message deleteMessageById(int id) {
        Message messageExists = messageDAO.getMessageById(id);
        boolean messageDeleted = messageDAO.deleteMessageById(id);

        if (messageDeleted) {
            return messageExists;
        }

        return null;
    }

    /**
     * Uses MessageDAO to update a message by a message ID.
     * 
     * @param id      The ID of the message.
     * @param message A message object with attributes to be updated.
     * @return The message object if it was updated in the database.
     */
    public Message updateMessageById(int id, Message message) {
        boolean messageExists = messageDAO.getMessageById(id) != null;
        boolean newMessageTextEmpty = message.message_text.isEmpty();
        boolean newMessageTextLength = message.message_text.length() < 255;
        boolean messageUpdateSuccessful = messageDAO.updateMessageById(id, message);

        if (messageExists && !newMessageTextEmpty && newMessageTextLength && messageUpdateSuccessful) {
            return messageDAO.getMessageById(id);
        }

        return null;
    }

    /**
     * Uses MessageDAO to fetch all messages by an account ID.
     * 
     * @param id The ID of the account.
     * @return All messages from the database as a List collection.
     */
    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }

}
