package me.tansdeva.spends.web.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.google.gson.Gson
import me.tansdeva.spends.utils.AppUtils
import me.tansdeva.spends.utils.Prefs
import me.tansdeva.spends.web.ApiConstants
import me.tansdeva.spends.web.BaseRequest

class GetBalance(private val context: Context) {

    fun sendRequest(listener: BalanceListener) {
        val request = BaseRequest(context, true, Request.Method.GET, ApiConstants.GET_BALANCE, Response.Listener {
            val amount = Gson().fromJson(it, UserBalance::class.java)
            AppUtils.getBalance(amount.balance, amount.currency).apply {
                Prefs.putValue(context, Prefs.USER_BALANCE, this)
                listener.onBalanceFetch(this)
            }
        }, Response.ErrorListener {
            //AppUtils.showToast(context, "Error")
        })
        request.send()
    }

    interface BalanceListener {
        fun onBalanceFetch(balance: String)
    }

    class UserBalance(val balance: String, val currency: String)
}