package com.example.kateapp.createProduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kateapp.data.Product

@Composable
fun CreateProductScreen(
    viewModel: CreateProductViewModel,
    navHostController: NavHostController
) {
    // Поля для заполнения
    val name by viewModel.name.collectAsState()
    val price by viewModel.price.collectAsState()
    val count by viewModel.count.collectAsState()

    // Поле для показа либо кнопки либо индикации загрузки
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // Колонка с полями продукта
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProductField(
                name.trim(),
                Product::name.name
            ) {
                viewModel.onNameEdit(it)
            }
            ProductField(
                price.toString(),
                Product::price.name,
                KeyboardOptions(keyboardType = KeyboardType.Number),
            ) {
                viewModel.onPriceEdit(it.toIntOrNull() ?: 0)
            }
            ProductField(
                count.toString(),
                Product::count.name,
                KeyboardOptions(keyboardType = KeyboardType.Number),
            ) {
                viewModel.onCountEdit(it.toIntOrNull() ?: 0)
            }
        }
        // Кнопка при нажатии которой происходит добавление продукта и переход
        // на главный экран
        // Если добавление в процессе то кнопка блокируется и вместо текста появляется
        // индикатор загрузки
        Button(
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { viewModel.onAdd(navHostController) },
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(text = "Add")
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductField(
    value: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit
) {
    TextField(
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        label = { Text(text = label) },
        value = value, onValueChange = onValueChange
    )
}