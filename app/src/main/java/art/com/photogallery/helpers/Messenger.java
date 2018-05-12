package art.com.photogallery.helpers;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

public class Messenger {

    public Messenger logMessage(String classTag, String logMsg){
        Log.d(classTag, logMsg);
        return this;
    }

    public Messenger showMessage(String appMsg, View view){
        Snackbar.make(view, appMsg, Snackbar.LENGTH_LONG)
                .show();
        return this;
    }
}
