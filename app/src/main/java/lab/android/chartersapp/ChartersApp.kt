package lab.android.chartersapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    // Optional: You can perform any global initialization here
    override fun onCreate() {
        super.onCreate()
        // Initialization code can go here
    }
}
