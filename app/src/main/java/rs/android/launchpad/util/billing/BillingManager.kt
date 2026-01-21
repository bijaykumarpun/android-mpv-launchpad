package rs.android.launchpad.util.billing
import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
class BillingManager @Inject constructor(private val context: Context) {

    private var billingClient: BillingClient? = null

    // State to observe
    val isUserPro = MutableStateFlow(false)

    // Product ID configured in Play Console
    private val productId = "upgrade_to_pro"

    init {
        startConnection({})
    }

    // Initialize BillingClient and connect to Google Play
    fun startConnection(onReady: () -> Unit) {
        billingClient = BillingClient.newBuilder(context)
            .setListener { billingResult, purchases ->
                // Called after a purchase flow completes
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    purchases?.forEach { handlePurchase(it) }
                }
            }
            .enablePendingPurchases(
                PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()
            )
            .build()

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Successfully connected to Play Billing
                    queryPurchases() // Check if user already owns the product
                    onReady()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Retry logic can go here (optional)
            }
        })
    }

    // Handles both new and restored purchases
    private fun handlePurchase(purchase: Purchase) {
        if (purchase.products.contains(productId)) {
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                // Acknowledge the purchase so user keeps access
                val acknowledgeParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient?.acknowledgePurchase(acknowledgeParams) { ackResult ->
                    if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        isUserPro.value = true
                    }
                }
            } else if (purchase.isAcknowledged) {
                isUserPro.value = true
            }
        }
    }

    // Launch purchase flow for "remove_ads"
    fun launchPurchase(activity: Activity) {
        // Prepare product request
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        val queryParams = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient?.queryProductDetailsAsync(queryParams) { billingResult, productDetailResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailResult.productDetailsList.isNotEmpty()) {
                val productDetails = productDetailResult.productDetailsList[0]

                // Create purchase params using ProductDetails (new API)
                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(
                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                        )
                    )
                    .build()

                // Launch Google Play purchase UI
                billingClient?.launchBillingFlow(activity, billingFlowParams)
            } else {
                Log.e("Purchase Error", "Failed ${billingResult.responseCode}")
            }
        }
    }

    // Check if the product was previously purchased
    private fun queryPurchases() {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient?.queryPurchasesAsync(params) { billingResult, purchasesList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                purchasesList.forEach { handlePurchase(it) }
            }
        }
    }
}