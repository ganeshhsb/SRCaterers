package com.srcaterersnasik.app.recipe

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.srcaterersnasik.R
import com.srcaterersnasik.app.recipe.listing.RecipeListFragment
import com.srcaterersnasik.app.recipe.listing.RecipeListViewModel
import com.srcaterersnasik.model.Recipe
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories

class RecipeActivity : AppCompatActivity(), RecipeListFragment.Listener {
    val recipeListViewModel: RecipeListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initObserver()
        setContent {
            Surface(color = MaterialTheme.colors.surface) {
                RecipeListScreen(
                    navigationClick = { onBackPressed() },
                    onAddClick = { onBackPressed() },
                    onItemClick = { recipe ->

                    }, recipeListViewModel = recipeListViewModel)
            }
        }
    }

    override fun onAddRecipeClick() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initObserver() {
//        recipeListViewModel.getObserver().observe(this) {
//            when (it) {
//                is RecipeListViewModel.Command.FetchRecipe.Response.RecipeList -> {
//
//                    it.recipeList.forEach { adapter.addRecipe(it) }
//                    adapter.notifyDataSetChanged()
//                }
//            }
//        }
    }

}
//@AndroidEntryPoint
//class RecipeActivity : AppCompatActivity(), RecipeListFragment.Listener {
//
//    private lateinit var binding: ActivityRecipeBinding
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityRecipeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
//
//        addRecipeFragment()
//    }
//
//    private fun addRecipeFragment() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, RecipeListFragment.getInstance())
//        transaction.commitNowAllowingStateLoss()
//    }
//
//    private fun addRecipeCreationFragment() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, RecipeCreationFragment.getInstance())
//        transaction.addToBackStack("RecipeCreationFragment")
//        transaction.commit()
//    }
//
//    override fun onAddRecipeClick() {
//        addRecipeCreationFragment()
//    }
//
//    override fun onBackPressed() {
//        val isBackHandled =(getCurrentFragment() as ActivityListener).onBackPressed()
//        if(!isBackHandled){
//            super.onBackPressed()
//        }
//    }
//
//    private fun getCurrentFragment(): Fragment? {
//        return supportFragmentManager.findFragmentById(R.id.container)
//    }
//}

@Composable
fun RecipeListScreen(
    navigationClick: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (recipe: Recipe) -> Unit,
    recipeListViewModel: RecipeListViewModel = viewModel(),
) {
    val recipes = recipeListViewModel.getObserver().observeAsState()
    SideEffect {
        recipeListViewModel.processCommand(RecipeListViewModel.Command.FetchRecipe.Request.FetchRecipe)
    }
    Scaffold(topBar = {
        RecipeScreenToolbar(
            title = "Recipe List",
            navigationIcon = Icons.Default.ArrowBack,
            navigationClick = navigationClick)
    },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, "")
            }
        },
        content = {
            when (val recipeListResponse = recipes.value) {

                is RecipeListViewModel.Command.FetchRecipe.Response.RecipeList -> {
                    val recipeList = recipeListResponse.recipeList
                    recipeList.forEach { recipe ->
                        Column(modifier = Modifier.clickable { onItemClick(recipe) }) {
                            Text(text = recipe.name ?: "")
                        }
                    }
                }
            }
        })
}

@Composable
fun RecipeScreenToolbar(
    title: String,
    navigationIcon: ImageVector,
    navigationClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(
                content = {
                    Icon(imageVector = navigationIcon, contentDescription = stringResource(
                        id = R.string.back))
                },
                onClick = { navigationClick() }
            )
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}