package art.com.photogallery.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import art.com.photogallery.Params.Params;
import art.com.photogallery.R;
import art.com.photogallery.activities.DetailsActivity;
import art.com.photogallery.adapters.GalleryAdapter;
import art.com.photogallery.data.WebServiceSh;
import art.com.photogallery.helpers.FieldEditAlertDialog;
import art.com.photogallery.helpers.Messenger;
import art.com.photogallery.helpers.RestApiFactory;
import art.com.photogallery.interfaces.RestApi;
import art.com.photogallery.models.Photo;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class GalleryFragment extends Fragment {
    private String CLASS_TAG = "";

    @BindView(R.id.textViewInfo)
    TextView textViewInfo;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.mainParentLayout)
    CoordinatorLayout mainParentLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context ctx;
    private RestApi restApiClient;
    private GalleryAdapter galleryAdapter;
    private Messenger messenger;


    public static GalleryFragment newInstance() {
        GalleryFragment fragmentFirst = new GalleryFragment();
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.CLASS_TAG = this.getActivity().getClass().getSimpleName();
        this.ctx = this.getActivity().getApplicationContext();

        initCameraConfiguration();
        messenger = new Messenger();
        openServerConnectionDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    public void setSortOption(String sortOption){
        galleryAdapter.sort(sortOption);
    }

    public void setFilterOption(String filterOption, String filterConstraint){
        galleryAdapter.filter(filterOption, filterConstraint);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Params.REQUEST_DETAILS_ACTIVITY) {
            if(data.getExtras().getBoolean(Params.INTENT_REFRESH_PHOTO)){
                refreshData(data.getExtras().getString(Params.INTENT_PHOTO_ID));
            }
        }else{
            EasyImage.handleActivityResult(requestCode, resultCode, data, this.getActivity(), new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    Snackbar.make(mainParentLayout, getResources().getString(R.string.gallery_camera_error), Snackbar.LENGTH_LONG)
                            .show();
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    uploadPhoto(imageFile);
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    if (source == EasyImage.ImageSource.CAMERA) {
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(ctx);
                        if (photoFile != null) photoFile.delete();
                    }
                }
            });
        }
    }

    private void initViews() {
        initFab();
        initRecyclerView();
    }

    private void refreshUI(){
        if(galleryAdapter.getPhotoList().isEmpty()){
            textViewInfo.setVisibility(View.VISIBLE);
        }else{
            textViewInfo.setVisibility(View.GONE);
        }
    }

    private void initFab() {
        fab.setOnClickListener((View view) -> takeCameraShot());
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), Params.ROWS_AMOUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
        galleryAdapter = new GalleryAdapter(
                (Photo photo, View view) -> {
                    moveToDetailsActivity(photo);
                },getActivity().getApplication());
        recyclerView.setAdapter(galleryAdapter);
    }

    private void getData() {
        Optional.ofNullable(restApiClient).ifPresent(restApiClient -> {
            restApiClient.getPhotos()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        messenger
                                .logMessage(CLASS_TAG, getResources().getString(R.string.data_load_success))
                                .showMessage(getResources().getString(R.string.gallery_photos_loaded_successfully), mainParentLayout);
                        insertData(new ArrayList(response));
                        refreshUI();
                    }, throwable -> {
                        messenger.logMessage(CLASS_TAG, throwable.getLocalizedMessage()).showMessage(getResources().getString(R.string.server_connection_error), mainParentLayout);
                        messenger.logMessage(CLASS_TAG, getResources().getString(R.string.server_response_error)).showMessage(getResources().getString(R.string.data_load_error), mainParentLayout);
                    });
        });
    }

    private void refreshData(String photoId){
        Optional.ofNullable(restApiClient).ifPresent(restApiClient -> {
            restApiClient.getPhoto(photoId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        messenger
                                .logMessage(CLASS_TAG, getResources().getString(R.string.data_load_success))
                                .showMessage(getResources().getString(R.string.gallery_photo_refresh_success), mainParentLayout);
                        galleryAdapter.refreshPhoto(photoId,response);
                        refreshUI();
                    }, throwable -> {
                        messenger.logMessage(CLASS_TAG, throwable.getLocalizedMessage()).showMessage(getResources().getString(R.string.server_connection_error), mainParentLayout);
                        messenger.logMessage(CLASS_TAG, getResources().getString(R.string.server_response_error)).showMessage(getResources().getString(R.string.data_load_error), mainParentLayout);
                    });
        });
    }

    private void uploadPhoto(File imageFile) {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        imageFile
                );
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", imageFile.getName(), requestFile);
        Optional.ofNullable(restApiClient).ifPresent(restApiClient -> {
            restApiClient.uploadPhoto(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (imageFile != null) imageFile.delete();
                        messenger
                                .logMessage(CLASS_TAG, getResources().getString(R.string.gallery_photo_upload_success))
                                .showMessage(getResources().getString(R.string.data_load_success), mainParentLayout);
                        addPhoto(response);
                        refreshUI();
                    }, throwable -> {
                        if (imageFile != null) imageFile.delete();
                        messenger.logMessage(CLASS_TAG, throwable.getLocalizedMessage()).showMessage(getResources().getString(R.string.gallery_photo_upload_error), mainParentLayout);
                    });
        });
    }

    private void openServerConnectionDialog(){
        WebServiceSh webServiceSh = new WebServiceSh(getActivity().getApplication());
        if(webServiceSh.getWebServiceAddress().equals(Params.EMPTY_VALUE)){
            new FieldEditAlertDialog(
                    (String text) -> {
                        webServiceSh.setWebServiceAddress(text);
                        startServerConnection();
                    }, getActivity(), getResources().getString(R.string.gallery_server_address_input), Params.EMPTY_VALUE)
                    .initAlert(false);
        }else{
            startServerConnection();
        }
    }

    private void startServerConnection(){
        restApiClient = RestApiFactory.getService(getActivity().getApplication());
        getData();
    }

    private void initCameraConfiguration(){
        EasyImage.configuration(this.getActivity()).saveInRootPicturesDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, Params.REQUEST_CAMERA_PERMISSION);
            }
        }
    }

    private void takeCameraShot() {
        EasyImage.openCamera(this.getActivity(), Params.REQUEST_TAKE_PHOTO);
    }

    private void addPhoto(Photo photo) {
        galleryAdapter.addPhoto(photo);
    }

    private void insertData(ArrayList<Photo> photoList) {
        for(Photo photo: photoList){ photo.countResolution(); }
        galleryAdapter.setData(photoList);
    }

    private void moveToDetailsActivity(Photo photo) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Params.PHOTO_ID, photo.getId());
        getActivity().startActivityForResult(intent, Params.REQUEST_DETAILS_ACTIVITY);
    }
}