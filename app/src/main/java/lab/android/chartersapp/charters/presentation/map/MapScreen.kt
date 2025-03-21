package lab.android.chartersapp.charters.presentation.map

import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import lab.android.chartersapp.R
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel
import org.osmdroid.views.overlay.OverlayItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController,viewModel: MapViewModel = hiltViewModel(), boatViewModel: BoatViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val offersState by boatViewModel.boats.observeAsState(ApiState.Loading)
    val filteredBoats by boatViewModel.filteredBoats.collectAsState()
    var selectedPortFromOSMDroid by remember { mutableStateOf<OverlayItem?>(null) }
    val selectedPort by viewModel.selectedPort

    val searchQuery by boatViewModel.searchQuery.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        boatViewModel.getBoats()
        viewModel.getPorts()
    }

    val screenHeight = LocalDensity.current.run { context.resources.displayMetrics.heightPixels.toDp() }
    val maxHeight = screenHeight * 2 / 3

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            selectedPortFromOSMDroid?.let { port ->
                Column(modifier = Modifier.padding(16.dp).heightIn(max = maxHeight)) {
                    Log.i("MapScreen", "Selected port: ${port.title}")
                    LaunchedEffect(port.title) {
                        viewModel.getPortByName(port.title)
                        boatViewModel.getBoatsByPort(port.title)
                    }
                    selectedPort?.name?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.padding(8.dp)) {
                        Column {
                            Text(text = "Country: ${selectedPort?.country}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "City: ${selectedPort?.city}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Address: ${selectedPort?.address}", style = MaterialTheme.typography.bodyMedium)
                            selectedPort?.phoneNumber?.let { Text(text = "Phone: $it", style = MaterialTheme.typography.bodyMedium) }
                            selectedPort?.email?.let { Text(text = "Email: $it", style = MaterialTheme.typography.bodyMedium) }
                            selectedPort?.website?.let { Text(text = "Website: $it", style = MaterialTheme.typography.bodyMedium) }
                            selectedPort?.places?.let { Text(text = "Places: $it", style = MaterialTheme.typography.bodyMedium) }
                            Text(text = "Description: ${selectedPort?.description}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Coordinates: (${selectedPort?.latitude}, ${selectedPort?.longitude})", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Boat offers for ${port.title}", style = MaterialTheme.typography.titleSmall)
                    when (offersState) {
                        is ApiState.Loading -> {
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
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.map_layout, null)
            }, modifier = Modifier.fillMaxSize()) { view ->
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
                    viewModel.initMap(context) { port ->
                        selectedPortFromOSMDroid = port
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
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

            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                boatViewModel.getBoats()
                SearchBar(
                    inputField = {
                        TextField(
                            value = searchQuery,
                            onValueChange = { boatViewModel.updateQuery(it) },
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
        }
    }
}