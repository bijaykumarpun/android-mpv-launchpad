package rs.android.launchpad.util.remote_config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import rs.android.launchpad.R

class FirebaseRemoteConfiguration {
    companion object {
        const val REMOTE_CONFIG_MIN_FETCH_INTERVAL_DEBUG = 5L
        const val REMOTE_CONFIG_MIN_FETCH_INTERVAL_RELEASE = 3600L
    }

    private lateinit var remoteConfig: FirebaseRemoteConfig
    fun initialize() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(if (rs.android.launchpad.DEBUG) REMOTE_CONFIG_MIN_FETCH_INTERVAL_DEBUG else REMOTE_CONFIG_MIN_FETCH_INTERVAL_RELEASE)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
    }
}