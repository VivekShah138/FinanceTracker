package com.example.financetracker.main_page_feature.view_records.saved_items.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsEvents
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsStates
import com.example.financetracker.main_page_feature.view_records.saved_items.presentation.ViewSavedItemsViewModel


@Composable
fun SearchBar(
    states: ViewSavedItemsStates,
    viewModel: ViewSavedItemsViewModel,
    focusRequester: FocusRequester,
    interactionSource: MutableInteractionSource,
    isFocused: Boolean,
    focusManager: FocusManager
) {

    OutlinedTextField(
        value = states.savedItem,
        onValueChange = {
            viewModel.onEvent(ViewSavedItemsEvents.ChangeSearchSavedItem(it))
            viewModel.onEvent(
                ViewSavedItemsEvents.FilterSavedItemList(
                    list = states.savedItemsList,
                    newWord = it
                )
            )
        },
        singleLine = true,
        label = { Text("Search for items") },
        leadingIcon = {
            if (isFocused) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Arrow",
                    modifier = Modifier.clickable {
                        // Clear text, reload transactions, and remove focus
                        viewModel.onEvent(ViewSavedItemsEvents.ChangeSearchSavedItem(""))
                        viewModel.onEvent(ViewSavedItemsEvents.LoadTransactions)
                        focusManager.clearFocus()
                    }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.clickable {
                        // Optional: focus the text field when search icon is clicked
                        focusRequester.requestFocus()
                    }
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .focusable(true),
        interactionSource = interactionSource
    )
}
