package me.tansdeva.spends.web.api

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import me.tansdeva.spends.model.TransactionBean
import me.tansdeva.spends.utils.AppUtils
import me.tansdeva.spends.web.ApiConstants
import me.tansdeva.spends.web.BaseRequest
import java.nio.charset.Charset

class AddSpend(private val context: Context, private val transaction: TransactionBean) {

    fun sendRequest(listener: SpendSyncListener) {
        val request = object: BaseRequest(context, false, Request.Method.POST, ApiConstants.ADD_SPEND, Response.Listener {
            listener.onSynced(transaction.getId())
        }, Response.ErrorListener {
            AppUtils.showToast(context, "Error")
        }) {
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                return try {
                    transaction.toString().toByteArray(Charset.forName("utf-8"))
                } catch (e: Exception) {
                    null
                }
            }
        }
        request.send()
    }

    interface SpendSyncListener {
        fun onSynced(transactionId: String)
    }
}