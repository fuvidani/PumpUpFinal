INSERT INTO categoryname VALUES (0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geraete');

INSERT INTO category
VALUES (0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilitaet', 0), (4, 'Bizeps', 1),
  (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Ruecken', 1), (9, 'Brust', 1),
  (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1), (13, 'Medizinball', 2),
  (14, 'Klimmzugstange', 2), (15, 'Kurzhantel', 2), (16, 'Langhantel', 2), (17, 'Springschnur', 2), (18, 'Sandsack', 2),
  (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);

INSERT INTO exercise VALUES
  (0, 'liegest�tz',
   'Legen Sie sich auf den Bauch, strecken Sie die Beine, schlie�en Sie die F��e und stellen Sie die Zehenspit-zen auf. Die H�nde sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr K�rper sollte w�hrend der Bewegung eine gerade Linie bilden � von den Fersen bis zum Nacken. Ihr Blick ist zum Bo-den gerichtet. Achten Sie besonders darauf, dass Ihre H�ften nicht durchh�ngen oder Sie das Ges�� in die Luft strecken. Eine ungenaue Ausf�hrung f�hrt zu ei-nem schwachen Core. Spannen Sie die Muskeln der K�rpermitte (Brust und R�cken) deshalb stets fest an! Senken Sie nun die Brust ab, bis Ihre Oberarme min-destens parallel zum Boden sind. Die Ellbogen zeigen dabei m�glichst nach hinten. Bei einem perfekten Lie-gest�tz ber�hren Sie den Bodenmit der Brust.',
   0.03, 'https://www.youtube.com/watch?v=fdA6oWzW96g', NULL, FALSE),
  (1, 'armrotation',
   'Eine tolle �bung, um die Schultern vor oder nach den intensiveren �bungen aufzuw�rmen oder abzuk�hlen. Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder gro�e Kreise. Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.',
   0.02, NULL, NULL, FALSE),
  (2, 'daumen hoch',
   'Nehmen Sie die Bauchlage ein. Ihre F��e sind h�ftbreit ge�ffnet, die Fu�spitzen aufgestellt. Strecken Sie die Arme seitlich aus und ballen Sie Ihre H�nde zu F�us-ten, die Daumen zeigen nach oben. Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie m�glich nach oben. Dabei n�hern sich Ihre Schulterbl�tter an. Halten Sie die Arme in der h�chsten Position f�r drei Sekunden und ziehen Sie die Schultern noch weiter nach hinten oben. Senken Sie dann die Arme wieder bis knapp �ber dem Boden ab. Absolvieren Sie so viele Wiederholungen wie m�glich.',
   0.01, NULL, NULL, FALSE),
  (3, 'Kniebeuge', 'Wichtig. Die Knie beim Hochgehen nicht ganz durch-strecken sondern etwas tief bleiben.', 0.02, NULL,
   NULL, FALSE),
  (4, 'Crunch it UP',
   'Legen Sie sich auf den R�cken. Stellen Sie die Beine angewinkelt h�ftbreit auf (je n�her die F��e am Ges�� sind, desto leichter wird die �bung) und haken Sie die F��e unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein. Verschr�nken Sie die Arme vor der Brust und halten Sie sie fest an den K�rper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an, l�sen Kopf und Schultern vom Boden und kommen schlie�lich langsam mit dem gesamten Oberk�rper nach oben, bis die Ellbogen die Oberschenkel nahe der H�ften ber�h-ren. Senken Sie sich anschlie�end wieder langsam ab, bis die Schulterbl�tter den Boden ber�hren. Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauch-muskeln angespannt. Diese �bung hat nicht viel Bewe-gungsspielraum und ist trotzdem sehr effektiv.',
   0.5, NULL, NULL, FALSE),
  (5, 'Strandschere',
   'Legen Sie sich mit gestreckten Beinen auf die linke Seite und st�tzen Sie den Kopf mit dem linken Arm ab. Die andere Hand st�tzen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie m�glich an und halten Sie es gestreckt f�r drei Sekunden in der',
   0.02, NULL, NULL, FALSE),
  (6, 'Auf der Stelle Laufen', '', 0.3, NULL, NULL, FALSE);

INSERT INTO exercise_category VALUES
  --liegestuetz, kraft, bizeps/trizeps/bauchmuskeln
  (0, 1), (0, 4), (0, 5), (0, 6),
  --armrotation, kraft, ausdauer, schultern
  (1, 0), (1, 1), (1, 7),
  --daumen hoch, ausdauer, schultern
  (2, 0), (2, 7),
  --kniebeugen, kraft, oberschenkel
  (3, 1), (3, 10),
  --crunch it up, kraft, bauchmuskeln, ruecken
  (4, 1), (4, 6), (4, 8),
  --strandschere, flexibilitaet, oberschenkel, unterschenkel
  (5, 3), (5, 10), (5, 11),
  --auf der stelle laufen, ausdauer
  (6, 0);

INSERT INTO gif VALUES
  --liegestuetz
  (1, 0, '/img_ex_pushup1.jpg'),
  (2, 0, '/img_ex_pushup2.jpg'),
  --armrotation
  (3, 1, '/img_ex_armrotation.jpg'),
  --daumen hoch
  (4, 2, '/img_ex_daumenhoch.jpg'),
  --kniebeugen
  (5, 3, '/img_ex_kniebeugen1.jpg'),
  (6, 3, '/img_ex_kniebeugen2.jpg'),
  --crunch it up
  (7, 4, '/img_ex_crunch1.jpg'),
  (8, 4, '/img_ex_crunch2.jpg'),
  --strandschere
  (9, 5, '/img_ex_strandschere1.jpg'),
  -- auf der stelle laufen
  (10, 6, '/img_ex_standlauf1.jpg');