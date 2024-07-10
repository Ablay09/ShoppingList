package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel
	private lateinit var shopListLinearLayout: LinearLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		viewModel.shopList.observe(this) {
			showList(it)
		}
		shopListLinearLayout = findViewById(R.id.ll_shop_list)
	}

	private fun showList(list: List<ShopItem>) {
		shopListLinearLayout.removeAllViews()
		list.forEach { item ->
			val layoutId = if (item.enabled) {
				R.layout.item_shop_enabled
			} else {
				R.layout.item_shop_disabled
			}
			val view = LayoutInflater.from(this).inflate(layoutId, shopListLinearLayout, false)
			val tvName = view.findViewById<TextView>(R.id.tv_name)
			val tvCount = view.findViewById<TextView>(R.id.tv_count)
			tvName.text = item.name
			tvCount.text = item.count.toString()
			view.setOnLongClickListener {
				viewModel.changeEnableState(item)
				true
			}
			shopListLinearLayout.addView(view)
		}
	}
}