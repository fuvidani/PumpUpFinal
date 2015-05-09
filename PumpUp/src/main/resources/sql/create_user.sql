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

