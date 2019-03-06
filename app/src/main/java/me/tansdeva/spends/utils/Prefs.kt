package me.tansdeva.spends.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs {

    companion object {
        private const val PREF_NAME = "spends_pref"
        const val WELCOME_TEXT = "welcome_text"
        const val USER_TOKEN = "user_token"
        const val USER_BALANCE = "user_balance"

        private fun getPrefs(context: Context): SharedPreferences? {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        private fun getEditor(context: Context): SharedPreferences.Editor? {
            val prefs = getPrefs(context)
            return prefs?.edit()
        }

        fun putValue(context: Context, key: String, value: String) {
            val editor = getEditor(context) ?: return
            editor.putString(key, value)
            editor.apply()

        }

        fun getString(context: Context, key: String): String? {
            val prefs = getPrefs(context)
            return prefs?.getString(key, null)
        }

        fun removeKey(context: Context, key: String) {
            val prefs = getPrefs(context)
            prefs?.edit()?.remove(key)?.apply()
        }
    }
}