package br.com.ecarrara.popularmovies.core.networking.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import br.com.ecarrara.popularmovies.BuildConfig;
import br.com.ecarrara.popularmovies.core.utils.gson.AutoValuesGsonAdapterFactory;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiConnection {

    private static final String QUERY_PARAMETER_API_KEY = "api_key";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;
    private static final String API_DATE_FORMAT = "yyyy-MM-dd";

    private String serverBaseUrl;
    private Retrofit retrofit;

    public RestApiConnection(String serverUrl) {
        serverBaseUrl = serverUrl;
        retrofit = buildRetrofitClient();
    }

    public <T> T connectTo(Class<T> apiInterface) {
        return retrofit.create(apiInterface);
    }

    private Retrofit buildRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl(serverBaseUrl)
                .client(buildOkHttpClient())
                .addConverterFactory(buildGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(QUERY_PARAMETER_API_KEY, API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

    private Converter.Factory buildGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setDateFormat(API_DATE_FORMAT)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(AutoValuesGsonAdapterFactory.create())
                .create();
        return GsonConverterFactory.create(gson);

    }
}
