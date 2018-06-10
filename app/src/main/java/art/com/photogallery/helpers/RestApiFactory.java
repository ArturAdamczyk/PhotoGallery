package art.com.photogallery.helpers;


import android.app.Application;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import art.com.photogallery.Params.WebServiceConfiguration;
import art.com.photogallery.data.WebServiceSh;
import art.com.photogallery.interfaces.RestApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestApiFactory {
    private static final String CLASS_TAG = RestApiFactory.class.getSimpleName();
    private static Retrofit retrofit;
    private static RestApi restApiClient = null;

    public static RestApi getService(Application application) throws IllegalArgumentException{
        try{
            if(restApiClient == null) {
                initRetrofit(application);
            }
            return restApiClient;
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static void initRetrofit(Application application) throws IllegalArgumentException{
        try{
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            retrofit = new Retrofit.Builder()
                    .baseUrl(getWebServiceUrl(application))
                    .client(okHttpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            restApiClient = retrofit.create(RestApi.class);
        }catch(IllegalArgumentException e){
            Log.d(CLASS_TAG, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String getWebServiceUrl(Application application){
        return WebServiceConfiguration.HTTP +
                new WebServiceSh(application).getWebServiceAddress() +
                WebServiceConfiguration.WEB_SERVICE_PORT;
    }
}
