package com.example.etherealartefacts.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.GrayIcon
import com.example.etherealartefacts.ui.theme.PurpleIcon

@Composable
fun RatingsFilter() {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)

    Column {
        Text(
            modifier = Modifier.padding(vertical = paddingMedium),
            text = stringResource(
                id = R.string.product_subtitle
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(5) { index ->
                Star(index)
            }
        }
    }
}

@Composable
fun Star(index: Int) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val displayedRating = homeViewModel.displayedRating.collectAsState()

    Icon(
        Icons.Default.Star,
        contentDescription = null,
        modifier = Modifier
            .height(horPadding)
            .width(horPadding)
            .clickable {
                homeViewModel.onRatingChange(index + 1)
            },
        tint = if (index <= (displayedRating.value - 1)) PurpleIcon else GrayIcon
    )
}