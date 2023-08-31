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
        boolean messageTextNotEmpty = !message.message_text.isEmpty();
        boolean messageTextLengthCorrect = message.message_text.length() < 255;
        boolean messagePosterExists = messageDAO.findPosterID(message.posted_by) > 0;

        if (messageTextNotEmpty && messageTextLengthCorrect && messagePosterExists) {
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
        return messageDAO.findAllMessages();
    }

    /**
     * Uses MessageDAO to fetch a message by a message ID.
     * 
     * @param id The ID of the message.
     * @return The message object if it was found in the database.
     */
    public Message getMessageByID(int id) {
        return messageDAO.findMessageByID(id);
    }

    /**
     * Uses MessageDAO to fetch all messages by an account ID.
     * 
     * @param id The ID of the account.
     * @return All messages from the database as a List collection.
     */
    public List<Message> getAllMessagesByAccountID(int id) {
        return messageDAO.findAllMessagesByAccountID(id);
    }

    /**
     * Uses MessageDAO to update a message by a message ID.
     * 
     * @param id      The ID of the message.
     * @param message A message object with attributes to be updated.
     * @return The message object if it was updated in the database.
     */
    public Message updateMessageByID(int id, Message message) {
        boolean messageExists = messageDAO.findMessageByID(id) != null;
        boolean messageTextNotEmpty = !message.message_text.isEmpty();
        boolean messageTextLengthCorrect = message.message_text.length() < 255;
        boolean messageUpdated = messageDAO.updateMessageByID(id, message);

        if (messageExists &&
            messageTextNotEmpty &&
            messageTextLengthCorrect &&
            messageUpdated) {
            return messageDAO.findMessageByID(id);
        }

        return null;
    }

    /**
     * Uses MessageDAO to delete a message by a message ID.
     * 
     * @param id The ID of the message.
     * @return The message object if it was deleted from the database.
     */
    public Message deleteMessageByID(int id) {
        Message messageToBeDeleted = messageDAO.findMessageByID(id);
        boolean messageDeleted = messageDAO.deleteMessageByID(id);

        if (messageDeleted) {
            return messageToBeDeleted;
        }

        return null;
    }

}
