package com.ss.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        init()
    }

    fun init()
    {
        Handler().postDelayed(
                {
                   startActivity(Intent(this, Universal::class.java))
                    finish()
                }, 4036
        )
    }
}