package com.example.kateapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kateapp.createProduct.CreateProductScreen
import com.example.kateapp.createProduct.CreateProductViewModel
import com.example.kateapp.productList.ProductListScreen
import com.example.kateapp.productList.ProductListViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    // NavHost Компонент, который содержит все экраны и переключает их
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        // Описываем экраны с помощью composable

        // Передаем название экрана в параметр
        composable(route = Screen.ProductList.route) {
            // Создаем viemodel с помощью hilt
            val viewModel = hiltViewModel<ProductListViewModel>()
            ProductListScreen(viewModel, navController)
        }

        composable(route = Screen.CreateProduct.route) {
            val viewModel = hiltViewModel<CreateProductViewModel>()
            CreateProductScreen(viewModel, navController)
        }

    }
}