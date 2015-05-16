DROP TABLE IF EXISTS ExerciseSet;
DROP TABLE IF EXISTS TrainingsPlan;
DROP TABLE IF EXISTS TrainingsSession;
DROP SEQUENCE IF EXISTS seq_TP;
DROP SEQUENCE IF EXISTS seq_TS;
DROP SEQUENCE IF EXISTS seq_ES;

CREATE SEQUENCE IF NOT EXISTS seq_TP START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_TS START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_ES START WITH 1 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS TrainingsPlan (
  ID_Plan     INTEGER DEFAULT nextval('seq_TP'),
  UID         INTEGER     NULL, --included plans have no user
  name        VARCHAR(30) NOT NULL,
  description VARCHAR(30) NULL,
  isDeleted   BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Plan),
  FOREIGN KEY (UID) REFERENCES user
);

CREATE TABLE IF NOT EXISTS TrainingsSession (
  ID_Session INTEGER DEFAULT nextval('seq_TS'),
  ID_Plan    INTEGER,
  name       VARCHAR(30),
  UID        INTEGER NULL, --included sessions have no user
  isDeleted  BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Session),
  FOREIGN KEY (ID_Plan) REFERENCES TrainingsPlan,
  FOREIGN KEY (UID) REFERENCES user
);

CREATE TABLE IF NOT EXISTS ExerciseSet (
  ID_Set      INTEGER DEFAULT nextval('seq_ES'),
  ID_Exercise INTEGER,
  UID         INTEGER NULL, --included sets have no user
  repeat      INTEGER NOT NULL CHECK (repeat > 0),
  type        VARCHAR(10) CHECK (type IN ('time', 'repeat')),
  order_nr    INTEGER,
  ID_Session  INTEGER,
  isDeleted   BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Set, ID_Exercise),
  FOREIGN KEY (ID_Exercise) REFERENCES Exercise (ID),
  FOREIGN KEY (ID_Session) REFERENCES TrainingsSession,
  FOREIGN KEY (UID) REFERENCES user
);