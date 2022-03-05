package com.srcaterersnasik.repo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.srcaterersnasik.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE  category_id IN (:categoryId)")
    fun getCategoryById(categoryId: String): List<Category>

    @Insert
    suspend fun insertAll(vararg category: Category)

    @Delete
    suspend fun delete(category: Category)
}