#!/bin/bash

echo $PROJECT_DB_PASSWORD
mysql -v --auto-rehash --host="$PROJECT_DB_HOST" --password="$PROJECT_DB_PASSWORD" --user web <<EOF

# Select toshl_chart database
USE diacritics;

# Drop all tables

DROP TABLE IF EXISTS users;

# Create  table users
CREATE TABLE users (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  PRIMARY KEY  (id)
);

EOF
