DROP TABLE categoryName;
DROP TABLE gif;
DROP TABLE exercise_category;
DROP TABLE exercise;
DROP TABLE category;
DROP SEQUENCE exercise_seq;
DROP SEQUENCE category_seq;
DROP SEQUENCE categoryName_seq;
DROP SEQUENCE gif_seq;


CREATE TABLE IF NOT EXISTS categoryName (
  id   INTEGER NOT NULL,
  name VARCHAR,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category (
  id   INTEGER NOT NULL,
  name VARCHAR NOT NULL,
  type INTEGER REFERENCES categoryName (id), --0 for categories, 1 muscle group, 2 equipment
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exercise (
  id         INTEGER NOT NULL,
  name       VARCHAR NOT NULL,
  descripion VARCHAR,
  calories   DOUBLE,
  videolink  VARCHAR,
  userid     INTEGER,
  isdeleted  BOOLEAN NOT NULL,
  FOREIGN KEY (userid) REFERENCES user (user_id),
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS exercise_category (
  exerciseid INT NOT NULL REFERENCES exercise (id),
  categoryid INT NOT NULL REFERENCES category (id),
  PRIMARY KEY (exerciseid, categoryid)
);

CREATE TABLE IF NOT EXISTS gif (
  id         INTEGER NOT NULL,
  exerciseid INTEGER NOT NULL,
  location   VARCHAR NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (exerciseid) REFERENCES exercise (id)
);


CREATE SEQUENCE IF NOT EXISTS exercise_seq START WITH 28 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS gif_seq START WITH 38 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 22 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS categoryName_seq START WITH 3 INCREMENT BY 1;


MERGE INTO categoryname VALUES (0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geräte');

MERGE INTO category VALUES (0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilität', 0),
  (4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Rücken', 1),
  (9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1),
  (13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel', 2), (16, 'Langhantel', 2),
  (17, 'Springschnur', 2),
  (18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);


MERGE INTO exercise KEY (id) VALUES
  (0, 'Liegestütz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schließen Sie die Füße und stellen Sie die Zehenspitzen auf.
  Die Hände sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr Körper sollte während der Bewegung eine gerade Linie
  bilden, von den Fersen bis zum Nacken.', 1.0, 'https://www.youtube.com/embed/fdA6oWzW96g?feature=player_detailpage',
   NULL, FALSE),
  (1, 'Armrotation', 'Eine tolle Übung, um die Schultern vor oder nach den intensiveren Übungen aufzuwärmen oder abzukühlen.
  Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt
  Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder große Kreise.
  Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.02, NULL, NULL, FALSE),
  (2, 'Daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre Füße sind hüftbreit geöffnet, die Fußspitzen aufgestellt.
  Strecken Sie die Arme seitlich aus und ballen Sie Ihre Hände zu Fäusten, die Daumen zeigen nach oben.
  Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie möglich nach oben.
  Dabei nähern sich Ihre Schulterblätter an.', 0.01, '', NULL, FALSE),
  (3, 'Kniebeuge', 'Wichtig. Die Knie beim Hochgehen nicht ganz durch-strecken sondern etwas tief bleiben.', 0.75, NULL, NULL, FALSE),
  (4, 'Crunch it UP', 'Legen Sie sich auf den Rücken. Stellen Sie die Beine angewinkelt hüftbreit auf
 und haken Sie die Füße unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein.
 Verschränken Sie die Arme vor der Brust und halten Sie sie fest an den Körper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an,
 lösen Kopf und Schultern vom Boden und kommen schließlich langsam mit dem gesamten Oberkörper nach oben, bis die Ellbogen die Oberschenkel
 nahe der Hüften berühren. Senken Sie sich anschließend wieder langsam ab, bis die Schulterblätter den Boden berühren.
 Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauch-muskeln angespannt.', 0.5, '', NULL, FALSE),
  (5, 'Strandschere', 'Legen Sie sich mit gestreckten Beinen auf die linke Seite und stützen Sie den Kopf mit dem linken Arm ab.
  Die andere Hand stützen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie möglich an und halten Sie es gestreckt für
  drei Sekunden in der', 0.2, NULL, NULL, FALSE),
  (6, 'Auf der Stelle Laufen', '', 0.3, '', NULL, FALSE),
  (7, 'Einbeinige Kniebeuge', 'Kraft, Koordination, Balance und Ausdauer. Und mit ein bisschen Kreativität können Sie die Übung endlos
  variieren. Stellen Sie sich auf den rechten Fuß und heben Sie Ihr linkes Bein gestreckt nach vorn an.
  Halten Sie sich mit der linken Hand an einer Stuhllehne oder einem anderen, etwa hüfthohen Gegenstand fest,
  um das Gleichgewicht zu bewahren. Der Rücken ist gerade, Ihr Blick ist nach vorn gerichtet.
  Beugen Sie nun das rechte Bein und gehen Sie so tief, bis der rechte Oberschenkel parallel zum Boden ist.
  Dabei neigen Sie denOberkörper nach vorn und schieben die Schultern über die Knie hinaus. Der linke Fuß bleibt währenddessen angehoben.
  Das rechte Knie bleibt über dem Mittelfuß. Achten Sie auf den geraden Rücken. Drücken Sie sich anschließend nur mit der Kraft Ihres
  rechten Beins wieder nach oben. Halten Sie sich nur fest, um das Gleichgewicht zu bewahren, und nicht,
  um sich mithilfe der abgestützten Hand hochzuschieben. Strecken Sie das rechte Knie am höchsten Punkt nicht ganz durch.
  Absolvieren Sie so viele Wiederholungen wie möglich, wechseln Sie dann die Seite.', 1.0, '', NULL, FALSE),
  (8, 'Ausfallschritt', 'Stellen Sie sich mit hüftbreit geöffneten Füßen aufrecht hin, die Fußspitzen zeigen nach vorne.
  Legen Sie die Hände an den Hinterkopf und richten Sie Ihren Blick in die Ferne. Jetzt machen Sie mit dem linken Fuß einen großen
  Schritt nach vorn und beugen dabei das Knie. Senken Sie Ihre Hüften so tief, bis das hintere Knie fast den Boden berührt.
  Beide Knie sollten am tiefsten Punkt im rechten Winkel gebeugt sein. Das vordere Knie befindet sich direkt über der Ferse,
  schieben Sie es nicht über die Zehen hinaus. Nun stoßen Sie sich mit dem linken Bein wieder ab und kommen zurück in die Ausgangsposition.
  Drücken Sie die Knie nicht ganz durch und achten Sie während des Bewegungsablaufs darauf, den Kopf in Verlängerung der Halswirbelsäule
  und den Rücken gerade zu halten. Wechseln Sie nun die Seite.', 0.8, '', NULL, FALSE),
  (9, 'PLANK', 'Setzen sie die Unterarme schulterbreit auf dem Boden auf und bilden Sie mit dem Rücken eine gerade Linie.
  Halten Sie diese Position.', 0.2, '', NULL, FALSE),
  (10, 'Seilspringen', 'Versuchen Sie eine geringe Sprunghöhe um Kraft zu schonen und versuchen Sie es auch einmal rückwärts', 0.25, '', NULL, FALSE),
  (11, 'BOXEN-Schläge',
   'Stellen Sie sich schulterbreit vor den Box/Sandsack und schlagen führen Sie gezielte Schläge aus', 0.2, '', NULL,
   FALSE),
  (12, 'BOXEN-Tritte', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und treten sie mit dem Schienbein gegen den Sandsack.
  Dehnen Sie sich vorher ausreichend', 0.5, '', NULL, FALSE),
  (13, 'Armstrecken mit Kurzhantel', '', 0.4, '', NULL, FALSE),
  (14, 'Bizeps mit Langhantel', '', 0.3, '', NULL, FALSE),
  (15, 'Kniebeugen mit Langhantel', '', 0.5, '', NULL, FALSE),
  (16, 'Bauchmuskeln Mit Roller', '', 0.2, '', NULL, FALSE),
  (17, 'Beinheben mit Yogaball', '', 0.2, '', NULL, FALSE),
  (18, 'Crunches mit Yogaball', '', 0.1, '', NULL, FALSE),
  (19, 'Superman', '', 0.1, '', NULL, FALSE),
  (20, 'Brust dehnen', '', 0.2, '', NULL, FALSE ),
  (21, 'Schultern dehnen','', 0.2, '', NULL , FALSE ),
  (22, 'Rücken (oben) dehnen', '', 0.2, '', NULL , FALSE ),
  (23, 'Rücken (Mitte) dehnen', '', 0.2, '', NULL , FALSE ),
  (24, 'Rücken (unten) dehnen', '', 0.2, '', NULL , FALSE ),
  (25, 'Quadrizeps dehnen', '', 0.2, '', NULL , FALSE ),
  (26, 'Gesäßmuskulatur dehnen', '', 0.2, '', NULL , FALSE ),
  (27, 'Kniesehne dehnen', '', 0.2, '', NULL , FALSE );


MERGE INTO exercise_category KEY (exerciseid, categoryid) VALUES
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
  (6, 0),
  --einbeinige kniebeugen, kraft, balance, oberschenkel
  (7, 1), (7, 2), (7, 10),
  --ausfallschritt balance, unterschenkel
  (8, 2), (8, 11),
  --plank, balance, bauchmuskeln, bizeps, schultern
  (9, 2), (9, 6), (9, 4), (9, 7),
  --seilspringen, balance, bauchmuskeln, oberschenkel, springschnur
  (10, 2), (10, 10), (10, 6), (10, 17),
  --boxen schlaege, kraft, ausdauer, sandsack
  (11, 0), (11, 1), (11, 18),
  --boxen tritte, kraft, sandsack
  (12, 1), (12, 18),
  --armstrecken mit kurzhantel, kraft, brust, schulter, bizeps, trizeps kurzhantel
  (13, 1), (13, 9), (13, 7), (13, 4), (13, 5), (13, 15),
  --bizeps mit langhantel, kraft, bizeps, langhantel
  (14, 1), (14, 4), (14, 16),
  --kniebeugen mit langhantel, kraft, oberschenkel, r�cken, langhantel
  (15, 1), (15, 10), (15, 8), (15, 16),
  --bauchmuskteln mit roller, kraft, bauchmuskeln, bauchmuskelroller
  (16, 1), (16, 6), (16, 20),
  --beinheben mit yogaball, balance, flexibilitaet, oberschenkel, ruecken, yogaball
  (17, 2), (17, 3), (17, 10), (17, 8), (17, 21),
  --crunches mit yogaball, balance, bauchmuskeln, yogaball
  (18, 2), (18, 6), (18, 21),
  --superman, kraft, ruecken, bauchmuskeln
  (19, 1), (19, 8), (19, 6),
  --Brust dehnen, Flexibilität, Brust
  (20, 3), (20, 9),
  --Schultern dehnen, Flexibilität, Schultern
  (21, 3), (21, 7),
  --Rücken (oben) dehnen, Flexibilität, Rücken
  (22, 3), (22, 8),
  --Rücken (Mitte) dehnen, Flexibilität, Rücken
  (23, 3), (23, 8),
  --Rücken (unten) dehnen, Flexibilität, Rücken
  (24, 3), (24, 8),
  --Quadrizeps dehnen, Flexibilität, Oberschenkel
  (25, 3), (25, 10),
  --Gesäßmuskulatur dehnen, Flexibilität, Oberschenkel
  (26, 3), (26, 10),
  --Kniesehne dehnen, Flexibilität, Oberschenkel
  (27, 3), (27, 10);


MERGE INTO gif KEY (id) VALUES
  --liegestuetz
  (1, 0, 'img_ex_pushup1.jpg'),
  (2, 0, 'img_ex_pushup2.jpg'),
  --armrotation
  (3, 1, 'img_ex_armrotation.jpg'),
  --daumen hoch
  (4, 2, 'img_ex_daumenhoch.jpg'),
  --kniebeugen
  (5, 3, 'img_ex_kniebeugen1.jpg'),
  (6, 3, 'img_ex_kniebeugen2.jpg'),
  --crunch it up
  (7, 4, 'img_ex_crunch1.jpg'),
  (8, 4, 'img_ex_crunch2.jpg'),
  --strandschere
  (9, 5, 'img_ex_strandschere1.jpg'),
  -- auf der stelle laufen
  (10, 6, 'img_ex_standlauf1.jpg'),
  --einbeinige kniebeugen
  (11, 7, 'img_ex_einb_kniebeugen1.jpg'),
  (12, 7, 'img_ex_einb_kniebeugen2.jpg'),
  --ausfallschritt
  (13, 8, 'img_ex_ausfallschritt.jpg'),
  --plank
  (14, 9, 'img_ex_plank.jpg'),
  --seilspringen
  (15, 10, 'img_ex_seilspringen.jpg'),
  --boxen schlaege
  (16, 11, 'img_ex_boxenschlag.jpg'),
  --boxen tritte
  (17, 12, 'img_ex_boxentritt.jpg'),
  --armstrecken mit kurzhantel
  (18, 13, 'img_ex_armstrecken_kurzhantel1.jpg'),
  (19, 13, 'img_ex_armstrecken_kurzhantel2.jpg'),
  --bizeps mit langhantel
  (20, 14, 'img_ex_bizeps_langhantel1.JPG'),
  (21, 14, 'img_ex_bizeps_langhantel2.JPG'),
  --kniebeugen mit langhantel
  (22, 15, 'img_ex_kniebeugen_langhantel.JPG'),
  --bauchmuskeln mit roller
  (23, 16, 'img_ex_bauchmuskel_roller1.JPG'),
  (24, 16, 'img_ex_bauchmuskel_roller2.JPG'),
  --beinheben mit yogaball
  (25, 17, 'img_ex_beinheben_yogaball.jpg'),
  --crunches mit yogaball
  (26, 18, 'img_ex_crunch_yogaball.jpg'),
  --superman
  (27, 19, 'img_ex_superman1.jpg'),
  (28, 19, 'img_ex_superman2.jpg'),
  --Brust dehnen, Flexibilität, Brust
  (29, 20, 'img_ex_chest_stretch.JPG'),
  --Schultern dehnen, Flexibilität, Schultern
  (30, 21, 'img_ex_shoulder_stretch1.JPG'),
  (31, 21, 'img_ex_shoulder_stretch2.JPG'),
  (32, 21, 'img_ex_shoulder_stretch3.JPG'),
  --Rücken (oben) dehnen, Flexibilität, Rücken
  (33, 22, 'img_ex_upper_back_stretch1.JPG'),
  --Rücken (Mitte) dehnen, Flexibilität, Rücken
  (34, 23, 'img_ex_middle_back_stretch1.JPG'),
  --Rücken (unten) dehnen, Flexibilität, Rücken
  (35, 24, 'img_ex_lower_back_stretch1.JPG'),
  --Quadrizeps dehnen, Flexibilität, Oberschenkel
  (36, 25, 'img_ex_quadriceps_stretch1.JPG'),
  --Gesäßmuskulatur dehnen, Flexibilität, Oberschenkel
  (36, 26, 'img_ex_glutes_stretch1.JPG'),
  --Kniesehne dehnen, Flexibilität, Oberschenkel
  (37, 27, 'img_ex_hamstring_stretch1.JPG');




