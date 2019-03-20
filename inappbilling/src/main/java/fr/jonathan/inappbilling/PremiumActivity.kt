package fr.jonathan.inappbilling

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class PremiumActivity : AppCompatActivity()
{
    protected lateinit var premiumManager: PremiumManager
    protected abstract val licenseKey:String
    protected abstract val purchaseProductIds:List<String>
    protected abstract val subscriptionProductIds:List<String>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        premiumManager = PremiumManager(this, licenseKey, purchaseProductIds, subscriptionProductIds)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (!premiumManager.onActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy()
    {
        if (::premiumManager.isInitialized)
            premiumManager.release()

        super.onDestroy()
    }
}