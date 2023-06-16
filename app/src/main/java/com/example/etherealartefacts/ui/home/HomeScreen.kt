@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.etherealartefacts.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.PurpleIcon
import com.example.etherealartefacts.utils.showErrorNotification
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.etherealartefacts.ui.theme.Black
import com.example.etherealartefacts.ui.theme.BorderGray
import com.example.etherealartefacts.ui.theme.DefaultTextField
import com.example.etherealartefacts.ui.theme.GrayIcon
import com.example.etherealartefacts.ui.theme.GrayText
import com.example.etherealartefacts.ui.theme.SearchBoxBg

@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val products by homeViewModel.products.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val filterCriteria by homeViewModel.filterCriteria.collectAsState()
    val filteredProducts by homeViewModel.filteredProducts.collectAsState()
    val context = LocalContext.current
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val iconSizeSmall = dimensionResource(id = R.dimen.icon_size_small)
    val searchIcon = dimensionResource(id = R.dimen.search_icons_size)
    val borderWidth = dimensionResource(id = R.dimen.border_width)
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    val errorOccurred by homeViewModel.errorOccurred.collectAsState()
    val errText = stringResource(id = R.string.error_fetching)
    var showedFetchErr by remember { mutableStateOf(false) }

    LaunchedEffect(errorOccurred) {
        if (errorOccurred == true && !showedFetchErr) {
            showedFetchErr = true
            showErrorNotification(context, errText)
        }
    }

    if (isLoading) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(
                    start = horPadding, end = horPadding, bottom = dimensionResource(
                        id = R.dimen.btn_top_padding
                    )
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.home_page_title),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Black,
                    actionIconContentColor = Black,
                    navigationIconContentColor = Black,
                ), actions = {
                    Box(modifier = Modifier
                        .clickable {
                            /* To be implemented with cart functionality */
                        }
                        .padding(end = dimensionResource(id = R.dimen.login_padding_bottom))
                    ) {
                        Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.home_profile_icon))
                                .width(dimensionResource(id = R.dimen.home_profile_icon))
                        )
                    }
                    Box(modifier = Modifier
                        .clickable {
                            /* To be implemented with cart functionality */
                        }
                    ) {
                        Icon(
                            Icons.Outlined.ShoppingCart, contentDescription = null,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.home_cart_icon))
                                .width(dimensionResource(id = R.dimen.home_cart_icon))
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
        ) {
            Image(
                painter = backgroundImg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.description_top_padding)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SearchBoxBg,
                        unfocusedContainerColor = SearchBoxBg,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    value = filterCriteria,
                    onValueChange = { value ->
                        homeViewModel.onChange(value)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.top_app_bar_hor_padding))
                                .height(searchIcon)
                                .width(searchIcon)
                        )
                    },
                    trailingIcon = {
                        if (filterCriteria != "") {
                            IconButton(onClick = {
                                homeViewModel.clear()
                            })
                            {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(dimensionResource(id = R.dimen.top_app_bar_hor_padding))
                                        .height(searchIcon)
                                        .width(searchIcon)
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search_placeholder),
                            color = DefaultTextField
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.search_border_radius)),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = horPadding),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.home_subtitle),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        Modifier
                            .width(dimensionResource(id = R.dimen.filter_icon_height))
                            .height(dimensionResource(id = R.dimen.filter_icon_width))
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(if(filterCriteria.isNotEmpty()) filteredProducts else products) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(id = R.dimen.text_box_padding))
                                .drawBehind {
                                    drawLine(
                                        color = BorderGray,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = borderWidth.toPx()
                                    )
                                }
                                .clickable {
                                    navController.navigate("detailsScreen/${product.id}")
                                }, horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = product.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.home_img_size))
                                    .width(dimensionResource(id = R.dimen.home_img_size))
                                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = dimensionResource(id = R.dimen.text_box_padding))
                            ) {
                                Text(
                                    text = product.category,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = GrayText
                                )
                                Text(
                                    text = product.title,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                                Text(
                                    text = product.short_description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = GrayText
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${product.rating}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    )
                                    repeat(product.rating) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(iconSizeSmall)
                                                .width(iconSizeSmall),
                                            tint = PurpleIcon
                                        )
                                    }
                                    repeat(5 - product.rating) {
                                        Icon(
                                            Icons.Default.Star, contentDescription = null,
                                            modifier = Modifier
                                                .height(iconSizeSmall)
                                                .width(iconSizeSmall),
                                            tint = GrayIcon
                                        )
                                    }
                                }
                                Text(
                                    text = "${stringResource(id = R.string.price_sign)} ${product.price}${
                                        stringResource(
                                            id = R.string.price_suffix
                                        )
                                    }",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

