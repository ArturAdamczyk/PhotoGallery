package art.com.photogallery.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.util.Optional;

import art.com.photogallery.Params.Params;
import art.com.photogallery.Params.WebServiceConfiguration;
import art.com.photogallery.R;
import art.com.photogallery.helpers.CustomAlertDialog;
import art.com.photogallery.helpers.FieldEditAlertDialog;
import art.com.photogallery.helpers.Messenger;
import art.com.photogallery.helpers.RestApiFactory;
import art.com.photogallery.interfaces.RestApi;
import art.com.photogallery.models.Photo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {
    private static final String CLASS_TAG = DetailsActivity.class.getSimpleName();

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewTagA)
    TextView textViewTagA;
    @BindView(R.id.textViewValueA)
    TextView textViewValueA;
    @BindView(R.id.textViewTagB)
    TextView textViewTagB;
    @BindView(R.id.textViewValueB)
    TextView textViewValueB;
    @BindView(R.id.textViewTagC)
    TextView textViewTagC;
    @BindView(R.id.textViewValueC)
    TextView textViewValueC;
    @BindView(R.id.textViewTagD)
    TextView textViewTagD;
    @BindView(R.id.textViewValueD)
    TextView textViewValueD;
    @BindView(R.id.textViewTagE)
    TextView textViewTagE;
    @BindView(R.id.textViewValueE)
    TextView textViewValueE;
    @BindView(R.id.textViewTagF)
    TextView textViewTagF;
    @BindView(R.id.textViewValueF)
    TextView textViewValueF;
    @BindView(R.id.textViewTagG)
    TextView textViewTagG;
    @BindView(R.id.textViewValueG)
    TextView textViewValueG;
    @BindView(R.id.buttonSaveImage)
    Button buttonSaveImage;
    @BindView(R.id.detailParentLayout)
    ConstraintLayout detailParentLayout;
    @BindView(R.id.textViewTagH)
    TextView textViewTagH;
    @BindView(R.id.textViewValueH)
    TextView textViewValueH;
    @BindView(R.id.textViewTagJ)
    TextView textViewTagJ;
    @BindView(R.id.textViewValueJ)
    TextView textViewValueJ;
    @BindView(R.id.textViewTagK)
    TextView textViewTagK;
    @BindView(R.id.textViewValueK)
    TextView textViewValueK;


    private Context ctx = this;
    private RestApi restApiClient;
    private Photo photo;
    private String photoId;
    private boolean photoUpdated = false;
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        messenger = new Messenger();
        restApiClient = RestApiFactory.getService(this.getApplication());
        getIntentParams();
        getData();
    }

    private void initViews() {
        ButterKnife.bind(this);
        loadImage();
        initTextViews();
    }

    private void refreshUI() {
        initTextViews();
    }

    @OnClick({R.id.imageView, R.id.textViewValueA, R.id.textViewValueB, R.id.textViewValueC,
            R.id.textViewValueD, R.id.textViewValueE, R.id.textViewValueF, R.id.textViewValueG,
            R.id.textViewValueH,R.id.textViewValueJ,R.id.textViewValueK,
            R.id.buttonSaveImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                initPopUp();
                break;
            case R.id.textViewValueA:
                break;
            case R.id.textViewValueB:
                break;
            case R.id.textViewValueC:
                createMakerDialog();
                break;
            case R.id.textViewValueD:
                initFieldEditAlertDialog(ctx.getResources().getString(R.string.details_model_label), textViewValueD);
                break;
            case R.id.textViewValueE:
                createOrientationDialog();
                break;
            case R.id.textViewValueF:
                createISODialog();
                break;
            case R.id.textViewValueG:
                break;
            case R.id.textViewValueH:
                break;
            case R.id.textViewValueJ:
                break;
            case R.id.textViewValueK:
                break;
            case R.id.buttonSaveImage:
                sendData();
                break;
        }
    }

    private void getIntentParams() {
        Intent intent = getIntent();
        photoId = intent.getStringExtra(Params.PHOTO_ID);
    }

    private void loadImage() {
        if (photo != null) {
            Glide.with(ctx)
                    .load(RestApiFactory.getWebServiceUrl(getApplication()) +
                            WebServiceConfiguration.DOWNLOAD_IMAGE_URL + "/" + photo.getId())
                    .into(imageView);
        } else {
            Glide.with(ctx)
                    .load(ctx.getResources().getDrawable(R.drawable.no_photo))
                    .into(imageView);
        }

    }

    private void initTextViews() {
        textViewTagA.setText(ctx.getResources().getString(R.string.details_date_label));
        textViewTagB.setText(ctx.getResources().getString(R.string.details_resolution_label));
        textViewTagC.setText(ctx.getResources().getString(R.string.details_maker_label));
        textViewTagD.setText(ctx.getResources().getString(R.string.details_model_label));
        textViewTagE.setText(ctx.getResources().getString(R.string.details_orientation_label));
        textViewTagF.setText(ctx.getResources().getString(R.string.details_iso_label));
        textViewTagG.setText(ctx.getResources().getString(R.string.details_size_label));
        textViewTagH.setText(ctx.getResources().getString(R.string.details_brightness_label));
        textViewTagJ.setText(ctx.getResources().getString(R.string.details_shutter_speed_label));
        textViewTagK.setText(ctx.getResources().getString(R.string.details_focal_length_label));
        textViewValueA.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueB.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueC.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueD.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueE.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueF.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueG.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueH.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueJ.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
        textViewValueK.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));

        Optional.ofNullable(photo.getExifData()).ifPresent(exifData -> {
            Optional.ofNullable(exifData.getDate())
                    .ifPresent(str -> textViewValueA.setText(str));
            Optional.ofNullable(exifData.getWidth() + " x " + exifData.getHeight())
                    .ifPresent(str -> textViewValueB.setText(str));
            Optional.ofNullable(exifData.getMake())
                    .ifPresent(str -> textViewValueC.setText(str));
            Optional.ofNullable(exifData.getModel())
                    .ifPresent(str -> textViewValueD.setText(str));
            Optional.ofNullable(String.valueOf(getResources().getStringArray(
                    R.array.orientation_filter_options)[Integer.parseInt(exifData.getOrientation()) - 1]))
                    .ifPresent(str -> textViewValueE.setText(str));
            Optional.ofNullable(exifData.getIso())
                    .ifPresent(str -> textViewValueF.setText(str));
            Optional.ofNullable(String.valueOf(exifData.getFileSize()))
                    .ifPresent(str -> textViewValueG.setText(str));
            Optional.ofNullable(exifData.getBrightness())
                    .ifPresent(str -> textViewValueH.setText(str));
            Optional.ofNullable(exifData.getShutterSpeed())
                    .ifPresent(str -> textViewValueJ.setText(str));
            Optional.ofNullable(exifData.getFocalLength())
                    .ifPresent(str -> textViewValueK.setText(str));
        });

        Optional.of(photo.getName())
                    .ifPresent(str -> textViewTitle.setText(str));
    }

    private void getData() {
        Optional.ofNullable(restApiClient).ifPresent(restApiClient -> {
            restApiClient.getPhoto(photoId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        messenger
                                .logMessage(CLASS_TAG, getResources().getString(R.string.data_load_success))
                                .showMessage(ctx.getResources().getString(R.string.details_photo_load_success), detailParentLayout);
                        photo = response;
                        initViews();
                    }, throwable -> {
                        messenger.logMessage(CLASS_TAG, throwable.getLocalizedMessage()).showMessage(getResources().getString(R.string.server_connection_error),  detailParentLayout);
                        messenger.logMessage(CLASS_TAG, getResources().getString(R.string.server_response_error)).showMessage(getResources().getString(R.string.data_load_error),  detailParentLayout);
                    });
        });
    }

    private void sendData() {
        Optional.ofNullable(restApiClient).ifPresent(restApiClient -> {
            restApiClient.updatePhoto(String.valueOf(photo.getId()), prepareEditedPhoto())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        messenger
                                .logMessage(CLASS_TAG, getResources().getString(R.string.data_load_success))
                                .showMessage(getResources().getString(R.string.details_exif_upload_success), detailParentLayout);
                        photo = response;
                        photoUpdated = true;
                        refreshUI();
                    }, throwable -> {
                        messenger.logMessage(CLASS_TAG, throwable.getLocalizedMessage()).showMessage(getResources().getString(R.string.server_connection_error),  detailParentLayout);
                        messenger.logMessage(CLASS_TAG, getResources().getString(R.string.server_response_error)).showMessage(getResources().getString(R.string.data_load_error),  detailParentLayout);
                    });
        });
    }

    private void initPopUp() {
        if (photo != null) {
            ImagePopup imagePopup = new ImagePopup(this);
            imagePopup.setFullScreen(true);
            imagePopup.setHideCloseIcon(true);
            imagePopup.setImageOnClickClose(false);
            imagePopup.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
            imagePopup.initiatePopupWithGlide(
                    RestApiFactory.getWebServiceUrl(getApplication()) +
                             WebServiceConfiguration.DOWNLOAD_IMAGE_URL + "/" + photo.getId());
            imagePopup.viewPopup();
        }
    }

    private void initFieldEditAlertDialog(String title, TextView textView) {
        new FieldEditAlertDialog(
                (String text) -> {
                    textView.setText(text);
                    textView.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                }, ctx, title, String.valueOf(textView.getText()))
                .initAlert(true);
    }

    private void createMakerDialog() {
        CustomAlertDialog.createAlertDialog(ctx, getResources().getString(R.string.settings_filter_by_maker_label))
                .setItems(getResources().getStringArray(R.array.maker_filter_options), (int position, String sortItem) -> {
                    textViewValueC.setText(getResources().getStringArray(R.array.maker_filter_options)[position]);
                    textViewValueC.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                }).show();
    }

    private void createOrientationDialog() {
        CustomAlertDialog.createAlertDialog(ctx, getResources().getString(R.string.settings_filter_by_orientation_label))
                .setItems(getResources().getStringArray(R.array.orientation_filter_options), (int position, String sortItem) -> {
                    textViewValueE.setText(getResources().getStringArray(R.array.orientation_filter_options)[position]);
                    textViewValueE.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                }).show();
    }

    private void createISODialog() {
        CustomAlertDialog.createAlertDialog(ctx, getResources().getString(R.string.settings_filter_by_iso_label))
                .setItems(getResources().getStringArray(R.array.iso_filter_options), (int position, String sortItem) -> {
                    textViewValueF.setText(getResources().getStringArray(R.array.iso_filter_options)[position]);
                    textViewValueF.setTextColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                }).show();
    }

    private Photo prepareEditedPhoto() {
        Photo editedPhoto = new Photo(photo);
        editedPhoto.getExifData().setMake(String.valueOf(textViewValueC.getText()));
        editedPhoto.getExifData().setModel(String.valueOf(textViewValueD.getText()));
        editedPhoto.getExifData().setOrientation(String.valueOf(findOrientationValue(textViewValueE)));
        editedPhoto.getExifData().setIso(String.valueOf(textViewValueF.getText()));
        return editedPhoto;
    }

    private int findOrientationValue(TextView textView){
        int position = 0;
        for(String item: getResources().getStringArray(R.array.orientation_filter_options)){
            if(item.equals(String.valueOf(textView.getText()))){
                break;
            }
            position++;
        }
        return position;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Params.INTENT_REFRESH_PHOTO, photoUpdated);
        intent.putExtra(Params.INTENT_PHOTO_ID, photo.getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
