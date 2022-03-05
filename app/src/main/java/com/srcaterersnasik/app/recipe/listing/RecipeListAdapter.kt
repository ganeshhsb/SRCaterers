package com.srcaterersnasik.app.recipe.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srcaterersnasik.R
import com.srcaterersnasik.model.Recipe

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterHolder>() {
    private val recipeList:ArrayList<Recipe> = ArrayList()
    class RecipeListAdapterHolder(view: View): RecyclerView.ViewHolder(view) {
        var recipeName = view.findViewById<TextView>(R.id.recipe_tv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecipeListAdapterHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recipe,parent,false)
        return RecipeListAdapterHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecipeListAdapterHolder,
        position: Int,
    ) {
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun addRecipes(recipes:ArrayList<Recipe>){
        recipeList.addAll(recipes)
    }

    fun addRecipe(recipe: Recipe){
        recipeList.add(recipe)
    }

    fun clearAllItems(){
        recipeList.clear()
    }
}