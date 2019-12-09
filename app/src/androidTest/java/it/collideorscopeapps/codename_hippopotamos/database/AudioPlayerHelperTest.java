package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AudioPlayerHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @After@Suppress
    public void tearDown() throws Exception {
    }

    @Test@Suppress
    public void resetAndPlay() throws IOException {

    }


    @Test
    public void playSingleFile() throws IOException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String singleAudioFileName = "heliosf.ogg";
        String audioFilesSubFolder = "audio/";
        String singleAudioFilePath = audioFilesSubFolder + singleAudioFileName;
        String[]audioFilePathsNames = new String[]{singleAudioFilePath};

        AssetManager assetManager = appContext.getAssets();

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, audioFilePathsNames);
        audioPlayerHelper.play();

        int playAttempts = 150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }

    @Test
    public void playMoreFiles() throws IOException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String singleAudioFileName = "hhmeraf.ogg";
        String[] audioFileNames = new String[]{"xwraf.ogg", "logosf.ogg", "nomosf.ogg"};
        String[] audioFilePathsNames = new String[audioFileNames.length];
        String audioFilesSubFolder = "audio/";
        String singleAudioFilePath = audioFilesSubFolder + singleAudioFileName;

        int idx = 0;
        for(String fileName:audioFileNames) {
            audioFilePathsNames[idx] = audioFilesSubFolder + fileName;
            idx++;
        }

        AssetManager assetManager = appContext.getAssets();

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, audioFilePathsNames);
        audioPlayerHelper.play();

        int playAttempts = 150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioFilePathsNames = new String[]{singleAudioFilePath};
        audioPlayerHelper.reset(audioFilePathsNames);
        audioPlayerHelper.play();

        playAttempts = 150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }

    @Test@Suppress
    public void close() {
    }
}