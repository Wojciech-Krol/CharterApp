package lab.android.chartersapp.charters.presentation.loginPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lab.android.chartersapp.R

@Composable
fun LoginPageScreen(navController: NavController, viewModel: LoginPageViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by viewModel.loginResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(256.dp) // Adjust the size as needed
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login(username, password) },
            modifier = Modifier.width(120.dp).align(Alignment.CenterHorizontally)
        ) {
            Text("Login")
        }

        loginResult?.let { result ->
            LaunchedEffect(result) {
                if (result) {
                    navController.navigate("account_page/$username/$password") {
                        popUpTo("login_page") { inclusive = true }
                    }
                }
            }
            if (result) {
                Text("Login successful!", color = MaterialTheme.colorScheme.primary)
            } else {
                Text("Login failed. Please try again.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}