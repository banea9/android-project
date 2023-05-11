package com.example.etherealartefacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductsScreen() {
    val img = painterResource(R.drawable.img)
    Column {
        Row {
            Image(
                painter = img,
                contentDescription = "Product Image",
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(text = "Category: Photography")
                Text(
                    text = "Nikon 1234",
                    fontWeight = FontWeight.W700
                )
                Text(
                    text = "Very cool tool for photography",
                )
                Row {
                    Text(text = "4")
                    repeat(4) {
                        Icon(Icons.Default.Star, contentDescription = null)
                    }
                    Icon(Icons.Outlined.StarRate, contentDescription = null)
                }
                Text(text = "$90.00")
            }
        }
    }
}