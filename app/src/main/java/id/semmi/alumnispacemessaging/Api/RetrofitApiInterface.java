package id.semmi.alumnispacemessaging.Api;

import java.util.List;

import id.semmi.alumnispacemessaging.models.FirebaseKey;
import id.semmi.alumnispacemessaging.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by semmi on 15/03/2016.
 */
public interface RetrofitApiInterface {

    @POST("loginJson.php")
    Call<User> loggedInUser(@Body User user);

    @GET("allUser.php")
    Call<List<User>> fetchAllUser();

    @POST("checkFirebaseKey.php")
    Call<FirebaseKey> checkFirebaseKey(@Body FirebaseKey firebaseKey);

    @POST("addFirebaseKey.php")
    Call<FirebaseKey> addFirebaseKey(@Body FirebaseKey firebaseKey);
}
