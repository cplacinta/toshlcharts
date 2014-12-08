#!/bin/bash

echo $PROJECT_DB_PASSWORD
mysql -v --auto-rehash --host="$PROJECT_DB_HOST" --password="$PROJECT_DB_PASSWORD" --user web <<EOF

# Select toshl_charts database
USE toshl_charts;

# Drop all tables
DROP TABLE IF EXISTS expenses_tags;
DROP TABLE IF EXISTS expenses;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;

# Create  table users
CREATE TABLE users (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL
);

# Create  table expenses
CREATE TABLE expenses(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  date DATE,
  amount DECIMAL,
  income DECIMAL,
  currency VARCHAR(10),
  description VARCHAR(45),
  user_id INT
);

# Create  table tags
CREATE TABLE tags(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id INT DEFAULT 0,
  name VARCHAR(20),
  UNIQUE tag_user (user_id, name)
);

# Create  table expenses_tags
CREATE TABLE expenses_tags(
  expense_id INT UNSIGNED,
  tag_id INT UNSIGNED,
  PRIMARY KEY (expense_id, tag_id),
  FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

EOF

#Expenses -> FK userId, FK tag_id
#Tags -> FK userId, unique per userId and name