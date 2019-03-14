package com.example.samar.firebaseintro.MODEL;

public class Link {


    public  String linkcode;
    public  String linkvalue;
    public   String linkdesc;
    public  String linkaddnl;

    public Link() {
    }


    public Link(String linkcode, String linkvalue, String linkdesc,String linkaddnl) {
        this.linkcode = linkcode;
        this.linkvalue = linkvalue;
        this.linkdesc = linkdesc;
        this.linkaddnl = linkaddnl;

    }


    public String getLinkcode() {
        return linkcode;
    }

    public void setLinkcode(String linkcode) {
        this.linkcode = linkcode;
    }

    public String getLinkvalue() {
        return linkvalue;
    }

    public void setLinkvalue(String linkvalue) {
        this.linkvalue = linkvalue;
    }

    public String getLinkdesc() {
        return linkdesc;
    }

    public void setLinkdesc(String linkdesc) {
        this.linkdesc = linkdesc;
    }

    public  String getLinkaddnl(){return linkaddnl;}
}
