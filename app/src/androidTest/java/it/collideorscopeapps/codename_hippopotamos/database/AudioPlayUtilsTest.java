package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AudioPlayUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void playAudioFile() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String audioFileName = "logosf.ogg";
        String audioFilesSubFolder = "audio/";
        String audioFilePath = audioFilesSubFolder + audioFileName;

        AssetManager assetManager = appContext.getAssets();
        MediaPlayer mediaPlayer = new MediaPlayer();

        AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, audioFilePath);
    }

    @Test
    public void insertFileIntoMediaplayer() {
    }
}