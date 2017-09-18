package ro.project.contacts.screens.user;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.project.contacts.R;
import ro.project.contacts.custom.AutoResizeTextView;
import ro.project.contacts.mvp.BaseActivity;
import ro.project.contacts.utils.PrefUtils;

/**
 * Created by cosmin on 18.09.2017.
 */

public class UserDetailsActivity extends BaseActivity<Contract.ContractPresenter> implements Contract.ContractView {

    @BindView(R.id.details_toolbar) Toolbar toolbar;
    @BindView(R.id.icon_contact_details) ImageView iconContact;
    @BindView(R.id.name_contact_details) AutoResizeTextView nameContact;
    @BindView(R.id.phone_number) AutoResizeTextView phoneNumber;
    @BindView(R.id.email) AutoResizeTextView email;
    @BindView(R.id.address) AutoResizeTextView address;
    @BindView(R.id.full_id) AutoResizeTextView fullId;

    private ImagePopup imagePopup;

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

        init();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_details;
    }

    @Override
    public void init() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(height);
        imagePopup.setWindowWidth(width);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        imagePopup.initiatePopup(iconContact.getDrawable());

        Picasso.with(UserDetailsActivity.this)
                .load(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.ICON_LARGE, ""))
                .into(iconContact);

        nameContact.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.NAME, ""));
        phoneNumber.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.PHONE, ""));
        email.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.EMAIL, ""));
        address.setText(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.ADDRESS, ""));
        fullId.setText("ID: " + PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.FULL_ID, ""));

        Picasso.with(UserDetailsActivity.this)
                .load(PrefUtils.getSharedPreference(UserDetailsActivity.this, PrefUtils.ICON_LARGE, ""))
                .into(iconContact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(value = {R.id.email_container, R.id.phone_container, R.id.icon_contact_details, R.id.address_container})
    public void onSelectEntry(View view) {
        switch (view.getId()) {
            case R.id.email_container:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Email Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My email content");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                break;
            case R.id.phone_container:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber.getText().toString(), null));
                startActivity(phoneIntent);
                break;
            case R.id.icon_contact_details:
                imagePopup.initiatePopup(iconContact.getDrawable());
                imagePopup.viewPopup();
                break;
            case R.id.address_container:
                Intent addressIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(String.format("geo:0,0?q=%s",
                                URLEncoder.encode(address.getText().toString()))));
                startActivity(addressIntent);
                break;
            default:
                return;
        }
    }

}
