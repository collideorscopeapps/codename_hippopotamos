package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

public class AudioPlayerHelper implements Closeable {

    //TODO maybe there is a method to check mediaplayer actual internal state
    //it geve this errors:
    //E/MediaPlayerNative: stop called in state 4, mPlayer(0xebf809a0)
    //E/MediaPlayerNative: stop called in state 0, mPlayer(0xebf809a0)
    //E/MediaPlayerNative: attachNewPlayer called in state 4
    //E/AudioPlayerHelper: java.lang.IllegalStateException

    public static final String TAG = "AudioPlayerHelper";

    AssetManager assetManager;

    AssetFileDescriptor[] assetFileDescriptors;
    int currentTrackIdx;
    public boolean firstFilePlayedAtLeastOnce;

    public boolean hasLastFilePlayed() {
        return _lastFileHasPlayed;
    }

    public void setLastFileHasPlayed(boolean hasPlayed) {
         this._lastFileHasPlayed = hasPlayed;
    }

    private boolean _lastFileHasPlayed;

    MediaPlayer mediaPlayer;
    enum PlayerState { UNKNOWN, IDLE, INITIALIZED,
        PREPARING, PREPARED, PLAYING, COMPLETED, STOPPED, ERROR }
    PlayerState currentPlayerState = PlayerState.UNKNOWN;

    static MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            Log.e(TAG,"what: " + what
                    + " extra: " + extra + " " + mp.toString());

            return false;
        }
    };

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer player) {

//            player.isPlaying();
//            player.setNextMediaPlayer();
//            player.setOnCompletionListener();
//
//            player.setOnErrorListener();
//            player.selectTrack

            currentPlayerState = PlayerState.PREPARED;
            Log.v("QuoteActivity","Starting media player..");
            player.start();
            currentPlayerState = PlayerState.PLAYING;
            // handle events during playback?
        }
    };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            currentPlayerState = PlayerState.COMPLETED;
            mp.stop();
            currentPlayerState = PlayerState.STOPPED;

            //FIXME: not playing file after first play
            // seems to be assigned incorrectly)
            setLastFileHasPlayed(currentTrackIdx == assetFileDescriptors.length-1);
            if(!hasLastFilePlayed()) {
                //play next
                mp.reset();
                currentPlayerState = PlayerState.IDLE;
                currentTrackIdx++;
                playNext(currentTrackIdx);
            }
            else {
                // we stay in stopped state. if activity changes screens..
                // setLastFileHasPlayed(true);
                // TODO ..
                // ..
                // only now we can accept another call to play()
                // otherwise we ignore them
            }
            // we should check if we should play next track
            // or if we have already played the last one

            //TODO
            // release would be only when quitting activity
            // not when changing to other screen with other audio quotes
        }
    };

    public AudioPlayerHelper(AssetManager assetManager) throws IOException {
        this(assetManager,(String[])null);
    }

    public AudioPlayerHelper(AssetManager assetManager,
                             String audioFilePath)  throws IOException {
        this(assetManager, new String[]{audioFilePath});
    }

    public AudioPlayerHelper(AssetManager assetManager,
                             ArrayList<String> audioFilePaths) throws IOException {

        this(assetManager, Utils.toArray(audioFilePaths));
    }

    public AudioPlayerHelper(AssetManager assetManager,
                             String[] audioFilePaths) throws IOException {

        setUpMediaPlayer();
        this.assetManager = assetManager;

        this.reset(audioFilePaths);
    }

    public void reset(String newAudioFilePath) throws IOException {
        reset(new String[]{newAudioFilePath});
    }

    //TODO test when passing null argument, was raising exception
    //TODO test because creation of asset file descriptor has been
    // postponed to end of method
    public void reset(String[] newAudioFilePaths) throws IOException {

        CloseAssetFileDescriptors();

        this.currentTrackIdx = 0;
        this.firstFilePlayedAtLeastOnce = false;
        this.setLastFileHasPlayed(false);

        if(newAudioFilePaths != null) {
            this.assetFileDescriptors = getAssetFileDescriptors(newAudioFilePaths,
                    this.assetManager);
        }
    }

    private static AssetFileDescriptor[] getAssetFileDescriptors(
            String[] audioFilePaths,
            AssetManager assetManager)
            throws IOException {

        //TODO fixme: handle/filter null/empty filePaths or having no asset

        AssetFileDescriptor[] tmpAssetFileDescriptors;
        tmpAssetFileDescriptors = new AssetFileDescriptor[audioFilePaths.length];
        for(int idx = 0; idx<audioFilePaths.length; idx++) {
            String audioFilePath = audioFilePaths[idx];
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(audioFilePath);
            tmpAssetFileDescriptors[idx] = assetFileDescriptor;
        }

        return tmpAssetFileDescriptors;
    }

    private void setUpMediaPlayer() {

        this.mediaPlayer = new MediaPlayer();
        this.currentPlayerState = PlayerState.IDLE;

        mediaPlayer.setOnPreparedListener(onPreparedListener);
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setOnErrorListener(onErrorListener);

        if(android.os.Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            mediaPlayer.setAudioAttributes(audioAttributes);
        }
        else {
            //mediaPlayer.setAudioStreamType(..);
        }
    }

    private void playNext(int trackIdx) {

        if(assetFileDescriptors.length <= trackIdx) {
            Log.e(TAG,"Track index out of bounds " + assetFileDescriptors);
            //return;
        }

        //TODO-FIXME handle if assetFileDescriptors is empty
        // and get ArrayIndexOutOfBoundsException

        //setDataSource, if ok state goes from idle to initialized
        tryInsertFileIntoMediaplayer(assetFileDescriptors[trackIdx]);

        //if ok state goes to PREPARING
        tryPrepareAsynch();
    }

    private void tryPrepareAsynch() {

        boolean isValidStateForPrepareAsynch
                = (this.currentPlayerState == PlayerState.INITIALIZED)
                || (this.currentPlayerState == PlayerState.STOPPED);

        if(isValidStateForPrepareAsynch) {
            try {
                mediaPlayer.prepareAsync();
                currentPlayerState = PlayerState.PREPARING;
            } catch (IllegalStateException e) {
                Log.e(TAG,e.toString());
            }
        }
    }

    public void play() {

        if(Utils.isNullOrEmpty(this.assetFileDescriptors)) {
            Log.e(TAG,"No files to play");
            return;
        }

        if(!this.firstFilePlayedAtLeastOnce
                && currentPlayerState == PlayerState.IDLE) {
            // first playback
            this.firstFilePlayedAtLeastOnce = true;
            this.currentTrackIdx = 0;
            Log.d(TAG,"Play request accepted, first play or idle");
            playNext(currentTrackIdx);
        } else
        if(currentPlayerState == PlayerState.STOPPED
                ) { //removed: && hasLastFilePlayed() which was causing a bug
            // we start the loop of audio tracks again
            this.currentTrackIdx = 0;
            Log.d(TAG,"Play request accepted from stopped");
            playNext(currentTrackIdx);
        } else {
            String msg = "Play request non accepted state, ignoring. State: "
                    + currentPlayerState;
            Log.d(TAG,msg);
        }

        // gets a series of audio files (asset file descriptors)
        // sets the first one into media player with setDataSource
        // starts prepareAsync

        //in the handler onPrepared, play the sound player.start();

        // onCompletion , (stop) set the next file on the list (unless is the last one),
        // and call prepare asynch
        // if it's the last one set some variable, lastFilePlayed = true
        // in the handler onPrepared, if(lastFilePlayed) do nothing
        // (add a method 'playAgain', callable externally)
        // if(!lastFilePlayed) call 'playAgain'
        // in 'playAgain', to avoid errors, check if isPlaying (than to nothing) and other states

        // keep in a variable the supposed current state of the media player, to avoid errors

        //add externally callable method close (closeable), from which we release the media player
    }

    public void stop() {

        //accepted states {Prepared, Started, Stopped, Paused, PlaybackCompleted}
        //non accepted states {Idle, Initialized, Error}

        if(currentPlayerState == PlayerState.IDLE
                || currentPlayerState == PlayerState.INITIALIZED
                || currentPlayerState == PlayerState.ERROR) {
            String msg = "Stop request from non accepted state, ignoring. State: "
                    + currentPlayerState;
            Log.e(TAG,msg);
        } else {
            this.mediaPlayer.stop();
            currentPlayerState = PlayerState.STOPPED;
            //setLastFileHasPlayed(false);
            currentTrackIdx=0;
        }
    }

    @Override
    public void close() throws IOException {

        this.mediaPlayer.release();
        CloseAssetFileDescriptors();
    }

    private void CloseAssetFileDescriptors() throws IOException {

        if(this.assetFileDescriptors == null) {
            return;
        }

        for (AssetFileDescriptor assetFileDescriptor:this.assetFileDescriptors) {
            try {
                if(assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
            } catch (IOException e) {
                Log.e(TAG,e.toString());
            }
        }
    }

    private void tryInsertFileIntoMediaplayer(AssetFileDescriptor assetFileDescriptor) {

        // this call to make sure we're not calling setDataSource in an invalid state
        // call to reset() should be valid in any state
        this.mediaPlayer.reset();
        this.currentPlayerState = PlayerState.IDLE;
        if(this.currentPlayerState != PlayerState.IDLE)
        {
            Log.e(TAG,"Invalid attempt for setDataSource from state"
                    + this.currentPlayerState);
            return;
        }

        try {
            if(android.os.Build.VERSION.SDK_INT >= 24) {
                mediaPlayer.setDataSource(assetFileDescriptor);
            }
            else {
                FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
                long offset = assetFileDescriptor.getStartOffset();
                long length = assetFileDescriptor.getLength();
                mediaPlayer.setDataSource(
                        fileDescriptor,
                        offset,
                        length);
            }

            this.currentPlayerState = PlayerState.INITIALIZED;

        } catch (IllegalArgumentException e) {
            Log.e(TAG,e.toString());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        } catch (IllegalStateException e) {
            //TODO should we change to error state?
            //this.currentPlayerState = PlayerState.ERROR
            Log.e(TAG,e.toString());
        }
    }
}
