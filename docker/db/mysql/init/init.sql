GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS `chess-test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS board
(
    id            BIGINT     NOT NULL AUTO_INCREMENT,
    current_color VARCHAR(5) NOT NULL CHECK ( current_color IN ('WHITE', 'BLACK')),
    winner_color  VARCHAR(5) NOT NULL CHECK ( winner_color IN ('NONE', 'WHITE', 'BLACK')),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    board_id BIGINT      NOT NULL,
    type     VARCHAR(10) NOT NULL CHECK ( type IN ('NONE', 'PAWN', 'ROOK', 'KNIGHT', 'BISHOP', 'QUEEN', 'KING')),
    color    VARCHAR(5)  NOT NULL CHECK ( color IN ('NONE', 'WHITE', 'BLACK')),
    file     INT         NOT NULL CHECK ( file between 1 AND 8 ),
    `rank`   INT         NOT NULL CHECK ( `rank` between 1 AND 8 ),
    PRIMARY KEY (id),
    FOREIGN KEY (board_id) REFERENCES board (id)
);

USE `chess-test`;

CREATE TABLE IF NOT EXISTS board
(
    id            BIGINT     NOT NULL AUTO_INCREMENT,
    current_color VARCHAR(5) NOT NULL CHECK ( current_color IN ('WHITE', 'BLACK')),
    winner_color  VARCHAR(5) NOT NULL CHECK ( winner_color IN ('NONE', 'WHITE', 'BLACK')),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    board_id BIGINT      NOT NULL,
    type     VARCHAR(10) NOT NULL CHECK ( type IN ('NONE', 'PAWN', 'ROOK', 'KNIGHT', 'BISHOP', 'QUEEN', 'KING')),
    color    VARCHAR(5)  NOT NULL CHECK ( color IN ('NONE', 'WHITE', 'BLACK')),
    file     INT         NOT NULL CHECK ( file between 1 AND 8 ),
    `rank`   INT         NOT NULL CHECK ( `rank` between 1 AND 8 ),
    PRIMARY KEY (id),
    FOREIGN KEY (board_id) REFERENCES board (id)
);
