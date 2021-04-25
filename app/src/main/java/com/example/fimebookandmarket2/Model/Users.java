package com.example.fimebookandmarket2.Model;

public class Users {
    private String id, email, name, password, phone, tipoUs;

    public Users()
    {

    }

    public Users(String id, String email, String name, String password, String phone, String tipoUs) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.tipoUs = tipoUs;
    }

    public String getTipoUs() { return tipoUs; }

    public void setTipoUs(String tipoUs) { this.tipoUs = tipoUs; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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
}
