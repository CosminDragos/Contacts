package ro.project.contacts.screens.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.project.contacts.R;
import ro.project.contacts.adapters.ContactsAdapter;
import ro.project.contacts.custom.RecyclerViewEmptySupport;
import ro.project.contacts.custom.SimpleDividerItemDecoration;
import ro.project.contacts.forms.Contact;
import ro.project.contacts.mvp.BaseActivity;
import ro.project.contacts.utils.PrefUtils;

public class MainActivity extends BaseActivity<Contract.ContractPresenter> implements Contract.ContractView {

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.contacts_list) RecyclerViewEmptySupport recyclerViewEmptySupport;
    @BindView(R.id.no_contact_found) View emptyView;

    private RecyclerView.LayoutManager mLayoutManager;
    private ContactsAdapter contactsAdapter;

    @Override
    public Contract.ContractPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().loadContacts(false, 1);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_white_24dp));
        toolbar.setTitle(getString(R.string.main_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewEmptySupport.setHasFixedSize(true);
        recyclerViewEmptySupport.setLayoutManager(mLayoutManager);
        recyclerViewEmptySupport.setEmptyView(emptyView);
        recyclerViewEmptySupport.addItemDecoration(new SimpleDividerItemDecoration(this));

    }

    @Override
    public void setAdapter(List<Contact> contactList) {

        contactsAdapter = new ContactsAdapter(this, this, contactList, new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Contact item) {
                getPresenter().goToUserDetailsActivity(MainActivity.this, item);
            }
        });

        recyclerViewEmptySupport.setAdapter(contactsAdapter);
    }

    @Override
    public void refreshList(List<Contact> contactList) {

    }
}
