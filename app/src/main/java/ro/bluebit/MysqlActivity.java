package ro.bluebit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MysqlActivity extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private TextView status, role, method;
    TextView afisareIdSesiune;

    OkHttpClient client = new OkHttpClient();
    public String url = "https://reqres.in/api/users/2";
    private static final String TAG = "Raspuns";
    Button loadApi, postReq;
    String sQuery = "SELECT nume_salariat, prenume_salariat FROM salariati";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);

        usernameField = (EditText) findViewById(R.id.textViewUserName);
        passwordField = (EditText) findViewById(R.id.textViewUserPassword);
        loadApi = (Button) findViewById(R.id.loadApi);
        postReq = (Button) findViewById(R.id.postReq);
        afisareIdSesiune=findViewById(R.id.afisare_sesiune_id);


        loadApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getHttpResponse(afisareIdSesiune.getText().toString(),sQuery);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        postReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRequest();
                    // getHttpResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

        public void getHttpResponse(String idsesiune, String sQuery) throws IOException   {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("www.farmaciilemyosotis.ro")
                    .addPathSegment("propriu")
                    .addPathSegment("transport_marfa")
                    .addPathSegment("test_mysql.php")
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
                public void onResponse(Call call, Response response) throws IOException {

                    String mMessage = response.body().string();
                    Log.e(TAG, mMessage);
                }
            });
        }


    public void postRequest() throws IOException {
        String url = "https://www.farmaciilemyosotis.ro:443/s/deschidere_sesiune.php";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder postDataAuth = new FormBody.Builder();
        postDataAuth.add("u","myotest");
        postDataAuth.add("p","myotest");
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
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Cookie cCookie=Cookie.parse(request.url(), response.headers("set-cookie").toString());
                            RecieveResponse(cCookie.value());


////                            for (int i=0; i<response.headers().size()  ; i++){
////                                   List<String> mHeadrs =new ArrayList<>();
//                                String a= response.headers().value(i);
//                                if (a.contains("PHPSESSID")){
//                                    afisareIdSesiune.setText(a);
//                                }
//                       // }

                    };
                });

                Log.e(TAG, mMessage);
            }
        };

    public void RecieveResponse(String cCookie){

       String valoarecookie=cCookie;
        //afisareIdSesiune.setText(cCookie);
        try {
            getHttpResponse(valoarecookie,sQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void postRequestForQuery() throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        MediaType MEDIA_TYPE_HTML=MediaType.parse("text/html; charset=utf-8");
        String url = "https://www.farmaciilemyosotis.ro/propriu/transport_marfa/transport_marfa.php";

        OkHttpClient client = new OkHttpClient();
//        JSONObject postdata = new JSONObject();
//        try {
//            postdata.put("username", "myotest");
//            postdata.put("password", "myotest");
//        } catch(JSONException e){
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        FormBody.Builder postData = new FormBody.Builder();
        postData.add("u","myotest");
        postData.add("p","myotest");
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .post(postData.build())
                .addHeader("content-type","text/html; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
            }
        });
    }


//    public void registerUser(String username, String password) {
//        RequestBody body = new FormEncodingBuilder()
//                .add("username", username)
//                .add("password", password)
//                .build();
//        Request request = new Request.Builder().url(BASE_URL).post(body).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//                //  Log.e(TAG_REGISTER, "Registration error: " + e.getMessage());
//                System.out.println("Registration Error" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                try {
//                    String resp = response.body().string();
////                    Log.v(TAG_REGISTER, resp);
//                    System.out.println(resp);
//                    if (response.isSuccessful()) {
//                    } else {
//
//                    }
//                } catch (IOException e) {
//                    // Log.e(TAG_REGISTER, "Exception caught: ", e);
//                    System.out.println("Exception caught" + e.getMessage());
//                }
//            }
//        });

            }
        );
    }
}