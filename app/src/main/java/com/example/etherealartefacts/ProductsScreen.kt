package com.example.etherealartefacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.models.ProductDetailsModel
import com.example.etherealartefacts.utils.showErrorNotification

@Composable
fun ProductsScreen() {
    val productsViewModel: ProductDetailsViewModel = hiltViewModel()
    val response by productsViewModel.response.collectAsState()
    val isLoading by productsViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val productState = remember { mutableStateOf<ProductDetailsModel?>(null) }

    LaunchedEffect(Unit) {
        productsViewModel.getProductDetails(8)
    }

    LaunchedEffect(response) {
        response?.let { result ->
            result.onSuccess { product: ProductDetailsModel ->
                productState.value = product
            }
            result.onFailure {
                showErrorNotification(context, "Error while fetching data")
            }
        }
    }

    if (isLoading) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }

    productState.value?.let { product ->
        Column {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(1f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(text = "Category: ${product.category}")
                    Text(
                        text = product.title,
                        fontWeight = FontWeight.W700
                    )
                    Text(
                        text = product.short_description,
                    )
                    Row {
                        Text(text = "${product.rating}")
                        repeat(product.rating) {
                            Icon(Icons.Default.Star, contentDescription = null)
                        }
                        repeat(5 - product.rating) {
                            Icon(Icons.Outlined.StarRate, contentDescription = null)
                        }
                    }
                    Text(text = "${product.price}.00 $")
                }
            }
        }
    }
}

