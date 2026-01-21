package rs.android.launchpad.repository.impl

import android.app.Activity
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import rs.android.launchpad.repository.ProductRepository
import rs.android.launchpad.util.billing.BillingManager


@Singleton
class ProductRepositoryImpl @Inject constructor(
    val billingManager: BillingManager,
) : ProductRepository {
    override val isUserPro: Flow<Boolean> = billingManager.isUserPro

    override fun initiatePurchaseFlow(activity: Activity) {
        billingManager.launchPurchase(activity)
    }
}