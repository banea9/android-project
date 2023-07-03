package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.etherealartefacts.ui.shared.AppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.etherealartefacts.CartState

@Destination
@Composable
fun CartScreen(destinationsNavigator: DestinationsNavigator) {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    Scaffold(
        topBar = {
            AppBar(
                modifier = Modifier.padding(
                    start = horPadding, end = horPadding, bottom = dimensionResource(
                        id = R.dimen.btn_top_padding
                    )
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.cart_page_title),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { paddingValues ->
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
                    .padding(horizontal = horPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CartState.cartItems.map { item ->
                        CartItems(item)
                    }
                    if (CartState.cartItems.isNotEmpty()) {
                        CartSummary()
                    }

                }
                if (CartState.cartItems.isNotEmpty()) {
                    CartCheckout(destinationsNavigator)
                }
            }

            if (CartState.cartItems.isEmpty()) {
                EmptyCart()
            }
        }
    }
}

