package lab.android.chartersapp.charters.data

import retrofit2.Response
import javax.inject.Inject

class BoatRepository @Inject constructor(private val offerApiService: OfferApiService) {
    suspend fun fetchBoats(): Response<List<Offer>> = offerApiService.getOffers()
}