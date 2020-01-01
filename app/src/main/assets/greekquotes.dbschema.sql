PRAGMA user_version = 3;--/

CREATE TABLE IF NOT EXISTS "app_notes" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"title"	TEXT UNIQUE,
	"note" TEXT
);--/

CREATE TABLE IF NOT EXISTS "credits" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"credit"	TEXT
);--/

CREATE TABLE IF NOT EXISTS "news" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"title"	TEXT UNIQUE,
	"link" TEXT UNIQUE
);--/

CREATE TABLE IF NOT EXISTS "greek_quotes" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"quoteText"	TEXT,
	"phoneticTranscription" TEXT,
	"audioFileName"	TEXT,
	CONSTRAINT unique_quote UNIQUE (quoteText,audioFileName)
);--/

CREATE TABLE IF NOT EXISTS "android_metadata" (
	"locale"	TEXT DEFAULT 'en_US'
);--/

CREATE TABLE IF NOT EXISTS "translation_languages" (
	"_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"LanguageName"	TEXT UNIQUE
);--/

CREATE TABLE IF NOT EXISTS "quotes_translations" (
	"greek_quote_id"	INTEGER NOT NULL,
	"language_id"	INTEGER NOT NULL,
	"translation"	TEXT,
	PRIMARY KEY("greek_quote_id","language_id"),
	FOREIGN KEY("greek_quote_id") REFERENCES "greek_quotes"("_id"),
	FOREIGN KEY("language_id") REFERENCES "translation_languages"("_id")
);--/

CREATE TABLE IF NOT EXISTS "schermate" (
	"_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"title"	TEXT,
	"description"	TEXT,
	"author_ref" TEXT,
    "short_quote_id" INTEGER,
    "full_quote_id" INTEGER,
    FOREIGN KEY("short_quote_id") REFERENCES "greek_quotes"("_id"),
    FOREIGN KEY("full_quote_id") REFERENCES "greek_quotes"("_id")
);--/

CREATE TABLE IF NOT EXISTS "noun_declensions_screens" (
	"_id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"base_screen_id" INTEGER NOT NULL,
	"short_quote_id" INTEGER NOT NULL,
	"full_quote_id" INTEGER NOT NULL,
	FOREIGN KEY("base_screen_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("short_quote_id") REFERENCES "greek_quotes"("_id"),
	FOREIGN KEY("full_quote_id") REFERENCES "greek_quotes"("_id")
);--/

CREATE TABLE IF NOT EXISTS "schermate_greek_translations" (
	"schermata_id"	INTEGER NOT NULL,
	"language_id"	INTEGER NOT NULL,
	"translation"	TEXT,
	PRIMARY KEY("schermata_id","language_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("language_id") REFERENCES "translation_languages"("_id")
);--/

CREATE TABLE IF NOT EXISTS "quotes_in_schermate" (
	"greek_quote_id"	INTEGER NOT NULL,
	"schermata_id"	INTEGER NOT NULL,
	"position"  INTEGER NOT NULL DEFAULT 1,

	PRIMARY KEY("greek_quote_id","schermata_id"),
	FOREIGN KEY("greek_quote_id") REFERENCES "greek_quotes"("_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id")
);--/

CREATE TABLE IF NOT EXISTS "linguistic_notes" (
	"schermata_id"	INTEGER NOT NULL,
	"language_id"	INTEGER NOT NULL,
	"linguisticNote"	TEXT,
	PRIMARY KEY("schermata_id","language_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("language_id") REFERENCES "translation_languages"("_id")
);--/

CREATE TABLE IF NOT EXISTS "easter_egg_comments" (
	"schermata_id"	INTEGER NOT NULL,
	"language_id"	INTEGER NOT NULL,
	"eeComment"	TEXT,
	PRIMARY KEY("schermata_id","language_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("language_id") REFERENCES "translation_languages"("_id")
);--/

CREATE TABLE IF NOT EXISTS "schermate_descriptions_translations" (
	"schermata_id"	INTEGER NOT NULL,
	"language_id"	INTEGER NOT NULL,
	"description"	TEXT,
	PRIMARY KEY("schermata_id","language_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("language_id") REFERENCES "translation_languages"("_id")
);--/

CREATE TABLE IF NOT EXISTS "playlists" (
	"_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"description"   TEXT UNIQUE,
	"play_order" INTEGER,
	"disabled" BOOLEAN DEFAULT FALSE
);--/

CREATE TABLE IF NOT EXISTS "playlists_schermate" (
	"playlist_id"	INTEGER NOT NULL,
	"schermata_id"	INTEGER NOT NULL,
	"sorting"  INTEGER,
	PRIMARY KEY("schermata_id","playlist_id"),
	FOREIGN KEY("schermata_id") REFERENCES "schermate"("_id"),
	FOREIGN KEY("playlist_id") REFERENCES "playlists"("_id")
);--/

CREATE TRIGGER IF NOT EXISTS update_p_sorting
    AFTER INSERT ON playlists_schermate
    BEGIN
        UPDATE playlists_schermate
        SET sorting = (SELECT count(*) FROM playlists_schermate )
        WHERE playlist_id = NEW.playlist_id AND schermata_id = NEW.schermata_id;
    END;--/

CREATE VIEW v_quotes_and_translations AS
    SELECT gq._id AS quote_id, tl.LanguageName AS translation_language, gq.quoteText AS quote, qt.translation AS translation
    FROM greek_quotes gq, quotes_translations qt, translation_languages tl
    WHERE gq._id = qt.greek_quote_id AND qt.language_id = tl._id
    ORDER BY translation_language;--/

CREATE VIEW v_schermate_default_language AS
    SELECT *
    FROM schermate s
    LEFT JOIN linguistic_notes n ON n.schermata_id = s._id AND n.language_id = 1
    LEFT JOIN easter_egg_comments e ON e.schermata_id = s._id AND e.language_id = 1
    LEFT JOIN schermate_greek_translations gt ON gt.schermata_id = s._id AND gt.language_id = 1
;--/

CREATE VIEW v_schermate_with_notes_default_language AS
    SELECT *
    FROM schermate s, linguistic_notes n
    WHERE n.schermata_id = s._id AND n.language_id = 1
    UNION ALL
    SELECT *, NULL, NULL, NULL
    FROM schermate s
    WHERE NOT EXISTS (
        SELECT * FROM linguistic_notes AS n
        WHERE n.schermata_id = s._id AND n.language_id = 1)
    ;--/

CREATE VIEW v_schermate_with_notes_and_eec_default_language AS
    SELECT *
    FROM v_schermate_with_notes_default_language s, easter_egg_comments e
    WHERE e.schermata_id = s._id AND e.language_id = 1
    UNION ALL
    SELECT *, NULL, NULL, NULL
    FROM v_schermate_with_notes_default_language s
    WHERE NOT EXISTS (
        SELECT * FROM easter_egg_comments AS e
        WHERE e.schermata_id = s._id AND e.language_id = 1)
    ;--/

CREATE VIEW v_schermate_with_notes_eec_and_translations_default_language AS
    SELECT *
    FROM v_schermate_with_notes_and_eec_default_language s, schermate_greek_translations gt
    WHERE gt.schermata_id = s._id AND gt.language_id = 1
    UNION ALL
    SELECT *, NULL, NULL, NULL
    FROM v_schermate_with_notes_and_eec_default_language s
    WHERE NOT EXISTS (
        SELECT * FROM schermate_greek_translations AS gt
        WHERE gt.schermata_id = s._id AND gt.language_id = 1)
    ;--/

CREATE VIEW v_schermate_default_language2 AS
    SELECT *
    FROM v_schermate_with_notes_eec_and_translations_default_language;--/

CREATE VIEW v_schermate_and_quotes AS
    SELECT s._id AS s_id,
    gq._id AS gq_id,
    gq.quoteText AS quote,
    gq.phoneticTranscription AS phoneticTranscription,
    qs.position AS position,
    s.title AS title,
    s.description AS description,
    s.short_quote_id AS short_quote_id,
    s.full_quote_id AS full_quote_id,
    s.translation AS default_translation,
    s.author_ref AS cit,
    s.linguisticNote AS linguisticNote,
    s.eeComment AS eeComment,
    gq.audioFileName as audioFileName
    FROM greek_quotes gq,
    quotes_in_schermate qs,
    v_schermate_default_language s
    WHERE  qs.greek_quote_id = gq._id AND qs.schermata_id = s._id
    --FROM v_schermate_default_language s
    --LEFT JOIN quotes_in_schermate qs ON qs.schermata_id = s._id
    --LEFT JOIN greek_quotes gq ON gq._id = qs.greek_quote_id
    --ORDER BY s_id
    ;--/

CREATE VIEW v_schermate_grouped AS
     SELECT s._id AS s_id,
     GROUP_CONCAT(gq._id) AS gq_id,
     GROUP_CONCAT(gq.quoteText,', ') AS quote,
     GROUP_CONCAT( gq.phoneticTranscription,', ') AS phoneticTranscription,
     qs.position AS position,
     s.title AS title,
     s.description AS description,
     s.translation AS default_translation,
     s.author_ref AS cit,
     s.linguisticNote AS linguisticNote,
     s.eeComment AS eeComment,
     GROUP_CONCAT(gq.audioFileName) as audioFileName
     FROM greek_quotes gq,
     quotes_in_schermate qs,
     v_schermate_default_language s
     WHERE  qs.greek_quote_id = gq._id AND qs.schermata_id = s._id
     GROUP BY s_id
     ORDER BY s_id;--/

CREATE VIEW v_reading_list AS
    SELECT s_id, quote, cit, description
    FROM v_schermate_grouped;--/

CREATE VIEW v_playlists AS
    SELECT p._id AS p_id,
    p.description,
    p.disabled AS disabled,
    p.play_order AS playlist_rank,
    GROUP_CONCAT(ps.schermata_id) AS schermate,
    GROUP_CONCAT(ps.sorting) AS sorting
    FROM playlists p, playlists_schermate ps
    WHERE p._id = ps.playlist_id
    GROUP BY p_id
    ORDER BY playlist_rank
    --ORDER BY ps.sorting
    ;--/
