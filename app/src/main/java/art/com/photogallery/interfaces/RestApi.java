package art.com.photogallery.interfaces;


import java.util.List;

import art.com.photogallery.models.Photo;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestApi {
    @GET("/getPhotos")
    Single<List<Photo>> getPhotos();

    @GET("/getPhoto/{id}")
    Single<Photo> getPhoto(@Path("id") String photoId);

    @PUT("/updatePhoto/{id}")
    Single<Photo> updatePhoto(@Path("id") String id, @Body Photo photo);

    @Multipart
    @POST("/uploadPhoto")
    Single<Photo> uploadPhoto(@Part MultipartBody.Part file);
}
