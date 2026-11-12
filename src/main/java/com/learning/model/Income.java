package com.learning.model;

public class Income {
    int userId;
    int amount;
    int balance;
    int withdrawn;

    public Income(int userId, int amount, int balance, int withdrawn) {
        userId = this.userId;
        balance = this.balance;
        Income.this.amount = this.amount;
        Income.this.withdrawn = this.withdrawn;
    }
}
