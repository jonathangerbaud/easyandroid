package fr.jonathangerbaud.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commitNow()
    }
}