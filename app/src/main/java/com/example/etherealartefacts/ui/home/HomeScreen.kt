package com.example.etherealartefacts.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.etherealartefacts.ui.destinations.ProductsScreenDestination
import com.example.etherealartefacts.ui.shared.AppBar
import com.example.etherealartefacts.ui.shared.SearchField
import com.example.etherealartefacts.ui.theme.Black
import com.example.etherealartefacts.ui.theme.BorderGray
import com.example.etherealartefacts.ui.theme.GrayIcon
import com.example.etherealartefacts.ui.theme.GrayText
import com.example.etherealartefacts.ui.theme.PurplePrimary
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@RootNavGraph
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(destinationsNavigator: DestinationsNavigator) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val filterCriteria by homeViewModel.filterCriteria.collectAsState()
    val displayedProducts by homeViewModel.displayedProducts.collectAsState()
    val context = LocalContext.current
    val horPadding = dimensionResource(id = R.dimen.hor_padding)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val iconSizeSmall = dimensionResource(id = R.dimen.icon_size_small)
    val iconSizeMedium = dimensionResource(id = R.dimen.icon_size_medium)
    val borderWidth = dimensionResource(id = R.dimen.border_width)
    val backgroundImg = painterResource(id = R.drawable.background_pd)
    val errorOccurred by homeViewModel.errorOccurred.collectAsState()
    val errText = stringResource(id = R.string.error_fetching)
    var showedFetchErr by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val sheetCoroutineScope = rememberCoroutineScope()


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
            AppBar(
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
                navigationIcon = {}
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
                SearchField(filterCriteria = filterCriteria, onChange = { value ->
                    homeViewModel.onChange(value)
                }, clear = {
                    homeViewModel.clear()
                })
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
                            .clickable {
                                sheetCoroutineScope.launch {
                                    sheetState.show()
                                }
                            }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(displayedProducts) { product ->
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
                                    destinationsNavigator.navigate(ProductsScreenDestination(product.id))
                                }, horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = product.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.home_img_size))
                                    .width(dimensionResource(id = R.dimen.home_img_size))
                                    .padding(bottom = paddingMedium)
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
        if (sheetState.isVisible) {
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
                    val defaultRatings = 4
                    var range by remember { mutableStateOf(35f..150f) }
                    var ratingState by remember { mutableIntStateOf(defaultRatings) }

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
                                println("Clicked")
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
                            value = range,
                            onValueChange = {
                                range =
                                    it.start.roundToInt().toFloat()..it.endInclusive.roundToInt()
                                        .toFloat()
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
                                text = "${stringResource(id = R.string.price_sign)}${range.start}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${stringResource(id = R.string.price_sign)}${range.endInclusive}",
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
                                            println(index + 1)
                                            ratingState = index + 1
                                        },
                                    tint = if (index <= ratingState - 1) PurpleIcon else GrayIcon
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesAccordion() {
    val iconSizeMedium = dimensionResource(id = R.dimen.icon_size_medium)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("All Categories", "Books", "Gemstones", "Home", "Jewellery")

    val rotateState = animateFloatAsState(
        targetValue = if (expanded) 180F else 0F,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_large))
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Black,
                disabledContainerColor = BorderGray,
                disabledContentColor = BorderGray,
            ),
            shape = RectangleShape,
            onClick = { expanded = !expanded })        {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.category_subtitle),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = R.string.category_default_option),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Icon(
                    Icons.Default.ArrowDropDown, "",
                    modifier = Modifier
                        .rotate(rotateState.value)
                        .height(iconSizeMedium)
                        .width(iconSizeMedium),
                    tint = Black
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
        ) {
            // TODO your choice of what you want to have here
            Column(modifier = Modifier.fillMaxWidth()) {
                repeat(options.count()) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = options[index],
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = paddingMedium)
                        )
                        Checkbox(
                            checked = index == 0,
                            onCheckedChange = null,
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.Transparent,
                                checkmarkColor = PurplePrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
