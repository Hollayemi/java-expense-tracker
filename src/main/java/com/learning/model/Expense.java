package com.learning.model;

public class Expense {
    int amount;
    String remark;
    int userId;

    public Expense(int userId, int amount, String remark ) {
        userId = this.userId;
        remark = this.remark;
        amount = this.amount;
    }
}
