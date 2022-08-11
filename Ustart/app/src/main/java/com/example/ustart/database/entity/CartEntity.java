package com.example.ustart.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class CartEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
    @ColumnInfo(name="ipd")
    private int ipd;
    @ColumnInfo(name="ivender")
    private String ivender;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="qquantity")
    private int qquantity;
    @ColumnInfo(name="price")
    private double price;
    @ColumnInfo(name="expdate")
    private String expdate;
    @ColumnInfo(name="imgURL")
    private String imgURL;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIpd() {
        return ipd;
    }

    public void setIpd(int ipd) {
        this.ipd = ipd;
    }

    public int getQquantity() {
        return qquantity;
    }

    public void setQquantity(int qquantity) {
        this.qquantity = qquantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIvender() {
        return ivender;
    }

    public void setIvender(String ivender) {
        this.ivender = ivender;
    }

    public String getName() {
        return name;
    }

    public void setName(String nName) {
        this.name = nName;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.ipd);
        parcel.writeInt(this.qquantity);
        parcel.writeDouble(this.price);
        parcel.writeString(this.ivender);
        parcel.writeString(this.name);
        parcel.writeString(this.expdate);
        parcel.writeString(this.imgURL);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        id = in.readInt();
        ipd = in.readInt();
        qquantity = in.readInt();
        price = in.readDouble();
        ivender = in.readString();
        name = in.readString();
        expdate = in.readString();
        imgURL = in.readString();
    }

    public static final Creator<CartEntity> CREATOR = new Creator<CartEntity>() {
        @Override
        public CartEntity createFromParcel(Parcel in) {
            return new CartEntity(in);
        }
        @Override
        public CartEntity[] newArray(int size) {
            return new CartEntity[size];
        }
    };
}
