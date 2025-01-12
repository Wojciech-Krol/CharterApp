package lab.android.chartersapp.charters.presentation.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.preference.PreferenceManager
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import lab.android.chartersapp.R
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

class MapViewModel @Inject constructor(
    private val portRepository: PortRepository
) : ViewModel() {

    lateinit var map: MapView
    lateinit var locationButton: ImageButton
    private lateinit var mapController: IMapController
    private lateinit var context: Context

    fun initMap(context: Context): MapView {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController = map.controller
        this.context = context

        //Setting the zoom level of the map
        mapController.setZoom(12.0)

        //Setting the center of the map
        mapController.setCenter(GeoPoint(54.04, 21.758889))
        locateMe()

        //Adding the location overlay
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        locationOverlay.enableMyLocation()
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.target)
        val scaledIcon = Bitmap.createScaledBitmap(icon, 75, 75, false)
        locationOverlay.setPersonHotspot(scaledIcon.width / 2f, scaledIcon.height / 2f)
        locationOverlay.setPersonIcon(scaledIcon)
        map.overlays.add(locationOverlay)

        //Enable zoom controls
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)

        //Adding metric scale
        var scaleBar = org.osmdroid.views.overlay.ScaleBarOverlay(map)
        scaleBar.setAlignRight(true)
        scaleBar.setAlignBottom(true)
        scaleBar.setScaleBarOffset(15, 15)
        map.overlays.add(scaleBar)

        //Adding the ports
        addPorts()

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

            val startPoint = GeoPoint(lat, lon)
            mapController.setCenter(startPoint)
        }
    }

    private fun addPorts() {
        //For testing Only
        val ports = listOf(
            Pair("Giżycko", GeoPoint(54.04, 21.758889)),
            Pair("Węgorzewo", GeoPoint(54.216667, 21.75)),
            Pair("Mikołajki", GeoPoint(53.798056, 21.573611)),
            Pair("Ryn", GeoPoint(53.816667, 21.5)),
            Pair("Pisz", GeoPoint(53.633333, 21.816667)),
            Pair("Orzysz", GeoPoint(53.8, 21.9)),
            Pair("Ruciane-Nida", GeoPoint(53.633333, 21.5)),
            Pair("Mragowo", GeoPoint(53.866667, 21.3)),
            Pair("Sztynort", GeoPoint(54.083333, 21.116667)),
            Pair("Wilkasy", GeoPoint(54.083333, 21.516667))
        )

//        val ports = portRepository.fetchPorts().map { port -> Pair(port.name, GeoPoint(port.latitude, port.longitude)) }

        val items = ArrayList<OverlayItem>()
        for (port in ports) {
            val item = OverlayItem(port.first, "Port: ${port.first}", port.second)
            var icon = BitmapFactory.decodeResource(context.resources, R.drawable.port_sign)
            icon = Bitmap.createScaledBitmap(icon, 75, 75, false)
            item.setMarker(BitmapDrawable(context.resources, icon))
            items.add(item)
        }

        val overlay = ItemizedIconOverlay(
            items,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
                    // Placeholder for action when marker is clicked
                    // Example: show a Toast or navigate to another screen
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
                    return false
                }
            },
            context
        )
        map.overlays.add(overlay)
    }
}