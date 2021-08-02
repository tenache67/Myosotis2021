package ro.bluebit;

import android.os.AsyncTask;
import android.util.Log;

import ro.bluebit.Database.MySQLHelperAlt;

public  class SincroDate extends AsyncTask <String, Integer,String>
//    implements BazaInterfataAppCompat
{
// params este array de string.
// pe 0 - php care primeste comanda sql
    // pe 1 - sirul de query
//     pe 2 - scop
    String sScop="";
    private BazaAppCompat activity ;
    public SincroDate (BazaAppCompat activity) {
        this.activity=activity ;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String test ="";
    }

    @Override
    protected String doInBackground(String... params) {
        String sPhp =params[0];
        String sQuery=params[1];
        sScop=params[2];
        String sRez="";
        Log.d("SINCRO","Ininte de sincro");
        try {
            sRez= MySQLHelperAlt.postRequest(sPhp, sQuery,sScop);
        } catch (Exception e) {
            Log.d("SINCRO","Eroare "+e.getMessage());
            sRez="FAIL";
            e.printStackTrace();
        }
        return sRez;
    }
// public abstract void executalaHttpResponse(String sScop,String sRaspuns);


    @Override
    protected void onPostExecute(String result) {
        activity.executalaHttpResponse(sScop,result);
    }
 }
//
//
//public  class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
//    protected Long doInBackground(URL... urls) {
//        int count = urls.length;
//        long totalSize = 0;
//        for (int i = 0; i < count; i++) {
//            totalSize += Downloader.downloadFile(urls[i]);
//            publishProgress((int) ((i / (float) count) * 100));
//            // Escape early if cancel() is called
//            if (isCancelled()) break;
//        }
//        return totalSize;
//    }
//
//    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
//    }
//
//    protected void onPostExecute(Long result) {
//        showDialog("Downloaded " + result + " bytes");
//    }
// }
