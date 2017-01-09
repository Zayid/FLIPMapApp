package com.whackyard.flipmapapp;

/**
 * Created by Nazila on 07/01/2017.
 */

public class Contact {

    int id;
    String name;
    String mobile_number;
    String landline_number;
    String email;
    String mode;
    String icon;

    public Contact(){
    }

    public Contact(int id){
        super();
        this.id = id;
    }

    public Contact(String name, String email, String mobile_number, String landline_number, String icon) {
        super();
        this.name = name;
        this.mobile_number = mobile_number;
        this.landline_number = landline_number;
        this.email = email;
        this.icon = icon;
    }

    public Contact(String name, String email, String mobile_number, String landline_number) {
        super();
        this.name = name;
        this.mobile_number = mobile_number;
        this.landline_number = landline_number;
        this.email = email;
    }

    public Contact(int id, String name, String email, String mobile_number, String landline_number, String mode) {
        this.id = id;
        this.name = name;
        this.mobile_number = mobile_number;
        this.landline_number = landline_number;
        this.email = email;
        this.mode = mode;
    }

    public Contact(String name, String email, String mobile_number, String landline_number,  String icon, String mode) {
        this.name = name;
        this.mobile_number = mobile_number;
        this.landline_number = landline_number;
        this.email = email;
        this.icon = icon;
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getLandline_number() {
        return landline_number;
    }

    public void setLandline_number(String landline_number) {
        this.landline_number = landline_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
