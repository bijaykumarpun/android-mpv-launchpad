package rs.android.launchpad.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.android.launchpad.models.RemoteFeature
import rs.android.launchpad.repository.FeatureRepository
import rs.android.launchpad.repository.RemoteConfigRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val featureRepository: FeatureRepository,
    private val remoteConfigRepo: RemoteConfigRepository
) :
    ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("Hello from Home!")
    val message: StateFlow<String> = _message

    //
    private val _isUserPro = MutableStateFlow(false)
    val isUserPro: StateFlow<Boolean> = _isUserPro

    // From local repository
    private val _isFeatureEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFeatureEnabled: StateFlow<Boolean> = _isFeatureEnabled

    // From remote config
    private val _bottomRemoteFeature: MutableStateFlow<RemoteFeature?> = MutableStateFlow(null)
    val bottomRemoteFeature: StateFlow<RemoteFeature?> = _bottomRemoteFeature

    init {
        initializeRemoteFeatures()
        observeFeatureToggle()
    }

    private fun observeFeatureToggle() {
        viewModelScope.launch(Dispatchers.IO) {
            featureRepository.isSettingsEnabled().collect { isEnabled ->
                _isFeatureEnabled.value = isEnabled
            }
        }

    }


    // Remote feature
    private fun initializeRemoteFeatures() {
        viewModelScope.launch {
            remoteConfigRepo.getRemoteConfigs().collect {
                val bottomCtaFeature =
                    it.find { feature -> feature.id == RemoteFeature.Ids.ID_HOME_SCREEN_CLICK_BUTTON && feature.skipForPro != _isUserPro.value }

                _bottomRemoteFeature.value = bottomCtaFeature
            }
        }
    }
}