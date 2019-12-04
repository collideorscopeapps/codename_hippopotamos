package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import it.collideorscopeapps.codename_hippopotamos.database.AsyncResponse;
import it.collideorscopeapps.codename_hippopotamos.database.CopyFileTask;
import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.database.Utils;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private CopyFileTask copyFileTask;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        copyFileTask =  new CopyFileTask(this);

        // todo, after db copy, try to open db and query it

        this.createDB();

    }

    public void processFinish(Boolean wasCopySuccessful){

        if(wasCopySuccessful) {

            Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            this.openQuoteActivity();
        }
        else
        {
            Toast.makeText(this, "Copy database ERROR", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void createDB() {

        File database = getApplicationContext().getDatabasePath(DBManager.DB_NAME);

        //TODO also check if DB is older version, in that case copy it again
        if(false == database.exists()) {
            Log.v("Main activity", "DB doed not exits, trying to copy..");
            dbManager.getReadableDatabase();

            String[] copyDBParams = { "/android_assets/" + DBManager.DB_NAME,
                    DBManager.DB_LOCATION + DBManager.DB_NAME};

            copyFileTask.execute(copyDBParams);

        }
        else {
            Log.v("Main activity", "DB already exits");
        }
    }

    void openQuoteActivity() {

        Intent intent = new Intent(MainActivity.this, QuoteActivity.class);

        startActivity(intent);
    }
}
