package ro.project.contacts.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ro.project.contacts.R;
import ro.project.contacts.custom.AutoResizeTextView;
import ro.project.contacts.forms.Contact;
import ro.project.contacts.forms.Load;

/**
 * Created by cosmin on 18.09.2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private Activity mActivity;
    private final LayoutInflater mInflater;
    private final OnItemClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false;
    private boolean isMoreDataAvailable = true;
    private List<Contact> contactList = new ArrayList<Contact>();

    public ContactsAdapter(Context mContext, Activity mActivity, List<Contact> contactList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        mInflater = LayoutInflater.from(mContext);
        this.contactList = contactList;
        this.listener = listener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.onLoadMoreListener = loadMoreListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER) {
            View itemView = mInflater.inflate(R.layout.progress_adapter, parent, false);
            return new LoadHolder(itemView);
        }
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(itemView);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public int addItem(Load item) {
        contactList.add(item);
        return contactList.size()-1;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.isMoreDataAvailable = moreDataAvailable;
    }

    public void removeItem(int position) {
        contactList.remove(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {

        } else if (getItemViewType(position) == TYPE_ITEM) {

            try {
                Picasso.with(mContext)
                        .load(contactList.get(position).getPicture().getLarge())
                        .into(holder.iconContact);
                holder.nameContact.setText(contactList.get(position).getName().getFullName());
                holder.infoContact.setText(contactList.get(position).getAge() + " years from " +
                        contactList.get(position).getNat());
                holder.dateRegisterContact.setText(contactList.get(position).getTimeDateRegistered());
                holder.bind(position, contactList.get(position), listener);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if ( (position >= getItemCount()-1) &&
                (isMoreDataAvailable) &&
                (!isLoading) &&
                (onLoadMoreListener != null) ) {
            isLoading = true;
            onLoadMoreListener.loadMoreContacts();
        }
    }

    public void addMore(List<Contact> contactList) {
        this.contactList.addAll(contactList);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (contactList.get(position) instanceof Load){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconContact;
        public AutoResizeTextView nameContact;
        public AutoResizeTextView infoContact;
        public AutoResizeTextView dateRegisterContact;

        public ViewHolder(View itemView) {
            super(itemView);
            iconContact = (ImageView) itemView.findViewById(R.id.icon_contact);
            nameContact = (AutoResizeTextView) itemView.findViewById(R.id.name_contact);
            infoContact = (AutoResizeTextView) itemView.findViewById(R.id.info_contact);
            dateRegisterContact = (AutoResizeTextView) itemView.findViewById(R.id.date_register_contact);
        }

        public void bind(final int position, final Contact item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position, item);
                }

            });
        }

    }

    static class LoadHolder extends ViewHolder {

        public View progressHolder;

        public LoadHolder(View itemView) {
            super(itemView);
            progressHolder = itemView.findViewById(R.id.progress_container);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, Contact item);
    }

    public interface OnLoadMoreListener {
        public void loadMoreContacts();
    }

}
