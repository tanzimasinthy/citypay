package com.ss.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.ss.city.Fragments.HomeFragment
import com.ss.city.Fragments.LoginFragment

class Universal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universal)
        supportActionBar?.hide()

        init()
    }

    fun init() {


        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.apply {
            val token = getString("Token", "Null")

            if (token == "Null")
            {
                val transaction = supportFragmentManager!!.beginTransaction()
                var fragment = LoginFragment()
                transaction.replace(R.id.univarsal, fragment)
                //transaction.addToBackStack(null)
                transaction.commit()
            }
            else
            {
                val transaction = supportFragmentManager!!.beginTransaction()
                var fragment = HomeFragment()
                transaction.replace(R.id.univarsal, fragment)
                //transaction.addToBackStack(null)
                transaction.commit()
            }
        }




    }
}