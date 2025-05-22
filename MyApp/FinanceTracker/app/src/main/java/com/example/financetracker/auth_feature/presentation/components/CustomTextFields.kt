package com.example.financetracker.auth_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financetracker.auth_feature.presentation.register.RegisterPageEvents
import com.example.financetracker.ui.theme.AppTheme


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


@Composable
fun CustomTextFields2(
    modifier: Modifier = Modifier,
    text : String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onValueChange : (String) -> Unit,
    textStyle: TextStyle,
    singleLine : Boolean,
    inputType : KeyboardOptions,
    isError : Boolean,
    errorMessage: String,
    label: String
){
    Box(modifier = modifier){
        Column{

            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                label = { Text(label) },
                textStyle = textStyle.copy(color = textColor),
                singleLine = singleLine,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = inputType,
                trailingIcon = {
                    if(isError){
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error Icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            if(isError){
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//@Composable
//fun CustomPasswordField(
//    modifier: Modifier = Modifier,
//    text : String,
//    textColor: Color = MaterialTheme.colorScheme.onBackground,
//    onValueChange : (String) -> Unit,
//    textStyle: TextStyle,
//    singleLine : Boolean,
//    inputType : KeyboardOptions,
//    isError : Boolean,
//    errorMessage: String,
//    label: String
//){
//    Box(modifier = modifier){
//        Column{
//
//            OutlinedTextField(
//                value = text,
//                onValueChange = onValueChange,
//                label = { Text(label) },
//                textStyle = textStyle.copy(color = textColor),
//                singleLine = singleLine,
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = inputType,
//                trailingIcon = {
//                    if(isError){
//                        Icon(
//                            imageVector = Icons.Default.Error,
//                            contentDescription = "Error Icon",
//                            tint = MaterialTheme.colorScheme.error
//                        )
//                    }
//                }
//            )
//            if(isError){
//                Text(
//                    text = errorMessage,
//                    textAlign = TextAlign.End,
//                    fontSize = 12.sp,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        }
//    }
//}

@Composable
fun CustomPasswordField(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    singleLine: Boolean,
    isError: Boolean,
    errorMessage: String,
    label: String,
    inputType : KeyboardOptions,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                label = { Text(label) },
                textStyle = textStyle.copy(color = textColor),
                singleLine = singleLine,
                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = inputType,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = inputType,
                trailingIcon = {
                    if (isError) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error Icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    } else {
                        val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = description
                            )
                        }
                    }
                }
            )

            if (isError) {
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}





@Preview(
    showBackground = true,
//    showSystemUi = true
)
@Composable
fun CustomTextFieldsPreview2() {

    AppTheme(
        darkTheme = true,
        dynamicColor = true
    ) {

        CustomTextFields2(
            text = "Vivek",
            onValueChange = {

            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = false,
            errorMessage = "Error Message",
            label = "name"
        )

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CustomTextFieldsPreview(){

    AppTheme(
        darkTheme = true,
        dynamicColor = true
    ) {

        CustomTextFields(
            text = "Vivek",
            onValueChange = {

            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = false
        )

    }

    AppTheme(
        darkTheme = false,
        dynamicColor = true
    ) {

        CustomTextFields(
            text = "Vivek",
            onValueChange = {

            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            inputType = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = false
        )

    }

}