package com.example.etherealartefacts.ui.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.etherealartefacts.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Black,
            actionIconContentColor = Black,
            navigationIconContentColor = Black,
        ),
        actions = actions,
        navigationIcon = navigationIcon
    )
}