package it.collideorscopeapps.codename_hippopotamos;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;
import it.collideorscopeapps.codename_hippopotamos.model.Schermata;
import it.collideorscopeapps.codename_hippopotamos.utils.Utils;

public class Globals {

    public static final int PREFERRED_FONT_RESOURCE = R.font.literata;
    public static final String AUDIO_FILES_SUBFOLDER = "audio/";

    public static Typeface getPreferredTypeface(Context context) {
        return ResourcesCompat.getFont(
                context,
                PREFERRED_FONT_RESOURCE);
    }

    public static ArrayList<String> getAudioFilePathNames(
            AssetManager assetManager, Schermata screen) {

        ArrayList<String> audioFilePathsNames = new ArrayList<>();

        //TODO FIXME handling of missing quote files
        if(Utils.assetExists(assetManager,getShortQuoteAudioFilePath(screen))) {
            audioFilePathsNames.add(getShortQuoteAudioFilePath(screen));
        }

        if(Utils.assetExists(assetManager,getLongQuoteAudioFilePath(screen))) {
            audioFilePathsNames.add(getLongQuoteAudioFilePath(screen));
        }

        return audioFilePathsNames;
    }
    static String getShortQuoteAudioFilePath(Schermata screen) {

        return getQuoteAudioFilePath(screen.getShortQuote());
    }

    static String getLongQuoteAudioFilePath(Schermata screen) {

        return getQuoteAudioFilePath(screen.getFullQuote());
    }

    static String getQuoteAudioFilePath(Quote quote) {
        final String EMPTY_STRING = "";

        if(quote == null) {
            return EMPTY_STRING;
        }

        String shortQuoteAudioFilePath = Globals.AUDIO_FILES_SUBFOLDER
                + quote.getAudioFileName();

        return shortQuoteAudioFilePath;
    }
}
