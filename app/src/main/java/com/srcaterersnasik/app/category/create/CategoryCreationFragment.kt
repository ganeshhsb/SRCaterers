package com.srcaterersnasik.app.category.create

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.ActivityListener
import com.srcaterersnasik.app.category.CategoryActivity
import com.srcaterersnasik.model.Category
import dagger.hilt.android.AndroidEntryPoint

//import kotlinx.android.synthetic.main.fragment_category_creation.category_name

//import kotlinx.android.synthetic.main.activity_category.toolbar
//import kotlinx.android.synthetic.main.fragment_category_creation.category_name

enum class MODE(val value: Int) {
    CREATE(0), EDIT(1);

    companion object {
        fun valueFrom(value: Int): MODE? {
            return MODE.values().firstOrNull { it.value == value }
        }
    }
}

@AndroidEntryPoint
class CategoryCreationFragment : Fragment(R.layout.fragment_category_creation), ActivityListener {

    private val categoryCreationViewModel: CategoryCreationViewModel by viewModels()
    private var categoryId: String? = null
    private var mode: MODE? = null

    companion object {

        const val MODE_VALUE = "mode_value"
        const val CATEGORY_ID: String = "category_id"
        fun getInstance(mode: MODE, categoryId: String = ""): CategoryCreationFragment {
            val bundle = Bundle()
            bundle.putInt(MODE_VALUE, mode.value)
            bundle.putString(CATEGORY_ID, categoryId)
            val fragment = CategoryCreationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        extractBundle()
        initViews(view)
        initObserver()
    }

    private fun extractBundle() {
        mode = MODE.valueFrom(arguments?.getInt(MODE_VALUE) ?: 0)
        categoryId = arguments?.getString(CATEGORY_ID) ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_category_creation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                val category_name = view?.findViewById<TextInputLayout>(R.id.category_name)
                categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.CreateCategory.Request.CreateCategory(
                    category_name?.editText?.text.toString()))
                return true
            }
            R.id.delete -> {
                categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.DeleteCategory.DeleteCategoryRequest())
            }
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }

        }
        return false
    }

    private fun initViews(view: View) {
        if (mode == MODE.EDIT) {
            categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.FetchCategory.Request.FetchCategory(
                categoryId ?: ""))
        }
    }

    private fun initObserver() {
        categoryCreationViewModel.getObserver().observe(viewLifecycleOwner) { response ->
            when (response) {
                is CategoryCreationViewModel.Command.CreateCategory.Response.CreateCategory -> {
                    Log.d("SRCaterars", response.category.name ?: "")
                }

                is CategoryCreationViewModel.Command.FetchCategory.Response.FetchCategory -> {
                    view?.findViewById<Toolbar>(R.id.toolbar)?.menu?.findItem(R.id.delete)?.isVisible =
                        true
                    initViewsWithCategory(response.category)
                }

                is CategoryCreationViewModel.Command.DeleteCategory.DeleteCategoryResponse -> {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun initViewsWithCategory(category: Category?) {
        category?.let { nonNullCategory ->
            view?.findViewById<TextInputLayout>(R.id.category_name)?.editText?.setText(category.name)
        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}