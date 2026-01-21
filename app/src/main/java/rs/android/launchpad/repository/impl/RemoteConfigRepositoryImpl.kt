package rs.android.launchpad.repository.impl

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import rs.android.launchpad.models.ClickAction
import rs.android.launchpad.models.RemoteFeature
import rs.android.launchpad.repository.RemoteConfigRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteConfigRepositoryImpl @Inject constructor(private val remoteConfig: FirebaseRemoteConfig) :
    RemoteConfigRepository {
    private val _features = MutableStateFlow<List<RemoteFeature>>(emptyList())
    val features: StateFlow<List<RemoteFeature>> = _features

    init {
        loadRemoteFeatures()
    }

    private fun loadRemoteFeatures() {
        val json = remoteConfig.getString("remote_features")
        if (json.isBlank()) {
            _features.value = emptyList()
        }

        try {
            // Parse JSON
            val featureList = Json.decodeFromString<List<RemoteFeatureDTO>>(json)
            val mapped = featureList.map {
                RemoteFeature(
                    id = it.id,
                    title = it.title,
                    clickAction = ClickAction.fromString(it.action),
                    data = it.data,
                    skipForPro = it.skipForPro
                )
            }
            _features.value = mapped

        } catch (e: Exception) {
            _features.value = emptyList()
        }
    }

    override fun getRemoteConfigs(): StateFlow<List<RemoteFeature>> {
        return features

    }
}

@Serializable
data class RemoteFeatureDTO(
    val id: String,
    val title: String,
    val action: String,
    val data: String,
    val skipForPro: Boolean
)

/**
 * [{"id":"home-screen-id","title":"Go To Settings Screen","action":"NavigateToLocalScreen","data":"settings","skipForPro":false},{"id":"home-screen-id","title":"Try New App","action":"NavigateToPlayStore","data":"playstore.app.package","skipForPro":false}]
 */