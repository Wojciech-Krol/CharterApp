package lab.android.chartersapp.charters.data

import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.data.dataclasses.BoatPhoto
import lab.android.chartersapp.charters.data.dataclasses.BoatsResponse
import lab.android.chartersapp.charters.data.dataclasses.ChartersResponse
import lab.android.chartersapp.charters.data.dataclasses.Port
import lab.android.chartersapp.charters.data.dataclasses.PortsResponse
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @POST("login/")
    suspend fun login(): Response<Boolean>

    @POST("logout/")
    suspend fun logout(): Response<Boolean>

    @POST("register/")
    suspend fun register(): Response<Boolean>
}

interface BoatsApiService {
    @GET("boats/")
    suspend fun getBoats(): Response<BoatsResponse>

    @GET("boats/byPort/")
    suspend fun getBoatsByPort(): Response<BoatsResponse>

    @GET("boats/byCompany/")
    suspend fun getBoatsByCompany(): Response<BoatsResponse>

    @GET("boats/details/")
    suspend fun getBoatDetails(): Response<Boat>

    @GET("boatPhotos/")
    suspend fun getBoatPhotos(): Response<List<BoatPhoto>>
}

interface ChartersApiService {
    @GET("charters/ByBoat/")
    suspend fun getChartersByBoat(): Response<ChartersResponse>

    @GET("charters/ByUser/")
    suspend fun getChartersByUser(): Response<ChartersResponse>

    @POST("charters/add/")
    suspend fun addCharter(): Response<Boolean>
}

interface PortsApiService {
    @GET("ports/")
    suspend fun getPorts(): Response<PortsResponse>

    @GET("ports/details/")
    suspend fun getPortDetails(@Query("portName") portName: String): Response<Port>
}