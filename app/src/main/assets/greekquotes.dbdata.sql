INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');

INSERT INTO "credits" ("credit") VALUES ('Laboratorio di Cultura digitale, Dipartimento di Lettere e Filosofia, Università di Pisa');
INSERT INTO "credits" ("credit") VALUES ('Wikipedia, List of Greek phrases, https://en.wikipedia.org/wiki/List_of_Greek_phrases');
INSERT INTO "credits" ("credit") VALUES ('Francese, Christopher. Greek Core Vocabulary. Dickinson College Commentaries (2014). http://dcc.dickinson.edu/greek-core-list');
INSERT INTO "credits" ("credit") VALUES ('Revithiadou, A. (2018) Ancient Greek pitch accent');
INSERT INTO "credits" ("credit") VALUES ('Poesia laina, testi greci interattivi http://www.poesialatina.it/_ns/Greek/tt2/p/Elenco.html');
INSERT INTO "credits" ("credit") VALUES ('http://homermultitext.blogspot.com/2013/06/iliad-8-scholia-on-mythological.html');
INSERT INTO "credits" ("credit") VALUES ('https://latinygriegojanda.files.wordpress.com/2012/06/orfeo-y-euridice.pdf');
INSERT INTO "credits" ("credit") VALUES ('https://www.cornellcollege.edu/classical_studies/ariadne/audio/index.shtml');
INSERT INTO "credits" ("credit") VALUES ('https://soundcloud.com/archaeologymag/what-ancient-greek-music');
INSERT INTO "credits" ("credit") VALUES ('https://sites.google.com/site/materialesathenaze/ho-dikaiopolis');
INSERT INTO "credits" ("credit") VALUES ('http://aliso.pntic.mec.es/agalle17/trabajo_final/cap4.html');

INSERT INTO "app_notes" ("title","note") VALUES ('about','Hippopotamuses are located in central and southern Africa, south of the Sahara desert and near the Nile River.</br></br><b>Diet</b></br>They are vegetarians and mostly eat grass. They may travel long distances during the night from their water source to reach the areas where they graze. They also sometimes eat fruit that has fallen off of trees or crops grown by local people.');

INSERT INTO "news" ("title","link") VALUES ('Pablo Escobar''s hippos keep multiplying and Colombia doesn''t know how to stop it','https://www.cbsnews.com/news/pablo-escobars-hippos-keep-multiplying-and-colombia-doesnt-know-how-to-stop-it/');

INSERT INTO "schermate" ("_id","description") VALUES (1,'Disyllabic paroxytone words');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (1,2,'parossìtone due sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (3,'τόπος');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (4,'φίλος','filosf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (5,'λόγος','logosf.ogg');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (3,2,'Luogo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (4,2,'Caro, amico');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (5,2,'parola, linguaggio, discorso; pensiero, ragione, conto');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,3,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,4,2);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,5,3);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (1,1,'Place, friend, word.');

INSERT INTO "schermate" ("_id","description") VALUES (2,'Trisyllabic Proparoxytone words');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (2,1,'Man, messenger.');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (2,2,'proparossìtone tre sillabee');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription","audioFileName") VALUES (6,'ἄνθρωπος','ántʰrɔɔpós','anthrwpf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (7,'ἄγγελος');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (6,2,'Uomo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (7,2,'Messaggero');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,6,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,7,2);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (3,'Fun Fact','Plutarch, De garrulitate, 17');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (3,1,'if');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (8,'αἴκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (8,2,'se (ipotetico) (solo in dialetto dorico)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (3,8);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (3,1,'αἴκα / αἴκε / αἴ κα / αἴ κε dor. / om. eἴκε for εἰ+ἄν > ἐάν, ἄν');
INSERT INTO "easter_egg_comments" ("schermata_id","language_id","eeComment") VALUES (3,1,'Plutarch reports that Phillip II of Macedon sent word to the Spartans, saying that "if I should invade Laconia, I shall drive you out." The Spartans laconically responded with "if."');

INSERT INTO "schermate" ("_id","description") VALUES (4,'1st declension, pure -ᾱ');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (4,1,'Region, fate, day.');
INSERT INTO "schermate_descriptions_translations" ("schermata_id","language_id","description") VALUES (4,2,'I declinazione in α puro');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (9,'χώρα','xwraf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (10,'μοῖρα');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (11,'ἡμέρα','hhmeraf.ogg');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (9,2,'regione');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (10,2,'destino');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (11,2,'giorno');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,11,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,9,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,10,1);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (5,'Incipits','Apollod. I.1.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (5,1,'Uranus (Sky) was the first lord of the whole universe.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (13,'Οὐρανὸς πρῶτος τοῦ παντὸς ἐδυνάστευσε κόσμου.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (13,2,'Urano fù il primo signore di tutto l''universo.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (13,1,'Uranus (Sky) was the first lord of the whole universe.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (5,13);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (5,1,'τοῦ κόσμου GEN sg (m, II decl.); ἐδυνάστευσε + GEN: was lord over');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (6,'εἰς Τάρταρον','Apollod. I.1.2');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (6,1,'he threw them into Tartarus, a dark place in the Ade, as far from Earth as Earth is from heaven');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (14,'τούτους [..] εἰς Τάρταρον ἔρριψε （τόπος δὲ οὖτος ἐρεβώδης ἐστὶν ἐν Ἅιδου, τοσοῦτον ἀπὸ γῆς ἔχων διάστημα ὅσον ἀπ᾿ οὐρανοῦ γῆ.)');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (14,2,'he threw them into Tartarus, a dark place in the Ade, as far from Earth as Earth is from heaven');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (6,14);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (6,1,'εἰς Τάρταρον, εἰς + Acc (sg, II decl.)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (7,'The start of Time','Apollod. I.1.3');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (7,1,'And again he (Uranus, the sky) begat children by Earth, the Titans as they are named: Ocean, Coeus, Hyperion, Crius, Iapetus, and, youngest of all, Cronus (Time)');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (15,'τεκνοῖ δὲ αὖθις ἐκ Γῆς παῖδας μὲν τοὺς Τιτᾶνας προσαγορευθέντας, Ὠκεανὸν Κοῖον Ὑπερίονα Κρεῖον Ἰαπετὸν καὶ νεώτατον ἁπάντων Κρόνον');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (15,2,'And again he (Uranus, the sky) begat children by Earth, the Titans as they are named: Ocean, Coeus, Hyperion, Crius, Iapetus, and, youngest of all, Cronus (Time)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (7,15);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (8,'Zeus, Pluto, Poseidon','Apollod. I.2.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (8,1,'And the Cyclopes then gave Zeus thunder and lightning and a thunderbolt, and on Pluto they bestowed a helmet and on Poseidon a trident.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (16,' καὶ Κύκλωπες τότε Διὶ μὲν διδόασι βροντὴν καὶ ἀστραπὴν καὶ κεραυνόν, Πλούτωνι δὲ κυνέην, Ποσειδῶνι δὲ τρίαιναν');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (16,1,'And the Cyclopes then gave Zeus thunder and lightning and a thunderbolt, and on Pluto they bestowed a helmet and on Poseidon a trident.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (8,16);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (9,'Δίκην, Εἰρήνην, Εὐνομίαν','Apollod. I.3.1');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (9,1,'Peace, Order, Justice, and the Fates');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (17,'Εἰρήνην Εὐνομίαν Δίκην, μοίρας');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,2,'Irene, la Pace, Eunomia, l''Ordine, Dike, la Giustizia, e le Moire');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,1,'Peace, Order, Justice, and the Fates');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (9,17);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (9,1,'Εἰρήνην Εὐνομίαν Δίκην: ACC sg; μοίρας: ACC pl (f)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (10,'The North Wind and the Sun','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (10,1,'The North Wind and the Sun were disputing which was the stronger (on strenght).');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (18,'Βορέας καὶ Ἥλιος περὶ δυνάμεως ἤριζον·');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (18,2,'Borea e il Sole litigavano su chi fosse il più forte (sulla forza).');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (18,1,'The North Wind and the Sun were disputing which was the stronger (on strenght).');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (10,18);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (10,1,'ἤριζον: imperf ind act, 3rd pl (ἐρίζω)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (22,'The North Wind and the Sun (2)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (22,1,'It was resolved that vicory would be given to the one who succeeded in making a traveler take off his cloak.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (33,'ἔδοξε δὲ αὐτοῖς ἐκείνῳ τὴν νίκην ἀπονεῖμαι, ὃς ἂν αὐτῶν ἄνθρωπον ὁδοιπόρον ἐκδύσῃ.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (33,2,'Decisero di assegnare la vittoria a chi di loro avesse spogliato un viandante.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (33,1,'It was resolved that the one who succeeded in making a traveler take his cloak off would win.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (22,33);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (22,1,'ἔδοξε: aor ind act, 3rd sg');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (23,'The North Wind and the Sun (3)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (23,1,'Then the North Wind started blowing hard; and the more he blew the more closely did the traveler fold his cloak around himself.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (28,'καὶ ὁ Βορέας ἀρξάμενος σφοδρὸς ἦν· τοῦ δὲ ἀνθρώπου ἀντεχομένου τῆς ἐσθῆτος μᾶλλον ἐπέκειτο.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (28,2,'Boreas all''inzio era impetuoso; e siccome l''uomo stava attaccato al vestito, soffiava di più.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (28,1,'Then the North Wind started blowing hard; and the more he blew the more closely did the traveler fold his cloak around himself.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (23,28);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (23,1,'ὁ Βορέας ἀρξάμενος σφοδρὸς ἦν: ..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (24,'The North Wind and the Sun (4)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (24,1,'Tormented even more by the cold, he put more clothes on.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (29,'ὁ δὲ ὑπὸ τοῦ ψύχους καταπονούμενος ἔτι μᾶλλον καὶ περιττοτέραν ἐσθῆτα προσελάμβανεν,');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (29,2,'E quello, tormentato ancora di più dal freddo si aggiunse anche un altro vestito,');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (24,29);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (24,1,'καταπονούμενος: ..; προσελάμβανεν: ..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (25,'The North Wind and the Sun (5)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (25,1,'and at last the North Wind grew quite weary, handing it over to the Sun.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (30,'ἕως ἀποκαμὼν ὁ Βορέας τῷ Ἡλίῳ μεταπαρέδωκε.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (30,2,'finché Boreas, stancatosi, cedette (l''uomo) ad Elios.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (30,1,'and at last the North Wind grew quite weary, handing it over to the Sun.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (25,30);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (25,1,'ἀποκαμὼν: part sg aor act, NOM sg (m); μεταπαρέδωκε: 3rd sg aor ind act');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (26,'The North Wind and the Sun (6)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (26,1,'then he (the Sun) first started shining mildly;');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (31,'κἀκεῖνος τὸ μὲν πρῶτον μετρίως προσέλαμψε·');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (26,31);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (27,'The North Wind and the Sun (7)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (27,1,'as the traveler took off the extra clothes, he sent more intense heat.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (36,'τοῦ δὲ ἀνθρώπου τὰ περισσὰ τῶν ἱματίων ἀποτιθεμένου σφοδρότερον τὸ καῦμα ἐπέτεινε,');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (27,36);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (31,'The North Wind and the Sun (8)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (31,1,'In the end, when the traveler couldn''t stand the heat, since there was a river nearby, he stripped off his clothes and went to take a bath.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (37,'μέχρις οὗ πρὸς τὴν ἀλέαν ἀντέχειν μὴ δυνάμενος ἀποδυσάμενος ποταμοῦ παραῤῥέοντος ἐπὶ λουτρὸν ἀπῄει.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (31,37);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (32,'The North Wind and the Sun (9)','Aesop');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (32,1,'The story shows that persuasion is often more effective than violence.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (38,'ὁ λόγος δηλοῖ, ὅτι πολλάκις τὸ πείθειν τοῦ βιάζεσθαι ἀνυστικώτερόν ἐστι.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (32,38);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (11,'Eureka','Archimedes (Plutarch 73.1094c)');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (11,1,'I''ve found (it)');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (19,'εὕρηκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (19,2,'ho trovato.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (11,19);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (11,2,'perfetto.. da εὑρίσκω, pf.');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (12,'GEN pl','Om. Iliad 6.128');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (12,1,'one of the immortals');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription") VALUES (12,'τὶς ἀθανάτων','tìs atʰanátɔɔn');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (12,2,'uno degli immortali');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (12,12);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (12,1,'τὶς ἀθανάτων: GEN pl');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (13,'Thyrants','Thales of Miletus, 35');
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (13,1,'What is the strangest thing to see? An aged tyrant.');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (20,'τί καινὸν εἴη τεθεαμένος; Γέροντα τύραννον.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (20,1,'What is the strangest thing to see? An aged tyrant.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (13,20);

INSERT INTO "playlists" ("_id","description") VALUES (0,'Test playlist');
INSERT INTO "schermate" ("_id","description") VALUES (14,'audio test');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,4);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,5);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,6);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,9);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (14,11);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (15,'In the nether world','Apollod. 1.2.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (21,'καθείρξαντες αὐτοὺς ἐν τῷ Ταρτάρῳ');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (21,1,'shut them up in Tartarus (in the nether world)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (15,21);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (15,1,'ἐν τῷ Ταρτάρῳ: DAT sg (m)..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (16,'Heavens, sea and Hades','Apollod. 1.2.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (22,'καὶ λαγχάνει Ζεὺς μὲν τὴν ἐν οὐρανῷ δυναστείαν, Ποσειδῶν δὲ τὴν ἐν θαλάσσῃ, Πλούτων δὲ τὴν ἐν Ἅιδου.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (22,1,'and to Zeus was allotted the dominion of the sky, to Poseidon the dominion of the sea, and to Pluto the dominion in Hades');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (16,22);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (16,1,'ἐν οὐρανῷ, ἐν θαλάσσῃ: DAT sg ..');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (17,'Atlas','Apollod. 1.2.3');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (23,'Ἄτλας, ὃς ἔχει τοῖς ὤμοις τὸν οὐρανόν');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (23,1,'Atlas, who has the sky on his shoulders');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (23,2,'Atlante, che porta il cielo sulle spalle');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (17,23);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (17,1,'τοῖς ὤμοις: DAT pl (m)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (18,'ἄνεμοι καὶ ἄστρα','Apollod. 1.2.4');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (24,'Ἠοῦς δὲ καὶ Ἀστραίου ἄνεμοι καὶ ἄστρα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (24,1,'and to Dawn and Astraeus were born winds and stars');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (24,2,'e dall''Aurora e da Astreo nacquero i venti e le stelle');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (18,24);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (18,1,'ἄνεμοι καὶ ἄστρα: NOM pl (m, nt)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (19,'Νίκη Κράτος Ζῆλος Βία.','Apollod. 1.2.4');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (25,'Πάλλαντος δὲ καὶ Στυγὸς Νίκη Κράτος Ζῆλος Βία.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (25,1,'and to Pallas and Styx were born Victory, Dominion, Emulation, and Violence.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (25,2,'e da Pallante e Stige nacquero Nike, la vittoria, Crato, il potere, Zelo, l''ardore, e Bia, la violenza.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (19,25);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (19,1,'Νίκη Κράτος Ζῆλος Βία.: NOM sg (f, nt, m, f)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (20,'Ζεὺς','Apollod. 1.3.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (26,'Ζεὺς δὲ γαμεῖ μὲν Ἥραν [..] μίγνυται δὲ πολλαῖς θνηταῖς τε καὶ ἀθανάτοις γυναιξίν.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (26,1,'Zeus wedded Hera .. but he had intercourse with many women, both mortals and immortals.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (26,2,'Zeus sposa Era .. ma si unisce a molte donne, mortali e immortali');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (20,26);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (20,1,'πολλαῖς γυναιξίν: DAT pl');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (21,'μούσας','Apollod. 1.3.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (27,'ἐκ δὲ Μνημοσύνης μούσας');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (27,1,'by Memory he had the Muses');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (27,2,'da Mnemosine gli nascono le Muse');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (21,27);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (21,1,'ἐκ Μνημοσύνης: GEN sg, μούσας: ACC pl');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (28,'The Spartans','Plutarch, De garrulitate, 17');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (34,'γράψαντος αὐτοῖς τοῦ Φιλίππου ‘ἂν ἐμβάλω εἰς τὴν Λακωνικήν, ἀναστάτους ὑμᾶς ποιήσω, ’ ἀντέγραψαν ‘αἴκα.’');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (34,1,'when Philip wrote to them, ‘If I invade Laconia, I shall turn you out,’ they wrote back, ‘If.’');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (28,34);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (28,1,'');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (29,'Ὀρφεὺς','Apollod. 1.3.2');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (35,'Ὀρφεὺς ὁ ἀσκήσας κιθαρῳδίαν, ὃς ᾁδων ἐκίνει λίθους τε καὶ δένδρα.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (35,1,'Orpheus, who practised minstrelsy and by his songs moved stones and trees.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (29,35);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (29,1,'λίθους τε καὶ δένδρα: ACC pl (f, nt II decl.');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (33,'Another one bites the dust','Homer, Iliad, XI.749, XIX.61, XXII.17, XXIV.738');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (39,'ὀδὰξ ἕλον οὖδας.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (33,39);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (33,1,'with their teeth have they bitten the dust.');
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (33,1,'ὀδὰξ: adv. "(biting) with the teeth"; ἕλον (αἱρέω): "grasp", aor.2 homeric, 3rd pl ind act; οὖδας: "surface of the earth, ground" sg neut epic indeclform');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (34,'The Memorable thoughts of Socrates','Xenophon, Memorabilia 1.1.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (40,'ἀδικεῖ Σωκράτης οὓς μὲν ἡ πόλις νομίζει θεοὺς οὐ νομίζων, ἕτερα δὲ καινὰ δαιμόνια εἰσφέρων: ἀδικεῖ δὲ καὶ τοὺς νέους διαφθείρων.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (34,40);
INSERT INTO "schermate_greek_translations" ("schermata_id","language_id","translation") VALUES (33,1,'Socrates is guilty of rejecting the gods acknowledged by the state, and of introducint new strange deities: he is also guilty of corrupting the youth.');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (2,'ἀγαθός');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,2,'Buono, nobile');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,1,'Good, noble');


INSERT INTO "playlists" ("_id","description","disabled") VALUES (1,'Parole, vocabolario, declinazioni',1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,4);

INSERT INTO "playlists" ("_id","description","disabled") VALUES (2,'Frasi celebri',1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (2,3);

INSERT INTO "playlists" ("_id","description") VALUES (3,'Reading list - word series for ear training');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (3,4);

INSERT INTO "playlists" ("_id","description") VALUES (4,'Reading list - micro quotes to learn nouns declensions');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,12);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,5);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,6);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,9);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,15);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,16);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,17);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,18);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,19);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,20);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,21);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (4,29);
--..more..

INSERT INTO "playlists" ("_id","description") VALUES (5,'Reading list - quotes to learn verb forms');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,11);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,10);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,22);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,23);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,24);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,25);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,26);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,27);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,31);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (5,32);

INSERT INTO "playlists" ("_id","description") VALUES (6,'Reading list - mythic and epic quotes');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,7);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,8);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,33);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (6,34);

INSERT INTO "playlists" ("_id","description") VALUES (7,'Reading list - more quotes');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (7,13);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (7,28);

INSERT INTO "playlists" ("_id","description") VALUES (8,'Reading list - quotes for prepositions');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (8,3);

--TODOs
--add/concat/merge/join column eec to description
--table: s_descriptions_translations (default in english), move it text in new table