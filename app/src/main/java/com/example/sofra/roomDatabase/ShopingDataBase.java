package com.example.sofra.roomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.sofra.roomDatabase.model.Shoping;

@Database(entities = {Shoping.class}, version = 1, exportSchema = false)
public abstract class ShopingDataBase extends RoomDatabase {

    public abstract ShopingDao shopingDao();

    private static ShopingDataBase shopingDataBase;

    public static ShopingDataBase getInstance(Context context) {

        if (shopingDataBase == null) {

            shopingDataBase = Room.databaseBuilder(context.getApplicationContext()
                    , ShopingDataBase.class, "Sofra-Database")
                    .allowMainThreadQueries()
                    .build();

        }
        return shopingDataBase;
    }

}
