package ro.bluebit;

import android.os.AsyncTask;
import android.util.Log;

import ro.bluebit.Database.MySQLHelperAlt;
import ro.bluebit.Diverse.Siruri;

public  class SincroDate extends AsyncTask <String, Integer,String>
//    implements BazaInterfataAppCompat
{
// params este array de string.
// pe 0 - php care primeste comanda sql
    // pe 1 - sirul de query
//     pe 2 - scop
    String sScop="";
    String sIdSesiune ;
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
        sIdSesiune=params[3];
        String sRez="";
        String sText=sPhp+Siruri.getCRLF()+sQuery;
        Log.d("SINCRO","Ininte de sincro");
        try {
            Siruri.scrieFisLog("logSincroDate.txt","SINCRO START "+sIdSesiune,activity);
            Siruri.scrieFisLog("logSincroDate.txt","    "+sScop+Siruri.getCRLF()+sText,activity);
            sRez= MySQLHelperAlt.postRequest(sPhp, sQuery,sScop,sIdSesiune,activity);
            Siruri.scrieFisLog("logSincroDate.txt","    DUPA POSTREUEST "+sIdSesiune,activity);

        } catch (Exception e) {
            Log.d("SINCRO","Eroare "+e.getMessage());
            Siruri.scrieFisLog("logSincroDate.txt","    EROARE "+sIdSesiune+" "+sScop+Siruri.getCRLF()+e.getMessage()+Siruri.getCRLF()+
                    sText,activity);
            sRez="FAIL";
            e.printStackTrace();
        }
        return sRez;
    }
// public abstract void executalaHttpResponse(String sScop,String sRaspuns);


    @Override
    protected void onPostExecute(String result) {
        Siruri.scrieFisLog("logSincroDate.txt","    RASPUNS "+sIdSesiune+Siruri.getCRLF()+result,activity);
        Siruri.scrieFisLog("logSincroDate.txt","FINAL "+sIdSesiune,activity);
        activity.executalaHttpResponse(sScop,result);
    }

    @Override
    protected void onCancelled() {

        super.onCancelled();
        Siruri.scrieFisLog("logSincroDate.txt","    EXECUTIE OPRITA "+sIdSesiune,activity);
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
