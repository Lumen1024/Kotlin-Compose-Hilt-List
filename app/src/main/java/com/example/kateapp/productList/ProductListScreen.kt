package com.example.kateapp.productList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductListViewModel, navHostController: NavHostController) {

    val searchText by viewModel.searchText.collectAsState()
    val products by viewModel.products.collectAsState(emptyList())

    val menuPopup by viewModel.menuPopup.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onCreateNewProduct(navHostController)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchText,
                leadingIcon = {
                    IconButton(onClick = { viewModel.onPopupToggle() }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Sorting",
                        )
                    }
                    DropdownMenu(
                        expanded = menuPopup,
                        onDismissRequest = { viewModel.onPopupToggle() }) {
                        Text(
                            text = "Id",
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked("id") })
                        Text(
                            text = "Name",
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked("name") })
                        Text(
                            text = "Price",
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked("price") })
                        Text(
                            text = "Count",
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked("count") })
                    }
                },
                onValueChange = {
                    viewModel.onTextEdit(it)
                }
            )
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)

            ) {
                items(products) { product ->
                    ProductListItem(product)
                }
            }
        }
    }
}