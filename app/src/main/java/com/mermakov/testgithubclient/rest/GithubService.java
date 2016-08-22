package com.mermakov.testgithubclient.rest;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.HTTP;


public class GithubService {
    private static final String TAG = GithubService.class.getSimpleName();

    private GithubService() { }

    public static GithubApi createGithubService() {
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com");


            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    String credentials = "swat-cat" + ":" + "StarWars12";
                    final String basic =
                            "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                    Log.d(TAG,basic);
                    Request newReq = request.newBuilder()
                            .addHeader("Accept", "application/json")
                            .build();
                    Response response = null;
                    try {
                        response = chain.proceed(newReq);
                        Log.d(TAG,response.message());
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
