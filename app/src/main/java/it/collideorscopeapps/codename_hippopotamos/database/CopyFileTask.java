package it.collideorscopeapps.codename_hippopotamos.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class CopyFileTask extends AsyncTask<CopyFileTask.Task, Integer, Boolean> {

    enum Task {
        CREATE_DB_FROM_SQL_FILE,
        COPY_DB_ASSET_FILE
    }

    public AsyncResponse delegate;
    AssetManager assetManager;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public CopyFileTask(AsyncResponse delegate,
                        AssetManager assetManager,
                        Context context,
                        SQLiteDatabase database) {

        this.delegate = delegate;
        this.assetManager = assetManager;
        this.context = context;
        this.sqLiteDatabase = database;
    }

    protected Boolean doInBackground(Task... params) {

        Task myTast = params[0];

        Boolean taskSuccessful = false;

        switch (myTast) {

            case COPY_DB_ASSET_FILE:
                taskSuccessful = copyDbAssetFile();
                break;
            case CREATE_DB_FROM_SQL_FILE:
                taskSuccessful = createDbFromSqlFile();
                break;
        }

        return taskSuccessful;
    }

    private Boolean createDbFromSqlFile() {

        return DBManager.createDBFromSqlFile(this.context, this.sqLiteDatabase);
    }

    @Deprecated
    private Boolean copyDbAssetFile() {

        final String DB_LOCATION = "/data/data" +
                "/it.collideorscopeapps.codename_hippopotamos" +
                "/databases/";
        String outputFileName = DB_LOCATION + DBManager.DB_NAME;

        boolean wasCopySuccessful = false;

        // TODO check if there is enough space in the SD card to copy the db

        try(InputStream dbAssetFileInputStream = assetManager.open(DBManager.DB_NAME);
            OutputStream outputStream = new FileOutputStream(outputFileName)) {


            byte[] buffer = new byte[1024];
            int lastBytesReadCount = 0;
            final int END_OF_FILE_CODE = -1;

            while (lastBytesReadCount != END_OF_FILE_CODE) {
                lastBytesReadCount = dbAssetFileInputStream.read(buffer);

                if(lastBytesReadCount != END_OF_FILE_CODE) {
                    outputStream.write(buffer, 0, lastBytesReadCount);
                }
                else {
                    break;
                }

                // we could update progress here (percent of copied file)
            }

            outputStream.flush();
            wasCopySuccessful = true;
            Log.v("CopyFileTask", "DB Copied");
        }
        catch (Exception e) {
            Log.v("CopyFileTask", "Error: " + e.toString());
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
