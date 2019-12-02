package ro.bluebit.Database;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ro.bluebit.BazaAppCompat;

public class MySQLHelper  {
    public static void postRequest(final String sPhp, final String sQuery,final Context context) throws IOException {
        String url = "https://www.farmaciilemyosotis.ro:443/s/deschidere_sesiune.php";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder postDataAuth = new FormBody.Builder();
        postDataAuth.add("u", "myotest");
        postDataAuth.add("p", "myotest");
        Request.Builder builder = new Request.Builder().url(url);
        builder.post(postDataAuth.build());
        final Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                final String mMessage = response.body().string();


                if (response.isSuccessful()) {
                    Cookie cCookie = Cookie.parse(request.url(), response.headers("set-cookie").toString());

                    getHttpResponse(cCookie.value(), sQuery , sPhp, context);
                }

            }
        });
    }
    public static void getHttpResponse(String idsesiune, String sQuery,String sPhp, final Context context) throws IOException   {
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



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {

             final   BazaAppCompat  act = (BazaAppCompat) context;
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            act.executalaHttpResponse(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch ( Exception e ) {

                        }
                    }
                });
             //   act.executalaHttpResponse(response.body().string());

            }
        });
    }

}