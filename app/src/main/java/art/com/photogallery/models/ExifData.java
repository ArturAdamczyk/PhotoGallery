package art.com.photogallery.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExifData {
    private String make;
    private String model;
    private String orientation;
    private String date;
    private String width;
    private String height;
    private String exposureTime;
    private String fNumber;
    private String iso;
    private String shutterSpeed;
    private String brightness;
    private String exposureBias;
    private String flash;
    private String focalLength;
    private String colorSpace;
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
