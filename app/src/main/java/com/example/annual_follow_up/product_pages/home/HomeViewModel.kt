package com.example.annual_follow_up.product_pages.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.example.annual_follow_up.utils.Utils

class HomeViewModel(private val context: Context) : ViewModel() {

    private val followUpDAO = FollowUpDAO()

    private val currentDates = MutableLiveData<String>().apply {
        value = "Current Date: ".plus(Utils.getCurrentDate())
    }
    val currentDate: LiveData<String> = currentDates

    private val homeProducts = MutableLiveData<List<FollowUp>>()
    val query: LiveData<List<FollowUp>> = homeProducts

    fun homeProductLoad() {
        val dbHelper = DatabaseHelper(context)
        val query = followUpDAO.getGroupProducts(
            dbHelper,
            "SELECT product_name, SUM(product_earning) as total_earning FROM FollowUp GROUP BY product_name"
        )
        homeProducts.value = query
    }
}