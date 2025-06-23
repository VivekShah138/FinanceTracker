package com.example.financetracker.setup_account.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.financetracker.setup_account.domain.model.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SimpleDropdownMenu(
    label: String,
    selectedText: String,
    expanded: Boolean,
    list: List<T>,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    displayText: (T) -> String,
    onItemSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(label) },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor() // Needed for correct dropdown positioning
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { item ->
                    val displayValue = displayText(item)  // Get the display value

                    DropdownMenuItem(
                        onClick = {
                            onItemSelect(item)  // Handle item selection
                        },
                        text = { Text(text = displayValue) }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SimpleDropdownMenu2(
    label: String,
    selectedText: String,
    expanded: Boolean,
    list: List<T>,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    displayText: (T) -> String,
    onItemSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false // NEW
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                label = { Text(label) },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    // Show loading spinner inside the dropdown
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
                } else {
                    list.forEach { item ->
                        val displayValue = displayText(item)
                        DropdownMenuItem(
                            onClick = {
                                onItemSelect(item)
                            },
                            text = { Text(text = displayValue) }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SimpleDropdownMenu3(
    label: String,
    selectedText: String,
    expanded: Boolean,
    list: List<T>,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    displayText: (T) -> String,
    onItemSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            ) {
                OutlinedTextField(
                    value = if (isLoading) "" else selectedText,
                    onValueChange = {},
                    label = { Text(label) },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    enabled = !isLoading // Optional: disable input while loading
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {

                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = displayText(item)) },
                        onClick = { onItemSelect(item) }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>SearchableDropdown3(
    label: String,
    selectedText: String,
    expanded: Boolean,
    list: List<T>,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    displayText: (T) -> String,
    onItemSelect: (T) -> Unit,
    isLoading: Boolean = false,
    onChangeSearchValue: (String) -> Unit
) {

    val focusRequester = remember { FocusRequester() }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange(it)
            if(it) focusRequester.requestFocus()
        },
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                onChangeSearchValue(it)
                onExpandedChange(it.isNotBlank())
            },
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    onExpandedChange(it.hasFocus)
                },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            when {
                isLoading -> {
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
                list.isEmpty() -> {
                    DropdownMenuItem(
                        text = { Text("No options") },
                        onClick = {}
                    )
                }
                else -> {
                    Log.d("ProfileCountry","SimpleDropDown list: $list")
                    list.forEach { option ->
                        val displayValue = displayText(option)
                        DropdownMenuItem(
                            onClick = {
                                onItemSelect(option)
                            },
                            text = { Text(text = displayValue) }
                        )
                    }
                }
            }
        }
    }
}


