package com.srcaterersnasik.repo

import com.srcaterersnasik.model.Recipe
import com.srcaterersnasik.repo.db.RecipeDao
import java.util.UUID
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao){
    suspend fun addNewRecipe(recipeName:String, ingredients:String): Recipe {
        val recipe = getRecipeFor(recipeName,ingredients)
        recipeDao.insertAll(recipe)
        return recipe
    }

    suspend fun getAllRecipe(): List<Recipe> {
        return recipeDao.getAll()
    }

    private fun getRecipeFor(recipeName: String, ingredients:String): Recipe {
        val recipe = Recipe()
        recipe.name = recipeName
        recipe.ingredients = ingredients
        recipe.recipe_id = UUID.nameUUIDFromBytes(recipeName.toByteArray()).toString()
        return recipe
    }
}