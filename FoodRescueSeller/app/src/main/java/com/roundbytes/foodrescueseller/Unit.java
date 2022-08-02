package com.roundbytes.foodrescueseller;

public class Unit {
    String iunit, nname, ememo;

    public Unit(String ipd, String nname, String ememo) {
        this.iunit = ipd;
        this.nname = nname;
        this.ememo = ememo;
    }

    public String getIunit() {
        return iunit;
    }

    public void setIunit(String iunit) {
        this.iunit = iunit;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getEmemo() {
        return ememo;
    }

    public void setEmemo(String ememo) {
        this.ememo = ememo;
    }
    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    @Override
    public String toString() {
        return nname;
    }
}
