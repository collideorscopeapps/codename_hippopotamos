package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class Utils {

    @Deprecated
    public static boolean copyDatabase(Context context) {

        boolean wasCopySuccessful = false;
        String outFileName = DBManager.DB_LOCATION + DBManager.DB_NAME;

        try(InputStream dbAssetFileInputStream = context.getAssets().open(DBManager.DB_NAME);
            OutputStream outputStream = new FileOutputStream(outFileName)) {

            //TODO check if there is enough space in the SD card to copy the db
            // TODO make sure that this copy is done in another thread than the main activity

            byte[] buffer = new byte[1024];
            int bytesReadCount;
            final int END_OF_FILE_CODE = -1;

            do {
                bytesReadCount = dbAssetFileInputStream.read(buffer);
                outputStream.write(buffer, 0, bytesReadCount);
            }
            while (bytesReadCount != END_OF_FILE_CODE);

            outputStream.flush();
            wasCopySuccessful = true;
            Log.v("DB Utils", "DB Copied");
        }
        catch (Exception e) {

        }

        return wasCopySuccessful;
    }

}
