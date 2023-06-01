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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.etherealartefacts.R
import com.example.etherealartefacts.models.LoginRequest
import com.example.etherealartefacts.models.LoginResponse
import com.example.etherealartefacts.networking.JWTTokenProvider
import com.example.etherealartefacts.ui.theme.Black
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
    val bottomPadding = dimensionResource(id = R.dimen.login_padding_bottom)

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
                .padding(top = dimensionResource(id = R.dimen.logo_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = logo,
                contentDescription = "Logo",
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.logo_width))
                    .height(dimensionResource(id = R.dimen.logo_height))
            )

            Column(
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.login_ver_padding),
                    horizontal = dimensionResource(id = R.dimen.login_hor_padding)
                )
            ) {
                Text(
                    modifier = Modifier.padding(bottom = bottomPadding),
                    text = stringResource(id = R.string.login_page_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = PurplePrimary,
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                    },
                    label = { Text(stringResource(id = R.string.email_label)) },
                    placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.text_border_radius)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = bottomPadding),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = DefaultTextField,
                        focusedIndicatorColor = FocusedTextField,
                        errorIndicatorColor = ErrorTextField,
                        unfocusedTextColor = if (isEmailValid) Black else ErrorTextField,
                        focusedTextColor = if (isEmailValid) Black else ErrorTextField
                    )
                )
                OutlinedTextField(value = password, onValueChange = { newValue ->
                    password = newValue
                }, label = { Text(stringResource(id = R.string.password_label)) },
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.text_border_radius)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = bottomPadding),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = DefaultTextField,
                        focusedIndicatorColor = FocusedTextField,
                        errorIndicatorColor = ErrorTextField,
                        unfocusedTextColor = if (isEmailValid) Black else ErrorTextField,
                        focusedTextColor = if (isEmailValid) Black else ErrorTextField

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
                                    contentDescription = null,
                                    tint = DefaultTextField
                                )
                            }
                        } else {
                            IconButton(onClick = { isPasswordVisible = true }) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = DefaultTextField
                                )
                            }
                        }
                    })
                Button(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.login_btn_padding))
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
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(id = R.string.login_page_title),
                    )
                }
            }
        }
    }

}