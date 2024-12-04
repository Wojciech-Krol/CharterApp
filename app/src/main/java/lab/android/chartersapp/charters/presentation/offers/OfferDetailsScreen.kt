package lab.android.chartersapp.charters.presentation.offers

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
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
import androidx.navigation.NavController
import lab.android.chartersapp.R

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailScreen(itemName: String?, navController: NavController) {
    // Example data for the carousel items
    val items = listOf(
        CarouselItem(0, R.drawable.carousel_image_1, "Image 1 description"),
        CarouselItem(1, R.drawable.carousel_image_2, "Image 2 description"),
        CarouselItem(2, R.drawable.carousel_image_3, "Image 3 description")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item Details") },
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
            // Horizontal carousel
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState(0) { items.size },
                preferredItemWidth = 252.dp,
                maxSmallItemWidth = 56.dp
            ) { i ->
                val item = items[i]
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = item.contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Item description text
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolotat non proident, sunt in culpa.",
                style = TextStyle(
                    fontSize = 24.sp, // Example size for Title Large
                    lineHeight = 32.sp,
                    //fontFamily = FontFamily(Font(R.font)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000)
                ),
                modifier = Modifier.padding(16.dp)
            )

            // Calendar view (example with basic items)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                // Example calendar items
                repeat(5) { index ->
                    Text("Event ${index + 1}")
                }
            }
        }
    }
}