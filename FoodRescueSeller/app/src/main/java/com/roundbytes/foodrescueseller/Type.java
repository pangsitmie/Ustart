package com.roundbytes.foodrescueseller;

public class Type {
    String itype, nname, ememo;

    public Type(String itype, String nname, String ememo) {
        this.itype = itype;
        this.nname = nname;
        this.ememo = ememo;
    }

    public String getItype() {
        return itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
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
