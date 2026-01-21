package rs.android.launchpad.repository

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val isUserPro: Flow<Boolean>
    fun initiatePurchaseFlow(activity: Activity)
}