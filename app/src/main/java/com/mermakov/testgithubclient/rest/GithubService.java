package com.mermakov.testgithubclient.rest;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.HTTP;


public class GithubService {
    private static final String TAG = GithubService.class.getSimpleName();

    private GithubService() { }

    public static GithubApi createGithubService() {
        Gson gson = new GsonBuilder() //do we really need such customization?
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
//                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com");


            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    String credentials = /*"swat-cat" + ":" + "StarWars12";
                    final String basic =
                            "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);*/
                    Credentials.basic("swat-cat", "StarWars12");

                    Log.d(TAG,credentials);
                    Request newReq = request.newBuilder()
                            .addHeader("Authorization", credentials)
                            .addHeader("Accept","application/vnd.github.v3+json")
                            .build();
                    Response response = null;
                    try {
                        response = chain.proceed(newReq);
                        ResponseBody body = response.body();
                        Log.d(TAG, "HTTP " + response.code() + " URL=" + response.request().url().toString());
                        Log.d(TAG,body.string());
                    } catch (IOException e) {
                        e.getLocalizedMessage();
                    }
                    return response;
                }
            }).build();
            builder.client(client);

        return builder.build().create(GithubApi.class);
    }
}
