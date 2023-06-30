package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.etherealartefacts.ui.destinations.HomeScreenDestination
import com.example.etherealartefacts.ui.shared.AppBar
import com.example.etherealartefacts.ui.theme.PurplePrimary
import com.example.etherealartefacts.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.ui.text.style.TextAlign
import com.example.etherealartefacts.CartState
import com.example.etherealartefacts.ui.theme.PurpleIcon

@Destination
@Composable
fun CardScreen(destinationsNavigator: DestinationsNavigator) {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val topAppBarPadding = dimensionResource(id = R.dimen.top_app_bar_hor_padding)
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
                        Row(
                            modifier = Modifier
                                .padding(vertical = paddingMedium)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${stringResource(id = R.string.cart_items_count)} ${CartState.cartItems.count()}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${stringResource(id = R.string.cart_items_total_price)}${CartState.getTotalPrice()}${
                                    stringResource(
                                        id = R.string.price_suffix
                                    )
                                }",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }
                if (CartState.cartItems.isNotEmpty()) {
                    Button(
                        onClick = {
                            CartState.placeOrder()
                            destinationsNavigator.navigate(HomeScreenDestination)
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
                            Text(
                                text = stringResource(id = R.string.check_out_btn),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.text_border_radius))
                            )
                        }
                    }
                }
            }

            if (CartState.cartItems.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Outlined.AddShoppingCart, contentDescription = null,
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.home_img_size))
                            .width(dimensionResource(id = R.dimen.home_img_size)),
                        tint = PurpleIcon
                    )
                    Text(
                        modifier = Modifier.padding(vertical = horPadding),
                        text = stringResource(id = R.string.empty_cart),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

