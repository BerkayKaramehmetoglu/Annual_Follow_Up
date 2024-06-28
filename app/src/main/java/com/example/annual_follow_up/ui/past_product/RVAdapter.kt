package com.example.annual_follow_up.ui.past_product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.annual_follow_up.R
import com.example.annual_follow_up.sqlite.FollowUp

class RVAdapter(private val context: Context, private val getAllProducts: ArrayList<FollowUp>) :
    RecyclerView.Adapter<RVAdapter.cardView>() {

    inner class cardView(view: View) : RecyclerView.ViewHolder(view) {

        var productDate: TextView
        var productName: TextView
        var productAmount: TextView
        var productSales: TextView
        var productExpense: TextView
        var productEarning: TextView

        init {
            productDate = view.requireViewById(R.id.productDate)
            productName = view.requireViewById(R.id.productName)
            productAmount = view.requireViewById(R.id.productAmount)
            productSales = view.requireViewById(R.id.productSales)
            productExpense = view.requireViewById(R.id.productExpense)
            productEarning = view.requireViewById(R.id.productEarning)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardView {

        val design = LayoutInflater.from(context).inflate(R.layout.past_product_card, parent, false)

        return cardView(design)
    }

    override fun getItemCount(): Int {
        return getAllProducts.size
    }

    override fun onBindViewHolder(holder: cardView, position: Int) {

        val getAllProduct = getAllProducts[position]

        holder.productDate.text = getAllProduct.productDate
        holder.productName.text = getAllProduct.productName
        holder.productAmount.text = getAllProduct.productAmount.toString()
        holder.productSales.text = getAllProduct.productSales.toString()
        holder.productExpense.text = getAllProduct.productExpense.toString()
        holder.productEarning.text = getAllProduct.productEarning.toString()

        setProductEarningColor(holder.productEarning, getAllProduct.productEarning)

    }

    fun updateProducts(newProducts: List<FollowUp>) {
        getAllProducts.clear()
        getAllProducts.addAll(newProducts)
        notifyDataSetChanged()
    }

    private fun setProductEarningColor(productEarningView: TextView, productEarning: Int) {
        val color = when {
            productEarning < 0 -> ContextCompat.getColor(context, R.color.red)
            productEarning == 0 -> ContextCompat.getColor(context, R.color.black)
            else -> ContextCompat.getColor(context, R.color.green)
        }
        productEarningView.setTextColor(color)
    }

}