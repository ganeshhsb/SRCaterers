package com.srcaterersnasik.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.srcaterersnasik.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Insert
    fun insertAll(vararg recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}