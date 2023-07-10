package com.example.kateapp

sealed class Screen(val route: String) {
    object ProductList : Screen(route = "product_list")
    object CreateProduct : Screen(route = "create_product")
}