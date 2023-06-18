package com.example.etherealartefacts.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.etherealartefacts.R
import com.example.etherealartefacts.ui.theme.DefaultTextField
import com.example.etherealartefacts.ui.theme.SearchBoxBg

@Composable
fun SearchField(
    filterCriteria: String,
    onChange: (String) -> Unit,
    clear: () -> Unit,
) {
    val searchIcon = dimensionResource(id = R.dimen.search_icons_size)

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
        onValueChange = onChange,
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
                IconButton(onClick = clear)
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
}