package lab.android.chartersapp.charters.data

import lab.android.chartersapp.charters.data.dataclasses.AddCharterResponse
import lab.android.chartersapp.charters.data.dataclasses.AuthResponse
import lab.android.chartersapp.charters.data.dataclasses.BoatPhotoResponse
import lab.android.chartersapp.charters.data.dataclasses.BoatResponse
import lab.android.chartersapp.charters.data.dataclasses.BoatsResponse
import lab.android.chartersapp.charters.data.dataclasses.ChartersResponse
import lab.android.chartersapp.charters.data.dataclasses.ChatsResponse
import lab.android.chartersapp.charters.data.dataclasses.MessagesResponse
import lab.android.chartersapp.charters.data.dataclasses.PortResponse
import lab.android.chartersapp.charters.data.dataclasses.PortsResponse
import lab.android.chartersapp.charters.data.dataclasses.UserData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @Multipart
    @POST("login/")
    suspend fun login(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): Response<AuthResponse>

    @POST("logout/")
    suspend fun logout(): Response<AuthResponse>

    @POST("register/")
    suspend fun register(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): Response<AuthResponse>

    @GET("session/")
    suspend fun getSession(): Response<AuthResponse>

    @GET("getUser/")
    suspend fun getUser(): Response<UserData>

    @POST("changePassword/")
    suspend fun changePassword(
        @Part("newPassword") newPassword: RequestBody,
        @Part("oldPassword") oldPassword: RequestBody
    ): Response<AuthResponse>

    @POST("changeEmail/")
    suspend fun changeEmail(
        @Part("newEmail") newEmail: RequestBody
    ): Response<AuthResponse>
}

interface BoatsApiService {
    @GET("boats/")
    suspend fun getBoats(): Response<BoatsResponse>

    @GET("boats/byPort/")
    suspend fun getBoatsByPort(@Query("portName") portName: String): Response<BoatsResponse>

    @GET("boats/byCompany/")
    suspend fun getBoatsByCompany(@Query("companyName") companyName: String): Response<BoatsResponse>

    @GET("boats/details/")
    suspend fun getBoatDetails(@Query("boatName") boatName: String): Response<BoatResponse>

    @GET("boatPhotos/")
    suspend fun getBoatPhotos(@Query("boatName") boatName: String): Response<BoatPhotoResponse>

    @GET("photos/{img_name}/")
    suspend fun getPhoto(@Path("img_name") imgName: String): Response<ResponseBody>
}

interface ChartersApiService {
    @GET("charters/ByBoat/")
    suspend fun getChartersByBoat(@Query("boatName") boatName: String): Response<ChartersResponse>

    @GET("charters/ByUser/")
    suspend fun getChartersByUser(@Query("userName") username: String): Response<ChartersResponse>

    @POST("charters/add/")
    suspend fun addCharter(
        @Part("boatName") boatName: RequestBody,
        @Part("startDate") startDate: RequestBody,
        @Part("endDate") endDate: RequestBody
    ): Response<AddCharterResponse>
}

interface PortsApiService {
    @GET("ports/")
    suspend fun getPorts(): Response<PortsResponse>

    @GET("port/details/")
    suspend fun getPortDetails(@Query("portName") portName: String): Response<PortResponse>
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