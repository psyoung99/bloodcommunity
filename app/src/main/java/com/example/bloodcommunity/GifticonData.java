package com.example.bloodcommunity;

import android.widget.ImageView;

public class GifticonData {

    private String ImagePath;
    private String barcode;
    private String name;
    private String due;

    public String getImagePath() {
        return ImagePath;
    }
    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public String getBarcode() {return barcode;}
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDue() {return due;}
    public void setDue(String due) {this.due = due;}
}

