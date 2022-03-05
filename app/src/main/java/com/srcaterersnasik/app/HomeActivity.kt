package com.srcaterersnasik.app

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.material.navigation.NavigationView
import com.srcaterersnasik.R
import com.srcaterersnasik.app.category.AppIconImage
import com.srcaterersnasik.app.category.CategoryDetailScreen
import com.srcaterersnasik.app.category.CategoryListScreen
import com.srcaterersnasik.app.category.CategoryListScreenContent
import com.srcaterersnasik.app.order.OrderScreen
import com.srcaterersnasik.app.order.OrderScreenState
import com.srcaterersnasik.app.recipe.RecipeDetailScreen
import com.srcaterersnasik.app.recipe.RecipeListScreen
import com.srcaterersnasik.app.recipe.RecipeListScreenContent
import com.srcaterersnasik.app.theme.SRCaterersTheme
import com.srcaterersnasik.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SRCaterersTheme {
                // A surface container using the 'background' color from the theme

                Surface(modifier = Modifier
                    .fillMaxSize(),
                    color = MaterialTheme.colors.surface) {
                    Scaffold() {
                        MainScreen(context = baseContext)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    val drawable = getDrawable(LocalContext.current.resources,
        LocalContext.current.theme)
    ModalDrawer(drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            HomeScreenDrawer(drawable, onDestinationClicked = { route ->
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            })
        }
    ) {
        HomeScreenNavigation(navController = navController, openDrawer = { openDrawer() })
    }

}

@Composable
fun HomeScreenNavigation(navController: NavHostController, openDrawer: () -> Unit) {
//    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DrawerScreens.Home.route) {
        composable(DrawerScreens.Home.route) {
//            OrderScreen(orderScreenState = OrderScreenState(), navController = navController) {
//
//            }
            HomeScreen(openDrawer = openDrawer)
        }
        composable(DrawerScreens.RecipeList.route) {
            RecipeListScreen(navHostController = navController, hiltViewModel()) { recipes, navHostController ->
                RecipeListScreenContent(recipes = recipes, navHostController = navHostController )
            }
        }

        composable(RecipesScreens.RecipesDetail.route
            .plus("/{mode}").plus("?recipeId={recipeId}"),
            arguments = listOf(navArgument("mode") {
                type = NavType.IntType
            },
                navArgument("recipeId") {
                    defaultValue = ""
                }
            )) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("recipeId") ?: ""
            val mode = backStackEntry.arguments?.getInt("mode") ?: 0
            RecipeDetailScreen(navHostController = navController,
                hiltViewModel(),
                recipeId = categoryId,
                mode = mode
            ) {
                navController.popBackStack(DrawerScreens.CategoryList.route, false)
            }
        }
        composable(DrawerScreens.CategoryList.route) {
            CategoryListScreen(navHostController = navController,
                hiltViewModel()) { categories, navController ->
                CategoryListScreenContent(categories,
                    navController)
            }
        }
        composable(CategoryScreens.CategoryDetail.route
            .plus("/{mode}").plus("?categoryId={categoryId}"),
            arguments = listOf(navArgument("mode") {
                type = NavType.IntType
            },
                navArgument("categoryId") {
                    defaultValue = ""
                }
            )) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            val mode = backStackEntry.arguments?.getInt("mode") ?: 0
            CategoryDetailScreen(navHostController = navController,
                hiltViewModel(),
                categoryId = categoryId,
                mode = mode
            ) {
                navController.popBackStack(DrawerScreens.CategoryList.route, false)
            }
        }
    }
}

sealed class DrawerScreens(val title: String, val resourceId: Int, val route: String) {
    object Home : DrawerScreens("Home", R.string.menu_open_orders, "Home")
    object OrderList : DrawerScreens("OrderList", R.string.menu_open_orders, "OrderList")
    object RecipeList : DrawerScreens("RecipeList", R.string.menu_recipe_list, "RecipeList")
    object CategoryList : DrawerScreens("CategoryList", R.string.menu_category_list, "CategoryList")
}

sealed class CategoryScreens(val title: String, val resourceId: Int, val route: String) {
    object CategoryList :
        DrawerScreens("Cateogry List", R.string.menu_category_list, "CategoryList")

    object CategoryDetail :
        DrawerScreens("Category Detail", R.string.category_detail, "CategoryDetail")
}

sealed class RecipesScreens(val title: String, val resourceId: Int, val route: String) {
    object RecipesList : DrawerScreens("Recipe List", R.string.menu_recipe_list, "RecipesList")
    object RecipesDetail :
        DrawerScreens("Recipe Detail", R.string.menu_recipe_list, "RecipesDetail")
}

private val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.RecipeList,
    DrawerScreens.CategoryList
)

fun getDrawable(resources: Resources, theme: Resources.Theme): Bitmap? {
    ResourcesCompat.getDrawable(
        resources,
        R.mipmap.ic_launcher_round, theme
    )?.let { drawable ->
        return Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    } ?: return null
}

@Composable
fun HomeScreenDrawer(bitmap: Bitmap? = null, onDestinationClicked: (route: String) -> Unit) {
    Column {
        screens.forEach { screen ->
            Text(
                text = stringResource(id = screen.resourceId),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .clickable {
                        onDestinationClicked(screen.route)
                    }
                    .padding(16.dp)
            )

            Divider(color = MaterialTheme.colors.primary, modifier = Modifier.height(2.dp))
        }
    }
}
@Composable
fun HomeScreen(openDrawer: () -> Unit) {
    val scaffloldState = rememberScaffoldState()
    Scaffold(topBar = {
        AppToolbar(title = "Home", navigationIcon = Icons.Filled.Menu, navigationClickListener = {
            openDrawer()
        }, action = {

        })
    }, content = {
        Text(text = "Hello World")
    })
}

@Composable
fun AppToolbar(
    title: String,
    navigationIcon: ImageVector,
    navigationClickListener: () -> Unit,
    action: @Composable (() -> Unit)? = null,
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
            AppIconImage(onClick = navigationClickListener, imageVector = navigationIcon, stringResource(
                id = R.string.back))
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            action?.invoke()
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 1.dp
    )
}
