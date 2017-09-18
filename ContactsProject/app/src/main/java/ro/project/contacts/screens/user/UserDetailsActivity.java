package ro.project.contacts.screens.user;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.project.contacts.R;
import ro.project.contacts.custom.AutoResizeTextView;
import ro.project.contacts.mvp.BaseActivity;
import ro.project.contacts.utils.PrefUtils;

/**
 * Created by cosmin on 18.09.2017.
 */

public class UserDetailsActivity extends BaseActivity<Contract.ContractPresenter> implements Contract.ContractView {

    @BindView(R.id.icon_contact_details) ImageView iconContact;
    @BindView(R.id.name_contact_details) AutoResizeTextView nameContact;
    @BindView(R.id.phone_number) AutoResizeTextView phoneNumber;
    @BindView(R.id.email) AutoResizeTextView email;
    @BindView(R.id.address) AutoResizeTextView address;
    @BindView(R.id.full_id) AutoResizeTextView fullId;

    @Override
    public Contract.ContractPresenter initPresenter() {
        return new UserDetailsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primarySecondScreenDark));
        }
        Picasso.with(UserDetailsActivity.this)
                .load(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.ICON_LARGE, ""))
                .into(iconContact);
        nameContact.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.NAME, ""));
        phoneNumber.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.PHONE, ""));
        email.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.EMAIL, ""));
        address.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.ADDRESS, ""));
        fullId.setText("ID: " + PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.FULL_ID, ""));

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_details;
    }
}