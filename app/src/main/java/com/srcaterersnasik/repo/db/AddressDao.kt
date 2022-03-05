package com.srcaterersnasik.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.srcaterersnasik.model.Address

@Dao
interface AddressDao {
    @Query("SELECT * FROM address")
    fun getAll(): List<Address>

    @Insert
    fun insertAll(vararg address: Address)

    @Delete
    fun delete(user: Address)
}