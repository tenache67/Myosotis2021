package ro.bluebit.Database;

import android.util.Log;

import java.io.IOException;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MySQLHelperAlt {

        public static String postRequest(final String sPhp, final String sQuery)  {
            return postRequest(sPhp,sQuery,"QUERY");
        }
        public static String postRequest(final String sPhp, final String sQuery, final String sScop)  {
            String sRez="";
        String url = "https://www.farmaciilemyosotis.ro:443/s/deschidere_sesiune.php";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder postDataAuth = new FormBody.Builder();
        postDataAuth.add("u", "myotest");
        postDataAuth.add("p", "myotest");
        Request.Builder builder = new Request.Builder().url(url);
        builder.post(postDataAuth.build());
        final Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            Cookie cCookie = Cookie.parse(request.url(), response.headers("set-cookie").toString());
            sRez=getHttpResponse(cCookie.value(), sQuery , sPhp,sScop);
        } catch ( Exception e) {
            Log.d("Eroare post http:",e.getMessage());
            sRez="[{\"rez\":\"FAIL\"}]";
        }
        return sRez ;
    }
    public static String getHttpResponse(String idsesiune, String sQuery, String sPhp, final String sScop) throws IOException   {
        String sRez="";
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("www.farmaciilemyosotis.ro")
                .addPathSegment("propriu")
                .addPathSegment("transport_marfa")
                .addPathSegment(sPhp)
                .addQueryParameter("query",sQuery)
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("accept","application/json")
                .url(httpUrl)
                .addHeader("Cookie","PHPSESSID="+idsesiune)
                .build();

        //header seteaza valoarea unui header existent
        //addheader adauga un header nou
        try {
            Response response = client.newCall(request).execute();
            sRez=response.body().string();
        }catch (Exception e) {
            Log.d("Eroare get http:",e.getMessage());
            sRez="[{\"rez\":\"FAIL\"}]";
        }
        return sRez;
    }

}