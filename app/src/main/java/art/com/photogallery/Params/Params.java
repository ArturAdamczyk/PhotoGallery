package art.com.photogallery.Params;

public class Params {
    public static final String SORT_BY_DATE_ASC = "SORT_DATE_ASC";
    public static final String SORT_BY_DATE_DSC = "SORT_DATE_DSC";
    public static final String SORT_BY_NAME_ASC = "SORT_BY_NAME_ASC";
    public static final String SORT_BY_NAME_DSC = "SORT_BY_NAME_DSC";
    public static final String SORT_BY_SIZE_ASC = "SORT_BY_SIZE_ASC";
    public static final String SORT_BY_SIZE_DSC = "SORT_BY_SIZE_DSC";
    public static final String SORT_BY_RESOLUTION_ASC = "SORT_BY_RESOLUTION_ASC";
    public static final String SORT_BY_RESOLUTION_DSC = "SORT_BY_RESOLUTION_DSC";

    public static final String FILTER_BY_ORIENTATION = "FILTER_BY_ORIENTATION";
    public static final String FILTER_BY_ISO = "FILTER_BY_ISO";
    public static final String FILTER_BY_MAKER = "FILTER_BY_MAKER";
    public static final String FILTER_BY_FLASH = "FILTER_BY_FLASH";

    public static final int GALLERY_FRAGMENT = 0;
    public static final int SETTINGS_FRAGMENT = 1;
    public static int FRAGMENTS_AMOUNT = 2;
    public static int ROWS_AMOUNT = 2;

    public static final String PHOTO_ID = "PhotoId";

    public static final int REQUEST_TAKE_PHOTO = 1000;
    public static final int REQUEST_CAMERA_PERMISSION = 1001;
    public static final int REQUEST_DETAILS_ACTIVITY = 1002;
    public static final String INTENT_REFRESH_PHOTO = "intent_refresh_photo";
    public static final String INTENT_PHOTO_ID = "intent_photo_id";

    public static final String EMPTY_VALUE = "";
}
