package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel
	private lateinit var shopListAdapter: ShopListAdapter
	private lateinit var rvShopList: RecyclerView
	private lateinit var buttonAddItem: FloatingActionButton

	private var shopItemContainer: FragmentContainerView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		shopItemContainer = findViewById(R.id.shop_item_container)
		rvShopList = findViewById(R.id.rv_shop_list)
		buttonAddItem = findViewById(R.id.button_add_shop_item)
		shopListAdapter = ShopListAdapter()
		setupRecyclerView()
		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		viewModel.shopList.observe(this) {
			shopListAdapter.submitList(it)
		}
		buttonAddItem.setOnClickListener {
			if (isOnePaneMode()) {
				val intent = ShopItemActivity.newIntentAddItem(this)
				startActivity(intent)
			} else {
				launchFragment(ShopItemFragment.newInstanceAddItem())
			}
		}
	}

	private fun isOnePaneMode(): Boolean {
		return shopItemContainer == null
	}

	private fun launchFragment(fragment: Fragment) {
		supportFragmentManager.popBackStack()
		supportFragmentManager.beginTransaction()
			.replace(R.id.shop_item_container, fragment)
			.addToBackStack(null)
			.commit()
	}
	private fun setupRecyclerView() {
		with(rvShopList) {
			rvShopList.adapter = shopListAdapter
			recycledViewPool.setMaxRecycledViews(
				ShopListAdapter.VIEW_TYPE_ENABLED,
				ShopListAdapter.MAX_POOL_SIZE
			)
			recycledViewPool.setMaxRecycledViews(
				ShopListAdapter.VIEW_TYPE_DISABLED,
				ShopListAdapter.MAX_POOL_SIZE
			)
		}
		setupLongClickListener()
		setupClickListener()
		setupSwipeListener()
	}

	private fun setupClickListener() {
		shopListAdapter.onShopItemClickListener = { shopItem ->
			Log.d("MainActivity", "clicked item: ${shopItem.name}")
			if (isOnePaneMode()) {
				val intent = ShopItemActivity.newIntentEditItem(this, shopItem.id)
				startActivity(intent)
			} else {
				launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.id))
			}
		}
	}

	private fun setupLongClickListener() {
		shopListAdapter.onShopItemLongClickListener = { shopItem ->
			viewModel.changeEnableState(shopItem)
		}
	}

	private fun setupSwipeListener() {
		val itemTouchHelperCallback = object :
			ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return false
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val item = shopListAdapter.currentList[viewHolder.adapterPosition]
				viewModel.deleteShopItem(item)
			}
		}
		val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
		itemTouchHelper.attachToRecyclerView(rvShopList)
	}
}