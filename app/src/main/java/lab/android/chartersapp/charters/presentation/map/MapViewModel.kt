package lab.android.chartersapp.charters.presentation.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageButton
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lab.android.chartersapp.R
import lab.android.chartersapp.charters.data.ApiState
import lab.android.chartersapp.charters.data.dataclasses.Port
import lab.android.chartersapp.charters.data.repositories.PortRepository
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val portRepository: PortRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _ports = MutableLiveData<ApiState<List<Pair<String, GeoPoint>>>>(ApiState.Loading)
    val ports: LiveData<ApiState<List<Pair<String, GeoPoint>>>> = _ports

    private val _selectedPort = mutableStateOf<Port?>(null)
    val selectedPort: State<Port?> = _selectedPort

    lateinit var map: MapView
    lateinit var locationButton: ImageButton
    private lateinit var mapController: IMapController

    fun initMap(context: Context, onPortSelected: (OverlayItem) -> Unit): MapView {
        // Fetch and add the ports
        getPorts()
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController = map.controller

        // Setting the zoom level of the map
        mapController.setZoom(12.0)

        // Setting the center of the map
        mapController.setCenter(GeoPoint(54.04, 21.758889))
        locateMe()

        // Adding the location overlay
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        locationOverlay.enableMyLocation()
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.target)
        val scaledIcon = Bitmap.createScaledBitmap(icon, 75, 75, false)
        locationOverlay.setPersonHotspot(scaledIcon.width / 2f, scaledIcon.height / 2f)
        locationOverlay.setPersonIcon(scaledIcon)
        map.overlays.add(locationOverlay)

        // Enable zoom controls
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)

        // Adding metric scale
        val scaleBar = org.osmdroid.views.overlay.ScaleBarOverlay(map)
        scaleBar.setAlignRight(true)
        scaleBar.setAlignBottom(true)
        scaleBar.setScaleBarOffset(15, 15)
        map.overlays.add(scaleBar)

        addPorts(onPortSelected)

        return map
    }

    fun initLocationButton(context: Context) {
        locationButton.setOnClickListener {
            locateMe()
        }
    }

    fun locateMe() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
            mapController.setZoom(12.0)

            val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location

            val lat = location.latitude
            val lon = location.longitude
            Log.i("MapViewModel", "Location: $lat, $lon")
            val startPoint = GeoPoint(lat, lon)
            mapController.setCenter(startPoint)
        }
    }

    fun getPorts() {
        viewModelScope.launch {
            try {
                val result = portRepository.fetchPorts().map { port -> Pair(port.name, GeoPoint(port.latitude, port.longitude)) }
                Log.d("API Response", "Ports fetched successfully: $result") // Log response
                _ports.value = ApiState.Success(result)
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch ports: ${e.message}") // Log error
                _ports.value = ApiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getPortByName(name: String) {
        viewModelScope.launch {
            try {
                val result = portRepository.fetchPortByName(name)
                _selectedPort.value = result
                Log.d("API Response", "Port fetched successfully: $result") // Log response
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch port: ${e.message}") // Log error
            }
        }
    }

    private fun addPorts(onPortSelected: (OverlayItem) -> Unit) {
        viewModelScope.launch {
            var retryCount = 0
            val maxRetries = 3
            val retryDelay = 1000L // 2 seconds

            while (retryCount < maxRetries) {
                val portsResult = _ports.value
                Log.i("MapViewModel", "Ports result: $portsResult")
                if (portsResult is ApiState.Success) {
                    val ports = portsResult.data

                    val items = ArrayList<OverlayItem>()
                    for (port in ports) {
                        val item = OverlayItem(port.first, "Port: ${port.first}", port.second)
                        var icon = BitmapFactory.decodeResource(context.resources, R.drawable.port_sign)
                        icon = Bitmap.createScaledBitmap(icon, 150, 150, false)
                        item.setMarker(BitmapDrawable(context.resources, icon))
                        items.add(item)
                    }

                    val overlay = ItemizedIconOverlay(
                        items,
                        object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                            override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
                                onPortSelected(item)
                                return true
                            }

                            override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
                                return false
                            }
                        },
                        context
                    )
                    map.overlays.add(overlay)
                    break
                } else {
                    Log.e("MapViewModel", "Failed to fetch ports, retrying... ($retryCount/$maxRetries)")
                    retryCount++
                    delay(retryDelay)
                    getPorts() // Retry fetching ports
                }
            }

            if (retryCount == maxRetries) {
                Log.e("MapViewModel", "Failed to fetch ports after $maxRetries retries")
            }
        }
    }
}