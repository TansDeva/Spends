package me.tansdeva.spends.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.view.View
import me.tansdeva.spends.R
import me.tansdeva.spends.databinding.SplashActivityBinding
import me.tansdeva.spends.utils.Prefs
import me.tansdeva.spends.web.api.LoginUser
import me.tansdeva.spends.web.api.WelcomeUser

class SplashActivity : BaseActivity() {
    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setContentView(binding.root)
        setupElements()
    }

    private fun setupElements() {
        Prefs.getString(context, Prefs.WELCOME_TEXT).apply {
            if (this != null) {
                binding.tvWelcomeText.text = this
                binding.tvWelcomeText.visibility = View.VISIBLE
            }
        }
        callNextScreen()
    }

    private fun callNextScreen() {
        WelcomeUser(context).sendRequest()
        LoginUser(context, true).sendRequest()
        Handler().postDelayed({
            if (context != null) {
                startActivity(Intent(context, MainActivity::class.java))
            }
        }, SPLASH_TIMER)
    }

    override fun onBackPressed() {
        //Do nothing
    }

    companion object {
        const val SPLASH_TIMER = 1000L
    }
}