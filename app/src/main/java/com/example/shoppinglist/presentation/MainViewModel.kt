package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

	private val repository = ShopListRepositoryImpl(application)

	private val getShopListUseCase = GetShopListUseCase(repository)
	private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
	private val editShopItemUseCase = EditShopItemUseCase(repository)

	private val coroutineScope = CoroutineScope(Dispatchers.IO)

	var shopList = getShopListUseCase.getShopList()

	fun deleteShopItem(shopItem: ShopItem) {
		coroutineScope.launch {
			deleteShopItemUseCase.deleteShopItem(shopItem)
		}
	}

	fun changeEnableState(shopItem: ShopItem) {
		coroutineScope.launch {
			val newItem = shopItem.copy(enabled = !shopItem.enabled)
			editShopItemUseCase.editShopItem(newItem)
		}
	}

	override fun onCleared() {
		super.onCleared()
		coroutineScope.cancel()
	}
}