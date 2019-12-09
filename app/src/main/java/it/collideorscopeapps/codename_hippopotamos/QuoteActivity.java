package it.collideorscopeapps.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class QuoteActivity extends AppCompatActivity {

    // todo, at activity startup, get schermata with "audio test"
    // iterate the quotes, get the audio files from assets folder
    // play the ogg vorbis files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
    }
}
