package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.content.res.Resources.NotFoundException;

public class Agenda {
    private String path;
    private ArrayList<Contact> contacts;

    public Agenda() {
	contacts = new ArrayList<Contact>();
    }

    public Agenda(String path) {
	this();
	this.path = path;
	load();
    }

    public Agenda(ArrayList<Contact> contacts) {
	this();
	this.contacts = contacts;
    }

    public Agenda(String path, ArrayList<Contact> contacts) {
	this(path);
	this.contacts = contacts;
    }

    /**
     * @return the path
     */
    public String getPath() {
	return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
	this.path = path;
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

    public boolean load() {
	contacts.clear();
	try {
	    File agendaFile = new File(path);
	    if (agendaFile.exists()) {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(agendaFile));
		String name = "";
		String phone = "";
		while ((name = bufferedReader.readLine()) != null) {
		    phone = bufferedReader.readLine();
		    Contact newContact = new Contact(name, phone);
		    contacts.add(newContact);
		}
		bufferedReader.close();
		return true;
	    } else {
		return false;
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	} catch (NotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean save() {
	BufferedWriter bufferedWriter;
	File agendaFile = new File(path);
	try {
	    if (!agendaFile.exists()) {
		agendaFile.createNewFile();
	    }
	    bufferedWriter = new BufferedWriter(new FileWriter(agendaFile));
	    for (Contact contact : contacts) {
		contact.save(bufferedWriter);
	    }
	    bufferedWriter.close();
	    return true;

	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public boolean add(Contact contact) {
	return contacts.add(contact);
    }

    public boolean remove(Contact contact) {
	return contacts.remove(contact);
    }
}
