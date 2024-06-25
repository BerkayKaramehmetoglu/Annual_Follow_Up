package com.example.annual_follow_up.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper
    (context, "FollowUp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        try {
            db?.execSQL(
                "CREATE TABLE followUp (product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "product_date TEXT NOT NULL,"+
                        "product_name TEXT NOT NULL," +
                        "product_amount INTEGER NOT NULL," +
                        "product_sales INTEGER NOT NULL," +
                        "product_expense INTEGER NOT NULL );"
            )
        } catch (e: SQLiteException) {
            throw e
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            db?.execSQL("DROP TABLE IF EXISTS follow_up")
            onCreate(db)
        } catch (e: SQLiteException) {
            throw e
        }

    }

}