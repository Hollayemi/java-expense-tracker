-- Run once against the "expense-tracker" database.
-- Note: "user" is a reserved word in PostgreSQL, so the table is called "users".

CREATE TABLE IF NOT EXISTS users (
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password      VARCHAR(100) NOT NULL,          -- BCrypt hash, never plain text
    profile_image VARCHAR(500),
    bio           VARCHAR(255),
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS incomes (
    id       SERIAL PRIMARY KEY,
    user_id  INT           NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title    VARCHAR(100)  NOT NULL,
    amount   NUMERIC(12,2) NOT NULL CHECK (amount > 0),
    tx_date  DATE          NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS expenses (
    id       SERIAL PRIMARY KEY,
    user_id  INT           NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title    VARCHAR(100)  NOT NULL,
    amount   NUMERIC(12,2) NOT NULL CHECK (amount > 0),
    tx_date  DATE          NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX IF NOT EXISTS idx_incomes_user_date  ON incomes (user_id, tx_date);
CREATE INDEX IF NOT EXISTS idx_expenses_user_date ON expenses (user_id, tx_date);
