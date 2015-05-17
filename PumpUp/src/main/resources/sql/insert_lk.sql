INSERT INTO categoryname VALUES(0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geraete');

INSERT INTO category VALUES(0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilitaet', 0), (4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Ruecken', 1), (9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1), (13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel',2), (16, 'Langhantel', 2), (17, 'Springschnur', 2), (18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);

INSERT INTO exercise VALUES
(0, 'liegestütz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schließen Sie die Füße und stellen Sie die Zehenspit-zen auf. Die Hände sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr Körper sollte während der Bewegung eine gerade Linie bilden – von den Fersen bis zum Nacken. Ihr Blick ist zum Bo-den gerichtet. Achten Sie besonders darauf, dass Ihre Hüften nicht durchhängen oder Sie das Gesäß in die Luft strecken. Eine ungenaue Ausführung führt zu ei-nem schwachen Core. Spannen Sie die Muskeln der Körpermitte (Brust und Rücken) deshalb stets fest an! Senken Sie nun die Brust ab, bis Ihre Oberarme min-destens parallel zum Boden sind. Die Ellbogen zeigen dabei möglichst nach hinten. Bei einem perfekten Lie-gestütz berühren Sie den Bodenmit der Brust.', 0.03, 'https://www.youtube.com/watch?v=fdA6oWzW96g', null, false),
  (1, 'armrotation', 'Eine tolle Übung, um die Schultern vor oder nach den intensiveren Übungen aufzuwärmen oder abzukühlen. Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder große Kreise. Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.02, null, null, false),
  (2, 'daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre Füße sind hüftbreit geöffnet, die Fußspitzen aufgestellt. Strecken Sie die Arme seitlich aus und ballen Sie Ihre Hände zu Fäus-ten, die Daumen zeigen nach oben. Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie möglich nach oben. Dabei nähern sich Ihre Schulterblätter an. Halten Sie die Arme in der höchsten Position für drei Sekunden und ziehen Sie die Schultern noch weiter nach hinten oben. Senken Sie dann die Arme wieder bis knapp über dem Boden ab. Absolvieren Sie so viele Wiederholungen wie möglich.', 0.01, null, null, false ),
  (3, 'Kniebeuge','Wichtig. Die Knie beim Hochgehen nicht ganz durch-strecken sondern etwas tief bleiben.', 0.02, null, null, false),
  (4, 'Crunch it UP', 'Legen Sie sich auf den Rücken. Stellen Sie die Beine angewinkelt hüftbreit auf (je näher die Füße am Gesäß sind, desto leichter wird die Übung) und haken Sie die Füße unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein. Verschränken Sie die Arme vor der Brust und halten Sie sie fest an den Körper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an, lösen Kopf und Schultern vom Boden und kommen schließlich langsam mit dem gesamten Oberkörper nach oben, bis die Ellbogen die Oberschenkel nahe der Hüften berüh-ren. Senken Sie sich anschließend wieder langsam ab, bis die Schulterblätter den Boden berühren. Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauch-muskeln angespannt. Diese Übung hat nicht viel Bewe-gungsspielraum und ist trotzdem sehr effektiv.', 0.5, null, null, false),
(5, 'Strandschere', 'Legen Sie sich mit gestreckten Beinen auf die linke Seite und stützen Sie den Kopf mit dem linken Arm ab. Die andere Hand stützen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie möglich an und halten Sie es gestreckt für drei Sekunden in der', 0.02, null, null, false),
(6, 'Auf der Stelle Laufen', '', 0.3, null, null, false);

INSERT INTO exercise_category VALUES
  --liegestuetz, kraft, bizeps/trizeps/bauchmuskeln
  (0, 1), (0, 4), (0, 5), (0, 6),
--armrotation, kraft, ausdauer, schultern
  (1,0), (1,1), (1, 7),
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