package com.example.annual_follow_up.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper
    (context, "FollowUp", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {

        try {
            db?.execSQL(
                "CREATE TABLE followUp (product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "product_date TEXT," +
                        "product_name TEXT," +
                        "product_amount INTEGER," +
                        "product_sales INTEGER," +
                        "product_expense INTEGER," +
                        "product_earning INTEGER);"
            )
        } catch (e: SQLiteException) {
            throw e
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            if (oldVersion < newVersion) {
                db?.execSQL("ALTER TABLE followUp ADD COLUMN product_earning INTEGER NOT NULL DEFAULT 0")
            } else {
                db?.execSQL("DROP TABLE IF EXISTS follow_up")
                onCreate(db)
            }
        } catch (e: SQLiteException) {
            throw e
        }
    }

}