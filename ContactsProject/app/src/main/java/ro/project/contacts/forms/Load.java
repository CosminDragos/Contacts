package ro.project.contacts.forms;

/**
 * Created by cosmin on 07.10.2016.
 */

public class Load extends Contact {

    private String type_of_item;

    public Load(String type_of_item) {
        this.type_of_item = type_of_item;
    }

    public String getType_of_item() {
        return type_of_item;
    }

    public void setType_of_item(String type_of_item) {
        this.type_of_item = type_of_item;
    }
}
