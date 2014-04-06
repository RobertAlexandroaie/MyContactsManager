package model.adapter;

import java.util.ArrayList;

import model.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fii.mycontactsmanager.R;

public class ContactsArrayAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int id;
    private ArrayList<Contact> contacts;

    public ContactsArrayAdapter(Context context, int textViewResourceId, ArrayList<Contact> objects) {
	super(context, textViewResourceId, objects);
	this.context = context;
	id = textViewResourceId;
	contacts = objects;
    }

    /**
     * @return the context
     */
    public Context getContext() {
	return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(Context context) {
	this.context = context;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the contacts
     */
    public ArrayList<Contact> getContacts() {
	return contacts;
    }

    /**
     * @param contacts
     *            the contacts to set
     */
    public void setContacts(ArrayList<Contact> contacts) {
	this.contacts = contacts;
    }

    public Contact getItem(int i) {
	return contacts.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View view = convertView;
	if (view == null) {
	    LayoutInflater viewInflater = (LayoutInflater) context
		    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = viewInflater.inflate(id, null);
	}

	final Contact contact = contacts.get(position);
	if (contact != null) {
	    TextView name_tv = (TextView) view.findViewById(R.id.contact_name_tv);
	    TextView phone_tv = (TextView) view.findViewById(R.id.contact_number_tv);

	    if (name_tv != null) {
		name_tv.setText(contact.getName());
	    }
	    if (phone_tv != null) {
		phone_tv.setText(contact.getPhone());
	    }
	}
	return view;
    }
}
