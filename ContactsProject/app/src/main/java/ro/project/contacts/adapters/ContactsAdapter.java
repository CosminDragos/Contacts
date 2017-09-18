package ro.project.contacts.adapters;

import android.app.Activity;
import android.content.Context;
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

/**
 * Created by cosmin on 18.09.2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private final LayoutInflater mInflater;
    private final OnItemClickListener listener;
    private List<Contact> contactList = new ArrayList<Contact>();

    public ContactsAdapter(Context mContext, Activity mActivity, List<Contact> contactList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        mInflater = LayoutInflater.from(mContext);
        this.contactList = contactList;
        this.listener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconContact;
        public AutoResizeTextView nameContact;
        public AutoResizeTextView infoContact;
        public AutoResizeTextView dateRegisterContact;

        public ViewHolder(View itemView, int viewType) {
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

    public interface OnItemClickListener {
        void onItemClick(int position, Contact item);
    }

}
