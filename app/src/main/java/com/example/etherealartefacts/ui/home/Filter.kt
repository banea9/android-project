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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.PurpleIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFilter(sheetState: SheetState, sheetCoroutineScope: CoroutineScope) {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val homeViewModel: HomeViewModel = hiltViewModel()
    val iconSizeMedium = dimensionResource(id = R.dimen.icon_size_medium)


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
            Divider()
            PriceRange()
            Divider()
            RatingsFilter()
        }
    }
}