package rs.android.launchpad.repository

import kotlinx.coroutines.flow.StateFlow

interface FeatureRepository {
    fun isSettingsEnabled(): StateFlow<Boolean>
}