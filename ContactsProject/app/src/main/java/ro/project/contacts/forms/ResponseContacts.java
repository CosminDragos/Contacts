package ro.project.contacts.forms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cosmin on 18.09.2017.
 */

public class ResponseContacts {

    @SerializedName("results")
    @Expose
    private List<Contact> result = null;

    public List<Contact> getResult() {
        return result;
    }

    public void setResult(List<Contact> result) {
        this.result = result;
    }
}
