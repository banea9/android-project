package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.CartState
import com.example.etherealartefacts.R

@Composable
fun CartSummary() {
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
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