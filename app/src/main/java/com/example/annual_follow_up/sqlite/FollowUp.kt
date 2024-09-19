package com.example.annual_follow_up.sqlite


data class FollowUp(
    var productId: Int,
    var productDate: String,
    var productName: String,
    var productAmount: Double,
    var productType: String,
    var productSales: Int,
    var productExpense: Int,
    var productEarning: Int,
    var productDesc: String,
) {
}