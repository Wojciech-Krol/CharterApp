package lab.android.chartersapp.charters.presentation.offers

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import lab.android.chartersapp.R
import lab.android.chartersapp.charters.data.Boat
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailScreen(item: Boat?, navController: NavController) {
    val items = listOf(
        CarouselItem(0, R.drawable.carousel_image_1, "Image 1 description"),
        CarouselItem(1, R.drawable.carousel_image_2, "Image 2 description"),
        CarouselItem(2, R.drawable.carousel_image_3, "Image 3 description")
    )

    // State to manage the visibility of the date picker
    var showDatePicker by remember { mutableStateOf(false) }

    // State to store the selected date range
    val datePickerState = rememberDateRangePickerState()

    val boatViewModel: BoatViewModel = hiltViewModel()

    // State to store the selected boat
    var boat by remember { mutableStateOf<Boat?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch the boat when the screen is loaded
    LaunchedEffect(item) {
       boat=item
    }
    errorMessage?.let { Log.e("error", it) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { boat?.let { Text(it.name) } },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState(0) { items.size },
                preferredItemWidth = 504.dp, // Double the original width (252.dp * 2)
                maxSmallItemWidth = 112.dp, // Double the original small item width (56.dp * 2)
                itemSpacing = 20.dp
            ) { i ->
                val item = items[i]
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.contentDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(504.dp) // Match preferred item width
                        .height(252.dp) // Half the assumed original height (252.dp / 2)
                        .padding(8.dp)
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .verticalScroll(rememberScrollState()) // Make the Column scrollable
                    .padding(4.dp)
            ) {
                // Boat name and model
                Text(
                    text = "${boat?.name} (${boat?.boatModel})",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF000000)
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                // General Information
                InformationSection(title = "General Information", details = listOf(
                    "Production Year: ${boat?.productionYear}",
                    "Company: ${boat?.company}",
                    "Mother Port: ${boat?.motherPort}",
                    "Description: ${boat?.description}"
                ))

                // Dimensions
                InformationSection(title = "Dimensions", details = listOf(
                    "Length: ${boat?.length} meters",
                    "Width: ${boat?.width} meters",
                    "Draft: ${boat?.draft} meters"
                ))

                // Pricing & Accommodation
                InformationSection(title = "Pricing & Accommodation", details = listOf(
                    "Price per Day: ${boat?.pricePerDay} USD",
                    "Beds: ${boat?.beds}"
                ))
            }

            // Button to show the date picker dialog
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                val startDate = datePickerState.selectedStartDateMillis?.toFormattedDate()
                val endDate = datePickerState.selectedEndDateMillis?.toFormattedDate()
                Text(
                    text = if (startDate != null && endDate != null) {
                        "Selected: $startDate to $endDate"
                    } else {
                        "Select a date range"
                    },
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight(400),
                    ),
                    color = MaterialTheme.colorScheme.primary,

                )

            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .size(120.dp) // 48.dp (default size) * 5 = 240.dp
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Date Picker",
                        modifier = Modifier.fillMaxSize(), // Ensures the icon scales with the button
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

        // Show Material Date Range Picker
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DateRangePicker(state = datePickerState)
            }
        }
    }
}
@Composable
fun InformationSection(title: String, details: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        details.forEach { detail ->
            Text(
                text = detail,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

// Extension function to format the date from timestamp
fun Long?.toFormattedDate(): String {
    return this?.let {
        java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(it)
    } ?: ""
}