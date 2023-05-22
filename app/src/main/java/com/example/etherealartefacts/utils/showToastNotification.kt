package com.example.etherealartefacts.utils

import android.content.Context
import android.widget.Toast

fun showErrorNotification(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}