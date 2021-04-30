package com.example.fimebookandmarket2.Model;

public class Users {
    private String id, email, name, password, phone, tipoUs, image, address;

    public Users()
    {

    }

    public Users(String id, String email, String name, String password, String phone, String tipoUs, String image, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.tipoUs = tipoUs;
        this.image = image;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTipoUs() {
        return tipoUs;
    }

    public void setTipoUs(String tipoUs) {
        this.tipoUs = tipoUs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
