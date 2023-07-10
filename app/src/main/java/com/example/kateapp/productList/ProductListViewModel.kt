package com.example.kateapp.productList

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.kateapp.Screen
import com.example.kateapp.data.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {

    private val _menuPopup = MutableStateFlow(false)
    val menuPopup = _menuPopup.asStateFlow()

    private val _sortFilter = MutableStateFlow("price")
    val sortFilter = _sortFilter.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products = sortFilter
        .flatMapLatest { sortFilter ->
            when (sortFilter) {
                "id" -> dao.getProductsOrderById()
                "name" -> dao.getProductsOrderByName()
                "price" -> dao.getProductsOrderByPrice()
                "count" -> dao.getProductsOrderByCount()
                else -> dao.getProductsOrderById()
            }
        }

    val products = searchText
        .debounce(80L)
        .combine(_products) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                products.filter {
                    it.name.lowercase().contains(text.lowercase())
                }.sortedBy {
                    it.name.lowercase().indexOf(text.lowercase())
                }
            }
        }

    fun onPopupToggle() {
        _menuPopup.value = !_menuPopup.value
    }

    fun onFilterClicked(filter: String) {
        _sortFilter.value = filter
    }


    fun onTextEdit(text: String) {
        _searchText.value = text
    }

    fun onCreateNewProduct(navHostController: NavHostController) {
        navHostController.navigate(route = Screen.CreateProduct.route)
    }


}

