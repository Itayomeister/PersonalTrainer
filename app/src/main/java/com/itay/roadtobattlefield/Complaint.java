package com.itay.roadtobattlefield;

public class Complaint {
    private  String name, email, phone, complaint;

    public Complaint(String name, String email, String phone, String complaint) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
