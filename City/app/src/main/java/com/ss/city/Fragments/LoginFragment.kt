package com.ss.city.Fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.httpPost
import com.google.android.material.snackbar.Snackbar
import com.ss.city.Data.response_register
import com.ss.city.R
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init()
    {
        var submit = view!!.findViewById<Button>(R.id.submit)
        var email = view!!.findViewById<EditText>(R.id.email)
        var pass = view!!.findViewById<EditText>(R.id.pass)
        var register = view!!.findViewById<TextView>(R.id.register)

        submit.setOnClickListener()
        {
            Log.e("sajib", email.text.toString())
            Log.e("sajib", pass.text.toString())

            if (email.text.isNotEmpty())
            {
                if (pass.text.isNotEmpty())
                {
                    launch {
                        val json = JSONObject()
                        json.put("phone", email.text.toString())
                        json.put("password", pass.text.toString())




                        "http://10.0.2.2:8080/city/login"
                            .httpPost()
                            .header("Content-Type" to "application/json")
                            .body(json.toString())
                            //.responseString()
                            .responseObject(response_register.Deserializer())
                            { request, response, result ->

                                when (result) {
                                    is com.github.kittinunf.result.Result.Failure  -> {

                                        val ex = result.getException()
                                        //Toast.makeText(p, "failed",Toast.LENGTH_LONG).show()
                                        updateUI {
                                            Snackbar.make(view!!,"User dosent exist",Snackbar.LENGTH_LONG).show()
                                            Log.e("sajib",ex.toString())
                                            Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                                        }



                                    }
                                    is com.github.kittinunf.result.Result.Success -> {

                                        if (result.value.name.toString().length>20)
                                        {
                                            val pref = PreferenceManager.getDefaultSharedPreferences(context)
                                            val editor = pref.edit()
                                            editor
                                                .putString("Token", result.value.name)
                                                .apply()

                                            updateUI {
                                                val transaction = fragmentManager!!.beginTransaction()
                                                var home_fragment = HomeFragment()
                                                transaction.replace(R.id.univarsal, home_fragment)
                                                //transaction.addToBackStack(null)
                                                transaction.commit()
                                                Snackbar.make(view!!,"Welcome",Snackbar.LENGTH_LONG).show()
                                                //Log.e("sajib", result.value.name)
                                                //fragmentManager!!.popBackStack()
                                            }
                                        }
                                        else
                                        {
                                            updateUI {
                                                Snackbar.make(view!!,result.value.name.toString(),Snackbar.LENGTH_LONG).show()
                                                Log.e("sajib", result.value.name)
                                            }
                                        }

                                    }


                                }
                            }

                    }
                }
                else
                {
                    Snackbar.make(view!!, "Enter a valid password", Snackbar.LENGTH_LONG).show()
                }
            }
            else
            {
                Snackbar.make(view!!, "Enter a valid email", Snackbar.LENGTH_LONG).show()
            }
        }

        register.setOnClickListener()
        {
            val transaction = fragmentManager!!.beginTransaction()
            var fragment = RegisterFragment()
            transaction.replace(R.id.univarsal, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}