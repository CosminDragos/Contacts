package ro.project.contacts.screens.user;

import android.content.Context;

import ro.project.contacts.mvp.BasePresenter;

/**
 * Created by cosmin on 18.09.2017.
 */

public class UserDetailsPresenter extends BasePresenter<Contract.ContractView> implements Contract.ContractPresenter {

    public UserDetailsPresenter(Contract.ContractView view) {
        super(view);
    }

}
