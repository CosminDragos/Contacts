package ro.project.contacts.screens.main;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.project.contacts.forms.Contact;
import ro.project.contacts.forms.ResponseContacts;
import ro.project.contacts.mvp.BasePresenter;
import ro.project.contacts.requests.Service;
import ro.project.contacts.screens.user.UserDetailsActivity;
import ro.project.contacts.utils.ApiUtils;
import ro.project.contacts.utils.PrefUtils;

/**
 * Created by cosmin on 18.09.2017.
 */

public class MainPresenter extends BasePresenter<Contract.ContractView> implements Contract.ContractPresenter {

    private Service service;

    public MainPresenter(Contract.ContractView view) {
        super(view);
        service = ApiUtils.getService();
    }

    @Override
    public void loadContacts(final boolean loadMore, int page) {
        if (!loadMore) {
            getView().init();
        }

        service.getContacts(page, 100, "abc")
                .enqueue(new Callback<ResponseContacts>() {
                    @Override
                    public void onResponse(Call<ResponseContacts> call, Response<ResponseContacts> response) {
                        if (response.isSuccessful()
                                && response.body().getResult() != null
                                && !response.body().getResult().isEmpty()) {

                            if (loadMore) {
                                getView().refreshList(response.body().getResult());
                            } else {
                                getView().setAdapter(response.body().getResult());
                            }

                        } else {
                            if (loadMore) {
                                getView().refreshList(new ArrayList<Contact>());
                            } else {
                                getView().setAdapter(new ArrayList<Contact>());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContacts> call, Throwable t) {
                        if (loadMore) {
                            getView().refreshList(new ArrayList<Contact>());
                        } else {
                            getView().setAdapter(new ArrayList<Contact>());
                        }
                    }
                });
    }

    @Override
    public void goToUserDetailsActivity(Context context, Contact item) {
        PrefUtils.setSharedPreference(context, PrefUtils.NAME, item.getName().getFullName());
        PrefUtils.setSharedPreference(context, PrefUtils.PHONE, item.getPhone());
        PrefUtils.setSharedPreference(context, PrefUtils.EMAIL, item.getEmail());
        PrefUtils.setSharedPreference(context, PrefUtils.ADDRESS, item.getLocation().getAddress());
        PrefUtils.setSharedPreference(context, PrefUtils.FULL_ID, item.getId().getFullId());
        PrefUtils.setSharedPreference(context, PrefUtils.ICON_THUMBNAIL, item.getPicture().getThumbnail());
        PrefUtils.setSharedPreference(context, PrefUtils.ICON_MEDIUM, item.getPicture().getMedium());
        PrefUtils.setSharedPreference(context, PrefUtils.ICON_LARGE, item.getPicture().getLarge());

        Intent intent = new Intent(context, UserDetailsActivity.class);
        context.startActivity(intent);
    }
}
