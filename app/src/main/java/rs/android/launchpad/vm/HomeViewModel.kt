package rs.android.launchpad.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.android.launchpad.repository.FeatureRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val featureRepository: FeatureRepository) :
    ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("Hello from Home!")
    val message: StateFlow<String> = _message

    private val _isFeatureEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFeatureEnabled: StateFlow<Boolean> = _isFeatureEnabled

    init {
        observeFeatureToggle()
    }

    private fun observeFeatureToggle() {
        viewModelScope.launch(Dispatchers.IO) {
            featureRepository.isSettingsEnabled().collect { isEnabled ->
                _isFeatureEnabled.value = isEnabled
            }
        }

    }
}