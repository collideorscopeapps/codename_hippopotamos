package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.AsyncResponse;
import it.collideorscopeapps.codename_hippopotamos.database.CopyFileTask;
import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private DBManager dbManager;
    TreeMap<Integer, Schermata> schermate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        // todo, after db copy, try to open db and query it

        //this.createDB();
        // TODO get choosen language from shared preferences
        schermate = dbManager.getSchermate(DBManager.Languages.EN);
        openQuoteActivity();
    }

    public void processFinish(Boolean wasCopySuccessful){

        if(wasCopySuccessful) {

            Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            Log.v("Main activity", "Copy database success, opening quote activity..");
            this.openQuoteActivity();
        }
        else
        {
            Toast.makeText(this, "Copy database ERROR", Toast.LENGTH_SHORT).show();
            Log.v("Main activity", "Copy database ERROR");
        }
    }

    void openQuoteActivity() {

        Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
        startActivity(intent);
    }
}
