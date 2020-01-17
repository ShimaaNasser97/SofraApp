package com.example.sofra.roomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sofra.roomDatabase.model.Shoping;

import java.util.List;

@Dao
public interface ShopingDao {
    @Insert
    public void addShoping(Shoping items);

    @Delete
    public void removeShoping(Shoping items);

    @Update
    public void updateShoping(Shoping items);

    @Query("select * from shoping;")
    List<Shoping> getAllShoping();

    @Query("Delete from shoping;")
    void deleteAllShoping();


}
