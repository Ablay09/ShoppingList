package com.example.shoppinglist.presentation

import android.app.Activity
import android.os.Bundle
import com.example.shoppinglist.R

class ShopItemActivity : Activity() {

	private lateinit var viewModel: ShopItemViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shop_item)
	}
}