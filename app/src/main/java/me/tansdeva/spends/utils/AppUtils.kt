package me.tansdeva.spends.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    companion object {
        const val SEPARATOR = ' '
        const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'"

        @JvmStatic fun isConnected(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

        fun hideKeyboard(context: Context, editText: View) {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(editText.windowToken, 0)

        }

        @JvmStatic fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic fun getFormattedTime(): String {
            return getFormattedTime(Date())
        }

        @JvmStatic fun getFormattedTime(date: Date): String {
            return SimpleDateFormat(DATE_FORMAT).format(date)
        }

        @JvmStatic fun getBalance(amount: String, currency: String): String {
            return amount.plus(AppUtils.SEPARATOR).plus(currency)
        }
    }
}