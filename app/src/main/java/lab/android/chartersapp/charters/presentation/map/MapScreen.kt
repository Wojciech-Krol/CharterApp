package lab.android.chartersapp.charters.presentation.map

import android.util.Log
import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import lab.android.chartersapp.R

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val context = LocalContext.current

    AndroidView(factory = { context ->
        LayoutInflater.from(context).inflate(R.layout.map_layout, null)
    }) { view ->
        viewModel.map = view.findViewById(R.id.map)
        viewModel.locationButton = view.findViewById(R.id.locationButton)

        if (viewModel.map == null) {
            Log.e("MapScreen", "MapView is null")
        } else {
            Log.d("MapScreen", "MapView initialized")
        }

        if (viewModel.locationButton == null) {
            Log.e("MapScreen", "LocationButton is null")
        } else {
            Log.d("MapScreen", "LocationButton initialized")
        }

        // Initialize the map and location button after they are assigned
        try {
            viewModel.initMap(context)
            Log.d("MapScreen", "Map initialized")
        } catch (e: Exception) {
            Log.e("MapScreen", "Error initializing map", e)
        }

        try {
            viewModel.initLocationButton(context)
            Log.d("MapScreen", "Location button initialized")
        } catch (e: Exception) {
            Log.e("MapScreen", "Error initializing location button", e)
        }
    }
}