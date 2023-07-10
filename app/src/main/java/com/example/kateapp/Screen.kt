package com.example.kateapp

// Класс константных названий экранов для навигации (используется в NavGraph)
sealed class Screen(val route: String) {
    object ProductList : Screen(route = "product_list")
    object CreateProduct : Screen(route = "create_product")
}