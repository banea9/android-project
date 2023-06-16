package com.example.etherealartefacts.ui.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: @Composable () -> Unit, actions: @Composable RowScope.() -> Unit = {}
) {
    val horPadding = dimensionResource(id = R.dimen.hor_padding)

    CenterAlignedTopAppBar(
        modifier = Modifier.padding(
            start = horPadding, end = horPadding, bottom = dimensionResource(
                id = R.dimen.btn_top_padding
            )
        ),
        title = title,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Black,
            actionIconContentColor = Black,
            navigationIconContentColor = Black,
        ),
        actions = actions
    )
}