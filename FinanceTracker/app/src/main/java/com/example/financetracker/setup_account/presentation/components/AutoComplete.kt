package com.example.financetracker.setup_account.presentation.components


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
import androidx.compose.material3.MaterialTheme
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
import com.example.financetracker.setup_account.domain.model.Country

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete2() {

    val categories = listOf(
        "Food",
        "Beverages",
        "Sports",
        "Learning",
        "Travel",
        "Rent",
        "Bills",
        "Fees",
        "Others",
    )

    var category by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = "Category",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {


                // Request focus only when expanded is true, after composition
                LaunchedEffect(expanded) {
                    if (expanded) {
                        focusRequester.requestFocus()
                    }
                }


                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = it
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .focusRequester(focusRequester)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        value = category,
                        onValueChange = {
                            category = it
                            expanded = true
                        },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = "arrow",
                                    tint = Color.Black
                                )
                            }
                        }
                    )

                    val filteredCategories = if (category.isNotEmpty()) {
                        categories.filter {
                            it.contains(category, ignoreCase = true)
                        }
                    } else {
                        categories
                    }.sorted()

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            filteredCategories.forEach { option ->
                                DropdownMenuItem(
                                    onClick = {
//                                        onItemSelect(option)
                                    },
                                    text = {
                                        Text(option)
                                    }
                                )
                            }

//                            LazyColumn(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .heightIn(max = 150.dp),
//                            ) {
//                                val filtered = if (category.isNotEmpty()) {
//                                    categories.filter {
//                                        it.contains(category, ignoreCase = true) || it.equals("others", ignoreCase = true)
//                                    }
//                                } else {
//                                    categories
//                                }.sorted()
//
//                                items(filtered) { item ->
//                                    CategoryItems(title = item) { title ->
//                                        category = title
//                                        expanded = false
//                                    }
//                                }
//                            }
                        }
                    }
                }
            }
        }
    }

}














