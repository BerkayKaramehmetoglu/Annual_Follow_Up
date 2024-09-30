package com.example.annual_follow_up.product_pages.home

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annual_follow_up.databinding.FragmentHomeBinding
import com.example.annual_follow_up.sqlite.DatabaseHelper
import com.example.annual_follow_up.sqlite.FollowUp
import com.example.annual_follow_up.sqlite.FollowUpDAO
import com.example.annual_follow_up.utils.Utils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = HomeViewModelFactory(requireContext())
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val writeDate: TextView = binding.currentDate
        homeViewModel.currentDate.observe(viewLifecycleOwner) {
            writeDate.text = it
        }

        homeViewModel.query.observe(viewLifecycleOwner) { productList ->
            val barEntries = arrayListOf<BarEntry>()
            val productNames = arrayListOf<String>()

            productList.forEachIndexed { index, product ->
                barEntries.add(BarEntry(index.toFloat(), product.productEarning.toFloat()))
                productNames.add(product.productName)
            }

            val barDataSet = BarDataSet(barEntries, null)
            barDataSet.valueTextSize = 15f
            barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
            barDataSet.formSize = 20f


            val barData = BarData(barDataSet)

            binding.barChart.animateY(1500)
            binding.barChart.setFitBars(true)
            binding.barChart.description = null
            binding.barChart.data = barData
            binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(productNames)
            binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE

            binding.barChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val y = e?.y

                    view?.let {
                        if (y != null) {
                            Utils.snackBar(it, "Total Earning so far " + y.toInt().toString())
                        }
                    }
                }

                override fun onNothingSelected() {

                }

            })

            binding.barChart.invalidate()
        }

        homeViewModel.homeProductLoad()

        return root
        //grafikte isimlerin grafiğinb üstünde görünmesibi sağa
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}