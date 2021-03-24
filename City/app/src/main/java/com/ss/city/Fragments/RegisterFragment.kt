package com.ss.city.Fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.httpPost
import com.google.android.material.snackbar.Snackbar
import com.ss.city.Data.response_register
import com.ss.city.R
import kotlinx.coroutines.launch
import org.json.JSONObject


class RegisterFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init()
    {
        var submit = view!!.findViewById<Button>(R.id.submit_register)
        var first_name = view!!.findViewById<EditText>(R.id.first_name)
        var last_name = view!!.findViewById<EditText>(R.id.last_name)
        var phone_number = view!!.findViewById<EditText>(R.id.phone_number)
        var email_address = view!!.findViewById<EditText>(R.id.email)
        var pass1 = view!!.findViewById<EditText>(R.id.pass1)
        var pass2 = view!!.findViewById<EditText>(R.id.pass2)
        var checkbox = view!!.findViewById<CheckBox>(R.id.checkBox)
        var back = view!!.findViewById<ImageView>(R.id.back)



        submit.setOnClickListener()
        {
            if (first_name.text.isNotEmpty())
            {
                if (last_name.text.isNotEmpty())
                {
                    if (phone_number.text.isNotEmpty())
                    {
                        if (email_address.text.isNotEmpty())
                        {
                            if (pass1.text.isNotEmpty())
                            {
                                if (pass2.text.isNotEmpty())
                                {
                                    if (checkbox.isChecked)
                                    {
                                        if (pass1.text.toString()==pass2.text.toString())
                                        {
                                            register(first_name.text.toString()+" "+last_name.text.toString(),phone_number.text.toString(),email_address.text.toString(),pass1.text.toString())
                                        }
                                        else
                                        {
                                            Snackbar.make(view!!,"password dosent match", Snackbar.LENGTH_LONG).show()
                                        }
                                    }
                                    else
                                    {
                                        Snackbar.make(view!!,"Please Agree to the Terms and Conditions", Snackbar.LENGTH_LONG).show()
                                    }
                                }
                                else
                                {
                                    Snackbar.make(view!!,"Please Confirm Password", Snackbar.LENGTH_LONG).show()
                                }
                            }
                            else
                            {
                                Snackbar.make(view!!,"Please Provide a Password", Snackbar.LENGTH_LONG).show()
                            }
                        }
                        else
                        {
                            Snackbar.make(view!!,"Please Provide an Email Address", Snackbar.LENGTH_LONG).show()
                        }
                    }
                    else
                    {
                        Snackbar.make(view!!,"Please Provide a Phone Number", Snackbar.LENGTH_LONG).show()
                    }
                }
                else
                {
                    Snackbar.make(view!!,"Please Provide Your Last Name", Snackbar.LENGTH_LONG).show()
                }
            }
            else
            {
                Snackbar.make(view!!,"Please Provide Your First Name", Snackbar.LENGTH_LONG).show()
            }
        }

        back.setOnClickListener()
        {
            fragmentManager!!.popBackStack()
        }

    }

    fun register(
            name:String,
            phone:String,
            email:String,
            password:String
    ) {
        launch {

            val json = JSONObject()
            json.put("name", name)
            json.put("phone", phone)
            json.put("password", password)
            json.put("bank", "city")
            json.put("email", email)

            "http://10.0.2.2:8080/city/register"
                    .httpPost()
                    .header("Content-Type" to "application/json")
                    .body(json.toString())
                    //.responseString()
                    .responseObject(response_register.Deserializer())
                    { request, response, result ->

                        when (result) {
                            is com.github.kittinunf.result.Result.Failure -> {

                                val ex = result.getException()
                                //Toast.makeText(p, "failed",Toast.LENGTH_LONG).show()
                                updateUI {
                                    Snackbar.make(view!!, ex.toString(), Snackbar.LENGTH_LONG).show()
                                    Log.e("sajib", ex.toString())
                                    Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                                }


                            }
                            is com.github.kittinunf.result.Result.Success -> {

                                if (result.value.name.toString().length > 20) {

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
                                        Snackbar.make(view!!, "Registered", Snackbar.LENGTH_LONG).show()
                                        //Log.e("sajib", result.value.name)
                                        //fragmentManager!!.popBackStack()
                                    }
                                } else {
                                    updateUI {
                                        Snackbar.make(view!!, result.value.name.toString(), Snackbar.LENGTH_LONG).show()
                                        Log.e("sajib", result.value.name)
                                    }
                                }

                            }


                        }
                    }
        }
    }

}