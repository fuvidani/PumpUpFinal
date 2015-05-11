INSERT INTO categoryname VALUES(0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geraete');

INSERT INTO category VALUES(1, 'Ausdauer', 0), (2, 'Kraft', 0), (3, 'Balance', 0), (4, 'Flexibilitaet', 0), (5, 'Bizeps', 1), (6, 'Trizeps', 1), (7, 'Bauchmuskeln', 1), (8, 'Schultern', 1), (9, 'Ruecken', 1), (10, 'Brust', 1), (11, 'Oberschenkel', 1), (12, 'Unterschenkel', 1), (13, 'Wadenbein', 1), (14, 'Medizinball', 2), (15, 'Klimmzugstange', 2), (16, 'Kurzhantel',2), (17, 'Langhantel', 2), (18, 'Springschnur', 2), (19, 'Sandsack', 2), (20, 'Expander', 2), (21, 'Bauchmuskel Roller', 2), (22, 'Yogaball', 2);

INSERT INTO exercise VALUES (1, 'liegestütz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schließen Sie die Füße und stellen Sie die Zehenspit-zen auf. Die Hände sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr Körper sollte während der Bewegung eine gerade Linie bilden – von den Fersen bis zum Nacken. Ihr Blick ist zum Bo-den gerichtet. Achten Sie besonders darauf, dass Ihre Hüften nicht durchhängen oder Sie das Gesäß in die Luft strecken. Eine ungenaue Ausführung führt zu ei-nem schwachen Core. Spannen Sie die Muskeln der Körpermitte (Brust und Rücken) deshalb stets fest an! Senken Sie nun die Brust ab, bis Ihre Oberarme min-destens parallel zum Boden sind. Die Ellbogen zeigen dabei möglichst nach hinten. Bei einem perfekten Lie-gestütz berühren Sie den Bodenmit der Brust.', 0.03, 'https://www.youtube.com/watch?v=fdA6oWzW96g', null, false, false),
  (2, 'armrotation', 'Eine tolle Übung, um die Schultern vor oder nach den intensiveren Übungen aufzuwärmen oder abzukühlen. Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder große Kreise. Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.02, null, null, true, false),
  (3, 'daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre Füße sind hüftbreit geöffnet, die Fußspitzen aufgestellt. Strecken Sie die Arme seitlich aus und ballen Sie Ihre Hände zu Fäus-ten, die Daumen zeigen nach oben. Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie möglich nach oben. Dabei nähern sich Ihre Schulterblätter an. Halten Sie die Arme in der höchsten Position für drei Sekunden und ziehen Sie die Schultern noch weiter nach hinten oben.
Senken Sie dann die Arme wieder bis knapp über dem Boden ab. Absolvieren Sie so viele Wiederholungen wie möglich.
', 0.01, null, null, true, false );


INSERT INTO exercise_category VALUES
  --liegestuetz, kraft, bizeps/trizeps/bauchmuskeln
  (1, 2), (1, 5), (1, 6), (1, 7),
--armrotation, kraft, ausdauer, schultern
  (2,1), (2,2), (2, 8),
--daumen hoch, ausdauer, schultern
  (3, 1), (3, 8);

INSERT INTO gif VALUES
  --liegestuetz
  (1, 1, 'img_ex_pushup1.jpg'),
  (2, 1, 'img_ex_pushup2.jpg'),
  --armrotation
  (3, 2, 'img_ex_armrotation.jpg'),
  --daumen hoch
  (4, 3, 'img_ex_daumenhoch.jpg');