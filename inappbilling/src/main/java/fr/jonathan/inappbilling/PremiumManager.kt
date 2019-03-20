package fr.jonathan.inappbilling

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import java.lang.ref.WeakReference

class PremiumManager(
    activity: Activity,
    licenseKey: String,
    private val purchaseProductIds: List<String>,
    private val subscriptionProductIds: List<String>
) : BillingProcessor.IBillingHandler
{
    companion object
    {
        private var isPremium: Boolean = false
    }

    private val bp: BillingProcessor = BillingProcessor.newBillingProcessor(activity, licenseKey, this)
    private val activity = WeakReference<Activity>(activity)

    init
    {
        bp.initialize()
    }

    override fun onBillingInitialized()
    {
        bp.loadOwnedPurchasesFromGoogle()
        refreshPremiumState()
    }

    override fun onPurchaseHistoryRestored()
    {
        refreshPremiumState()
    }

    private fun refreshPremiumState()
    {
        isPremium = false

        if (bp.isOneTimePurchaseSupported)
            purchaseProductIds.forEach { id -> isPremium = isPremium || bp.isPurchased(id) }

        if (bp.isSubscriptionUpdateSupported)
            subscriptionProductIds.forEach { id -> isPremium = isPremium || bp.isSubscribed(id) }
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?)
    {
        isPremium = true
    }

    override fun onBillingError(errorCode: Int, error: Throwable?)
    {

    }

    internal fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    {
        return bp.handleActivityResult(requestCode, resultCode, data)
    }


    internal fun release()
    {
        bp.release()
    }

    fun launchPlayPurchase(productId: String)
    {
        if (bp.isOneTimePurchaseSupported)
            bp.purchase(activity.get(), productId)
        else
            cantConnectToGooglePlay()
    }

    fun launchPlaySubscription(productId: String)
    {
        if (bp.isSubscriptionUpdateSupported)
            bp.subscribe(activity.get(), productId)
        else
            cantConnectToGooglePlay()
    }

    private fun cantConnectToGooglePlay()
    {
        activity.get()?.let {
            Toast.makeText(activity.get(), R.string.jg_cant_connect_to_google_play, Toast.LENGTH_SHORT).show()
        }
    }
}