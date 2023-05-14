@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.example.etherealartefacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navigateToDetailsScreen: () -> Unit = {}) {
    var loginViewModel: LoginViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val logo = painterResource(id = R.drawable.logo)
    val userEmail = "test@gmail.com"
    val userPassword = "Start123!"

    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var err by remember { mutableStateOf("") }

    var corountineScope = rememberCoroutineScope()

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
                    if (isPasswordVisible) {
                        IconButton(onClick = { isPasswordVisible = false }) {
                            Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = { isPasswordVisible = true }) {
                            Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
                        }
                    }
                })
            Button(
                onClick = {

                    val body = LoginRequest(email, password)
                    corountineScope.launch(Dispatchers.IO) {
                        loginViewModel.login(body)
                    }
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