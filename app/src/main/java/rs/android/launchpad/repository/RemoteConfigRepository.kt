package rs.android.launchpad.repository

import kotlinx.coroutines.flow.StateFlow
import rs.android.launchpad.models.RemoteFeature

interface RemoteConfigRepository {
    fun getRemoteConfigs(): StateFlow<List<RemoteFeature>>
}