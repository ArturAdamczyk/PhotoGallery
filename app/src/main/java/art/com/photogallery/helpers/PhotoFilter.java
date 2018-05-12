package art.com.photogallery.helpers;

import java.util.ArrayList;

import art.com.photogallery.Params.Params;
import art.com.photogallery.models.Photo;

public class PhotoFilter {
    private String filterType;
    private String filterConstraint;
    private ArrayList<Photo> allPhotoList;

    public PhotoFilter(ArrayList<Photo> allPhotoList, String filterType, String filterConstraint){
        this.filterType = filterType;
        this.allPhotoList = allPhotoList;
        this.filterConstraint = filterConstraint;
    }

    public ArrayList<Photo> performFiltering(){
        switch(filterType){
            case Params.FILTER_BY_ORIENTATION:
                return filterByOrientation();
            case Params.FILTER_BY_ISO:
                return filterByISO();
            case Params.FILTER_BY_MAKER:
                return filterByMaker();
            case Params.FILTER_BY_FLASH:
                return filterByFlash();
            default:
                return new ArrayList(allPhotoList);
        }
    }

    private ArrayList<Photo> filterByOrientation(){
        ArrayList<Photo> filteredList = new ArrayList();
        for (int i = 0; i < allPhotoList.size(); i++) {
            if (allPhotoList.get(i).getExifData().getOrientation().equals(filterConstraint)) {
                filteredList.add(allPhotoList.get(i));
                continue;
            }
        }
        return filteredList;
    }

    private  ArrayList<Photo> filterByISO(){
        ArrayList<Photo> filteredList = new ArrayList();
        for (int i = 0; i < allPhotoList.size(); i++) {
            if (allPhotoList.get(i).getExifData().getIso().equals(filterConstraint)) {
                filteredList.add(allPhotoList.get(i));
                continue;
            }
        }
        return filteredList;
    }

    private  ArrayList<Photo> filterByMaker(){
        ArrayList<Photo> filteredList = new ArrayList();
        for (int i = 0; i < allPhotoList.size(); i++) {
            if (allPhotoList.get(i).getExifData().getMake().toLowerCase()
                    .contains(filterConstraint.toLowerCase())) {
                filteredList.add(allPhotoList.get(i));
                continue;
            }
        }
        return filteredList;
    }

    private  ArrayList<Photo> filterByFlash(){
        ArrayList<Photo> filteredList = new ArrayList();
        for (int i = 0; i < allPhotoList.size(); i++) {
            if (allPhotoList.get(i).getExifData().getFlash().equals(filterConstraint)) {
                filteredList.add(allPhotoList.get(i));
                continue;
            }
        }
        return filteredList;
    }
}
