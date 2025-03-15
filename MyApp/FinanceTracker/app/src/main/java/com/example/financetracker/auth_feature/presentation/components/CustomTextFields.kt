package com.example.financetracker.auth_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@Composable
fun CustomTextFields(
    modifier: Modifier = Modifier,
    text : String,
    onValueChange : (String) -> Unit,
    textStyle: TextStyle,
    singleLine : Boolean,
    inputType : KeyboardOptions,
    isError : Boolean
){
    Box(modifier = modifier){

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            singleLine = singleLine,
            keyboardOptions = inputType,
            isError = isError
        )
    }
}