package me.tansdeva.spends.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.tansdeva.spends.databinding.TransactionBinding
import me.tansdeva.spends.model.TransactionBean
import me.tansdeva.spends.utils.AppUtils

class TransactionAdapter(private val context: Context, private val transactions: List<TransactionBean>):
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val inflater = LayoutInflater.from(context)
        return TransactionHolder(TransactionBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        if (position in 0..transactions.lastIndex) {
            holder.bindData(transactions[position])
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class TransactionHolder(private val binding: TransactionBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(transaction: TransactionBean) {
            binding.tvDetail.text = transaction.description
            binding.tvAmount.text = AppUtils.getBalance(transaction.amount, transaction.currency)
        }
    }
}