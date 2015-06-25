MERGE INTO categoryname VALUES (0, 'Kategorie'), (1, 'Muskelgruppe'), (2, 'Geräte');

MERGE INTO category VALUES (0, 'Ausdauer', 0), (1, 'Kraft', 0), (2, 'Balance', 0), (3, 'Flexibilität', 0),
  (4, 'Bizeps', 1), (5, 'Trizeps', 1), (6, 'Bauchmuskeln', 1), (7, 'Schultern', 1), (8, 'Rücken', 1),
  (9, 'Brust', 1), (10, 'Oberschenkel', 1), (11, 'Unterschenkel', 1), (12, 'Wadenbein', 1),
  (13, 'Medizinball', 2), (14, 'Klimmzugstange', 2), (15, 'Kurzhantel', 2), (16, 'Langhantel', 2),
  (17, 'Springschnur', 2),
  (18, 'Sandsack', 2), (19, 'Expander', 2), (20, 'Bauchmuskel Roller', 2), (21, 'Yogaball', 2);


MERGE INTO exercise KEY (id) VALUES
  (0, 'Liegestütz', 'Legen Sie sich auf den Bauch, strecken Sie die Beine, schließen Sie die Füße und stellen Sie die Zehenspitzen auf. Die Hände sind direkt unter den Schultern. Stemmen Sie sich nun vom Boden hoch. Ihr Körper sollte während der Bewegung eine gerade Linie bilden, von den Fersen bis zum Nacken.', 1.0, 'pushup.mp4',
   NULL, FALSE),
  (1, 'Armrotation', 'Eine tolle Übung, um die Schultern vor oder nach den intensiveren Übungen aufzuwärmen oder abzukühlen. Armrotationen sind besonders dann geeignet, wenn man nach einer Verletzung oder im Alter gerade wieder mit dem Training beginnt. Stellen Sie sich aufrecht hin, strecken Sie die Arme seitlich aus und machen Sie kleine oder große Kreise.   Zuerst kreisen Sie zehnmal in die eine, dann in die andere Richtung.', 0.3, 'armrotation.mp4', NULL, FALSE),
  (2, 'Daumen hoch', 'Nehmen Sie die Bauchlage ein. Ihre Füße sind hüftbreit geöffnet, die Fußspitzen aufgestellt. Strecken Sie die Arme seitlich aus und ballen Sie Ihre Hände zu Fäusten, die Daumen zeigen nach oben. Heben Sie nun die Schultern und den Kopf vom Boden an und ziehen Sie die gestreckten Arme so weit wie möglich nach oben. Dabei nähern sich Ihre Schulterblätter an.', 0.3, 'armup.mp4', NULL, FALSE),
  (3, 'Kniebeuge', 'Wichtig. Die Knie beim Hochgehen nicht ganz durchstrecken sondern etwas tief bleiben.', 0.75, 'kniebeugen.mp4', NULL, FALSE),
  (4, 'Crunch it UP', 'Legen Sie sich auf den Rücken. Stellen Sie die Beine angewinkelt hüftbreit auf und haken Sie die Füße unter einem Gegenstand (Bett, Sofa, Stuhl, Regal, Couchtisch etc.) ein. Verschränken Sie die Arme vor der Brust und halten Sie sie fest an den Körper gepresst. Jetzt spannen Sie die Bauchmuskeln fest an, lösen Kopf und Schultern vom Boden und kommen schließlich langsam mit dem gesamten Oberkörper nach oben, bis die Ellbogen die Oberschenkel nahe der Hüften berühren. Senken Sie sich anschließend wieder langsam ab, bis die Schulterblätter den Boden berühren. Legen Sie Kopf und Schultern nicht ab und halten Sie die Bauchmuskeln angespannt.', 0.5, 'crunch.mp4', NULL, FALSE),
  (5, 'Strandschere', 'Legen Sie sich mit gestreckten Beinen auf die linke Seite und stützen Sie den Kopf mit dem linken Arm ab. Die andere Hand stützen Sie vor Ihrer Brust auf. Heben Sie dann Ihr rechtes Bein so hoch wie möglich an und halten Sie es gestreckt für drei Sekunden in der', 0.2, 'strandschere.mp4', NULL, FALSE),
  (6, 'Auf der Stelle Laufen', 'laufen Sie auf der Stelle', 0.3, 'auf_der_stelle_laufen.mp4', NULL, FALSE),
  (7, 'Einbeinige Kniebeuge', 'Kraft, Koordination, Balance und Ausdauer. Und mit ein bisschen Kreativität können Sie die Übung endlos variieren. Stellen Sie sich auf den rechten Fuß und heben Sie Ihr linkes Bein gestreckt nach vorn an. Halten Sie sich mit der linken Hand an einer Stuhllehne oder einem anderen, etwa hüfthohen Gegenstand fest, um das Gleichgewicht zu bewahren. Der Rücken ist gerade, Ihr Blick ist nach vorn gerichtet. Beugen Sie nun das rechte Bein und gehen Sie so tief, bis der rechte Oberschenkel parallel zum Boden ist. Dabei neigen Sie denOberkörper nach vorn und schieben die Schultern über die Knie hinaus. Der linke Fuß bleibt währenddessen angehoben.Das rechte Knie bleibt über dem Mittelfuß. Achten Sie auf den geraden Rücken. Drücken Sie sich anschließend nur mit der Kraft Ihres rechten Beins wieder nach oben. Halten Sie sich nur fest, um das Gleichgewicht zu bewahren, und nicht, um sich mithilfe der abgestützten Hand hochzuschieben. Strecken Sie das rechte Knie am höchsten Punkt nicht ganz durch.Absolvieren Sie so viele Wiederholungen wie möglich, wechseln Sie dann die Seite.', 1.0, 'einbeinige_kniebeugen.mp4', NULL, FALSE),
  (8, 'Ausfallschritt', 'Stellen Sie sich mit hüftbreit geöffneten Füßen aufrecht hin, die Fußspitzen zeigen nach vorne. Legen Sie die Hände an den Hinterkopf und richten Sie Ihren Blick in die Ferne. Jetzt machen Sie mit dem linken Fuß einen großen Schritt nach vorn und beugen dabei das Knie. Senken Sie Ihre Hüften so tief, bis das hintere Knie fast den Boden berührt. Beide Knie sollten am tiefsten Punkt im rechten Winkel gebeugt sein. Das vordere Knie befindet sich direkt über der Ferse, schieben Sie es nicht über die Zehen hinaus. Nun stoßen Sie sich mit dem linken Bein wieder ab und kommen zurück in die Ausgangsposition.Drücken Sie die Knie nicht ganz durch und achten Sie während des Bewegungsablaufs darauf, den Kopf in Verlängerung der Halswirbelsäule und den Rücken gerade zu halten. Wechseln Sie nun die Seite.', 0.8, 'ausfallschritte.mp4', NULL, FALSE),
  (9, 'Plank', 'Setzen sie die Unterarme schulterbreit auf dem Boden auf und bilden Sie mit dem Rücken eine gerade Linie. Halten Sie diese Position.', 0.4, NULL, NULL, FALSE),
  (10, 'Seilspringen', 'Versuchen Sie eine geringe Sprunghöhe um Kraft zu schonen und versuchen Sie es auch einmal rückwärts', 0.25, 'schnurspringen.mp4', NULL, FALSE),
  (11, 'BOXEN-Schläge','Stellen Sie sich schulterbreit vor den Box/Sandsack und schlagen führen Sie gezielte Schläge aus', 0.3, 'boxen_schlaege.mp4', NULL,
   FALSE),
  (12, 'BOXEN-Tritte', 'Stellen Sie sich schulterbreit vor den Box/Sandsack und treten sie mit dem Schienbein gegen den Sandsack.Dehnen Sie sich vorher ausreichend', 0.5, 'boxen_tritte.mp4', NULL, FALSE),
--   (13, 'Armstrecken mit Kurzhantel', '', 0.4, NULL, NULL, FALSE),
--   (14, 'Bizeps mit Langhantel', '', 0.3, NULL, NULL, FALSE),
--   (15, 'Kniebeugen mit Langhantel', '', 0.5, NULL, NULL, FALSE),
--   (16, 'Bauchmuskeln Mit Roller', '', 0.3, NULL, NULL, FALSE),
--   (17, 'Beinheben mit Yogaball', '', 0.3, NULL, NULL, FALSE),
--   (18, 'Crunches mit Yogaball', '', 0.3, NULL, NULL, FALSE),
  (19, 'Superman', 'Legen Sie sich auf den Bauch und strecken die Arme und Beine aus. Heben Sie den Kopf leicht vom Boden ab, so dass Sie aber noch kein Ziehen im Nacken spüren. Heben sie nun die ausgestreckten Arme und Beine so weit an, bis sie die Spannung in Ihrem Rücken und in den Bauchmuskeln spüren. Halten Sie diese Position für einige Sekunden und lassen Sie danach sanft ihre Arme und Beine wieder sinken.', 0.3, 'superman.mp4', NULL, FALSE),
--   (20, 'Brust dehnen', '', 0.3, NULL, NULL, FALSE),
  (21, 'Schultern dehnen', 'Positionieren Sie einen Arm so, dass er parallel zu Ihrem Oberkörber ist. Greifen Sie nun mit dem der anderen Hand auf Ihren zu dehnenden Oberarm und ziehen Sie sanft in Richtung ihres Oberkörpers. Nun sollten Sie im Oberarm bereits eine Dehnung merken.', 0.3, NULL, NULL, FALSE),
  (22, 'Rücken (oben) dehnen', 'Stellen Sie sicht aufrecht hin und strecken Ihre Arme vor sich aus. Führen Sie nun ihre beiden Hände ineinander über und drehen diese so, dass ihre Handflächen von Ihnen weg schauen. Achten Sie hier auf einen geraden Rücken und dass Sie nicht zu viel Druck ausüben.', 0.3, NULL, NULL, FALSE),
  (23, 'Rücken (Mitte) dehnen', 'Knien Sie in die Yoga Katzenposition mit Händen und Knien auf dem Boden. Drücken Sie nun Ihren Rücken in Richtung des Bodens. Hier ist besonders wichtig nicht zu viel Druck auszuüben und sanft zu beginnen.', 0.3, NULL, NULL, FALSE),
  (24, 'Rücken (unten) dehnen', 'Legen Sie sich auf den Rücken und ziehen Sie Ihre Beine dicht an Ihren Bauch heran. Greifen Sie nun mit Ihren Händen nach Ihren Unterschenklen, lassen Sie dabei aber den Oberarm auf dem Boden aufliegen. Üben Sie nun einen sanften Zug zu Ihnen aus.', 0.3, NULL, NULL, FALSE),
  (25, 'Quadrizeps dehnen', 'Stellen Sie sich für diese Übung am besten vor eine Wand um sich abstütezen zu können. Legen Sie eine Hand auf die Wand, Sessel, ... Winkeln Sie nun ein Bein ab und ziehen es langsam mit hilfe des zweiten Armes immer näher an Ihr Gesäs heran.', 0.3, NULL, NULL, FALSE),
  (26, 'Gesäßmuskulatur dehnen', 'Setzen Sie sich mit ausgestreckten Beinen auf den Boden. Stellen Sie ein Bein auf der gegenüberliegenden Seite mit der Sohle auf den Boden. Nehmen Sie nun den gegenüberliegenden Arm, strecken diesen aus und legen Ihn auf etwa Kniehöhe ab. Beginnen Sie nun ihren Oberkörper in entgegengesetzter Richtung zum aufgestellten Bein zu drehen. Hierbei ensteht Druck auf ihren Arm welcher auf dem Knie aufliegt. Durch das Drehen sollten Sie nun eine Dehnung im unteren Beckenbereich spüren.', 0.3, NULL, NULL, FALSE),
  (27, 'Kniesehne dehnen', 'Setzen Sie sich auf den Boden und strecken die Beine aus. Ziehen Sie die Zähenspitzen leicht zu Ihnen und versuchen Sie mit der Hand nach den Zehenspitzen zu greifen. Sollten Sie dies nicht gleich schaffen, ist dies nicht weiters schlimm. Versuchen Sie nicht mit Gewalt die Zehenspitzen zu erreichen, dies kann zu einer Überdehnung der Kniesehen führen.', 0.3, NULL, NULL, FALSE),
  (28, 'Burpees', 'Stellen Sie sich aufrecht mit geschlossenen Beinen hin. Gehen Sie nun mit Ihrem ganzen Körper nieder und legen die Arme auf dem Boden auf. Springen Sie nun mir Ihren Beinen nach hinten, so dass diese wie in der Liegestütz-Position gestreckt sind. Springen Sie aus dieser Position wieder nach Vorne, mit den Händen noch am Boden. Gehen sie danach wieder auf und führen Ihre Arme über Ihrem Kopf wie im Hampelmann zusammen. Als kleine Alternative können sie auch einen Liegestütz ausführen.', 0.2, 'burpees.mp4', NULL, FALSE),
  (29, 'Captain Morgan', 'Stellen Sie sich aufrecht hin und strecken die Arme im 90 Grad winkel von sich aus. Heben Sie nun ein Bein so weit wie möglich an und halten dieses über Ihrer Zentrallinie (ca. Körpermitte, Posiion des zweiten Fußes)', 0.2, 'captain_morgan.mp4', NULL, FALSE),
  (30, 'Diamond Liegestütz', 'Formen Sie für diese Variaiton des Liegestütz mit beiden Händen einen Diamanten auf dem Boden und führen Sie so Liegestütze aus. Bei dieser Variation trainieren Sie vor allem die Unterarme.', 1.0, 'diamond_pushup.mp4', NULL, FALSE),
  (31, 'Handstand Liegestütz', 'Führen Sie diese Übung am besten an einer Wand aus. Machen Sie einen Handstand und lehnen sie sich dabei von der Wand an. Bringen Sie nun alle Kraft in Ihre Arme und drücken sich vom Boden ab.', 1.5, 'handstand_pushup.mp4', NULL, FALSE),
  (32, 'Heel To Toe Walk', 'Stellen Sie sich aufrecht hin und strecken die Arme im 90 Grad Winkel von sich ab. Bringen Sie nun einen Fuß direkt unmittelbar vor den zweiten Fuß und halten sie die Balance. Achten Sie bei dieser Übung darauf, dass Sie sich nicht zu sehr auf ihre Füße konzentrieren. Im Optimalfall schauen Sie geradeaus.', 0.2, 'heel_to_toe_walk.mp4', NULL, FALSE),
  (33, 'Ice Skater', 'Stellen Sie sich aufrecht hin und strecken die Arme im 90 Grad Winkel von sich ab. Winkeln Sie nun ein Bein hinter sich leicht überkreuzt ab und halten Sie diese Position. Beim Ablassen des Beines nicht zu schnell sein.', 0.2, 'ice_skate.mp4', NULL, FALSE),
  (34, 'Jumping Jack', 'Stellen Sie sich aufrecht hin und lassen die Arme locker hängen. Machen Sie nun einen Sprung und öffnen dabei Ihre beine etwas mehr als Schulterbreit. Gleichzeitig schlagen Sie ihre Arme über Ihrem Kopf mit den Handflächen zusammen. Gehen Sie anschließend wieder in die Ausgangsposition über. Ziel sollte hier eine schnelle Ausführung sein und vor allem ein gleichzeitiges Auseinanderspringen der Beine und Zusammenführen der Arme.', 0.2, 'jumping_jack.mp4', NULL, FALSE),
  (35, 'Klimmzüge', 'Für diese Übung suchen Sie sich am besten eine Stabile Stange. Platzieren sie ihre Hände in etwa schulterbreit auf der Stange. Ziehen Sie sich nun mit Ihrem ganzen Körper auf, bis Ihr Kinn ca. auf Höhe der Stange ist. Für ein Maximum an Trainingsleistung lassen Sie sich langsam ab.', 1.5, 'klimmzuege.mp4', NULL, FALSE),
  (36, 'Mountain Climber', 'Gehen sie in eine Liegestützposition mit den Armen ca. schulterbreit geöffnet. Ziehen Sie nun ein Bein in Richtung Ihres Oberkörpers. Beim Zurückführen des Beines springen Sie bereits wieder mit dem zweiten Bein nach Vorne.', 0.5, 'mountain_climber.mp4', NULL, FALSE),
  (37, 'Push-Up Knee Cross-over', 'Gehen Sie in eine Liegestützposition. Führen Sie nun ein Bein langsam auf die Gegenüberliegende Seite über und halten diese Position kurz. Gehen Sie anschließend wieder in die Ausgangsposition zurück und wiederholen diese Übung nun mit dem anderen Bein.', 0.3, 'pushup_knee_crossover.mp4', NULL, FALSE);


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
--   (13, 1), (13, 9), (13, 7), (13, 4), (13, 5), (13, 15),
--   --bizeps mit langhantel, kraft, bizeps, langhantel
--   (14, 1), (14, 4), (14, 16),
--   --kniebeugen mit langhantel, kraft, oberschenkel, r�cken, langhantel
--   (15, 1), (15, 10), (15, 8), (15, 16),
--   --bauchmuskteln mit roller, kraft, bauchmuskeln, bauchmuskelroller
--   (16, 1), (16, 6), (16, 20),
--   --beinheben mit yogaball, balance, flexibilitaet, oberschenkel, ruecken, yogaball
--   (17, 2), (17, 3), (17, 10), (17, 8), (17, 21),
--   --crunches mit yogaball, balance, bauchmuskeln, yogaball
--   (18, 2), (18, 6), (18, 21),
  --superman, kraft, ruecken, bauchmuskeln
  (19, 1), (19, 8), (19, 6),
  --Brust dehnen, Flexibilität, Brust
--   (20, 3), (20, 9),
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
  (27, 3), (27, 10),
  --Burpees, Ausdauer, Bauchmuskeln, Rücken
  (28, 0), (28, 6), (28, 8),
  --Captain Morgan, Balance, Bauchmuskeln, Rücken
  (29, 2), (29, 6), (29, 8),
  --Diamond push-up, Kraft, Ausdauer, Trizeps, Schultern, Brust, Bauchmuskeln
  (30, 0), (30, 1), (30, 5), (30, 6), (30, 7), (30, 9),
  --Handstand push-up, Kraft, Trizeps, Schultern, Rücken, Bauchmuskeln
  (31, 0), (31, 5), (31, 6), (31, 7), (31, 8),
  --Heel to toe walk, Balance
  (32, 2),
  --Ice Skater, Balance, Bauchmuskeln, Rücken
  (33, 2), (33, 6), (33, 8),
  --Jumping Jack, Ausdauer
  (34, 0),
  --Klimmzüge, Ausdauer, Kraft, Bizeps, Schultern, Rücken
  (35, 0), (35, 1), (35, 4), (35, 7), (35, 8), (35, 14),
  --Mountain Climber, Ausdauer, Kraft, Bauchmuskeln
  (36, 0), (36, 1), (36, 6),
  --Push-up knee cross-over, Ausdauer, Bauchmuskeln
  (37, 0), (37, 6);


MERGE INTO gif KEY (id) VALUES
  --liegestuetz
  (1, 0, 'img_ex_pushup1.jpg'),
  (2, 0, 'img_ex_pushup2.jpg'),
  --armrotation
  (3, 1, 'img_ex_armrotation1.jpg'),
  (4, 1, 'img_ex_armrotation2.jpg'),
  --daumen hoch
  (5, 2, 'img_ex_daumenhoch.jpg'),
  --kniebeugen
  (6, 3, 'img_ex_kniebeugen1.jpg'),
  (7, 3, 'img_ex_kniebeugen2.jpg'),
  --crunch it up
  (8, 4, 'img_ex_crunch1.jpg'),
  (9, 4, 'img_ex_crunch2.jpg'),
  --strandschere
  (10, 5, 'img_ex_strandschere1.JPG'),
  (11, 5, 'img_ex_strandschere2.JPG'),
  --einbeinige kniebeugen
  (13, 7, 'img_ex_einb_kniebeugen1.jpg'),
  (14, 7, 'img_ex_einb_kniebeugen2.jpg'),
  --ausfallschritt
  (15, 8, 'img_ex_ausfallschritt_links.jpg'),
  --plank
  (16, 9, 'img_ex_plank.jpg'),
  --boxen schlaege
  (18, 11, 'img_ex_boxen_schlaege_1.jpg'),
  (19, 11, 'img_ex_boxen_schlaege_2.jpg'),
  --superman
  (29, 19, 'img_ex_superman1.jpg'),
  (30, 19, 'img_ex_superman2.jpg'),
  --Schultern dehnen, Flexibilität, Schultern
  (32, 21, 'img_ex_shoulder_stretch1.JPG'),
  (33, 21, 'img_ex_shoulder_stretch2.JPG'),
  (34, 21, 'img_ex_shoulder_stretch3.JPG'),
  --Rücken (oben) dehnen, Flexibilität, Rücken
  (35, 22, 'img_ex_upper_back_stretch1.JPG'),
  --Rücken (Mitte) dehnen, Flexibilität, Rücken
  (36, 23, 'img_ex_middle_back_stretch1.JPG'),
  --Rücken (unten) dehnen, Flexibilität, Rücken
  (37, 24, 'img_ex_lower_back_stretch1.JPG'),
  --Quadrizeps dehnen, Flexibilität, Oberschenkel
  (38, 25, 'img_ex_quadriceps_stretch1.JPG'),
  --Gesäßmuskulatur dehnen, Flexibilität, Oberschenkel
  (39, 26, 'img_ex_glutes_stretch1.JPG'),
  --Kniesehne dehnen, Flexibilität, Oberschenkel
  (40, 27, 'img_ex_hamstring_stretch1.JPG'),
  --Burpees
  (41, 28, 'img_ex_burpees_1.JPG'),
  (42, 28, 'img_ex_burpees_2.JPG'),
  (43, 28, 'img_ex_burpees_3.JPG'),
  (44, 28, 'img_ex_burpees_4.JPG'),
  (45, 28, 'img_ex_burpees_5.JPG'),
  --Captain Morgan
  (46, 29, 'img_ex_captain_morgan_1.JPG'),
  (47, 29, 'img_ex_captain_morgan_2.JPG'),
  --Diamond push-up
  (48, 30, 'img_ex_diamond_pushup_1.JPG'),
  (49, 30, 'img_ex_diamond_pushup_2.JPG'),
  --Handstand push-up
  (50, 31, 'img_ex_handstand_pushup_UP.JPG'),
  (51, 31, 'img_ex_handstand_pushup_DOWN.JPG'),
  --Heel to toe walk
  (53, 32, 'img_ex_heel_to_toe_walk_1.JPG'),
  (54, 32, 'img_ex_heel_to_toe_walk_2.JPG'),
  --Ice Skater
  (55, 33, 'img_ex_ice_skate_1.JPG'),
  (56, 33, 'img_ex_ice_skate_2.JPG'),
  (57, 33, 'img_ex_ice_skate_3.JPG'),
  --Jumping Jack
  (58, 34, 'img_ex_jumping_jack_1.JPG'),
  (59, 34, 'img_ex_jumping_jack_2.JPG'),
  --Klimmzüge
  (60, 35, 'img_ex_klimmzug_down.JPG'),
  (61, 35, 'img_ex_klimmzug_up.JPG'),
  --Mountain Climber
  (62, 36, 'img_ex_mountain_climber_links.JPG'),
  (63, 36, 'img_ex_mountain_climber_rechts.JPG'),
  --Push-Up Knee Cross-over
  (64, 37, 'img_ex_pushup_knee_crossover_2.JPG'),
  (65, 37, 'img_ex_pushup_knee_crossover_1.JPG'),
  (66, 37, 'img_ex_pushup_knee_crossover_3.JPG'),
  (67, 37, 'img_ex_pushup_knee_crossover_1.JPG'),
  --Auf der Stelle laufen
  (68, 6, 'img_ex_lauf_am_stand_rechts.JPG'),
  (69, 6, 'img_ex_lauf_am_stand_links.JPG'),
  --Seilspringen
  (70, 10, 'img_ex_schnurspringen_opt_hoehe.JPG'),
  --Boxen-tritte
  (71, 12, 'img_ex_boxen_tritte.JPG'),
  --Ausfallschritt (2. Bild)
  (72, 8, 'img_ex_ausfallschritt_rechts.jpg');