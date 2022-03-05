package com.srcaterersnasik.app.recipe.create

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.ActivityListener
import com.srcaterersnasik.model.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeCreationFragment : Fragment(R.layout.fragment_recipe_creation), ActivityListener {

    private val recipeCreationViewModel: RecipeCreationViewModel by activityViewModels()

    companion object {
        fun getInstance(): RecipeCreationFragment {
            return RecipeCreationFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        initViews(view)
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.recipe_creation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                val category_selection_dropdown =
                    view?.findViewById<AutoCompleteTextView>(R.id.category_selection_dropdown)
                val adapter = category_selection_dropdown?.adapter
                val category = adapter?.getItem(0) as Category
                val recipe_name = view?.findViewById<TextInputLayout>(R.id.recipe_name)
                val ingredients = view?.findViewById<TextInputLayout>(R.id.ingredients)
                recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.CreateRecipe.Request.CreateRecipe(
                    category,
                    recipe_name?.editText?.text.toString(),
                    ingredients?.editText?.text?.toString() ?: ""))
                return true
            }
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }

        }
        return false
    }

    private fun initViews(view: View) {
        recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.FetchCategories.Request.FetchCategories)
    }

    private fun initObserver() {
        recipeCreationViewModel.getObserver().observe(viewLifecycleOwner) { response ->
            when (response) {
                is RecipeCreationViewModel.Command.CreateRecipe.Response.CreatedRecipe -> {
                    Log.d("SRCaterars", response.recipe.name ?: "")
                }

                is RecipeCreationViewModel.Command.FetchCategories.Response.Categories -> {
                    val categories = response.categories
                    // category_selector
                    val adapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
                        requireContext(),
                        R.layout.category_selection_layout,
                        categories)

                    val category_selection_dropdown =
                        view?.findViewById<AutoCompleteTextView>(R.id.category_selection_dropdown)
//                    val editTextFilledExposedDropdown: AutoCompleteTextView = category_selection_dropdown
                    category_selection_dropdown?.setAdapter(adapter)


                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}