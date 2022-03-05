package com.srcaterersnasik.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.srcaterersnasik.model.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM `order`")
    fun getAll(): List<Order>

    @Insert
    fun insertAll(vararg order: Order)

    @Delete
    fun delete(order: Order)
}