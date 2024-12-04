import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavViewModel : ViewModel() {
    data class NavigationItem(
        val route: String,
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val badgeCount: Int? = null,
        val hasNews: Boolean = false
    )


    private val _currentRoute = MutableLiveData("home_page")
    val currentRoute: LiveData<String> = _currentRoute

    fun onRouteSelected(route: String) {
        _currentRoute.value = route
    }
}
