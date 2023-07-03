package com.example.etherealartefacts.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.etherealartefacts.CartState
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.destinations.HomeScreenDestination
import com.example.etherealartefacts.ui.theme.PurplePrimary
import com.example.etherealartefacts.ui.theme.White
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun CartCheckout(destinationsNavigator: DestinationsNavigator) {
    Button(
        onClick = {
            CartState.placeOrder()
            destinationsNavigator.navigate(HomeScreenDestination)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = PurplePrimary, contentColor = White
        ),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.top_app_bar_hor_padding))

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