package com.example.financetracker.presentation.features.setup_account_feature.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.financetracker.domain.model.Country

@Composable
fun AutoComplete(
    label: String,
    categories: List<Country>,
    loadCountry: () -> Unit,
    category: String,
    onSearchValueChange: (String) -> Unit,
    onItemSelect: (Country) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    displayText: (Country) -> String
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }


    Column(
        modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
                onExpandedChange(false)
            }
        )
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth(),
                value = category,
                onValueChange = {
                    onSearchValueChange(it)
                    onExpandedChange(true)
                    loadCountry()
                },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onExpandedChange(!expanded)
                            loadCountry()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "arrow",
                            tint = Color.Black
                        )
                    }
                }
            )
        }

        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp)
            ) {
                if (categories.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp),
                ) {
                    val filteredCategories = if (category.isNotEmpty()) {
                        categories.filter {
                            val displayValue = displayText(it)
                            displayValue.lowercase().contains(category.lowercase()) ||
                                    it.name.common.lowercase().contains("others")
                        }.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    } else {
                        categories.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    }

                        if (filteredCategories.isNotEmpty()) {
                    items(filteredCategories) { item ->
                        val displayValue = displayText(item)
                        CategoryItems(
                            title = displayValue,
                            onSelect = {
                                onItemSelect(item)
                            }
                        )
                    }
                        } else {
                            item {
                                Text(
                                    text = "No results found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}






