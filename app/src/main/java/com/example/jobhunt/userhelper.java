
package com.example.jobhunt;

public class userhelper {
    String ename,eabout,euni,eresume,econtact,eimage;

    public userhelper() {
    }

    public userhelper(String ename, String eabout, String euni, String eresume, String econtact,String eimage) {
        this.ename = ename;
        this.eabout = eabout;
        this.euni = euni;
        this.eresume = eresume;
        this.econtact = econtact;
        this.eimage = eimage;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEabout() {
        return eabout;
    }

    public void setEabout(String eabout) {
        this.eabout = eabout;
    }

    public String getEuni() {
        return euni;
    }

    public void setEuni(String euni) {
        this.euni = euni;
    }

    public String getEresume() {
        return eresume;
    }

    public void setEresume(String eresume) {
        this.eresume = eresume;
    }

    public String getEcontact() {
        return econtact;
    }

    public void setEcontact(String econtact) {
        this.econtact = econtact;
    }

    public void setEimage(String eimage) {this.eimage=eimage;}

    public String getEimage() {return eimage;}
}