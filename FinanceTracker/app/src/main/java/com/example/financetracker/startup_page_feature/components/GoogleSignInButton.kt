package com.example.financetracker.startup_page_feature.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun GoogleSignInButton(
    onClick:() -> Unit,
    googleIcon: Painter,
    googleText: String,
//    textStyle: TextStyle
) {


    Button(
        onClick = onClick,
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = ButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent
        )
    ){
        Icon(
            painter = googleIcon,
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = googleText,
//            style = textStyle
        )
    }
}