package com.srcaterersnasik.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.srcaterersnasik.model.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): List<Person>

    @Insert
    fun insertAll(vararg person: Person)

    @Delete
    fun delete(person: Person)
}