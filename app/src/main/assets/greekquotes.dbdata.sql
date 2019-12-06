
INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (2,'ἀγαθός');

INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (3,'τόπος ');
INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (4,'φίλος');
INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (5,'λόγος');

INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (6,'ἄνθρωπος');
INSERT INTO "greek_quotes" ("_id", "quoteText") VALUES (7,'ἄγγελος');


INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');

INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (2,2,'Buono, nobile');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (2,1,'Good, noble');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (3,2,'Luogo');

INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (4,2,'Caro, amico');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (5,2,'parola, linguaggio, discorso. pensiero, ragione, conto');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (6,2,'Uomo');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (7,2,'Messaggero');

INSERT INTO "schermate" ("_id", "description") VALUES (1,'parossìtone due sillabe');
INSERT INTO "schermate" ("_id", "description") VALUES (2,'proparossìtone tre sillabe');

INSERT INTO "quotes_in_schermate" ("schermata_id", "greek_quote_id", "position") VALUES (1,3, 1);
INSERT INTO "quotes_in_schermate" ("schermata_id", "greek_quote_id", "position") VALUES (1,4, 2);
INSERT INTO "quotes_in_schermate" ("schermata_id", "greek_quote_id", "position") VALUES (1,5, 3);
INSERT INTO "quotes_in_schermate" ("schermata_id", "greek_quote_id", "position") VALUES (2,6, 1);
INSERT INTO "quotes_in_schermate" ("schermata_id", "greek_quote_id", "position") VALUES (2,7, 2);
