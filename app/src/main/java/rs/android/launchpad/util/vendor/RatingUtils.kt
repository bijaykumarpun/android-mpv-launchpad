package rs.android.launchpad.util.vendor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.google.android.play.core.review.ReviewManagerFactory

// Probably a bad name :(
class RatingUtils {
    companion object {
        fun openAppInPlayStore(context: Context, packageName: String) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                "market://details?id=${packageName}".toUri()
            ).apply {
                setPackage("com.android.vending")
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                // Ignore
            }
        }


        /**
         * Remember this is not guaranteed to show the review dialog.
         * fallbackToPlayStore - if true, will open Play Store page if in-app review request fails
         */
        fun requestInAppRating(
            activity: Activity,
            fallbackToPlayStore: Boolean = false // Only triggered if request was unsuccessful & not if dialog was/wasn't displayed
        ) {
            val manager = ReviewManagerFactory.create(activity)

            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val reviewInfo = task.result
                    manager.launchReviewFlow(activity, reviewInfo)
                } else {
                    // This means the request failed and not that dialog was not displayed
                    // Eg. app was not downloaded from Play Store

                    // There is no way to know if dialog was or wasn't displayed
                    if (fallbackToPlayStore) {
                        openAppInPlayStore(activity, activity.packageName)
                    }
                }
            }
        }
    }
}