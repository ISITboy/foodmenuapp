package com.example.gosporttastingtask.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gosporttastingtask.ui.data.remote.utils.NetworkResult
import com.example.gosporttastingtask.ui.domain.connectivity.ConnectivityObserver
import com.example.gosporttastingtask.ui.domain.connectivity.NetworkConnectivityObserver
import com.example.gosporttastingtask.ui.presentation.common.BottomNavBar
import com.example.gosporttastingtask.ui.presentation.common.TopBar
import com.example.gosporttastingtask.ui.presentation.navgraph.BasketDestination
import com.example.gosporttastingtask.ui.presentation.navgraph.Destination.*
import com.example.gosporttastingtask.ui.presentation.navgraph.MenuDestination
import com.example.gosporttastingtask.ui.presentation.navgraph.ProfileDestination
import com.example.gosporttastingtask.ui.presentation.navgraph.tabRowScreens
import com.example.gosporttastingtask.ui.presentation.screens.basket.BasketScreen
import com.example.gosporttastingtask.ui.presentation.screens.menu.MenuScreen
import com.example.gosporttastingtask.ui.presentation.screens.menu.MenuScreenViewModel
import com.example.gosporttastingtask.ui.presentation.screens.profile.ProfileScreen
import com.example.gosporttastingtask.ui.presentation.theme.GoSportTastingTaskTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity"
private lateinit var connectivityObserver: ConnectivityObserver
private lateinit var menuViewModel: MenuScreenViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            menuViewModel = hiltViewModel()

            GoSportApp(){
                menuViewModel.getAllProducts()
                menuViewModel.getAllCategories()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        menuViewModel.saveDataInDatabase()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoSportApp(loadingData:()->Unit) {
    val currentScreen = remember { tabRowScreens }
    val navController = rememberNavController()
    val backStackState by navController.currentBackStackEntryAsState()
    val status by connectivityObserver.observe()
        .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    if(status== ConnectivityObserver.Status.Available){
        loadingData()
    }

    GoSportTastingTaskTheme {
        Scaffold(
            topBar = { TopBar(status = status) },
            bottomBar = {
                BottomNavBar(
                    allScreens = currentScreen,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = getCurrentScreen(backStackState?.destination)
                )
            }
        ) { paddingValues ->
            AppNavHost(
                paddingValues = paddingValues,
                navController = navController,
                status = status
            )
        }
    }
}

@Composable
fun AppNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    status: ConnectivityObserver.Status
) {

    NavHost(
        navController = navController,
        startDestination = MenuDestination.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = MenuDestination.route) {
            when (status) {
                ConnectivityObserver.Status.Available -> {
                    val categoriesState =
                        menuViewModel.categoriesApi.observeAsState().value
                            ?: NetworkResult.Loading()
                    val productsState =
                        menuViewModel.productsApi.observeAsState().value ?: NetworkResult.Loading()
                    MenuScreen(
                        viewModel = menuViewModel,
                        categoriesState = categoriesState,
                        productsState = productsState
                    )
                }

                ConnectivityObserver.Status.Unavailable, ConnectivityObserver.Status.Lost, ConnectivityObserver.Status.Losing -> {
                    val products = menuViewModel.products.observeAsState().value ?: listOf()
                    val categories = menuViewModel.categories.observeAsState().value ?: listOf()
                    MenuScreen(
                        viewModel = menuViewModel,
                        products = products,
                        categories = categories
                    )
                }
            }
        }
        composable(route = ProfileDestination.route) {
            ProfileScreen()
        }
        composable(route = BasketDestination.route) {
            BasketScreen()
        }
    }
}


private fun getCurrentScreen(currentDestination: NavDestination?) =
    tabRowScreens.find { it.route == currentDestination?.route } ?: MenuDestination


private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }