drop table categoryName;
drop table gif;
drop table exercise_category;
drop table exercise;
drop table category;
drop SEQUENCE exercise_seq;
drop SEQUENCE category_seq;
drop SEQUENCE  categoryName_seq;
drop SEQUENCE  gif_seq;


CREATE TABLE IF NOT EXISTS categoryName(
  id INTEGER NOT NULL,
  name VARCHAR,
  PRIMARY KEY (id)
);

CREATE TABLE if NOT EXISTS category(
  id INTEGER not null,
  name VARCHAR not null,
  type INTEGER REFERENCES categoryName(id), --0 for categories, 1 muscle group, 2 equipment
  PRIMARY KEY (id)
);

create table if not exists exercise(
  id INTEGER not null,
  name VARCHAR not null,
  descripion VARCHAR,
  calories DOUBLE,
  videolink VARCHAR,
  userid INTEGER,
  isdeleted BOOLEAN not null,
  FOREIGN KEY (userid) REFERENCES user(user_id),
  PRIMARY KEY(id)
);


create table if not exists exercise_category(
  exerciseid int not NULL REFERENCES exercise(id),
  categoryid int not null REFERENCES category(id),
  PRIMARY KEY (exerciseid, categoryid)
);

create table if not exists gif(
  id INTEGER not null,
  exerciseid INTEGER not null,
  location VARCHAR not null,
  PRIMARY KEY(id),
  FOREIGN KEY(exerciseid) references exercise(id)
);



CREATE SEQUENCE if not exists exercise_seq START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE if not exists gif_seq START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE IF NOT exists category_seq START WITH 0 INCREMENT BY 1 ;
CREATE SEQUENCE IF NOT exists categoryName_seq START WITH 0 INCREMENT BY 1 ;

-- INSERT INTO categoryname VALUES(0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geraete');
--
-- INSERT INTO category VALUES(0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilitaet', 0), (4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Ruecken', 1), (9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1), (13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel',2), (16, 'Langhantel', 2), (17, 'Springschnur', 2), (18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);
--
--
--
-- INSERT INTO exercise VALUES
--   (0, 'Liegest�tz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schlie�en Sie die F��e und stellen Sie die Zehenspit-zen auf. Die H�nde sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr K�rper sollte w�hrend der Bewegung eine gerade Linie bilden � von den Fersen bis zum Nacken. Ihr Blick ist zum Bo-den gerichtet. Achten Sie besonders darauf, dass Ihre H�ften nicht durchh�ngen oder Sie das Ges�� in die Luft strecken. Eine ungenaue Ausf�hrung f�hrt zu ei-nem schwachen Core. Spannen Sie die Muskeln der K�rpermitte (Brust und R�cken) deshalb stets fest an! Senken Sie nun die Brust ab, bis Ihre Oberarme min-destens parallel zum Boden sind. Die Ellbogen zeigen dabei m�glichst nach hinten. Bei einem perfekten Lie-gest�tz ber�hren Sie den Bodenmit der Brust.', 0.03, 'https://www.youtube.com/watch?v=fdA6oWzW96g', null, false),
--   (1, 'Armrotation', 'Eine tolle �bung, um die Schultern vor oder nach den intensiveren �bungen aufzuw�rmen oder abzuk�hlen. Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem training beginnt Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder gro�e Kreise. Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.02, null, null, false),
--   (2, 'Daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre F��e sind h�ftbreit ge�ffnet, die Fu�spitzen aufgestellt. Strecken Sie die Arme seitlich aus und ballen Sie Ihre H�nde zu F�us-ten, die Daumen zeigen nach oben. Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie m�glich nach oben. Dabei n�hern sich Ihre Schulterbl�tter an. Halten Sie die Arme in der h�chsten Position f�r drei Sekunden und ziehen Sie die Schultern noch weiter nach hinten oben. Senken Sie dann die Arme wieder bis knapp �ber dem Boden ab. Absolvieren Sie so viele Wiederholungen wie m�glich.', 0.01, null, null, false ),
--   (3, 'Kniebeuge','Wichtig. Die Knie beim Hochgehen nicht ganz durch-strecken sondern etwas tief bleiben.', 0.02, null, null, false),
--   (4, 'Crunch it UP', 'Legen Sie sich auf den R�cken. Stellen Sie die Beine angewinkelt h�ftbreit auf (je n�her die F��e am Ges�� sind, desto leichter wird die �bung) und haken Sie die F��e unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein. Verschr�nken Sie die Arme vor der Brust und halten Sie sie fest an den K�rper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an, l�sen Kopf und Schultern vom Boden und kommen schlie�lich langsam mit dem gesamten Oberk�rper nach oben, bis die Ellbogen die Oberschenkel nahe der H�ften ber�h-ren. Senken Sie sich anschlie�end wieder langsam ab, bis die Schulterbl�tter den Boden ber�hren. Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauch-muskeln angespannt. Diese �bung hat nicht viel Bewe-gungsspielraum und ist trotzdem sehr effektiv.', 0.5, 'https://www.youtube.com/watch?v=O_kqAgdD17E', null, false),
--   (5, 'Strandschere', 'Legen Sie sich mit gestreckten Beinen auf die linke Seite und st�tzen Sie den Kopf mit dem linken Arm ab. Die andere Hand st�tzen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie m�glich an und halten Sie es gestreckt f�r drei Sekunden in der', 0.02, null, null, false),
--   (6, 'Auf der Stelle Laufen', '', 0.3, null, null, false),
--   (7, 'Einbeinige Kniebeuge', 'Kraft, Koordination, Balance und Ausdauer. Und mit ein bisschen Kreativit�t k�nnen Sie die �bung endlos vari-ieren. Stellen Sie sich auf den rechten Fu� und heben Sie Ihr linkes Bein gestreckt nach vorn an. Halten Sie sich mit der linken Hand an einer Stuhllehne oder einem anderen, etwa h�fthohen Gegenstand fest, um das Gleichgewicht zu bewahren. Der R�cken ist gerade, Ihr Blick ist nach vorn gerichtet. Beugen Sie nun das rechte Bein und gehen Sie so tief, bis der rechte Ober-schenkel parallel zum Boden ist. Dabei neigen Sie denOberk�rper nach vorn und schieben die Schultern �ber die Knie hinaus. Der linke Fu� bleibt w�hrenddessen angehoben. Das rechte Knie bleibt �ber dem Mittelfu�. Achten Sie auf den geraden R�cken. Dr�cken Sie sich anschlie�end nur mit der Kraft Ihres rechten Beins wie-der nach oben. Halten Sie sich nur fest, um das Gleichgewicht zu bewahren, und nicht, um sich mithilfe der abgest�tzten Hand hochzuschieben. Strecken Sie das rechte Knie am h�chsten Punkt nicht ganz durch. Absolvieren Sie so viele Wiederholungen wie m�glich, wechseln Sie dann die Seite.', 0.5, '', null, false),
--   (8, 'Ausfallschritt', 'Stellen Sie sich mit h�ftbreit ge�ffneten F��en aufrecht hin, die Fu�spitzen zeigen nach vorn. Legen Sie die H�nde an den Hinterkopf und richten Sie Ihren Blick in die Ferne. Jetzt machen Sie mit dem linken Fu� einen gro�en Schritt nach vorn und beugen dabei das Knie. Senken Sie Ihre H�ften so tief, bis das hintere Knie fast den Boden ber�hrt. Beide Knie sollten am tiefsten Punkt im rechten Winkel gebeugt sein. Das vordere Knie befindet sich direkt �ber der Ferse, schieben Sie es nicht �ber die Zehen hinaus. Nun sto�en Sie sich mit dem linken Bein wieder ab und kommen zur�ck in die Ausgangsposition. Dr�cken Sie die Knie nicht ganz durch und achten Sie w�hrend des Bewegungsablaufs darauf, den Kopf in Verl�ngerung der Halswirbels�ule und den R�cken gerade zu halten. Wechseln Sie nun die Seite.', 0.3, '', null, false),
--   (9, 'PLANK', 'Setzen sie die Unterarme schulterbreit auf dem Boden auf und bilden Sie mit dem R�cken eine gerade Linie. Halten Sie diese Position.', 0.2, '', null, false),
--   (10, 'Seilspringen', 'Versuchen Sie eine geringe Sprungh�he um Kraft zu schonen und versuchen Sie es auch einmal r�ckw�rts', 0.1, '', null, false),
--   (11, 'BOXEN-Schl�ge', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und schlagen f�hren Sie gezielte Schl�ge aus',0.2, '', null, false),
--   (12, 'BOXEN-Tritte', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und treten sie mit dem Schienbein gegen den Sandsack. Dehnen Sie sich vorher ausreichend', 0.5,' ', null, false),
--   (13, 'Armstrecken mit Kurzhan-tel', '', 0.4, ' ', null, false),
--   (14, 'Bizeps mit Langhantel', '', 0.3,  ' ',null, false),
--   (15, 'Kniebeugen mit Langhantel', '', 0.5,  ' ',null, false),
--   (16, 'Bauchmuskeln Mit Roller', '', 0.2, ' ', null, false),
--   (17, 'Beinheben mit Yogaball', '', 0.1, ' ', null, false),
--   (18, 'Crunches mit Yogaball', '', 0.1, ' ', null, false),
--   (19, 'Superman', '', 0.1,  ' ',null, false);
--
-- INSERT INTO exercise_category VALUES
--   --liegestuetz, kraft, bizeps/trizeps/bauchmuskeln
--   (0, 1), (0, 4), (0, 5), (0, 6),
--   --armrotation, kraft, ausdauer, schultern
--   (1,0), (1,1), (1, 7),
--   --daumen hoch, ausdauer, schultern
--   (2, 0), (2, 7),
--   --kniebeugen, kraft, oberschenkel
--   (3, 1), (3, 10),
--   --crunch it up, kraft, bauchmuskeln, ruecken
--   (4, 1), (4, 6), (4, 8),
--   --strandschere, flexibilitaet, oberschenkel, unterschenkel
--   (5, 3), (5, 10), (5, 11),
--   --auf der stelle laufen, ausdauer
--   (6, 0),
--   --einbeinige kniebeugen, kraft, balance, oberschenkel
--   (7, 1), (7, 2), (7, 10),
-- --ausfallschritt balance, unterschenkel
-- (8, 2), (8,11),
--   --plank, balance, bauchmuskeln, bizeps, schultern
--   (9, 2), (9, 6), (9, 4), (9, 7),
-- --seilspringen, balance, bauchmuskeln, oberschenkel, springschnur
-- (10, 2), (10, 10), (10, 6), (10, 17),
-- --boxen schlaege, kraft, ausdauer, sandsack
--   (11, 0),(11, 1), (11, 18),
--   --boxen tritte, kraft, sandsack
--   (12, 1), (12, 18),
--   --armstrecken mit kurzhantel, kraft, brust, schulter, bizeps, trizeps kurzhantel
--   (13, 1), (13, 9), (13, 7), (13, 4), (13, 5), (13, 15),
--   --bizeps mit langhantel, kraft, bizeps, langhantel
--   (14, 1), (14, 4), (14, 16),
--   --kniebeugen mit langhantel, kraft, oberschenkel, r�cken, langhantel
--   (15, 1), (15, 10), (15, 8), (15, 16),
--   --bauchmuskteln mit roller, kraft, bauchmuskeln, bauchmuskelroller
--   (16, 1),(16, 6), (16, 20),
--   --beinheben mit yogaball, balance, flexibilitaet, oberschenkel, ruecken, yogaball
--   (17, 2), (17, 3), (17, 10), (17, 8), (17, 21),
--   --crunches mit yogaball, balance, bauchmuskeln, yogaball
--   (18, 2),(18, 6), (18, 21),
--   --superman, kraft, ruecken, bauchmuskeln
--   (19, 1), (19, 8), (19, 6);
--
--
-- INSERT INTO gif VALUES
--   --liegestuetz
--   (1, 0, 'img_ex_pushup1.jpg'),
--   (2, 0, 'img_ex_pushup2.jpg'),
--   --armrotation
--   (3, 1, 'img_ex_armrotation.jpg'),
--   --daumen hoch
--   (4, 2, 'img_ex_daumenhoch.jpg'),
--   --kniebeugen
--   (5, 3, 'img_ex_kniebeugen1.jpg'),
--   (6, 3, 'img_ex_kniebeugen2.jpg'),
--   --crunch it up
--   (7, 4, 'img_ex_crunch1.jpg'),
--   (8, 4, 'img_ex_crunch2.jpg'),
--   --strandschere
--   (9, 5, 'img_ex_strandschere1.jpg'),
--   -- auf der stelle laufen
--   (10, 6, 'img_ex_standlauf1.jpg'),
-- --einbeinige kniebeugen
-- (11, 7, 'img_ex_einb_kniebeugen1.jpg'),
-- (12, 7, 'img_ex_einb_kniebeugen2.jpg'),
--   --ausfallschritt
--   (13, 8, 'img_ex_ausfallschritt.jpg'),
--   --plank
--   (14, 9, 'img_ex_plank.jpg'),
-- --seilspringen
-- (15, 10, 'img_ex_seilspringen.jpg'),
-- --boxen schlaege
-- (16, 11, 'img_ex_boxenschlag.jpg'),
-- --boxen tritte
-- (17, 12, 'img_ex_boxentritt.jpg'),
--   --armstrecken mit kurzhantel
--   (18, 13,'img_ex_armstrecken_kurzhantel1.jpg'),
--   (19, 13,'img_ex_armstrecken_kurzhantel2.jpg'),
--   --bizeps mit langhantel
--   (20, 14, 'img_ex_bizeps_langhantel1.JPG'),
--   (21, 14, 'img_ex_bizeps_langhantel2.JPG'),
--   --kniebeugen mit langhantel
--   (22, 15, 'img_ex_kniebeugen_langhantel.JPG'),
--   --bauchmuskeln mit roller
--   (23, 16, 'img_ex_bauchmuskel_roller1.JPG'),
--   (24, 16, 'img_ex_bauchmuskel_roller2.JPG'),
--   --beinheben mit yogaball
--   (25, 17, 'img_ex_beinheben_yogaball.jpg'),
--   --crunches mit yogaball
--   (26, 18, 'img_ex_crunch_yogaball.jpg'),
--   --superman
--   (27, 19, 'img_ex_superman1.jpg'),
--   (28, 19, 'img_ex_superman2.jpg');
merge INTO categoryname VALUES(0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geräte');

merge INTO category VALUES(0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilität', 0),
(4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Rücken', 1),
(9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1),
(13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel',2), (16, 'Langhantel', 2), (17, 'Springschnur', 2),
(18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);



MERGE INTO exercise KEY(id) VALUES
  (0, 'Liegestötz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schließen Sie die Füße und stellen Sie die Zehenspitzen auf.
  Die Hände sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr Körper sollte während der Bewegung eine gerade Linie
  bilden, von den Fersen bis zum Nacken.', 0.03, 'https://www.youtube.com/embed/fdA6oWzW96g?feature=player_detailpage', null, false),
  (1, 'Armrotation', 'Eine tolle Übung, um die Schultern vor oder nach den intensiveren Übungen aufzuwärmen oder abzukühlen.
  Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt
  Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder große Kreise.
  Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.02, null, null, false),
  (2, 'Daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre Füße sind hüftbreit geöffnet, die Fußspitzen aufgestellt.
  Strecken Sie die Arme seitlich aus und ballen Sie Ihre Hände zu Fäusten, die Daumen zeigen nach oben.
  Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie möglich nach oben.
  Dabei nähern sich Ihre Schulterblätter an.', 0.01, null, null, false ),
  (3, 'Kniebeuge','Wichtig. Die Knie beim Hochgehen nicht ganz durch-strecken sondern etwas tief bleiben.', 0.02, null, null, false),
  (4, 'Crunch it UP', 'Legen Sie sich auf den Rücken. Stellen Sie die Beine angewinkelt hüftbreit auf
 und haken Sie die Füße unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein.
 Verschränken Sie die Arme vor der Brust und halten Sie sie fest an den Körper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an,
 lösen Kopf und Schultern vom Boden und kommen schließlich langsam mit dem gesamten Oberkörper nach oben, bis die Ellbogen die Oberschenkel
 nahe der Hüften berühren. Senken Sie sich anschließend wieder langsam ab, bis die Schulterblätter den Boden berühren.
 Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauch-muskeln angespannt.', 0.5, '"https://www.youtube.com/embed/O_kqAgdD17E?feature=player_detailpage', null, false),
  (5, 'Strandschere', 'Legen Sie sich mit gestreckten Beinen auf die linke Seite und stützen Sie den Kopf mit dem linken Arm ab.
  Die andere Hand stützen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie möglich an und halten Sie es gestreckt für
  drei Sekunden in der', 0.02, null, null, false),
  (6, 'Auf der Stelle Laufen', '', 0.3, null, null, false),
  (7, 'Einbeinige Kniebeuge', 'Kraft, Koordination, Balance und Ausdauer. Und mit ein bisschen Kreativität können Sie die Übung endlos
  variieren. Stellen Sie sich auf den rechten Fuß und heben Sie Ihr linkes Bein gestreckt nach vorn an.
  Halten Sie sich mit der linken Hand an einer Stuhllehne oder einem anderen, etwa hüfthohen Gegenstand fest,
  um das Gleichgewicht zu bewahren. Der Rücken ist gerade, Ihr Blick ist nach vorn gerichtet.
  Beugen Sie nun das rechte Bein und gehen Sie so tief, bis der rechte Oberschenkel parallel zum Boden ist.
  Dabei neigen Sie denOberkörper nach vorn und schieben die Schultern über die Knie hinaus. Der linke Fuß bleibt währenddessen angehoben.
  Das rechte Knie bleibt über dem Mittelfuß. Achten Sie auf den geraden Rücken. Drücken Sie sich anschließend nur mit der Kraft Ihres
  rechten Beins wieder nach oben. Halten Sie sich nur fest, um das Gleichgewicht zu bewahren, und nicht,
  um sich mithilfe der abgestützten Hand hochzuschieben. Strecken Sie das rechte Knie am höchsten Punkt nicht ganz durch.
  Absolvieren Sie so viele Wiederholungen wie möglich, wechseln Sie dann die Seite.', 0.5, '', null, false),
  (8, 'Ausfallschritt', 'Stellen Sie sich mit hüftbreit geöffneten Füßen aufrecht hin, die Fußspitzen zeigen nach vorn.
  Legen Sie die Hände an den Hinterkopf und richten Sie Ihren Blick in die Ferne. Jetzt machen Sie mit dem linken Fuß einen großen
  Schritt nach vorn und beugen dabei das Knie. Senken Sie Ihre Hüften so tief, bis das hintere Knie fast den Boden berührt.
  Beide Knie sollten am tiefsten Punkt im rechten Winkel gebeugt sein. Das vordere Knie befindet sich direkt über der Ferse,
  schieben Sie es nicht über die Zehen hinaus. Nun stoßen Sie sich mit dem linken Bein wieder ab und kommen zurück in die Ausgangsposition.
  Drücken Sie die Knie nicht ganz durch und achten Sie während des Bewegungsablaufs darauf, den Kopf in Verlängerung der Halswirbelsäule
  und den Rücken gerade zu halten. Wechseln Sie nun die Seite.', 0.3, '', null, false),
  (9, 'PLANK', 'Setzen sie die Unterarme schulterbreit auf dem Boden auf und bilden Sie mit dem Rücken eine gerade Linie.
  Halten Sie diese Position.', 0.2, '', null, false),
  (10, 'Seilspringen', 'Versuchen Sie eine geringe Sprunghöhe um Kraft zu schonen und versuchen Sie es auch einmal rückwärts', 0.1, '', null, false),
  (11, 'BOXEN-Schläge', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und schlagen führen Sie gezielte Schläge aus',0.2, '', null, false),
  (12, 'BOXEN-Tritte', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und treten sie mit dem Schienbein gegen den Sandsack.
  Dehnen Sie sich vorher ausreichend', 0.5,' ', null, false),
  (13, 'Armstrecken mit Kurzhantel', '', 0.4, ' ', null, false),
  (14, 'Bizeps mit Langhantel', '', 0.3,  ' ',null, false),
  (15, 'Kniebeugen mit Langhantel', '', 0.5,  ' ',null, false),
  (16, 'Bauchmuskeln Mit Roller', '', 0.2, ' ', null, false),
  (17, 'Beinheben mit Yogaball', '', 0.1, ' ', null, false),
  (18, 'Crunches mit Yogaball', '', 0.1, ' ', null, false),
  (19, 'Superman', '', 0.1,  ' ',null, false);

merge INTO exercise_category KEY(exerciseid, categoryid) VALUES
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
  (6, 0),
  --einbeinige kniebeugen, kraft, balance, oberschenkel
  (7, 1), (7, 2), (7, 10),
--ausfallschritt balance, unterschenkel
(8, 2), (8,11),
  --plank, balance, bauchmuskeln, bizeps, schultern
  (9, 2), (9, 6), (9, 4), (9, 7),
--seilspringen, balance, bauchmuskeln, oberschenkel, springschnur
(10, 2), (10, 10), (10, 6), (10, 17),
--boxen schlaege, kraft, ausdauer, sandsack
  (11, 0),(11, 1), (11, 18),
  --boxen tritte, kraft, sandsack
  (12, 1), (12, 18),
  --armstrecken mit kurzhantel, kraft, brust, schulter, bizeps, trizeps kurzhantel
  (13, 1), (13, 9), (13, 7), (13, 4), (13, 5), (13, 15),
  --bizeps mit langhantel, kraft, bizeps, langhantel
  (14, 1), (14, 4), (14, 16),
  --kniebeugen mit langhantel, kraft, oberschenkel, r�cken, langhantel
  (15, 1), (15, 10), (15, 8), (15, 16),
  --bauchmuskteln mit roller, kraft, bauchmuskeln, bauchmuskelroller
  (16, 1),(16, 6), (16, 20),
  --beinheben mit yogaball, balance, flexibilitaet, oberschenkel, ruecken, yogaball
  (17, 2), (17, 3), (17, 10), (17, 8), (17, 21),
  --crunches mit yogaball, balance, bauchmuskeln, yogaball
  (18, 2),(18, 6), (18, 21),
  --superman, kraft, ruecken, bauchmuskeln
  (19, 1), (19, 8), (19, 6);


merge INTO gif KEY(id) VALUES
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
  (18, 13,'img_ex_armstrecken_kurzhantel1.jpg'),
  (19, 13,'img_ex_armstrecken_kurzhantel2.jpg'),
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
  (28, 19, 'img_ex_superman2.jpg');

