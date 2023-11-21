SET sql_safe_updates=0;

CREATE DATABASE IF NOT EXISTS goorm_attendance_management;
USE goorm_attendance_management;
ALTER DATABASE goorm_attendance_management DEFAULT CHARACTER SET utf8mb4;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS players;
# DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS attendance_dates;
DROP TABLE IF EXISTS attendance_sessions;
SET foreign_key_checks = 1;

CREATE TABLE admins (
    admin_id VARCHAR(20),
    admin_pw VARCHAR(255),
    PRIMARY KEY (admin_id)
);

CREATE TABLE players (
    player_id INT AUTO_INCREMENT,
    player_pw VARCHAR(255),
    player_name VARCHAR(20),
    player_email VARCHAR(100),
    player_course VARCHAR(20),
    PRIMARY KEY (player_id)
);

CREATE TABLE attendance_dates (
    date_id INT AUTO_INCREMENT,
    player_id INT,
    attendance_date DATE,
    PRIMARY KEY (date_id),
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);

CREATE TABLE attendance_sessions (
    session_id INT AUTO_INCREMENT,
    date_id INT,
    session_one INT,
    session_two INT,
    session_three INT,
    session_four INT,
    session_five INT,
    session_six INT,
    session_seven INT,
    session_eight INT,
    PRIMARY KEY (session_id),
    FOREIGN KEY (date_id) REFERENCES attendance_dates(date_id)
);

INSERT INTO admins
VALUES ('goorm', '1234');

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('가', '123456','가@goorm.com',  '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (1, 1, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (1, 1, 1, 1, 1, 0, 1, 0, 1, 0);

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('나', '223456','나@goorm.com', '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (2, 2, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (2, 2, 2, 1, 1, 0, 1, 0, 1, 0);


INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('다', '323456','다@goorm.com', '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (3, 3, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (3, 3, 3, 1, 1, 0, 1, 0, 1, 0);

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('라', '423456','라@goorm.com', '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (4, 4, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (4, 4, 4, 1, 1, 0, 1, 0, 1, 0);

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('마', '523456','미@goorm.com', '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (5, 5, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (5, 5, 5, 1, 1, 0, 1, 0, 1, 0);

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('바', '623456','바@goorm.com', '풀스택 3회차');
INSERT INTO attendance_dates
VALUES (6, 6, '2023-11-20');
INSERT INTO attendance_sessions
VALUES (6, 6, 6, 1, 1, 0, 1, 0, 1, 0);


SELECT * FROM admins;
SELECT * FROM players;
SELECT * FROM attendance_dates;
SELECT * FROM attendance_sessions;