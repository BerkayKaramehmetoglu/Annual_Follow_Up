package com.example.annual_follow_up.sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class FollowUpDAO {


    companion object {
        var count: Int = 0
    }

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
            val values = ContentValues(6).apply {
                put("product_date", getDate)
                put("product_name", productName.uppercase(Locale.getDefault()))
                put("product_amount", productAmount)
                put("product_sales", productSales)
                put("product_expense", productExpense)
                put("product_earning", productSales.minus(productExpense))
            }
            it.insertOrThrow("followUp", null, values)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun allSelectProducts(vt: DatabaseHelper): List<FollowUp> {
        val followUpList = ArrayList<FollowUp>()
        val db = vt.readableDatabase

        db.use { database ->
            val cursor = database.rawQuery("SELECT * FROM FollowUp ORDER BY product_id DESC", null)

            cursor.use { cur ->
                while (cur.moveToNext()) {
                    val followUp = FollowUp(
                        cur.getInt(cur.getColumnIndexOrThrow("product_id")),
                        cur.getString(cur.getColumnIndexOrThrow("product_date")),
                        cur.getString(cur.getColumnIndexOrThrow("product_name")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_amount")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_sales")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_expense")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_earning"))
                    )
                    followUpList.add(followUp)
                }
            }
        }
        return followUpList
    }

    fun totalProduct(vt: DatabaseHelper): Int {

        val db = vt.readableDatabase

        db.use { database ->
            val cursor = database.rawQuery("SELECT COUNT(*) FROM FollowUp", null)

            cursor.use {
                if (it.moveToFirst()) {
                    count = it.getInt(0)
                }
            }
        }
        return count
    }

    fun deleteTable(vt: DatabaseHelper, tableName: String) {
        val db = vt.writableDatabase
        try {
            db.execSQL("DELETE FROM $tableName")
            db.execSQL("VACUUM")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
}