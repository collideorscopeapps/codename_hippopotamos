package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import it.collideorscopeapps.codename_hippopotamos.Globals;

import static org.junit.Assert.assertEquals;

public class AudioPlayerHelperTest {

    AssetManager assetManager;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assetManager = appContext.getAssets();
    }

    @After@Suppress
    public void tearDown() throws Exception {
    }

    @Test@Ignore
    public void playerIllegalStates() {
        //TODO more tests for potential player illegal or incorrect states

        /*
        notes on possible bugged transitions:
        (logs from tests)
        IDLE to IDLE (maybe some redundancy)
        PREPARING to STOPPED (no intermediate play and completed states)
        STOPPED to COMPLETED (cant complete after stop)
        COMPLETED to IDLE (means a call to reset())
        missing log of transition from PLAYING to UNKNOWN (probably contructor called, other test)
        */
    }

    @Test
    public void resetAndPlay() throws IOException {

        String file1 = "Od.6.1-diosodisseus.ogg";
        String file2 = "Od.6.13-glaukopis.ogg";
        String filePath1 = Globals.getFilePath(file1,Globals.AUDIO_FILES_SUBFOLDER);
        String filePath2 = Globals.getFilePath(file2,Globals.AUDIO_FILES_SUBFOLDER);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, filePath1);
        audioPlayerHelper.play();
        audioPlayerHelper.changeAudioFiles(filePath2);
        audioPlayerHelper.play();
    }

    @Test
    public void resetWithFewerFilesOutOfBoundsException() throws IOException {

        //TODO FIXME this test still does not reproduce the error that was happening
        // error was in playNext(int trackIdx) with trackIdx out of bounds

        String[] filePaths = getSomeAudioFilesPaths();

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, filePaths);
        audioPlayerHelper.play();

        String singleAudioFileName = "Od.6.1-diosodisseus.ogg";
        String singleAudioFilePath = Globals.AUDIO_FILES_SUBFOLDER
                + "/" + singleAudioFileName;

        audioPlayerHelper.changeAudioFiles(singleAudioFilePath);
        audioPlayerHelper.play();

        audioPlayerHelper.changeAudioFiles(filePaths);
        audioPlayerHelper.play();
    }

    @Test
    public void playWithNoFiles() throws IOException {

        //TODO add test for calling playnext(int trackIdx) when idx out of bounds exception
        // (error setting variable currentTrackIdx

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager);
        audioPlayerHelper.play();
    }

    @Test
    public void playSingleFile() throws IOException {

        String singleAudioFileName = "Od.6.1-diosodisseus.ogg";
        String audioFilesSubFolder = "audio/";
        String singleAudioFilePath = audioFilesSubFolder + singleAudioFileName;
        String[]audioFilePathsNames = new String[]{singleAudioFilePath};

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
    public void playAfterStopped() throws IOException {
        String file1 = "Od.6.1-diosodisseus.ogg";
        String filePath1 = Globals.getFilePath(file1,Globals.AUDIO_FILES_SUBFOLDER);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, filePath1);
        audioPlayerHelper.play();
        audioPlayerHelper.stop();
        audioPlayerHelper.play();
    }

    private static String[] getSomeAudioFilesPaths() {

        String[] audioFilePathsNames;

        //FIXME TODO add more files
        //"xwraf.ogg", "logosf.ogg", "nomosf.ogg"};
        String[] audioFileNames = new String[]{
                "Od.6.13-glaukopis.ogg","Od.6.24-glaukopis.ogg","Od.6.41-glaukopis.ogg",
                "Od.6.6-oispheas.ogg", "Od.6.8-ekasandron.ogg",
                "Od.6.9-anphideteikos.ogg",
                "Od.6.9-kaiedeimato.ogg",
                "Od.6.10-kaineus.ogg"};
        audioFilePathsNames = new String[audioFileNames.length];
        String audioFilesSubFolder = Globals.AUDIO_FILES_SUBFOLDER;

        int idx = 0;
        for(String fileName:audioFileNames) {
            audioFilePathsNames[idx] = audioFilesSubFolder + "/" + fileName;
            idx++;
        }

        return audioFilePathsNames;
    }

    //TODO test for trackIdx
    @Test
    public void audioFileTrackIdx() throws IOException {
        String[] audioFilePathsNames = getSomeAudioFilesPaths();

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, audioFilePathsNames);
        audioPlayerHelper.play();

        assertEquals("files count",audioFilePathsNames.length,
                audioPlayerHelper.filesCount());

        audioPlayerHelper.close();
    }

    //TODO FIXME lifecycle of when calling mp.reset()
    // it should be change files/reset than play
    // no calls to reset right after play

    @Test
    public void playMoreFiles() throws IOException {

        String singleAudioFileName = "Od.6.1-diosodisseus.ogg";
        String singleAudioFilePath = Globals.AUDIO_FILES_SUBFOLDER
                + "/" + singleAudioFileName;
        String[] audioFilePathsNames = getSomeAudioFilesPaths();

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                assetManager, audioFilePathsNames);
        audioPlayerHelper.play();

        int playAttempts = 150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioFilePathsNames = new String[]{singleAudioFilePath};
        audioPlayerHelper.changeAudioFiles(audioFilePathsNames);
        audioPlayerHelper.play();

        playAttempts = 150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }
}