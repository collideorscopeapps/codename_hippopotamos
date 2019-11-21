package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.openQuoteActivity();
    }

    void openQuoteActivity() {

        Intent intent = new Intent(MainActivity.this, QuoteActivity.class);

        startActivity(intent);
    }
}
