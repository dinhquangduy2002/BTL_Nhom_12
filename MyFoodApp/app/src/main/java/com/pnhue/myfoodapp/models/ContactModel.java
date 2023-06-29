package com.pnhue.myfoodapp.models;
import java.io.Serializable;

public class ContactModel implements  Serializable{
    String name;
    String phone;
    String email;
    String text;
    String documentId;

    public ContactModel(String name, String phone, String email, String text, String documentId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.text = text;
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}