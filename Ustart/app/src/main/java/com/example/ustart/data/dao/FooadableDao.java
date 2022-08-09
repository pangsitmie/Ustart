package com.example.ustart.data.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ustart.data.entity.CartEntity;
import com.example.ustart.data.entity.ItemEntity;

import java.util.ArrayList;

public interface FooadableDao {
    //cart entity
    @Query("SELECT * FROM cartentities")
    ArrayList<CartEntity> getAllCarts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartEntity(CartEntity cartEntity);

    @Delete
    void deleteCartEntity(CartEntity cartEntity);

    //item entity

    @Query("SELECT * FROM itementities")
    ArrayList<ItemEntity> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemEntity(ItemEntity itemEntity);

    @Delete
    void deleteItemEntity(ItemEntity itemEntity);


}
