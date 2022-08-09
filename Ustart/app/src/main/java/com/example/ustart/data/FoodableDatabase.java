package com.example.ustart.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ustart.data.dao.FooadableDao;
import com.example.ustart.data.entity.CartEntity;
import com.example.ustart.data.entity.ItemEntity;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = false)
public abstract class FoodableDatabase extends RoomDatabase {
    public abstract FooadableDao fooadableDao();

    private static volatile FoodableDatabase INSTANCE;

    public static final String DATABASE_NAME = "FoodableData.db";

    public static FoodableDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (FoodableDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),FoodableDatabase.class,DATABASE_NAME ).build();
            }
        }
        return INSTANCE;
    }
}
