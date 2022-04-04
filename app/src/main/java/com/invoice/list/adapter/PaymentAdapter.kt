package com.invoice.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.invoice.list.R
import com.invoice.list.model.Payment
import com.invoice.list.model.PaymentType

class PaymentAdapter(
    val list: List<Payment>,
    val itemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.payment_list_design, parent, false),
            itemClick

        )
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}

class PaymentViewHolder(
    itemView: View,
    var itemClick: (position: Int) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {


    val paymentAmount: TextView
    val paymentDate: TextView


    init {
        paymentAmount = itemView.findViewById(R.id.amount)
        paymentDate = itemView.findViewById(R.id.date)
        itemView.setOnClickListener { itemClick(adapterPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bind(model: Payment) {
        paymentAmount.text = "${model.amount}₺"
        paymentDate.text = model.date

    }

}