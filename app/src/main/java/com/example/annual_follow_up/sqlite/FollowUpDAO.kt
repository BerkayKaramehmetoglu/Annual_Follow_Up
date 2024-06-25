package com.example.annual_follow_up.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class FollowUpDAO {


    fun insertProducts(
        vt: DatabaseHelper,
        productName: String,
        productAmount: Int,
        productSales: Int,
        productExpense: Int
    ) {

        val db = vt.writableDatabase

        db.use {
            val getDate = getCurrentDate()
            val values = ContentValues(4).apply {
                put("product_date", getDate)
                put("product_name", productName)
                put("product_amount", productAmount)
                put("product_sales", productSales)
                put("product_expense", productExpense)
            }
            it.insertOrThrow("followUp", null, values)
        }

    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun allSelectProducts(vt: DatabaseHelper): ArrayList<FollowUp> {
        val followUpList = ArrayList<FollowUp>()
        val db = vt.readableDatabase

        db.use { database ->
            val cursor = database.rawQuery("SELECT * FROM FollowUp LIMIT 2", null)

            cursor.use { cur ->
                while (cur.moveToNext()) {
                    val followUp = FollowUp(
                        cur.getInt(cur.getColumnIndexOrThrow("product_id")),
                        cur.getString(cur.getColumnIndexOrThrow("product_date")),
                        cur.getString(cur.getColumnIndexOrThrow("product_name")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_amount")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_sales")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_expense"))
                    )
                    followUpList.add(followUp)
                }
            }
        }
        return followUpList
    }
}