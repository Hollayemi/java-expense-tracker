package com.learning.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * One row from either the incomes or the expenses table.
 * Replaces the old Income and Expense classes. Balance is not stored,
 * it is calculated with SQL (total income minus total expense).
 */
public class Transaction {
    private final int id;
    private final int userId;
    private final String title;
    private final BigDecimal amount;
    private final Date date;
    private final TransactionType type;

    public Transaction(int id, int userId, String title, BigDecimal amount, Date date, TransactionType type) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public Date getDate() { return date; }
    public TransactionType getType() { return type; }
    public boolean isIncome() { return type == TransactionType.INCOME; }
}
