package lab.android.chartersapp.charters.presentation.offers

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import lab.android.chartersapp.R
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.presentation.searchBar.BoatViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.SelectableDates
import lab.android.chartersapp.charters.data.dataclasses.Charter
import java.time.LocalDate
import java.util.Calendar
import java.util.TimeZone

fun convertToUtcMillis(dateString: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = dateFormat.parse(dateString)
    return date?.time ?: 0L
}

fun getDatesBetween(startDate: String, endDate: String): List<Long> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val start = dateFormat.parse(startDate)
    val end = dateFormat.parse(endDate)
    val dates = mutableListOf<Long>()

    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.time = start

    while (calendar.time <= end) {
        dates.add(calendar.timeInMillis)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dates
}


@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    private val charterDates = mutableListOf<Long>()

    fun setCharterDates(dates: List<Long>) {
        charterDates.clear()
        charterDates.addAll(dates)
        Log.i("SelectableDates", "Charter dates: $charterDates")
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis > System.currentTimeMillis() && !charterDates.contains(utcTimeMillis)
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailScreen(item: Boat?, navController: NavController) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDateRangePickerState(selectableDates = PastOrPresentSelectableDates)
    val boatViewModel: BoatViewModel = hiltViewModel()
    var boat by remember { mutableStateOf<Boat?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(item) {
        boat = item
        boat?.name?.let {
            boatViewModel.getBoatPhotos(it)
            boatViewModel.getCharters(it)
        }
    }


    val boatPhotosState by boatViewModel.boatPhotos.observeAsState(ApiState.Loading)
    val boatImagesState by boatViewModel.boatImages.observeAsState(ApiState.Loading)
    val chartersState by boatViewModel.charters.observeAsState(ApiState.Loading)

    LaunchedEffect(chartersState) {
        if (chartersState is ApiState.Success) {
            val charters = (chartersState as ApiState.Success<List<Charter>>).data
            val charterDates = charters.flatMap { charter ->
                getDatesBetween(charter.startDate, charter.endDate)
            }
            PastOrPresentSelectableDates.setCharterDates(charterDates)
        }
    }

    errorMessage?.let { Log.e("error", it) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    boat?.let {
                        Text(
                            text = it.name,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                },
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
            when (boatImagesState) {
                is ApiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ApiState.Success -> {
                    val photos = (boatImagesState as ApiState.Success<List<Bitmap>>).data
                    HorizontalMultiBrowseCarousel(
                        state = rememberCarouselState(0) { photos.size },
                        preferredItemWidth = 504.dp,
                        maxSmallItemWidth = 112.dp,
                        itemSpacing = 20.dp
                    ) { i ->
                        val photo = photos[i]
                        Image(
                            bitmap = photo.asImageBitmap(),
                            contentDescription = "Boat photo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(504.dp)
                                .height(252.dp)
                                .padding(2.dp)
                        )
                    }
                }
                is ApiState.Error -> {
                    errorMessage = (boatImagesState as ApiState.Error).message
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Failed to load photos: $errorMessage")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(4.dp)
            ) {
                Text(
                    text = "${boat?.name} (${boat?.boatModel})",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(4.dp)
                )

                InformationSection(title = "General Information", details = listOf(
                    "Production Year: ${boat?.productionYear}",
                    "Company: ${boat?.company}",
                    "Mother Port: ${boat?.motherPort}",
                    "Description: ${boat?.description}"
                ))

                InformationSection(title = "Dimensions", details = listOf(
                    "Length: ${boat?.length} meters",
                    "Width: ${boat?.width} meters",
                    "Draft: ${boat?.draft} meters"
                ))

                InformationSection(title = "Pricing & Accommodation", details = listOf(
                    "Price per Day: ${boat?.pricePerDay} USD",
                    "Beds: ${boat?.beds}"
                ))
            }

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
                        "Check availability"
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
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.size(100.dp)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Date Picker",
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    /*Button(onClick = {
                        val chatJson = Gson().toJson(boat)
                        navController.navigate("chat_window/$chatJson")
                    }) {
                        Text("Chat with Owner")
                    }*/
                    Button(onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(boat?.contactEmail))
                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry about ${boat?.name}")
                            putExtra(Intent.EXTRA_TEXT, "Hello, I am interested in chartering ${boat?.name}")
                        }
                        navController.context.startActivity(emailIntent)
                    }) {
                        Text("Contact Owner")
                    }
                }
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        if (datePickerState.selectedStartDateMillis != null && datePickerState.selectedEndDateMillis != null) {
                            val startDate =
                                datePickerState.selectedStartDateMillis.toFormattedDate()
                            val endDate = datePickerState.selectedEndDateMillis.toFormattedDate()
                            Log.d("DateRangePicker", "Selected: $startDate to $endDate")
                        } else {
                            dateError = "Please select a date range"
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                },
                content = {
                    DateRangePicker(
                        state = datePickerState
                    )
                }
            )
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
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

fun Long?.toFormattedDate(): String {
    return this?.let {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
    } ?: ""
}