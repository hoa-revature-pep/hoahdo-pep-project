package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    // Create message DAO object
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    // Add a message to the database
    public Message addMessage(Message message) {
        boolean messageTextEmpty = message.message_text.isEmpty();
        boolean messageTextLength = message.message_text.length() < 255;
        boolean messagePosterExists = messageDAO.getPosterId(message.posted_by) > 0;

        if (!messageTextEmpty && messageTextLength && messagePosterExists) {
            return messageDAO.insertMessage(message);
        }

        return null;
    }

    // Fetch all messages from the database
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Fetch message from database by its id
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    // Delete message from database by its id
    public Message deleteMessageById(int id) {
        Message messageExists = messageDAO.getMessageById(id);
        boolean messageDeleted = messageDAO.deleteMessageById(id);

        if (messageDeleted) {
            return messageExists;
        }

        return null;
    }

    // Update a message from database by its id
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

    // Fecth all messages from database by account id
    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }
}
