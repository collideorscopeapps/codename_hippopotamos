package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import it.collideorscopeapps.codename_hippopotamos.Globals;
import it.collideorscopeapps.codename_hippopotamos.SharedTestUtils;
import it.collideorscopeapps.codename_hippopotamos.database.AudioPlayerHelper.PlayerState;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void stop() throws IOException{
        //TODO
        // get a media player
        // set it to all valid states for stop, stop it at each one,
        // check that no errors occur, and state is stopped
        // set it to all invalid states for stop, check that error/warning is reported
        // but no crash, and the resulting state is appropriate

        AudioPlayerHelper player = getIdleMP();
        assertEquals(PlayerState.IDLE,player._mediaPlayer.getCurrentPlayerState());

        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }

        assertEquals(PlayerState.IDLE,player._mediaPlayer.getCurrentPlayerState());

        player = getInitializedMP();
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());

        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());

        //NOTE: call to player.play() would result in prepareAcynh
        // which switches to the PREPARING transient state, any
        // call during this state is undefined

        player = getPreparedMP();
        assertEquals(PlayerState.PREPARED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        player = getStartedMP();
        assertEquals(PlayerState.PLAYING,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        player = getPausedMP();
        assertEquals(PlayerState.PAUSED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        //TODO test asynch for playback complete (or very short track and loop)
    }

    @Test
    public void stopFromInitialized() throws IOException{
        AudioPlayerHelper player = getInitializedMP();
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());
    }

    private AudioPlayerHelper getIdleMP() throws IOException {

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper();
        return audioPlayerHelper;
    }

    private AudioPlayerHelper getInitializedMP() throws IOException {

        AudioPlayerHelper audioPlayerHelper = getIdleMP();
        audioPlayerHelper.changeAudioFiles(getSingleAudioFileDescriptor(assetManager));

        return audioPlayerHelper;
    }

    private AudioPlayerHelper getPreparedMP() throws IOException {
        AudioPlayerHelper player = getInitializedMP();
        player._mediaPlayer.prepare();

        return player;
    }

    private AudioPlayerHelper getStartedMP() throws IOException {
        AudioPlayerHelper player = getPreparedMP();
        player._mediaPlayer.start();

        return player;
    }

    private AudioPlayerHelper getPausedMP() throws IOException {
        AudioPlayerHelper player = getStartedMP();
        player.pause();

        return player;
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

        AssetFileDescriptor file1 = getSingleAudioFileDescriptor(assetManager);
        AssetFileDescriptor file2;
        {
            String fileName2 = "Od.6.13-glaukopis.ogg";
            String filePath2 = Globals.getFilePath(fileName2,Globals.AUDIO_FILES_SUBFOLDER);
            file2 = getFileDescriptor(filePath2, assetManager);
        }

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(file1);
        audioPlayerHelper.play();
        audioPlayerHelper.changeAudioFiles(file2);
        audioPlayerHelper.play();
    }

    @Test
    public void resetWithFewerFilesOutOfBoundsException() throws IOException {

        //TODO FIXME this test still does not reproduce the error that was happening
        // error was in playNext(int trackIdx) with trackIdx out of bounds

        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(audioFiles);
        audioPlayerHelper.play();

        AssetFileDescriptor singleFile = getSingleAudioFileDescriptor(assetManager);

        audioPlayerHelper.changeAudioFiles(singleFile);
        audioPlayerHelper.play();


        audioPlayerHelper.changeAudioFiles(audioFiles);
        audioPlayerHelper.play();
    }

    @Test
    public void changeAudioFilesCheckState() throws IOException {
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);
        AssetFileDescriptor singleFile
                = getSingleAudioFileDescriptor(assetManager);


        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        audioPlayerHelper.changeAudioFiles(singleFile);

        AudioPlayerHelper.PlayerState expectedPlayerState
                = AudioPlayerHelper.PlayerState.INITIALIZED;
        AudioPlayerHelper.PlayerState actualPlayerState
                = SharedTestUtils.getCurrentPlayerState(audioPlayerHelper);

        assertEquals("Wrong player state",expectedPlayerState,actualPlayerState);
    }

    @Test
    public void playWithNoFiles() throws IOException {

        //TODO add test for calling playnext(int trackIdx) when idx out of bounds exception
        // (error setting variable currentTrackIdx

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper();
        audioPlayerHelper.play();
    }

    @Test
    public void playSingleFile() throws IOException {

        //TODO test name is misleading

        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        int playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }

    @Test
    public void playAfterStopped() throws IOException {

        AssetFileDescriptor singleFile
                = getSingleAudioFileDescriptor(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                singleFile);
        audioPlayerHelper.play();
        audioPlayerHelper.stop();
        audioPlayerHelper.play();
    }

    @Deprecated
    private static String getSingleAudioFilePath() {
        String singleAudioFileName = "Od.6.1-diosodisseus.ogg";
        String singleAudioFilePath = Globals.AUDIO_FILES_SUBFOLDER
                + "/" + singleAudioFileName;

        return singleAudioFilePath;
    }

    private static AssetFileDescriptor getSingleAudioFileDescriptor(
            AssetManager assetManager) {

        return getFileDescriptor(getSingleAudioFilePath(), assetManager);
    }

    private static AssetFileDescriptor getFileDescriptor(String path,
            AssetManager assetManager) {

        return Utils.getAssetFileDescriptor(path,
                assetManager);
    }

    private static AssetFileDescriptor[] getSomeAudioAssetFileDescriptors(
            AssetManager assetManager) {
        String[] paths = getSomeAudioFilesPaths();

        return Utils.getAssetFileDescriptors(paths,assetManager);
    }

    @Deprecated
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
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        assertEquals("files count",audioFiles.length,
                audioPlayerHelper.filesCount());

        audioPlayerHelper.close();
    }

    //TODO FIXME lifecycle of when calling mp.reset()
    // it should be change files/reset than play
    // no calls to reset right after play

    @Test
    public void playMoreFiles() throws IOException {

        AssetFileDescriptor singleAudioFile
                = getSingleAudioFileDescriptor(assetManager);
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        int playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioFiles = new AssetFileDescriptor[]{singleAudioFile};
        audioPlayerHelper.changeAudioFiles(audioFiles);
        audioPlayerHelper.play();

        playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }
}