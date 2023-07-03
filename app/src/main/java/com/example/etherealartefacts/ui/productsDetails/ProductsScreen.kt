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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.CartState
import com.example.etherealartefacts.utils.showErrorNotification
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.CartItem
import com.example.etherealartefacts.ui.destinations.CartScreenDestination
import com.example.etherealartefacts.ui.shared.AppBar
import com.example.etherealartefacts.ui.theme.GreenIcon
import com.example.etherealartefacts.ui.theme.PurpleIcon
import com.example.etherealartefacts.ui.theme.PurplePrimary
import com.example.etherealartefacts.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductsScreen(productId: Int, destinationsNavigator: DestinationsNavigator) {
    val productsViewModel: ProductDetailsViewModel = hiltViewModel()
    val products by productsViewModel.products.collectAsState()
    val isLoading by productsViewModel.isLoading.collectAsState()
    var showedFetchErr by remember { mutableStateOf(false) }
    val errorOccurred by productsViewModel.errorOccurred.collectAsState()
    val context = LocalContext.current
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    val topAppBarPadding = dimensionResource(id = R.dimen.top_app_bar_hor_padding)
    val iconSize = dimensionResource(id = R.dimen.icon_size_small)
    val errText = stringResource(id = R.string.error_fetching)

    LaunchedEffect(Unit) {
        productsViewModel.getProductDetails(productId)
    }

    LaunchedEffect(errorOccurred) {
        if (errorOccurred == true && !showedFetchErr) {
            showedFetchErr = true
            showErrorNotification(context, errText)
        }
    }


    if (isLoading) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                modifier = Modifier.padding(start = topAppBarPadding, end = topAppBarPadding),
                title = {
                    Text(
                        text = stringResource(id = R.string.product_top_app_bar),
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }, actions = {
                    Box(modifier = Modifier
                        .clickable {
                            destinationsNavigator.navigate(CartScreenDestination())
                        }
                    ) {
                        val cartItemsCount = CartState.cartItems.size
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = null)
                        if(cartItemsCount != 0) {
                            Box(
                                modifier = Modifier.offset(
                                    dimensionResource(id = R.dimen.top_app_bar_hor_padding),
                                    -dimensionResource(id = R.dimen.text_border_radius)
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(dimensionResource(id = R.dimen.text_box_padding))
                                        .background(color = PurpleIcon, CircleShape)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Text(
                                        text = cartItemsCount.toString(),
                                        color = White,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { destinationsNavigator.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        products?.let { product ->
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
                        .padding(horizontal = dimensionResource(id = R.dimen.hor_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = rememberAsyncImagePainter(product.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(id = R.dimen.img_height))
                        )
                        Box(
                            modifier = Modifier.offset(
                                x = dimensionResource(id = R.dimen.note_x_offset),
                                y = dimensionResource(id = R.dimen.note_y_offset)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = dimensionResource(id = R.dimen.note_padding))
                                    .background(
                                        White,
                                        RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium))
                                    ),
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircleOutline,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(horizontal = dimensionResource(id = R.dimen.icon_padding))
                                        .height(dimensionResource(id = R.dimen.icon_size_small))
                                        .width(dimensionResource(id = R.dimen.icon_size_small)),
                                    tint = GreenIcon,

                                    )
                                Text(
                                    text = stringResource(id = R.string.product_in_stock),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.note_text_padding))
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = product.title,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )

                            Box {

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${product.rating}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )
                                    repeat(product.rating) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(iconSize)
                                                .width(iconSize),
                                            tint = PurpleIcon
                                        )
                                    }
                                    repeat(5 - product.rating) {
                                        Icon(
                                            Icons.Outlined.StarRate, contentDescription = null,
                                            modifier = Modifier
                                                .height(iconSize)
                                                .width(iconSize),
                                            tint = PurpleIcon
                                        )
                                    }
                                }
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${stringResource(id = R.string.product_top_app_bar)} ${product.category}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = dimensionResource(id = R.dimen.description_top_padding),
                                    bottom = dimensionResource(id = R.dimen.description_bottom_padding)
                                ),
                                text = product.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${stringResource(id = R.string.price_sign)} ${product.price}${
                                    stringResource(
                                        id = R.string.price_suffix
                                    )
                                }",
                                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.btn_top_padding)),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Button(
                                onClick = {
                                    val cartItem = CartItem(
                                        id = product.id,
                                        name = product.title,
                                        image = product.image,
                                        price = product.price,
                                        quantity = 1
                                    )
                                    CartState.addProduct(cartItem)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PurplePrimary, contentColor = White
                                ),
                                contentPadding = PaddingValues(topAppBarPadding)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .padding(horizontal = dimensionResource(id = R.dimen.hor_padding))
                                        .fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(dimensionResource(id = R.dimen.padding_large))
                                            .height(dimensionResource(id = R.dimen.padding_large))
                                            .padding(horizontal = dimensionResource(id = R.dimen.note_padding))

                                    )
                                    Text(
                                        text = stringResource(id = R.string.add_to_cart_btn),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.text_border_radius))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

