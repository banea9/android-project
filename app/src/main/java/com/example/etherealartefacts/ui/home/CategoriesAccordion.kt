package com.example.etherealartefacts.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.Black
import com.example.etherealartefacts.ui.theme.BorderGray
import com.example.etherealartefacts.ui.theme.PurplePrimary

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
            onClick = { expanded = !expanded }) {
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
                            onCheckedChange = {
                                println("IT: $it")
                            },
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