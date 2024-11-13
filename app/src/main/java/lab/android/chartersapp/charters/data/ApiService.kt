package lab.android.chartersapp.charters.data

import retrofit2.http.GET
import retrofit2.Response
interface OfferApiService {
    @GET("offers")
    suspend fun getOffers(): Response<List<Offer>>
}