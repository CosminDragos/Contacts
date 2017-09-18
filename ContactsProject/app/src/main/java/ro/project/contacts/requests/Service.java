package ro.project.contacts.requests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ro.project.contacts.forms.ResponseContacts;

/**
 * Created by cosmin on 18.09.2017.
 */

public interface Service {

    @GET("/api")
    Call<ResponseContacts> getContacts(@Query("page") String page,
                                       @Query("results") String results,
                                       @Query("seed") String seed);


}
