package com.example.etherealartefacts.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.etherealartefacts.ui.theme.EtherealArtefactsTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.etherealartefacts.ui.login.LoginScreen
import com.example.etherealartefacts.ui.productsDetails.ProductsScreen
import com.example.etherealartefacts.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtherealArtefactsTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold {
                        NavHost(
                            navController = navController,
                            startDestination = "login",
                            modifier = Modifier.padding(it)
                        ) {
                            composable(route = "login") {
                                LoginScreen(navigateToDetailsScreen = {
                                    navController.navigate("/")
                                })
                            }
                            composable(route = "detailsScreen") {
                                ProductsScreen()
                            }
                            composable(route = "/") {
                                HomeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
