SET sql_safe_updates=0;

CREATE DATABASE IF NOT EXISTS goorm_attendance_management;
USE goorm_attendance_management;
ALTER DATABASE goorm_attendance_management DEFAULT CHARACTER SET utf8mb4;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS attendances;
SET foreign_key_checks = 1;

CREATE TABLE admins (
    admin_id VARCHAR(20) NOT NULL,
    admin_pw VARCHAR(255) NOT NULL,
    PRIMARY KEY (admin_id)
);

CREATE TABLE players (
    player_id INT AUTO_INCREMENT,
    player_pw VARCHAR(255) NOT NULL,
    player_name VARCHAR(20) NOT NULL,
    player_email VARCHAR(100) DEFAULT 'youremail@groom.io',
    player_course VARCHAR(20) NOT NULL,
    PRIMARY KEY (player_id)
);

CREATE TABLE attendances (
    attendance_id INT AUTO_INCREMENT,
    player_id INT,
    attendance_date DATE DEFAULT CURRENT_DATE,
    session_one INT DEFAULT 0,
    session_two INT DEFAULT 0,
    session_three INT DEFAULT 0,
    session_four INT DEFAULT 0,
    session_five INT DEFAULT 0,
    session_six INT DEFAULT 0,
    session_seven INT DEFAULT 0,
    session_eight INT DEFAULT 0,
    PRIMARY KEY (attendance_id),
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);


INSERT INTO admins
VALUES ('goorm', '1234');

INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('가', '123456','가@goorm.io',  '풀스택 3회차');
INSERT INTO attendances (player_id, attendance_date, session_one, session_two, session_three
                       , session_four, session_five, session_six, session_seven, session_eight)
VALUES (1, CURRENT_DATE(), 1, 1, 1, 1, 1, 1, 1, 1); # 정상 출석


INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('나', '223456','나@goorm.io', '풀스택 3회차');
INSERT INTO attendances (player_id, attendance_date, session_one, session_two, session_three
                       , session_four, session_five, session_six, session_seven, session_eight)
VALUES (2, CURRENT_DATE(), 0, 0, 1, 1, 1, 1, 1, 1); # 지각


INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('다', '323456','다@goorm.io', '풀스택 3회차');
INSERT INTO attendances (player_id, attendance_date, session_one, session_two, session_three
                       , session_four, session_five, session_six, session_seven, session_eight)
VALUES (3, CURRENT_DATE(), 1, 1, 1, 1, 1, 1, 0, 0); # 조퇴


INSERT INTO players (player_name, player_pw, player_email, player_course)
VALUES ('라', '423456','라@goorm.io', '풀스택 3회차');
INSERT INTO attendances (player_id, attendance_date, session_one, session_two, session_three
                       , session_four, session_five, session_six, session_seven, session_eight)
VALUES (4, CURRENT_DATE(), 1, 0, 1, 0, 1, 0, 0, 0); # 결석


SELECT * FROM admins;
SELECT * FROM players;
SELECT * FROM attendances;