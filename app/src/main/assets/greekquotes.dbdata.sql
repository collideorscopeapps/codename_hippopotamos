INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');

INSERT INTO "credits" ("credit") VALUES ('Laboratorio di Cultura digitale, Dipartimento di Lettere e Filosofia, Università di Pisa');
INSERT INTO "credits" ("credit") VALUES ('Wikipedia, List of Greek phrases, https://en.wikipedia.org/wiki/List_of_Greek_phrases');
INSERT INTO "credits" ("credit") VALUES ('Francese, Christopher. Greek Core Vocabulary. Dickinson College Commentaries (2014). http://dcc.dickinson.edu/greek-core-list');
INSERT INTO "credits" ("credit") VALUES ('Revithiadou, A. (2018) Ancient Greek pitch accent');

INSERT INTO "app_notes" ("title","note") VALUES ('about','Hippopotamuses are located in central and southern Africa, south of the Sahara desert and near the Nile River.</br></br><b>Diet</b></br>They are vegetarians and mostly eat grass. They may travel long distances during the night from their water source to reach the areas where they graze. They also sometimes eat fruit that has fallen off of trees or crops grown by local people.');

INSERT INTO "schermate" ("_id","description") VALUES (1,'parossìtone due sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (3,'τόπος');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (4,'φίλος');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (5,'λόγος');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (3,2,'Luogo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (4,2,'Caro, amico');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (5,2,'parola, linguaggio, discorso. pensiero, ragione, conto');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,3,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,4,2);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,5,3);

INSERT INTO "schermate" ("_id","description") VALUES (2,'proparossìtone tre sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription") VALUES (6,'ἄνθρωπος','ántʰrɔɔpós');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (7,'ἄγγελος');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (6,2,'Uomo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (7,2,'Messaggero');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,6,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (2,7,2);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (3,'Fun Fact','Plutarch, De garrulitate, 17');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (8,'αἴκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (8,2,'se (ipotetico) (solo in dialetto dorico)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (3,8);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (3,1,'αἴκα / αἴκε / αἴ κα / αἴ κε dor. / om. eἴκε for εἰ+ἄν > ἐάν');
INSERT INTO "easter_egg_comments" ("schermata_id","language_id","eeComment") VALUES (3,1,'Plutarch reports that Phillip II of Macedon sent word to the Spartans, saying that "if I should invade Laconia, I shall drive you out." The Spartans laconically responded with "if."');

INSERT INTO "schermate" ("_id","description") VALUES (4,'I declinazione in α puro');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (9,'χώρα');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (10,'μοῖρα');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (11,'ἡμέρα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (9,2,'regione');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (10,2,'destino');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (11,2,'giorno');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,11,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,9,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (4,10,1);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (5,'Incipits','Apollodoro, I.1.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (13,'Οὐρανὸς πρῶτος τοῦ παντὸς ἐδυνάστευσε κόσμου.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (13,2,'Urano ..per primo cosmo ..');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (5,13);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (5,1,'τοῦ κόσμου GEN sg (m? decl?)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (6,'εἰς Τάρταρον','Apollodoro, I.1.2');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (14,'τόπος δὲ οὖτος ἐρεβώδης ἐστὶν ἐν Ἅιδου, τοσοῦτον ἀπὸ γῆς ἔχων διάστημα ὅσον ἀπ᾿ οὐραωοῦ γῆ.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (14,2,'A dark place in the Ade, as far from Earth as Earth is from heaven');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (6,14);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (6,1,'εἰς Τάρταρον, εἰς + Acc');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (7,'The start of Time','Apollodoro, I.1.3');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (15,'..Κρόνον');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (15,2,'..Cronos..');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (7,15);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (8,'Todos','Apollodoro, I.2.1 Zeus, Plutone, Poseidone');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (16,'..Zeus, Plutone, Poseidone');
INSERT INTO "schermate" ("_id","description","author_ref") VALUES (9,'Todos','Apollodoro, I.3.1 Irene, Dike e le Moire');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (17,'..Irene, Dike e le Moire');
INSERT INTO "schermate" ("_id","description","author_ref") VALUES (10,'The North Wind and the Sun','..');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (18,'..The North Wind and the Sun');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (11,'Eureka','ARCHIMEDES (PLUTARCH 73.1094c)');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (19,'εὕρηκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (19,2,'ho trovato.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (11,19);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (11,2,'perfetto.. da εὑρίσκω, pf.');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (12,'GEN pl','Om. Iliad 6.128');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription") VALUES (12,'τὶς ἀθανάτων','tìs atʰanátɔɔn');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (12,2,'uno degli immortali');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (12,12);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (12,2,'τὶς ἀθανάτων: GEN pl');

INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (2,'ἀγαθός');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,2,'Buono, nobile');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,1,'Good, noble');

INSERT INTO "playlists" ("_id","description") VALUES (0,'Test playlist');


INSERT INTO "playlists" ("_id","description") VALUES (1,'Parole, vocabolario, declinazioni');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,4);

INSERT INTO "playlists" ("_id","description") VALUES (2,'Frasi celebri');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (2,3);