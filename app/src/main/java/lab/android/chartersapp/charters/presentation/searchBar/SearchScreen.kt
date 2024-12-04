package lab.android.chartersapp.charters.presentation.searchBar;

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = viewModel()) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredItems by viewModel.filteredItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.updateQuery(it) },
            placeholder = {
                Text("Search...")
            },
            onSearch = {

            },
            active = false,
            onActiveChange = {},
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu Icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        ) { /* Additional suggestions or actions can go here */ }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn {
            items(filteredItems.size) { index ->
                ListItem(
                    headlineContent = {
                        Text(filteredItems[index])
                    },
                    supportingContent = {
                        Text("Supporting line text lorem ipsum dolor sit amet, consectetur.")
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text=filteredItems[index].firstOrNull()?.toString() ?: "",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    },
                    modifier = Modifier.padding(8.dp).clickable {
                        navController.navigate("details/${filteredItems[index]}")
                    }
                )
                //HorizontalDivider()
            }
        }
    }
}

