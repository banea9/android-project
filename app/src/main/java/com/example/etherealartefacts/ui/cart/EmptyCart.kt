package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.PurpleIcon

@Composable
fun EmptyCart() {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)

    Column(
        modifier = androidx.compose.ui.Modifier
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