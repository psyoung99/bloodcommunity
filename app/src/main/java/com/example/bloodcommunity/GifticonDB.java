package com.example.bloodcommunity;

import java.util.HashMap;
import java.util.Map;

public class GifticonDB {
    public String id;
    public String barcode;
    public String imagePath;
    public String name;
    public String due;

    public GifticonDB() {
        //DataSnapshot.getValue(GifticonDB.class)에 대한 콜 호출
    }

    public GifticonDB(String id, String barcode, String imagePath, String name, String due) {
        this.id = id;
        this.barcode = barcode;
        this.imagePath = imagePath;
        this.name = name;
        this.due = due;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("barcode", barcode);
        result.put("imagePath", imagePath);
        result.put("name",name);
        result.put("due",due);

        return result;
    }

    public String getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDue() {
        return due;
    }

}


