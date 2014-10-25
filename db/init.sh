#!/bin/bash

mysql -v --auto-rehash --host="$PROJECT_DB_HOST" --password="$PROJECT_DB_PASSWORD" --user root <<EOF

# Drop toshlcharts db
DROP DATABASE IF EXISTS toshl_charts;

# Create toshlcharts database
CREATE DATABASE IF NOT EXISTS toshl_charts
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_romanian_ci;

# Add web user
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,ALTER,DROP,INDEX,CREATE TEMPORARY TABLES
        ON toshlcharts.*
        TO web@'localhost'
        IDENTIFIED BY '$PROJECT_DB_PASSWORD';

# Commit access control changes
FLUSH PRIVILEGES;

# Select admin database
USE mysql;

# Set admin pass
UPDATE user SET Password = PASSWORD('$PROJECT_DB_PASSWORD') WHERE User='root';

EOF
