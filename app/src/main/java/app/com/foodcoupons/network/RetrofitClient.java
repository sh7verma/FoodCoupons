package app.com.foodcoupons.network;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import app.com.foodcoupons.utils.AppApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String GOOGLEPLACE_NEARBY = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static final String GOOGLEPLACE = "https://maps.googleapis.com/maps/api/place/autocomplete/";

    private static final String BASE_URL = "http://52.221.165.224:3050/";// Development
    //    private static final String BASE_URL = "http://52.28.171.36:3000/";// LIVE
//    private static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static Retrofit retrofit = null;
    private static Retrofit retrofitGoogle = null;
    private static Retrofit retrofitEtaGoogle = null;
    private static ApiInterface apiInterface = null;

    //
    public static ApiInterface getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (apiInterface == null) {
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

    //Creating OKHttpClient
    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    //Creating Cache
    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(AppApplication.getInstance().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception ignored) {

        }
        return cache;
    }

    private static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    //Provides offline cache
    private static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!AppApplication.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    public static Retrofit getClientNearBy() {
        if (retrofitGoogle == null) {
            retrofitGoogle = new Retrofit.Builder()
                    .baseUrl(GOOGLEPLACE_NEARBY)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitGoogle;
    }

    public static Retrofit getAutoClient() {
        if (retrofitEtaGoogle == null) {
            retrofitEtaGoogle = new Retrofit.Builder()
                    .baseUrl(GOOGLEPLACE)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitEtaGoogle;
    }
}
