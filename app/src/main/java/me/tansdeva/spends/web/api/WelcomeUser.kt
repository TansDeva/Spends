package me.tansdeva.spends.web.api

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.google.gson.Gson
import me.tansdeva.spends.utils.AppUtils
import me.tansdeva.spends.utils.Prefs
import me.tansdeva.spends.web.ApiConstants
import me.tansdeva.spends.web.BaseRequest

class WelcomeUser(private val context: Context) {

    fun sendRequest() {
        val request = BaseRequest(context, null, Request.Method.GET, ApiConstants.WELCOME_API, Response.Listener {
            Prefs.putValue(context, Prefs.WELCOME_TEXT, it)
        }, Response.ErrorListener {
            //AppUtils.showToast(context, "Error")
        })
        request.send()
    }
}