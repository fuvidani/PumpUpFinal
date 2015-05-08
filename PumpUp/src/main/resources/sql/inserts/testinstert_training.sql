-- Exercise
INSERT INTO exercise
VALUES (nextval('exercisesequence'), 'liegestuetz', 'eine der besten uebungen ueberhaupt', 9.0, '', FALSE);
INSERT INTO gif VALUES (nextval('gifsequence'), currval('exercisesequence'), 'menshealth');
INSERT INTO gif VALUES (nextval('gifsequence'), currval('exercisesequence'), 'youtube');

-- Training
INSERT INTO TrainingsPlan (UID, name, description, isDeleted)
VALUES (NULL, 'Mein Ausdauertraining', 'eigene Pläne sind super!', FALSE);

INSERT INTO TrainingsSession (ID_Plan, UID, isDeleted) VALUES (currval('seq_TP'), NULL, FALSE);
INSERT INTO TrainingsSession (ID_Plan, UID, isDeleted) VALUES (currval('seq_TP'), NULL, FALSE);
INSERT INTO TrainingsSession (ID_Plan, UID, isDeleted) VALUES (currval('seq_TP'), NULL, FALSE);

INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 10, 1, currval('seq_TS'), FALSE);
INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 15, 2, currval('seq_TS'), FALSE);
INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 5, 3, currval('seq_TS'), FALSE);

INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 20, 1, currval('seq_TS') - 1, FALSE);
INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 9, 2, currval('seq_TS') - 1, FALSE);
INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_Session, isDeleted)
VALUES (currval('exercisesequence'), NULL, 8, 3, currval('seq_TS') - 1, FALSE);

INSERT INTO TrainingsPlanType (UID, name, description, isDeleted) VALUES (NULL, 'Ausdauer', 'Ausdauer ist gut!', FALSE );
INSERT INTO TrainingsPlanType (UID, name, description, isDeleted) VALUES (NULL, 'Oberkörper', 'Oberkörper ist cool!', FALSE );

INSERT INTO PlanHasType VALUES (currval('seq_TP'), currval('seq_TPType'));
INSERT INTO PlanHasType VALUES (currval('seq_TP'), currval('seq_TPType') - 1);