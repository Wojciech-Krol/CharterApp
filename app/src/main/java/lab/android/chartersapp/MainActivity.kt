package lab.android.chartersapp

import NavViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import lab.android.chartersapp.charters.presentation.map.MapScreen
import lab.android.chartersapp.charters.presentation.map.MapViewModel
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel
import lab.android.chartersapp.charters.presentation.navigation.NavigationBarBottom
import lab.android.chartersapp.charters.presentation.offers.OfferDetailScreen
import lab.android.chartersapp.charters.presentation.offers.OfferListScreen
import lab.android.chartersapp.charters.presentation.searchBar.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBarBottom(
                            viewModel=NavViewModel(),
                            navController= navController // Pass the navController
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_page",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home_page") {
                            val viewModel: MapViewModel by viewModels()
                            MapScreen(viewModel = viewModel)
                        }

                        composable("chat_page"){
                            val viewModel: BoatViewModel = hiltViewModel()
                            SearchScreen(navController,viewModel = viewModel)
                        }
                        composable(
                            "details/{itemName}",
                            arguments = listOf(navArgument("itemName") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val itemName = backStackEntry.arguments?.getString("itemName")
                            OfferDetailScreen(itemName = itemName, navController = navController)
                        }
                        composable("account_page"){
                            val viewModel: BoatViewModel by viewModels()
                            OfferListScreen(viewModel = viewModel)
                        }


                        /*navigation(
                            startDestination = "home_page",
                            route = "mainNav"
                        ) {
                            composable("home") {
                                val viewModel = it.sharedViewModel<LoginViewModel>(navController)
                                // Call LoginScreen composable here
                            }
                            composable("chat") {
                                val viewModel = it.sharedViewModel<RegisterViewModel>(navController)
                                // Call RegisterScreen composable here
                            }
                            composable("account") {
                                val viewModel = it.sharedViewModel<ForgotPasswordViewModel>(navController)
                                // Call ForgotPasswordScreen composable here
                            }
                        }*/
                        // Additional navigation structure here...
                    }
                }
            }
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

