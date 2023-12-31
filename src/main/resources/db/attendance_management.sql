SET sql_safe_updates=0;
CREATE DATABASE IF NOT EXISTS goorm_attendance_management;
USE goorm_attendance_management;
ALTER DATABASE goorm_attendance_management DEFAULT CHARACTER SET utf8mb4;
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS attendances;
DROP TABLE IF EXISTS attendance_sessions;
DROP TABLE IF EXISTS applications;
SET foreign_key_checks = 1;
CREATE TABLE admins (
    admin_id VARCHAR(255) PRIMARY KEY,
    admin_password VARCHAR(255) NOT NULL,
    role ENUM('admin, supporters') NOT NULL
);
CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL
);
CREATE TABLE players (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    player_email VARCHAR(255) UNIQUE NOT NULL,
    player_password VARCHAR(255) NOT NULL,
    player_name VARCHAR(255) NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
CREATE TABLE attendances (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    attendance_date DATE DEFAULT (CURRENT_DATE),
    attendance_status ENUM('미입력', '출석', '부분출석', '결석', '공결', '휴가') NOT NULL,
    session_one INT DEFAULT 0,
    session_two INT DEFAULT 0,
    session_three INT DEFAULT 0,
    session_four INT DEFAULT 0,
    session_five INT DEFAULT 0,
    session_six INT DEFAULT 0,
    session_seven INT DEFAULT 0,
    session_eight INT DEFAULT 0,
    attendance_notes TEXT,
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);
CREATE TABLE applications
(
    application_id     INT AUTO_INCREMENT PRIMARY KEY,
    player_id          INT                           NOT NULL,
    application_date   DATE                          NOT NULL,
    application_type   ENUM ('외출', '조퇴', '공결', '휴가') NOT NULL,
    application_status ENUM ('대기', '승인', '거절'),
    application_reason TEXT                          NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players (player_id)
);
