package me.tansdeva.spends

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle

class SpendsApp : Application() {
    private val context = applicationContext

    override fun onCreate() {
        super.onCreate()
        //Fabric.with(context, Crashlytics()
        registerActivityLifecycleCallbacks(LifeCycleCallbacks())
    }

    class LifeCycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity?) {
            
        }

        override fun onActivityResumed(p0: Activity?) {
        }

        override fun onActivityStarted(p0: Activity?) {
        }

        override fun onActivityDestroyed(p0: Activity?) {
        }

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        }

        override fun onActivityStopped(p0: Activity?) {
        }

        override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            p0?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}