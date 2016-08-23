package com.mermakov.testgithubclient.services.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;


public class GithubService {
    private static final String TAG = GithubService.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private GithubService() { }

    public static GithubApi createGithubService(final String credentials) {
        Gson gson = new GsonBuilder() //do we really need such customization?
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
//                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com");


            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();


                    Log.d(TAG,credentials);
                    Request newReq = request.newBuilder()
                            .addHeader("Authorization", credentials)
                            .addHeader("Accept","application/vnd.github.v3+json")
                            .build();
                    Response response = null;
                    String newStringBody = "";
                    try {
                        response = chain.proceed(newReq);
                        ResponseBody body = response.body();
                        Log.d(TAG, "HTTP " + response.code() + " URL=" + response.request().url().toString());
                        String bodyString = body.string();
                        Log.d(TAG,bodyString);
                        if(bodyString.startsWith("[")){
                            newStringBody = "{\"data\":"+bodyString+"}";
                        }
                        else {
                            newStringBody = bodyString;
                        }
                        Log.d(TAG,newStringBody);
                        final Response.Builder newResponse = response.newBuilder()
                                .body(ResponseBody.create(JSON, newStringBody));
                        response = newResponse.build();

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
