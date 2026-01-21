package rs.android.launchpad

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import rs.android.launchpad.util.remote_config.FirebaseRemoteConfiguration

@HiltAndroidApp
class LaunchpadApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseRemoteConfiguration().initialize()
    }
}