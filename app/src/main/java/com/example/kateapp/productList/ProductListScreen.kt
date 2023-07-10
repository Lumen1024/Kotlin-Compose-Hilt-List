package com.example.kateapp.productList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kateapp.data.Product

// Главный экран со всеми покупками, поиском и сортировкой
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductListViewModel, navHostController: NavHostController) {

    // Получаем переменные из viewModel
    val searchText by viewModel.searchText.collectAsState() // Текст в строке поиска
    val products by viewModel.products.collectAsState(emptyList()) // Список продуктов
    val menuPopup by viewModel.menuPopup.collectAsState() // Открыт ли pop up menu

    // Разметочный контейнер с основным полем content и дополнительным полями, в том числе
    // с парящей кнопкой (floating button)
    Scaffold(
        // Висячая кнопка
        floatingActionButton = { AddProductActionButton(viewModel, navHostController) }
    ) { paddingValues ->
        // Основной контент
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Строка поиска с кнопкой фильтра перед текстом
            SearchField(searchText, viewModel, menuPopup)
            // Список продуктов с динамическим отображением
            ProductList(paddingValues, products)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchField(
    searchText: String,
    viewModel: ProductListViewModel,
    menuPopup: Boolean
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = searchText,
        leadingIcon = {
            // Кнопка по которой вызывается дроп даун меню с фильтрами
            FilterButton(viewModel)
            // Само меню которое открывается по нажатию на кнопку выше
            FilterDropDownMenu(menuPopup, viewModel)
        },
        // Когда значение в строке поиска меняется вызывается эта функция
        onValueChange = { viewModel.onTextEdit(it) }
    )
}

@Composable
private fun ProductList(
    paddingValues: PaddingValues,
    products: List<Product>
) {
    // Список с динамически подгружаемыми продуктами
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // Превращаем список объектов в компонент отображающий продукты
        items(products) { product ->
            ProductListItem(product)
        }
    }
}

@Composable
private fun FilterButton(viewModel: ProductListViewModel) {
    // Кнопка по открывается меню
    IconButton(onClick = { viewModel.onPopupToggle() }) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Sorting",
        )
    }
}

@Composable
private fun FilterDropDownMenu(
    menuPopup: Boolean,
    viewModel: ProductListViewModel
) {
    // Меню с 4 фильтрами для списка
    DropdownMenu(
        expanded = menuPopup, // Открыт ли pop up
        onDismissRequest = { viewModel.onPopupToggle() }) // Вызывается при попытке закрытия pop up
    {
        FilterMenuItem(viewModel, "id")
        FilterMenuItem(viewModel, "name")
        FilterMenuItem(viewModel, "price")
        FilterMenuItem(viewModel, "count")
    }
}

@Composable
private fun FilterMenuItem(viewModel: ProductListViewModel, filter: String) {
    // Кликабельный текст по нажатию на который устанавливается фильтр и закрывается меню
    Text(
        text = filter.replaceFirstChar(Char::titlecase),
        modifier = Modifier
            .padding(10.dp)
            .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked(filter) })
}

@Composable
private fun AddProductActionButton(
    viewModel: ProductListViewModel,
    navHostController: NavHostController
) {
    // Парящая кнопка, при нажатии на которую происходит переход на экран создания продукта
    FloatingActionButton(onClick = {
        viewModel.onCreateNewProduct(navHostController)
    }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}