package com.example.ustart.data;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;

import javax.sql.DataSource;

public interface FooadableDao {
    @Query("SELECT * FROM cartentities")
    ArrayList<CartEntity> getCarts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartEntity(CartEntity cartEntity);

    @Delete
    void deleteCartEntity(CartEntity cartEntity);
}
