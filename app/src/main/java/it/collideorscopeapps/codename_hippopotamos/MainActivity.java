package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.database.AsyncResponse;
import it.collideorscopeapps.codename_hippopotamos.database.DBManager;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    public static final int QUOTE_ACTIVITY = 100;

    private DBManager dbManager;
    TreeMap<Integer, Schermata> schermate;

    Button demoBtn;
    Button startPlayingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        // this should call on create

        this.demoBtn = this.findViewById(R.id.demoBtn);


        this.startPlayingBtn = this.findViewById(R.id.startPlayingBtn);
        this.startPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSlideActivity();
            }
        });

        //this.createDB();
        // TODO get chosen language from shared preferences
        //TODO ensure this is done asynch, not on UI thread
        schermate = dbManager.getSchermateById(DBManager.Languages.EN);
        //openQuoteActivity();

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

        intent.putExtra("schermate", schermate);
        startActivityForResult(intent, QUOTE_ACTIVITY);

        //FIXME probably a bug to do another activity start
        startActivity(intent);

        //FIXME reported issue that double back button give blank screen
        // probably is the returning into main activity, which is empty
    }

    void openSlideActivity() {
        Intent intent = new Intent(MainActivity.this,
                QuotePagerActivity.class);
        startActivity(intent);
    }
}
