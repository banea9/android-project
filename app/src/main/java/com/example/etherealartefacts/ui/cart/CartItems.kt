package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.CartItem

@Composable
fun CartItems(item: CartItem) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val iconSizeSmall = dimensionResource(id = R.dimen.icon_size_small)
    val cartViewModel: CartViewModel = hiltViewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.cart_item_img))
                    .height(dimensionResource(id = R.dimen.cart_item_img))
            )
            Column(modifier = Modifier.padding(start = iconSizeSmall)) {
                Text(
                    text = "Solar neshto si",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(id = R.string.price_sign)}${item.price}${
                        stringResource(
                            id = R.string.price_suffix
                        )
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(id = R.string.cart_item_quantity)} ${item.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .width(paddingMedium)
                .height(paddingMedium)
                .clickable { cartViewModel.removeItem(item.name) }
        )
    }
    Divider()
}