package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

public class AudioPlayerHelper implements Closeable {

    AssetManager assetManager;

    AssetFileDescriptor[] assetFileDescriptors;
    int currentTrackIdx;
    public boolean firstFilePlayedAtLeastOnce;
    public boolean lastFileHasPlayed;

    MediaPlayer mediaPlayer;
    enum PlayerState { UNKNOWN, IDLE, INITIALIZED,
        PREPARING, PREPARED, PLAYING, COMPLETED, STOPPED, ERROR };
    PlayerState currentPlayerState = PlayerState.UNKNOWN;


    static MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            Log.e("AudioPlayerHelper","what: " + what
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

            lastFileHasPlayed = currentTrackIdx == assetFileDescriptors.length-1;
            if(!lastFileHasPlayed) {
                //play next
                mp.reset();
                currentPlayerState = PlayerState.IDLE;
                currentTrackIdx++;
                playNext(currentTrackIdx);
            }
            else {
                // we stay in stopped state. if activity changes screens..
                // lastFileHasPlayed = true;
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

    public AudioPlayerHelper(AssetManager assetManager,
                             String[] audioFilePaths) throws IOException {

        setUpMediaPlayer();
        this.assetManager = assetManager;

        this.reset(audioFilePaths);
    }

    public void reset(String[] newAudioFilePaths) throws IOException {

        CloseAssetFileDescriptors();

        this.assetFileDescriptors = getAssetFileDescriptors(newAudioFilePaths,
                this.assetManager);
        this.currentTrackIdx = 0;
        this.firstFilePlayedAtLeastOnce = false;
        this.lastFileHasPlayed = false;
    }

    private static AssetFileDescriptor[] getAssetFileDescriptors(
            String[] audioFilePaths,
            AssetManager assetManager)
            throws IOException {

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

        //TODO-FIXME handle if assetFileDescriptors is empty and get ArrayIndexOutOfBoundsException

        //setDataSource, state goes from idle to initialized
        try {
            insertFileIntoMediaplayer(mediaPlayer, assetFileDescriptors[trackIdx]);
            currentPlayerState = PlayerState.INITIALIZED;
        } catch (IOException e) {
            Log.e("AudioPlayerHelper", e.toString());
            currentPlayerState = PlayerState.ERROR;
        }

        mediaPlayer.prepareAsync();
        currentPlayerState = PlayerState.PREPARING;
    }

    public void play() {

        if(!this.firstFilePlayedAtLeastOnce
                && currentPlayerState == PlayerState.IDLE) {
            // first playback
            this.firstFilePlayedAtLeastOnce = true;
            this.currentTrackIdx = 0;
            playNext(currentTrackIdx);
        } else
        if(currentPlayerState == PlayerState.STOPPED
                && lastFileHasPlayed) {
            // we do one more loop
            this.currentTrackIdx = 0;
            playNext(currentTrackIdx);
        } else {
            String msg = "Play request non accepted state, ignoring. State: " + currentPlayerState;
            Log.e("AudioPlayerHelper",msg);
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
                Log.e("AudioPlayerHelper",e.toString());
            }
        }
    }


    public static void insertFileIntoMediaplayer(MediaPlayer mediaPlayer,
                                                 AssetFileDescriptor assetFileDescriptor)
            throws IOException {

        if(mediaPlayer == null) {
            throw new NullPointerException();
        }

        // this call to make sure we're not calling setDataSource in an invalid state
        mediaPlayer.reset();

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
    }
}
