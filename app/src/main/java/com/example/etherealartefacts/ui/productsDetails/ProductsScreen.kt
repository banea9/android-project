@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.etherealartefacts.ui.productsDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.models.ProductDetailsModel
import com.example.etherealartefacts.utils.showErrorNotification
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Black
import com.example.etherealartefacts.ui.theme.GreenIcon
import com.example.etherealartefacts.ui.theme.PurpleIcon
import com.example.etherealartefacts.ui.theme.PurplePrimary

@Composable
fun ProductsScreen() {
    val productsViewModel: ProductDetailsViewModel = hiltViewModel()
    val response by productsViewModel.response.collectAsState()
    val isLoading by productsViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    val productState = remember { mutableStateOf<ProductDetailsModel?>(null) }

    LaunchedEffect(Unit) {
        productsViewModel.getProductDetails(2)
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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                title = {
                    Text(
                        text = "Item",
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*To be implemented with homework 2-2 (home screen)*/ }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Black,
                    actionIconContentColor = Black,
                    navigationIconContentColor = Black,
                ), actions = {
                    Box(modifier = Modifier
                        .clickable {
                            /* To be implemented with cart functionality */
                        }
                        .padding(15.dp)) {

                        Icon(Icons.Outlined.ShoppingCart, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        productState.value?.let { product ->
            Box(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize()
            ) {
                Image(
                    painter = backgroundImg,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = rememberAsyncImagePainter(product.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(340.dp)
                        )
                        Box(modifier = Modifier.offset(x = (190).dp, y = (-330).dp)) {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .background(Color.White, RoundedCornerShape(16.dp)),
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircleOutline,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(horizontal = 13.dp)
                                        .height(18.dp)
                                        .width(18.dp),
                                    tint = GreenIcon,

                                    )
                                Text(
                                    text = "In stock",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 19.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = product.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )

                            Box {

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${product.rating}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                    )
                                    repeat(product.rating) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(11.dp)
                                                .width(11.dp),
                                            tint = PurpleIcon
                                        )
                                    }
                                    repeat(5 - product.rating) {
                                        Icon(
                                            Icons.Outlined.StarRate, contentDescription = null,
                                            modifier = Modifier
                                                .height(11.dp)
                                                .width(11.dp),
                                        )
                                    }
                                }
                            }
                        }
                        Text(text = "Category: ${product.category}", fontSize = 12.sp)
                        Text(
                            modifier = Modifier.padding(top = 23.dp, bottom = 19.dp),
                            text = product.description,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "$${product.price}.00",
                            modifier = Modifier.padding(bottom = 52.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { println("adding to cart") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PurplePrimary, contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(horizontal = 40.dp)
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp)
                                        .padding(horizontal = 5.dp)

                                )
                                Text(
                                    text = "Add to cart",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

