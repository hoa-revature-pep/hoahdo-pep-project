package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    /**
     * Instantiating a connection object to connect to the database.
     */
    Connection conn = ConnectionUtil.getConnection();

    /**
     * Add a message to the message table.
     * 
     * @param message A message object.
     * @return The message object added to the table.
     */
    public Message insertMessage(Message message) {
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);
            ps.setLong(3, message.time_posted_epoch);

            ps.executeUpdate();
            ResultSet messageIdResultSet = ps.getGeneratedKeys();
            if (messageIdResultSet.next()) {
                int generatedMessageId = (int) messageIdResultSet.getLong(1);
                Message newlyAddedMessage = new Message(
                        generatedMessageId,
                        message.posted_by,
                        message.message_text,
                        message.time_posted_epoch);
                return newlyAddedMessage;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Fetch posted_by ID from message table by its poster ID.
     * 
     * @param id The ID of the poster.
     * @return The ID of the poster found in the table.
     */
    public int getPosterId(int id) {
        try {
            String sql = "SELECT posted_by FROM message WHERE posted_by = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int foundPosterId = rs.getInt("posted_by");
                return foundPosterId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    /**
     * Fetch all messages from message table.
     * 
     * @return All messages from the table as a List Collection.
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     * Fetch a message from message table by its message ID.
     * 
     * @param id The ID of the message.
     * @return The message object found in the table.
     */
    public Message getMessageById(int id) {
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Delete a message in message table by its message ID.
     * 
     * @param id The ID of the message.
     * @return True if the message object was deleted from the table.
     */
    public boolean deleteMessageById(int id) {
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int numberOfUpdatedRows = ps.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Update a message in message table by its message ID.
     * 
     * @param id      The ID of the message.
     * @param message A message object with attributes to be updated.
     * @return True if the message object was updated in the table.
     */
    public boolean updateMessageById(int id, Message message) {
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, message.message_text);
            ps.setInt(2, id);

            int numberOfUpdatedRows = ps.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * Fetch all messages from message table by an account ID.
     * 
     * @param id The ID of the account.
     * @return All messages from the table as a List collection.
     */
    public List<Message> getAllMessagesByAccountId(int id) {
        List<Message> messages = new ArrayList<Message>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
