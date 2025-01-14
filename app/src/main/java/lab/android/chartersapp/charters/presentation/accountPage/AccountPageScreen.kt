package lab.android.chartersapp.charters.presentation.accountPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Charter

@Composable
fun AccountPageScreen(viewModel: AccountPageViewModel = hiltViewModel(), username: String?, password: String?) {
    LaunchedEffect(Unit) {
        if (username != null) {
            viewModel.getChartersByUser(username)
        }
    }

    val chartersState by viewModel.charters.observeAsState(ApiState.Loading)

    Column(modifier = Modifier.padding(16.dp)) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account Icon",
            modifier = Modifier.height(256.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (username != null) {
            TextField(
                value = username,
                supportingText = { Text("Your username") },
                onValueChange = {},
                label = { Text("User") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (password != null) {
            TextField(
                value = password,
                supportingText = { Text("Your password") },
                onValueChange = {},
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Charter history",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (chartersState) {
            is ApiState.Loading -> {
                CircularProgressIndicator()
            }
            is ApiState.Success<*> -> {
                val charters = (chartersState as ApiState.Success<List<Charter>>).data
                LazyColumn {
                    items(charters) { charter ->
                        CharterItem(charter)
                    }
                }
            }
            is ApiState.Error -> {
                val error = (chartersState as ApiState.Error).message
                Text(text = "Failed to fetch charters: $error", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun CharterItem(charter: Charter) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Boat: ${charter.boat}", style = TextStyle(fontWeight = FontWeight.Bold))
        Text(text = "Start Date: ${charter.startDate}")
        Text(text = "End Date: ${charter.endDate}")
        Text(text = "Price: ${charter.price} USD")
        Spacer(modifier = Modifier.height(8.dp))
    }
}