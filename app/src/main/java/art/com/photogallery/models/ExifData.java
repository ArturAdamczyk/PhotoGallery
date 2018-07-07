package art.com.photogallery.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExifData {
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("orientation")
    @Expose
    private String orientation;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("exposureTime")
    @Expose
    private String exposureTime;
    @SerializedName("fNumber")
    @Expose
    private String fNumber;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("shutterSpeed")
    @Expose
    private String shutterSpeed;
    @SerializedName("brightness")
    @Expose
    private String brightness;
    @SerializedName("exposureBias")
    @Expose
    private String exposureBias;
    @SerializedName("flash")
    @Expose
    private String flash;
    @SerializedName("focalLength")
    @Expose
    private String focalLength;
    @SerializedName("colorSpace")
    @Expose
    private String colorSpace;
    @SerializedName("fileSize")
    @Expose
    private int fileSize;
    private transient int resolution;

    public ExifData(ExifData exifData){
        this.make = exifData.make;
        this.model = exifData.model;
        this.orientation = exifData.orientation;
        this.date = exifData.date;
        this.width = exifData.width;
        this.height = exifData.height;
        this.exposureTime = exifData.exposureTime;
        this.fNumber = exifData.fNumber;
        this.iso = exifData.iso;
        this.shutterSpeed = exifData.shutterSpeed;
        this.brightness = exifData.brightness;
        this.exposureBias = exifData.exposureBias;
        this.flash = exifData.flash;
        this.focalLength = exifData.focalLength;
        this.colorSpace = exifData.colorSpace;
        this.fileSize = exifData.fileSize;
        this.resolution = exifData.resolution;
    }
}
