package lab.android.chartersapp.charters.presentation.searchBar;

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    private val items = List(12) { index ->
            "Mikołajki Maxus 33.1 RS - Item $index"
    }


    private val _filteredItems = MutableStateFlow(items)
    val filteredItems: StateFlow<List<String>> = _filteredItems.asStateFlow()


    fun updateQuery(query: String) {
        _searchQuery.value = query
        _filteredItems.value = if (query.isEmpty()) {
            items
        } else {
            items.filter { it.contains(query, ignoreCase = true) }
        }
    }
}
