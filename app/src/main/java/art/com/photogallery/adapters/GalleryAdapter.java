package art.com.photogallery.adapters;


import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

import art.com.photogallery.Params.Params;
import art.com.photogallery.Params.WebServiceConfiguration;
import art.com.photogallery.R;
import art.com.photogallery.helpers.PhotoFilter;
import art.com.photogallery.helpers.RestApiFactory;
import art.com.photogallery.models.Photo;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private ArrayList<Photo> photoList = new ArrayList();
    private ArrayList<Photo> allPhotoList = new ArrayList();
    private ClickListenerCallback callback;
    private Application application;

    public interface ClickListenerCallback {
        void onItemClick(Photo photo, View view);
    }

    public GalleryAdapter(ClickListenerCallback callback, Application application) {
        this.application = application;
        this.callback = callback;
    }

    public void setData(ArrayList<Photo> photoList){
        this.photoList = photoList;
        this.allPhotoList = photoList;
        notifyDataSetChanged();
    }

    public void addPhoto(Photo photo){
        photoList.add(photo);
        notifyDataSetChanged();
    }

    public void refreshPhoto(String photoId, Photo updatedPhoto){
        refreshList(photoId, updatedPhoto, photoList);
        refreshList(photoId, updatedPhoto, allPhotoList);
        notifyDataSetChanged();
    }

    private void refreshList(String photoId, Photo updatedPhoto, ArrayList<Photo> list){
        boolean isPresent = false;
        int index = 0;
        for(Photo photo: list){
            if(photo.getId().equals(photoId)){
                isPresent = true;
                break;
            }
            index++;
        }
        if(isPresent){
            list.set(index, new Photo(updatedPhoto));
        }
    }

    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, int position) {
        holder.setData(photoList.get(position));
        holder.fillItemUI();
    }

    @Override
    public int getItemCount() {
        if(!photoList.isEmpty()){
            return photoList.size();
        }else{
            return 0;
        }
    }

    public void sort(String sortParam){
        switch(sortParam){
            case Params.SORT_BY_NAME_ASC:
                Collections.sort(photoList, Photo.getNameComparatorAsc());
                break;
            case Params.SORT_BY_NAME_DSC:
                Collections.sort(photoList, Photo.getNameComparatorDsc());
                break;
            case Params.SORT_BY_DATE_ASC:
                Collections.sort(photoList, Photo.getDateComparatorAsc());
                break;
            case Params.SORT_BY_DATE_DSC:
                Collections.sort(photoList, Photo.getDateComparatorDsc());
                break;
            case Params.SORT_BY_SIZE_ASC:
                Collections.sort(photoList, Photo.getSizeComparatorAsc());
                break;
            case Params.SORT_BY_SIZE_DSC:
                Collections.sort(photoList, Photo.getSizeComparatorDsc());
                break;
            case Params.SORT_BY_RESOLUTION_ASC:
                Collections.sort(photoList, Photo.getResolutionComparatorAsc());
                break;
            case Params.SORT_BY_RESOLUTION_DSC:
                Collections.sort(photoList, Photo.getResolutionComparatorDsc());
                break;
            default:
                Collections.sort(photoList, Photo.getDateComparatorAsc());
                break;
        }
        notifyDataSetChanged();
    }


    public void filter(final String filterType, final String filterConstraint) {
        if (!(filterType.isEmpty() || filterConstraint.isEmpty())) {
            photoList = new ArrayList(new PhotoFilter(
                    allPhotoList,
                    filterType,
                    filterConstraint
            ).performFiltering());
        }else{
            photoList = allPhotoList;
        }
        notifyDataSetChanged();
    }

     protected class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.galleryImage)
        ImageView galleryImage;
        private Photo photo;

        GalleryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void setData(Photo photo){
            this.photo = photo;
        }

        public void fillItemUI(){
            Glide.with(galleryImage.getContext())
                    .load(RestApiFactory.getWebServiceUrl(application) +WebServiceConfiguration.DOWNLOAD_IMAGE_URL+"/"+photo.getId())
                    .into(galleryImage);
        }

        @Override
        public void onClick(View v) {
            callback.onItemClick(photo, v);
        }

    }
}
