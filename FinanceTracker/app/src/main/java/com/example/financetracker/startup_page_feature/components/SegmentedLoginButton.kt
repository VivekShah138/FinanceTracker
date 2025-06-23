package com.example.financetracker.startup_page_feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.example.financetracker.ui.theme.AppTheme

@Composable
fun SegmentedLoginButton(
    selectedType: String,
    onLoginSelected: (String) -> Unit,
    onRegisterSelected: (String) -> Unit,
    selectedColor: Color,
    unSelectedColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background).clip(RoundedCornerShape(28.dp))
    ) {

        Box(
            modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = selectedColor, shape = RoundedCornerShape(30.dp))
        ){

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {
                listOf("Login", "Register").forEach { type ->
                    val isSelected = type == selectedType
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if(type == "Login"){
                                    onLoginSelected(type)
                                }else {
                                    onRegisterSelected(type)
                                }
                            }
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                if (isSelected) selectedColor else unSelectedColor

                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = type,
                            color = if (isSelected) unSelectedColor else selectedColor
                        )
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {
    var selectedType by remember { mutableStateOf("Register") }

    AppTheme(dynamicColor = false, darkTheme = true) {
        SegmentedLoginButton(
            selectedType = selectedType,
            onLoginSelected = { selectedType = it },
            onRegisterSelected = {selectedType = it},
            selectedColor = MaterialTheme.colorScheme.primary,
            unSelectedColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}
