package com.example.etherealartefacts.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.BorderGray
import com.example.etherealartefacts.ui.theme.GrayIcon
import com.example.etherealartefacts.ui.theme.PurpleIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFilter(sheetState: SheetState, sheetCoroutineScope: CoroutineScope) {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val iconSizeMedium = dimensionResource(id = R.dimen.icon_size_medium)
    val homeViewModel: HomeViewModel = hiltViewModel()
    val displayedRating = homeViewModel.displayedRating.collectAsState()
    val displayedRange = homeViewModel.displayedRange.collectAsState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = {
            sheetCoroutineScope.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horPadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        Icons.Default.Close, "",
                        modifier = Modifier
                            .height(iconSizeMedium)
                            .width(iconSizeMedium)
                            .padding(end = paddingMedium)
                            .clickable {
                                homeViewModel.resetFilter()
                                sheetCoroutineScope.launch {
                                    sheetState.hide()
                                }
                            },
                    )
                    Text(text = stringResource(id = R.string.filters_title))
                }
                Text(
                    text = stringResource(id = R.string.save_btn),
                    color = PurpleIcon,
                    modifier = Modifier.clickable {
                        sheetCoroutineScope.launch {
                            sheetState.hide()
                        }
                        homeViewModel.filterProducts()
                    })

            }

            CategoriesAccordion()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(
                            id = R.dimen.padding_large
                        )
                    )
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val startY = 0f
                        val endY = size.height

                        drawLine(
                            color = BorderGray,
                            start = Offset(0f, startY),
                            end = Offset(size.width, startY),
                            strokeWidth = strokeWidth
                        )
                        drawLine(
                            color = BorderGray,
                            start = Offset(0f, endY),
                            end = Offset(size.width, endY),
                            strokeWidth = strokeWidth
                        )
                    }
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
                    valueRange = 0f..200f,
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
                }
            }
        }
    }
}