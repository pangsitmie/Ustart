package com.example.ustart.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ustart.database.dao.CartDAO;
import com.example.ustart.database.entity.CartEntity;


@Database(entities={CartEntity.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    private static AppDatabase INSTANCE;
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE==null)
        {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class,
                    "FoodableDB").build();
        }
        return INSTANCE;
    }
}
