package rs.android.launchpad.repository.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rs.android.launchpad.repository.FeatureRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureRepositoryImpl @Inject constructor() : FeatureRepository {
    override fun isSettingsEnabled(): StateFlow<Boolean> {
        return MutableStateFlow(false).asStateFlow()
    }
}