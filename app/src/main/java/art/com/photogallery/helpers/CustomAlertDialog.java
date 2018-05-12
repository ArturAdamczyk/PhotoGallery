package art.com.photogallery.helpers;

import android.content.Context;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import art.com.photogallery.R;

public class CustomAlertDialog {

    public static LovelyChoiceDialog createAlertDialog(Context ctx, String title){
        return new LovelyChoiceDialog(ctx)
                .setTopTitle(title)
                .setTopTitleColor(R.color.colorWhite)
                .setTopColorRes(R.color.colorAccent);
    }
}
