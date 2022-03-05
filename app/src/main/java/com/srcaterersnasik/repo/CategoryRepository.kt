package com.srcaterersnasik.repo

import com.srcaterersnasik.model.Category
import com.srcaterersnasik.repo.db.CategoryDao
import java.util.UUID
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {
    suspend fun addNewCategory(categoryName: String): Category {
        val category = getCategoryFor(categoryName)
        categoryDao.insertAll(category)
        return category
    }

    suspend fun replaceCategory(categoryName: String, categoryInDB: Category): Category {
        val category = getCategoryFor(categoryName)
        categoryDao.delete(categoryInDB)
        categoryDao.insertAll(category)
        return category
    }

    suspend fun getCategoryById(categoryId: String): Category? {
        return categoryDao.getCategoryById(categoryId).firstOrNull()
    }

    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAll()
    }

    suspend fun deleteCategory(category: Category) {
        return categoryDao.delete(category)
    }

    private fun getCategoryFor(categoryName: String): Category {
        val category = Category()
        category.name = categoryName
        category.category_id = UUID.nameUUIDFromBytes(categoryName.toByteArray()).toString()
        return category
    }
}