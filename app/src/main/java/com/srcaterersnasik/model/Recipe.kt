package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
class Recipe {
    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    var recipe_id:String = ""

    @ColumnInfo(name="name")
    var name:String? = null

    @ColumnInfo(name = "ingredients")
    var ingredients:String? = null

    fun getCopyOf():Recipe{
        val newRecipe = Recipe()
        newRecipe.recipe_id = recipe_id
        newRecipe.name = name
        newRecipe.ingredients = ingredients
        return newRecipe
    }
}