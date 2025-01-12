package lab.android.chartersapp

import NavViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.presentation.loginPage.LoginPageScreen
import lab.android.chartersapp.charters.presentation.loginPage.LoginPageViewModel
import lab.android.chartersapp.charters.presentation.map.MapScreen
import lab.android.chartersapp.charters.presentation.map.MapViewModel
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel
import lab.android.chartersapp.charters.presentation.navigation.NavigationBarBottom
import lab.android.chartersapp.charters.presentation.offers.OfferDetailScreen
import lab.android.chartersapp.charters.presentation.offers.OfferListScreen
import lab.android.chartersapp.charters.presentation.searchBar.ChatsScreen
import lab.android.chartersapp.charters.presentation.searchBar.ChatsViewModel
import lab.android.chartersapp.charters.presentation.searchBar.SearchScreen
import lab.android.chartersapp.charters.presentation.chatWindow.ChatWindowScreen

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
                            viewModel = NavViewModel(),
                            navController = navController // Pass the navController
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
                            MapScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable("chat_page") {
                            val viewModel: ChatsViewModel = hiltViewModel()
                            ChatsScreen(navController, viewModel = viewModel)
                        }
                        composable(
                            "details/{boatJson}",
                            arguments = listOf(navArgument("boatJson") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val boatJson = backStackEntry.arguments?.getString("boatJson")
                            val boat = Gson().fromJson(boatJson, Boat::class.java) // Deserialize JSON to `Boat`

                            if (boat != null) {
                                OfferDetailScreen(item = boat, navController = navController)
                            } else {
                                // Handle case where the boat is null
                                Text("Boat not found", modifier = Modifier.padding(16.dp))
                            }
                        }
                        composable(
                            "chat_window/{chatJson}",
                            arguments = listOf(navArgument("chatJson") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val chatJson = backStackEntry.arguments?.getString("chatJson")
                            if (chatJson != null) {
                                ChatWindowScreen(navController, chatJson)
                            } else {
                                Text("Chat not found", modifier = Modifier.padding(16.dp))
                            }
                        }
                        composable("account_page") {
                            val viewModel: LoginPageViewModel by viewModels()
                            LoginPageScreen(navController, viewModel = viewModel)
                        }

                    }
                }
            }
        }
    }
}