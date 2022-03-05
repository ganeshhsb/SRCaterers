package com.srcaterersnasik.app.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.material.textfield.TextInputLayout
import com.srcaterersnasik.R
import com.srcaterersnasik.app.AppToolbar
import com.srcaterersnasik.app.CategoryScreens
import com.srcaterersnasik.app.category.create.CategoryCreationFragment
import com.srcaterersnasik.app.category.create.CategoryCreationViewModel
import com.srcaterersnasik.app.category.create.MODE
import com.srcaterersnasik.app.category.listing.CategoryListViewModel
import com.srcaterersnasik.model.Category

@Composable
fun CategoryListScreen(
    navHostController: NavHostController,
    categoryListViewModel: CategoryListViewModel = hiltViewModel(),
    categoryListScreenContent: @Composable (
        categories: State<CategoryListViewModel.Command.FetchCategory.Response?>,
        navHostController: NavHostController,
    ) -> Unit,
) {
    val categories = categoryListViewModel.getObserver().observeAsState()
    LaunchedEffect(true) {
        categoryListViewModel.processCommand(CategoryListViewModel.Command.FetchCategory.Request.FetchCategory)
    }
    Scaffold(topBar = {
        AppToolbar(stringResource(id = R.string.category),
            Icons.Filled.ArrowBack,
            { navHostController.popBackStack() })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(CategoryScreens.CategoryDetail.route.plus("/${MODE.CREATE.value}"))
            }) {
                Icon(Icons.Filled.Add, "")
            }
        }) {
        categoryListScreenContent(categories, navHostController)
    }
}

@Composable
fun CategoryListScreenContent(
    categories: State<CategoryListViewModel.Command.FetchCategory.Response?>,
    navHostController: NavHostController,
) {


    when (val categoryListResponse = categories.value) {

        is CategoryListViewModel.Command.FetchCategory.Response.CategoryList -> {
            val categoryList = categoryListResponse.categoryList
            LazyColumn() {
                items(categoryList.size) { index ->
                    val category = categoryList[index]

                    val onClickNavigation = {
                        val route =
                            CategoryScreens.CategoryDetail.route
                                .plus("/${MODE.EDIT.value}")
                                .plus("?")
                                .plus("categoryId=${category.category_id}")
                        navHostController.navigate(route)
                    }
                    AppText(category.name ?: "", onClickNavigation)
                }
            }
        }
    }
}

@Composable
fun CategoryDetailScreen(
    navHostController: NavHostController,
    categoryCreationViewModel: CategoryCreationViewModel = viewModel(),
    categoryId: String,
    mode: Int,
    onBackPress: () -> Unit,
) {
    val (categoryNameValue, setCategoryNameValue) = remember {
        mutableStateOf("")
    }
    val modeEnum = remember { MODE.valueFrom(mode) }
    val title = if (modeEnum == MODE.CREATE) {
        stringResource(id = R.string.category_create)
    } else {
        stringResource(id = R.string.category_edit)
    }
    val categoryCreateViewModelResponse = categoryCreationViewModel.getObserver().observeAsState()
    when (categoryCreateViewModelResponse.value) {
        is CategoryCreationViewModel.Command.CreateCategory.Response.CreateCategory -> {
            onBackPress()
        }

        is CategoryCreationViewModel.Command.FetchCategory.Response.FetchCategory -> {
            val categoryValue =
                if (categoryCreateViewModelResponse.value is CategoryCreationViewModel.Command.FetchCategory.Response.FetchCategory) {
                    (categoryCreateViewModelResponse.value as CategoryCreationViewModel.Command.FetchCategory.Response.FetchCategory)?.category?.name
                } else {
                    ""
                }
            setCategoryNameValue(categoryValue ?: "")
        }

        is CategoryCreationViewModel.Command.DeleteCategory.DeleteCategoryResponse -> {
            onBackPress()
        }
    }
    LaunchedEffect(key1 = true) {
        if (modeEnum == MODE.EDIT) {
            categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.FetchCategory.Request.FetchCategory(
                categoryId ?: ""))
        }

    }

    Scaffold(topBar = {
        AppToolbar(title = title,
            navigationIcon = Icons.Filled.Clear,
            navigationClickListener = onBackPress,
            action =
            if (modeEnum == MODE.CREATE) {
                {
                    TopBarAction(Icons.Filled.Done, "Done") {
                        categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.CreateCategory.Request.CreateCategory(
                            categoryNameValue ?: ""))
                    }
                }
            } else {
                {
                    TopBarAction(imageVector = Icons.Filled.Delete, contentDescription = "Delete") {
                        categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.DeleteCategory.DeleteCategoryRequest())

                    }

                    TopBarAction(imageVector = Icons.Filled.Check, contentDescription = "Delete") {
                        categoryCreationViewModel.processCommand(CategoryCreationViewModel.Command.CreateCategory.Request.CreateCategory(
                            categoryNameValue))
                    }
                }
            }
        )
    }) {
        OutlinedTextField(value = categoryNameValue ?: "", onValueChange = {
            val category = Category()
            category.category_id = categoryId
            category.name = it
            categoryCreationViewModel.setCategory(category)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
    }
}

@Composable
fun TopBarAction(imageVector: ImageVector, contentDescription: String, callback: () -> Unit) {
    AppIconImage(callback, imageVector, contentDescription)
}


@Composable
fun AppIconImage(onClick: () -> Unit, imageVector: ImageVector, contentDescription: String = "") {
    IconButton(onClick = onClick) {
        Image(imageVector = imageVector,
            contentDescription = "Clear")
    }
}

@Composable
fun AppText(text: String, onClick: () -> Unit) {
    Text(text = text,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(8.dp))
}