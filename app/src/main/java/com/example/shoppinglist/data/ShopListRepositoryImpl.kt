package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

	private val shopList = mutableListOf<ShopItem>()
	private var autoIncrementId = 0

	private val shopListLiveData = MutableLiveData<List<ShopItem>>()
	init {
		for (i in 0 until 10) {
			val item = ShopItem("Name $i", i, true)
			addShopItem(item)
		}
	}

	override fun addShopItem(shopItem: ShopItem) {
		if (shopItem.id == UNDEFINED_ID) {
			shopItem.id = autoIncrementId++
		}
		shopList.add(shopItem)
		updateList()
	}

	override fun editShopItem(shopItem: ShopItem) {
		val oldElement = getShopItem(shopItem.id)
		shopList.remove(oldElement)
		shopList.add(shopItem)
		updateList()
	}

	override fun deleteShopItem(shopItem: ShopItem) {
		shopList.remove(shopItem)
		updateList()
	}

	override fun getShopItem(id: Int): ShopItem {
		return shopList.find { it.id == id }
			?: throw RuntimeException("Element with id: $id not found")
	}

	override fun getShopList(): LiveData<List<ShopItem>> {
		return shopListLiveData
	}

	private fun updateList() {
		shopListLiveData.value = shopList.toList()
	}
}