package lab.android.chartersapp.charters.presentation.offers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.Offer
import lab.android.chartersapp.charters.presentation.OfferViewModel
import androidx.hilt.navigation.compose.hiltViewModel
// UNUSED FOR NOW, WAITING FOR DATABASE AND THIS JUST HAS THE LOGIC TO COPY
@Composable
fun OfferListScreen(viewModel: OfferViewModel = hiltViewModel()) {
    val offersState by viewModel.offers

    LaunchedEffect(Unit) {
        viewModel.fetchOffers()
    }

    when (offersState) {
        is ApiState.Loading -> {
            // Show loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ApiState.Success<*> -> {
            val offers = (offersState as ApiState.Success<List<Offer>>).data
            // Display the fetched offers using Jetpack Compose components
            Column {
                Text("Offer List:")
                LazyColumn {
                    items(offers) { offer ->
                        Text(offer.name) // Adjust this to the relevant offer properties
                    }
                }
            }
        }
        is ApiState.Error -> {
            val error = (offersState as ApiState.Error).message
            // Show error message
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
