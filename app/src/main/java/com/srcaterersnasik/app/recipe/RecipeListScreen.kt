package com.srcaterersnasik.app.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.srcaterersnasik.R
import com.srcaterersnasik.app.AppToolbar
import com.srcaterersnasik.app.CategoryScreens
import com.srcaterersnasik.app.RecipesScreens
import com.srcaterersnasik.app.category.AppText
import com.srcaterersnasik.app.category.TopBarAction
import com.srcaterersnasik.app.category.create.CategoryCreationViewModel
import com.srcaterersnasik.app.category.create.MODE
import com.srcaterersnasik.app.category.listing.CategoryListViewModel
import com.srcaterersnasik.app.recipe.create.RecipeCreationViewModel
import com.srcaterersnasik.app.recipe.listing.RecipeListViewModel
import com.srcaterersnasik.model.Category
import com.srcaterersnasik.model.Recipe
import com.srcaterersnasik.model.Selectable

@Composable
fun RecipeListScreen(
    navHostController: NavHostController,
    recipeListViewModel: RecipeListViewModel = hiltViewModel(),
    recipeListScreenContent: @Composable (
        recipes: State<RecipeListViewModel.Command.FetchRecipe.Response?>,
        navHostController: NavHostController,
    ) -> Unit,
) {
    val categories = recipeListViewModel.getObserver().observeAsState()
    LaunchedEffect(true) {
        recipeListViewModel.processCommand(RecipeListViewModel.Command.FetchRecipe.Request.FetchRecipe)
    }
    Scaffold(topBar = {
        AppToolbar(stringResource(id = R.string.recipe),
            Icons.Filled.ArrowBack,
            { navHostController.popBackStack() })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(RecipesScreens.RecipesDetail.route.plus("/${MODE.CREATE.value}"))
            }) {
                Icon(Icons.Filled.Add, "")
            }
        }) {
        recipeListScreenContent(categories, navHostController)
    }
}

@Composable
fun RecipeListScreenContent(
    recipes: State<RecipeListViewModel.Command.FetchRecipe.Response?>,
    navHostController: NavHostController,
) {
    when (val recipeListResponse = recipes.value) {

        is RecipeListViewModel.Command.FetchRecipe.Response.RecipeList -> {
            val recipeList = recipeListResponse.recipeList
            LazyColumn() {
                items(recipeList.size) { index ->
                    val recipe = recipeList[index]

                    val onClickNavigation = {
                        val route =
                            CategoryScreens.CategoryDetail.route
                                .plus("/${MODE.EDIT.value}")
                                .plus("?")
                                .plus("recipeId=${recipe.recipe_id}")
                        navHostController.navigate(route)
                    }
                    AppText(recipe.name ?: "", onClickNavigation)
                }
            }
        }
    }
}

@Composable
fun RecipeDetailScreen(
    navHostController: NavHostController,
    recipeCreationViewModel: RecipeCreationViewModel = viewModel(),
    recipeId: String,
    mode: Int,
    onBackPress: () -> Unit,
) {
    val (recipeValue, setRecipeValue) = remember { mutableStateOf(Recipe()) }
    val (categoryListValue, setCategoryListValue) = remember { mutableStateListOf(mutableListOf<Category>()) }
    val selectedCategory = remember { mutableStateOf<Selectable>(Category()) }
    val modeEnum = remember { MODE.valueFrom(mode) }
    val title = if (modeEnum == MODE.CREATE) {
        stringResource(id = R.string.recipe_create)
    } else {
        stringResource(id = R.string.recipe_edit)
    }
    val recipeCreateViewModelResponse = recipeCreationViewModel.getObserver().observeAsState()
    val categoriesResponse = recipeCreationViewModel.getObserver().observeAsState()

    when (recipeCreateViewModelResponse.value) {
        is RecipeCreationViewModel.Command.CreateRecipe.Response.CreatedRecipe -> {
            onBackPress()
        }

        is RecipeCreationViewModel.Command.FetchRecipe -> {
            val recipe =
                if (recipeCreateViewModelResponse.value is RecipeCreationViewModel.Command.FetchRecipe.Response.FetchRecipe) {
                    (recipeCreateViewModelResponse.value as RecipeCreationViewModel.Command.FetchRecipe.Response.FetchRecipe).recipe
                } else {
                    Recipe()
                }
            setRecipeValue(recipe)
        }

        is RecipeCreationViewModel.Command.DeleteRecipe.Response.DeleteResponse -> {
            onBackPress()
        }
        else -> {
            // Do nothing
        }
    }
    when (categoriesResponse.value) {
        is RecipeCreationViewModel.Command.FetchCategories.Response.Categories -> {
            val categories =
                if (recipeCreateViewModelResponse.value is RecipeCreationViewModel.Command.FetchCategories.Response.Categories) {
                    (recipeCreateViewModelResponse.value as RecipeCreationViewModel.Command.FetchCategories.Response.Categories).categories
                } else {
                    emptyList()
                }
            setCategoryListValue.clear()
            setCategoryListValue.addAll(categories)
        }
        else -> {
            // Do nothing
        }
    }
    LaunchedEffect(key1 = true) {
        recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.FetchCategories.Request.FetchCategories)
        if (modeEnum == MODE.EDIT) {
            recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.FetchRecipe.Request.Fetch(
                recipeId))
        }
    }

    Scaffold(topBar = {
        AppToolbar(title = title,
            navigationIcon = Icons.Filled.Clear,
            navigationClickListener = onBackPress,
            action = { RecipeCreateActions(recipeCreationViewModel, modeEnum = modeEnum!!) }
        )
    }) {
        AppDropDownMenu(categoryListValue, selectedCategory)
        OutlinedTextField(value = recipeValue.name ?: "", onValueChange = {
            val newRecipe = recipeValue.getCopyOf()
            newRecipe.ingredients = it
            setRecipeValue(newRecipe)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        OutlinedTextField(value = recipeValue.ingredients ?: "",
            onValueChange = {
                val newRecipe = recipeValue.getCopyOf()
                newRecipe.ingredients = it
                setRecipeValue(newRecipe)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
    }
}

@Composable
fun RecipeCreateActions(recipeCreationViewModel: RecipeCreationViewModel, modeEnum: MODE) {
    if (modeEnum == MODE.CREATE) {
        TopBarAction(Icons.Filled.Done, "Done") {
            recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.CreateRecipe.Request.CreateRecipe(
                Category(), "", ""))
        }
    } else {
        TopBarAction(imageVector = Icons.Filled.Delete, contentDescription = "Delete") {
            recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.DeleteRecipe.Request.Delete(
                ""))
        }

        TopBarAction(imageVector = Icons.Filled.Check, contentDescription = "Save") {
            recipeCreationViewModel.processCommand(RecipeCreationViewModel.Command.CreateRecipe.Request.CreateRecipe(
                Category(), "", ""))
        }
    }
}

@Composable
fun RecipeDetailScreenContent(
    categories: State<CategoryListViewModel.Command.FetchCategory.Response?>,
    navHostController: NavHostController,
) {
}

@Composable
fun AppDropDownMenu(selectableItemList: List<Selectable>, selectedItem: MutableState<Selectable>) {
    val expanded = remember { mutableStateOf(false) }
    val textfieldSize = remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded.value)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Column {
        OutlinedTextField(
            value = selectedItem.value.getDisplayName(),
            onValueChange = { changedText ->
                selectedItem.value = selectableItemList.first { it.getDisplayName() == changedText }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize.value = coordinates.size.toSize()
                },
            label = { Text("Label") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded.value = !expanded.value })
            }
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.value.width.toDp() })
        ) {
            selectableItemList.forEach { selectable ->
                DropdownMenuItem(onClick = {
                    selectedItem.value =
                        selectableItemList.first { it.getDisplayName() == selectable.getDisplayName() }
                }) {
                    Text(text = selectable.getDisplayName())
                }
            }
        }
    }
}