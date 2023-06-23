package com.example.etherealartefacts.ui.cart

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.etherealartefacts.ui.shared.AppBar
import com.example.etherealartefacts.ui.shared.SearchField
import com.example.etherealartefacts.ui.theme.BorderGray
import com.example.etherealartefacts.ui.theme.GrayIcon
import com.example.etherealartefacts.ui.theme.GrayText
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CardScreen(navController: NavController) {
    val context = LocalContext.current
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val iconSizeSmall = dimensionResource(id = R.dimen.icon_size_small)
    val borderWidth = dimensionResource(id = R.dimen.border_width)
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    val logo = painterResource(id = R.drawable.logo)

    Scaffold(
        topBar = {
            AppBar(
                modifier = Modifier.padding(
                    start = horPadding, end = horPadding, bottom = dimensionResource(
                        id = R.dimen.btn_top_padding
                    )
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.cart_page_title),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
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
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ArrowBack, null)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = paddingMedium)
                        .drawBehind {
                            drawLine(
                                color = BorderGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = borderWidth.toPx()
                            )
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Image(
                            painter = logo,
                            contentDescription = "Logo",
                            modifier = Modifier
                                .width(56.dp)
                                .height(56.dp)
                        )
                        Column(modifier = Modifier.padding(start = iconSizeSmall)) {
                            Text(
                                text = "Solar neshto si",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${stringResource(id = R.string.price_sign)}20${
                                    stringResource(
                                        id = R.string.price_suffix
                                    )
                                }",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${stringResource(id = R.string.cart_item_quantity)} 1",
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
                            .clickable { println("Deleting") }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = paddingMedium)
                        .drawBehind {
                            drawLine(
                                color = BorderGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = borderWidth.toPx()
                            )
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Image(
                            painter = logo,
                            contentDescription = "Logo",
                            modifier = Modifier
                                .width(56.dp)
                                .height(56.dp)
                        )
                        Column(modifier = Modifier.padding(start = iconSizeSmall)) {
                            Text(
                                text = "Solar neshto si",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${stringResource(id = R.string.price_sign)}20${
                                    stringResource(
                                        id = R.string.price_suffix
                                    )
                                }",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${stringResource(id = R.string.cart_item_quantity)} 1",
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
                            .clickable { println("Deleting") }
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = paddingMedium).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(id = R.string.cart_items_count)} 4",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${stringResource(id = R.string.cart_items_total_price)}190${
                            stringResource(
                                id = R.string.price_suffix
                            )
                        }",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

