package model;

import java.io.BufferedWriter;
import java.io.IOException;

public class Contact {
    private String name;
    private String phone;

    public Contact() {

    }

    public Contact(String name) {
	this.name = name;
    }

    public Contact(String name, String phone) {
	this(name);
	this.phone = phone;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
	return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
	this.phone = phone;
    }

    public void save(BufferedWriter writer) {
	try {
	    writer.append(name + System.getProperty("line.separator"));
	    writer.append(phone + System.getProperty("line.separator"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
