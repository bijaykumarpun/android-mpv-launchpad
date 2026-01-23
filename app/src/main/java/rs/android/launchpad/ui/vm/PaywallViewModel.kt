package rs.android.launchpad.ui.vm

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.android.launchpad.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _isUserPro: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserPro: StateFlow<Boolean> = _isUserPro

    private val _message: MutableStateFlow<String> = MutableStateFlow("Purchase Pro!")
    val message: StateFlow<String> = _message

    init {
        initializeUserProStatus()
    }

    private fun initializeUserProStatus() {
        viewModelScope.launch {
            productRepository.isUserPro.collect {
                _isUserPro.value = it
                if (it == true) {
                    _message.value = "User is pro!"
                }
            }
        }

    }

    fun onPurchaseTapped(activity: Activity){
        viewModelScope.launch {
            productRepository.initiatePurchaseFlow(activity)
        }
    }
}