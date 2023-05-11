@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.etherealartefacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.etherealartefacts.ui.theme.EtherealArtefactsTheme
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
                                    navController.navigate(
                                        "detailsScreen"
                                    )
                                })
                            }
                            composable(route = "detailsScreen") {
                                ProductsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navigateToDetailsScreen: () -> Unit = {}) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val logo = painterResource(id = R.drawable.logo)
    val userEmail = "test@gmail.com"
    val userPassword = "Start123!"

    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var err by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .width(200.dp)
                .aspectRatio(1f)
        )

        Column {
            Text(
                text = "Log in",
                fontSize = 26.sp,
                color = Color(71, 51, 122),
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                },
                label = { Text("Email") },
                placeholder = { Text(text = "Input Email") },
                shape = RoundedCornerShape(percent = 20),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (isEmailValid) Color.Black else Color.Red
                )
            )
            OutlinedTextField(value = password, onValueChange = { newValue ->
                password = newValue
            }, label = { Text(" Password") },
                placeholder = { Text(text = "Input Password") },
                shape = RoundedCornerShape(percent = 20),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    //I couldn't find visible and visibleOff icons...
                    if (isPasswordVisible) {
                        IconButton(onClick = { isPasswordVisible = false }) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Hide")
                        }
                    } else {
                        IconButton(onClick = { isPasswordVisible = true }) {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = "Show")
                        }
                    }
                })
            Button(
                onClick = {
                    if (email == userEmail) {
                        isEmailValid = true
                        if (password == userPassword) {
                            password = ""
                            err = ""
                            navigateToDetailsScreen()
                        } else {
                            password = ""
                            err = "Invalid credentials"
                        }
                    } else {
                        isEmailValid = false
                        err = "Invalid credentials"
                    }
                    keyboardController?.hide()
                },
            ) {
                Text(
                    text = "Log in",
                )
            }
            Text(
                text = err,
                color = Color.Red,
            )
        }
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
                    fontWeight = FontWeight.W700
                )
                Text(
                    text = "Very cool tool for photography",
                )
                Row {
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