package lab.android.chartersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import lab.android.chartersapp.charters.presentation.ForgotPasswordViewModel
import lab.android.chartersapp.charters.presentation.LoginViewModel
import lab.android.chartersapp.charters.presentation.OfferViewModel
import lab.android.chartersapp.charters.presentation.RegisterViewModel
import lab.android.chartersapp.charters.presentation.offers.OfferListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "offers_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("offers_list") {
                            val viewModel: OfferViewModel = viewModel()
                            OfferListScreen(viewModel = viewModel)
                        }

                        navigation(
                            startDestination = "login_page",
                            route = "auth"
                        ) {
                            composable("login") {
                                val viewModel = it.sharedViewModel<LoginViewModel>(navController)
                                // Call LoginScreen composable here
                            }
                            composable("register") {
                                val viewModel = it.sharedViewModel<RegisterViewModel>(navController)
                                // Call RegisterScreen composable here
                            }
                            composable("forgot_password") {
                                val viewModel = it.sharedViewModel<ForgotPasswordViewModel>(navController)
                                // Call ForgotPasswordScreen composable here
                            }
                        }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Greeting("Android")
    }
}