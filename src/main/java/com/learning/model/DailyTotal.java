package com.learning.model;

import java.math.BigDecimal;
import java.sql.Date;

/** Income and expense totals for a single day, used by the dashboard chart. */
public class DailyTotal {
    private final Date date;
    private final BigDecimal income;
    private final BigDecimal expense;

    public DailyTotal(Date date, BigDecimal income, BigDecimal expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public Date getDate() { return date; }
    public BigDecimal getIncome() { return income; }
    public BigDecimal getExpense() { return expense; }
}
