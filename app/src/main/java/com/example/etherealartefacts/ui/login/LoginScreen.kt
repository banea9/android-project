@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.example.etherealartefacts.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.ui.theme.DefaultTextField
import com.example.etherealartefacts.ui.theme.ErrorTextField
import com.example.etherealartefacts.ui.theme.FocusedTextField
import com.example.etherealartefacts.ui.theme.InactivePrimary
import com.example.etherealartefacts.ui.theme.PurplePrimary
import com.example.etherealartefacts.utils.showErrorNotification


@Composable
fun LoginScreen(jwtTokenProvider: JWTTokenProvider, navigateToDetailsScreen: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val logo = painterResource(id = R.drawable.logo)
    val backgroundImage = painterResource(id = R.drawable.background)
    val loginViewModel: LoginViewModel = hiltViewModel()
    val response by loginViewModel.response.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(response) {
        response?.let { result ->
            result.onSuccess { response: LoginResponse ->
                jwtTokenProvider.setJwtToken(response.jwt)
                navigateToDetailsScreen()
            }
            result.onFailure {
                isEmailValid = false
                showErrorNotification(context, "Invalid Credentials")
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 115.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = logo,
                contentDescription = "Logo",
                modifier = Modifier
                    .width(256.dp)
                    .height(162.dp)
            )

            Column(modifier = Modifier.padding(vertical = 56.dp, horizontal = 24.dp)) {
                Text(
                    text = "Log in",
                    fontSize = 30.sp,
                    color = Color(71, 51, 122),
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                    },
                    label = { Text("Email") },
                    placeholder = { Text(text = "Input Text") },
                    shape = RoundedCornerShape(size = 4.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        unfocusedIndicatorColor = DefaultTextField,
                        focusedIndicatorColor = FocusedTextField,
                        errorIndicatorColor = ErrorTextField,
                        textColor = if (isEmailValid) Color.Black else Color.Red
                    )
                )
                OutlinedTextField(value = password, onValueChange = { newValue ->
                    password = newValue
                }, label = { Text(" Password") },
                    shape = RoundedCornerShape(size = 4.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        unfocusedIndicatorColor = DefaultTextField,
                        focusedIndicatorColor = FocusedTextField,
                        errorIndicatorColor = ErrorTextField,
                        textColor = if (isEmailValid) Color.Black else Color.Red
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        if (isPasswordVisible) {
                            IconButton(onClick = { isPasswordVisible = false }) {
                                Icon(
                                    imageVector = Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        } else {
                            IconButton(onClick = { isPasswordVisible = true }) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        }
                    })
                Button(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isLoading) PurplePrimary else InactivePrimary
                    ),
                    onClick = {
                        val body = LoginRequest(email, password)
                        loginViewModel.login(body)
                        keyboardController?.hide()
                    },
                    enabled = !isLoading
                ) {
                    Text(
                        fontSize = 16.sp,
                        text = "Log in",
                    )
                }
            }
        }
    }

}