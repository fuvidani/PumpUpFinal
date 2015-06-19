DROP TABLE IF EXISTS appointment;
DROP SEQUENCE IF EXISTS appointment_seq;

CREATE SEQUENCE IF NOT EXISTS appointment_seq INCREMENT BY 1 START WITH 0 NOCYCLE;

CREATE TABLE IF NOT EXISTS appointment (
  appointment_id INTEGER PRIMARY KEY,
  datum          DATE    NOT NULL,
  session_id     INTEGER NOT NULL REFERENCES TrainingsSession (ID_Session),
  user_id        INTEGER NOT NULL REFERENCES user (user_id),
  isTrained      BOOLEAN DEFAULT FALSE,
  isDeleted      BOOLEAN DEFAULT FALSE
)