package me.tansdeva.spends.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import me.tansdeva.spends.R
import me.tansdeva.spends.databinding.AddSpendActivityBinding
import me.tansdeva.spends.model.TransactionBean
import me.tansdeva.spends.utils.AppUtils

class AddSpendActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    val currencies = arrayListOf<String>("GBP", "USD", "INR")
    private lateinit var binding: AddSpendActivityBinding
    private var currency: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_spend)
        setContentView(binding.root)
        binding.addSpend = this
        setupListeners()
    }

    private fun setupListeners() {
        binding.spCurrency.onItemSelectedListener = this
        binding.etSpendDetail.
            setOnEditorActionListener { _, _, keyEvent: KeyEvent? ->
                AppUtils.hideKeyboard(context, binding.etSpendDetail)
                binding.btnSubmit.performClick()
                true
            }
        binding.btnSubmit.setOnClickListener {
            val message = when {
                binding.etSpendAmount.text.isEmpty() -> "Enter amount"
                binding.etSpendDetail.text.isEmpty() -> "Enter detail"
                (currency == null) -> "Choose currency"
                else -> null
            }
            if (message != null) {
                AppUtils.showToast(context, message!!)
            } else {
                val timeStamp = System.currentTimeMillis().toString()
                val transaction = TransactionBean(timeStamp, AppUtils.getFormattedTime(),
                    binding.etSpendDetail.text.toString(), binding.etSpendAmount.text.toString(), currency!!)
                Intent().apply {
                    putExtra(MainActivity.ARG_SPEND_DATA, transaction)
                    clearActivity(this)
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id) {
            R.id.sp_currency -> currency = currencies[p2]
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        currency = null
    }

    override fun onBackPressed() {
        if (binding.etSpendAmount.text.isEmpty() && binding.etSpendDetail.text.isEmpty()) {
            clearActivity(null)
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Exit")
            }.setMessage("Current changes will be lost")
                .setPositiveButton("Yes") { _, _ ->
                    clearActivity(null)
                }
                .setNegativeButton("No") { _, _ ->
                    //Do nothing
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun clearActivity(data: Intent?) {
        if (data != null) {
            setResult(Activity.RESULT_OK, data)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }
}