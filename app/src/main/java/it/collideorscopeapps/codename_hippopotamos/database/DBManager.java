package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import it.collideorscopeapps.codename_hippopotamos.model.Playlist;
import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class DBManager {

    private static final String TAG = "DBManager";

    //..removed stuff

    SQLiteDatabase _myDB;

    // TODO for the audio files, not stored into the db, check how to have them in the sd card only
    // i.e. by downloading them
    // TODO check if there is a default "app data" dir in the sd card, or path conventions
    // TODO make unit/integration tests

    // TODO check how to handle/synch/check db version here and in the db created from sql file
    // TODO

    //..removed stuff

    //FIXME this failed on device, " unknown error (code 14): Could not open database"
    // SQLiteDatabase db = tryOpenDB(path,cf,flags);
    // seems fixed now, apparently was because of the static methods creating the db

    //TODO check if close causes errors if reopening

    //..removed stuff

    // ensureDBOpen
    //TODO check the need for this, add tests. was giving error
    // when db closed
    // when db file manually deleted before runnning tests

    //..removed stuff



    //..removed stuff



}
