package Service;

import Model.Message;
import DAO.MessageDAO;

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

}
