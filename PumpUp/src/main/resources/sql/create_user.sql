DROP TABLE IF EXISTS picturehistory;
DROP TABLE IF EXISTS bodyfathistory;
DROP TABLE IF EXISTS weighthistory;
DROP TABLE IF EXISTS user;
DROP SEQUENCE IF EXISTS picturehistory_seq;
DROP SEQUENCE IF EXISTS bodyfathistory_seq;
DROP SEQUENCE IF EXISTS weighthistory_seq;
DROP SEQUENCE IF EXISTS user_seq;

CREATE SEQUENCE IF NOT EXISTS user_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS weighthistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS bodyfathistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT EXISTS picturehistory_seq INCREMENT BY 1 START WITH 0 NOCYCLE;

CREATE TABLE IF NOT EXISTS user (
  user_id   INTEGER PRIMARY KEY,
  username  VARCHAR(25) NOT NULL,
  gender    BOOLEAN DEFAULT TRUE,
  age       INTEGER     NOT NULL,
  height    INTEGER     NOT NULL,
  email     VARCHAR(320),
  playlist  VARCHAR,
  isDeleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS weighthistory (
  weighthistory_id INTEGER PRIMARY KEY,
  user_id          INTEGER NOT NULL,
  weight           INTEGER NOT NULL,
  date             DATE    NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user
);

CREATE TABLE IF NOT EXISTS bodyfathistory (
  bodyfathistory_id INTEGER PRIMARY KEY,
  user_id           INTEGER NOT NULL,
  bodyfat           INTEGER NOT NULL,
  date              DATE    NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user
);

CREATE TABLE IF NOT EXISTS picturehistory (
  picturehistory_id INTEGER PRIMARY KEY,
  user_id           INTEGER NOT NULL,
  location          VARCHAR NOT NULL,
  date              DATE    NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user
);

MERGE INTO user VALUES (1, 'lukas', true, 22, 178, 'loc.kathrein@gmail.com',75, false);
ALTER SEQUENCE user_seq RESTART WITH 2;
