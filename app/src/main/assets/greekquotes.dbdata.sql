INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');

INSERT INTO "credits" ("credit") VALUES ('Laboratorio di Cultura digitale, Dipartimento di Lettere e Filosofia, Università di Pisa');
INSERT INTO "credits" ("credit") VALUES ('Wikipedia, List of Greek phrases, https://en.wikipedia.org/wiki/List_of_Greek_phrases');
INSERT INTO "credits" ("credit") VALUES ('Francese, Christopher. Greek Core Vocabulary. Dickinson College Commentaries (2014). http://dcc.dickinson.edu/greek-core-list');
INSERT INTO "credits" ("credit") VALUES ('Revithiadou, A. (2018) Ancient Greek pitch accent');

INSERT INTO "app_notes" ("title","note") VALUES ('about','Hippopotamuses are located in central and southern Africa, south of the Sahara desert and near the Nile River.</br></br><b>Diet</b></br>They are vegetarians and mostly eat grass. They may travel long distances during the night from their water source to reach the areas where they graze. They also sometimes eat fruit that has fallen off of trees or crops grown by local people.');

INSERT INTO "news" ("title","link") VALUES ('Pablo Escobar''s hippos keep multiplying and Colombia doesn''t know how to stop it','https://www.cbsnews.com/news/pablo-escobars-hippos-keep-multiplying-and-colombia-doesnt-know-how-to-stop-it/');

INSERT INTO "schermate" ("_id","description") VALUES (1,'parossìtone due sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (3,'τόπος');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (4,'φίλος','filosf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (5,'λόγος','logosf.ogg');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (3,2,'Luogo');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (4,2,'Caro, amico');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (5,2,'parola, linguaggio, discorso; pensiero, ragione, conto');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,3,1);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,4,2);
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id","position") VALUES (1,5,3);

INSERT INTO "schermate" ("_id","description") VALUES (2,'proparossìtone tre sillabe');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription","audioFileName") VALUES (6,'ἄνθρωπος','ántʰrɔɔpós','anthrwpf.ogg');
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
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (9,'χώρα','xwraf.ogg');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (10,'μοῖρα');
INSERT INTO "greek_quotes" ("_id","quoteText","audioFileName") VALUES (11,'ἡμέρα','hhmeraf.ogg');
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
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (14,'τόπος δὲ οὖτος ἐρεβώδης ἐστὶν ἐν Ἅιδου, τοσοῦτον ἀπὸ γῆς ἔχων διάστημα ὅσον ἀπ᾿ οὐρανοῦ γῆ.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (14,2,'A dark place in the Ade, as far from Earth as Earth is from heaven');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (6,14);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (6,1,'εἰς Τάρταρον, εἰς + Acc');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (7,'The start of Time','Apollodoro, I.1.3');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (15,'τεκνοῖ δὲ αὖθις ἐκ Γῆς παῖδας μὲν τοὺς Τιτᾶνας προσαγορευθέντας, Ὠκεανὸν Κοῖον Ὑπερίονα Κρεῖον Ἰαπετὸν καὶ νεώτατον ἁπάντων Κρόνον');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (15,2,'And again he (Uranus, the sky) begat children by Earth, the Titans as they are named: Ocean, Coeus, Hyperion, Crius, Iapetus, and, youngest of all, Cronus (Time)');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (7,15);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (8,'Zeus, Pluto, Poseidon','Apollodoro, I.2.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (16,' καὶ Κύκλωπες τότε Διὶ μὲν διδόασι βροντὴν καὶ ἀστραπὴν καὶ κεραυνόν, Πλούτωνι δὲ κυνέην, Ποσειδῶνι δὲ τρίαιναν');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (16,1,'And the Cyclopes then gave Zeus thunder and lightning and a thunderbolt,4 and on Pluto they bestowed a helmet and on Poseidon a trident.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (8,16);

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (9,'Δίκην, Εἰρήνην, Εὐνομίαν','Apollodoro, I.3.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (17,'Εἰρήνην Εὐνομίαν Δίκην, μοίρας');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,2,'Irene, la Pace, Eunomia, l''Ordine, Dike, la Giustizia, e le Moire');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (17,1,'Peace, Order, Justice, and the Fates');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (9,17);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (9,1,'Εἰρήνην Εὐνομίαν Δίκην: ACC sg; μοίρας: ACC pl (f)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (10,'The North Wind and the Sun','..');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (18,'..The North Wind and the Sun');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (10,18)

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (11,'Eureka','ARCHIMEDES (PLUTARCH 73.1094c)');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (19,'εὕρηκα');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (19,2,'ho trovato.');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (11,19);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (11,1,'perfetto.. da εὑρίσκω, pf.');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (12,'GEN pl','Om. Iliad 6.128');
INSERT INTO "greek_quotes" ("_id","quoteText","phoneticTranscription") VALUES (12,'τὶς ἀθανάτων','tìs atʰanátɔɔn');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (12,2,'uno degli immortali');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (12,12);
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (12,1,'τὶς ἀθανάτων: GEN pl');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (13,'Thyrants','Talete 35');
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
INSERT INTO "linguistic_notes" ("schermata_id","language_id","linguisticNote") VALUES (15,1,'ἐν τῷ Ταρτάρῳ: DAT sg (m)');

INSERT INTO "schermate" ("_id","description","author_ref") VALUES (16,'Heavens, sea and Hades','Apollod. 1.2.1');
INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (22,'καὶ λαγχάνει Ζεὺς μὲν τὴν ἐν οὐρανῷ δυναστείαν, Ποσειδῶν δὲ τὴν ἐν θαλάσσῃ, Πλούτων δὲ τὴν ἐν Ἅιδου.');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (22,1,'and to Zeus was allotted the dominion of the sky, to Poseidon the dominion of the sea, and to Pluto the dominion in Hades');
INSERT INTO "quotes_in_schermate" ("schermata_id","greek_quote_id") VALUES (16,22);

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



INSERT INTO "greek_quotes" ("_id","quoteText") VALUES (2,'ἀγαθός');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,2,'Buono, nobile');
INSERT INTO "quotes_translations" ("greek_quote_id","language_id","translation") VALUES (2,1,'Good, noble');


INSERT INTO "playlists" ("_id","description") VALUES (1,'Parole, vocabolario, declinazioni');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,1);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,2);
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (1,4);

INSERT INTO "playlists" ("_id","description") VALUES (2,'Frasi celebri');
INSERT INTO "playlists_schermate" ("playlist_id","schermata_id") VALUES (2,3);