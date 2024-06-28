package com.example.annual_follow_up

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.annual_follow_up.databinding.ActivityMainBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUpDAO

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_past_product, R.id.navigation_dashboard, R.id.navigation_entry_product
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val vt = DatabaseHelper(this)

        val deneme = FollowUpDAO().allSelectProducts(vt)

        for (k in deneme){
            Log.e("Product İd",(k.productId.toString()))
            Log.e("Product Date",(k.productDate))
            Log.e("Product Name",(k.productName))
            Log.e("Product Amount",(k.productAmount.toString()))
            Log.e("Product Sales",(k.productSales.toString()))
            Log.e("Product Expense",(k.productExpense.toString()))
            Log.e("Product Earning",(k.productEarning.toString()))
        }

    }
}