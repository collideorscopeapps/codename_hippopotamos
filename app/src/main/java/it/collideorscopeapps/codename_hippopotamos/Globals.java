package it.collideorscopeapps.codename_hippopotamos;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

public class Globals {

    public static final String TAG = "Globals";
    public static final int PREFERRED_FONT_RESOURCE = R.font.literata;
    public static final String AUDIO_FILES_SUBFOLDER = "audio";
    public static final String FOLDER_SEPARATOR = "/";

    public static Typeface getPreferredTypeface(Context context) {
        return ResourcesCompat.getFont(
                context,
                PREFERRED_FONT_RESOURCE);
    }

    public static String getAudioAssetPath(AssetManager assetManager,
                                           Quote quote) {
        if(quote == null) {
            return null;
        }

        String quoteAudioFileName = quote.getAudioFileName();
        String folder = Globals.AUDIO_FILES_SUBFOLDER;
        String quoteAudioFilePath = getQuoteAudioFilePath(quote,folder);

        if(!Utils.isNullOrEmpty(quoteAudioFileName)) {

            if(Utils.assetExists(assetManager,quoteAudioFileName, folder)) {
                return quoteAudioFilePath;
            }
            else {

                Log.e(TAG,"No such asset: " + quoteAudioFilePath);
                String assetsNamesJoined = "";
                try {
                    String[] assetsNames = assetManager.list(folder);
                    assetsNamesJoined = Utils.joinString(assetsNames);
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                }

                Log.e(TAG,"Available assets in " + folder + ": "
                        + assetsNamesJoined);
            }
        }

        return null;
    }

    static String getQuoteAudioFilePath(Quote quote, String folder) {
        final String EMPTY_STRING = "";

        if(quote == null) {
            return EMPTY_STRING;
        }

        String shortQuoteAudioFilePath = folder
                + FOLDER_SEPARATOR
                + quote.getAudioFileName();

        return shortQuoteAudioFilePath;
    }
}
