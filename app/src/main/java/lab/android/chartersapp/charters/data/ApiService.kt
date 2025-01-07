package lab.android.chartersapp.charters.data

import retrofit2.http.GET
import retrofit2.Response
interface OfferApiService {
    @GET("boats/")
    suspend fun getBoats(): Response<BoatResponse>
}