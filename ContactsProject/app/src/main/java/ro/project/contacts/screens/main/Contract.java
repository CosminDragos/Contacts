package ro.project.contacts.screens.main;

import android.content.Context;

import java.util.List;

import ro.project.contacts.forms.Contact;
import ro.project.contacts.mvp.BaseContract;

/**
 * Created by cosmin on 18.09.2017.
 */

class Contract {

    public interface ContractView extends BaseContract.ContractView {
        public void init();
        public void setAdapter(List<Contact> contactList);
        public void refreshList(List<Contact> contactList);
    }

    public interface ContractPresenter extends BaseContract.ContractPresenter {
        public void loadContacts(boolean loadMore, int page);
        public void goToUserDetailsActivity(Context context, Contact item);
    }

}
