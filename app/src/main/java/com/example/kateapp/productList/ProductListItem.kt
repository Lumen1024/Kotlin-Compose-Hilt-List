package com.example.kateapp.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kateapp.data.Product

// Разметка для одного элемента Product в списке
@Composable
fun ProductListItem(product: Product) {
    Card(modifier = Modifier
        .fillMaxWidth(),
    ) {
        Row(Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = product.id.toString())
            Text(text = product.name)
            Text(text = product.price.toString() + "$")
            Text(text = product.count.toString())
        }
    }

}