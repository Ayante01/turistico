/**
 * Login Activity
 * Esta clase utiliza retrofit para conectarse con la base de datos por medio de un api
 *
 * @author Olman Castro
 * @version 1.0
 * @since 2020-10-1
 */
package cr.ac.ucr.turistico.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilder {
    /**
     * Variables
     */
    private static final String TAG = "RetrofitBuilder";
    //TODO: Aqui ira el link del api
    private static final String BASE_URL = "";

    private static final OkHttpClient client = buildClient();
    private static final Retrofit retrofit = buildRetrofit(client);

    /**
     * Metodo buildClient()
     * @return OkHttpClient
     */
    private static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {

                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {

                        Request original = chain.request();

                        HttpUrl url = original.url();

                        HttpUrl newUrl = url.newBuilder()
                                .build();

                        Request.Builder builder = original.newBuilder().url(newUrl);
                        Request request = builder.build();

                        return chain.proceed(request);
                    }
                });

        return builder.build();
    }

    /**
     * Meotodo buildRetrofit
     * @param okHttpClient
     * @return
     */
    private static Retrofit buildRetrofit(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Metodo createService()
     * @param service
     * @param <T>
     * @return service
     */
    public static <T> T createService(final Class<T> service) {
        return retrofit.create(service);
    }

    /**
     *
     * @return retrofit
     */
    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
