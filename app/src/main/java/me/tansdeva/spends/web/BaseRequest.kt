package me.tansdeva.spends.web

import android.content.Context
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import me.tansdeva.spends.utils.AppUtils
import me.tansdeva.spends.utils.Prefs
import me.tansdeva.spends.web.api.LoginUser

open class BaseRequest(private val context: Context, private val isToken: Boolean?, method: Int, url: String,
                       listener: Response.Listener<String>, errorListener: Response.ErrorListener) :
    StringRequest(method, url, listener, errorListener) {
    private var isLogin = false

    override fun getHeaders(): MutableMap<String, String> {
        return if (isToken != null) {
            val headers = mutableMapOf<String, String>()
            headers["Accept"] = "application/json"
            if (method == Request.Method.POST) {
                headers["Content-Type"] = "application/json"
            }
            if (isToken) {
                Prefs.getString(context, Prefs.USER_TOKEN).apply {
                    if (this != null) {
                        headers["Authorization"] = String.format("Bearer %s", this)
                    } else {
                        isLogin = true
                    }
                }
            }
            headers
        } else super.getHeaders()
    }

    fun send() {
        if (isLogin) {
            LoginUser(context, false).sendRequest()
            AppUtils.showToast(context, "Retry")
        } else {
            retryPolicy = DefaultRetryPolicy(
                TIMEOUT_MILLIS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            VolleyRequest.getInstance(context).addToRequestQueue(this)
        }
    }

    companion object {
        const val TIMEOUT_MILLIS = 10000
    }
}