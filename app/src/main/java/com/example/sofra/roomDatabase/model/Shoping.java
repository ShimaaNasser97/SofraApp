package com.example.sofra.roomDatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.widget.RecyclerView;

@Entity
public class Shoping {

    @PrimaryKey(autoGenerate = true)
    int id;



    @ColumnInfo
    String itemId;

    @ColumnInfo
    String path;

    @ColumnInfo
    String name;

    @ColumnInfo
    String price;

    @ColumnInfo
    String discription;
    @ColumnInfo
    String content;

    @ColumnInfo
    String quantity;

    public Shoping() {

    }
    @Ignore
    public Shoping(String itemId , String path,String name, String price, String discription, String content, String quantity) {
        this.path = path;
        this.name = name;
        this.price = price;
        this.discription = discription;
        this.content = content;
        this.quantity = quantity;
        this.itemId=itemId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
