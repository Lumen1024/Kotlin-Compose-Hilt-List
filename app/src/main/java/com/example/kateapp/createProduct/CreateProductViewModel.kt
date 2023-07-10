package com.example.kateapp.createProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.kateapp.Screen
import com.example.kateapp.data.Product
import com.example.kateapp.data.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {
    // Идёт ли добавление в базу
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Имя продукта
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    // Цена продукта
    private val _price = MutableStateFlow(0)
    val price = _price.asStateFlow()

    // Количество продукта
    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun onNameEdit(name: String) { _name.value = name }

    fun onPriceEdit(price: Int) { _price.value = price }

    fun onCountEdit(count: Int) { _count.value = count }

    // onAdd вызывается при добавлении продукта и запускает в потоке viewModel
    // добавление в базу данных и устанавливается состояние загрузки.
    // После этого запускается ожидание добавления в потоке viewModel
    // и переход на главный экран после того как продукт добавлен в базу
    fun onAdd(navHostController: NavHostController) {
        val addJob = viewModelScope.launch {
            _isLoading.value = true
            delay(300L)
            dao.insertProduct(Product(
                name = name.value,
                price = price.value,
                count = count.value
            ))
        }

        viewModelScope.launch {
            addJob.join()
            _isLoading.value = false
            navHostController.navigate(Screen.ProductList.route)
        }
    }
}