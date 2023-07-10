package com.example.kateapp.createProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.kateapp.Screen
import com.example.kateapp.data.Product
import com.example.kateapp.data.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _price = MutableStateFlow(0)
    val price = _price.asStateFlow()

    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun onNameEdit(name: String) { _name.value = name }

    fun onPriceEdit(price: Int) { _price.value = price }

    fun onCountEdit(count: Int) { _count.value = count }

    fun onAdd(navHostController: NavHostController) {
        val addJob = viewModelScope.launch {
            _isLoading.value = true
            //delay(3000L)
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