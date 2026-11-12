-- Optional demo data matching the dashboard screenshot.
-- 1) Register a user named "cedric" through the app first.
-- 2) Then run this file.

INSERT INTO incomes (user_id, title, amount, tx_date)
SELECT id, v.title, v.amount, v.d::date FROM users,
  (VALUES ('Salary', 4000, '2024-03-01'),
          ('Cash Interest', 200, '2024-03-05'),
          ('Dividend', 180, '2024-03-15'),
          ('Rent Income', 1850, '2024-03-28')) AS v(title, amount, d)
WHERE username = 'cedric';

INSERT INTO expenses (user_id, title, amount, tx_date)
SELECT id, v.title, v.amount, v.d::date FROM users,
  (VALUES ('Car Expense', 920, '2024-03-01'),
          ('Utility', 320, '2024-03-05'),
          ('Grocery buying', 300, '2024-03-15'),
          ('Flight ticket', 1200, '2024-03-28')) AS v(title, amount, d)
WHERE username = 'cedric';
