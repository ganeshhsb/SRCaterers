package com.srcaterersnasik.app.category.listing

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.ActivityListener
import com.srcaterersnasik.databinding.FragmentCategoryListBinding
import com.srcaterersnasik.model.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment(R.layout.fragment_category_list), ActivityListener {

    private val categoryListViewModel: CategoryListViewModel by viewModels()
    private var categoryListView: RecyclerView? = null
    private var adapter: CategoryListAdapter = CategoryListAdapter()
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    interface Listener{
        fun onAddCategoryClick()
        fun onCategoryClick(category:Category)
    }
    companion object{
        fun getInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCategoryListBinding.bind(view)
        setHasOptionsMenu(true)
        initViews(view)
        initObserver()
        categoryListViewModel.processCommand(CategoryListViewModel.Command.FetchCategory.Request.FetchCategory)
    }

    private fun initObserver() {
        categoryListViewModel.getObserver().observe(viewLifecycleOwner) {
            when (it) {
                is CategoryListViewModel.Command.FetchCategory.Response.CategoryList -> {
                    adapter.clearAllItems()
                    it.categoryList.forEach { adapter.addCategory(it) }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initViews(view: View?) {
        categoryListView = view?.findViewById(R.id.category_list_rv)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        categoryListView?.layoutManager = linearLayoutManager
        categoryListView?.setHasFixedSize(true)
        adapter.setOnItemClickListener {
            (activity as? Listener)?.onCategoryClick(it)
        }
        categoryListView?.adapter = adapter // CategoryListAdapter()
        binding.floatingActionButton.setOnClickListener {
            (activity as? Listener)?.onAddCategoryClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_category_creation, menu)
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