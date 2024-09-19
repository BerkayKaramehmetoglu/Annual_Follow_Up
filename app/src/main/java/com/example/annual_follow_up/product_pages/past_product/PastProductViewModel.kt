package com.example.annual_follow_up.product_pages.past_product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.example.annual_follow_up.utils.Utils

class PastProductViewModel(private val context: Context) : ViewModel() {

    private val followUpDAO = FollowUpDAO()

    private val pastProducts = MutableLiveData<List<FollowUp>>()
    val pastProduct: LiveData<List<FollowUp>> = pastProducts

    fun pastProductLoad() {
        val dbHelper = DatabaseHelper(context)
        val productDb = followUpDAO.getProducts(
            dbHelper,
            "SELECT * FROM FollowUp ORDER BY product_id DESC LIMIT 5 OFFSET ${Utils.offsetValues}"
        )
        pastProducts.value = productDb
    }

    private val totalProducts = MutableLiveData<Int>().apply {
        val dbHelper = DatabaseHelper(context)
        val productDb = followUpDAO.totalProduct(dbHelper)

        value = productDb
    }
    val totalProduct: LiveData<Int> = totalProducts

}