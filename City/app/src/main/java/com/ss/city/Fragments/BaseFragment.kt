package com.ss.city.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ss.city.R
import kotlinx.coroutines.*
import org.jetbrains.anko.runOnUiThread
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), CoroutineScope {

    private var isLoading = false


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    protected fun updateUI(action: () -> Unit) {
        if (!isActive) return
        launch(Dispatchers.Main) {
            action()
        }
    }

    override fun onDetach() {
        super.onDetach()
        cancel()
    }

    fun showLoading() {
        context?.runOnUiThread {
            if (!isLoading) {
                //loading.setMessage(context?.getString(R.string.loading))
                isLoading = true
                //loading.show()
            }
        }
    }

//    fun stopLoading() {
//        loading.dismiss()
//    }

    fun showMessage(message: String) {
        context?.runOnUiThread {


            val viewGroup = (activity
                ?.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

            val alert = Snackbar.make(
                viewGroup,
                message, Snackbar.LENGTH_SHORT
            )
            val snackView = alert.view
            snackView.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_on_secondary))


            alert.setText(message).show()

        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onTokenExpiredEvent(event: TokenExpiredEvent) {
//        launch {
//            val refresh =  authService.refreshSession()
//            if(!refresh.isSuccessful){
//                logOutUser()
//            }
//        }
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onLogOutEvent(event: TokenExpiredEvent) {
//        logOutUser()
//    }

//    private fun logOutUser() {
//
//    }
}


