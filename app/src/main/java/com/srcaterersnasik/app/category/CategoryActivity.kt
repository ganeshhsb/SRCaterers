package com.srcaterersnasik.app.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.create.CategoryCreationFragment
import com.srcaterersnasik.app.category.create.MODE
import com.srcaterersnasik.app.category.listing.CategoryListFragment
import com.srcaterersnasik.databinding.ActivityCategoryBinding
import com.srcaterersnasik.model.Category
import dagger.hilt.android.AndroidEntryPoint

interface ActivityListener {
    fun onBackPressed(): Boolean
}

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity(), CategoryListFragment.Listener {

    private lateinit var binding: ActivityCategoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        addCategoryFragment()
    }

    private fun addCategoryFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CategoryListFragment.getInstance())
        transaction.commitNowAllowingStateLoss()
    }

    override fun onAddCategoryClick() {
        addCategoryCreationFragment()
    }

    override fun onCategoryClick(category: Category) {
        addCategoryCreationFragmentWithEditMode(category)
    }

    private fun addCategoryCreationFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CategoryCreationFragment.getInstance(MODE.CREATE))
        transaction.addToBackStack("CategoryCreationFragment")
        transaction.commit()
    }

    private fun addCategoryCreationFragmentWithEditMode(category: Category) {
        val fragment = CategoryCreationFragment.getInstance(MODE.EDIT,
            category.category_id)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack("CategoryCreationFragment")
        transaction.commit()
    }

    override fun onBackPressed() {
        val isBackHandled = (getCurrentFragment() as ActivityListener).onBackPressed()
        if (!isBackHandled) {
            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.container)
    }
}