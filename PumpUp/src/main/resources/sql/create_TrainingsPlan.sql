/*DROP TABLE ExerciseSet, PlanHasType, TrainingsPlan, TrainingsPlanType, TrainingsSession;
DROP SEQUENCE seq_TP;
DROP SEQUENCE seq_TPType;
DROP SEQUENCE seq_TS;
DROP SEQUENCE seq_ES;*/

CREATE SEQUENCE IF NOT EXISTS seq_TPType START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_TP START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_TS START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_ES START WITH 1 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS TrainingsPlan (
  ID_Plan     INTEGER DEFAULT nextval('seq_TP'),
  UID         INTEGER     NULL, --included plans have no user
  name        VARCHAR(30) NOT NULL,
  description VARCHAR(30) NULL,
  isDeleted   BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Plan)
);

CREATE TABLE IF NOT EXISTS TrainingsSession (
  ID_Session INTEGER DEFAULT nextval('seq_TS'),
  ID_Plan    INTEGER,
  UID        INTEGER NULL, --included plans have no user
  isDeleted  BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Session),
  FOREIGN KEY (ID_Plan) REFERENCES TrainingsPlan
);

CREATE TABLE IF NOT EXISTS ExerciseSet (
  ID_Set      INTEGER DEFAULT nextval('seq_ES'),
  ID_Exercise INTEGER,
  UID         INTEGER NULL, --included plans have no user
  repeat      INTEGER NOT NULL CHECK (repeat > 0),
  order_nr    INTEGER,
  ID_Session  INTEGER,
  isDeleted   BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Set, ID_Exercise),
  FOREIGN KEY (ID_Exercise) REFERENCES Exercise (ID),
  FOREIGN KEY (ID_Session) REFERENCES TrainingsSession
);

CREATE TABLE IF NOT EXISTS TrainingsPlanType (
  ID_Type     INTEGER DEFAULT nextval('seq_TPType'),
  UID         INTEGER     NULL, --included plans have no user
  name        VARCHAR(30) NOT NULL,
  description VARCHAR(30) NULL,
  isDeleted   BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (ID_Type)
);

CREATE TABLE IF NOT EXISTS PlanHasType (
  ID_Plan INTEGER,
  ID_Type INTEGER,

  PRIMARY KEY (ID_Plan, ID_Type),
  FOREIGN KEY (ID_Plan) REFERENCES TrainingsPlan,
  FOREIGN KEY (ID_Type) REFERENCES TrainingsPlanType
);