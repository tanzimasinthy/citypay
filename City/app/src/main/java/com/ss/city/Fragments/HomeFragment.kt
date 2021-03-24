package com.ss.city.Fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.google.android.material.snackbar.Snackbar
import com.ss.city.Data.response_register
import com.ss.city.R
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init()
    {
        var logout = view!!.findViewById<Button>(R.id.logout)

        logout.setOnClickListener()
        {
            val pref = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = pref.edit()
            editor
                .putString("Token", "Null")
                .apply()

            val transaction = fragmentManager!!.beginTransaction()
            var fragment = LoginFragment()
            transaction.replace(R.id.univarsal, fragment)
            //transaction.addToBackStack(null)
            transaction.commit()
        }


    }


}