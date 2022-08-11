package com.example.ustart.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ustart.database.entity.CartEntity;

import java.util.List;

@Dao
public abstract interface CartDAO {
    @Query("SELECT * FROM cart")
    List<CartEntity> getAllCart();

    @Query("SELECT * FROM cart WHERE id=:id ")
    CartEntity getCart(int id);

    @Insert
    void insert(CartEntity cart);

    @Update
    void update(CartEntity cart);

    @Delete
    void delete(CartEntity cart);
}