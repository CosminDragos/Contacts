package ro.project.contacts.utils;

import ro.project.contacts.requests.RetrofitClient;
import ro.project.contacts.requests.Service;

/**
 * Created by cosmin on 18.09.2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://randomuser.me/";

    public static Service getService() {
        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }
}
