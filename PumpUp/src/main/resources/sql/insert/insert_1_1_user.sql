MERGE INTO user VALUES (0, 'DONT MESS WITH THE MASTA', TRUE, 22, 178, 'stopBeingCocky@thanks.com', NULL, FALSE);
MERGE INTO user VALUES (1, 'frank medrano', TRUE, 35, 160, 'thefrankmedrano@gmail.com', NULL, FALSE);
MERGE INTO bodyfathistory
VALUES (0, 0, 20, '2015-02-23'), (1, 0, 10, '2015-03-24'), (2, 0, 15, '2015-04-25'), (3, 0, 18, '2015-05-25');
MERGE INTO bodyfathistory VALUES (3, 1, 4, '2015-05-23');
MERGE INTO weighthistory
VALUES (0, 0, 88, '2015-02-23'), (1, 0, 73, '2015-03-24'), (2, 0, 80, '2015-04-25'), (3, 0, 83, '2015-05-25');
MERGE INTO weighthistory VALUES (3, 1, 65, '2015-05-23');
ALTER SEQUENCE user_seq RESTART WITH 2;
ALTER SEQUENCE bodyfathistory_seq RESTART WITH 4;
ALTER SEQUENCE weighthistory_seq RESTART WITH 4;