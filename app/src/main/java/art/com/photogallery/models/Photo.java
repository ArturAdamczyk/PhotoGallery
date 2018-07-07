package art.com.photogallery.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Comparator.comparing;

@Data
@NoArgsConstructor
@AllArgsConstructor()
public class Photo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private byte[] image;
    @SerializedName("exifData")
    @Expose
    private ExifData exifData;

    public Photo(Photo photo){
        this.id = photo.id;
        this.name = photo.name;
        this.image = photo.image;
        this.exifData = new ExifData(photo.getExifData());
    }

    public static Comparator<Photo> getDateComparatorAsc(){
       return comparing(Photo::getExifData, comparing(ExifData::getDate));
    }

    public static Comparator<Photo> getDateComparatorDsc(){
        return getDateComparatorAsc().reversed();
    }

    public static Comparator<Photo> getNameComparatorAsc(){
        return comparing(Photo::getName);
    }

    public static Comparator<Photo> getNameComparatorDsc(){
        return getNameComparatorAsc().reversed();
    }

    public static Comparator<Photo> getSizeComparatorAsc(){
        return comparing(Photo::getExifData, comparing(ExifData::getFileSize));
    }

    public static Comparator<Photo> getSizeComparatorDsc(){
        return getSizeComparatorAsc().reversed();
    }

    public static Comparator<Photo> getResolutionComparatorAsc(){
        return comparing(Photo::getExifData, comparing(ExifData::getResolution));
    }

    public static Comparator<Photo> getResolutionComparatorDsc(){
        return getResolutionComparatorAsc().reversed();
    }

    public void countResolution(){
        try{
            this.exifData.setResolution(
                    Integer.parseInt(this.exifData.getHeight()) * Integer.parseInt(this.exifData.getWidth()));
        }catch(NumberFormatException e){
            this.exifData.setResolution(0);
        }
    }
}
