package com.t0rchie.votingLinks;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private final String url;
    private final String username;
    private final String password;

    private Connection connection;
    private final Logger logger;

    public DatabaseManager(Logger logger, String host, int port, String dbName, String username, String password) {
        this.logger = logger;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&autoReconnect=true";
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Connected to database.");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS votingLinks_votes (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "username VARCHAR(32)," +
                    "votes INT DEFAULT 0," +
                    "last_vote TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    public void recordVote(String uuid, String username) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO votingLinks_votes (uuid, username, votes, last_vote) VALUES (?, ?, 1, NOW()) " +
                            "ON DUPLICATE KEY UPDATE votes = votes + 1, last_vote = NOW()"
            );
            ps.setString(1, uuid);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to record vote", e);
        }
    }

    public int getVoteCount(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT votes FROM votingLinks_votes WHERE uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("votes");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve vote count", e);
        }
        return 0;
    }

}
