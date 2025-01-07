package lab.android.chartersapp.charters.presentation.offers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab.android.chartersapp.charters.data.ApiState
import androidx.hilt.navigation.compose.hiltViewModel
import lab.android.chartersapp.charters.data.Boat
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel

@Composable
fun OfferListScreen(viewModel: BoatViewModel = hiltViewModel()) {
    // Observe the state of the boat list
    val offersState by viewModel.boats.observeAsState(ApiState.Loading)

    // Trigger fetching boats when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.getBoats()
    }

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
            // When data is successfully fetched, display the list of boats
            val offers = (offersState as ApiState.Success<List<Boat>>).data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(offers) { offer ->
                    OfferItem(offer) // Display each boat using a custom composable
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

@Composable
fun OfferItem(offer: Boat) {
    // A simple view to display each offer's details
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Handle item click here (e.g., navigate to a details screen)
            },
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = offer.name,
            //style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Model: ${offer.boatModel}",
            //style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Production Year: ${offer.productionYear}",
            //style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Add more fields as needed
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
