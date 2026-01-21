package rs.android.launchpad.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("Hello from Home!")
    val message: StateFlow<String> = _message
}