@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.etherealartefacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.ui.theme.EtherealArtefactsTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtherealArtefactsTheme {
//                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Scaffold() {
//                        NavHost(
//                            navController = navController,
//                            startDestination = "home",
//                            modifier = Modifier.padding(it)
//                        ) {
//                            composable(route = "home") {
//
//                            }
//                        }
//                    }
                    ProductsScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var textField by remember { mutableStateOf("") }
    Column {
        TextField(value = textField, onValueChange = { newValue ->
            textField = newValue
        })
    }
}

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
//            Icon(Icons.Default.Star, contentDescription = null)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(text = "Category: Photography")
                Text(
                    text = "Nikon 1234",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700)
                )
                Text(
                    text = "Very cool tool for photography",
                )
                Row() {
                    Text(text = "4")
                    Icon(Icons.Default.Star, contentDescription = null)
                    Icon(Icons.Default.Star, contentDescription = null)
                    Icon(Icons.Default.Star, contentDescription = null)
                    Icon(Icons.Default.Star, contentDescription = null)
                    Icon(Icons.Outlined.Star, contentDescription = null) //not working
                }
                Text(text = "$90.00")
            }
        }
    }
}