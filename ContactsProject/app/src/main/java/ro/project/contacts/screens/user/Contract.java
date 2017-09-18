package ro.project.contacts.screens.user;

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
    }

    public interface ContractPresenter extends BaseContract.ContractPresenter {

    }

}
