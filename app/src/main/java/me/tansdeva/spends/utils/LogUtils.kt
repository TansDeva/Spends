package me.tansdeva.spends.utils

import android.util.Log
import me.tansdeva.spends.BuildConfig

class LogUtils {

    companion object {

        @JvmStatic fun d(tag: String, message: Any) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message.toString())
            }
        }
    }
}