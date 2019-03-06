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

class LoginUser(private val context: Context, isClear: Boolean) {

    init {
        if (isClear) {
            Prefs.removeKey(context, Prefs.USER_TOKEN)
        }
    }

    fun sendRequest() {
        val request = BaseRequest(context, false, Request.Method.POST, ApiConstants.USER_LOGIN, Response.Listener {
            val response = Gson().fromJson(it, LoginResponse::class.java)
            Prefs.putValue(context, Prefs.USER_TOKEN, response.token)
        }, Response.ErrorListener {
            AppUtils.showToast(context, "Error")
        })
        request.send()
    }

    class LoginResponse(val token: String)
}