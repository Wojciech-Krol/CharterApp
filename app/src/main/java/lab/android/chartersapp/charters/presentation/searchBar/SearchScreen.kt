package lab.android.chartersapp.charters.presentation.searchBar;

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import lab.android.chartersapp.charters.data.ApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: BoatViewModel = hiltViewModel()) {

    // Observe the state of the boat list
    val offersState by viewModel.boats.observeAsState(ApiState.Loading)

    // Trigger fetching boats when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.getBoats()
    }

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredBoats by viewModel.filteredBoats.collectAsState()

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            inputField = {
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateQuery(it) },
                    placeholder = { Text("Search...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        }
                    },
                )
            },
            expanded = isExpanded,
            onExpandedChange = { expanded -> isExpanded = expanded },

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            tonalElevation = 4.dp
        ) {
            // You can add additional actions or components inside the expanded content
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (offersState) {
            is ApiState.Loading -> {
                // Show loading indicator while fetching data
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ApiState.Success<*> -> {
                LazyColumn {
                    items(filteredBoats.size) { index ->
                        val boat = filteredBoats[index]
                        ListItem(
                            headlineContent = {
                                Text(boat.name)
                            },
                            supportingContent = {
                                Text("${boat.boatModel} - ${boat.productionYear}")
                            },
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = boat.name.firstOrNull()?.toString() ?: "",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    val boatJson = Gson().toJson(boat) // Serialize the `Boat` object
                                    navController.navigate("details/$boatJson")
                                }
                        )
                    }
                }
            }
            is ApiState.Error -> {
                // Show an error message if the data fetch fails
                val error = (offersState as ApiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Failed to fetch data: $error")
                }
            }
        }
    }
}
