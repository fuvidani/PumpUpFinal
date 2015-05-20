-- Training
MERGE INTO TrainingsPlan VALUES (1, NULL, 'Testtraining1', 'Testtrainingbeschreibung', 10, FALSE);
MERGE INTO TrainingsPlan VALUES (2, NULL, 'Testtraining2', 'Testtrainingbeschreibung', 5, FALSE);

MERGE INTO TrainingsSession VALUES (1, 1, 'Session 1', NULL, FALSE);
MERGE INTO TrainingsSession VALUES (2, 1, 'Session 2', NULL, FALSE);

MERGE INTO ExerciseSet VALUES (1, 0, NULL, 10, 'time', 1, 1, FALSE);
MERGE INTO ExerciseSet VALUES (2, 0, NULL, 15, 'repeat', 2, 1, FALSE);
MERGE INTO ExerciseSet VALUES (3, 0, NULL, 5, 'repeat', 3, 1, FALSE);

MERGE INTO ExerciseSet VALUES (4, 0, NULL, 20, 'repeat', 1, 2, FALSE);
MERGE INTO ExerciseSet VALUES (5, 0, NULL, 9, 'repeat', 2, 2, FALSE);
MERGE INTO ExerciseSet VALUES (6, 0, NULL, 8, 'repeat', 3, 2, FALSE);
MERGE INTO ExerciseSet VALUES (7, 0, NULL, 10, 'repeat', 4, 2, FALSE);
MERGE INTO ExerciseSet VALUES (8, 0, NULL, 100, 'repeat', 5, 2, FALSE);

ALTER SEQUENCE seq_TP RESTART WITH 3;
ALTER SEQUENCE seq_TS RESTART WITH 3;
ALTER SEQUENCE seq_ES RESTART WITH 9;