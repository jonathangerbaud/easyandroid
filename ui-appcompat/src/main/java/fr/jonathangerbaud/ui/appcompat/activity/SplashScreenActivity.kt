package fr.jonathangerbaud.ui.appcompat.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.jonathangerbaud.core.ext.runDelayedOnUiThread
// Move to ui.appcompat module
abstract class AbstractSplashScreenActivity : AppCompatActivity()
{
    private var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runDelayedOnUiThread(getDelayBeforeNextActivity()) {
            displayMain()
        }
    }

    private fun displayMain()
    {
        finish()
        startActivity(Intent(this, getNextActivity()))
    }

    override fun onPause()
    {
        super.onPause()
        paused = true
    }

    override fun onResume()
    {
        super.onResume()

        // If user left the app before the main had the chance to be displayed
        // Then when he comes back, display the main screen
        if (paused)
            displayMain()
    }

    /**
     * Getting next activity class
     */
    abstract fun getNextActivity():Class<Activity>

    /**
     * Delay in millis before showing next activity
     */
    protected fun getDelayBeforeNextActivity():Long = 2000
}