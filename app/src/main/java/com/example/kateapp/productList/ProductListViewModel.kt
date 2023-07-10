package com.example.kateapp.productList

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.kateapp.Screen
import com.example.kateapp.data.Product
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

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {

    // Открыто ли pop up menu
    private val _menuPopup = MutableStateFlow(false)
    val menuPopup = _menuPopup.asStateFlow()

    // Текущий фильтр
    private val _sortFilter = MutableStateFlow("id")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Списк продуктов получаемый из базы данных в зависимости от значения _sortFilter, который
    // обновляется каждый раз при изменении _sortFilter
    private val _products = _sortFilter
        .flatMapLatest { sortFilter ->
            when (sortFilter) { // Свитч по колонкам базы
                Product::id.name -> dao.getProductsOrderById()
                Product::name.name -> dao.getProductsOrderByName()
                Product::price.name -> dao.getProductsOrderByPrice()
                Product::price.name -> dao.getProductsOrderByCount()
                else -> dao.getProductsOrderById()
            }
        }

    // Список отфильтрованный по колонкам фильтруется также по поисковой строке
    // каждый раз при изменении searchText вызывается лямбда в которой происходит фильтрация
    // по заданному searchText
    val products = searchText
        .debounce(80L) // Задержка обработки ввода в поле поиска
        .combine(_products) { text, products ->
            if (text.isBlank()) { // Если в поле поиска пусто то возвращаем products
                products
            } else { // Если нет то убираем продукты не содержащие в имени строку поиска а затем
                     // сортируем по наилучшему совпадению
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

    fun onSearchTextEdit(text: String) {
        _searchText.value = text
    }

    // Когда нажата плавающая кнопка на создание нового продукта
    fun onCreateNewProduct(navHostController: NavHostController) {
        navHostController.navigate(route = Screen.CreateProduct.route)
    }


}

