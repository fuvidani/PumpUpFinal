DROP TABLE picturehistory;
DROP TABLE bodyfathistory;
DROP TABLE weighthistory;
DROP TABLE user;
DROP SEQUENCE picturehistory_seq;
DROP SEQUENCE bodyfathistory_seq;
DROP SEQUENCE weighthistory_seq;
DROP SEQUENCE user_seq;

CREATE SEQUENCE IF NOT EXISTS user_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS weighthistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS bodyfathistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS picturehistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;

CREATE TABLE IF NOT EXISTS user(
    user_id INTEGER PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    gender BOOLEAN DEFAULT TRUE,
    age INTEGER NOT NULL,
    height INTEGER NOT NULL,
    email VARCHAR(320),
    playlist VARCHAR,
    isDeleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS weighthistory(
    weighthistory_id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user
);

CREATE TABLE IF NOT EXISTS bodyfathistory(
    bodyfathistory_id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    bodyfat INTEGER NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user
);

CREATE TABLE IF NOT EXISTS picturehistory(
    picturehistory_id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    location VARCHAR NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user
);

