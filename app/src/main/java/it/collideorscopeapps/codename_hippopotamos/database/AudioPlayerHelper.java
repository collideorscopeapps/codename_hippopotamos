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

    enum PlayerState { UNKNOWN, IDLE, INITIALIZED,
        PREPARING, PREPARED, PLAYING, COMPLETED, STOPPED, ERROR,
        END_RELEASED_UNAVAILABLE
    }

    private class MediaPlayerWrapper extends MediaPlayer {

        private static final String TAG = "MediaPlayerWrapper" ;

        private PlayerState currentPlayerState;

        public MediaPlayerWrapper() {
            super();

            setCurrentPlayerState(PlayerState.UNKNOWN);
        }

        public PlayerState getCurrentPlayerState() {
            return currentPlayerState;
        }

        private void setCurrentPlayerState(PlayerState state) {
            Log.v(TAG,"Going from " + getCurrentPlayerState()
                    + " to " + state);
            this.currentPlayerState = state;
        }

        public void setListeners(OnPreparedListener onPreparedListener,
                                 OnCompletionListener onCompletionListener,
                                 OnErrorListener onErrorListener) {

            this.setOnPreparedListener(onPreparedListener);
            this.setOnCompletionListener(onCompletionListener);
            this.setOnErrorListener(onErrorListener);
        }

        public void setDefaultAudioAttributes() {
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                AudioAttributes DEFAULT_AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();

                this.setAudioAttributes(DEFAULT_AUDIO_ATTRIBUTES);
            }
            else {
                //mediaPlayer.setAudioStreamType(..);
            }
        }

        public boolean isStateValidForPrepareAsynch() {
            return (_mediaPlayer.getCurrentPlayerState() == PlayerState.INITIALIZED)
                    || (_mediaPlayer.getCurrentPlayerState() == PlayerState.STOPPED);
        }

        public boolean isStateInValidForStop() {
            return currentPlayerState == PlayerState.IDLE
                    || currentPlayerState == PlayerState.INITIALIZED
                    || currentPlayerState == PlayerState.ERROR;
        }

        @Override
        public void prepareAsync() throws IllegalStateException {
            super.prepareAsync();
            setCurrentPlayerState(PlayerState.PREPARING);
        }

        @Override
        public void stop() throws IllegalStateException {
            super.stop();
            setCurrentPlayerState(PlayerState.STOPPED);
        }

        @Override
        public void release() {
            super.release();
            setCurrentPlayerState(PlayerState.END_RELEASED_UNAVAILABLE);
        }

        @Override
        public void reset() {
            super.reset();
            setCurrentPlayerState(PlayerState.IDLE);
        }

        @Override
        public void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalArgumentException, IllegalStateException {
            super.setDataSource(fd, offset, length);

            //this method is for compatibility with SDK API < 24
            setCurrentPlayerState(PlayerState.INITIALIZED);
        }

        @Override
        public void setDataSource(String path) throws IOException,
                IllegalArgumentException, IllegalStateException, SecurityException {
            super.setDataSource(path);

            //this method works only on SDK API >= 24
            setCurrentPlayerState(PlayerState.INITIALIZED);
        }
    }

    private class MediaPlayerWrapperOneFileAtATime extends MediaPlayerWrapper {

    }

    private class MediaPlayerWrapperMultipleFiles
            extends MediaPlayerWrapperOneFileAtATime {

    }

    //TODO maybe there is a method to check mediaplayer actual internal state
    //it geve this errors:
    //E/MediaPlayerNative: stop called in state 4, mPlayer(0xebf809a0)
    //E/MediaPlayerNative: stop called in state 0, mPlayer(0xebf809a0)
    //E/MediaPlayerNative: attachNewPlayer called in state 4
    //E/AudioPlayerHelper: java.lang.IllegalStateException

    public static final String TAG = "AudioPlayerHelper";

    AssetManager assetManager;

    AssetFileDescriptor[] assetFileDescriptors;

    public int filesCount() {
        if(Utils.isNullOrEmpty(assetFileDescriptors)) {
            return 0;
        }

        return assetFileDescriptors.length;
    }

    private int getCurrentTrackIdx() {
        return _currentTrackIdx;
    }

    private int getLastTrackIdx() {
        return filesCount() - 1;
    }

    public void initCurrentTrackIdx() {
        this._currentTrackIdx = 0;
    }

    private void incrementCurrentTrackIdx() {
        this._currentTrackIdx++;

        if(this._currentTrackIdx >= filesCount()) {
            initCurrentTrackIdx();
        }
    }

    private int _currentTrackIdx;


    public boolean firstFilePlayedAtLeastOnce;

    public boolean hasLastFilePlayed() {
        return _lastFileHasPlayed;
    }

    public void setLastFileHasPlayed(boolean hasPlayed) {
         this._lastFileHasPlayed = hasPlayed;
    }

    private boolean _lastFileHasPlayed;

    MediaPlayerWrapper _mediaPlayer;

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

            _mediaPlayer.setCurrentPlayerState(PlayerState.PREPARED);
            Log.v(TAG,"Starting media player..");
            player.start();
            _mediaPlayer.setCurrentPlayerState(PlayerState.PLAYING);
            // handle events during playback?
        }
    };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            _mediaPlayer.setCurrentPlayerState(PlayerState.COMPLETED);
            //FIXME this stop() might be causing the error:
            //E/MediaPlayer: stop called in state 0
            //E/MediaPlayer: error (-38, 0)
            //mp.stop();
            //_mediaPlayer.setCurrentPlayerState(PlayerState.STOPPED);
            //FIXME: after removing stop, causes:
            //D/AudioPlayerHelper: Play request non accepted state, ignoring. State: COMPLETED

            //NB from completed, to play again whole loop,
            // call start()

            //FIXME: not playing file after first play
            // seems to be assigned incorrectly)
            setLastFileHasPlayed(getCurrentTrackIdx() == getLastTrackIdx());
            if(!hasLastFilePlayed()) {
                //play next
                mp.reset();
                _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);
                incrementCurrentTrackIdx();
                playNext(getCurrentTrackIdx());
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

        this.changeAudioFiles(audioFilePaths);
    }

    public void changeAudioFiles(String newAudioFilePath) throws IOException {
        changeAudioFiles(new String[]{newAudioFilePath});
    }

    //TODO test when passing null argument, was raising exception
    //TODO test because creation of asset file descriptor has been
    // postponed to end of method
    public void changeAudioFiles(String[] newAudioFilePaths) throws IOException {

        CloseAssetFileDescriptors();

        initCurrentTrackIdx();
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

        this._mediaPlayer = new MediaPlayerWrapper();
        _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);

        this._mediaPlayer.setListeners(onPreparedListener,
                onCompletionListener,onErrorListener);

        this._mediaPlayer.setDefaultAudioAttributes();
    }

    private void playNext(int trackIdx) {

        if(assetFileDescriptors.length <= trackIdx) {
            //TODO FIXME this sometimes happen, should handle propertly and add tests
            // possibly because inconsistent state after changing audio files (fewer)
            Log.e(TAG,"Track index out of bounds " + assetFileDescriptors);
            return;
        }

        //TODO-FIXME handle if assetFileDescriptors is empty
        // and get ArrayIndexOutOfBoundsException

        //setDataSource, if ok state goes from idle to initialized
        //TODO FIXME this method call is misleading
        tryInsertFileIntoMediaplayer(assetFileDescriptors[trackIdx]);

        //if ok state goes to PREPARING
        tryPrepareAsynch();
    }

    private void tryPrepareAsynch() {

        if(_mediaPlayer.isStateValidForPrepareAsynch()) {
            try {
                _mediaPlayer.prepareAsync();
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
                && _mediaPlayer.getCurrentPlayerState() == PlayerState.IDLE) {
            // first playback
            this.firstFilePlayedAtLeastOnce = true;
            initCurrentTrackIdx();
            Log.d(TAG,"Play request accepted, first play or idle");
            playNext(getCurrentTrackIdx());
        } else
        if(_mediaPlayer.getCurrentPlayerState() == PlayerState.STOPPED
                ) { //removed: && hasLastFilePlayed() which was causing a bug
            //TODO FIXME distinguish between playing the initial series of files
            // or calling changeAudioFiles/reset because we need to change files
            // but changeAudioFiles/reset is usually already called by setting the new files

            // we start the loop of audio tracks again
            initCurrentTrackIdx();
            Log.d(TAG,"Play request accepted from stopped");
            playNext(getCurrentTrackIdx());
        } else {
            String msg = "Play request non accepted state, ignoring. State: "
                    + _mediaPlayer.getCurrentPlayerState();
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

        if(_mediaPlayer.isStateInValidForStop()) {
            String msg = "Stop request from non accepted state, ignoring. State: "
                    + _mediaPlayer.getCurrentPlayerState();
            Log.e(TAG,msg);
        } else {
            this._mediaPlayer.stop();
            //setLastFileHasPlayed(false);
            initCurrentTrackIdx();
        }
    }

    @Override
    public void close() throws IOException {

        this._mediaPlayer.release();
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
        // call to mp.reset() should be valid in any state
        this._mediaPlayer.reset();
        if(_mediaPlayer.getCurrentPlayerState() != PlayerState.IDLE)
        {
            Log.e(TAG,"Invalid attempt for setDataSource from state"
                    + _mediaPlayer.getCurrentPlayerState());
            return;
        }

        try {
            if(android.os.Build.VERSION.SDK_INT >= 24) {
                _mediaPlayer.setDataSource(assetFileDescriptor);
            }
            else {
                FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
                long offset = assetFileDescriptor.getStartOffset();
                long length = assetFileDescriptor.getLength();
                _mediaPlayer.setDataSource(
                        fileDescriptor,
                        offset,
                        length);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG,e.toString());
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        } catch (IllegalStateException e) {
            //TODO should we change to error state?
            //_mediaPlayer.setCurrentPlayerState(PlayerState.ERROR)
            Log.e(TAG,e.toString());
        }
    }
}
