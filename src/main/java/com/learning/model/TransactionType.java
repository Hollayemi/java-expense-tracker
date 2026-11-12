package com.learning.model;

public enum TransactionType {
    INCOME("incomes", "Incomes"),
    EXPENSE("expenses", "Expenses");

    private final String table;
    private final String label;

    TransactionType(String table, String label) {
        this.table = table;
        this.label = label;
    }

    /** Table name comes only from this enum, so it is safe to concatenate into SQL. */
    public String getTable() { return table; }
    public String getLabel() { return label; }
}
