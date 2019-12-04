package it.collideorscopeapps.codename_hippopotamos.database;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


// AsyncTask<Params, Progress, Result>.
public class CopyFileTask extends AsyncTask<String, Integer, Boolean> {


    public AsyncResponse delegate;


    public CopyFileTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    protected Boolean doInBackground(String... params) {

        String inputFileName = params[0];
        String outputFileName = params[1];

        // total size DB in bytes
        // from input stream, get total size before read
        // alternative: context.getAssets().openFd(DBManager.DB_NAME).getLength()
        double dbInputFileSize = new File(inputFileName).length();//path: "/android_assets/" + DBManager.DB_NAME

        boolean wasCopySuccessful = false;

        // TODO check if there is enough space in the SD card to copy the db

        try(InputStream dbAssetFileInputStream = new FileInputStream((inputFileName));//context.getAssets().open(DBManager.DB_NAME);
            OutputStream outputStream = new FileOutputStream(outputFileName)) {


            byte[] buffer = new byte[1024];
            int bytesReadCount;
            final int END_OF_FILE_CODE = -1;

            do {
                bytesReadCount = dbAssetFileInputStream.read(buffer);
                outputStream.write(buffer, 0, bytesReadCount);

                // we could update progress here (percent of copied file)
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

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean wasCopySuccessful) {

        //super.onPostExecute(result);

        delegate.processFinish(wasCopySuccessful);
    }
}
