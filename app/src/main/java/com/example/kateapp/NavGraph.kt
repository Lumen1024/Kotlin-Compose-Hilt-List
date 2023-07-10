package com.example.kateapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kateapp.productList.ProductListScreen
import com.example.kateapp.productList.ProductListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kateapp.createProduct.CreateProductScreen
import com.example.kateapp.createProduct.CreateProductViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        composable(route = Screen.ProductList.route) {
            val viewModel = hiltViewModel<ProductListViewModel>()
            ProductListScreen(viewModel, navController)
        }
        composable(route = Screen.CreateProduct.route) {
            val viewModel = hiltViewModel<CreateProductViewModel>()
            CreateProductScreen(viewModel, navController)
        }

    }
}