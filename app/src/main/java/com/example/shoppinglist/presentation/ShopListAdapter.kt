package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

	val list = listOf<ShopItem>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
		return ShopItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
		val item = list[position]
		holder.bind(item)

	}

	override fun getItemCount(): Int = list.size

	class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val tvName = view.findViewById<TextView>(R.id.tv_name)
		private val tvCount = view.findViewById<TextView>(R.id.tv_count)

		fun bind(item: ShopItem) {
			tvName.text = item.name
			tvCount.text = item.count.toString()
		}
	}
}