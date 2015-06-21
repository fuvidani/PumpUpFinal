-- Training
MERGE INTO TrainingsPlan VALUES (1, NULL, 'Heimtraining für Fortgeschrittene', 'Dieser Trainingsplan ermöglicht mit Hilfe eines Kurz- und Langhantel-Sets sowie einer Bank ein Heimtraining für Fortgeschrittene.

Dabei wird die gesamte Körpermuskulatur in einem 2er-Split trainiert.', 5, FALSE),
  (2, NULL, 'Heimtraining ohne Geräte', 'Dieser Heimtrainingsplan richtet sich an Anfänger, die ihre Fitness durch Kraft- und Cardioübungen verbessern möchten.
Das Heimtraining gliedert sich in zwei Ganzkörper-Workouts, die ohne Geräte zu Hause durchgeführt werden können.
Das Heimtraining ohne Geräte ist durch durch die relativ niedrige Trainingsintensität eher für Anfänger geeignet.

Der Wiederholungsbereich der Kraftübungssätze liegt bei 20. Da es sich bei den Übungen jedoch um Kraftübungen ohne Gewichte handelt, sollten so viele korrekt ausführbare Wiederholungen wie möglich durchgeführt werden.',
   5, FALSE),
  (3, NULL, 'Full-Body Workout', '', 5, FALSE);

MERGE INTO TrainingsSession VALUES
  (1, 1, 'Session 1', NULL, FALSE),
  (2, 2, 'Session 1', NULL, FALSE),
  (3, 3, 'Session 1', NULL, FALSE),
  (4, 3, 'Session 2', NULL, FALSE);

MERGE INTO ExerciseSet VALUES
  (1, 8, NULL, 12, 'repeat', 1, 1, FALSE), (2, 8, NULL, 12, 'repeat', 2, 1, FALSE),
  (3, 8, NULL, 12, 'repeat', 3, 1, FALSE), (4, 3, NULL, 12, 'repeat', 4, 1, FALSE),
  (5, 3, NULL, 12, 'repeat', 5, 1, FALSE), (6, 3, NULL, 12, 'repeat', 6, 1, FALSE),
  (7, 12, NULL, 12, 'repeat', 7, 1, FALSE), (8, 12, NULL, 12, 'repeat', 8, 1, FALSE),
  (9, 12, NULL, 12, 'repeat', 9, 1, FALSE), (10, 5, NULL, 12, 'repeat', 10, 1, FALSE),
  (11, 5, NULL, 12, 'repeat', 11, 1, FALSE), (12, 5, NULL, 12, 'repeat', 12, 1, FALSE),
  (13, 19, NULL, 12, 'repeat', 13, 1, FALSE), (14, 19, NULL, 12, 'repeat', 14, 1, FALSE),
  (15, 19, NULL, 12, 'repeat', 15, 1, FALSE), (16, 9, NULL, 1, 'time', 16, 1, FALSE),
  (17, 9, NULL, 1, 'time', 17, 1, FALSE), (18, 9, NULL, 1, 'time', 18, 1, FALSE),
  (19, 0, NULL, 20, 'repeat', 1, 2, FALSE), (20, 0, NULL, 20, 'repeat', 2, 2, FALSE),
  (21, 0, NULL, 20, 'repeat', 3, 2, FALSE), (22, 0, NULL, 20, 'repeat', 4, 2, FALSE),
  (23, 0, NULL, 20, 'repeat', 5, 2, FALSE), (24, 8, NULL, 20, 'repeat', 6, 2, FALSE),
  (25, 8, NULL, 20, 'repeat', 7, 2, FALSE), (26, 8, NULL, 20, 'repeat', 8, 2, FALSE),
  (27, 3, NULL, 20, 'repeat', 9, 2, FALSE), (28, 3, NULL, 20, 'repeat', 10, 2, FALSE),
  (29, 3, NULL, 20, 'repeat', 11, 2, FALSE), (30, 19, NULL, 1, 'time', 12, 2, FALSE),
  (31, 19, NULL, 1, 'time', 13, 2, FALSE), (32, 19, NULL, 1, 'time', 14, 2, FALSE),
  (33, 19, NULL, 1, 'time', 15, 2, FALSE), (34, 4, NULL, 20, 'repeat', 16, 2, FALSE),
  (35, 4, NULL, 20, 'repeat', 17, 2, FALSE), (36, 4, NULL, 20, 'repeat', 18, 2, FALSE),
  (37, 0, NULL, 20, 'repeat', 1, 3, FALSE), (38, 0, NULL, 20, 'repeat', 2, 3, FALSE),
  (39, 0, NULL, 20, 'repeat', 3, 3, FALSE), (40, 12, NULL, 16, 'repeat', 4, 3, FALSE),
  (41, 12, NULL, 12, 'repeat', 5, 3, FALSE), (42, 12, NULL, 10, 'repeat', 6, 3, FALSE),
  (43, 4, NULL, 20, 'repeat', 7, 3, FALSE), (44, 4, NULL, 20, 'repeat', 8, 3, FALSE),
  (45, 4, NULL, 20, 'repeat', 9, 3, FALSE), (46, 3, NULL, 20, 'repeat', 10, 3, FALSE),
  (47, 3, NULL, 20, 'repeat', 11, 3, FALSE), (48, 3, NULL, 20, 'repeat', 12, 3, FALSE),
  (49, 3, NULL, 20, 'repeat', 1, 4, FALSE), (50, 3, NULL, 20, 'repeat', 2, 4, FALSE),
  (51, 3, NULL, 20, 'repeat', 3, 4, FALSE), (52, 12, NULL, 20, 'repeat', 4, 4, FALSE),
  (53, 12, NULL, 16, 'repeat', 5, 4, FALSE), (54, 12, NULL, 20, 'repeat', 6, 4, FALSE),
  (55, 19, NULL, 20, 'repeat', 7, 4, FALSE), (56, 19, NULL, 20, 'repeat', 8, 4, FALSE),
  (57, 19, NULL, 20, 'repeat', 9, 4, FALSE);

ALTER SEQUENCE seq_TP RESTART WITH 4;
ALTER SEQUENCE seq_TS RESTART WITH 5;
ALTER SEQUENCE seq_ES RESTART WITH 58;