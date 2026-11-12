package com.learning.dao;

import com.learning.model.DailyTotal;
import com.learning.model.Transaction;
import com.learning.model.TransactionType;
import com.learning.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean add(TransactionType type, int userId, String title, BigDecimal amount, Date date) {
        String sql = "INSERT INTO " + type.getTable() + " (user_id, title, amount, tx_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setBigDecimal(3, amount);
            stmt.setDate(4, date);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** user_id is in the WHERE clause so a user can only delete their own rows. */
    public boolean delete(TransactionType type, int id, int userId) {
        String sql = "DELETE FROM " + type.getTable() + " WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Transaction> findAll(TransactionType type, int userId) {
        String sql = "SELECT id, user_id, title, amount, tx_date FROM " + type.getTable()
                + " WHERE user_id = ? ORDER BY tx_date DESC, id DESC";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs, type));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Latest incomes and expenses mixed together (the "Recent History" panel). */
    public List<Transaction> recent(int userId, int limit) {
        String sql =
                "SELECT id, user_id, title, amount, tx_date, 'INCOME' AS type FROM incomes WHERE user_id = ? " +
                "UNION ALL " +
                "SELECT id, user_id, title, amount, tx_date, 'EXPENSE' AS type FROM expenses WHERE user_id = ? " +
                "ORDER BY tx_date DESC, id DESC LIMIT ?";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            stmt.setInt(3, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs, TransactionType.valueOf(rs.getString("type"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** COALESCE turns "no rows" (NULL) into 0. */
    public BigDecimal total(TransactionType type, int userId) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM " + type.getTable() + " WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /** One row per day with that day's income and expense totals (for the chart). */
    public List<DailyTotal> dailyTotals(int userId) {
        String sql =
                "SELECT d, SUM(income) AS income, SUM(expense) AS expense FROM ( " +
                "  SELECT tx_date AS d, amount AS income, CAST(0 AS NUMERIC) AS expense FROM incomes WHERE user_id = ? " +
                "  UNION ALL " +
                "  SELECT tx_date AS d, CAST(0 AS NUMERIC) AS income, amount AS expense FROM expenses WHERE user_id = ? " +
                ") t GROUP BY d ORDER BY d";
        List<DailyTotal> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new DailyTotal(rs.getDate("d"), rs.getBigDecimal("income"), rs.getBigDecimal("expense")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Transaction map(ResultSet rs, TransactionType type) throws SQLException {
        return new Transaction(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getBigDecimal("amount"),
                rs.getDate("tx_date"),
                type);
    }
}
