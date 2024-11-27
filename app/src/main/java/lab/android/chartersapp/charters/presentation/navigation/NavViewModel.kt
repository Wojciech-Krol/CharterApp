package lab.android.chartersapp.charters.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)

class NavViewModel : ViewModel() {
    private val _selectedItemIndex = MutableStateFlow(0)
    val selectedItemIndex: StateFlow<Int> = _selectedItemIndex

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn
        ),
        BottomNavigationItem(
            title = "Chat",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            badgeCount = 45
        ),
        BottomNavigationItem(
            title = "Account",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            hasNews = true
        )
    )

    fun onItemSelected(index: Int) {
        _selectedItemIndex.value = index
    }
}
