package lab.android.chartersapp.charters.data

import android.annotation.SuppressLint
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Disable SSL certificate validation (not recommended for production)
        val trustManager = @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true } // Disable hostname verification
            .addInterceptor(LoggingInterceptor())
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8000/db/") // Change this to your server URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): BoatsApiService {
        return retrofit.create(BoatsApiService::class.java)
    }
    @Provides
    @Singleton
    fun providePortsApiService(retrofit: Retrofit): PortsApiService {
        return retrofit.create(PortsApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideChatsApiService(retrofit: Retrofit): ChatsApiService {
        return retrofit.create(ChatsApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideMessagesApiService(retrofit: Retrofit): MessagesApiService {
        return retrofit.create(MessagesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChartersApiService(retrofit: Retrofit): ChartersApiService {
        return retrofit.create(ChartersApiService::class.java)
    }

}

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("HTTP Request", "URL: ${request.url}")

        val requestBody = request.body
        if (requestBody != null) {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            val charset = requestBody.contentType()?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            Log.d("HTTP Request", "Body: ${buffer.readString(charset)}")
        }

        val response = chain.proceed(request)
        val responseBody = response.body
        val contentLength = responseBody?.contentLength() ?: 0

        if (contentLength != 0L) {
            val source = responseBody?.source()
            source?.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source?.buffer
            val charset = responseBody?.contentType()?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
            val bodyString = buffer?.clone()?.readString(charset)
            Log.d("HTTP Response", "Body: $bodyString")

        }

        return response
    }
}