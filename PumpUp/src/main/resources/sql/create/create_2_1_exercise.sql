DROP TABLE IF EXISTS categoryName;
DROP TABLE IF EXISTS gif;
DROP TABLE IF EXISTS exercise_category;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS category;
DROP SEQUENCE IF EXISTS exercise_seq;
DROP SEQUENCE IF EXISTS category_seq;
DROP SEQUENCE IF EXISTS categoryName_seq;
DROP SEQUENCE IF EXISTS gif_seq;


CREATE TABLE IF NOT EXISTS categoryName (
  id   INTEGER NOT NULL,
  name VARCHAR,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category (
  id   INTEGER NOT NULL,
  name VARCHAR NOT NULL,
  type INTEGER REFERENCES categoryName (id), --0 for categories, 1 muscle group, 2 equipment
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exercise (
  id         INTEGER NOT NULL,
  name       VARCHAR NOT NULL,
  descripion VARCHAR,
  calories   DOUBLE,
  videolink  VARCHAR,
  userid     INTEGER,
  isdeleted  BOOLEAN NOT NULL,
  FOREIGN KEY (userid) REFERENCES user (user_id),
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS exercise_category (
  exerciseid INT NOT NULL REFERENCES exercise (id),
  categoryid INT NOT NULL REFERENCES category (id),
  PRIMARY KEY (exerciseid, categoryid)
);

CREATE TABLE IF NOT EXISTS gif (
  id         INTEGER NOT NULL,
  exerciseid INTEGER NOT NULL,
  location   VARCHAR NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (exerciseid) REFERENCES exercise (id)
);


CREATE SEQUENCE IF NOT EXISTS exercise_seq START WITH 40 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS gif_seq START WITH 75 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 22 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS categoryName_seq START WITH 3 INCREMENT BY 1;






