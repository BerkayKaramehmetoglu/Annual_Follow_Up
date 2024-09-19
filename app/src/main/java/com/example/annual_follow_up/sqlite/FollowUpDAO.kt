package com.example.annual_follow_up.sqlite


import android.content.ContentValues
import com.example.annual_follow_up.utils.Utils
import com.github.mikephil.charting.data.BarEntry
import java.util.Locale

class FollowUpDAO {

    fun insertProducts(
        vt: DatabaseHelper,
        productName: String,
        productAmount: Double,
        productType: String,
        productSales: Int,
        productExpense: Int,
        productDesc: String,
    ) {

        val db = vt.writableDatabase

        db.use {
            val getDate = Utils.getCurrentDate()
            val values = ContentValues(6).apply {
                put("product_date", getDate)
                put("product_name", productName.uppercase(Locale.getDefault()).trim())
                put("product_amount", productAmount)
                put("product_type", productType)
                put("product_sales", productSales)
                put("product_expense", productExpense)
                put("product_earning", productSales.minus(productExpense))
                put("product_desc", productDesc.trim())
            }
            it.insertOrThrow("followUp", null, values)
        }
    }

    fun getProducts(vt: DatabaseHelper, query: String): List<FollowUp> {
        val followUpList = ArrayList<FollowUp>()
        val db = vt.readableDatabase

        db.use { database ->
            val cursor = database.rawQuery(
                query,
                null
            )

            cursor.use { cur ->
                while (cur.moveToNext()) {
                    val followUp = FollowUp(
                        cur.getInt(cur.getColumnIndexOrThrow("product_id")),
                        cur.getString(cur.getColumnIndexOrThrow("product_date")),
                        cur.getString(cur.getColumnIndexOrThrow("product_name")),
                        cur.getDouble(cur.getColumnIndexOrThrow("product_amount")),
                        cur.getString(cur.getColumnIndexOrThrow("product_type")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_sales")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_expense")),
                        cur.getInt(cur.getColumnIndexOrThrow("product_earning")),
                        cur.getString(cur.getColumnIndexOrThrow("product_desc"))
                    )
                    followUpList.add(followUp)
                }
            }
        }
        return followUpList
    }

    fun totalProduct(vt: DatabaseHelper): Int {
        var count = 0
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

    fun getGroupProducts(vt: DatabaseHelper, query: String): List<FollowUp> {
        val groupList = ArrayList<FollowUp>()
        val db = vt.readableDatabase

        db.use { database ->
            val cursor = database.rawQuery(query, null)

            cursor.use { cur ->
                while (cur.moveToNext()) {
                    val groups = FollowUp(
                        productId = 0,
                        productDate = "",
                        productName = cur.getString(cur.getColumnIndexOrThrow("product_name")),
                        productAmount = 0.0,
                        productType = "",
                        productSales = 0,
                        productExpense = 0,
                        productEarning = cur.getInt(cur.getColumnIndexOrThrow("total_earning")),
                        productDesc = ""
                    )
                    groupList.add(groups)
                }
            }
        }
        return groupList
    }

}