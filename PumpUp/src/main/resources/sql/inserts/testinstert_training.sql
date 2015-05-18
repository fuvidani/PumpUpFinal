-- Exercise
MERGE INTO categoryname VALUES(0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geraete');

MERGE INTO category VALUES(0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilitaet', 0), (4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Ruecken', 1), (9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1), (13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel',2), (16, 'Langhantel', 2), (17, 'Springschnur', 2), (18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);

MERGE INTO exercise
VALUES (1, 'liegestuetz', 'eine der besten uebungen ueberhaupt', 9.0, '', null, FALSE);
MERGE INTO gif VALUES (1, 1, 'menshealth');
MERGE INTO gif VALUES (2, 1, 'youtube');

-- Training
MERGE INTO TrainingsPlan VALUES (1, NULL, 'Testtraining', 'Testtrainingbeschreibung', FALSE);

MERGE INTO TrainingsSession VALUES (1, 1, 'Session 1', NULL, FALSE);
MERGE INTO TrainingsSession VALUES (2, 1, 'Session 2', NULL, FALSE);
MERGE INTO TrainingsSession VALUES (3, 1, 'Session 3', NULL, FALSE);

MERGE INTO ExerciseSet VALUES (1, 1, NULL, 10, 'repeat', 1, 1, FALSE);
MERGE INTO ExerciseSet VALUES (2, 1, NULL, 15, 'repeat', 2, 1, FALSE);
MERGE INTO ExerciseSet VALUES (3, 1, NULL, 5, 'repeat', 3, 1, FALSE);

MERGE INTO ExerciseSet VALUES (4, 1, NULL, 20, 'repeat', 1, 2, FALSE);
MERGE INTO ExerciseSet VALUES (5, 1, NULL, 9, 'repeat', 2, 2, FALSE);
MERGE INTO ExerciseSet VALUES (6, 1, NULL, 8, 'repeat', 3, 2, FALSE);

ALTER SEQUENCE seq_TP RESTART WITH 2;
ALTER SEQUENCE seq_TPType RESTART WITH 3;
ALTER SEQUENCE seq_TS RESTART WITH 4;
ALTER SEQUENCE seq_ES RESTART WITH 7;
ALTER SEQUENCE exercisesequence RESTART WITH 2;
ALTER SEQUENCE gifsequence RESTART WITH 3;
