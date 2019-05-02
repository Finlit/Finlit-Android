package com.app.finlit.data;

import android.util.Log;
import com.app.finlit.BuildConfig;
import com.app.finlit.utils.App;
import com.app.finlit.utils.SharedPreferenceHelper;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class Injector {

    private static Retrofit provideRetrofit (String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpClient() )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    private static OkHttpClient provideOkHttpClient () {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideHeaderInterceptor() )
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build();
    }

    private static Interceptor provideOfflineCacheInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();

                if (!App.hasNetwork() ) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }
                return chain.proceed( request );
            }
        };
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor () {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
                    @Override
                    public void log (String message) {
                        Log.d("Injector", message);
                    }
                } );
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? BODY : NONE );
        return httpLoggingInterceptor;
    }

    private static Interceptor provideHeaderInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String token = SharedPreferenceHelper.getInstance().getUserToken();
                if(token == null){
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                } else {
                    Request request = chain.request().newBuilder().addHeader("x-access-token", token).build();
                    return chain.proceed(request);
                }
            }
        };
    }

    public static InterfaceApi provideApi () {
        return provideRetrofit( InterfaceApi.BASE_URL ).create( InterfaceApi.class );
    }

//    public static InterfaceApi provideApi1 () {
//        return provideRetrofit( InterfaceApi.LOCAL_URL ).create( InterfaceApi.class );
//    }

}
