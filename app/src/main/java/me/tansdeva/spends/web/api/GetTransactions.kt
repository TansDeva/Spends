package me.tansdeva.spends.web.api

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.tansdeva.spends.model.TransactionBean
import me.tansdeva.spends.utils.AppUtils
import me.tansdeva.spends.web.ApiConstants
import me.tansdeva.spends.web.BaseRequest

class GetTransactions(private val context: Context, private val listener: TransactionsListener) {

    fun sendRequest() {
        val request = BaseRequest(context, true, Request.Method.GET, ApiConstants.GET_TRANSACTIONS, Response.Listener {
            val type = object : TypeToken<List<TransactionBean>>() {}.type
            val transactions: List<TransactionBean> = Gson().fromJson(it, type)
            listener.onSuccess(transactions)
        }, Response.ErrorListener {
            //AppUtils.showToast(context, "Error")
        })
        request.send()
    }

    interface TransactionsListener {
        fun onSuccess(transactions: List<TransactionBean>)
    }
}