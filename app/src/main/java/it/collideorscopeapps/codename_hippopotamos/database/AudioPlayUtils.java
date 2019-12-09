package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;

public class AudioPlayUtils {

    static MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer player) {
            Log.v("QuoteActivity","Starting media player..");
            player.start();
            //TODO should we ensure playback is completed before relase? is .start asynch?
            //player.release();
            //player = null;
        }
    };

    public static void playAudioFile(MediaPlayer mediaPlayer,
                                     AssetManager assetManager,
                                     String audioFilePath) {

        try(AssetFileDescriptor assetFileDescriptor = assetManager.openFd(audioFilePath)) {

            AudioPlayUtils.insertFileIntoMediaplayer(mediaPlayer, assetFileDescriptor);

            mediaPlayer.setOnPreparedListener(onPreparedListener);

            mediaPlayer.prepareAsync();
        }
        catch (IOException e) {
            Log.e("QuoteActivity-audioplay", e.toString());
        }
    }

    public static void insertFileIntoMediaplayer(MediaPlayer mediaPlayer,
                                                  AssetFileDescriptor assetFileDescriptor)
            throws IOException {
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
