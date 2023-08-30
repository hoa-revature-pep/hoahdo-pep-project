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

    // Create JDBC connection object to connect to database //
    Connection connection = ConnectionUtil.getConnection();

    // Insert a new message into message table
    public Message insertMessage(Message message) {
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.posted_by);
            ps.setString(2, message.message_text);
            ps.setLong(3, message.time_posted_epoch);

            ps.executeUpdate();
            ResultSet messageIdResultSet = ps.getGeneratedKeys();
            if (messageIdResultSet.next()) {
                int generatedMessageId = (int) messageIdResultSet.getLong(1);
                return new Message(
                        generatedMessageId,
                        message.posted_by,
                        message.message_text,
                        message.time_posted_epoch);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Fetch a posted_by from message table by its poster Id
    public int getPosterId(int id) {
        try {
            String sql = "SELECT posted_by FROM message WHERE posted_by = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
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

    // Fetch all messages from message table
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";

            PreparedStatement ps = connection.prepareStatement(sql);

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

    // Fetch a message from message table by its message id
    public Message getMessageById(int id) {
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
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

}
