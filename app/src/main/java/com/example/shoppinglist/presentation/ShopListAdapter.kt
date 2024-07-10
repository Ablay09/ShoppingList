package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

	var shopList = listOf<ShopItem>()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

		val view =
			LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
		return ShopItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
		val item = shopList[position]
		holder.bind(item)
	}

	override fun getItemCount(): Int = shopList.size

	override fun onViewRecycled(holder: ShopItemViewHolder) {
		super.onViewRecycled(holder)
		holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
		holder.tvName.text = ""
		holder.tvCount.text = ""
	}

	class ShopItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
		val tvName = view.findViewById<TextView>(R.id.tv_name)
		val tvCount = view.findViewById<TextView>(R.id.tv_count)

		fun bind(item: ShopItem) {
			val status = if (item.enabled) {
				"Active"
			} else {
				"Not active"
			}

			view.setOnLongClickListener {
				true
			}
			if (item.enabled) {
				tvName.text = "${item.name} $status"
				tvCount.text = item.count.toString()
				tvName.setTextColor(ContextCompat.getColor(view.context, android.R.color.holo_red_dark))
			}
		}
	}
}