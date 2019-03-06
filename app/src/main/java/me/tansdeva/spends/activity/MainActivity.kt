package me.tansdeva.spends.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import me.tansdeva.spends.R
import me.tansdeva.spends.adapter.TransactionAdapter
import me.tansdeva.spends.databinding.MainActivityBinding
import me.tansdeva.spends.model.TransactionBean
import me.tansdeva.spends.utils.Prefs
import me.tansdeva.spends.web.api.AddSpend
import me.tansdeva.spends.web.api.GetBalance
import me.tansdeva.spends.web.api.GetTransactions

class MainActivity : BaseActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: TransactionAdapter
    private val userTransactions = mutableListOf<TransactionBean>()
    private val savedTransactions = mutableListOf<TransactionBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        setupElements()
        setupListeners()
    }

    private fun setupElements() {
        getTransactions()
        Prefs.getString(context, Prefs.USER_BALANCE).apply {
            if (this != null) {
                binding.tvUserBalance.text = this
            }
        }
        adapter = TransactionAdapter(context, userTransactions)
        binding.rvTransactions.layoutManager = LinearLayoutManager(context)
        binding.rvTransactions.adapter = adapter
    }

    private fun setupListeners() {
        binding.srlTransactions.setOnRefreshListener {
            getTransactions()
            Handler().postDelayed({
                if (binding?.srlTransactions != null) {
                    binding.srlTransactions.isRefreshing = false
                }
            }, REFRESH_CLEAR_TIMER)
        }
        binding.fabAddSpend.setOnClickListener {
            Intent(context, AddSpendActivity::class.java).apply {
                startActivityForResult(this, RC_ADD_SPEND)
            }
        }
    }

    private fun getTransactions() {
        GetBalance(context).sendRequest(object : GetBalance.BalanceListener {
            override fun onBalanceFetch(balance: String) {
                binding.tvUserBalance.text = balance
            }
        })
        GetTransactions(context, object : GetTransactions.TransactionsListener {
            override fun onSuccess(transactions: List<TransactionBean>) {
                userTransactions.clear()
                userTransactions.addAll(transactions)
                for ((index, item) in savedTransactions.withIndex()) {
                    userTransactions.add(index, item)
                }
                adapter.notifyDataSetChanged()
            }
        }).sendRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RC_ADD_SPEND -> {
                    if (data != null && data.hasExtra(ARG_SPEND_DATA)) {
                        val spendData = data.getParcelableExtra<TransactionBean>(ARG_SPEND_DATA)
                        savedTransactions.add(0, spendData)
                        userTransactions.add(0, spendData)
                        AddSpend(context, spendData).sendRequest(object : AddSpend.SpendSyncListener {
                            override fun onSynced(transactionId: String) {
                                removeTransaction(transactionId)
                            }
                        })
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        }
    }

    private fun removeTransaction(transactionId: String) {
        for (i in 0..savedTransactions.lastIndex) {
            if (transactionId == savedTransactions[i].getId()) {
                savedTransactions.removeAt(i)
                break
            }
        }
        for (i in 0..userTransactions.lastIndex) {
            if (transactionId == userTransactions[i].getId()) {
                userTransactions.removeAt(i)
                break
            }
        }
        getTransactions()
    }

    companion object {
        const val RC_ADD_SPEND = 1001
        const val ARG_SPEND_DATA = "SpendData"
        private const val REFRESH_CLEAR_TIMER = 1000L
    }
}