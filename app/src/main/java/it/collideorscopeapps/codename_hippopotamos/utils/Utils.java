package it.collideorscopeapps.codename_hippopotamos.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import it.collideorscopeapps.codename_hippopotamos.model.Quote;

public class Utils {

    public static final String DEFAULT_STRING_SEPARATOR = ",";
    public static final String TAG = "Utils";

    public static Toast shortToast(Context context, String msg) {
        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast shortToast(Context context, String msg, Toast cancelMe) {

        if(cancelMe != null) {
            cancelMe.cancel();
        }

        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static boolean assetExists(AssetManager assetManager, String path) {

        boolean exists = false;
        try{
            exists = Arrays.asList(assetManager.list("")).contains(path);
        }
        catch (IOException e) {
            Log.e("QuoteFragment","Getting assets list: " + e.toString());
        }
        finally {
            return exists;
        }
    }

    public static String joinString(Iterable<String> strings) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(String s: strings) {
            sb.append(sep).append(s);
            sep = DEFAULT_STRING_SEPARATOR;
        }
        return sb.toString();
    }


    private static int SwitchTypeface(Context context,
                                      ArrayList<Typeface> typefaces,
                                      Map<String,String> typefaceNames,
                                      TextView tv,
                                      int currentTypefaceIdx,
                                      Toast lastToast) {

        Typeface currentTypeface = typefaces.get(currentTypefaceIdx);

        if(currentTypeface != null) {
            tv.setTypeface(currentTypeface);

            //Object fontTag = greekMainTitleTV.getFontFeatureSettings()
            //String fontAsString = fontTag.toString();
            //List<String> typefaceDescr = MainActivity.getKeyWithValue(currentTypeface);
            //String fontAsString = Utils.joinString(typefaceDescr);

            String fontAsString = typefaceNames.get(currentTypeface.toString());

            lastToast = Utils.shortToast(context,fontAsString);
        }
        else {
            lastToast = Utils.shortToast(context,"null typeface");
        }

        currentTypefaceIdx++;
        if(currentTypefaceIdx>=typefaces.size()) {
            currentTypefaceIdx = 0;
        }

        return currentTypefaceIdx;
    }

    private static void tryFonts(Context context,
                                 ArrayList<Typeface> typefaces,
                                 Map<String,String> typefaceNames,
                                 TextView tv,
                                 int currentTypefaceIdx,
                                 Toast lastToast) {
        {
            typefaceNames = new TreeMap<>();
            typefaces = new ArrayList<>();
            /*typefaces.add(Typeface.DEFAULT);
            typefaceNames.put(Typeface.DEFAULT.toString(),"Typeface.DEFAULT");
            typefaces.add(Typeface.SANS_SERIF);
            typefaceNames.put(Typeface.SANS_SERIF.toString(),"Typeface.SANS_SERIF");*/
            typefaces.add(Typeface.SERIF);
            typefaceNames.put(Typeface.SERIF.toString(),"Typeface.SERIF");//NotoSerif-Regular.ttf
            //typefaces.add(Typeface.MONOSPACE);
            //typefaceNames.put(Typeface.MONOSPACE.toString(),"Typeface.MONOSPACE");

            try {
                /*Typeface loraTypeface = ResourcesCompat.getFont(this, R.font.lora);
                typefaces.add(loraTypeface);
                typefaceNames.put(loraTypeface.toString(),"loraTypeface");
                Log.v(TAG, "typefaces: " + typefaces.size() + " " + typefaces.toString());

                Typeface gentiumTypeface = ResourcesCompat.getFont(this, R.font.gentium);
                typefaces.add(gentiumTypeface);
                typefaceNames.put(gentiumTypeface.toString(),"gentiumTypeface");
                Log.v(TAG, "typefaces: " + typefaces.size() + " " + typefaces.toString());*/

                int fontResourceId = 0;//R.font.noto

                Typeface notoTypeface = ResourcesCompat.getFont(context, fontResourceId);
                typefaces.add(notoTypeface);
                typefaceNames.put(notoTypeface.toString(),"notoTypeface");
                Log.v(TAG, "typefaces: " + typefaces.size() + " " + typefaces.toString());

            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
                Log.e(TAG, "typefaces: " + typefaces.size() + " " + typefaces.toString());
            }
        }
    }

    public static boolean isNullOrEmpty(String string) {

        return string == null || string.isEmpty();
    }

    public static boolean isNullOrEmpty(Quote quote) {

        if(quote == null) {
            return true;
        }

        String quoteTxt = quote.getQuoteText();
        return quoteTxt == null || quoteTxt.isEmpty();
    }

    public static String[] toArray(ArrayList<String> arrayList){
        ArrayList<String> nonEmptyValues = new ArrayList<>();
        for(String s:arrayList) {
            if(s != null) {
                nonEmptyValues.add(s);
            }
        }
        String[] valuesArray = new String[nonEmptyValues.size()];
        valuesArray = nonEmptyValues.toArray(valuesArray);

        return valuesArray;
    }
}
