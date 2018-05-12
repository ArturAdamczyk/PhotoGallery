package art.com.photogallery.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import art.com.photogallery.Params.WebServiceConfiguration;
import art.com.photogallery.R;

public class WebServiceSh {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public WebServiceSh(Application application){
        sharedPreferences = application.getSharedPreferences(application.getResources().getString(R.string.sh_web_service_config), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setWebServiceAddress(String clientId) {
        editor.putString(WebServiceConfiguration.WEB_SERVICE_URL, clientId);
        editor.commit();
    }

    public String getWebServiceAddress(){
        return sharedPreferences.getString(WebServiceConfiguration.WEB_SERVICE_URL,"");
    }

}
