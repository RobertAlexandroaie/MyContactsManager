package com.fii.mycontactsmanager;

import model.Agenda;
import model.Contact;
import model.adapter.ContactsArrayAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private Agenda agenda;
    private ContactsArrayAdapter adapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Intent intent = getIntent();
	String action = intent.getAction();
	String type = intent.getType();

	if (Intent.ACTION_SEND.equals(action) && type != null) {
	    if ("application/cnt".equals(type)) {
		agenda = new Agenda(intent.getExtras().getString("filePath"));
		load();
	    }
	} else {
	    agenda = new Agenda(getResources().getString(R.string.save_path));
	    load();
	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;
	switch (item.getItemId()) {
	case R.id.add_contact:
	    builder = new AlertDialog.Builder(this);
	    LayoutInflater inflater = this.getLayoutInflater();
	    final View view = inflater.inflate(R.layout.modify_contact, null);
	    builder.setView(view);
	    builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		    EditText nameET = (EditText) view.findViewById(R.id.edit_name_et);
		    final Contact newContact = new Contact();
		    if (nameET != null) newContact.setName(nameET.getText().toString());

		    EditText phoneET = (EditText) view.findViewById(R.id.edit_phone_et);
		    if (phoneET != null) newContact.setPhone(phoneET.getText().toString());

		    Toast success = Toast.makeText(MainActivity.this, "The contact " + newContact.getName()
			    + " has been created.", Toast.LENGTH_SHORT);

		    Toast fail = Toast.makeText(MainActivity.this, "Contact creation failed!",
			    Toast.LENGTH_SHORT);
		    agenda.add(newContact);
		    if (agenda.save()) {
			success.show();
			load();
		    } else {
			fail.show();
		    }
		}
	    });
	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	    });
	    alertDialog = builder.create();
	    alertDialog.show();

	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	// TODO Auto-generated method stub
	super.onCreateContextMenu(menu, v, menuInfo);
	getMenuInflater().inflate(R.menu.contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
	final Contact contact = adapter.getItem(pos);
	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;

	switch (item.getItemId()) {
	case R.id.modify:
	    builder = new AlertDialog.Builder(this);
	    LayoutInflater inflater = this.getLayoutInflater();
	    builder.setTitle(contact.getName());
	    final View view = inflater.inflate(R.layout.modify_contact, null);
	    builder.setView(view);
	    builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		    EditText nameET = (EditText) view.findViewById(R.id.edit_name_et);
		    if (nameET != null) contact.setName(nameET.getText().toString());

		    EditText phoneET = (EditText) view.findViewById(R.id.edit_phone_et);
		    if (phoneET != null) contact.setPhone(phoneET.getText().toString());

		    Toast success = Toast.makeText(MainActivity.this, "The contact " + contact.getName()
			    + " has been modified.", Toast.LENGTH_SHORT);

		    Toast fail = Toast.makeText(MainActivity.this, "Contact modification failed!",
			    Toast.LENGTH_SHORT);

		    if (agenda.save()) {
			success.show();
			load();
		    } else {
			fail.show();
		    }
		    load();
		}
	    });
	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	    });
	    alertDialog = builder.create();
	    alertDialog.show();

	    EditText nameET = (EditText) alertDialog.findViewById(R.id.edit_name_et);
	    nameET.setText(contact.getName());

	    EditText phoneET = (EditText) alertDialog.findViewById(R.id.edit_phone_et);
	    phoneET.setText(contact.getPhone());

	    return true;
	case R.id.delete:
	    builder = new AlertDialog.Builder(this);
	    builder.setTitle(contact.getName());
	    builder.setMessage("Are you sure you want to delete " + contact.getName() + "?");
	    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {

		    Toast success = Toast.makeText(MainActivity.this, "The contact " + contact.getName()
			    + " has been modified.", Toast.LENGTH_SHORT);

		    Toast fail = Toast.makeText(MainActivity.this, "Contact modification failed!",
			    Toast.LENGTH_SHORT);

		    if (agenda.remove(contact) && agenda.save()) {
			success.show();
			load();
		    } else {
			fail.show();
		    }
		    load();
		}
	    });
	    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	    });
	    alertDialog = builder.create();
	    alertDialog.show();

	    return true;
	case R.id.call:
	    Intent intent = new Intent(Intent.ACTION_CALL);
	    intent.setData(Uri.parse("tel:" + contact.getPhone()));
	    startActivity(intent);

	    return true;
	default:
	    return super.onContextItemSelected(item);
	}
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
	super.onListItemClick(listView, view, position, id);
	this.registerForContextMenu(view);
	view.showContextMenu();
	pos = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
    }

    private boolean load() {
	if (!agenda.load()) {
	    return false;
	} else {
	    adapter = new ContactsArrayAdapter(this, R.layout.contact_view, agenda.getContacts());
	    this.setListAdapter(adapter);
	    return true;
	}
    }
}
