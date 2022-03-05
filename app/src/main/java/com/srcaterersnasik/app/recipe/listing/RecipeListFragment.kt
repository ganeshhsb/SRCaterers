package com.srcaterersnasik.app.recipe.listing

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.ActivityListener
import com.srcaterersnasik.databinding.FragmentRecipeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment(R.layout.fragment_recipe_list), ActivityListener {

    private val recipeListViewModel: RecipeListViewModel by activityViewModels()
    private var recipeListView: RecyclerView? = null
    private var adapter: RecipeListAdapter = RecipeListAdapter()
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    interface Listener{
        fun onAddRecipeClick()
    }
    companion object{
        fun getInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentRecipeListBinding.bind(view)
        setHasOptionsMenu(true)
        initViews(view)
        initObserver()
        recipeListViewModel.processCommand(RecipeListViewModel.Command.FetchRecipe.Request.FetchRecipe)
    }

    private fun initObserver() {
        recipeListViewModel.getObserver().observe(viewLifecycleOwner) {
            when (it) {
                is RecipeListViewModel.Command.FetchRecipe.Response.RecipeList -> {

                    it.recipeList.forEach { adapter.addRecipe(it) }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initViews(view: View?) {
        recipeListView = view?.findViewById(R.id.recipe_list_rv)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recipeListView?.layoutManager = linearLayoutManager
        recipeListView?.setHasFixedSize(true)
        recipeListView?.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            (activity as? Listener)?.onAddRecipeClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.recipe_creation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }

        }
        return false
    }

    override fun onBackPressed(): Boolean {
        return false
    }


}