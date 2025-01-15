package lab.android.chartersapp.charters.data

import lab.android.chartersapp.charters.data.dataclasses.Boat
import lab.android.chartersapp.charters.data.dataclasses.BoatPhoto
import lab.android.chartersapp.charters.data.dataclasses.BoatsResponse
import lab.android.chartersapp.charters.data.dataclasses.ChartersResponse
import lab.android.chartersapp.charters.data.dataclasses.Chat
import lab.android.chartersapp.charters.data.dataclasses.ChatsResponse
import lab.android.chartersapp.charters.data.dataclasses.MessagesResponse
import lab.android.chartersapp.charters.data.dataclasses.Port
import lab.android.chartersapp.charters.data.dataclasses.PortsResponse
import lab.android.chartersapp.charters.data.dataclasses.UserData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @POST("login/")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Boolean>

    @POST("logout/")
    suspend fun logout(): Response<Boolean>

    @POST("register/")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String
    ): Response<Boolean>

    @GET("getUser/")
    suspend fun getUser(): Response<UserData>

    @POST("changePassword/")
    suspend fun changePassword(
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Response<Boolean>

    @POST("changeEmail/")
    suspend fun changeEmail(
        @Query("newEmail") newEmail: String
    ): Response<Boolean>
}

interface BoatsApiService {
    @GET("boats/")
    suspend fun getBoats(): Response<BoatsResponse>

    @GET("boats/byPort/")
    suspend fun getBoatsByPort(@Query("portName") portName: String): Response<BoatsResponse>

    @GET("boats/byCompany/")
    suspend fun getBoatsByCompany(@Query("companyName") companyName: String): Response<BoatsResponse>

    @GET("boats/details/")
    suspend fun getBoatDetails(@Query("boatName") boatName: String): Response<Boat>

    @GET("boatPhotos/")
    suspend fun getBoatPhotos(@Query("boatName") boatName: String): Response<List<BoatPhoto>>

    @GET("photos/{img_name}/")
    suspend fun getPhoto(@Path("img_name") imgName: String): Response<ResponseBody>
}

interface ChartersApiService {
    @GET("charters/ByBoat/")
    suspend fun getChartersByBoat(@Query("boatName") boatName: String): Response<ChartersResponse>

    @GET("charters/ByUser/")
    suspend fun getChartersByUser(): Response<ChartersResponse>

    @POST("charters/add/")
    suspend fun addCharter(
        @Query("boatName") boatName: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<Boolean>
}

interface PortsApiService {
    @GET("ports/")
    suspend fun getPorts(): Response<PortsResponse>

    @GET("port/details/")
    suspend fun getPortDetails(@Query("portName") portName: String): Response<Port>
}

interface ChatsApiService {

    @GET("chats/")
    suspend fun getAllChats(): Response<ChatsResponse>

    @FormUrlEncoded
    @POST("chats/create/")
    suspend fun createChat(
        @Field("title") title: String
    ): Response<Any>
}

interface MessagesApiService {
    @GET("messages/")
    suspend fun getMessages(
        @Query("chat_title") chatTitle: String
    ): Response<MessagesResponse>

    @FormUrlEncoded
    @POST("messages/create/")
    suspend fun sendMessage(
        @Field("chat_title") chatTitle: String,
        @Field("content") message: String
    ): Response<Any>
}