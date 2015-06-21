MERGE INTO user VALUES
(0, 'Lukas', TRUE, 22, 178, 'loc.kathrein@gmail.com', NULL, FALSE);

MERGE INTO bodyfathistory VALUES
  (0, 0, 20, '2015-02-23'),
  (1, 0, 15, '2015-03-24'),
  (2, 0, 12, '2015-04-25'),
  (3, 0, 10, '2015-05-26'),
  (4, 0, 13, '2015-05-29'),
  (5, 0, 15, '2015-06-11'),
  (6, 0, 18, '2015-06-15');

MERGE INTO weighthistory VALUES
  (0, 0, 88, '2015-02-23'),
  (1, 0, 73, '2015-03-24'),
  (2, 0, 73, '2015-04-25'),
  (3, 0, 70, '2015-05-26'),
  (4, 0, 69, '2015-05-29'),
  (5, 0, 75, '2015-06-11'),
  (6, 0, 78, '2015-06-15');


ALTER SEQUENCE user_seq RESTART WITH 1;
ALTER SEQUENCE bodyfathistory_seq RESTART WITH 7;
ALTER SEQUENCE weighthistory_seq RESTART WITH 7;