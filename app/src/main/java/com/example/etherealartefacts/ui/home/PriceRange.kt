package com.example.etherealartefacts.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.PurpleIcon

@Composable
fun PriceRange() {
    val range = 0f..200f
    val homeViewModel: HomeViewModel = hiltViewModel()
    val displayedRange = homeViewModel.displayedRange.collectAsState()
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(
                    id = R.dimen.padding_large
                )
            )
    ) {
        Text(
            modifier = Modifier.padding(vertical = paddingMedium),
            text = stringResource(
                id =
                R.string.price_subtitle
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        RangeSlider(
            value = displayedRange.value,
            onValueChange = { rangeValue ->
                homeViewModel.onRangeChange(rangeValue)
            },
            valueRange = range,
            colors = SliderDefaults.colors(
                activeTrackColor = PurpleIcon,
                thumbColor = PurpleIcon,
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = paddingMedium
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.price_sign)}${displayedRange.value.start}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${stringResource(id = R.string.price_sign)}${displayedRange.value.endInclusive}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}