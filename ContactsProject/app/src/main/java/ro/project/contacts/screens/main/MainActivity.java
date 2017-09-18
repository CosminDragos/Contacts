package ro.project.contacts.screens.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.project.contacts.R;
import ro.project.contacts.adapters.ContactsAdapter;
import ro.project.contacts.custom.RecyclerViewEmptySupport;
import ro.project.contacts.custom.SimpleDividerItemDecoration;
import ro.project.contacts.forms.Contact;
import ro.project.contacts.forms.Load;
import ro.project.contacts.mvp.BaseActivity;
import ro.project.contacts.utils.PrefUtils;

public class MainActivity extends BaseActivity<Contract.ContractPresenter> implements Contract.ContractView {

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.contacts_list) RecyclerViewEmptySupport recyclerViewEmptySupport;
    @BindView(R.id.no_contact_found) View emptyView;
    @BindView(R.id.progress_container_main) View progressView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    private ContactsAdapter contactsAdapter;
    private int page = 1;

    @Override
    public Contract.ContractPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().loadContacts(false, page);
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

        recyclerViewEmptySupport.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewEmptySupport.setLayoutManager(mLayoutManager);
        recyclerViewEmptySupport.setEmptyView(emptyView);
        recyclerViewEmptySupport.addItemDecoration(new SimpleDividerItemDecoration(this));

        recyclerViewEmptySupport.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ?
                        0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadContacts(false, page);
            }
        });

        if (!mSwipeRefreshLayout.isRefreshing()) {
            progressView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void setAdapter(List<Contact> contactList) {

        contactsAdapter = new ContactsAdapter(this, this, contactList, new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Contact item) {
                getPresenter().goToUserDetailsActivity(MainActivity.this, item);
            }
        });

        contactsAdapter.setLoadMoreListener(new ContactsAdapter.OnLoadMoreListener() {
            @Override
            public void loadMoreContacts() {

                recyclerViewEmptySupport.post(new Runnable() {
                    @Override
                    public void run() {
                        if (page <= 3) {
                            int position = contactsAdapter.addItem(new Load("Loading"));
                            contactsAdapter.notifyItemInserted(position);
                            getPresenter().loadContacts(true, page);
                        } else {
                            contactsAdapter.setLoading(false);
                            contactsAdapter.setMoreDataAvailable(false);
                            contactsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        recyclerViewEmptySupport.setAdapter(contactsAdapter);
        page = (!contactList.isEmpty()) ? page+1 : page;

        mSwipeRefreshLayout.setRefreshing(false);
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void refreshList(List<Contact> contactList) {
        page = (!contactList.isEmpty()) ? page+1 : page;
        contactsAdapter.removeItem(contactsAdapter.getItemCount()-1);
        contactsAdapter.addMore(contactList);
        contactsAdapter.setLoading(false);
        contactsAdapter.notifyDataSetChanged();
    }
}
