package com.example.gohealthlyapplication;

public class equipement_medicament {
    String pid;
    String name;
    String price;
    String description ;
    String url_im;
    String equi_state ;
    Boolean encoreDisponible;
    public equipement_medicament() {
    }

    public equipement_medicament(String pid, String name, String price, String description, String url_im) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.url_im = url_im;
        this.equi_state ="Not approved";
        this.encoreDisponible=true;
    }
    public equipement_medicament(String Pid , String _name, String _descrip , String _price, String url_image, Boolean ED)
    {
        this.pid=Pid;
        this.name=_name;
        this.description =_descrip ;
        this.price=_price;
        this.url_im =url_image;
        this.encoreDisponible=ED;
        this.equi_state="Not approved";
    }


    public Boolean getEncoreDisponible() {
        return encoreDisponible;
    }

    public void setEncoreDisponible(Boolean encoreDisponible) {
        this.encoreDisponible = encoreDisponible;
    }
    /*public equipement_medicament(String Pid , String _name, String _descrip , String  _price, String url_image, Boolean ED) {
        this.pid=Pid;
        this.name=_name;
        this.description =_descrip ;
        this.price=_price;
        this.url_im =url_image;


    }*/

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl_im() {
        return url_im;
    }

    public void setUrl_im(String url_im) {
        this.url_im = url_im;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEqui_state() {
        return equi_state;
    }

    public void setEqui_state(String equi_state) {
        this.equi_state = equi_state;
    }
}
