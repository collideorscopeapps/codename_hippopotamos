BEGIN TRANSACTION;
DROP TABLE IF EXISTS "greek_quotes";
CREATE TABLE IF NOT EXISTS "greek_quotes" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"quoteText"	TEXT,
	"EEcomment"	TEXT,
	"grAudioResFileName"	TEXT
);

DROP TABLE IF EXISTS "android_metadata";
CREATE TABLE IF NOT EXISTS "android_metadata" (
	"locale"	TEXT DEFAULT 'en_US'
);

DROP TABLE IF EXISTS "translation_languages";
CREATE TABLE IF NOT EXISTS "translation_languages" (
	"_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"LanguageName"	TEXT UNIQUE
);

DROP TABLE IF EXISTS "quotes_translations";
CREATE TABLE IF NOT EXISTS "quotes_translations" (
	"greek_quote_id"	INTEGER NOT NULL,
	"translation_language_id"	INTEGER NOT NULL,
	"translation"	TEXT,
	PRIMARY KEY("greek_quote_id","translation_language_id"),
	FOREIGN KEY("greek_quote_id") REFERENCES "greek_quotes"("_id")
	FOREIGN KEY("translation_language_id") REFERENCES "translation_languages"("_id")
);

INSERT INTO "greek_quotes" ("_id","quoteText","EEcomment","grAudioResFileName") VALUES (2,'ἀγαθός','just trying some words','');
INSERT INTO "android_metadata" ("locale") VALUES ('en_US');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (1,'English');
INSERT INTO "translation_languages" ("_id","LanguageName") VALUES (2,'Italian');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (2,2,'Buono');
INSERT INTO "quotes_translations" ("greek_quote_id","translation_language_id","translation") VALUES (2,1,'Good');


DROP VIEW IF EXISTS v_quotes_and_translations;
CREATE VIEW v_quotes_and_translations
AS
SELECT gq._id , tl.LanguageName AS translation_language, qt.translation AS translation, gq.quoteText AS quote, gq.EEcomment as EEcomment
FROM greek_quotes gq, quotes_translations qt, translation_languages tl
WHERE gq._id = qt.greek_quote_id AND translation_language_id = tl._id;

COMMIT;