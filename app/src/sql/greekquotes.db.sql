BEGIN TRANSACTION;
DROP VIEW IF EXISTS v_quotes_and_translations;
DROP TABLE IF EXISTS "quotes_translations";
DROP TABLE IF EXISTS "translation_languages";
DROP TABLE IF EXISTS "android_metadata";
DROP TABLE IF EXISTS "greek_quotes";

CREATE TABLE IF NOT EXISTS "greek_quotes" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"quoteText"	TEXT,
	"EEcomment"	TEXT,
	"grAudioResFileName"	TEXT
);

CREATE TABLE IF NOT EXISTS "android_metadata" (
	"locale"	TEXT DEFAULT 'en_US'
);

CREATE TABLE IF NOT EXISTS "translation_languages" (
	"_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"LanguageName"	TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS "quotes_translations" (
	"greek_quote_id"	INTEGER NOT NULL,
	"translation_language_id"	INTEGER NOT NULL,
	"translation"	TEXT,
	PRIMARY KEY("greek_quote_id","translation_language_id"),
	FOREIGN KEY("greek_quote_id") REFERENCES "greek_quotes"("_id"),
	FOREIGN KEY("translation_language_id") REFERENCES "translation_languages"("_id")
);

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
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (5,2,'Parola');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (6,2,'Uomo');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (7,2,'Messaggero');

CREATE VIEW v_quotes_and_translations
AS
SELECT gq._id , tl.LanguageName AS translation_language, gq.quoteText AS quote, qt.translation AS translation, gq.EEcomment as EEcomment
FROM greek_quotes gq, quotes_translations qt, translation_languages tl
WHERE gq._id = qt.greek_quote_id AND translation_language_id = tl._id
ORDER BY translation_language;

COMMIT;