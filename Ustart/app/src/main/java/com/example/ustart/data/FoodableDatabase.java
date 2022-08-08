package com.example.ustart.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartEntity.class}, version = 1, exportSchema = false)
public abstract class FoodableDatabase extends RoomDatabase {
    public abstract FooadableDao fooadableDao();

    private static volatile FoodableDatabase INSTANCE;

    public static FoodableDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (FoodableDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),FoodableDatabase.class, "FoodableData.db").build();
            }
        }
        return INSTANCE;
    }
}
