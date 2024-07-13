package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

	private var autoIncrementId = 0
	private val shopList = sortedSetOf(Comparator<ShopItem> { p0, p1 ->
		p0.id.compareTo(p1.id)
	})

	private val shopListLiveData = MutableLiveData<List<ShopItem>>()
	init {
		for (i in 0 until 10) {
			val item = ShopItem("Name $i", i, Random.nextBoolean())
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