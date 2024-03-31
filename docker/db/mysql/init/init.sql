GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess-temp` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `chess-temp`;

CREATE TABLE IF NOT EXISTS user
(
    user_id VARCHAR(12) NOT NULL,
    name    VARCHAR(64) NOT NULL,
    PRIMARY KEY (user_id)
);

# INSERT INTO user (user_id, name)
# VALUES ('pobiconan', 'pobi'),
#        ('sugarbrown', 'brown');
